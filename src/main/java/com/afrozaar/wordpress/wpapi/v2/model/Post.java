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
        "content",
        "status",
        "_links",
        "modified",
        "guid",
        "featured_media",
        "sticky",
        "password",
        "format",
        "link",
        "ping_status",
        "excerpt",
        "modified_gmt",
        "id",
        "title",
        "comment_status",
        "type",
        "slug",
        "date",
        "date_gmt",
        "categories",
        "tags"
})
public class Post {

    @JsonProperty("author")
    private Long author;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("status")
    private String status;
    @JsonProperty("_links")
    private com.afrozaar.wordpress.wpapi.v2.model.Links Links;
    @JsonProperty("modified")
    private String modified;
    @JsonProperty("guid")
    private Guid guid;
    @JsonProperty("featured_media")
    private Long featuredMedia;
    @JsonProperty("sticky")
    private Boolean sticky;
    @JsonProperty("password")
    private String password;
    @JsonProperty("format")
    private String format;
    @JsonProperty("link")
    private String link;
    @JsonProperty("ping_status")
    private String pingStatus;
    @JsonProperty("excerpt")
    private Excerpt excerpt;
    @JsonProperty("modified_gmt")
    private String modifiedGmt;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private Title title;
    @JsonProperty("comment_status")
    private String commentStatus;
    @JsonProperty("type")
    private String type;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("date")
    private String date;
    @JsonProperty("date_gmt")
    private String dateGmt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    @JsonProperty("categories")
    private List<Long> categoryIds = new ArrayList<>();

    @JsonProperty("tags")
    private List<Long> tagIds = new ArrayList<>();

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    @JsonProperty("author")
    public Long getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(Long author) {
        this.author = author;
    }

    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("_links")
    public com.afrozaar.wordpress.wpapi.v2.model.Links getLinks() {
        return Links;
    }

    @JsonProperty("_links")
    public void setLinks(com.afrozaar.wordpress.wpapi.v2.model.Links Links) {
        this.Links = Links;
    }

    @JsonProperty("modified")
    public String getModified() {
        return modified;
    }

    @JsonProperty("modified")
    public void setModified(String modified) {
        this.modified = modified;
    }

    @JsonProperty("guid")
    public Guid getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(Guid guid) {
        this.guid = guid;
    }

    @JsonProperty("featured_media")
    public Long getFeaturedMedia() {
        return featuredMedia;
    }

    @JsonProperty("featured_media")
    public void setFeaturedMedia(Long featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    @JsonProperty("sticky")
    public Boolean getSticky() {
        return sticky;
    }

    @JsonProperty("sticky")
    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("ping_status")
    public String getPingStatus() {
        return pingStatus;
    }

    @JsonProperty("ping_status")
    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    @JsonProperty("excerpt")
    public Excerpt getExcerpt() {
        return excerpt;
    }

    @JsonProperty("excerpt")
    public void setExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
    }

    @JsonProperty("modified_gmt")
    public String getModifiedGmt() {
        return modifiedGmt;
    }

    @JsonProperty("modified_gmt")
    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("title")
    public Title getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(Title title) {
        this.title = title;
    }

    @JsonProperty("comment_status")
    public String getCommentStatus() {
        return commentStatus;
    }

    @JsonProperty("comment_status")
    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("date_gmt")
    public String getDateGmt() {
        return dateGmt;
    }

    @JsonProperty("date_gmt")
    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonProperty("tags")
    public List<Long> getTagIds() {
        return tagIds;
    }

    @JsonProperty("tags")
    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }
}
