package com.afrozaar.wordpress.wpapi.v2.request;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class SearchRequest extends Request {

    public SearchRequest(String uri, Map<String, List<String>> params) {
        super(uri, params);
    }

    public static SearchRequest posts() {
        return new SearchRequest(Request.POSTS, ImmutableMap.<String, List<String>>of());
    }

}
