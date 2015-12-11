package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.exception.PageNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.exception.ParsedRestException;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.exception.TermNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Link;
import com.afrozaar.wordpress.wpapi.v2.model.Media;
import com.afrozaar.wordpress.wpapi.v2.model.Page;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostMeta;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.model.Taxonomy;
import com.afrozaar.wordpress.wpapi.v2.model.Term;
import com.afrozaar.wordpress.wpapi.v2.request.Request;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.afrozaar.wordpress.wpapi.v2.util.AuthUtil;
import com.afrozaar.wordpress.wpapi.v2.util.Two;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
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
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
    public Post createPost(final Map<String, Object> postFields, PostStatus status) throws PostCreateException {
        final ImmutableMap<String, Object> post = new ImmutableMap.Builder<String, Object>()
                .putAll(postFields)
                .put("status", status.value)
                .build();
        try {
            return doExchange1(Request.POSTS, HttpMethod.POST, Post.class, forExpand(), null, post).getBody();
        } catch (HttpClientErrorException e) {
            throw new PostCreateException(e);
        }
    }

    @Override
    public Post createPost(Post post, PostStatus status) throws PostCreateException {
        return createPost(fieldsFrom(post), status);
    }

    @Override
    public Post getPost(Long id) {
        final ResponseEntity<Post> exchange = doExchange1(Request.POST, HttpMethod.GET, Post.class, forExpand(id), null, null);

        return exchange.getBody();
    }

    @Override
    public Post updatePost(Post post) {
        final ResponseEntity<Post> exchange = doExchange1(Request.POST, HttpMethod.PUT, Post.class, forExpand(post.getId()), ImmutableMap.of(), fieldsFrom(post));
        return exchange.getBody();
    }

    @Override
    public Post updatePostField(Long postId, String field, Object value) {
        return doExchange1(Request.POST, HttpMethod.PUT, Post.class, forExpand(postId), null, ImmutableMap.of(field, value)).getBody();
    }

    @Override
    public Post deletePost(Post post) {
        final ResponseEntity<Post> exchange = doExchange1(Request.POST, HttpMethod.DELETE, Post.class, forExpand(post.getId()), null, null);// Deletion of a post returns the post's data before removing it.
        Preconditions.checkArgument(exchange.getStatusCode().is2xxSuccessful());
        return exchange.getBody();
    }

    @Override
    public <T> PagedResponse<T> search(SearchRequest<T> search) {
        final URI uri = search.usingClient(this).build().toUri();
        return getPagedResponse(uri, search.getClazz());
    }

    @Override
    public Media createMedia(Media media, Resource resource) throws WpApiParsedException {
        try {
            final MultiValueMap<String, Object> uploadMap = new LinkedMultiValueMap<>();
            BiConsumer<String, Object> p = (index, value) -> Optional.ofNullable(value).ifPresent(v -> uploadMap.add(index, v));

            p.accept("title", media.getTitle().getRendered());
            p.accept("post", media.getPost());
            p.accept("alt_text", media.getAltText());
            p.accept("caption", media.getCaption());
            p.accept("description", media.getDescription());

            uploadMap.add("file", resource);

            return doExchange1(Request.MEDIAS, HttpMethod.POST, Media.class, forExpand(), null, uploadMap).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw WpApiParsedException.of(e);
        }
    }

    @Override
    public Post setPostFeaturedImage(Long postId, Media media) {
        Preconditions.checkArgument("image".equals(media.getMediaType()), "Can not set non-image media type as a featured image on a post.");
        return updatePostField(postId, "featured_image", media.getId());
    }

    @Override
    public List<Media> getMedia() {
        List<Media> collected = new ArrayList<>();
        PagedResponse<Media> pagedResponse = this.getPagedResponse(Request.MEDIAS, Media.class);
        collected.addAll(pagedResponse.getList());
        while (pagedResponse.hasNext()) {
            pagedResponse = this.traverse(pagedResponse, PagedResponse.NEXT);
            collected.addAll(pagedResponse.getList());
        }
        return collected;
    }

    @Override
    public Media getMedia(Long id) {
        return doExchange1(Request.MEDIA, HttpMethod.GET, Media.class, forExpand(id), null, null).getBody();
    }

    @Override
    public Media updateMedia(Media media) {
        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<>();
        BiConsumer<String, Object> p = (key, value) -> Optional.ofNullable(value).ifPresent(v -> builder.put(key, v));

        p.accept("title", media.getTitle().getRendered());
        p.accept("post", media.getPost());
        p.accept("alt_text", media.getAltText());
        p.accept("caption", media.getCaption());
        p.accept("description", media.getDescription());

        ResponseEntity<Media> exchange = doExchange1(Request.MEDIA, HttpMethod.POST, Media.class, forExpand(media.getId()), null, builder.build());

        return exchange.getBody();
    }

    @Override
    public boolean deleteMedia(Media media, boolean force) {
        final ResponseEntity<Media> exchange = doExchange1(Request.MEDIA, HttpMethod.DELETE, Media.class, forExpand(media.getId()), ImmutableMap.of("force", force), null);
        return exchange.getStatusCode().is2xxSuccessful();
    }

    @Override
    public boolean deleteMedia(Media media) {
        final ResponseEntity<Media> exchange = doExchange1(Request.MEDIA, HttpMethod.DELETE, Media.class, forExpand(media.getId()), null, null);
        return exchange.getStatusCode().is2xxSuccessful();
    }

    @Override
    public PostMeta createMeta(Long postId, String key, String value) {
        final ImmutableMap<String, String> body = ImmutableMap.of("key", key, "value", value);
        final ResponseEntity<PostMeta> exchange = doExchange1(Request.METAS, HttpMethod.POST, PostMeta.class, forExpand(postId), null, body);
        return exchange.getBody();
    }

    @Override
    public List<PostMeta> getPostMetas(Long postId) {
        final ResponseEntity<PostMeta[]> exchange = doExchange1(Request.METAS, HttpMethod.GET, PostMeta[].class, forExpand(postId), null, null);
        return Arrays.asList(exchange.getBody());
    }

    @Override
    public PostMeta getPostMeta(Long postId, Long metaId) {
        final ResponseEntity<PostMeta> exchange = doExchange1(Request.META, HttpMethod.GET, PostMeta.class, forExpand(postId, metaId), null, null);
        return exchange.getBody();
    }

    @Override
    public PostMeta updatePostMetaValue(Long postId, Long metaId, String value) {
        return updatePostMeta(postId, metaId, null, value);
    }

    @Override
    public PostMeta updatePostMeta(Long postId, Long metaId, String key, String value) {
        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<>();
        BiConsumer<String, Object> biConsumer = (key1, value1) -> Optional.ofNullable(value1).ifPresent(v -> builder.put(key1, v));

        biConsumer.accept("key", key);
        biConsumer.accept("value", value);
        final ResponseEntity<PostMeta> exchange = doExchange1(Request.META, HttpMethod.POST, PostMeta.class, forExpand(postId, metaId), null, builder.build());

        return exchange.getBody();
    }

    @Override
    public boolean deletePostMeta(Long postId, Long metaId) {
        final ResponseEntity<Map> exchange = doExchange1(Request.META, HttpMethod.DELETE, Map.class, forExpand(postId, metaId), null, null);
        Preconditions.checkArgument(exchange.getStatusCode().is2xxSuccessful(), String.format("Expected success on post meta delete request: /posts/%s/meta/%s", postId, metaId));

        return exchange.getStatusCode().is2xxSuccessful();
    }

    @Override
    public boolean deletePostMeta(Long postId, Long metaId, boolean force) {
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
    public Term createTerm(String taxonomy, Term term) throws WpApiParsedException {
        try {
            return doExchange1(Request.TERMS, HttpMethod.POST, Term.class, forExpand(taxonomy), null, term.asMap()).getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            final WpApiParsedException exception = WpApiParsedException.of(e);
            LOG.error("Could not create {} term '{}'. {} ", taxonomy, term.getName(), exception.getMessage(), exception);
            throw exception;
        }
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

    @Override
    public List<Term> deleteTerms(String taxonomy, Term... terms) {
        List<Term> deletedTerms = new ArrayList<>(terms.length);

        for (Term term : terms) {
            try {
                deletedTerms.add(deleteTerm(taxonomy, term));
            } catch (TermNotFoundException e) {
                LOG.error("Error ", e);
            }
        }

        return deletedTerms;
    }

    @Override
    public Term createPostTerm(Post post, String taxonomy, Term term) throws WpApiParsedException {
        final Term termToUse = Objects.nonNull(term.getId()) ? term : createTerm(taxonomy, term);
        return doExchange1(Request.POST_TERM, HttpMethod.POST, Term.class, forExpand(post.getId(), taxonomy, termToUse.getId()), null, null).getBody();
    }

    @Override
    public Term updatePostTerm(Post post, String taxonomy, Term term) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public List<Term> getPostTerms(Post post, String taxonomy) {
        // TODO: 2015/12/10 For tagging we might need to do paged requests for example if a post has a large number of tags.
        return Arrays.asList(doExchange1(Request.POST_TERMS, HttpMethod.GET, Term[].class, forExpand(post.getId(), taxonomy), null, null).getBody());
    }

    @Override
    public Term deletePostTerm(Post post, String taxonomy, Term term) {
        // This returns 501 Not Implemented from server. Use variant with force parameter.
        return doExchange1(Request.POST_TERM, HttpMethod.DELETE, Term.class, forExpand(post.getId(), taxonomy, term.getId()), null, null).getBody();
    }

    @Override
    public Term deletePostTerm(Post post, String taxonomy, Term term, boolean force) {
        return doExchange1(Request.POST_TERM, HttpMethod.DELETE, Term.class, forExpand(post.getId(), taxonomy, term.getId()), ImmutableMap.of("force", force), null).getBody();
    }

    @Override
    public Term getPostTerm(Post post, String taxonomy, Term term) throws WpApiParsedException {
        try {
            return doExchange1(Request.POST_TERM, HttpMethod.GET, Term.class, forExpand(post.getId(), taxonomy, term.getId()), null, null).getBody();
        } catch (HttpStatusCodeException e) {
            throw new WpApiParsedException(ParsedRestException.of(e));
        }
    }

    @Override
    public Page createPage(Page page, PostStatus status) {
        final Map<String, Object> map = page.asMap();
        final ImmutableMap<String, Object> pageFields = new ImmutableMap.Builder<String, Object>()
                .putAll(map)
                .put("status", status.value)
                .build();

        return doExchange1(Request.PAGES, HttpMethod.POST, Page.class, forExpand(), null, pageFields).getBody();
    }

    @Override
    public Page getPage(Long pageId) throws PageNotFoundException {
        try {
            return getPage(pageId, "view");
        } catch (HttpClientErrorException e) {
            throw new PageNotFoundException(e);
        }
    }

    @Override
    public Page getPage(Long pageId, String context) {
        return doExchange1(Request.PAGE, HttpMethod.GET, Page.class, forExpand(pageId), ImmutableMap.of("context", context), null).getBody();
    }

    @Override
    public Page updatePage(Page page) {
        return doExchange1(Request.PAGE, HttpMethod.POST, Page.class, forExpand(page.getId()), null, page.asMap()).getBody();
    }

    @Override
    public Page deletePage(Page page) {
        return doExchange1(Request.PAGE, HttpMethod.DELETE, Page.class, forExpand(page.getId()), null, null).getBody();
    }

    @Override
    public Page deletePage(Page page, boolean force) {
        return doExchange1(Request.PAGE, HttpMethod.DELETE, Page.class, forExpand(page.getId()), ImmutableMap.of("force", force), null).getBody();
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

    private Map<String, Object> fieldsFrom(Post post) {
        ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<>();

        BiConsumer<String, Object> biConsumer = (key, value) -> Optional.ofNullable(value).ifPresent(v -> builder.put(key, v));

        biConsumer.accept("date", post.getDate());
        biConsumer.accept("modified_gmt", post.getModified());
        //        biConsumer.accept("slug", post.getSlug());
        //        biConsumer.accept("status",post.getStatus());
        biConsumer.accept("title", post.getTitle().getRendered());
        biConsumer.accept("content", post.getContent().getRendered());
        biConsumer.accept("author", post.getAuthor());
        biConsumer.accept("excerpt", post.getExcerpt().getRendered());
        biConsumer.accept("comment_status", post.getCommentStatus());
        biConsumer.accept("ping_status", post.getPingStatus());
        biConsumer.accept("format", post.getFormat());
        biConsumer.accept("sticky", post.getSticky());
        biConsumer.accept("featured_image", post.getFeaturedImage());

        return builder.build();
    }

    private <T> ResponseEntity<T> doExchange(HttpMethod method, URI uri, Class<T> typeRef, T body) {
        return doExchange0(method, uri, typeRef, body);
    }

    private <T, B> ResponseEntity<T> doExchange0(HttpMethod method, URI uri, Class<T> typeRef, B body) {
        final Two<String, String> authTuple = AuthUtil.authTuple(username, password);
        final RequestEntity<B> entity = RequestEntity.method(method, uri).header(authTuple.a, authTuple.b).body(body);
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
