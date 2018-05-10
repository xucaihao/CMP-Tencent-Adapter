package com.cmp.tencentadapter.snapshot.service;

import com.cmp.tencentadapter.common.CloudEntity;
import com.cmp.tencentadapter.snapshot.model.req.ReqCreSnapshot;
import com.cmp.tencentadapter.snapshot.model.res.ResSnapshot;
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
     * 查询指定快照
     *
     * @param cloud      云（用户提供ak、sk）
     * @param regionId   区域id
     * @param snapshotId 快照id
     * @return 指定快照信息
     */
    ResSnapshot describeSnapshotAttribute(CloudEntity cloud, String regionId, String snapshotId);

    /**
     * 创建快照
     *
     * @param cloud          云
     * @param reqCreSnapshot 请求体
     */
    void createSnapshot(CloudEntity cloud, ReqCreSnapshot reqCreSnapshot);

    /**
     * 删除快照
     *
     * @param cloud      云（用户提供ak、sk）
     * @param regionId   区域id
     * @param snapshotId 快照id
     */
    void deleteSnapshot(CloudEntity cloud, String regionId, String snapshotId);
}
