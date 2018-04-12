package com.cmp.tencentadapter.instance.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 实例系统盘、数据盘信息
 */
public class QDiskBean {
    /**
     * DiskType : CLOUD_BASIC
     * DiskSize : 50
     * DiskId : disk-30dbvkoy
     */
    @JsonProperty("DiskType")
    private String diskType;

    @JsonProperty("DiskSize")
    private int diskSize;

    @JsonProperty("DiskId")
    private String diskId;

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
    }

    public int getDiskSize() {
        return diskSize;
    }

    public void setDiskSize(int diskSize) {
        this.diskSize = diskSize;
    }

    public String getDiskId() {
        return diskId;
    }

    public void setDiskId(String diskId) {
        this.diskId = diskId;
    }
}
