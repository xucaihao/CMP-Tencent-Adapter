package com.cmp.tencentadapter.snapshot.model;

public class QSnapshot {
    /**
     * snapshotId : snap-58mjhdg0
     * snapshotStatus :
     * percent : 100
     * createTime : 2018-04-16 10:18:15
     * encrypt : null
     * storageSize : 50
     * snapshotName : xuhao-snapshot1
     * zoneId : 160001
     * zoneName : null
     * diskType : root
     * deadline : null
     * projectId : 0
     * storageId : disk-30dbvkoy
     */

    private String snapshotId;
    private String snapshotName;
    /**
     * normal 已创建
     * creating 创建中
     * rollbacking 回滚中
     */
    private String snapshotStatus;
    /**
     * 快照创建进度百分比
     */
    private int percent;
    private String createTime;
    /**
     * 是否加密
     */
    private boolean encrypt;
    /**
     * 源硬盘id
     */
    private String storageId;
    /**
     * 源硬盘大小
     */
    private int storageSize;
    private int zoneId;
    private Object zoneName;
    /**
     * 源硬盘类型
     */
    private String diskType;
    private Object deadline;
    private int projectId;

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public String getSnapshotName() {
        return snapshotName;
    }

    public void setSnapshotName(String snapshotName) {
        this.snapshotName = snapshotName;
    }

    public String getSnapshotStatus() {
        return snapshotStatus;
    }

    public void setSnapshotStatus(String snapshotStatus) {
        this.snapshotStatus = snapshotStatus;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public int getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(int storageSize) {
        this.storageSize = storageSize;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public Object getZoneName() {
        return zoneName;
    }

    public void setZoneName(Object zoneName) {
        this.zoneName = zoneName;
    }

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
    }

    public Object getDeadline() {
        return deadline;
    }

    public void setDeadline(Object deadline) {
        this.deadline = deadline;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
