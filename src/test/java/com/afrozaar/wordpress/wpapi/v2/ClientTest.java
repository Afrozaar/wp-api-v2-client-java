package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.util.ClientFactory;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Properties;

public class ClientTest {

    @Test
    public void foo() {
        Assertions.assertThat("x").isEqualTo("x");
    }

    @Test
    public void posts() {
        Properties properties = new Properties();
        properties.put("baseUrl", "http://localhost/");

        Client client = ClientFactory.fromProperties(properties);

        final PagedResponse<Post> postPagedResponse = client.fetchPosts();

        // TODO


    }
}