package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.api.Contexts;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.exception.PostNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ContentBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.ExcerptBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder;
import com.afrozaar.wordpress.wpapi.v2.util.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.util.ClientFactory;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author johan
 */
public class GalleryQuoteTest {

    @Ignore
    @Test
    public void foo() throws PostCreateException, PostNotFoundException {
        Wordpress wordpress = ClientFactory.fromConfig(ClientConfig.of("http://docker.dev", "docker", "docker!", true));

        final Post post = wordpress.createPost(PostBuilder.aPost()
                .withTitle(TitleBuilder.aTitle().withRendered("Some Title").build())
                .withExcerpt(ExcerptBuilder.anExcerpt().withRendered("Raw Excerpt").build())
                .withContent(ContentBuilder.aContent(   ).withRendered("[gallery ids=\"10\"]").build())
                .build(), PostStatus.publish);

        final Post fetched = wordpress.getPost(post.getId(), Contexts.EDIT);

        System.out.println("raw = " + fetched.getContent().getRaw());
    }
}
