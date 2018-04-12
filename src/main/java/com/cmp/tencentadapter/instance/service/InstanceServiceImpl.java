package com.cmp.tencentadapter.instance.service;

import com.cmp.tencentadapter.common.*;
import com.cmp.tencentadapter.instance.model.res.InstanceInfo;
import com.cmp.tencentadapter.instance.model.res.QDiskBean;
import com.cmp.tencentadapter.instance.model.res.QInstance;
import com.cmp.tencentadapter.instance.model.res.ResInstances;
import com.cmp.tencentadapter.region.model.res.ResRegions;
import com.cmp.tencentadapter.region.service.RegionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.qcloud.Module.Cvm;
import com.qcloud.Utilities.Json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
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

    private InstanceInfo convertInstance(QInstance instance, String status) {
        InstanceInfo resInstance = new InstanceInfo();
        resInstance.setInstanceId(instance.getInstanceId());
        resInstance.setInstanceName(instance.getInstanceName());
        resInstance.setStatus(status);
        resInstance.setZoneId(instance.getPlacement().getZone());
        resInstance.setCreatedTime(instance.getCreatedTime());
        resInstance.setExpiredTime(instance.getExpiredTime());
        resInstance.setInstanceType(instance.getInstanceType());
        resInstance.setOsName(instance.getOsName());
        resInstance.setImageId(instance.getImageId());
        resInstance.setMemory(instance.getMemory() * 1024);
        resInstance.setCpu(instance.getCpu());
        resInstance.setInternetChargeType(instance.getInstanceChargeType());
        resInstance.setInternetChargeType(instance.getInstanceChargeType());
        resInstance.setPublicIpAddresses(instance.getPublicIpAddresses());
        resInstance.setInnerIpAddress(instance.getPrivateIpAddresses());
        resInstance.setSecurityGroupIds(instance.getSecurityGroupIds());
        return resInstance;
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
                ResRegions resRegions = regionService.describeRegions(cloud);
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
                                                String status = describeInstancesStatus(cloud, region.getRegionId(), instanceId);
                                                return convertInstance(instance, status);
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

    private String describeInstancesStatus(CloudEntity cloud, String regionId, String instanceId) {
        try {
            try {
                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, regionId);
                TreeMap<String, Object> param = new TreeMap<>();
                List<String> instanceIds = new ArrayList<>();
                instanceIds.add("InstanceIds.0=" + instanceId);
                param.put("InstanceIds", instanceIds);
                String result = TencentClient.call(config, new Cvm(), "DescribeInstancesStatus", param);
                JSONObject jsonResult = new JSONObject(result);
                if (jsonResult.getJSONObject("Response").has("Error")) {
                    String message = jsonResult.getJSONObject("Response")
                            .getJSONObject("Error")
                            .getString("Message");
                    throw new RestException(message, BAD_REQUEST.value());
                } else {
                    return jsonResult.getJSONObject("Response")
                            .getJSONObject("InstanceStatusSet")
                            .getString("InstanceState");
                }
            } catch (Exception e) {
                logger.error("describeInstancesStatus error: {}, instanceId: {}", e.getMessage(), instanceId);
                return null;
            }
        } catch (Exception e) {
            throw (RuntimeException) e;
        }
    }

}
