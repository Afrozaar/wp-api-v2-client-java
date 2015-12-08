package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.exception.TermNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.Link;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostMeta;
import com.afrozaar.wordpress.wpapi.v2.model.Taxonomy;
import com.afrozaar.wordpress.wpapi.v2.model.Term;
import com.afrozaar.wordpress.wpapi.v2.request.Request;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.afrozaar.wordpress.wpapi.v2.util.AuthUtil;
import com.afrozaar.wordpress.wpapi.v2.util.Two;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Client implements Wordpress {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private RestTemplate restTemplate = new RestTemplate();
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
    public Post createPost(Map<String, Object> post) throws PostCreateException {
        try {
            return doExchange1(Request.POSTS, HttpMethod.POST, Post.class, forExpand(), null, post).getBody();
        } catch (HttpClientErrorException e) {
            throw new PostCreateException(e);
        }
    }

    @Override
    public Post createPost(Post post) throws PostCreateException {
        return createPost(fieldsFrom(post));
    }

    @Override
    public Post getPost(Integer id) {
        final ResponseEntity<Post> exchange = doExchange1(Request.POST, HttpMethod.GET, Post.class, forExpand(id), null, null);

        return exchange.getBody();
    }

    @Override
    public Post updatePost(Post post) {

        // update post is not as straight forward :(
        // the fields need to be checked for being empty (not null) and should not be included

        final ResponseEntity<Post> exchange = doExchange1(Request.POST, HttpMethod.PUT, Post.class, forExpand(post.getId()), ImmutableMap.of(), fieldsFrom(post));
        return exchange.getBody();
    }

    @Override
    public Post deletePost(Post post) {
        final ResponseEntity<Post> exchange = doExchange1(Request.POST, HttpMethod.DELETE, Post.class, forExpand(post.getId()), null, null);// Deletion of a post returns the post's data before removing it.
        Preconditions.checkArgument(exchange.getStatusCode().is2xxSuccessful());
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

        return PagedResponse.Builder.aPagedResponse(Post.class)
                .withPages(headers)
                .withPosts(posts)
                .withSelf(uri.toASCIIString())
                .withNext(link(links, next))
                .withPrevious(link(links, previous))
                .build();
    }

    @Override
    public PostMeta createMeta(Integer postId, String key, String value) {
        final ImmutableMap<String, String> body = ImmutableMap.of("key", key, "value", value);
        final ResponseEntity<PostMeta> exchange = doExchange1(Request.METAS, HttpMethod.POST, PostMeta.class, forExpand(postId), null, body);
        return exchange.getBody();
    }

    @Override
    public List<PostMeta> getPostMetas(Integer postId) {
        final ResponseEntity<PostMeta[]> exchange = doExchange1(Request.METAS, HttpMethod.GET, PostMeta[].class, forExpand(postId), null, null);
        return Arrays.asList(exchange.getBody());
    }

    @Override
    public PostMeta getPostMeta(Integer postId, Integer metaId) {
        final ResponseEntity<PostMeta> exchange = doExchange1(Request.META, HttpMethod.GET, PostMeta.class, forExpand(postId, metaId), null, null);
        return exchange.getBody();
    }

    @Override
    public PostMeta updatePostMetaValue(Integer postId, Integer metaId, String value) {
        return updatePostMeta(postId, metaId, null, value);
    }

    @Override
    public PostMeta updatePostMeta(Integer postId, Integer metaId, String key, String value) {
        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<>();
        populateEntry(() -> key, builder, "key");
        populateEntry(() -> value, builder, "value");

        final ResponseEntity<PostMeta> exchange = doExchange1(Request.META, HttpMethod.POST, PostMeta.class, forExpand(postId, metaId), null, builder.build());

        return exchange.getBody();
    }

    @Override
    public boolean deletePostMeta(Integer postId, Integer metaId) {
        final ResponseEntity<Map> exchange = doExchange1(Request.META, HttpMethod.DELETE, Map.class, forExpand(postId, metaId), null, null);
        Preconditions.checkArgument(exchange.getStatusCode().is2xxSuccessful(), String.format("Expected success on post meta delete request: /posts/%s/meta/%s", postId, metaId));

        return exchange.getStatusCode().is2xxSuccessful();
    }

    @Override
    public boolean deletePostMeta(Integer postId, Integer metaId, boolean force) {
        final ResponseEntity<Map> exchange = doExchange1(Request.META, HttpMethod.DELETE, Map.class, forExpand(postId, metaId), ImmutableMap.of("force", force), null);
        Preconditions.checkArgument(exchange.getStatusCode().is2xxSuccessful(), String.format("Expected success on post meta delete request: /posts/%s/meta/%s", postId, metaId));

        return exchange.getStatusCode().is2xxSuccessful();
    }

    @Override
    public List<Taxonomy> getTaxonomies() {
        final ResponseEntity<Taxonomy[]> exchange = doExchange1(Request.TAXONOMIES, HttpMethod.GET, Taxonomy[].class, forExpand(), null, null);
        return Arrays.asList(exchange.getBody());
    }

    @Override
    public Taxonomy getTaxonomy(String slug) {
        return doExchange1(Request.TAXONOMY, HttpMethod.GET, Taxonomy.class, forExpand(slug), null, null).getBody();
    }

    @Override
    public Term createTerm(String taxonomy, Term term) {
        return doExchange1(Request.TERMS, HttpMethod.POST, Term.class, forExpand(taxonomy), null, term.asMap()).getBody();
    }

    @Override
    public List<Term> getTerms(String taxonomy) {
        List<Term> collected = new ArrayList<>();
        PagedResponse<Term> pagedResponse = this.getPagedResponse(Request.TERMS, Term.class, taxonomy);
        collected.addAll(pagedResponse.getList());
        while (pagedResponse.hasNext()) {
            pagedResponse = this.traverse(pagedResponse, PagedResponse.NEXT);
            collected.addAll(pagedResponse.getList());
        }
        return collected;
    }

    @Override
    public Term getTerm(String taxonomy, Long id) throws TermNotFoundException {
        try {
            return doExchange1(Request.TERM, HttpMethod.GET, Term.class, forExpand(taxonomy, id), null, null).getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError() && e.getStatusCode().value() == 404) {
                throw new TermNotFoundException(e);
            } else {
                throw e;
            }
        }
    }

    @Override
    public Term updateTerm(String taxonomy, Term term) {
        return doExchange1(Request.TERM, HttpMethod.POST, Term.class, forExpand(taxonomy, term.getId()), null, term.asMap()).getBody();
    }

    @Override
    public Term deleteTerm(String taxonomy, Term term) throws TermNotFoundException {
        try {
            return doExchange1(Request.TERM, HttpMethod.DELETE, Term.class, forExpand(taxonomy, term.getId()), null, null).getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError() && e.getStatusCode().value() == 404) {
                throw new TermNotFoundException(e);
            } else {
                throw e;
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> PagedResponse<T> getPagedResponse(String context, Class<T> typeRef, String... expandParams) {
        final URI uri = Request.of(context).usingClient(this).buildAndExpand(expandParams).toUri();
        return getPagedResponse(uri, typeRef);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> PagedResponse<T> getPagedResponse(final URI uri, Class<T> typeRef) {
        try {
            final ResponseEntity<T[]> exchange = doExchange0(HttpMethod.GET, uri, (Class<T[]>) Class.forName("[L" + typeRef.getName() + ";"), null);
            final HttpHeaders headers = exchange.getHeaders();
            final List<Link> links = parseLinks(headers);

            final List<T> body = Arrays.asList((T[]) exchange.getBody()); // Ugly... but the only way to get the generic stuff working

            return PagedResponse.Builder.aPagedResponse(typeRef)
                    .withPages(headers)
                    .withPosts(body)
                    .withSelf(uri.toASCIIString())
                    .withNext(link(links, next))
                    .withPrevious(link(links, previous))
                    .build();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> PagedResponse<T> traverse(PagedResponse<T> response, Function<PagedResponse<?>, String> direction) {
        final URI uri = response.getUri(direction);
        return getPagedResponse(uri, response.getClazz());
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

    private Map<String, Object> fieldsFrom(Post post) {
        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<>();

        populateEntry(post::getDate, builder, "date");
        populateEntry(post::getModifiedGmt, builder, "modified_gmt");
        //populateEntry(post::getSlug, builder, "slug");
        //populateEntry(post::getCommentStatus, builder, "status");
        populateEntry(() -> post.getTitle().getRendered(), builder, "title");
        populateEntry(() -> post.getContent().getRendered(), builder, "content");
        populateEntry(post::getAuthor, builder, "author");
        populateEntry(() -> post.getExcerpt().getRendered(), builder, "excerpt");
        populateEntry(post::getCommentStatus, builder, "comment_status");
        populateEntry(post::getPingStatus, builder, "ping_status");
        populateEntry(post::getFormat, builder, "format");
        populateEntry(post::getSticky, builder, "sticky");

        return builder.build();
    }

    <T> void populateEntry(Supplier<T> supplier, ImmutableMap.Builder<String, Object> builder, String key) {
        Optional.ofNullable(supplier.get()).ifPresent(value -> builder.put(key, value));
    }

    private <T> ResponseEntity<T> doExchange(HttpMethod method, URI uri, Class<T> typeRef, T body) {
        return doExchange0(method, uri, typeRef, body);
    }

    private <T, B> ResponseEntity<T> doExchange0(HttpMethod method, URI uri, Class<T> typeRef, B body) {
        final Two<String, String> authTuple = AuthUtil.authTuple(username, password);
        final RequestEntity<B> entity = RequestEntity.method(method, uri).header(authTuple.k, authTuple.v).body(body);
        debugRequest(entity);
        final ResponseEntity<T> exchange = restTemplate.exchange(entity, typeRef);
        debugHeaders(exchange.getHeaders());
        return exchange;
    }

    private <T, B> ResponseEntity<T> doExchange0(HttpMethod method, UriComponents uriComponents, Class<T> typeRef, B body) {
        return doExchange0(method, uriComponents.toUri(), typeRef, body);
    }

    private <T, B> ResponseEntity<T> doExchange1(String context, HttpMethod method, Class<T> typeRef, Object[] buildAndExpand, Map<String, Object> queryParams, B body) {
        final UriComponentsBuilder builder = Request.of(context).usingClient(this);
        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }
        return doExchange0(method, builder.buildAndExpand(buildAndExpand), typeRef, body);
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

    private Object[] forExpand(Object... values) {
        return values;
    }
}
