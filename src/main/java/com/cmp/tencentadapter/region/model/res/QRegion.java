package com.cmp.tencentadapter.region.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QRegion {

    @JsonProperty("Region")
    private String region;
    @JsonProperty("RegionName")
    private String regionName;
    @JsonProperty("RegionState")
    private String regionState;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionState() {
        return regionState;
    }

    public void setRegionState(String regionState) {
        this.regionState = regionState;
    }
}
