package com.cmp.tencentadapter.disk.service;

import com.cmp.tencentadapter.common.CloudEntity;
import com.cmp.tencentadapter.disk.model.req.ReqModifyDisk;
import com.cmp.tencentadapter.disk.model.res.ResDisks;

public interface DiskService {

    /**
     * 查询硬盘列表
     *
     * @param cloud 云
     * @return 硬盘列表
     */
    ResDisks describeDisks(CloudEntity cloud);

    /**
     * 修改硬盘名称
     *
     * @param cloud         云（用户提供ak、sk）
     * @param reqModifyDisk 请求体
     */
    void modifyDiskName(CloudEntity cloud, ReqModifyDisk reqModifyDisk);
}
