package com.afrozaar.wordpress.wpapi.v2.request;

import com.afrozaar.wordpress.wpapi.v2.Client;

import com.google.common.collect.ImmutableMap;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

public abstract class Request {
    public static final String POSTS = "/posts";
    public static final String POST = "/posts/{id}";
    public static final String POST_TERMS = "/posts/{postId}/terms/{taxonomy}";
    public static final String POST_TERM = "/posts/{postId}/terms/{taxonomy}/{termId}";

    public static final String METAS = "/posts/{postId}/meta";
    public static final String META = "/posts/{postId}/meta/{metaId}";
    public static final String TAXONOMIES = "/taxonomies";
    public static final String TAXONOMY = "/taxonomies/{slug}";
    public static final String TERMS = "/terms/{taxonomySlug}";
    public static final String TERM = "/terms/{taxonomySlug}/{termId}";
    public static final String MEDIAS = "/media";
    public static final String MEDIA = "/media/{mediaId}";

    final String uri;
    final Map<String, List<String>> params;

    public Request(String uri, Map<String, List<String>> params) {
        this.params = params;
        this.uri = uri;
    }

    public static Request of(String uri) {
        return of(uri, ImmutableMap.of());
    }

    public static Request of(String uri, Map<String, List<String>> params) {
        return new Request(uri, params) {};
    }

    public static <T> SearchRequest<T> fromLink(String link, String context) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(link);
        final UriComponents build = builder.build();
        final ImmutableMap.Builder<String, List<String>> parBuilder = new ImmutableMap.Builder<>();

        build.getQueryParams().entrySet().stream().forEach(entry -> parBuilder.put(entry.getKey(), entry.getValue()));

        return new SearchRequest<>(build.getPath().replace(context, ""), parBuilder.build());
    }

    protected UriComponentsBuilder init(String baseUrl, String context) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl + context + this.uri);
    }

    public UriComponentsBuilder usingClient(Client client) {
        return forHost(client.baseUrl, Client.CONTEXT);
    }

    public UriComponentsBuilder forHost(String baseUrl, String context) {
        final UriComponentsBuilder builder = init(baseUrl, context);
        params.forEach((key, values) -> builder.queryParam(key, values.toArray()));
        return builder;
    }

    public static URI fromLink(String apply) {
        return UriComponentsBuilder.fromHttpUrl(apply).build().toUri();
    }
}
