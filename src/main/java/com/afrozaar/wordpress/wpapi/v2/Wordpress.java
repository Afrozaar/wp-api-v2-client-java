package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.model.Post;

public interface Wordpress {
    String CONTEXT = "/wp-json/wp/v2";

    PagedResponse<Post> fetchPosts();

    PagedResponse<Post> fetchPosts(SearchRequest search);

}
