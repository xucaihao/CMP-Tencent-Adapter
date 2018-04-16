package com.cmp.tencentadapter.image.service;

import com.cmp.tencentadapter.common.CloudEntity;
import com.cmp.tencentadapter.image.model.ResImages;

public interface ImageService {

    /**
     * 查询镜像列表
     *
     * @param cloud 云（用户提供ak、sk）
     * @return 镜像列表
     */
    ResImages describeImages(CloudEntity cloud);
}
