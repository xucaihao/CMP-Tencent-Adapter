package com.cmp.tencentadapter.instance.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class InstanceInfo {

    private String instanceId;

    /**
     * 实例名称
     */
    private String instanceName;

    /**
     * 实例状态
     */
    private String status;

    /**
     * 地域
     */
    private String regionId;

    /**
     * 可用区id
     */
    private String zoneId;

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 到期时间
     */
    private String expiredTime;

    /**
     * 实例机型
     */
    private String instanceType;

    /**
     * 操作系统名称
     */
    @JsonProperty("osname")
    private String osName;

    /**
     * 镜像ID
     */
    private String imageId;

    /**
     * 内存容量，单位：MB。
     */
    private int memory;

    /**
     * CPU核数，单位：核。
     */
    private int cpu;

    /**
     * 数据盘列表
     */
    private List<QDiskBean> dataDisks;

    /**
     * 系统盘
     */
    private QDiskBean systemDisk;

    /**
     * 计费模式
     */
    private String instanceChargeType;

    /**
     * 网络计费类型
     * PayByTraffic：按流量计费
     * PayByBandwidth：按带宽计费
     */
    private String internetChargeType;

    /**
     * 私网Ip列表
     */
    private List<String> innerIpAddress;

    /**
     * 公网ip列表
     */
    private List<String> publicIpAddresses;

    /**
     * 安全组id列表
     */
    private List<String> securityGroupIds;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public List<QDiskBean> getDataDisks() {
        return dataDisks;
    }

    public void setDataDisks(List<QDiskBean> dataDisks) {
        this.dataDisks = dataDisks;
    }

    public QDiskBean getSystemDisk() {
        return systemDisk;
    }

    public void setSystemDisk(QDiskBean systemDisk) {
        this.systemDisk = systemDisk;
    }

    public String getInstanceChargeType() {
        return instanceChargeType;
    }

    public void setInstanceChargeType(String instanceChargeType) {
        this.instanceChargeType = instanceChargeType;
    }

    public String getInternetChargeType() {
        return internetChargeType;
    }

    public void setInternetChargeType(String internetChargeType) {
        this.internetChargeType = internetChargeType;
    }

    public List<String> getInnerIpAddress() {
        return innerIpAddress;
    }

    public void setInnerIpAddress(List<String> innerIpAddress) {
        this.innerIpAddress = innerIpAddress;
    }

    public List<String> getPublicIpAddresses() {
        return publicIpAddresses;
    }

    public void setPublicIpAddresses(List<String> publicIpAddresses) {
        this.publicIpAddresses = publicIpAddresses;
    }

    public List<String> getSecurityGroupIds() {
        return securityGroupIds;
    }

    public void setSecurityGroupIds(List<String> securityGroupIds) {
        this.securityGroupIds = securityGroupIds;
    }
}
