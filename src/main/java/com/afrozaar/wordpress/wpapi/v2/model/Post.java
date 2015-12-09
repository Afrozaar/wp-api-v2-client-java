
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
    "author",
    "content",
    "status",
    "_links",
    "modified",
    "guid",
    "featured_image",
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
    "date_gmt"
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
    @JsonProperty("featured_image")
    private Long featuredImage;
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

    /**
     * 
     * @return
     *     The author
     */
    @JsonProperty("author")
    public Long getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    @JsonProperty("author")
    public void setAuthor(Long author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The content
     */
    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The Links
     */
    @JsonProperty("_links")
    public com.afrozaar.wordpress.wpapi.v2.model.Links getLinks() {
        return Links;
    }

    /**
     * 
     * @param Links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(com.afrozaar.wordpress.wpapi.v2.model.Links Links) {
        this.Links = Links;
    }

    /**
     * 
     * @return
     *     The modified
     */
    @JsonProperty("modified")
    public String getModified() {
        return modified;
    }

    /**
     * 
     * @param modified
     *     The modified
     */
    @JsonProperty("modified")
    public void setModified(String modified) {
        this.modified = modified;
    }

    /**
     * 
     * @return
     *     The guid
     */
    @JsonProperty("guid")
    public Guid getGuid() {
        return guid;
    }

    /**
     * 
     * @param guid
     *     The guid
     */
    @JsonProperty("guid")
    public void setGuid(Guid guid) {
        this.guid = guid;
    }

    /**
     * 
     * @return
     *     The featuredImage
     */
    @JsonProperty("featured_image")
    public Long getFeaturedImage() {
        return featuredImage;
    }

    /**
     * 
     * @param featuredImage
     *     The featured_image
     */
    @JsonProperty("featured_image")
    public void setFeaturedImage(Long featuredImage) {
        this.featuredImage = featuredImage;
    }

    /**
     * 
     * @return
     *     The sticky
     */
    @JsonProperty("sticky")
    public Boolean getSticky() {
        return sticky;
    }

    /**
     * 
     * @param sticky
     *     The sticky
     */
    @JsonProperty("sticky")
    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    /**
     * 
     * @return
     *     The password
     */
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     *     The password
     */
    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     *     The format
     */
    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    /**
     * 
     * @param format
     *     The format
     */
    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 
     * @return
     *     The link
     */
    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The pingStatus
     */
    @JsonProperty("ping_status")
    public String getPingStatus() {
        return pingStatus;
    }

    /**
     * 
     * @param pingStatus
     *     The ping_status
     */
    @JsonProperty("ping_status")
    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    /**
     * 
     * @return
     *     The excerpt
     */
    @JsonProperty("excerpt")
    public Excerpt getExcerpt() {
        return excerpt;
    }

    /**
     * 
     * @param excerpt
     *     The excerpt
     */
    @JsonProperty("excerpt")
    public void setExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
    }

    /**
     * 
     * @return
     *     The modifiedGmt
     */
    @JsonProperty("modified_gmt")
    public String getModifiedGmt() {
        return modifiedGmt;
    }

    /**
     * 
     * @param modifiedGmt
     *     The modified_gmt
     */
    @JsonProperty("modified_gmt")
    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public Title getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The commentStatus
     */
    @JsonProperty("comment_status")
    public String getCommentStatus() {
        return commentStatus;
    }

    /**
     * 
     * @param commentStatus
     *     The comment_status
     */
    @JsonProperty("comment_status")
    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The slug
     */
    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    /**
     * 
     * @param slug
     *     The slug
     */
    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * 
     * @return
     *     The date
     */
    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The dateGmt
     */
    @JsonProperty("date_gmt")
    public String getDateGmt() {
        return dateGmt;
    }

    /**
     * 
     * @param dateGmt
     *     The date_gmt
     */
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

}
