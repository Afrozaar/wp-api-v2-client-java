package com.afrozaar.wordpress.wpapi.v2;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IWordpressMockGenerator {

    String generatePosts(int numOfPosts) throws JsonProcessingException;
}
