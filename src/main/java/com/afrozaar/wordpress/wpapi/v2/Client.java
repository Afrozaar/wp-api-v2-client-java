package com.afrozaar.wordpress.wpapi.v2;

import static com.afrozaar.wordpress.wpapi.v2.util.AuthUtil.basicAuth;

import com.afrozaar.wordpress.wpapi.v2.model.Link;
import com.afrozaar.wordpress.wpapi.v2.model.Post;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Client implements Wordpress {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private RestTemplate restTemplate = new RestTemplate(Arrays.asList(new MappingJackson2HttpMessageConverter()));
    private final Predicate<Link> next = link -> Strings.NEXT.equals(link.getRel());
    private final Predicate<Link> previous = link -> Strings.PREV.equals(link.getRel());

    final private String baseUrl;
    final private String username;
    final private String password;
    final private boolean debug;

    public Client(String baseUrl, String username, String password, boolean debug) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
        this.debug = debug;
    }

    @Override
    public PagedResponse<Post> fetchPosts() {
        SearchRequest initial = SearchRequest.posts(); //TODO: need better 'default'
        return fetchPosts(initial);
    }

    @Override
    public PagedResponse<Post> fetchPosts(SearchRequest search) {
        //ResponseEntity<String> exchange = template.exchange(WP_ENDPOINT + "/list", HttpMethod.GET, new HttpEntity<String>(AuthUtil.createHeaders(USER_NAME, PASSWORD)), String.class);

        final UriComponentsBuilder uriBuilder = search.forHost(baseUrl, CONTEXT);
        final URI uri = uriBuilder.build().toUri();

        // http://host/posts?page=2&filter=foo

        LOG.trace("fetching {}", uri);

        final ResponseEntity<Post[]> exchange = restTemplate.exchange(uri, HttpMethod.GET, basicAuth(username, password), Post[].class);

        debugHeaders(exchange.getHeaders());

        final HttpHeaders headers = exchange.getHeaders();
        final List<Link> links = parseLinks(headers);
        final List<Post> posts = Arrays.asList(exchange.getBody());

        LOG.trace("{} returned {} posts.", uri, posts.size());

        return PagedResponse.Builder.<Post>aPagedResponse()
                .withPages(headers)
                .withPosts(posts)
                .withSelf(uri.toASCIIString())
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

    private void debugHeaders(HttpHeaders headers) {
        if (debug) {
            LOG.debug("Headers:");
            headers.entrySet().stream().forEach(entry -> LOG.debug("{} -> {}", entry.getKey(), entry.getValue()));
        }
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

    public PagedResponse<Post> get(PagedResponse<Post> postPagedResponse, Function<PagedResponse<Post>, String> uri) {
        return fetchPosts(fromPagedResponse(postPagedResponse, uri));
    }

    private SearchRequest fromPagedResponse(PagedResponse<Post> response, Function<PagedResponse<Post>, String> uri) {
        return SearchRequest.fromLink(uri.apply(response), CONTEXT);
    }
}
