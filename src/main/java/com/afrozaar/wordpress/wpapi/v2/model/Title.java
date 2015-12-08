package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "rendered"
})
public class Title {

    @JsonProperty("rendered")
    private String rendered;

    /**
     * @return The rendered
     */
    @JsonProperty("rendered")
    public String getRendered() {
        return rendered;
    }

    /**
     * @param rendered The rendered
     */
    @JsonProperty("rendered")
    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    @Override
    public String toString() {
        return "Title{" +
                "rendered='" + rendered + '\'' +
                '}';
    }
}
