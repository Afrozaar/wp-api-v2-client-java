package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.model.PostMeta;

import java.util.List;

/**
 * @author johan
 */
public interface PostMetas {

    PostMeta createMeta(Long postId, String key, String value);

    List<PostMeta> getPostMetas(Long postId);

    PostMeta getPostMeta(Long postId, Long metaId);

    List<PostMeta> getCustomPostMetas(Long postId, String requestPath);

    PostMeta getCustomPostMeta(Long postId, Long metaId, String requestPath);

    PostMeta updatePostMetaValue(Long postId, Long metaId, String value);

    PostMeta updatePostMeta(Long postId, Long metaId, String key, String value);

    PostMeta updateCustomPostMeta(Long postId, Long metaId, String key, String value, String customPostTypeName);

    boolean deletePostMeta(Long postId, Long metaId);

    boolean deletePostMeta(Long postId, Long metaId, Boolean force);
}
