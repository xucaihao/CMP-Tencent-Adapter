package com.cmp.tencentadapter.instance.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QInstance {
//    [{
//        "InstanceId": "ins-71mo54qt"
//        "InstanceName": "u672au547du540d",
//        "ImageId": "img-31tjrtph",
//        "OsName": "CentOS 7.2 64位",

//        "CreatedTime": "2018-04-12T01:49:44Z",
//        "DataDisks": null,
//        "PrivateIpAddresses": ["172.27.0.5"],
//
//        "Memory": 2,
//        "InstanceChargeType": "PREPAID",
//        "InternetAccessible": {
//            "InternetMaxBandwidthOut": 1,
//            "InternetChargeType": "BANDWIDTH_PREPAID"
//        },
//        "CPU": 1,
//        "SecurityGroupIds": null,
//        "RenewFlag": "NOTIFY_AND_MANUAL_RENEW",

//        "SystemDisk": {
//            "DiskType": "CLOUD_BASIC",
//            "DiskSize": 50,
//            "DiskId": "disk-30dbvkoy"
//        },
//        "VirtualPrivateCloud": {
//            "VpcId": "vpc-873tug9o",
//            "AsVpcGateway": false,
//            "SubnetId": "subnet-4t6bvtd5"
//        },
//        "Placement": {
//            "Zone": "ap-chengdu-1",
//            "ProjectId": 0,
//            "HostId": null
//        },
//        "ExpiredTime": "2018-05-12T01:49:44Z",
//        "LoginSettings": {"KeyIds": null},

//        "PublicIpAddresses": ["118.24.15.39"],
//        "RestrictState": "NORMAL",
//        "InstanceType": "S2.SMALL2"
//    }]

    @JsonProperty("InstanceId")
    private String instanceId;
    @JsonProperty("InstanceName")
    private String instanceName;
    @JsonProperty("ImageId")
    private String imageId;
    @JsonProperty("OsName")
    private String oSName;

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
