package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.model.Taxonomy;

import java.util.List;

public interface Taxonomies {

    String tag = "tag";
    String category = "category";

    List<Taxonomy> getTaxonomies();

    Taxonomy getTaxonomy(String slug);
}
