package com.afrozaar.wordpress.wpapi.v2.request;

import static org.assertj.core.api.Assertions.assertThat;

import com.afrozaar.wordpress.wpapi.v2.Client;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ContentBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;

import com.google.common.collect.ImmutableMap;

import org.springframework.web.util.UriComponentsBuilder;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

public class UpdatePostRequestTest {

    @Test
    public void createUpdateRequest() throws URISyntaxException {

        // given
        final Post post = PostBuilder.aPost()
                .withId(new Random().nextLong())
                .withContent(ContentBuilder.aContent()
                        .withRendered("foo bar").build())
                .build();

        final URI expected = new URI("http://localhost/wp-json/wp/v2/posts/" + post.getId());

        Client client = new Client("http://localhost", "username", "password", true, true);

        // when
        final URI result = UpdatePostRequest
                .forPost(post)
                .forHost(client, client.getContext()).buildAndExpand(post.getId())
                .toUri();

        System.out.println("result = " + result);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void createUpdateRequestWithPermalinkOff() throws URISyntaxException {

        // given
        final Post post = PostBuilder.aPost()
                .withId(new Random().nextLong())
                .withContent(ContentBuilder.aContent()
                        .withRendered("foo bar").build())
                .build();

        final URI expected = new URI("http://localhost?rest_route=/wp/v2/posts/" + post.getId());

        Client client = new Client("http://localhost", "username", "password", false, true);

        // when
        final URI result = UpdatePostRequest
                .forPost(post)
                .forHost(client, client.getContext()).buildAndExpand(post.getId())
                .toUri();

        System.out.println("result = " + result);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void sanityTest() {
        assertThat(UriComponentsBuilder.fromUriString("/foo/bar/{baz}").build().expand(ImmutableMap.of("baz", "123")).getPath()).isEqualTo("/foo/bar/123");
    }

}