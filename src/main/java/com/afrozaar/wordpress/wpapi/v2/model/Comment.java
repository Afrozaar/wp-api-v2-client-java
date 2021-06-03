package com.afrozaar.wordpress.wpapi.v2.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "post",
    "parent",
    "author",
    "author_name",
    "author_url",
    "date",
    "date_gmt",
    "content",
    "link",
    "status",
    "type",
    "author_avatar_urls",
    "meta",
    "_links"
})
public class Comment {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("post")
    private Integer post;
    @JsonProperty("parent")
    private Integer parent;
    @JsonProperty("author")
    private Integer author;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("author_email")
    private String authorEmail;
    @JsonProperty("author_url")
    private String authorUrl;
    @JsonProperty("date")
    private String date;
    @JsonProperty("date_gmt")
    private String dateGmt;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("link")
    private String link;
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;
    @JsonProperty("author_avatar_urls")
    private AvatarUrls avatarUrls;
    @JsonProperty("meta")
    private List<Object> meta = new ArrayList<>();
    @JsonProperty("_links")
    private Links links;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("post")
    public Integer getPost() {
        return post;
    }

    @JsonProperty("post")
    public void setPost(Integer post) {
        this.post = post;
    }

    @JsonProperty("parent")
    public Integer getParent() {
        return parent;
    }

    @JsonProperty("parent")
    public void setParent(Integer parent) {
        this.parent = parent;
    }

    @JsonProperty("author")
    public Integer getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(Integer author) {
        this.author = author;
    }

    @JsonProperty("author_name")
    public String getAuthorName() {
        return authorName;
    }

    @JsonProperty("author_name")
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @JsonProperty("author_email")
    public String getAuthorEmail() {
        return authorEmail;
    }

    @JsonProperty("author_email")
    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    @JsonProperty("author_url")
    public String getAuthorUrl() {
        return authorUrl;
    }

    @JsonProperty("author_url")
    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
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

    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("author_avatar_urls")
    public AvatarUrls getAvatarUrls() {
        return avatarUrls;
    }

    @JsonProperty("author_avatar_urls")
    public void setAvatarUrls(AvatarUrls avatarUrls) {
        this.avatarUrls = avatarUrls;
    }

    @JsonProperty("meta")
    public List<Object> getMeta() {
        return meta;
    }

    @JsonProperty("meta")
    public void setMeta(List<Object> meta) {
        this.meta = meta;
    }

    @JsonProperty("_links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("_links")
    public void setLinks(Links links) {
        this.links = links;
    }

}
