package com.cmp.tencentadapter.instance.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 实例的抽象位置，包括其所在的可用区，所属的项目，宿主机等（仅CDH产品可用）
 */
public class QPlacementBean {

    /**
     * Zone : ap-chengdu-1
     * ProjectId : 0
     * HostId : null
     */
    @JsonProperty("Zone")
    private String zone;

    @JsonProperty("ProjectId")
    private int projectId;

    @JsonProperty("HostIds")
    private List<String> hostIds;

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public List<String> getHostIds() {
        return hostIds;
    }

    public void setHostIds(List<String> hostIds) {
        this.hostIds = hostIds;
    }
}
