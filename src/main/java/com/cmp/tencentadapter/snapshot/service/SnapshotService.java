package com.cmp.tencentadapter.snapshot.service;

import com.cmp.tencentadapter.common.CloudEntity;
import com.cmp.tencentadapter.snapshot.model.req.ReqCreSnapshot;
import com.cmp.tencentadapter.snapshot.model.res.ResSnapshots;

public interface SnapshotService {

    /**
     * 查询快照列表
     *
     * @param cloud 云（用户提供ak、sk）
     * @return 快照列表
     */
    ResSnapshots describeSnapshots(CloudEntity cloud);

    /**
     * 创建快照
     *
     * @param cloud          云
     * @param reqCreSnapshot 请求体
     */
    void createSnapshot(CloudEntity cloud, ReqCreSnapshot reqCreSnapshot);
}
