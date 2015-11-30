package com.afrozaar.wordpress.wpapi.v2;

import static com.afrozaar.wordpress.wpapi.v2.util.AuthUtil.basicAuth;

import com.afrozaar.wordpress.wpapi.v2.model.Link;
import com.afrozaar.wordpress.wpapi.v2.model.Post;

import com.google.common.collect.ImmutableMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Client implements Wordpress {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);
    public static final String SELF = "self";

    private RestTemplate restTemplate = new RestTemplate(Arrays.asList(new MappingJackson2HttpMessageConverter()));
    private final Predicate<Link> next = link -> Strings.NEXT.equals(link.getRel());
    private final Predicate<Link> previous = link -> Strings.PREV.equals(link.getRel());
    private final Predicate<Link> self = link -> SELF.equals(link.getRel());

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
        final String uri = uriForRequest(search);
        final Map<String, String> parameters = parametersForRequest(search);

        // http://host/posts?page=2&filter=foo

        final ResponseEntity<Post[]> exchange = restTemplate.exchange(uri, HttpMethod.GET, basicAuth(username, password), Post[].class, parameters);
        debugHeaders(exchange.getHeaders());

        final HttpHeaders headers = exchange.getHeaders();

        final List<Link> links = parseLinks(headers);

        return PagedResponse.Builder.<Post>aPagedResponse()
                .withPages(headers)
                .withPosts(Arrays.asList(exchange.getBody()))
                .withSelf(self(uri, parameters))
                .withNext(link(links, next))
                .withPrevious(link(links, previous))
                .build();
    }

    private Optional<String> link(List<Link> links, Predicate<? super Link> linkPredicate) {
        return links.stream()
                .filter(linkPredicate)
                .map(Link::getHref)
                .findFirst();
    }

    private String self(String uri, Map<String, String> parameters) {
        return null;
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


    public List<Link> parseLinks(HttpHeaders headers) {
        //Link -> [<http://johan-wp/wp-json/wp/v2/posts?page=2>; rel="next"]

        Optional<List<String>> linkHeader = Optional.ofNullable(headers.get(Strings.HEADER_LINK));
        if (linkHeader.isPresent()) {
            final String rawResponse = linkHeader.get().get(0);
            final String[] links = rawResponse.split(", ");

            return Arrays.stream(links).map(link -> { // <http://johan-wp/wp-json/wp/v2/posts?page=2>; rel="next"
                String[] linkData = link.split("; ");
                final String href = linkData[0].replace("<", "").replace(">", "");
                final String rel = linkData[1].substring(4).replace("\"", "");
                return Link.of(href, rel);
            }).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }

    }
}
