package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Media {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("media_details")
    private MediaDetails mediaDetails;
    @JsonProperty("author")
    private int author;

    public MediaDetails getMediaDetails() {
        return mediaDetails;
    }
    public void setMediaDetails(MediaDetails mediaDetails) {
        this.mediaDetails = mediaDetails;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", mediaDetails=" + mediaDetails.toString() +
                ", author=" + author +
                '}';
    }
}
