package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;

import java.util.Map;
import java.util.function.Function;

public interface Posts {

    Function<PagedResponse<Post>, String> next = response -> response.getNext().get();
    Function<PagedResponse<Post>, String> prev = response -> response.getPrevious().get();

    /**
     * <pre>
     * GET /posts
     * GET /posts?page=1
     * GET /posts?page=2&meta_key=foo&meta_value=bar
     * </pre>
     */
    PagedResponse<Post> fetchPosts(SearchRequest<Post> search);

    Post createPost(Map<String, Object> post) throws PostCreateException;
    Post createPost(Post post) throws PostCreateException;

    Post getPost(Integer id);

    /**
     * <pre>
     * PUT /post/{id}
     * </pre>
     */
    Post updatePost(Post post);

    Post deletePost(Post post);

    SearchRequest<Post> fromPagedResponse(PagedResponse<Post> response, Function<PagedResponse<Post>, String> uri);

    PagedResponse<Post> get(PagedResponse<Post> postPagedResponse, Function<PagedResponse<Post>, String> previousOrNext);
}
