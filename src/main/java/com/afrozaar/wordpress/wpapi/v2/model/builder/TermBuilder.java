package com.afrozaar.wordpress.wpapi.v2.model.builder;

import com.afrozaar.wordpress.wpapi.v2.model.Term;

/**
 * @author johan
 */
public class TermBuilder {
    private Integer count;
    private Long id;
    private String description;
    private String link;
    private String name;
    private String slug;
    private String taxonomySlug;
    private Long parentId;

    private TermBuilder() {
    }

    public static TermBuilder aTerm() {
        return new TermBuilder();
    }

    public TermBuilder withCount(Integer count) {
        this.count = count;
        return this;
    }

    public TermBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public TermBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TermBuilder withLink(String link) {
        this.link = link;
        return this;
    }

    public TermBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TermBuilder withSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public TermBuilder withTaxonomySlug(String taxonomySlug) {
        this.taxonomySlug = taxonomySlug;
        return this;
    }

    public TermBuilder withParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public TermBuilder but() {
        return aTerm().withCount(count).withId(id).withDescription(description).withLink(link).withName(name).withSlug(slug).withTaxonomySlug(taxonomySlug).withParentId(parentId);
    }

    public Term build() {
        Term term = new Term();
        term.setCount(count);
        term.setId(id);
        term.setDescription(description);
        term.setLink(link);
        term.setName(name);
        term.setSlug(slug);
        term.setTaxonomySlug(taxonomySlug);
        term.setParentId(parentId);
        return term;
    }
}
