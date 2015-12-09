package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.api.PostMetas;

import org.junit.Test;

public class PostMetaTest {

    @Test
    public void testUpdateMeta() {
        PostMetas meta = new Client("http://localhost", "","",true);

        Long postId = 1L;
        Long metaId = 1L;
        String key = "foo";
        String value = "bar";
        meta.updatePostMeta(postId, metaId, key, value);
    }

}