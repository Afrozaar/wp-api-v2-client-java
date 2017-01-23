package com.afrozaar.wordpress.wpapi.v2.model;

import com.google.common.collect.ImmutableMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

@JsonIgnoreProperties(value = { "_links" })
public class Term {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("description")
    private String description;
    @JsonProperty("link")
    private String link;
    @JsonProperty("name")
    private String name;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("taxonomy")
    private String taxonomySlug;
    @JsonProperty("parent")
    private Long parentId;
    @JsonProperty("meta")
    private List<Long> meta;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTaxonomySlug() {
        return taxonomySlug;
    }

    public void setTaxonomySlug(String taxonomySlug) {
        this.taxonomySlug = taxonomySlug;
    }

    //TODO: add support for creating term with meta ids.
    public List<Long> getMeta() {
        return meta;
    }

    public void setMeta(List<Long> meta) {
        this.meta = meta;
    }

    public Map<String, Object> asMap() {
        final ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<>();
        BiConsumer<String, Object> c = (index, value) -> Optional.ofNullable(value).ifPresent(val -> builder.put(index, val));
        c.accept("description", description);
        c.accept("name", name);
        c.accept("slug", slug);
        c.accept("parent", parentId);
        return builder.build();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Term{");
        sb.append("count=").append(count);
        sb.append(", id=").append(id);
        sb.append(", description='").append(description).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", slug='").append(slug).append('\'');
        sb.append(", taxonomySlug='").append(taxonomySlug).append('\'');
        sb.append(", parentId=").append(parentId);
        sb.append('}');
        return sb.toString();
    }
}
