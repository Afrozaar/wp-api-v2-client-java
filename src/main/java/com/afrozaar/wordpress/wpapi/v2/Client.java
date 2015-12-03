package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.model.Link;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.request.GetPostRequest;
import com.afrozaar.wordpress.wpapi.v2.request.Request;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.request.UpdatePostRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.afrozaar.wordpress.wpapi.v2.util.AuthUtil;
import com.afrozaar.wordpress.wpapi.v2.util.Two;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

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

    public final String baseUrl;
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
    public Post createPost(Post post) {
        return null;
    }

    @Override
    public Post getPost(Integer id) {
        final URI uri = GetPostRequest.newInstance().usingClient(this).buildAndExpand(id).toUri();
        final ResponseEntity<Post> exchange = doExchange(HttpMethod.GET, uri, Post.class, null);

        return exchange.getBody();
    }

    @Override
    public Post updatePost(Post post) {
        final URI uri = UpdatePostRequest.forPost(post).usingClient(this).buildAndExpand(post.getId()).toUri();
        final ResponseEntity<Post> exchange = doExchange(HttpMethod.PUT, uri, Post.class, post);

        return exchange.getBody();
    }

    @Override
    public PagedResponse<Post> fetchPosts(SearchRequest<Post> search) {
        final URI uri = search.forHost(baseUrl, CONTEXT).build().toUri();
        final ResponseEntity<Post[]> exchange = doExchange(HttpMethod.GET, uri, Post[].class, null);

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

    private <T> ResponseEntity<T> doExchange(HttpMethod method, URI uri, Class<T> typeRef, T body) {
        final Two<String, String> authTuple = AuthUtil.authTuple(username, password);
        final RequestEntity<T> entity = RequestEntity.method(method, uri).header(authTuple.k, authTuple.v).body(body);
        debugRequest(entity);
        final ResponseEntity<T> exchange = restTemplate.exchange(entity, typeRef);
        debugHeaders(exchange.getHeaders());
        return exchange;
    }

    private Optional<String> link(List<Link> links, Predicate<? super Link> linkPredicate) {
        return links.stream()
                .filter(linkPredicate)
                .map(Link::getHref)
                .findFirst();
    }

    private void debugRequest(RequestEntity<?> entity) {
        if (debug) {
            LOG.debug("Request Entity: {}", entity);
        }
    }

    private void debugHeaders(HttpHeaders headers) {
        if (debug) {
            LOG.debug("Response Headers:");
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

    @Override
    public PagedResponse<Post> get(PagedResponse<Post> postPagedResponse, Function<PagedResponse<Post>, String> previousOrNext) {
        return fetchPosts(fromPagedResponse(postPagedResponse, previousOrNext));
    }

    @Override
    public SearchRequest<Post> fromPagedResponse(PagedResponse<Post> response, Function<PagedResponse<Post>, String> previousOrNext) {
        return Request.fromLink(previousOrNext.apply(response), CONTEXT);
    }
}
