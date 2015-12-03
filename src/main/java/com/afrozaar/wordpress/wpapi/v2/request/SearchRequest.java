package com.afrozaar.wordpress.wpapi.v2.request;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SearchRequest extends Request {

    public SearchRequest(String uri, Map<String, List<String>> params) {
        super(uri, params);
    }

    public static SearchRequest posts() {
        return Builder.aSearchRequest().build();
    }

    public static class Builder {
        ImmutableMap.Builder<String, List<String>> paramBuilder = new ImmutableMap.Builder<>();
        String uri = Request.POSTS;

        private Builder() {
        }

        public static Builder aSearchRequest() {
            return new Builder();
        }

        public Builder withParam(String key, String... values) {
            paramBuilder.put(key, Arrays.asList(values));
            return this;
        }

        public Builder withParams(Map<String, List<String>> params) {
            this.paramBuilder.putAll(params);
            return this;
        }

        public Builder withUri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder but() {
            return aSearchRequest().withParams(paramBuilder.build()).withUri(uri);
        }

        public SearchRequest build() {
            return new SearchRequest(uri, paramBuilder.build());
        }
    }
}
