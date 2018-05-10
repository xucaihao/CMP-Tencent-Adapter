package com.cmp.tencentadapter.image.service;

import com.cmp.tencentadapter.common.*;
import com.cmp.tencentadapter.image.model.req.ReqCreImage;
import com.cmp.tencentadapter.image.model.res.ImageInfo;
import com.cmp.tencentadapter.image.model.res.QImage;
import com.cmp.tencentadapter.image.model.res.ResImage;
import com.cmp.tencentadapter.image.model.res.ResImages;
import com.cmp.tencentadapter.region.model.res.RegionInfo;
import com.cmp.tencentadapter.region.model.res.ResRegions;
import com.cmp.tencentadapter.region.service.RegionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.qcloud.Module.Image;
import com.qcloud.Module.Snapshot;
import com.qcloud.Utilities.Json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static com.cmp.tencentadapter.common.Constance.GET;
import static com.cmp.tencentadapter.common.Constance.SUCCESS;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Autowired
    private RegionService regionService;

    private ImageInfo convertImage(QImage image, String regionId) {
        ImageInfo resImage = new ImageInfo();
        resImage.setImageId(image.getImageId());
        resImage.setImageName(image.getImageName());
        resImage.setRegionId(regionId);
        resImage.setCreationTime("---");
        if (!StringUtils.isEmpty(image.getCreatedTime())) {
            String createdTime = image.getCreatedTime()
                    .replace("T", " ")
                    .replace("Z", " ");
            resImage.setCreationTime(createdTime);
        }
        resImage.setPlatform(image.getPlatform());
        resImage.setArchitecture(image.getArchitecture());
        resImage.setSize(image.getImageSize());
        resImage.setOsName(image.getOsName());
        resImage.setSupportCloudInit(image.isSupportCloudInit());
        resImage.setImageOwnerAlias(image.getImageType());
        resImage.setDescription(image.getImageDescription());
        resImage.setStatus(image.getImageState());
        return resImage;
    }

    /**
     * 查询镜像列表
     *
     * @param cloud 云（用户提供ak、sk）
     * @return 镜像列表
     */
    @Override
    public ResImages describeImages(CloudEntity cloud) {
        if (TencentClient.getStatus()) {
            try {
                List<RegionInfo> regions = new ArrayList<>();
                RegionInfo regionInfo = new RegionInfo();
                regionInfo.setRegionId("cd");
                regions.add(regionInfo);
                ResRegions resRegions = new ResRegions(regions);
                //                ResRegions resRegions = regionService.describeRegions(cloud);
                List<CompletionStage<List<ImageInfo>>> futures = resRegions.getRegions().stream().map(region ->
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, region.getRegionId());
                                TreeMap<String, Object> param = new TreeMap<>();
                                param.put("Limit", 50);
                                String result = TencentClient.call(config, new Image(), "DescribeImages", param);
                                JSONObject jsonResult = new JSONObject(result);
                                if (jsonResult.getJSONObject("Response").has("Error")) {
                                    String message = jsonResult.getJSONObject("Response")
                                            .getJSONObject("Error")
                                            .getString("Message");
                                    throw new RestException(message, BAD_REQUEST.value());
                                } else {
                                    String imageSet = jsonResult.getJSONObject("Response")
                                            .getJSONArray("ImageSet")
                                            .toString();
                                    return Optional.ofNullable(JsonUtil.stringToGenericObject(imageSet,
                                            new TypeReference<List<QImage>>() {
                                            })).map(instances -> instances.stream().map(image ->
                                                    convertImage(image, region.getRegionId())
                                            ).collect(toList())
                                    ).orElseThrow(() -> new RestException("", BAD_REQUEST.value()));
                                }
                            } catch (Exception e) {
                                logger.error("describeImages in region: {} occurred error: {}", region.getRegionId(), e.getMessage());
                                return null;
                            }
                        })
                ).collect(toList());
                List<ImageInfo> images = CommonUtil.aggregateList(CommonUtil.joinRes(futures));
                return new ResImages(images);
            } catch (Exception e) {
                throw (RuntimeException) e;
            }
        } else {
            List<ImageInfo> images = TencentSimulator.getAll(ImageInfo.class);
            return new ResImages(images);
        }
    }

    /**
     * 查询指定镜像
     *
     * @param cloud    云（用户提供ak、sk）
     * @param regionId 区域id
     * @param imageId  镜像id
     * @return 指定镜像信息
     */
    @Override
    public ResImage describeImageAttribute(CloudEntity cloud, String regionId, String imageId) {
        if (TencentClient.getStatus()) {
            try {
                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, regionId);
                TreeMap<String, Object> param = new TreeMap<>();
                param.put("ImageIds.0", imageId);
                String result = TencentClient.call(config, new Image(), "DescribeImages", param);
                JSONObject jsonResult = new JSONObject(result);
                if (jsonResult.getJSONObject("Response").has("Error")) {
                    String message = jsonResult.getJSONObject("Response")
                            .getJSONObject("Error")
                            .getString("Message");
                    throw new RestException(message, BAD_REQUEST.value());
                } else {
                    String imageSet = jsonResult.getJSONObject("Response")
                            .getJSONArray("ImageSet")
                            .get(0)
                            .toString();
                    ImageInfo imageInfo = Optional.ofNullable(JsonUtil.stringToObject(imageSet, QImage.class))
                            .map(image -> convertImage(image, regionId))
                            .orElseThrow(() -> new RestException("", BAD_REQUEST.value()));
                    return new ResImage(imageInfo);
                }
            } catch (Exception e) {
                logger.error("describeImageAttribute in region: {} occurred error: {}", regionId, e.getMessage());
                throw (RuntimeException) e;
            }
        } else {
            ImageInfo image = TencentSimulator.get(ImageInfo.class, imageId);
            return new ResImage(image);
        }
    }

    /**
     * 创建镜像
     *
     * @param cloud       云
     * @param reqCreImage 请求体
     */
    @Override
    public void createImage(CloudEntity cloud, ReqCreImage reqCreImage) {
        if (TencentClient.getStatus()) {
            try {
                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, reqCreImage.getRegionId());
                TreeMap<String, Object> param = new TreeMap<>();
                param.put("InstanceId", reqCreImage.getInstanceId());
                param.put("ImageName", reqCreImage.getImageName());
                param.put("ImageDescription", reqCreImage.getDescription());
                param.put("ForcePoweroff", reqCreImage.isForceStop());
                String result = TencentClient.call(config, new Image(), "CreateImage", param);
                JSONObject jsonResult = new JSONObject(result);
                if (jsonResult.getJSONObject("Response").has("Error")) {
                    String message = jsonResult.getJSONObject("Response")
                            .getJSONObject("Error")
                            .getString("Message");
                    throw new RestException(message, BAD_REQUEST.value());
                }
            } catch (Exception e) {
                logger.error("createImage in region: {} occurred error: {}", reqCreImage.getRegionId(), e.getMessage());
                throw (RuntimeException) e;
            }
        }
    }

    /**
     * 删除镜像
     *
     * @param cloud    云（用户提供ak、sk）
     * @param regionId 区域id
     * @param imageId  镜像id
     */
    @Override
    public void deleteImage(CloudEntity cloud, String regionId, String imageId) {
        if (TencentClient.getStatus()) {
            try {
                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, regionId);
                TreeMap<String, Object> param = new TreeMap<>();
                param.put("ImageIds.0", imageId);
                String result = TencentClient.call(config, new Image(), "DeleteImages", param);
                JSONObject jsonResult = new JSONObject(result);
                if (jsonResult.getJSONObject("Response").has("Error")) {
                    String message = jsonResult.getJSONObject("Response")
                            .getJSONObject("Error")
                            .getString("Message");
                    throw new RestException(message, BAD_REQUEST.value());
                }
            } catch (Exception e) {
                logger.error("deleteImage in region: {} occurred error: {}", regionId, e.getMessage());
                throw (RuntimeException) e;
            }
        }
    }
}
