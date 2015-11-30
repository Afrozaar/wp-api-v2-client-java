package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "id",
        "title",
        "_links",
        "slug",
        "link",
        "content",
        "featured_image",
        "format",
        "sticky",
        "comment_status",
        "guid",
        "author",
        "excerpt",
        "date",
        "modified_gmt",
        "type",
        "ping_status"
})
public class Post {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private Title title;
    @JsonProperty("_links")
    private com.afrozaar.wordpress.wpapi.v2.model.Links Links;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("link")
    private String link;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("featured_image")
    private Integer featuredImage;
    @JsonProperty("format")
    private String format;
    @JsonProperty("sticky")
    private Boolean sticky;
    @JsonProperty("comment_status")
    private String commentStatus;
    @JsonProperty("guid")
    private Guid guid;
    @JsonProperty("author")
    private Integer author;
    @JsonProperty("excerpt")
    private Excerpt excerpt;
    @JsonProperty("date")
    private String date;
    @JsonProperty("modified_gmt")
    private String modifiedGmt;
    @JsonProperty("type")
    private String type;
    @JsonProperty("ping_status")
    private String pingStatus;

    /**
     * @return The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    @JsonProperty("title")
    public Title getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    @JsonProperty("title")
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * @return The Links
     */
    @JsonProperty("_links")
    public com.afrozaar.wordpress.wpapi.v2.model.Links getLinks() {
        return Links;
    }

    /**
     * @param Links The _links
     */
    @JsonProperty("_links")
    public void setLinks(com.afrozaar.wordpress.wpapi.v2.model.Links Links) {
        this.Links = Links;
    }

    /**
     * @return The slug
     */
    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    /**
     * @param slug The slug
     */
    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * @return The link
     */
    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return The content
     */
    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    /**
     * @return The featuredImage
     */
    @JsonProperty("featured_image")
    public Integer getFeaturedImage() {
        return featuredImage;
    }

    /**
     * @param featuredImage The featured_image
     */
    @JsonProperty("featured_image")
    public void setFeaturedImage(Integer featuredImage) {
        this.featuredImage = featuredImage;
    }

    /**
     * @return The format
     */
    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    /**
     * @param format The format
     */
    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return The sticky
     */
    @JsonProperty("sticky")
    public Boolean getSticky() {
        return sticky;
    }

    /**
     * @param sticky The sticky
     */
    @JsonProperty("sticky")
    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    /**
     * @return The commentStatus
     */
    @JsonProperty("comment_status")
    public String getCommentStatus() {
        return commentStatus;
    }

    /**
     * @param commentStatus The comment_status
     */
    @JsonProperty("comment_status")
    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    /**
     * @return The guid
     */
    @JsonProperty("guid")
    public Guid getGuid() {
        return guid;
    }

    /**
     * @param guid The guid
     */
    @JsonProperty("guid")
    public void setGuid(Guid guid) {
        this.guid = guid;
    }

    /**
     * @return The author
     */
    @JsonProperty("author")
    public Integer getAuthor() {
        return author;
    }

    /**
     * @param author The author
     */
    @JsonProperty("author")
    public void setAuthor(Integer author) {
        this.author = author;
    }

    /**
     * @return The excerpt
     */
    @JsonProperty("excerpt")
    public Excerpt getExcerpt() {
        return excerpt;
    }

    /**
     * @param excerpt The excerpt
     */
    @JsonProperty("excerpt")
    public void setExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
    }

    /**
     * @return The date
     */
    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The modifiedGmt
     */
    @JsonProperty("modified_gmt")
    public String getModifiedGmt() {
        return modifiedGmt;
    }

    /**
     * @param modifiedGmt The modified_gmt
     */
    @JsonProperty("modified_gmt")
    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    /**
     * @return The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The pingStatus
     */
    @JsonProperty("ping_status")
    public String getPingStatus() {
        return pingStatus;
    }

    /**
     * @param pingStatus The ping_status
     */
    @JsonProperty("ping_status")
    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Post{");
        sb.append("author=").append(author);
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", Links=").append(Links);
        sb.append(", slug='").append(slug).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append(", content=").append(content);
        sb.append(", featuredImage=").append(featuredImage);
        sb.append(", format='").append(format).append('\'');
        sb.append(", sticky=").append(sticky);
        sb.append(", commentStatus='").append(commentStatus).append('\'');
        sb.append(", guid=").append(guid);
        sb.append(", excerpt=").append(excerpt);
        sb.append(", date='").append(date).append('\'');
        sb.append(", modifiedGmt='").append(modifiedGmt).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", pingStatus='").append(pingStatus).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
