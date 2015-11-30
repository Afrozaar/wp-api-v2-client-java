package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "embeddable",
        "href"
})
public class PostTag {

    @JsonProperty("embeddable")
    private Boolean embeddable;
    @JsonProperty("href")
    private String href;

    /**
     * @return The embeddable
     */
    @JsonProperty("embeddable")
    public Boolean getEmbeddable() {
        return embeddable;
    }

    /**
     * @param embeddable The embeddable
     */
    @JsonProperty("embeddable")
    public void setEmbeddable(Boolean embeddable) {
        this.embeddable = embeddable;
    }

    /**
     * @return The href
     */
    @JsonProperty("href")
    public String getHref() {
        return href;
    }

    /**
     * @param href The href
     */
    @JsonProperty("href")
    public void setHref(String href) {
        this.href = href;
    }

}
