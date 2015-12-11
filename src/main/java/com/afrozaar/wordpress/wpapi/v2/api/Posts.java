package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;

import java.util.Map;

public interface Posts {

    /**
     * <pre>
     * GET /posts
     * GET /posts?page=1
     * GET /posts?page=2&meta_key=foo&meta_value=bar
     * </pre>
     */
    PagedResponse<Post> searchPosts(SearchRequest<Post> search);

    /**
     * @param post   {@code Map<String, Object>}
     * @param status
     * @return Created {@link Post}
     * @throws PostCreateException
     * @see <a href="http://wp-api.org/#posts_create-a-post">Create a Post - v1 documentation</a>
     * <pre>
     *     status - Post status of the post:
     *     * draft,
     *     * publish,
     *     * pending,
     *     * future,
     *     * private
     *      or any custom registered status.
     *     If providing a status of future, you must specify a date in order for the post to be published as expected.
     *     Default is draft. (string) optional
     * </pre>
     */
    Post createPost(Map<String, Object> post, PostStatus status) throws PostCreateException;

    Post createPost(Post post, PostStatus status) throws PostCreateException;

    Post getPost(Long id);

    /**
     * <pre>
     * PUT /post/{id}
     * </pre>
     */
    Post updatePost(Post post);

    Post deletePost(Post post);
}
