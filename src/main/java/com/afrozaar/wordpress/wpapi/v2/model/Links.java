package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "replies",
        "version-history",
        "collection",
        "http://wp-api.org/2.0/meta",
        "attachments",
        "author",
        "post_tag",
        "post_format",
        "category",
        "self"
})
public class Links {

    @JsonProperty("replies")
    private List<Reply> replies = new ArrayList<Reply>();
    @JsonProperty("version-history")
    private List<VersionHistory> versionHistory = new ArrayList<VersionHistory>();
    @JsonProperty("collection")
    private List<Collection> collection = new ArrayList<Collection>();
    @JsonProperty("http://wp-api.org/2.0/meta")
    private List<HttpWpApiOrg20Metum> httpWpApiOrg20Meta = new ArrayList<HttpWpApiOrg20Metum>();
    @JsonProperty("attachments")
    private List<Attachment> attachments = new ArrayList<Attachment>();
    @JsonProperty("author")
    private List<Author> author = new ArrayList<Author>();
    @JsonProperty("post_tag")
    private List<PostTag> postTag = new ArrayList<PostTag>();
    @JsonProperty("post_format")
    private List<PostFormat> postFormat = new ArrayList<PostFormat>();
    @JsonProperty("category")
    private List<Category> category = new ArrayList<Category>();
    @JsonProperty("self")
    private List<Self> self = new ArrayList<Self>();

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
     * @return The httpWpApiOrg20Meta
     */
    @JsonProperty("http://wp-api.org/2.0/meta")
    public List<HttpWpApiOrg20Metum> getHttpWpApiOrg20Meta() {
        return httpWpApiOrg20Meta;
    }

    /**
     * @param httpWpApiOrg20Meta The http://wp-api.org/2.0/meta
     */
    @JsonProperty("http://wp-api.org/2.0/meta")
    public void setHttpWpApiOrg20Meta(List<HttpWpApiOrg20Metum> httpWpApiOrg20Meta) {
        this.httpWpApiOrg20Meta = httpWpApiOrg20Meta;
    }

    /**
     * @return The attachments
     */
    @JsonProperty("attachments")
    public List<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * @param attachments The attachments
     */
    @JsonProperty("attachments")
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

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
     * @return The postTag
     */
    @JsonProperty("post_tag")
    public List<PostTag> getPostTag() {
        return postTag;
    }

    /**
     * @param postTag The post_tag
     */
    @JsonProperty("post_tag")
    public void setPostTag(List<PostTag> postTag) {
        this.postTag = postTag;
    }

    /**
     * @return The postFormat
     */
    @JsonProperty("post_format")
    public List<PostFormat> getPostFormat() {
        return postFormat;
    }

    /**
     * @param postFormat The post_format
     */
    @JsonProperty("post_format")
    public void setPostFormat(List<PostFormat> postFormat) {
        this.postFormat = postFormat;
    }

    /**
     * @return The category
     */
    @JsonProperty("category")
    public List<Category> getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    @JsonProperty("category")
    public void setCategory(List<Category> category) {
        this.category = category;
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

}
