package com.cmp.tencentadapter.instance.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 实例的公网使用计费模式，最大带宽等
 */
public class QInternetAccessibleBean {

    /**
     * InternetMaxBandwidthOut : 1
     * InternetChargeType : BANDWIDTH_PREPAID
     */
    @JsonProperty("InternetMaxBandwidthOut")
    private int internetMaxBandwidthOut;

    @JsonProperty("InternetChargeType")
    private String internetChargeType;

    public int getInternetMaxBandwidthOut() {
        return internetMaxBandwidthOut;
    }

    public void setInternetMaxBandwidthOut(int internetMaxBandwidthOut) {
        this.internetMaxBandwidthOut = internetMaxBandwidthOut;
    }

    public String getInternetChargeType() {
        return internetChargeType;
    }

    public void setInternetChargeType(String internetChargeType) {
        this.internetChargeType = internetChargeType;
    }
}
