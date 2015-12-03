package com.afrozaar.wordpress.wpapi.v2;

import static org.assertj.core.api.Assertions.assertThat;

import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
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

    @BeforeClass
    public static void init() throws UnknownHostException {
        yamlConfig = String.format("/config/%s-test.yaml", InetAddress.getLocalHost().getHostName());
        clientConfig = ClientConfig.load(ClientConfig.class.getResourceAsStream(yamlConfig));
    }

    @Test
    public void posts() {
        final Client client = ClientFactory.fromConfig(clientConfig);

        final String EXPECTED = String.format("%s%s/posts", clientConfig.getWordpress().getBaseUrl(), Client.CONTEXT);

        final PagedResponse<Post> postPagedResponse = client.fetchPosts(SearchRequest.posts());

        postPagedResponse.debug();

        assertThat(postPagedResponse.hasNext()).isTrue();
        assertThat(postPagedResponse.getPrevious().isPresent()).isFalse();
        assertThat(postPagedResponse.getSelf()).isEqualTo(EXPECTED);

        PagedResponse<Post> next = client.get(postPagedResponse, resp -> resp.getNext().get());

        next.debug();

        while (next.hasNext()) {
            next = client.get(next, response -> response.getNext().get());
            next.debug();
        }

    }

    @Test
    public void testGetPost() {
        final Client client = ClientFactory.fromConfig(clientConfig);

        final Post post = client.getPost(3629);

        assertThat(post).isNotNull();

        LOG.debug("post = {}", post);
    }

    @Test
    public void searchWithFilterParametersForInvalidAuthor_shouldReturnEmptyList() {
        final Client client = ClientFactory.fromConfig(clientConfig);

        SearchRequest search = SearchRequest.Builder.aSearchRequest().withParam("filter[author]", "999").build();

        final PagedResponse<Post> postPagedResponse = client.fetchPosts(search);

        assertThat(postPagedResponse.getList()).isEmpty();
    }

    @Test
    public void searchWithFilterParametersForValidAuthor_shouldReturnPopulatedList() {

    }
}
