package com.afrozaar.wordpress.wpapi.v2.request;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * @author johan
 */
public class CreatePostRequest extends Request {

    public CreatePostRequest(String uri, Map<String, List<String>> params) {
        super(uri, params);
    }

    public static CreatePostRequest newInstance() {
        return new CreatePostRequest(Request.POSTS, ImmutableMap.of());
    }
}
