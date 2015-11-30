package com.afrozaar.wordpress.wpapi.v2;

import static org.assertj.core.api.Assertions.assertThat;

import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.util.ClientFactory;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ClientTest {

    Logger LOG = LoggerFactory.getLogger(ClientTest.class);

    @Test
    public void foo() {
        assertThat("x").isEqualTo("x");
    }

    @Test
    public void posts() {


        Properties properties = new Properties();
        properties.put("baseUrl", "http://localhost/");
        properties.put("username", "username");
        properties.put("password", "password");

        LOG.debug("properties: {}", properties);

        Client client = ClientFactory.fromProperties(properties);

        final PagedResponse<Post> postPagedResponse = client.fetchPosts();

        assertThat(postPagedResponse.hasNext()).isTrue();
        assertThat(postPagedResponse.hasPrevious()).isFalse();
        assertThat(postPagedResponse.getPrevious().isPresent()).isFalse();


        // TODO:

    }
}