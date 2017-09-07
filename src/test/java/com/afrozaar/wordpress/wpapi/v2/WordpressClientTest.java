package com.afrozaar.wordpress.wpapi.v2;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.fail;

import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.builder.MediaBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder;
import com.afrozaar.wordpress.wpapi.v2.util.FilenameWrapperByteArrayResource;

import org.springframework.core.io.ByteArrayResource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Map;

/**
 * @author johan
 */
public class WordpressClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(WordpressClientTest.class);
    Wordpress wordpress = ClientFactory.fromConfig(ClientConfig.of("http://localhost", "username", "password", false, true));

    @Test
    public void TestCreateClient() {
        System.out.println("media = " + wordpress.getMedia(10L));
    }

    @Test
    public void PostFieldProcessingTest() {

        Post post = PostBuilder.aPost()
                .withTitle(TitleBuilder.aTitle().withRendered("foobar").build())
                .withModifiedGmt(Instant.now().toString())
                .withContent(null)
                .withFormat("myformat")
                .build();

        final Map<String, Object> fieldMap = ((Client) wordpress).fieldsFrom(post);

        assertThat(fieldMap).containsOnlyKeys("title", "modified_gmt","format");

        LOG.debug("map to post: {}", fieldMap);

    }

    @Test(expected = NullPointerException.class)
    public void CreateMedia_MustFallOverWhenResourceDoesNotReturnFilename() throws WpApiParsedException {
        wordpress.createMedia(MediaBuilder.aMedia().build(), new ByteArrayResource(new byte[0]));
    }

    @Test
    public void CreateMedia_MustNotFallOverWhenResourceReturnsFileName() throws WpApiParsedException {



        try {
            wordpress.createMedia(MediaBuilder.aMedia().build(), new FilenameWrapperByteArrayResource(new byte[0], "myfile.png"));
        } catch (RuntimeException e) {
            if (e instanceof NullPointerException) {
                fail("Did not expect a nullpointer exception");
            }
        }
    }

}