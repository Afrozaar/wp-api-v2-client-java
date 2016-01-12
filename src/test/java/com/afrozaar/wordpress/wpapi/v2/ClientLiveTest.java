package com.afrozaar.wordpress.wpapi.v2;

import static com.afrozaar.wordpress.wpapi.v2.api.Taxonomies.CATEGORY;
import static com.afrozaar.wordpress.wpapi.v2.model.builder.ContentBuilder.aContent;
import static com.afrozaar.wordpress.wpapi.v2.model.builder.ExcerptBuilder.anExcerpt;
import static com.afrozaar.wordpress.wpapi.v2.model.builder.MediaBuilder.aMedia;
import static com.afrozaar.wordpress.wpapi.v2.model.builder.PageBuilder.aPage;
import static com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder.aPost;
import static com.afrozaar.wordpress.wpapi.v2.model.builder.TermBuilder.aTerm;
import static com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder.aTitle;
import static com.afrozaar.wordpress.wpapi.v2.model.builder.UserBuilder.aUser;
import static com.afrozaar.wordpress.wpapi.v2.request.SearchRequest.Builder.aSearchRequest;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.fail;

import com.afrozaar.wordpress.wpapi.v2.api.Contexts;
import com.afrozaar.wordpress.wpapi.v2.api.Posts;
import com.afrozaar.wordpress.wpapi.v2.exception.PageNotFoundException;
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
import com.afrozaar.wordpress.wpapi.v2.request.Request;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.afrozaar.wordpress.wpapi.v2.util.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.util.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.util.Two;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.HttpServerErrorException;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author johan
 */
public class ClientLiveTest {

    private static final Logger LOG = LoggerFactory.getLogger(ClientLiveTest.class);

    private static ClientConfig clientConfig;
    private static Wordpress client;

    @BeforeClass
    public static void init() throws IOException {
        clientConfig = resolveConfig();
        client = ClientFactory.fromConfig(clientConfig);
    }

    @Before
    public void cleanup() {
        final PagedResponse<Post> response = client.search(aSearchRequest(Post.class).build());
        for (Post post : response.getList()) {
            client.deletePost(post);
        }
    }

    private static ClientConfig resolveConfig() {
        try {
            Resource userResource = new ClassPathResource(String.format("/config/%s-test.yaml", InetAddress.getLocalHost().getHostName()));
            Resource resourceToUse = userResource.exists()
                    ? userResource
                    : new ClassPathResource("/config/docker-test.yaml");
            return ClientConfig.load(resourceToUse);
        } catch (IOException e) {
            throw new RuntimeException("Can not run tests without a configuration. Please ensure you have a valid configuration in ${project}/src/test/resources/config/<hostname>-test.yaml");
        }
    }

    @Test
    public void testTraverse() throws PostCreateException {

        for (int i = 0; i < 11; i++) {
            client.createPost(newTestPostWithRandomData(), PostStatus.publish);
        }
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

        try {
            final Post post = client.getPost(createdPost.getId());
            assertThat(post).isNotNull();
        } catch (com.afrozaar.wordpress.wpapi.v2.exception.PostNotFoundException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testSearchWithFilterParametersForInvalidAuthor_shouldReturnEmptyList() {

        // given
        SearchRequest<Post> search = aSearchRequest(Post.class).withParam("filter[author]", "999").build();

        // when
        final PagedResponse<Post> postPagedResponse = client.search(search);

        // then
        assertThat(postPagedResponse.getList()).isEmpty();
    }

    @Test
    public void testSearchWithFilterParametersForValidAuthor_shouldReturnPopulatedList() throws PostCreateException {
        client.createPost(newTestPostWithRandomData(), PostStatus.publish);
        // given
        SearchRequest<Post> search = aSearchRequest(Post.class).withParam("filter[author]", "1").build();

        // when
        final PagedResponse<Post> postPagedResponse = client.search(search);

        // then
        assertThat(postPagedResponse.getList()).isNotEmpty();
    }

    @Test
    public void testSearchForMetaKey() throws PostCreateException {

        final Two<Post, PostMeta> postWithMeta = newTestPostWithRandomDataWithMeta();

        final PagedResponse<Post> response = client.search(aSearchRequest(Post.class).withParam("filter[meta_key]", postWithMeta.b.getKey()).build());

        assertThat(response.getList()).isNotEmpty().hasSize(1);
    }

    @Test
    @Ignore // this is for documentation purpose only
    public void testSearchForPostsNotHavingAParticularMetaKey() throws PostCreateException {

        final PagedResponse<Post> response = client.search(aSearchRequest(Post.class)
                .withUri(Request.POSTS)
                .withParam("filter[meta_key]", "baobab_indexed")
                .withParam("filter[meta_compare]", "NOT EXISTS") //RestTemplate takes care of escaping values ('space' -> '%20')
                .build());

        // this request using curl/httpie:
        // http --auth 'username:wordpress!' http://myhost/wp-json/wp/v2/posts?filter[meta_key]=baobab_indexed&filter[meta_compare]=NOT%20EXISTS
    }

    @Test
    public void testCreatePostWithSufficientData_mustNotFailWithException() throws PostCreateException {

        final String expectedTitle = "Hello, World!";
        final String expectedExcerpt = "This is...";
        final String expectedContent = "<p>This is the sandbox</p>\n";

        final Post post = aPost().withTitle(aTitle().withRendered(expectedTitle).build())
                .withExcerpt(anExcerpt().withRendered(expectedExcerpt).build())
                .withContent(aContent().withRendered(expectedContent).build())
                .build();

        final Post createdPost = client.createPost(post, PostStatus.publish);

        assertThat(createdPost).isNotNull();
        assertThat(createdPost.getId()).isNotNull();
        assertThat(createdPost.getTitle().getRendered()).isEqualTo(expectedTitle);
        assertThat(createdPost.getContent().getRendered()).isEqualTo(expectedContent);

        LOG.debug("created post:\n{}", createdPost);
    }

    @Test
    public void testUpdatePost() throws PostCreateException {
        final Post post = newTestPostWithRandomData();

        final Post createdPost = client.createPost(post, PostStatus.publish);
        final String createdContent = createdPost.getContent().getRendered();
        final String createdExcerpt = createdPost.getExcerpt().getRendered();

        createdPost.getContent().setRendered(RandomStringUtils.randomAlphabetic(50));
        createdPost.getExcerpt().setRendered(RandomStringUtils.randomAlphabetic(50));

        final Post updatedPost = client.updatePost(createdPost);

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
            client.updatePostField(post.getId(), "featured_image", createdMedia.getId());
        } catch (HttpServerErrorException e) {
            LOG.error("Error: {}", e.getResponseBodyAsString(), e);
        }

        //TODO need an assert?
    }

    @Test
    public void tesGetMedia() throws WpApiParsedException {

        final Two<Post, Media> postWithMedia = newTestPostWithMedia();

        final Media media = client.getMedia(postWithMedia.b.getId());

        assertThat(media).isNotNull();

        LOG.debug("Media: {}", media);
    }

    @Test
    public void testGetMedia() throws WpApiParsedException {
        //TODO No idea why this is failing
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);
        client.createMedia(newRandomMedia(post), new ClassPathResource("/bin/gradient_colormap.jpg"));

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
    }

    @Test
    public void testUpdateMedia() throws WpApiParsedException {

        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);

        final Media media = client.createMedia(newRandomMedia(post), new ClassPathResource("/bin/gradient_colormap.jpg"));

        media.setDescription("JUnit Description");

        Media updatedMedia = client.updateMedia(media);

        assertThat(updatedMedia.getDescription()).isEqualTo(media.getDescription());
    }

    @Test
    public void testGetPostMetas() throws PostCreateException {
        // given
        final Two<Post, PostMeta> postWithMeta = newTestPostWithRandomDataWithMeta();

        //when
        final List<PostMeta> postMetas = client.getPostMetas(postWithMeta.a.getId());

        // then
        assertThat(postMetas).isNotNull();
        assertThat(postMetas).isNotEmpty();
    }

    @Test
    public void testGetPostMeta() throws PostCreateException {

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

        assertThat(createdMeta).isNotNull();
        assertThat(createdMeta.getKey()).isEqualTo(key);
        assertThat(createdMeta.getValue()).isEqualTo(value);
    }

    @Test
    public void testUpdatePostMeta() throws PostCreateException {
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
    }

    @Test
    public void testDeletePostMeta() throws PostCreateException {
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
        //TODO need to create tags first
        List<Term> tags = client.getTags();
        assertThat(tags).isNotNull().isNotEmpty();
        assertThat(tags.size()).isGreaterThan(0);
        LOG.debug("total tag terms: {}", tags.size());
    }

    @Test
    public void testGetCategories() {
        //TODO need to create categories first
        List<Term> categories = client.getCategories();
        assertThat(categories).isNotNull().isNotEmpty();
        assertThat(categories.size()).isGreaterThan(0);
        LOG.debug("total category terms: {}", categories.size());
    }

    @Test
    public void testCreateCategory() throws WpApiParsedException {

        final String expectedName = RandomStringUtils.randomAlphabetic(5);
        final String expectedDescription = RandomStringUtils.randomAlphabetic(10);

        final Term createdCategory = client.createCategory(aTerm()
                .withName(expectedName)
                .withDescription(expectedDescription).build());

        client.deleteCategory(createdCategory);

        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory.getName()).isEqualTo(expectedName);
        assertThat(createdCategory.getDescription()).isEqualTo(expectedDescription);

    }

    @Test(expected = WpApiParsedException.class)
    public void testCreateTagManyTimes() throws WpApiParsedException {

        final Term build = aTerm().withName("J-Unit").build();

        client.createTag(build);
        client.createTag(build);
    }

    @Test(expected = TermNotFoundException.class)
    public void testGetTaxonomyCategoryNotFound_mustThrowTermNotFoundException() throws TermNotFoundException {
        client.getTerm(CATEGORY, 9999L);
    }

    @Test
    public void testCreateTaxonomyCategoryHierarchical() throws WpApiParsedException {
        final Term rootTerm = client.createCategory(aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20)).build());

        LOG.debug("rootTerm: {}", rootTerm);

        final Term child1 = client.createCategory(aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withParentId(rootTerm.getId()).build());
        final Term child2 = client.createCategory(aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withParentId(rootTerm.getId()).build());
        final Term child3 = client.createCategory(aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withParentId(rootTerm.getId()).build());
        final Term child4 = client.createCategory(aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withParentId(rootTerm.getId()).build());
        final Term child41 = client.createCategory(aTerm()
                .withName(RandomStringUtils.randomAlphabetic(5))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withParentId(child4.getId()).build());
        final Term child42 = client.createCategory(aTerm()
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
        Term tag = aTerm()
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
        Term tag = aTerm()
                .withDescription(expectedDescription)
                .withName(expectedName)
                .withTaxonomySlug("post_tag")
                .build();

        final Term createdTag = client.createTag(tag);
        final Term deletedTerm = client.deleteTag(createdTag);

        assertThat(deletedTerm).isNotNull();

        assertThat(deletedTerm.getName()).isEqualTo(createdTag.getName());

        client.getTag(createdTag.getId());
    }

    @Test
    public void testUpdateTaxonomyTag() throws WpApiParsedException {
        Term tag = aTerm()
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

        Term tagTerm = aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build();

        final Term postTerm = client.createPostTag(post, tagTerm);

        assertThat(postTerm).isNotNull();

        LOG.debug("created post tag: {}", postTerm);
    }

    @Test
    public void testGetPostTagTerms() throws WpApiParsedException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);

        client.createPostTag(post, aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());
        client.createPostTag(post, aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());
        client.createPostTag(post, aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());
        client.createPostTag(post, aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());
        client.createPostTag(post, aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());

        final List<Term> postTags = client.getPostTags(post);

        client.deletePost(post); // cleanup

        assertThat(postTags).isNotNull().isNotEmpty().hasSize(5);
    }

    @Test(expected = WpApiParsedException.class)
    public void testDeletePostTagTerm() throws WpApiParsedException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);
        final Term postTerm = client.createPostTag(post, aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());

        client.deletePost(post);
        final Term deletedTerm = client.deletePostTag(post, postTerm, true);

        assertThat(postTerm).isNotNull();
        assertThat(deletedTerm).isNotNull();
        assertThat(deletedTerm.getName()).isEqualTo(postTerm.getName());

        client.getPostTag(post, postTerm);
    }

    @Test
    public void testGetPostTagsPaged() throws WpApiParsedException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);

        final int limit = 50;

        IntStream.iterate(0, idx -> idx + 1).limit(limit).forEach(idx -> {
            try {
                client.createPostTag(post, aTerm().withName(RandomStringUtils.randomAlphabetic(5)).build());
            } catch (WpApiParsedException e) {
                LOG.error("Error ", e);
            }
        });

        final List<Term> postTags = client.getPostTags(post);

        // cleanup
        for (Term term : postTags) {
            client.deletePostTag(post, term, true);
        }

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

        client.getPage(createdPage.getId());
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
        User userRequest = aUser()
                .withFirstName(RandomStringUtils.randomAlphabetic(4))
                .withLastName(RandomStringUtils.randomAlphabetic(7))
                .withDescription(RandomStringUtils.randomAlphabetic(20))
                .withEmail(String.format("%s@%s.test", RandomStringUtils.randomAlphabetic(4), RandomStringUtils.randomAlphabetic(3)))
                .withRoles(Collections.singletonList("administrator"))
                .build();
        final User createdUser = client.createUser(userRequest, RandomStringUtils.randomAlphabetic(3), RandomStringUtils.randomAlphanumeric(3));

        LOG.debug("createdUser: {}", createdUser);

        assertThat(userRequest.getEmail()).isEqualTo(createdUser.getEmail());
        assertThat(userRequest.getDescription()).isEqualTo(createdUser.getDescription());
        assertThat(userRequest.getFirstName()).isEqualTo(createdUser.getFirstName());
        assertThat(userRequest.getLastName()).isEqualTo(createdUser.getLastName());
        assertThat(createdUser.getId()).isNotNull();
    }

    @Test
    public void testGetUser() {
        try {
            User user = client.getUser(1L, Contexts.VIEW);
            assertThat(user.getEmail()).isEqualTo("docker@docker.dev");
            assertThat(user.getName()).isEqualTo("docker");
        } catch (com.afrozaar.wordpress.wpapi.v2.exception.UserNotFoundException e) {
            LOG.error("Error ", e);
            fail(e.toString());
        }
    }

    @Test
    public void testDeleteUser() {

        User user = aUser()
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

        try {
            final User user1 = client.getUser(createdUser.getId());
        } catch (com.afrozaar.wordpress.wpapi.v2.exception.UserNotFoundException e) {
            LOG.error("Error ", e);
        }
    }

    @Ignore
    @Test
    public void testUpdateUser() {
        // TODO: create a test to update a user.
    }

    private Page newPageWithRandomData() {
        return aPage()
                .withTitle(aTitle().withRaw(RandomStringUtils.randomAlphabetic(20)).build())
                .withExcerpt(anExcerpt().withRaw(RandomStringUtils.randomAlphabetic(10)).build())
                .withContent(aContent().withRaw(RandomStringUtils.randomAlphabetic(50)).build())
                .build();
    }

    private Post newTestPostWithRandomData() {
        return aPost()
                .withContent(aContent().withRendered(RandomStringUtils.randomAlphabetic(20)).build())
                .withTitle(aTitle().withRendered(RandomStringUtils.randomAlphabetic(5)).build())
                .withExcerpt(anExcerpt().withRendered(RandomStringUtils.randomAlphabetic(5)).build())
                //                .withFeaturedImage(113L)
                .build();
    }

    private Two<Post, PostMeta> newTestPostWithRandomDataWithMeta() throws PostCreateException {
        final Post post = client.createPost(newTestPostWithRandomData(), PostStatus.publish);
        final PostMeta meta = client.createMeta(post.getId(), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(10));
        return Two.of(post, meta);
    }

    private Media newRandomMedia(Post post) {
        return aMedia()
                .withTitle(aTitle().withRendered(RandomStringUtils.randomAlphabetic(10)).build())
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
