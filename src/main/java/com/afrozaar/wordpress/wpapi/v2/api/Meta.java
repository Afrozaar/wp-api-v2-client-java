package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.model.PostMeta;

import java.util.List;

/**
 * @author johan
 */
public interface Meta {

    PostMeta createMeta(Integer postId, String key, String value);

    List<PostMeta> getPostMetas(Integer postId);

    PostMeta getPostMeta(Integer postId, Integer metaId);

    PostMeta updatePostMeta();

    // deletePostMeta();
}
