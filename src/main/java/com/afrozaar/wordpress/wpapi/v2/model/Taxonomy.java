package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Taxonomy {

/*    {
    "description": "",
    "hierarchical": false,
    "labels": {
        "add_new_item": "Add New Tag",
        "add_or_remove_items": "Add or remove tags",
        "all_items": "All Tags",
        "choose_from_most_used": "Choose from the most used tags",
        "edit_item": "Edit Tag",
        "menu_name": "Tags",
        "name": "Tags",
        "name_admin_bar": "post_tag",
        "new_item_name": "New Tag Name",
        "no_terms": "No tags",
        "not_found": "No tags found.",
        "parent_item": null,
        "parent_item_colon": null,
        "popular_items": "Popular Tags",
        "search_items": "Search Tags",
        "separate_items_with_commas": "Separate tags with commas",
        "singular_name": "Tag",
        "update_item": "Update Tag",
        "view_item": "View Tag"
    },
    "name": "Tags",
    "show_cloud": true,
    "slug": "post_tag",
    "types": [
        "post"
    ]
}*/

    @JsonProperty("slug")
    private String slug;
    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("types")
    private List<String> types;
    @JsonProperty("hierarchical")
    private Boolean hierarchical;

    @JsonProperty("labels")
    private Map<String, String> labels;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHierarchical() {
        return hierarchical;
    }

    public void setHierarchical(Boolean hierarchical) {
        this.hierarchical = hierarchical;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Taxonomy{");
        sb.append("description='").append(description).append('\'');
        sb.append(", slug='").append(slug).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", types=").append(types);
        sb.append(", hierarchical=").append(hierarchical);
        sb.append(", labels=").append(labels);
        sb.append('}');
        return sb.toString();
    }
}
