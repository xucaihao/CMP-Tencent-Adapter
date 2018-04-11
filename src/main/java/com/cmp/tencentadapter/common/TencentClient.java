package com.cmp.tencentadapter.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.qcloud.Module.Base;
import com.qcloud.QcloudApiModuleCenter;

import java.util.Optional;
import java.util.TreeMap;

import static com.cmp.tencentadapter.common.Constance.*;
import static com.cmp.tencentadapter.common.ErrorEnum.ERR_AUTH_INFO;

public class TencentClient {

    //true(使用腾讯云数据) false(使用模拟数据)
    private static final boolean status = true;

    public static boolean getStatus() {
        return status;
    }

    /**
     * 配置公共参数
     *
     * @param cloud         云（提供ak,sk）
     * @param requestMethod 请求方法
     * @param regionId      地域id
     * @return 公共参数
     */
    public static TreeMap<String, Object> initConfig(CloudEntity cloud, String requestMethod, String regionId) {
        // 初始化
        JsonNode authInfo = Optional.ofNullable(
                JsonUtil.stringToObject(cloud.getAuthInfo(), JsonNode.class))
                .orElseThrow(() -> new TencentException(ERR_AUTH_INFO));
        //"AKIDXu3yf8wL5bsuhGMfV5RYQ4TeaGhtsY1F"
        String accessKey = authInfo.path(ACCESS_KEY).asText();
        //"5wYv3xc2D9qIVTChwwjHaeT3PWP3Scki"
        String secret = authInfo.path(SECRET).asText();
        TreeMap<String, Object> config = new TreeMap<>();
        config.put("SecretId", accessKey);
        config.put("SecretKey", secret);
        config.put("RequestMethod", requestMethod);
        config.put("DefaultRegion", regionId);
        return config;
    }

    /**
     * 调用腾讯云接口
     *
     * @param config     公共参数
     * @param base       使用接口所在的模块
     * @param actionName 接口名
     * @param params     输入参数
     * @return 结果
     * @throws Exception Exception
     */
    public static String call(
            TreeMap<String, Object> config, Base base, String actionName, TreeMap<String, Object> params) throws Exception {
        // 初始化
        QcloudApiModuleCenter module = new QcloudApiModuleCenter(base, config);
        params.put("Version", VERSION);
        return module.call(actionName, params);
    }
}
