package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Sizes {
    @JsonProperty("thumbnail")
    private Thumbnail thumbnail;
    @JsonProperty("medium")
    private Medium medium;

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    @Override
    public String toString() {
        return "Sizes{" + "thumbnail=" + (thumbnail != null ? thumbnail.toString() : null) + ", medium=" + (medium != null ? medium.toString() : null) + '}';
    }
}
