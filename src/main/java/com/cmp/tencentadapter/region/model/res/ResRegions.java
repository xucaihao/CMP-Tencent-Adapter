package com.cmp.tencentadapter.region.model.res;

import java.util.List;

public class ResRegions {

    private List<RegionInfo> regions;

    public ResRegions() {
    }

    public ResRegions(List<RegionInfo> regions) {
        this.regions = regions;
    }

    public List<RegionInfo> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionInfo> regions) {
        this.regions = regions;
    }
}
