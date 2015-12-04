package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.api.Meta;
import com.afrozaar.wordpress.wpapi.v2.api.Posts;

public interface Wordpress extends Posts, Meta {
    String CONTEXT = "/wp-json/wp/v2";

}
