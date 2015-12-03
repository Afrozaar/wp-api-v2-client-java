package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;

public interface Wordpress {
    String CONTEXT = "/wp-json/wp/v2";

    /**
     * <pre>
     * GET /posts
     * GET /posts?page=1
     * GET /posts?page=2&meta_key=foo&meta_value=bar
     * </pre>
     */
    PagedResponse<Post> fetchPosts(SearchRequest search);

    Post createPost(Post post);

    Post getPost(Integer id);

    /**
     * <pre>
     * PUT /post/{id}
     * </pre>
     */
    Post updatePost(Post post);

    // deletePost(Integer id);
}
