package com.afrozaar.wordpress.wpapi.v2;

import static com.afrozaar.wordpress.wpapi.v2.api.Taxonomies.CATEGORY;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import com.afrozaar.wordpress.wpapi.v2.api.Contexts;
import com.afrozaar.wordpress.wpapi.v2.api.Posts;
import com.afrozaar.wordpress.wpapi.v2.api.Taxonomies;
import com.afrozaar.wordpress.wpapi.v2.exception.PageNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.exception.ParsedRestException;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.exception.TermNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Media;
import com.afrozaar.wordpress.wpapi.v2.model.Page;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostMeta;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.model.Taxonomy;
import com.afrozaar.wordpress.wpapi.v2.model.Term;
import com.afrozaar.wordpress.wpapi.v2.model.User;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ContentBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ExcerptBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.MediaBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PageBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TermBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.UserBuilder;
import com.afrozaar.wordpress.wpapi.v2.request.Request;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.afrozaar.wordpress.wpapi.v2.util.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.util.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.util.Two;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import org.apache.commons.lang.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author johan
 */
public class ClientLiveTest {

    private static final Logger LOG = LoggerFactory.getLogger(ClientLiveTest.class);

    static String yamlConfig;
    static ClientConfig clientConfig;
    static Wordpress client;

    @BeforeClass
    public static void init() throws UnknownHostException {
        yamlConfig = String.format("/config/%s-test.yaml", InetAddress.getLocalHost().getHostName());
        clientConfig = ClientConfig.load(ClientConfig.class.getResourceAsStream(yamlConfig));
        client = ClientFactory.fromConfig(clientConfig);
    }

    @Test
    public void posts() {
        final String EXPECTED = String.format("%s%s/posts", clientConfig.getWordpress().getBaseUrl(), Client.CONTEXT);

        final PagedResponse<Post> postPagedResponse = client.search(Posts.list());

        postPagedResponse.debug();

        assertThat(postPagedResponse.hasNext()).isTrue();
        assertThat(postPagedResponse.getPrevious().isPresent()).isFalse();
        assertThat(postPagedResponse.getSelf()).isEqualTo(EXPECTED);

        PagedResponse<Post> response = client.traverse(postPagedResponse, PagedResponse.NEXT);

        response.debug();

        while (response.hasNext()) {
            response = client.traverse(response, PagedResponse.NEXT);
            response.debug();
        }

    }

    @Test
    public void testGetPost() throws PostCreateException {
        final Post createdPost = client.createPost(newTestPostWithRandomData(), PostStatus.publish);

        final Post post = client.getPost(createdPost.getId());

        client.deletePost(createdPost); // cleanup

        assertThat(post).isNotNull();
    }

    @Test
    public void searchWithFilterParametersForInvalidAuthor_shouldReturnEmptyList() {

        // given
        SearchRequest<Post> search = SearchRequest.Builder.aSearchRequest(Post.class).withParam("filter[author]", "999").build();

        // when
        final PagedResponse<Post> postPagedResponse = client.search(search);

        // then
        assertThat(postPagedResponse.getList()).isEmpty();
    }

    @Test
    public void searchWithFilterParametersForValidAuthor_shouldReturnPopulatedList() {
        // given
        SearchRequest<Post> search = SearchRequest.Builder.aSearchRequest(Post.class).withParam("filter[author]", "1").build();

        // when
        final PagedResponse<Post> postPagedResponse = client.search(search);

        // then
        assertThat(postPagedResponse.getList()).isNotEmpty();
    }

    @Test
    public void searchForMetaKey() throws PostCreateException {

        final Two<Post, PostMeta> postWithMeta = newTestPostWithRandomDataWithMeta();

        final PagedResponse<Post> response = client.search(SearchRequest.Builder.aSearchRequest(Post.class).withParam("filter[meta_key]", postWithMeta.b.getKey()).build());

        client.deletePost(postWithMeta.a);

        assertThat(response.getList()).isNotEmpty().hasSize(1);
    }

    @Test
    @Ignore // this is for documentation purpose only
    public void searchForPostsNotHavingAParticularMetaKey() throws PostCreateException {

        final PagedResponse<Post> response = client.search(SearchRequest.Builder.aSearchRequest(Post.class)
                .withUri(Request.POSTS)
                .withParam("filter[meta_key]", "baobab_indexed")
                .withParam("filter[meta_compare]", "NOT EXISTS") //RestTemplate takes care of escaping values ('space' -> '%20')
                .build());

        // this request using curl/httpie:
        // http --auth 'username:wordpress!' http://myhost/wp-json/wp/v2/posts?filter[meta_key]=baobab_indexed&filter[meta_compare]=NOT%20EXISTS
    }

    @Test
    public void createPostTestWithSufficientData_mustNotFailWithException() throws PostCreateException {

        final String expectedTitle = "Hello, World!";
        final String expectedExcerpt = "This is...";
        final String expectedContent = "<p>This is the sandbox</p>\n";

        final Post post = PostBuilder.aPost().withTitle(TitleBuilder.aTitle().withRendered(expectedTitle).build())
                .withExcerpt(ExcerptBuilder.anExcerpt().withRendered(expectedExcerpt).build())
                .withContent(ContentBuilder.aContent().withRendered(expectedContent).build())
                .build();

        final Post createdPost = client.createPost(post, PostStatus.publish);

        assertThat(createdPost).isNotNull();
        assertThat(createdPost.getId()).isNotNull();
        assertThat(createdPost.getTitle().getRendered()).isEqualTo(expectedTitle);
        assertThat(createdPost.getContent().getRendered()).isEqualTo(expectedContent);

        LOG.debug("created post:\n{}", createdPost);
    }

    @Test
    public void updatePostFields() throws PostCreateException {
        final Post post = newTestPostWithRandomData();

        final Post createdPost = client.createPost(post, PostStatus.publish);
        final String createdContent = createdPost.getContent().getRendered();
        final String createdExcerpt = createdPost.getExcerpt().getRendered();

        createdPost.getContent().setRendered(RandomStringUtils.randomAlphabetic(50));
        createdPost.getExcerpt().setRendered(RandomStringUtils.randomAlphabetic(50));

        final Post updatedPost = client.updatePost(createdPost);

        client.deletePost(updatedPost); // cleanup before testing in case test fails.

        final String updatedContent = updatedPost.getContent().getRendered();
        final String updatedExcerpt = updatedPost.getExcerpt().getRendered();

        assertThat(updatedContent).isNotEqualTo(createdContent);
        assertThat(updatedExcerpt).isNotEqualTo(createdExcerpt);
    }

    @Test
    public void testCreateMedia() throws WpApiParsedException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);
        Media media = newRandomMedia(post);

        try {
            Resource resource = new ClassPathResource("/bin/gradient_colormap.jpg");
            final Media createdMedia = client.createMedia(media, resource);
            LOG.debug("created media: {}", createdMedia);
            post.setFeaturedImage(createdMedia.getId());
            client.updatePostField(post.getId(),"featured_image", createdMedia.getId());
        } catch (HttpServerErrorException e) {
            LOG.error("Error: {}", e.getResponseBodyAsString(), e);
        } finally {
            client.deletePost(post);
        }
    }

    @Test
    public void getMediaItem() throws WpApiParsedException {

        final Two<Post, Media> postWithMedia = newTestPostWithMedia();

        final Media media = client.getMedia(postWithMedia.b.getId());

        client.deletePost(postWithMedia.a);

        // TODO: 2015/12/10 use when it becomes available: client.deleteMedia(postWithMedia.b);

        assertThat(media).isNotNull();

        LOG.debug("Media: {}", media);
    }

    @Test
    public void getMediaItems() {
        final List<Media> medias = client.getMedia();

        assertThat(medias).isNotNull().isNotEmpty();

        LOG.debug("Media: {}", medias);
    }

    @Test
    public void testDeleteMedia() throws WpApiParsedException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);
        Media media = client.createMedia(newRandomMedia(post), new ClassPathResource("/bin/gradient_colormap.jpg"));
        Media media2 = client.getMedia(media.getId());

        //Check the response code is 2xx successful when deleted.
        assertThat(client.deleteMedia(media2, true)).isTrue();
        client.deletePost(post);

        //TODO: Double check that the article does not exist anymore
    }

    @Test
    public void testUpdateMedia() throws WpApiParsedException {

        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);

        final Media media = client.createMedia(newRandomMedia(post), new ClassPathResource("/bin/gradient_colormap.jpg"));

        media.setDescription("JUnit Description");

        Media updatedMedia = client.updateMedia(media);

        assertThat(updatedMedia.getDescription()).isEqualTo(media.getDescription());

        client.deleteMedia(updatedMedia, true);
        client.deletePost(post);
    }

    @Test
    public void getPostMetas() throws PostCreateException {
        // given
        final Two<Post, PostMeta> postWithMeta = newTestPostWithRandomDataWithMeta();

        //when
        final List<PostMeta> postMetas = client.getPostMetas(postWithMeta.a.getId());

        client.deletePost(postWithMeta.a); // cleanup

        // then
        assertThat(postMetas).isNotNull();
        assertThat(postMetas).isNotEmpty();
    }

    @Test
    public void getPostMeta() throws PostCreateException {

        final Two<Post, PostMeta> postWithMeta = newTestPostWithRandomDataWithMeta();

        final PostMeta postMeta = client.getPostMeta(postWithMeta.a.getId(), postWithMeta.b.getId());

        assertThat(postMeta).isNotNull();

        LOG.debug("postMeta: {}", postMeta);
    }

    @Test
    public void createPostMeta() throws PostCreateException {
        final Post createdPost = client.createPost(newTestPostWithRandomData(), PostStatus.publish);

        final String key = RandomStringUtils.randomAlphabetic(5);
        final String value = RandomStringUtils.randomAlphabetic(5);

        final PostMeta createdMeta = client.createMeta(createdPost.getId(), key, value);

        client.deletePost(createdPost); //cleanup

        assertThat(createdMeta).isNotNull();
        assertThat(createdMeta.getKey()).isEqualTo(key);
        assertThat(createdMeta.getValue()).isEqualTo(value);
    }

    @Test
    public void updatePostMeta() throws PostCreateException {
        Post post = newTestPostWithRandomData();
        final Post createdPost = client.createPost(post, PostStatus.publish);

        final String key = RandomStringUtils.randomAlphabetic(5);
        final String value = RandomStringUtils.randomAlphabetic(5);
        final String key2 = RandomStringUtils.randomAlphabetic(5);
        final String value2 = RandomStringUtils.randomAlphabetic(5);

        final PostMeta createdMeta = client.createMeta(createdPost.getId(), key, value);

        final PostMeta updatedMeta = client.updatePostMeta(createdPost.getId(), createdMeta.getId(), key2, value2);

        assertThat(updatedMeta.getId()).isEqualTo(createdMeta.getId());
        assertThat(updatedMeta.getKey()).isEqualTo(key2);
        assertThat(updatedMeta.getValue()).isEqualTo(value2);
        assertThat(updatedMeta.getKey()).isNotEqualTo(createdMeta.getKey());
        assertThat(updatedMeta.getValue()).isNotEqualTo(createdMeta.getValue());

        final Post deletedPost = client.deletePost(createdPost);

        assertThat(deletedPost.getId()).isEqualTo(post.getId());
    }

    @Test
    public void testUpdatePostMetaValue() throws PostCreateException {
        Post post = newTestPostWithRandomData();
        final Post createdPost = client.createPost(post, PostStatus.publish);

        final String key = RandomStringUtils.randomAlphabetic(5);
        final String value = RandomStringUtils.randomAlphabetic(5);
        final String value2 = RandomStringUtils.randomAlphabetic(5);

        final PostMeta createdMeta = client.createMeta(createdPost.getId(), key, value);

        final PostMeta updatedMeta = client.updatePostMetaValue(createdPost.getId(), createdMeta.getId(), value2);

        assertThat(updatedMeta.getId()).isEqualTo(createdMeta.getId());
        assertThat(updatedMeta.getKey()).isEqualTo(key);
        assertThat(updatedMeta.getValue()).isEqualTo(value2);
        assertThat(updatedMeta.getValue()).isNotEqualTo(createdMeta.getValue());

        final Post deletedPost = client.deletePost(createdPost);

        assertThat(deletedPost.getId()).isEqualTo(post.getId());
    }

    @Test
    public void deletePostMeta() throws PostCreateException {
        Post post = newTestPostWithRandomData();
        final Post createdPost = client.createPost(post, PostStatus.publish);

        final String key = RandomStringUtils.randomAlphabetic(5);
        final String value = RandomStringUtils.randomAlphabetic(5);

        final PostMeta createdMeta = client.createMeta(createdPost.getId(), key, value);

        final boolean deleted = client.deletePostMeta(createdPost.getId(), createdMeta.getId(), true);

        assertThat(deleted).isTrue();
    }

    @Test
    public void testGetTaxonomies() {
        final List<Taxonomy> taxonomies = client.getTaxonomies();

        assertThat(taxonomies).isNotNull().isNotEmpty();

        taxonomies.forEach(taxonomy -> LOG.debug("taxonomy: {} - {}", taxonomy.getSlug(), taxonomy.getName()));
    }

    @Test
    public void testGetTaxonomyCategories() {
        final Taxonomy taxCategory = client.getTaxonomy(CATEGORY);

        assertThat(taxCategory).isNotNull();

        LOG.debug("taxCategory: {}", taxCategory);
    }

    @Test
    public void testGetTags() {
        List<Term> tags = client.getTags();
        assertThat(tags).isNotNull().isNotEmpty();
        assertThat(tags.size()).isGreaterThan(0);
        LOG.debug("total tag terms: {}", tags.size());
    }

    @Test
    public void testGetCategories() {
        List<Term> categories = client.getCategories();
        assertThat(categories).isNotNull().isNotEmpty();
        assertThat(categories.size()).isGreaterThan(0);
        LOG.debug("total category terms: {}", categories.size());
    }

    @Test
    public void testCreateCategory() throws WpApiParsedException {

        final String expectedName = RandomStringUtils.randomAlphabetic(5);
        final String expectedDescription = RandomStringUtils.randomAlphabetic(10);

        final Term createdCategory = client.createCategory(TermBuilder.aTerm()
                .withName(expectedName)
                .withDescription(expectedDescription).build());

        client.deleteCategory(createdCategory);

        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory.getName()).isEqualTo(expectedName);
        assertThat(createdCategory.getDescription()).isEqualTo(expectedDescription);

    }

    @Test(expected = WpApiParsedException.class)
    public void testCreateSameTaxonomyTagManyTimes() throws WpApiParsedException {

        final Term build = TermBuilder.aTerm().withName("J-Unit").build();

        client.createTerm(Taxonomies.TAG, build);
        client.createTerm(Taxonomies.TAG, build);
    }

    @Test(expected = TermNotFoundException.class)
    public void testGetTaxonomyCategoryNotFound_mustThrowTermNotFoundException() throws TermNotFoundException {
        final Term term = client.getTerm(CATEGORY, 9999L);
        failBecauseExceptionWasNotThrown(TermNotFoundException.class);
    }

    @Test
    public void testCreateTaxonomyCategoryHierarchical() throws WpApiParsedException {
        final Term rootTerm = client.createCategory(TermBuilder.aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20)).build());

        LOG.debug("rootTerm: {}", rootTerm);

        final Term child1 = client.createCategory(TermBuilder.aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withParentId(rootTerm.getId()).build());
        final Term child2 = client.createCategory(TermBuilder.aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withParentId(rootTerm.getId()).build());
        final Term child3 = client.createCategory(TermBuilder.aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withParentId(rootTerm.getId()).build());
        final Term child4 = client.createCategory(TermBuilder.aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withParentId(rootTerm.getId()).build());
        final Term child41 = client.createCategory(TermBuilder.aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withParentId(child4.getId()).build());
        final Term child42 = client.createCategory(TermBuilder.aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withParentId(child4.getId()).build());

        assertThat(child42.getParentId()).isEqualTo(child4.getId());
        assertThat(child41.getParentId()).isEqualTo(child4.getId());
        assertThat(child4.getParentId()).isEqualTo(rootTerm.getId());
        assertThat(child3.getParentId()).isEqualTo(rootTerm.getId());
        assertThat(child2.getParentId()).isEqualTo(rootTerm.getId());
        assertThat(child1.getParentId()).isEqualTo(rootTerm.getId());
        assertThat(rootTerm.getParentId()).isEqualTo(0L); // new root term will always have 0 as parent.

        // cleanup
        client.deleteCategories(child41, child42, child4, child3, child2, child1, rootTerm);
    }

    @Test
    public void testCreateTaxonomyTag() throws WpApiParsedException {

        String expectedName = RandomStringUtils.randomAlphabetic(3);
        String expectedDescription = RandomStringUtils.randomAlphabetic(5);
        Term tag = TermBuilder.aTerm()
                .withDescription(expectedDescription)
                .withName(expectedName)
                .withTaxonomySlug("post_tag")
                .build();

        final Term createdTag = client.createTag(tag);
        client.deleteTag(createdTag);

        assertThat(createdTag).isNotNull();
        assertThat(createdTag.getName()).isEqualTo(expectedName);
        assertThat(createdTag.getDescription()).isEqualTo(expectedDescription);
    }

    @Test(expected = TermNotFoundException.class)
    public void testDeleteTaxonomyTag() throws WpApiParsedException {
        String expectedName = RandomStringUtils.randomAlphabetic(3);
        String expectedDescription = RandomStringUtils.randomAlphabetic(5);
        Term tag = TermBuilder.aTerm()
                .withDescription(expectedDescription)
                .withName(expectedName)
                .withTaxonomySlug("post_tag")
                .build();

        final Term createdTag = client.createTag(tag);
        final Term deletedTerm = client.deleteTag(createdTag);

        assertThat(deletedTerm).isNotNull();

        client.getTag(createdTag.getId());

        Assertions.failBecauseExceptionWasNotThrown(TermNotFoundException.class);
    }

    @Test
    public void testUpdateTaxonomyTag() throws WpApiParsedException {
        Term tag = TermBuilder.aTerm()
                .withDescription(RandomStringUtils.randomAlphabetic(5))
                .withName(RandomStringUtils.randomAlphabetic(3))
                .withTaxonomySlug("post_tag")
                .build();

        final Term createdTag = client.createTag(tag);

        final String expectedDescription = RandomStringUtils.randomAlphabetic(10);
        final String expectedName = RandomStringUtils.randomAlphabetic(10);
        createdTag.setDescription(expectedDescription);
        createdTag.setName(expectedName);

        final Term updatedTerm = client.updateTag(createdTag);
        client.deleteTag(updatedTerm);

        assertThat(updatedTerm.getDescription()).isEqualTo(expectedDescription);
        assertThat(updatedTerm.getName()).isEqualTo(expectedName);
    }

    @Test
    public void testCreatePostTagTerms() throws WpApiParsedException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);

        Term tagTerm = TermBuilder.aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build();

        final Term postTerm;
        try {
            postTerm = client.createPostTag(post, tagTerm);
        } catch (HttpClientErrorException e) {
            throw WpApiParsedException.of(ParsedRestException.of(e));
        }

        assertThat(postTerm).isNotNull();

        LOG.debug("created post tag: {}", postTerm);
    }

    @Test
    public void testGetPostTagTerms() throws WpApiParsedException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);

        client.createPostTag(post, TermBuilder.aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());
        client.createPostTag(post, TermBuilder.aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());
        client.createPostTag(post, TermBuilder.aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());
        client.createPostTag(post, TermBuilder.aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());
        client.createPostTag(post, TermBuilder.aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());

        final List<Term> postTags = client.getPostTags(post);

        client.deletePost(post); // cleanup

        assertThat(postTags).isNotNull().isNotEmpty().hasSize(5);
    }

    @Test(expected = WpApiParsedException.class)
    public void testDeletePostTagTerm() throws WpApiParsedException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);
        final Term postTerm = client.createPostTag(post, TermBuilder.aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());

        client.deletePost(post);
        final Term deletedTerm = client.deletePostTag(post, postTerm, true);

        assertThat(postTerm).isNotNull();
        assertThat(deletedTerm).isNotNull();

        final Term term = client.getPostTag(post, postTerm);

        failBecauseExceptionWasNotThrown(WpApiParsedException.class);
    }

    @Test
    public void testPagedPostTagTerms() throws WpApiParsedException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);

        final int limit = 50;

        IntStream.iterate(0, idx -> idx + 1).limit(limit).forEach(idx -> {
            try {
                client.createPostTag(post, TermBuilder.aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());
            } catch (WpApiParsedException e) {
                LOG.error("Error ", e);
            }
        });

        final List<Term> postTags = client.getPostTags(post);

        // cleanup
        for (Term term : postTags) {
            client.deletePostTag(post, term, true);
        }

        client.deletePost(post);

        assertThat(postTags).isNotNull().hasSize(limit);
    }

    @Test
    public void testCreatePage() {
        Page page = newPageWithRandomData();
        final Page createdPage = client.createPage(page, PostStatus.publish);

        assertThat(createdPage).isNotNull();
    }

    @Test
    public void testDeletePageTrashOnly() {
        Page page = newPageWithRandomData();
        final Page createdPage = client.createPage(page, PostStatus.publish);
        assertThat(createdPage).isNotNull();
        final Page deletedPage = client.deletePage(createdPage);
        assertThat(deletedPage).isNotNull();

        final Page trashedPage = client.getPage(createdPage.getId(), "edit");
        assertThat(trashedPage).isNotNull();
        LOG.debug("Trashed page: {}", trashedPage);
        assertThat(trashedPage.getStatus()).isEqualTo("trash");

    }

    @Test(expected = PageNotFoundException.class)
    public void testDeletePage() throws PageNotFoundException {
        Page page = newPageWithRandomData();
        final Page createdPage = client.createPage(page, PostStatus.publish);

        assertThat(createdPage).isNotNull();

        final Page deletedPage = client.deletePage(createdPage, true);

        assertThat(deletedPage).isNotNull();

        final Page pageNotFound = client.getPage(createdPage.getId());

        failBecauseExceptionWasNotThrown(PageNotFoundException.class);
    }

    @Test
    public void testUpdatePage() {
        final Page page = newPageWithRandomData();
        final Page createdPage = client.createPage(page, PostStatus.publish);

        createdPage.getContent().setRaw(RandomStringUtils.randomAlphabetic(60));

        final Page updatedPage = client.updatePage(createdPage);

        client.deletePage(updatedPage, true); // cleanup

        assertThat(updatedPage.getContent().getRendered()).isNotEqualTo(createdPage.getContent().getRendered());
    }

    @Test
    public void testGetUsers() {
        List<User> users = client.getUsers();

        assertThat(users).isNotEmpty();

        LOG.debug("Got {} users.", users.size());

        final Optional<User> found = users.stream().filter(user -> 1 == user.getId()).findFirst();
        assertThat(found).isPresent();
        assertThat(found.get().getRoles()).contains("administrator");
    }

    @Test
    public void testCreateUser() {
        User userRequest = UserBuilder.aUser()
                .withFirstName(RandomStringUtils.randomAlphabetic(4))
                .withLastName(RandomStringUtils.randomAlphabetic(7))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withEmail(String.format("%s@%s.test", RandomStringUtils.randomAlphabetic(4), RandomStringUtils.randomAlphabetic(3)))
                .withRoles(Arrays.asList("administrator"))
                .build();
        final User createdUser = client.createUser(userRequest, RandomStringUtils.randomAlphabetic(3), RandomStringUtils.randomAlphanumeric(3));

        LOG.debug("createdUser: {}", createdUser);

        assertThat(userRequest.getEmail()).isEqualTo(createdUser.getEmail());
        assertThat(userRequest.getDescription()).isEqualTo(createdUser.getDescription());
        assertThat(userRequest.getLastName()).isEqualTo(createdUser.getLastName());
        assertThat(createdUser.getId()).isNotNull();
    }

    @Test
    public void testGetUser() {
        User user = client.getUser(1L, Contexts.VIEW);
        assertThat(user.getEmail()).isEqualTo("docker@docker.dev");
        assertThat(user.getName()).isEqualTo("docker");
    }

    @Test
    @Ignore // currently having an Internal Server Error:
    // <b>Fatal error</b>:  Call to undefined function wp_delete_user() in <b>/var/www/wp-content/plugins/rest-api/lib/endpoints/class-wp-rest-users-controller.php</b> on line <b>357</b><br />
    public void testDeleteUser() {

        User user = UserBuilder.aUser()
                .withName(RandomStringUtils.randomAlphabetic(4))
                .withLastName(RandomStringUtils.randomAlphabetic(5))
                .withEmail(String.format("%s@%s.dev", RandomStringUtils.randomAlphabetic(3), RandomStringUtils.randomAlphabetic(3)))
                .build();
        String username = RandomStringUtils.randomAlphabetic(4);
        String password = RandomStringUtils.randomAlphanumeric(5);
        final User createdUser = client.createUser(user, username, password);

        client.getUsers().stream()
                .filter(u -> u.getId() != 1L)
                .forEach(u -> client.deleteUser(u));
    }

    @Ignore
    @Test
    public void testUpdateUser() {
        // TODO: create a test to update a user.
    }

    private Page newPageWithRandomData() {
        return PageBuilder.aPage()
                .withTitle(TitleBuilder.aTitle().withRaw(RandomStringUtils.randomAlphabetic(20)).build())
                .withExcerpt(ExcerptBuilder.anExcerpt().withRaw(RandomStringUtils.randomAlphabetic(10)).build())
                .withContent(ContentBuilder.aContent().withRaw(RandomStringUtils.randomAlphabetic(50)).build())
                .build();
    }

    private Post newTestPostWithRandomData() {
        return PostBuilder.aPost()
                .withContent(ContentBuilder.aContent().withRendered(RandomStringUtils.randomAlphabetic(20)).build())
                .withTitle(TitleBuilder.aTitle().withRendered(RandomStringUtils.randomAlphabetic(5)).build())
                .withExcerpt(ExcerptBuilder.anExcerpt().withRendered(RandomStringUtils.randomAlphabetic(5)).build())
                //                .withFeaturedImage(113L)
                .build();
    }

    private Two<Post, PostMeta> newTestPostWithRandomDataWithMeta() throws PostCreateException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);
        final PostMeta meta = client.createMeta(post.getId(), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(10));
        return Two.of(post, meta);
    }

    private Media newRandomMedia(Post post) {
        return MediaBuilder.aMedia()
                .withTitle(TitleBuilder.aTitle().withRendered(RandomStringUtils.randomAlphabetic(10)).build())
                .withCaption(RandomStringUtils.randomAlphabetic(50))
                .withAltText("image")
                .withDescription(RandomStringUtils.randomAscii(20))
                .withPost(post.getId())
                .build();
    }

    private Two<Post, Media> newTestPostWithMedia() throws WpApiParsedException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);

        Resource resource = new ClassPathResource("/bin/gradient_colormap.jpg");
        final Media mediaItem = client.createMedia(newRandomMedia(post), resource);

        return Two.of(post, mediaItem);
    }
}
