package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Medium {
    @JsonProperty("width")
    private Integer width;
    @JsonProperty("height")
    private Integer height;
    @JsonProperty("file")
    private String file;
    @JsonProperty("sizes")
    private Sizes sizes;
    @JsonProperty("image_meta")
    private ImageMeta imageMeta;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    public ImageMeta getImageMeta() {
        return imageMeta;
    }

    public void setImageMeta(ImageMeta imageMeta) {
        this.imageMeta = imageMeta;
    }

    @Override
    public String toString() {
        return "Medium{" +
                "width=" + width +
                ", height=" + height +
                ", file='" + file + '\'' +
                ", sizes=" + sizes +
                ", imageMeta=" + imageMeta +
                '}';
    }
}
