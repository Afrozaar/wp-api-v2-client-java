package com.afrozaar.wordpress.wpapi.v2;

import static org.assertj.core.api.Assertions.assertThat;

import com.afrozaar.wordpress.wpapi.v2.api.Posts;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostMeta;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ContentBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ExcerptBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.afrozaar.wordpress.wpapi.v2.util.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.util.ClientFactory;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

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

        final PagedResponse<Post> postPagedResponse = client.fetchPosts(SearchRequest.posts());

        postPagedResponse.debug();

        assertThat(postPagedResponse.hasNext()).isTrue();
        assertThat(postPagedResponse.getPrevious().isPresent()).isFalse();
        assertThat(postPagedResponse.getSelf()).isEqualTo(EXPECTED);

        PagedResponse<Post> response = client.get(postPagedResponse, Posts.next);

        response.debug();

        while (response.hasNext()) {
            response = client.get(response, Posts.next);
            response.debug();
        }

    }

    @Test
    public void testGetPost() {

        final Post post = client.getPost(3629);

        assertThat(post).isNotNull();

        LOG.debug("post = {}", post);
    }

    @Test
    public void searchWithFilterParametersForInvalidAuthor_shouldReturnEmptyList() {

        // given
        SearchRequest<Post> search = SearchRequest.Builder.<Post>aSearchRequest().withParam("filter[author]", "999").build();

        // when
        final PagedResponse<Post> postPagedResponse = client.fetchPosts(search);

        // then
        assertThat(postPagedResponse.getList()).isEmpty();
    }

    @Test
    public void searchWithFilterParametersForValidAuthor_shouldReturnPopulatedList() {
        // given
        SearchRequest<Post> search = SearchRequest.Builder.<Post>aSearchRequest().withParam("filter[author]", "1").build();

        // when
        final PagedResponse<Post> postPagedResponse = client.fetchPosts(search);

        // then
        assertThat(postPagedResponse.getList()).isNotEmpty();
    }

    @Test
    public void searchForMetaKey() {
        final PagedResponse<Post> response = client.fetchPosts(SearchRequest.Builder.<Post>aSearchRequest().withParam("filter[meta_key]", "pKlRn").build());
        assertThat(response.getList()).isNotEmpty().hasSize(1);
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

        final Post createdPost = client.createPost(post);

        assertThat(createdPost).isNotNull();
        assertThat(createdPost.getId()).isNotNull();
        assertThat(createdPost.getTitle().getRendered()).isEqualTo(expectedTitle);
        assertThat(createdPost.getContent().getRendered()).isEqualTo(expectedContent);

        LOG.debug("created post:\n{}", createdPost);
    }

    @Test
    public void getPostMetas() {
        final List<PostMeta> postMetas = client.getPostMetas(3746);

        LOG.debug("postMetas: {}", postMetas);
    }

    @Test
    public void getPostMeta() {
        final PostMeta postMeta = client.getPostMeta(3746, 11934);

        assertThat(postMeta).isNotNull();

        LOG.debug("postMeta: {}", postMeta);
    }

    @Test
    public void createPostMeta() {
        final PostMeta meta = client.createMeta(3746, RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAscii(10));

        LOG.debug("meta: {}", meta);
    }

    @Test
    public void updatePostMeta() throws PostCreateException {
        Post post = PostBuilder.aPost()
                .withContent(ContentBuilder.aContent().withRendered(RandomStringUtils.randomAlphabetic(20)).build())
                .withTitle(TitleBuilder.aTitle().withRendered(RandomStringUtils.randomAlphabetic(5)).build())
                .withExcerpt(ExcerptBuilder.anExcerpt().withRendered(RandomStringUtils.randomAlphabetic(5)).build())
                .build();
        final Post createdPost = client.createPost(post);

        final String key = RandomStringUtils.randomAlphabetic(5);
        final String value = RandomStringUtils.randomAlphabetic(5);
        final String key2 = RandomStringUtils.randomAlphabetic(5);
        final String value2 = RandomStringUtils.randomAlphabetic(5);

        final PostMeta createdMeta = client.createMeta(createdPost.getId(), key, value);

        final PostMeta updatedMeta = client.updatePostMeta(createdPost.getId(), createdMeta.getId().intValue(), key2, value2);

        assertThat(updatedMeta.getId()).isEqualTo(createdMeta.getId());
        assertThat(updatedMeta.getKey()).isEqualTo(key2);
        assertThat(updatedMeta.getValue()).isEqualTo(value2);
        assertThat(updatedMeta.getKey()).isNotEqualTo(createdMeta.getKey());
        assertThat(updatedMeta.getValue()).isNotEqualTo(createdMeta.getValue());

        final Post deletedPost = client.deletePost(createdPost);

        assertThat(deletedPost.getId()).isEqualTo(post.getId());
    }

    @Test
    public void deletePostMeta() throws PostCreateException {
        Post post = PostBuilder.aPost()
                .withContent(ContentBuilder.aContent().withRendered(RandomStringUtils.randomAlphabetic(20)).build())
                .withTitle(TitleBuilder.aTitle().withRendered(RandomStringUtils.randomAlphabetic(5)).build())
                .withExcerpt(ExcerptBuilder.anExcerpt().withRendered(RandomStringUtils.randomAlphabetic(5)).build())
                .build();
        final Post createdPost = client.createPost(post);

        final String key = RandomStringUtils.randomAlphabetic(5);
        final String value = RandomStringUtils.randomAlphabetic(5);

        final PostMeta createdMeta = client.createMeta(createdPost.getId(), key, value);

        final boolean deleted = client.deletePostMeta(createdPost.getId(), createdMeta.getId().intValue(), true);

        assertThat(deleted).isTrue();
    }
}
