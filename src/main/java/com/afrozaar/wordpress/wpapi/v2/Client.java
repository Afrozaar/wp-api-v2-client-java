package com.afrozaar.wordpress.wpapi.v2;

import static com.afrozaar.wordpress.wpapi.v2.util.AuthUtil.basicAuth;

import com.afrozaar.wordpress.wpapi.v2.model.Post;

import com.google.common.collect.ImmutableMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

public class Client implements Wordpress {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private RestTemplate restTemplate = new RestTemplate();

    final private String baseUrl;
    final private String username;
    final private String password;

    public Client(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public PagedResponse<Post> fetchPosts() {
        SearchRequest initial = SearchRequest.posts(); //TODO: need better 'default'
        return fetchPosts(initial);
    }

    @Override
    public PagedResponse<Post> fetchPosts(SearchRequest search) {
        //ResponseEntity<String> exchange = template.exchange(WP_ENDPOINT + "/list", HttpMethod.GET, new HttpEntity<String>(AuthUtil.createHeaders(USER_NAME, PASSWORD)), String.class);

        // TODO: traverse pages
        final ResponseEntity<Post[]> exchange = restTemplate.exchange(uriForRequest(search), HttpMethod.GET, basicAuth(username, password), Post[].class, parametersForRequest(search));
        debugHeaders(exchange.getHeaders());

        return PagedResponse.Builder.<Post>aPagedResponse()
                .withPages(Integer.parseInt(exchange.getHeaders().get(Strings.HEADER_TOTAL_PAGES).get(0)))
                .withPosts(Arrays.asList(exchange.getBody()))
                //.withSelf()
                //.withNext()
                //.withPrevious()
                .build();
    }

    private String uriForRequest(SearchRequest search) {
        return baseUrl + CONTEXT + search.uri;
    }

    private Map<String, String> parametersForRequest(SearchRequest search) {
        // todo parse parameters from searchRequest.
        return ImmutableMap.of();
    }

    private void debugHeaders(HttpHeaders headers) {
        LOG.debug("Headers:");
        headers.entrySet().stream().forEach(entry -> {
            LOG.debug("{} -> {}", entry.getKey(), entry.getValue());
        });
    }
}
