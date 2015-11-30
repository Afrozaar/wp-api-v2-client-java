package com.afrozaar.wordpress.wpapi.v2;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class SearchRequest {

    final String uri;

    final Map<String, String[]> queryParameters;

    static final String POSTS = "/posts";

    public SearchRequest(String uri, Map<String, String[]> queryParameters) {
        this.uri = uri;
        this.queryParameters = queryParameters;
    }

    public static SearchRequest posts() {
        return new SearchRequest(SearchRequest.POSTS, ImmutableMap.<String, String[]>of());
    }
}
