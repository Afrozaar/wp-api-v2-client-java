package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.model.Post;

public interface WordpressInstanceInterface {

    Post[] generateRandomPosts(int numOfPosts);
}
