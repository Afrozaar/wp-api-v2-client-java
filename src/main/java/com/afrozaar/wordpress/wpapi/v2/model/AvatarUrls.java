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
        "24",
        "48",
        "96"
})
public class AvatarUrls {

    @JsonProperty("24")
    private String _24;
    @JsonProperty("48")
    private String _48;
    @JsonProperty("96")
    private String _96;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("24")
    public String get24() {
        return _24;
    }

    @JsonProperty("24")
    public void set24(String _24) {
        this._24 = _24;
    }

    @JsonProperty("48")
    public String get48() {
        return _48;
    }

    @JsonProperty("48")
    public void set48(String _48) {
        this._48 = _48;
    }

    @JsonProperty("96")
    public String get96() {
        return _96;
    }

    @JsonProperty("96")
    public void set96(String _96) {
        this._96 = _96;
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
