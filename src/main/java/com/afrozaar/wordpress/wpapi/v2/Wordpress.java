package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.api.Medias;
import com.afrozaar.wordpress.wpapi.v2.api.PostMetas;
import com.afrozaar.wordpress.wpapi.v2.api.PostTerms;
import com.afrozaar.wordpress.wpapi.v2.api.Posts;
import com.afrozaar.wordpress.wpapi.v2.api.Taxonomies;
import com.afrozaar.wordpress.wpapi.v2.api.Terms;
import com.afrozaar.wordpress.wpapi.v2.model.Media;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;

import java.net.URI;
import java.util.function.Function;

public interface Wordpress extends Posts, PostMetas, PostTerms, Taxonomies, Terms, Medias {
    String CONTEXT = "/wp-json/wp/v2";

    <T> PagedResponse<T> getPagedResponse(String context, Class<T> typeRef, String... expandParams);
    <T> PagedResponse<T> getPagedResponse(URI uri, Class<T> typeRef);
    <T> PagedResponse<T> traverse(PagedResponse<T> response, Function<PagedResponse<?>, String> direction);
}
