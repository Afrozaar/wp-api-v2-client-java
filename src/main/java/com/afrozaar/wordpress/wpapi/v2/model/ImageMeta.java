package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageMeta {

    @JsonProperty("aperture")
    private Integer aperture;
    @JsonProperty("credit")
    private String credit;
    @JsonProperty("camera")
    private String camera;
    @JsonProperty("caption")
    private String caption;
    @JsonProperty("created_timestamp")
    private Integer createdTimestamp;
    @JsonProperty("copyright")
    private String copyright;
    @JsonProperty("focal_length")
    private Integer focalLength;
    @JsonProperty("iso")
    private Integer iso;
    @JsonProperty("shutter_speed")
    private Integer shutterSpeed;
    @JsonProperty("title")
    private String title;
    @JsonProperty("orientation")
    private Integer orientation;

    public Integer getAperture() {
        return aperture;
    }

    public void setAperture(Integer aperture) {
        this.aperture = aperture;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Integer createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Integer getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(Integer focalLength) {
        this.focalLength = focalLength;
    }

    public Integer getIso() {
        return iso;
    }

    public void setIso(Integer iso) {
        this.iso = iso;
    }

    public Integer getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(Integer shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public void setOrientation(Integer orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "ImageMeta{" +
                "aperture=" + aperture +
                ", credit='" + credit + '\'' +
                ", camera='" + camera + '\'' +
                ", caption='" + caption + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                ", copyright='" + copyright + '\'' +
                ", focalLength=" + focalLength +
                ", iso=" + iso +
                ", shutterSpeed=" + shutterSpeed +
                ", title='" + title + '\'' +
                ", orientation=" + orientation +
                '}';
    }

}
