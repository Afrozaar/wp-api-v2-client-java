package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.api.Categories;
import com.afrozaar.wordpress.wpapi.v2.api.Medias;
import com.afrozaar.wordpress.wpapi.v2.api.Pages;
import com.afrozaar.wordpress.wpapi.v2.api.PostMetas;
import com.afrozaar.wordpress.wpapi.v2.api.Posts;
import com.afrozaar.wordpress.wpapi.v2.api.Tags;
import com.afrozaar.wordpress.wpapi.v2.api.Taxonomies;
import com.afrozaar.wordpress.wpapi.v2.api.Terms;
import com.afrozaar.wordpress.wpapi.v2.api.Users;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;

import java.net.URI;
import java.util.function.Function;

public interface Wordpress extends Posts, PostMetas, Taxonomies, Terms, Medias, Pages, Users, Tags, Categories {
    String CONTEXT = "/wp-json/wp/v2";

    <T> PagedResponse<T> getPagedResponse(String context, Class<T> typeRef, String... expandParams);

    <T> PagedResponse<T> getPagedResponse(URI uri, Class<T> typeRef);

    <T> PagedResponse<T> traverse(PagedResponse<T> response, Function<PagedResponse<?>, String> direction);

    <T> PagedResponse<T> search(SearchRequest<T> search);
}
