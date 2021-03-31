package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.exception.PostNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.request.Request;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;

import java.util.List;
import java.util.Map;

public interface Posts {

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

    Post getCustomPost(Long id, String postTypeName) throws PostNotFoundException;

    Post getPost(Long id) throws PostNotFoundException;
    Post getPost(Long id, String context) throws PostNotFoundException;

    /**
     * <pre>
     * PUT /post/{id}
     * </pre>
     */
    Post updatePost(Post post);

    Post updatePostField(Long postId, String field, Object value);

    Post deletePost(Post post);

    List<Post> getCategoryPosts(Long categoryId);

    /**
     * Search request just returning the first page of posts.
     */
    static SearchRequest<Post> list() {
        return SearchRequest.Builder.aSearchRequest(Post.class)
                .withUri(Request.POSTS)
                .build();
    }
}
