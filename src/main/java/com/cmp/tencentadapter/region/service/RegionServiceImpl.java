package com.cmp.tencentadapter.region.service;

import com.cmp.tencentadapter.common.CloudEntity;
import com.cmp.tencentadapter.common.JsonUtil;
import com.cmp.tencentadapter.common.RestException;
import com.cmp.tencentadapter.common.TencentClient;
import com.cmp.tencentadapter.region.model.res.RegionEntity;
import com.cmp.tencentadapter.region.model.res.RegionInfo;
import com.cmp.tencentadapter.region.model.res.ResRegions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.qcloud.Module.Cvm;
import com.qcloud.Utilities.Json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import static com.cmp.tencentadapter.common.Constance.GET;
import static com.cmp.tencentadapter.common.Constance.SUCCESS;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class RegionServiceImpl implements RegionService {

    private static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);

    /**
     * 查询所有地域列表
     *
     * @param cloud 云（提供ak,sk）
     * @return 所有地域列表
     */
    @Override
    public ResRegions describeRegions(CloudEntity cloud) {
        if (TencentClient.getStatus()) {
            try {
                TreeMap<String, Object> config = TencentClient.initConfig(cloud, GET, "ap-guangzhou");
                String result = TencentClient.call(config, new Cvm(), "DescribeRegions");
                JSONObject jsonResult = new JSONObject(result);
                String codeDesc = (String) jsonResult.get("codeDesc");
                if (SUCCESS.equals(codeDesc.toLowerCase())) {
                    String regionSet = jsonResult.getJSONArray("regionSet").toString();
                    List<RegionEntity> resRegions = Optional.ofNullable(JsonUtil.stringToGenericObject(regionSet, new TypeReference<List<RegionInfo>>() {
                    })).map(regions -> regions.stream().map(region -> {
                                RegionEntity resRegion = new RegionEntity();
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
            List<RegionEntity> regions = new ArrayList<>();
            RegionEntity region0 = new RegionEntity();
            region0.setRegionId("ap-beijing");
            region0.setLocalName("华北地区(北京)");
            region0.setStatus("AVAILABLE");
            regions.add(region0);

            RegionEntity region1 = new RegionEntity();
            region1.setRegionId("ap-guangzhou");
            region1.setLocalName("华南地区(广州)");
            region1.setStatus("AVAILABLE");
            regions.add(region1);

            RegionEntity region2 = new RegionEntity();
            region2.setRegionId("ap-guangzhou-open");
            region2.setLocalName("华南地区(广州Open)");
            region2.setStatus("AVAILABLE");
            regions.add(region2);

            RegionEntity region3 = new RegionEntity();
            region3.setRegionId("ap-hongkong");
            region3.setLocalName("东南亚地区(香港)");
            regions.add(region3);

            RegionEntity region4 = new RegionEntity();
            region4.setRegionId("ap-shanghai");
            region4.setLocalName("华东地区(上海)");
            regions.add(region4);

            return new ResRegions(regions);
        }
    }
}
