package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "author",
        "collection",
        "https://api.w.org/attachment",
        "https://api.w.org/meta",
        "replies",
        "self",
        "version-history"
})
public class Links {

    @JsonProperty("author")
    private List<Author> author = new ArrayList<Author>();
    @JsonProperty("collection")
    private List<Collection> collection = new ArrayList<Collection>();
    @JsonProperty("https://api.w.org/attachment")
    private List<HttpsApiWOrgAttachment> httpsApiWOrgAttachment = new ArrayList<HttpsApiWOrgAttachment>();
    @JsonProperty("https://api.w.org/meta")
    private List<HttpsApiWOrgMetum> httpsApiWOrgMeta = new ArrayList<HttpsApiWOrgMetum>();
    @JsonProperty("replies")
    private List<Reply> replies = new ArrayList<Reply>();
    @JsonProperty("self")
    private List<Self> self = new ArrayList<Self>();
    @JsonProperty("version-history")
    private List<VersionHistory> versionHistory = new ArrayList<VersionHistory>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The author
     */
    @JsonProperty("author")
    public List<Author> getAuthor() {
        return author;
    }

    /**
     * @param author The author
     */
    @JsonProperty("author")
    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    /**
     * @return The collection
     */
    @JsonProperty("collection")
    public List<Collection> getCollection() {
        return collection;
    }

    /**
     * @param collection The collection
     */
    @JsonProperty("collection")
    public void setCollection(List<Collection> collection) {
        this.collection = collection;
    }

    /**
     * @return The httpsApiWOrgAttachment
     */
    @JsonProperty("https://api.w.org/attachment")
    public List<HttpsApiWOrgAttachment> getHttpsApiWOrgAttachment() {
        return httpsApiWOrgAttachment;
    }

    /**
     * @param httpsApiWOrgAttachment The https://api.w.org/attachment
     */
    @JsonProperty("https://api.w.org/attachment")
    public void setHttpsApiWOrgAttachment(List<HttpsApiWOrgAttachment> httpsApiWOrgAttachment) {
        this.httpsApiWOrgAttachment = httpsApiWOrgAttachment;
    }

    /**
     * @return The httpsApiWOrgMeta
     */
    @JsonProperty("https://api.w.org/meta")
    public List<HttpsApiWOrgMetum> getHttpsApiWOrgMeta() {
        return httpsApiWOrgMeta;
    }

    /**
     * @param httpsApiWOrgMeta The https://api.w.org/meta
     */
    @JsonProperty("https://api.w.org/meta")
    public void setHttpsApiWOrgMeta(List<HttpsApiWOrgMetum> httpsApiWOrgMeta) {
        this.httpsApiWOrgMeta = httpsApiWOrgMeta;
    }

    /**
     * @return The replies
     */
    @JsonProperty("replies")
    public List<Reply> getReplies() {
        return replies;
    }

    /**
     * @param replies The replies
     */
    @JsonProperty("replies")
    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    /**
     * @return The self
     */
    @JsonProperty("self")
    public List<Self> getSelf() {
        return self;
    }

    /**
     * @param self The self
     */
    @JsonProperty("self")
    public void setSelf(List<Self> self) {
        this.self = self;
    }

    /**
     * @return The versionHistory
     */
    @JsonProperty("version-history")
    public List<VersionHistory> getVersionHistory() {
        return versionHistory;
    }

    /**
     * @param versionHistory The version-history
     */
    @JsonProperty("version-history")
    public void setVersionHistory(List<VersionHistory> versionHistory) {
        this.versionHistory = versionHistory;
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
