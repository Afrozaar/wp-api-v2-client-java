package com.afrozaar.wordpress.wpapi.v2.model.builder;

import com.afrozaar.wordpress.wpapi.v2.model.*;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author johan
 */
public class PostBuilder {
    private String author;
    private Long id;
    private Title title;
    private com.afrozaar.wordpress.wpapi.v2.model.Links Links;
    private String slug;
    private String link;
    private Content content;
    private Long featuredMedia;
    private String format;
    private Boolean sticky;
    private String commentStatus;
    private Guid guid;
    private Excerpt excerpt;
    private String date;
    private String modifiedGmt;
    private String type;
    private String pingStatus;
    private List<Long> categoryIds;
    private List<Long> tagIds;

    private PostBuilder() {
    }

    public static PostBuilder aPost() {
        return new PostBuilder();
    }

    public PostBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public PostBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PostBuilder withTitle(Title title) {
        this.title = title;
        return this;
    }

    public PostBuilder withLinks(com.afrozaar.wordpress.wpapi.v2.model.Links Links) {
        this.Links = Links;
        return this;
    }

    public PostBuilder withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public PostBuilder withLink(String link) {
        this.link = link;
        return this;
    }

    public PostBuilder withContent(Content content) {
        this.content = content;
        return this;
    }

    public PostBuilder withFeaturedMedia(Long featuredMedia) {
        this.featuredMedia = featuredMedia;
        return this;
    }

    public PostBuilder withFormat(String format) {
        this.format = format;
        return this;
    }

    public PostBuilder withSticky(Boolean sticky) {
        this.sticky = sticky;
        return this;
    }

    public PostBuilder withCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
        return this;
    }

    public PostBuilder withGuid(Guid guid) {
        this.guid = guid;
        return this;
    }

    public PostBuilder withExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
        return this;
    }

    public PostBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    public PostBuilder withModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
        return this;
    }

    public PostBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public PostBuilder withPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
        return this;
    }

    /**
     * Add existing categories when building a post.
     */
    public PostBuilder withCategories(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
        return this;
    }

    /**
     * Add existing tags when building a post.
     */
    public PostBuilder withTags(List<Long> tagIds) {
        this.tagIds = tagIds;
        return this;
    }

    /**
     * Add existing Category term items when building a post.
     */
    public PostBuilder withCategories(Term... terms) {
        return withCategories(Arrays.stream(terms).map(Term::getId).collect(toList()));
    }

    /**
     * Add existing Tag term items when building a post.
     */
    public PostBuilder withTags(Term... tags) {
        return withTags(Arrays.stream(tags).map(Term::getId).collect(toList()));
    }

    public PostBuilder but() {
        return aPost()
                .withAuthor(author)
                .withId(id)
                .withTitle(title)
                .withLinks(Links)
                .withSlug(slug)
                .withLink(link)
                .withContent(content)
                .withFeaturedMedia(featuredMedia)
                .withFormat(format)
                .withSticky(sticky)
                .withCommentStatus(commentStatus)
                .withGuid(guid)
                .withExcerpt(excerpt)
                .withDate(date)
                .withModifiedGmt(modifiedGmt)
                .withType(type)
                .withPingStatus(pingStatus)
                .withCategories(categoryIds)
                .withTags(tagIds);
    }

    public Post build() {
        Post post = new Post();
        post.setAuthor(author);
        post.setId(id);
        post.setTitle(title);
        post.setLinks(Links);
        post.setSlug(slug);
        post.setLink(link);
        post.setContent(content);
        post.setFeaturedMedia(featuredMedia);
        post.setFormat(format);
        post.setSticky(sticky);
        post.setCommentStatus(commentStatus);
        post.setGuid(guid);
        post.setExcerpt(excerpt);
        post.setDate(date);
        post.setModifiedGmt(modifiedGmt);
        post.setType(type);
        post.setPingStatus(pingStatus);
        post.setCategoryIds(categoryIds);
        post.setTagIds(tagIds);
        return post;
    }


}
