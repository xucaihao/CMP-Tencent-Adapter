package com.cmp.tencentadapter.instance.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QInstance {


    /**
     * createdTime : 2018-04-12T01:49:44Z
     * dataDisks : null
     * privateIpAddresses : ["172.27.0.5"]
     * instanceId : ins-71mo54qt
     * memory : 2
     * instanceChargeType : PREPAID
     * internetAccessible : {"InternetMaxBandwidthOut":1,"InternetChargeType":"BANDWIDTH_PREPAID"}
     * osName : CentOS 7.2 64位
     * cpu : 1
     * securityGroupIds : null
     * renewFlag : NOTIFY_AND_MANUAL_RENEW
     * instanceName : u672au547du540d
     * systemDisk : {"DiskType":"CLOUD_BASIC","DiskSize":50,"DiskId":"disk-30dbvkoy"}
     * virtualPrivateCloud : {"VpcId":"vpc-873tug9o","AsVpcGateway":false,"SubnetId":"subnet-4t6bvtd5"}
     * placement : {"Zone":"ap-chengdu-1","ProjectId":0,"HostId":null}
     * expiredTime : 2018-05-12T01:49:44Z
     * loginSettings : {"KeyIds":null}
     * imageId : img-31tjrtph
     * publicIpAddresses : ["118.24.15.39"]
     * restrictState : NORMAL
     * instanceType : S2.SMALL2
     */

    /**
     * 实例id
     */
    @JsonProperty("InstanceId")
    private String instanceId;

    /**
     * 实例名称
     */
    @JsonProperty("InstanceName")
    private String instanceName;

    /**
     * 实例所在的位置
     */
    @JsonProperty("Placement")
    private QPlacementBean placement;

    /**
     * 创建时间
     */
    @JsonProperty("CreatedTime")
    private String createdTime;

    /**
     * 到期时间
     */
    @JsonProperty("ExpiredTime")
    private String expiredTime;

    /**
     * 实例机型
     */
    @JsonProperty("InstanceType")
    private String instanceType;

    /**
     * 操作系统名称
     */
    @JsonProperty("OsName")
    private String osName;

    /**
     * 镜像ID
     */
    @JsonProperty("ImageId")
    private String imageId;

    /**
     * 内存容量，单位：GB。
     */
    @JsonProperty("Memory")
    private int memory;

    /**
     * CPU核数，单位：核。
     */
    @JsonProperty("CPU")
    private int cpu;

    /**
     * GPU核数，单位：核。
     */
    @JsonProperty("GPU")
    private int gpu;

    /**
     * 数据盘列表
     */
    @JsonProperty("DataDisks")
    private List<QDiskBean> dataDisks;

    /**
     * 系统盘
     */
    @JsonProperty("systemDisk")
    private QDiskBean systemDisk;

    /**
     * 计费模式
     */
    @JsonProperty("InstanceChargeType")
    private String instanceChargeType;

    /**
     * 自动续费标识。取值范围：
     * NOTIFY_AND_MANUAL_RENEW：表示通知即将过期，但不自动续费
     * NOTIFY_AND_AUTO_RENEW：表示通知即将过期，而且自动续费
     * DISABLE_NOTIFY_AND_MANUAL_RENEW：表示不通知即将过期，也不自动续费
     */
    @JsonProperty("RenewFlag")
    private String renewFlag;

    /**
     * 公网使用计费模式，最大带宽
     */
    @JsonProperty("InternetAccessible")
    private QInternetAccessibleBean internetAccessible;

    /**
     * 私网Ip列表
     */
    @JsonProperty("PrivateIpAddresses")
    private List<String> privateIpAddresses;

    /**
     * 公网ip列表
     */
    @JsonProperty("PublicIpAddresses")
    private List<String> publicIpAddresses;

    /**
     * 安全组id列表
     */
    @JsonProperty("SecurityGroupIds")
    private List<String> securityGroupIds;

    /**
     * 实例VPC相关信息，包括子网，IP信息等
     */
    @JsonProperty("VirtualPrivateCloud")
    private QVirtualPrivateCloudBean virtualPrivateCloud;

    /**
     * 登录信息
     */
    @JsonProperty("LoginSettings")
    private QLoginSettingsBean loginSettings;

    /**
     * 实例业务状态。取值范围：
     * NORMAL：表示正常状态的实例
     * EXPIRED：表示过期的实例
     * PROTECTIVELY_ISOLATED：表示被安全隔离的实例。
     */
    @JsonProperty("RestrictState")
    private String restrictState;

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

    public QPlacementBean getPlacement() {
        return placement;
    }

    public void setPlacement(QPlacementBean placement) {
        this.placement = placement;
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

    public int getGpu() {
        return gpu;
    }

    public void setGpu(int gpu) {
        this.gpu = gpu;
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

    public String getRenewFlag() {
        return renewFlag;
    }

    public void setRenewFlag(String renewFlag) {
        this.renewFlag = renewFlag;
    }

    public QInternetAccessibleBean getInternetAccessible() {
        return internetAccessible;
    }

    public void setInternetAccessible(QInternetAccessibleBean internetAccessible) {
        this.internetAccessible = internetAccessible;
    }

    public List<String> getPrivateIpAddresses() {
        return privateIpAddresses;
    }

    public void setPrivateIpAddresses(List<String> privateIpAddresses) {
        this.privateIpAddresses = privateIpAddresses;
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

    public QVirtualPrivateCloudBean getVirtualPrivateCloud() {
        return virtualPrivateCloud;
    }

    public void setVirtualPrivateCloud(QVirtualPrivateCloudBean virtualPrivateCloud) {
        this.virtualPrivateCloud = virtualPrivateCloud;
    }

    public QLoginSettingsBean getLoginSettings() {
        return loginSettings;
    }

    public void setLoginSettings(QLoginSettingsBean loginSettings) {
        this.loginSettings = loginSettings;
    }

    public String getRestrictState() {
        return restrictState;
    }

    public void setRestrictState(String restrictState) {
        this.restrictState = restrictState;
    }
}
