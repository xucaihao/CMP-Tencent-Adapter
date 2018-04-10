package com.cmp.tencentadapter.region.service;

import com.cmp.tencentadapter.common.CloudEntity;
import com.cmp.tencentadapter.region.model.res.ResRegions;

public interface RegionService {

    /**
     * 查询所有地域列表
     *
     * @param cloud 云（提供ak,sk）
     * @return 所有地域列表
     */
    ResRegions describeRegions(CloudEntity cloud);
}
