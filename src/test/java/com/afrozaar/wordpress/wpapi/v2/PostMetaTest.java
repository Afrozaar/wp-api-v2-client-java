package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.api.Meta;

import org.junit.Test;

public class PostMetaTest {

    @Test
    public void testUpdateMeta() {
        Meta meta = new Client("http://localhost", "","",true);

        Integer postId = 1;
        Integer metaId = 1;
        String key = "foo";
        String value = "bar";
        meta.updatePostMeta(postId, metaId, key, value);
    }

}