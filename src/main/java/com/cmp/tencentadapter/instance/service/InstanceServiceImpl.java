package com.cmp.tencentadapter.instance.service;

import com.cmp.tencentadapter.common.*;
import com.cmp.tencentadapter.instance.model.req.ReqCloseInstance;
import com.cmp.tencentadapter.instance.model.req.ReqStartInstance;
import com.cmp.tencentadapter.instance.model.res.InstanceInfo;
import com.cmp.tencentadapter.instance.model.res.QInstance;
import com.cmp.tencentadapter.instance.model.res.ResInstances;
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

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static com.cmp.tencentadapter.common.Constance.GET;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class InstanceServiceImpl implements InstanceService {

    private static final Logger logger = LoggerFactory.getLogger(InstanceServiceImpl.class);

    @Autowired
    private RegionService regionService;

    private InstanceInfo convertInstance(QInstance instance, String status, String regionId) {
        InstanceInfo resInstance = new InstanceInfo();
        resInstance.setInstanceId(instance.getInstanceId());
        resInstance.setInstanceName(instance.getInstanceName());
        resInstance.setStatus(status);
        resInstance.setRegionId(regionId);
        resInstance.setZoneId(instance.getPlacement().getZone());
        String createdTime = instance.getCreatedTime()
                .replace("T", " ")
                .replace("Z", " ");
        resInstance.setCreationTime(createdTime);
        String expiredTime = instance.getExpiredTime()
                .replace("T", " ")
                .replace("Z", " ");
        resInstance.setExpiredTime(expiredTime);
        resInstance.setInstanceType(instance.getInstanceType());
        resInstance.setOsName(instance.getOsName());
        resInstance.setImageId(instance.getImageId());
        resInstance.setMemory(instance.getMemory() * 1024);
        resInstance.setCpu(instance.getCpu());
        resInstance.setInternetChargeType(instance.getInternetAccessible().getInternetChargeType());
        resInstance.setInstanceChargeType(instance.getInstanceChargeType());
        resInstance.setPublicIpAddress(instance.getPublicIpAddresses());
        resInstance.setInnerIpAddress(instance.getPrivateIpAddresses());
        resInstance.setSecurityGroupIds(instance.getSecurityGroupIds());
        return resInstance;
    }

    private String describeInstancesStatus(CloudEntity cloud, String regionId, String instanceId) {
        try {
            TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, regionId);
            TreeMap<String, Object> param = new TreeMap<>();
            param.put("InstanceIds.0", instanceId);
            String result = TencentClient.call(config, new Cvm(), "DescribeInstancesStatus", param);
            JSONObject jsonResult = new JSONObject(result);
            if (jsonResult.getJSONObject("Response").has("Error")) {
                String message = jsonResult.getJSONObject("Response")
                        .getJSONObject("Error")
                        .getString("Message");
                throw new RestException(message, BAD_REQUEST.value());
            } else {
                return jsonResult.getJSONObject("Response")
                        .getJSONArray("InstanceStatusSet")
                        .getJSONObject(0)
                        .getString("InstanceState");
            }
        } catch (Exception e) {
            logger.error("describeInstancesStatus error: {}, instanceId: {}", e.getMessage(), instanceId);
            return null;
        }
    }

    /**
     * 查询主机列表
     *
     * @param cloud 云（用户提供ak、sk）
     * @return 主机列表
     */
    @Override
    public ResInstances describeInstances(CloudEntity cloud) {
        if (TencentClient.getStatus()) {
            try {
                List<RegionInfo> regions = new ArrayList<>();
                RegionInfo regionInfo = new RegionInfo();
                regionInfo.setRegionId("cd");
                regions.add(regionInfo);
                ResRegions resRegions = new ResRegions(regions);
                //                ResRegions resRegions = regionService.describeRegions(cloud);
                List<CompletionStage<List<InstanceInfo>>> futures = resRegions.getRegions().stream().map(region ->
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, region.getRegionId());
                                TreeMap<String, Object> param = new TreeMap<>();
                                param.put("Limit", 50);
                                String result = TencentClient.call(config, new Cvm(), "DescribeInstances", param);
                                JSONObject jsonResult = new JSONObject(result);
                                if (jsonResult.getJSONObject("Response").has("Error")) {
                                    String message = jsonResult.getJSONObject("Response")
                                            .getJSONObject("Error")
                                            .getString("Message");
                                    throw new RestException(message, BAD_REQUEST.value());
                                } else {
                                    String instanceSet = jsonResult.getJSONObject("Response")
                                            .getJSONArray("InstanceSet")
                                            .toString();
                                    return Optional.ofNullable(JsonUtil.stringToGenericObject(instanceSet,
                                            new TypeReference<List<QInstance>>() {
                                            })).map(instances -> instances.stream().map(instance -> {
                                                //查询主机运行状态
                                                String instanceId = instance.getInstanceId();
                                                String status = describeInstancesStatus(cloud, "cd", instanceId);
                                                return convertInstance(instance, status, region.getRegionId());
                                            }).collect(toList())
                                    ).orElseThrow(() -> new RestException("", BAD_REQUEST.value()));
                                }
                            } catch (Exception e) {
                                logger.error("describeRegions in region: {} occurred error: {}", region.getLocalName(), e.getMessage());
                                return null;
                            }
                        })
                ).collect(toList());
                List<InstanceInfo> instances = CommonUtil.aggregateList(CommonUtil.joinRes(futures));
                return new ResInstances(instances);
            } catch (Exception e) {
                throw (RuntimeException) e;
            }
        } else {
            List<InstanceInfo> instances = TencentSimulator.getAll(InstanceInfo.class);
            return new ResInstances(instances);
        }
    }

    /**
     * 关闭实例
     *
     * @param cloud            云（用户提供ak、sk）
     * @param reqCloseInstance 请求体
     */
    @Override
    public void closeInstance(CloudEntity cloud, ReqCloseInstance reqCloseInstance) {
        if (TencentClient.getStatus()) {
            try {
                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, reqCloseInstance.getRegionId());
                TreeMap<String, Object> param = new TreeMap<>();
                param.put("InstanceIds.0", reqCloseInstance.getInstanceId());
                param.put("ForceStop", reqCloseInstance.isForceStop());
                String result = TencentClient.call(config, new Cvm(), "StopInstances", param);
                JSONObject jsonResult = new JSONObject(result);
                if (jsonResult.getJSONObject("Response").has("Error")) {
                    String message = jsonResult.getJSONObject("Response")
                            .getJSONObject("Error")
                            .getString("Message");
                    throw new RestException(message, BAD_REQUEST.value());
                }
            } catch (Exception e) {
                logger.error("closeInstance in region: {} occurred error: {}", reqCloseInstance.getRegionId(), e.getMessage());
                throw (RuntimeException) e;
            }
        } else {
            Map<String, Object> values = new HashMap<>(16);
            values.put("status", "stopped");
            TencentSimulator.modify(InstanceInfo.class, reqCloseInstance.getInstanceId(), values);
        }
    }

    /**
     * 启动实例
     *
     * @param cloud            云（用户提供ak、sk）
     * @param reqStartInstance 请求体
     */
    @Override
    public void startInstance(CloudEntity cloud, ReqStartInstance reqStartInstance) {
        if (TencentClient.getStatus()) {
            try {
                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, reqStartInstance.getRegionId());
                TreeMap<String, Object> param = new TreeMap<>();
                param.put("InstanceIds.0", reqStartInstance.getInstanceId());
                String result = TencentClient.call(config, new Cvm(), "StartInstances", param);
                JSONObject jsonResult = new JSONObject(result);
                if (jsonResult.getJSONObject("Response").has("Error")) {
                    String message = jsonResult.getJSONObject("Response")
                            .getJSONObject("Error")
                            .getString("Message");
                    throw new RestException(message, BAD_REQUEST.value());
                }
            } catch (Exception e) {
                logger.error("startInstance in region: {} occurred error: {}", reqStartInstance.getRegionId(), e.getMessage());
                throw (RuntimeException) e;
            }
        } else {
            Map<String, Object> values = new HashMap<>(16);
            values.put("status", "running");
            TencentSimulator.modify(InstanceInfo.class, reqStartInstance.getInstanceId(), values);
        }
    }
}
