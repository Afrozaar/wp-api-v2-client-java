package com.afrozaar.wordpress.wpapi.v2;

import com.google.common.collect.ImmutableMap;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

public class SearchRequest {

    final String uri;

    final Map<String, List<String>> params;

    static final String POSTS = "/posts";

    public SearchRequest(String uri, Map<String, List<String>> params) {
        this.uri = uri;
        this.params = params;
    }

    private UriComponentsBuilder init(String baseUrl, String context) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl + context + this.uri);
    }

    public UriComponentsBuilder forHost(String baseUrl, String context) {
        final UriComponentsBuilder builder = init(baseUrl, context);
        params.forEach((key, values) -> builder.queryParam(key, values.toArray()));
        return builder;
    }

    public static SearchRequest posts() {
        return new SearchRequest(SearchRequest.POSTS, ImmutableMap.<String, List<String>>of());
    }

    public static SearchRequest fromLink(String link, String context) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(link);
        final UriComponents build = builder.build();
        final ImmutableMap.Builder<String, List<String>> parBuilder = new ImmutableMap.Builder<>();

        build.getQueryParams().entrySet().stream().forEach(entry -> parBuilder.put(entry.getKey(), entry.getValue()));

        return new SearchRequest(build.getPath().replace(context, ""), parBuilder.build());
    }
}
