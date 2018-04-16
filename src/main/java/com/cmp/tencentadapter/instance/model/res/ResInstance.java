package com.cmp.tencentadapter.instance.model.res;

public class ResInstance {

    private InstanceInfo instance;

    public ResInstance() {
    }

    public ResInstance(InstanceInfo instance) {
        this.instance = instance;
    }

    public InstanceInfo getInstance() {
        return instance;
    }

    public void setInstance(InstanceInfo instance) {
        this.instance = instance;
    }
}
