package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.api.Posts;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;

import java.util.function.Function;

public interface Wordpress extends Posts {
    String CONTEXT = "/wp-json/wp/v2";

    PagedResponse<Post> get(PagedResponse<Post> postPagedResponse, Function<PagedResponse<Post>, String> previousOrNext);

    SearchRequest<Post> fromPagedResponse(PagedResponse<Post> response, Function<PagedResponse<Post>, String> uri);
}
