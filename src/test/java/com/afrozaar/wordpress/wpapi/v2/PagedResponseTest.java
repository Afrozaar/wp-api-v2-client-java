package com.afrozaar.wordpress.wpapi.v2;

import static org.assertj.core.api.Assertions.assertThat;

import com.afrozaar.wordpress.wpapi.v2.model.Post;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author johan
 */
public class PagedResponseTest {

    @Test
    public void pagedResponseTest() {

        List<Post> posts = Arrays.asList();

        PagedResponse<Post> postResponse = PagedResponse.Builder.<Post>aPagedResponse()
                .withSelf("http://example.com/self")
                .withNext(Optional.of("http://example.com/self?page=1"))
                .withPosts(posts)
                .build();

        assertThat(postResponse.hasPrevious()).isFalse();
        assertThat(postResponse.hasNext()).isTrue();
        assertThat(postResponse.getList()).isEmpty();



    }

}