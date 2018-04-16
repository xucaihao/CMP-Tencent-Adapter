package com.cmp.tencentadapter.image.model;

import java.util.List;

public class ResImages {

    private List<ImageInfo> images;

    public ResImages() {
    }

    public ResImages(List<ImageInfo> images) {
        this.images = images;
    }

    public List<ImageInfo> getImages() {
        return images;
    }

    public void setImages(List<ImageInfo> images) {
        this.images = images;
    }
}
