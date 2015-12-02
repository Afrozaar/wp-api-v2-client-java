package com.afrozaar.wordpress.wpapi.v2.request;

import com.afrozaar.wordpress.wpapi.v2.model.Post;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class UpdatePostRequest extends Request {

    public UpdatePostRequest(String uri, Map<String, List<String>> params) {
        super(uri, params);
    }

    public static UpdatePostRequest forPost(Post post) {
        return new UpdatePostRequest(Request.POST_UPDATE, ImmutableMap.of());
    }
}
