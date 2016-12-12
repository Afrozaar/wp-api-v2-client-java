package com.afrozaar.wordpress.wpapi.v2.model.builder;

import com.afrozaar.wordpress.wpapi.v2.model.Content;
import com.afrozaar.wordpress.wpapi.v2.model.Excerpt;
import com.afrozaar.wordpress.wpapi.v2.model.Guid;
import com.afrozaar.wordpress.wpapi.v2.model.Page;
import com.afrozaar.wordpress.wpapi.v2.model.Title;

public class PageBuilder {
    private Long author;
    private com.afrozaar.wordpress.wpapi.v2.model.Links Links;
    private String commentStatus;
    private Content content;
    private String date;
    private String dateGmt;
    private Excerpt excerpt;
    private Long featuredMedia;
    private Guid guid;
    private Long id;
    private String link;
    private Long menuOrder;
    private String modified;
    private String modifiedGmt;
    private Long parent;
    private String password;
    private String pingStatus;
    private String slug;
    private String status;
    private String template;
    private Title title;
    private String type;

    private PageBuilder() {
    }

    public static PageBuilder aPage() {
        return new PageBuilder();
    }

    public PageBuilder withAuthor(Long author) {
        this.author = author;
        return this;
    }

    public PageBuilder withLinks(com.afrozaar.wordpress.wpapi.v2.model.Links Links) {
        this.Links = Links;
        return this;
    }

    public PageBuilder withCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
        return this;
    }

    public PageBuilder withContent(Content content) {
        this.content = content;
        return this;
    }

    public PageBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    public PageBuilder withDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
        return this;
    }

    public PageBuilder withExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
        return this;
    }

    public PageBuilder withFeaturedMedia(Long featuredMedia) {
        this.featuredMedia = featuredMedia;
        return this;
    }

    public PageBuilder withGuid(Guid guid) {
        this.guid = guid;
        return this;
    }

    public PageBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PageBuilder withLink(String link) {
        this.link = link;
        return this;
    }

    public PageBuilder withMenuOrder(Long menuOrder) {
        this.menuOrder = menuOrder;
        return this;
    }

    public PageBuilder withModified(String modified) {
        this.modified = modified;
        return this;
    }

    public PageBuilder withModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
        return this;
    }

    public PageBuilder withParent(Long parent) {
        this.parent = parent;
        return this;
    }

    public PageBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public PageBuilder withPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
        return this;
    }

    public PageBuilder withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public PageBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public PageBuilder withTemplate(String template) {
        this.template = template;
        return this;
    }

    public PageBuilder withTitle(Title title) {
        this.title = title;
        return this;
    }

    public PageBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public PageBuilder but() {
        return aPage()
                .withAuthor(author)
                .withLinks(Links)
                .withCommentStatus(commentStatus)
                .withContent(content)
                .withDate(date)
                .withDateGmt(dateGmt)
                .withExcerpt(excerpt)
                .withFeaturedMedia(featuredMedia)
                .withGuid(guid)
                .withId(id)
                .withLink(link)
                .withMenuOrder(menuOrder)
                .withModified(modified)
                .withModifiedGmt(modifiedGmt)
                .withParent(parent)
                .withPassword(password)
                .withPingStatus(pingStatus)
                .withSlug(slug)
                .withStatus(status)
                .withTemplate(template)
                .withTitle(title)
                .withType(type);
    }

    public Page build() {
        Page page = new Page();
        page.setAuthor(author);
        page.setLinks(Links);
        page.setCommentStatus(commentStatus);
        page.setContent(content);
        page.setDate(date);
        page.setDateGmt(dateGmt);
        page.setExcerpt(excerpt);
        page.setFeaturedMedia(featuredMedia);
        page.setGuid(guid);
        page.setId(id);
        page.setLink(link);
        page.setMenuOrder(menuOrder);
        page.setModified(modified);
        page.setModifiedGmt(modifiedGmt);
        page.setParent(parent);
        page.setPassword(password);
        page.setPingStatus(pingStatus);
        page.setSlug(slug);
        page.setStatus(status);
        page.setTemplate(template);
        page.setTitle(title);
        page.setType(type);
        return page;
    }
}
