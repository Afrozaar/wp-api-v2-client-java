package com.afrozaar.wordpress.wpapi.v2;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface WordpressInstanceInterface {

    byte[] getJsonObject (int numOfPosts) throws JsonProcessingException;
}
