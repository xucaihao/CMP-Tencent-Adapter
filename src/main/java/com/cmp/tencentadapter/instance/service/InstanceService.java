package com.cmp.tencentadapter.instance.service;

import com.cmp.tencentadapter.common.CloudEntity;
import com.cmp.tencentadapter.instance.model.res.ResInstances;

public interface InstanceService {

    /**
     * 查询主机列表
     *
     * @param cloud 云（用户提供ak、sk）
     * @return 主机列表
     */
    ResInstances describeInstances(CloudEntity cloud);

//    /**
//     * 查询指定主机
//     * @param cloud
//     * @param regionId
//     * @param instanceId
//     * @return
//     */
//    ResInstance describeInstanceAttribute(
//            CloudEntity cloud, String regionId, String instanceId);
}
