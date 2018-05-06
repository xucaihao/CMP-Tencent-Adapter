package com.cmp.tencentadapter.disk.service;

import com.cmp.tencentadapter.common.CloudEntity;
import com.cmp.tencentadapter.disk.model.res.ResDisks;

public interface DiskService {

    /**
     * 查询硬盘列表
     *
     * @param cloud 云
     * @return 硬盘列表
     */
    ResDisks describeDisks(CloudEntity cloud);
}
