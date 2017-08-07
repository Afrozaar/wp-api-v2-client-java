package com.afrozaar.wordpress.wpapi.v2.model;

import com.google.common.collect.ImmutableMap;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "_links",
        "author",
        "comment_status",
        "content",
        "date",
        "date_gmt",
        "excerpt",
        "featured_media",
        "guid",
        "id",
        "link",
        "menu_order",
        "modified",
        "modified_gmt",
        "parent",
        "password",
        "ping_status",
        "slug",
        "status",
        "template",
        "title",
        "type"
})
public class Page {

    @JsonProperty("_links")
    private com.afrozaar.wordpress.wpapi.v2.model.Links Links;
    @JsonProperty("author")
    private Long author;
    @JsonProperty("comment_status")
    private String commentStatus;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("date")
    private String date;
    @JsonProperty("date_gmt")
    private String dateGmt;
    @JsonProperty("excerpt")
    private Excerpt excerpt;
    @JsonProperty("featured_media")
    private Long featuredMedia;
    @JsonProperty("guid")
    private Guid guid;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("link")
    private String link;
    @JsonProperty("menu_order")
    private Long menuOrder;
    @JsonProperty("modified")
    private String modified;
    @JsonProperty("modified_gmt")
    private String modifiedGmt;
    @JsonProperty("parent")
    private Long parent;
    @JsonProperty("password")
    private String password;
    @JsonProperty("ping_status")
    private String pingStatus;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("status")
    private String status;
    @JsonProperty("template")
    private String template;
    @JsonProperty("title")
    private Title title;
    @JsonProperty("type")
    private String type;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("_links")
    public com.afrozaar.wordpress.wpapi.v2.model.Links getLinks() {
        return Links;
    }

    @JsonProperty("_links")
    public void setLinks(com.afrozaar.wordpress.wpapi.v2.model.Links Links) {
        this.Links = Links;
    }

    @JsonProperty("author")
    public Long getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(Long author) {
        this.author = author;
    }

    @JsonProperty("comment_status")
    public String getCommentStatus() {
        return commentStatus;
    }

    @JsonProperty("comment_status")
    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
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

    @JsonProperty("excerpt")
    public Excerpt getExcerpt() {
        return excerpt;
    }

    @JsonProperty("excerpt")
    public void setExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
    }

    @JsonProperty("featured_media")
    public Long getFeaturedMedia() {
        return featuredMedia;
    }

    @JsonProperty("featured_media")
    public void setFeaturedMedia(Long featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    @JsonProperty("guid")
    public Guid getGuid() {
        return guid;
    }

    @JsonProperty("guid")
    public void setGuid(Guid guid) {
        this.guid = guid;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("menu_order")
    public Long getMenuOrder() {
        return menuOrder;
    }

    @JsonProperty("menu_order")
    public void setMenuOrder(Long menuOrder) {
        this.menuOrder = menuOrder;
    }

    @JsonProperty("modified")
    public String getModified() {
        return modified;
    }

    @JsonProperty("modified")
    public void setModified(String modified) {
        this.modified = modified;
    }

    @JsonProperty("modified_gmt")
    public String getModifiedGmt() {
        return modifiedGmt;
    }

    @JsonProperty("modified_gmt")
    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    @JsonProperty("parent")
    public Long getParent() {
        return parent;
    }

    @JsonProperty("parent")
    public void setParent(Long parent) {
        this.parent = parent;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("ping_status")
    public String getPingStatus() {
        return pingStatus;
    }

    @JsonProperty("ping_status")
    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("template")
    public String getTemplate() {
        return template;
    }

    @JsonProperty("template")
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty("title")
    public Title getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(Title title) {
        this.title = title;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Map<String, Object> asMap() {
        final ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<>();
        BiConsumer<String, Object> c = (index, value) -> Optional.ofNullable(value).ifPresent(val -> builder.put(index, val));

        c.accept("author", author);
        c.accept("comment_status", commentStatus);
        c.accept("content", Objects.nonNull(content) ? content.getRaw() : null);
        c.accept("date", date);
        c.accept("date_gmt", dateGmt);
        c.accept("excerpt", Objects.nonNull(excerpt) ? excerpt.getRaw() : null);
        c.accept("featured_media", featuredMedia);
        c.accept("guid", Objects.nonNull(guid) ? guid.getRaw() : null);
        c.accept("id", id);
        c.accept("link", link);
        c.accept("menu_order", menuOrder);
        c.accept("modified", modified);
        c.accept("modified_gmt", modifiedGmt);
        c.accept("parent", parent);
        c.accept("password", password);
        c.accept("ping_status", pingStatus);
        c.accept("slug", slug);
        c.accept("status", status);
        c.accept("template", template);
        c.accept("title", Objects.nonNull(title) ? title.getRaw() : null);
        c.accept("type", type);

        return builder.build();
    }
}
