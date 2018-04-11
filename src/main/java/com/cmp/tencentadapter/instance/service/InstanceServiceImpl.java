package com.cmp.tencentadapter.instance.service;

import com.cmp.tencentadapter.common.CloudEntity;
import com.cmp.tencentadapter.common.JsonUtil;
import com.cmp.tencentadapter.common.RestException;
import com.cmp.tencentadapter.common.TencentClient;
import com.cmp.tencentadapter.instance.model.res.InstanceInfo;
import com.cmp.tencentadapter.instance.model.res.QInstance;
import com.cmp.tencentadapter.region.model.res.QRegion;
import com.cmp.tencentadapter.region.model.res.RegionInfo;
import com.cmp.tencentadapter.region.model.res.ResRegions;
import com.cmp.tencentadapter.region.service.RegionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.qcloud.Module.Cvm;
import com.qcloud.Utilities.Json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

import static com.cmp.tencentadapter.common.Constance.GET;
import static com.cmp.tencentadapter.common.Constance.SUCCESS;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class InstanceServiceImpl implements InstanceService {

    private static final Logger logger = LoggerFactory.getLogger(InstanceServiceImpl.class);

    @Autowired
    private RegionService regionService;

    /**
     * 查询所有地域列表
     *
     * @param cloud 云（提供ak,sk）
     * @return 所有地域列表
     */
    @Override
    public ResRegions describeInstances(CloudEntity cloud) {
        if (TencentClient.getStatus()) {
            try {
                ResRegions resRegions = regionService.describeRegions(cloud);
                resRegions.getRegions().stream().map(region ->
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, region.getRegionId());
                                TreeMap<String, Object> param = new TreeMap<>();
                                param.put("Limit", 50);
                                String result = TencentClient.call(config, new Cvm(), "DescribeInstances", param);
                                JSONObject jsonResult = new JSONObject(result);
                                String codeDesc = (String) jsonResult.get("codeDesc");
                                if (SUCCESS.equals(codeDesc.toLowerCase())) {
                                    String instanceSet = jsonResult.getJSONArray("InstanceSet").toString();
                                    List<InstanceInfo> resInstances = Optional.ofNullable(JsonUtil.stringToGenericObject(instanceSet,
                                            new TypeReference<List<QInstance>>() {
                                            })).map(instances -> instances.stream().map(instance -> {
                                                InstanceInfo resInstance = new InstanceInfo();

                                                return resInstance;
                                            }).collect(toList())
                                    ).orElseThrow(() -> new RestException("", BAD_REQUEST.value()));
                                    return resInstances;
                                } else {
                                    String message = (String) jsonResult.get("message");
                                    throw new RestException(message, BAD_REQUEST.value());
                                }
                            } catch (Exception e) {
                                logger.error("");
                            }

                        })
                ).collect(toList());
                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, "ap-guangzhou");
                TreeMap<String, Object> param = new TreeMap<>();
                String result = TencentClient.call(config, new Cvm(), "DescribeRegions", param);
                JSONObject jsonResult = new JSONObject(result);
                String codeDesc = (String) jsonResult.get("codeDesc");
                if (SUCCESS.equals(codeDesc.toLowerCase())) {
                    String regionSet = jsonResult.getJSONArray("regionSet").toString();
                    List<RegionInfo> resRegions = Optional.ofNullable(JsonUtil.stringToGenericObject(regionSet, new TypeReference<List<QRegion>>() {
                    })).map(regions -> regions.stream().map(region -> {
                                RegionInfo resRegion = new RegionInfo();
                                resRegion.setRegionId(region.getRegion());
                                resRegion.setLocalName(region.getRegionName());
                                resRegion.setStatus(region.getRegionState());
                                return resRegion;
                            }).collect(toList())
                    ).orElseThrow(() -> new RestException("", BAD_REQUEST.value()));
                    return new ResRegions(resRegions);
                } else {
                    String message = (String) jsonResult.get("message");
                    throw new RestException(message, BAD_REQUEST.value());
                }
            } catch (Exception e) {
                throw (RuntimeException) e;
            }
        } else {

        }
    }
}
