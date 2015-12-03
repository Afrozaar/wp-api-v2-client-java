package com.afrozaar.wordpress.wpapi.v2.request;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SearchRequest<T> extends Request {

    public SearchRequest(String uri, Map<String, List<String>> params) {
        super(uri, params);
    }

    public static <T> SearchRequest<T> posts() {
        return Builder.<T>aSearchRequest().build();
    }

    public static class Builder<BT> {
        ImmutableMap.Builder<String, List<String>> paramBuilder = new ImmutableMap.Builder<>();
        String uri = Request.POSTS;

        private Builder() {
        }

        public static <BT> Builder<BT> aSearchRequest() {
            return new Builder<>();
        }

        public Builder<BT> withParam(String key, String... values) {
            paramBuilder.put(key, Arrays.asList(values));
            return this;
        }

        public Builder<BT> withParams(Map<String, List<String>> params) {
            this.paramBuilder.putAll(params);
            return this;
        }

        public Builder<BT> withUri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder<BT> but() {
            return Builder.<BT>aSearchRequest().withParams(paramBuilder.build()).withUri(uri);
        }

        public SearchRequest<BT> build() {
            return new SearchRequest<>(uri, paramBuilder.build());
        }
    }
}
