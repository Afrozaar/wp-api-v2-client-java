package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.model.PostMeta;

import java.util.List;

/**
 * @author johan
 */
public interface Meta {

    PostMeta createMeta(Long postId, String key, String value);

    List<PostMeta> getPostMetas(Long postId);

    PostMeta getPostMeta(Long postId, Long metaId);

    PostMeta updatePostMetaValue(Long postId, Long metaId, String value);
    PostMeta updatePostMeta(Long postId, Long metaId, String key, String value);

    boolean deletePostMeta(Long postId, Long metaId);
    boolean deletePostMeta(Long postId, Long metaId, boolean force);
}
