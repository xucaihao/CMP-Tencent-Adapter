package com.cmp.tencentadapter.instance.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QLoginSettingsBean {

    /**
     * 密钥id列表
     */
    @JsonProperty("keyIds")
    private List<String> KeyIds;

    /**
     * 实例登录密码
     */
    @JsonProperty("Password")
    private String password;

    public List<String> getKeyIds() {
        return KeyIds;
    }

    public void setKeyIds(List<String> keyIds) {
        KeyIds = keyIds;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
