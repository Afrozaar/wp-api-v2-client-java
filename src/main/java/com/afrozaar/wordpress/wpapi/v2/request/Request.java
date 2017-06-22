package com.afrozaar.wordpress.wpapi.v2.request;

import com.afrozaar.wordpress.wpapi.v2.Client;

import com.google.common.collect.ImmutableMap;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

public abstract class Request {
    public static final String POSTS = "/posts";
    public static final String POST = "/posts/{id}";
    public static final String POST_TERMS = "/posts/{postId}/{taxonomy}";
    public static final String POST_TERM = "/posts/{postId}/{taxonomy}/{termId}";
    public static final String POST_TAGS = "/tags?post={postId}";

    public static final String METAS = "/posts/{postId}/meta";
    public static final String META = "/posts/{postId}/meta/{metaId}";
    public static final String META_POST_DELETE = "/posts/{postId}/meta/{metaId}/delete";
    public static final String TAXONOMIES = "/taxonomies";
    public static final String TAXONOMY = "/taxonomies/{slug}";
    public static final String TERMS = "/terms/{taxonomySlug}";
    public static final String TERM = "/terms/{taxonomySlug}/{termId}";
    public static final String MEDIAS = "/media";
    public static final String MEDIA = "/media/{mediaId}";
    public static final String PAGES = "/pages";
    public static final String PAGE = "/pages/{pageId}";
    public static final String USERS = "/users";
    public static final String USERS_WITH_CONTEXT = "/users?context={context}";
    public static final String USER = "/users/{userId}";
    public static final String TAGS = "/tags";
    public static final String TAG = "/tags/{tagId}";
    public static final String CATEGORIES = "/categories";
    public static final String CATEGORY = "/categories/{categoryId}";
    public static final String QP_CONTEXT = "context";
    public static final String QP_ORDER_BY = "orderby";
    public static final String QP_ORDER = "order";
    private static final String QP_REST_ROUTE = "rest_route";
    private static final String WP_JSON = "/wp-json";

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
        return new Request(uri, params) {
        };
    }

    public UriComponentsBuilder usingClient(Client client) {
        return forHost(client, Client.CONTEXT);
    }

    public UriComponentsBuilder forHost(Client client, String context0) {

        final String context = (client.permalinkEndpoint ? context0 : context0.replace(WP_JSON, "")) + this.uri;
        // context = "/wp-json/wp/v2"

        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(client.baseUrl + (client.permalinkEndpoint ? context : ""));
        if (!client.permalinkEndpoint) {
            builder.queryParam(QP_REST_ROUTE, context);
        }
        params.forEach((key, values) -> builder.queryParam(key, values.toArray()));
        return builder;
    }

    public static URI fromLink(String apply) {
        return UriComponentsBuilder.fromHttpUrl(apply).build().toUri();
    }

    public String asRequestUrl(Client client) {
        return usingClient(client).build().toUri().toASCIIString();
    }
}
