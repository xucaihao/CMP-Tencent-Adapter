package com.cmp.tencentadapter.instance.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 实例VPC相关信息，包括子网，IP信息等
 */
public class QVirtualPrivateCloudBean {

    /**
     * VpcId : vpc-873tug9o
     * AsVpcGateway : false
     * SubnetId : subnet-4t6bvtd5
     */
    @JsonProperty("VpcId")
    private String vpcId;

    @JsonProperty("asVpcGateway")
    private boolean AsVpcGateway;

    @JsonProperty("subnetId")
    private String SubnetId;

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public boolean isAsVpcGateway() {
        return AsVpcGateway;
    }

    public void setAsVpcGateway(boolean asVpcGateway) {
        AsVpcGateway = asVpcGateway;
    }

    public String getSubnetId() {
        return SubnetId;
    }

    public void setSubnetId(String subnetId) {
        SubnetId = subnetId;
    }
}
