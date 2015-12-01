package com.afrozaar.wordpress.wpapi.v2;

import static org.assertj.core.api.Assertions.assertThat;

import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.util.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.util.ClientFactory;

import com.google.common.collect.ImmutableMap;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ClientTest {

    static String yamlConfig;
    static ClientConfig clientConfig;

    Logger LOG = LoggerFactory.getLogger(ClientTest.class);

    @BeforeClass
    public static void init() throws UnknownHostException {
        yamlConfig = String.format("/config/%s-test.yaml", InetAddress.getLocalHost().getHostName());
        clientConfig = ClientConfig.load(ClientConfig.class.getResourceAsStream(yamlConfig));
    }


    @Test
    public void foo() {
        assertThat("x").isEqualTo("x");
    }

    @Test
    public void posts() {
        final Client client = ClientFactory.fromConfig(clientConfig);

        final String EXPECTED = String.format("%s%s/posts", clientConfig.getWordpress().getBaseUrl(), Client.CONTEXT);

        final PagedResponse<Post> postPagedResponse = client.fetchPosts();

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
    public void QueryParams() {
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(new MappingJackson2HttpMessageConverter()));

        final ImmutableMap<String, String> of = ImmutableMap.of("foo", "bar", "baz", "foobar");
        //restTemplate.exchange("http://localhost/", HttpMethod.GET, null, String.class, of);

        final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/");

        of.entrySet().stream().forEach(entry -> {
            builder.queryParam(entry.getKey(), entry.getValue());
        });

        final UriComponents uriComponents = builder.build();
        LOG.debug("queryParams: {}", uriComponents.getQueryParams());

        final MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromHttpUrl("http://foo.bar/?q=searchKey&v=moo").build().getQueryParams();

        LOG.debug("queryParams = {}", queryParams);
    }
}