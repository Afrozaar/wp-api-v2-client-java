package com.afrozaar.wordpress.wpapi.v2.request;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class GetPostRequest extends Request {

    public GetPostRequest(String uri, Map<String, List<String>> params) {
        super(uri, params);
    }

    public static Request newInstance() {
        return new GetPostRequest(Request.POST_GET, ImmutableMap.of());
    }
}
