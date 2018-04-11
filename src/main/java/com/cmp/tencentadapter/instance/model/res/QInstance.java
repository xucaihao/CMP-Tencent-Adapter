package com.cmp.tencentadapter.instance.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QInstance {

    @JsonProperty("InstanceId")
    private String instanceId;
    @JsonProperty("InstanceName")
    private String instanceName;
    @JsonProperty("ImageId")
    private String imageId;
    @JsonProperty("OsName")
    private String oSName;
    @JsonProperty("OsType")
    private String oSType;
    private String regionId;
    private String zoneId;
    private String clusterId;
    private String instanceType;
    private Integer cpu;
    private Integer memory;
    private String hostName;
    private String status;
    private String serialNumber;
    private String internetChargeType;
    private Integer internetMaxBandwidthIn;
    private Integer internetMaxBandwidthOut;
    private String vlanId;
    private String creationTime;
    private String startTime;
    private String instanceNetworkType;
    private String instanceChargeType;
    private String saleCycle;
    private String expiredTime;
    private String autoReleaseTime;
    private Boolean ioOptimized;
    private Boolean deviceAvailable;
    private String instanceTypeFamily;
    private Long localStorageCapacity;
    private Integer localStorageAmount;
    private Integer gPUAmount;
    private String gPUSpec;
    private String spotStrategy;
    private Float spotPriceLimit;
    private String resourceGroupId;
    private String keyPairName;
    private Boolean recyclable;
    private String hpcClusterId;
    private String stoppedMode;


}
