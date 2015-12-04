package com.afrozaar.wordpress.wpapi.v2;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IWordpressMockGenerator {

    String generateResponse(Enum type, int numOfPosts) throws JsonProcessingException;

    String generatePosts(int numOfPosts) throws JsonProcessingException;
}
