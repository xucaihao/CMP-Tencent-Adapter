package com.cmp.tencentadapter.disk.service;

import com.cmp.tencentadapter.common.*;
import com.cmp.tencentadapter.disk.model.req.ReqModifyDisk;
import com.cmp.tencentadapter.disk.model.res.DiskInfo;
import com.cmp.tencentadapter.disk.model.res.QDisk;
import com.cmp.tencentadapter.disk.model.res.ResDisks;
import com.cmp.tencentadapter.region.model.res.RegionInfo;
import com.cmp.tencentadapter.region.model.res.ResRegions;
import com.cmp.tencentadapter.region.service.RegionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.qcloud.Module.Cbs;
import com.qcloud.Module.Cvm;
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
public class DiskServiceImpl implements DiskService {

    private static final Logger logger = LoggerFactory.getLogger(DiskServiceImpl.class);

    @Autowired
    private RegionService regionService;

    private DiskInfo convertDisk(QDisk disk, String regionId) {
        DiskInfo resDisk = new DiskInfo();
        resDisk.setDiskId(disk.getStorageId());
        resDisk.setDiskName(disk.getStorageName());
        resDisk.setRegionId(regionId);
        resDisk.setDescription("");
        resDisk.setType(disk.getDiskType());
        resDisk.setCategory(disk.getStorageType());
        resDisk.setEncrypted(disk.getEncrypt());
        resDisk.setSize(disk.getStorageSize());
        resDisk.setStatus(disk.getStorageStatus());
        resDisk.setInstanceId(disk.getuInstanceId());
        if (!StringUtils.isEmpty(disk.getCreateTime())) {
            String createdTime = disk.getCreateTime()
                    .replace("T", " ")
                    .replace("Z", " ");
            resDisk.setCreationTime(createdTime);
        }
        resDisk.setDiskChargeType(disk.getPayMode());
        if (1 == disk.getPortable()) {
            resDisk.setPortable(true);
        } else {
            resDisk.setPortable(false);
        }
        return resDisk;
    }

    /**
     * 查询硬盘列表
     *
     * @param cloud 云
     * @return 硬盘列表
     */
    @Override
    public ResDisks describeDisks(CloudEntity cloud) {
        if (TencentClient.getStatus()) {
            try {
                List<RegionInfo> regions = new ArrayList<>();
                RegionInfo regionInfo = new RegionInfo();
                regionInfo.setRegionId("cd");
                regions.add(regionInfo);
                ResRegions resRegions = new ResRegions(regions);
                //                ResRegions resRegions = regionService.describeRegions(cloud);
                List<CompletionStage<List<DiskInfo>>> futures = resRegions.getRegions().stream().map(region ->
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, region.getRegionId());
                                TreeMap<String, Object> param = new TreeMap<>();
                                param.put("Limit", 50);
                                String result = TencentClient.call(config, new Cbs(), "DescribeCbsStorages", param);
                                JSONObject jsonResult = new JSONObject(result);
                                if (!SUCCESS.equals(jsonResult.getString("codeDesc").toLowerCase())) {
                                    throw new RestException(jsonResult.getString("message"), BAD_REQUEST.value());
                                } else {
                                    String diskSet = jsonResult.getJSONArray("storageSet")
                                            .toString();
                                    return Optional.ofNullable(JsonUtil.stringToGenericObject(diskSet,
                                            new TypeReference<List<QDisk>>() {
                                            })).map(disks -> disks.stream().map(disk ->
                                                    convertDisk(disk, region.getRegionId())
                                            ).collect(toList())
                                    ).orElseThrow(() -> new RestException("", BAD_REQUEST.value()));
                                }
                            } catch (Exception e) {
                                logger.error("describeDisks in region: {} occurred error: {}", region.getRegionId(), e.getMessage());
                                return null;
                            }
                        })
                ).collect(toList());
                List<DiskInfo> disks = CommonUtil.aggregateList(CommonUtil.joinRes(futures));
                return new ResDisks(disks);
            } catch (Exception e) {
                throw (RuntimeException) e;
            }
        } else {
            List<DiskInfo> disks = TencentSimulator.getAll(DiskInfo.class);
            return new ResDisks(disks);
        }
    }

    /**
     * 修改硬盘名称
     *
     * @param cloud         云（用户提供ak、sk）
     * @param reqModifyDisk 请求体
     */
    @Override
    public void modifyDiskName(CloudEntity cloud, ReqModifyDisk reqModifyDisk) {
        if (TencentClient.getStatus()) {
            try {
                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, reqModifyDisk.getRegionId());
                TreeMap<String, Object> param = new TreeMap<>();
                param.put("storageId", reqModifyDisk.getDiskId());
                param.put("storageName", reqModifyDisk.getDiskName());
                String result = TencentClient.call(config, new Cvm(), "ModifyCbsStorageAttributes", param);
                JSONObject jsonResult = new JSONObject(result);
                if (jsonResult.getJSONObject("Response").has("Error")) {
                    String message = jsonResult.getJSONObject("Response")
                            .getJSONObject("Error")
                            .getString("Message");
                    throw new RestException(message, BAD_REQUEST.value());
                }
            } catch (Exception e) {
                logger.error("modifyDiskName in region: {} occurred error: {}", reqModifyDisk.getRegionId(), e.getMessage());
                throw (RuntimeException) e;
            }
        }
    }
}
