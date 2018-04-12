package com.cmp.tencentadapter.instance.service;

import com.cmp.tencentadapter.common.*;
import com.cmp.tencentadapter.instance.model.res.InstanceInfo;
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
                                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, "cd");
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
                                    String instanceSet = jsonResult.getJSONObject("Response").getJSONArray("InstanceSet").toString();
                                    return Optional.ofNullable(JsonUtil.stringToGenericObject(instanceSet,
                                            new TypeReference<List<QInstance>>() {
                                            })).map(instances -> instances.stream().map(instance -> {
                                                InstanceInfo resInstance = new InstanceInfo();

                                                return resInstance;
                                            }).collect(toList())
                                    ).orElseThrow(() -> new RestException("", BAD_REQUEST.value()));
                                }
                            } catch (Exception e) {
                                logger.error("describeRegions in region: {}", region.getLocalName());
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
}
