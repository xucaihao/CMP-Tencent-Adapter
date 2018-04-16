package com.cmp.tencentadapter.image.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QImage {

    /**
     * CreatedTime : null
     * Platform : CentOS
     * Architecture : x86_64
     * ImageSize : 50
     * OsName : CentOS 7.4 64λ
     * isSupportCloudinit : true
     * ImageType : PUBLIC_IMAGE
     * ImageName : CentOS 7.4 64λ
     * ImageCreator : null
     * ImageId : img-8toqc6s3
     * ImageSource : OFFICIAL
     * ImageDescription : CentOS 7.4 64λ
     * ImageState : NORMAL
     */

    @JsonProperty("CreatedTime")
    private String createdTime;
    @JsonProperty("Platform")
    private String platform;
    @JsonProperty("Architecture")
    private String architecture;
    @JsonProperty("ImageSize")
    private int imageSize;
    @JsonProperty("OsName")
    private String osName;
    @JsonProperty("isSupportCloudinit")
    private boolean isSupportCloudInit;
    @JsonProperty("ImageType")
    private String imageType;
    @JsonProperty("ImageName")
    private String imageName;
    @JsonProperty("ImageCreator")
    private String imageCreator;
    @JsonProperty("ImageId")
    private String imageId;
    @JsonProperty("ImageSource")
    private String imageSource;
    @JsonProperty("ImageDescription")
    private String imageDescription;
    @JsonProperty("ImageState")
    private String imageState;

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public boolean isSupportCloudInit() {
        return isSupportCloudInit;
    }

    public void setSupportCloudInit(boolean supportCloudInit) {
        isSupportCloudInit = supportCloudInit;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageCreator() {
        return imageCreator;
    }

    public void setImageCreator(String imageCreator) {
        this.imageCreator = imageCreator;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getImageState() {
        return imageState;
    }

    public void setImageState(String imageState) {
        this.imageState = imageState;
    }
}
