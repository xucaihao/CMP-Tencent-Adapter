package com.cmp.tencentadapter.instance.model.res;

import java.util.List;

public class ResInstances {

    private List<InstanceInfo> instances;

    public ResInstances() {
    }

    public ResInstances(List<InstanceInfo> instances) {
        this.instances = instances;
    }

    public List<InstanceInfo> getInstances() {
        return instances;
    }

    public void setInstances(List<InstanceInfo> instances) {
        this.instances = instances;
    }
}
