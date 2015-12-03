package com.afrozaar.wordpress.wpapi.v2;

import static org.assertj.core.api.Assertions.assertThat;

import com.afrozaar.wordpress.wpapi.v2.api.Posts;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ContentBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.afrozaar.wordpress.wpapi.v2.util.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.util.ClientFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
    public void createPostTest() {

        Post post = PostBuilder.aPost()
                .withTitle(TitleBuilder.aTitle().withRendered("Hello, World!").build())
                .withContent(ContentBuilder.aContent().withRendered("<p>This is the sandbox</p>").build())
                .build();

        final Post createdPost = client.createPost(post);

        assertThat(createdPost).isNotNull();
        assertThat(createdPost.getId()).isNotNull();
    }
}
