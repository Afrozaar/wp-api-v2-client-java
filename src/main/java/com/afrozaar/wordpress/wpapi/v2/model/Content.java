package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "raw",
        "rendered"
})
public class Content {

    @JsonProperty("raw")
    private String raw;
    @JsonProperty("rendered")
    private String rendered;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The raw
     */
    @JsonProperty("raw")
    public String getRaw() {
        return raw;
    }

    /**
     * @param raw The raw
     */
    @JsonProperty("raw")
    public void setRaw(String raw) {
        this.raw = raw;
    }

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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
