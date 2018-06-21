package com.afrozaar.wordpress.wpapi.v2;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.fail;

import com.afrozaar.wordpress.wpapi.v2.api.Contexts;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.builder.MediaBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder;
import com.afrozaar.wordpress.wpapi.v2.util.FilenameWrapperByteArrayResource;

import org.springframework.core.io.ByteArrayResource;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

import java.time.Instant;
import java.util.Map;

/**
 * @author johan
 */
public class WordpressClientIT {

    private static final Logger LOG = LoggerFactory.getLogger(WordpressClientIT.class);

    @ClassRule
    @SuppressWarnings("unchecked")
    public static GenericContainer wordpressContainer = new GenericContainer<>("afrozaar/wordpress:latest").withExposedPorts(80).waitingFor(
            new LogMessageWaitStrategy().withRegEx("[\\s\\S]*INFO success: mysqld entered RUNNING state[\\s\\S]*"));

    private static Wordpress wordpress;

    @BeforeClass
    public static void setup() {
        wordpress = ClientFactory.fromConfig(ClientConfig.of("http://" + wordpressContainer.getContainerIpAddress() + ":" + wordpressContainer.getMappedPort(
                80), "username",
                                                             "password", false, true));
    }

    @Test
    @Ignore("don't know how this media is created")
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

        assertThat(fieldMap).containsOnlyKeys("title", "modified_gmt", "format", "author");

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

    @Test
    @Ignore("don't know how this is creawted")
    public void GetMediaWithViewContext_MustReturnMediaWithAvailableFieldsPopulated() {
        System.out.println("media = " + wordpress.getMedia(1033L, Contexts.VIEW));
    }

}