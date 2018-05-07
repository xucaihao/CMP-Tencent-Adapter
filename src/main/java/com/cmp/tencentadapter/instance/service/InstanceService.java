package com.cmp.tencentadapter.instance.service;

import com.cmp.tencentadapter.common.CloudEntity;
import com.cmp.tencentadapter.instance.model.req.ReqCloseInstance;
import com.cmp.tencentadapter.instance.model.req.ReqModifyInstance;
import com.cmp.tencentadapter.instance.model.req.ReqStartInstance;
import com.cmp.tencentadapter.instance.model.res.ResInstance;
import com.cmp.tencentadapter.instance.model.res.ResInstances;

public interface InstanceService {

    /**
     * 查询主机列表
     *
     * @param cloud 云（用户提供ak、sk）
     * @return 主机列表
     */
    ResInstances describeInstances(CloudEntity cloud);

    /**
     * 查询指定主机
     *
     * @param cloud      云
     * @param regionId   区域id
     * @param instanceId 实例id
     * @return 指定主机
     */
    ResInstance describeInstanceAttribute(CloudEntity cloud, String regionId, String instanceId);

    /**
     * 关闭实例
     *
     * @param cloud            云（用户提供ak、sk）
     * @param reqCloseInstance 请求体
     */
    void closeInstance(CloudEntity cloud, ReqCloseInstance reqCloseInstance);

    /**
     * 启动实例
     *
     * @param cloud            云（用户提供ak、sk）
     * @param reqStartInstance 请求体
     */
    void startInstance(CloudEntity cloud, ReqStartInstance reqStartInstance);

    /**
     * 修改实例名称
     *
     * @param cloud             云（用户提供ak、sk）
     * @param reqModifyInstance 请求体
     */
    void modifyInstanceName(CloudEntity cloud, ReqModifyInstance reqModifyInstance);

    /**
     * 重置实例密码
     *
     * @param cloud             云（用户提供ak、sk）
     * @param reqModifyInstance 请求体
     */
    void resetInstancesPassword(CloudEntity cloud, ReqModifyInstance reqModifyInstance);

}
