package com.afrozaar.wordpress.wpapi.v2;

import static com.afrozaar.wordpress.wpapi.v2.util.ClientConfig.of;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.fail;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;
import com.afrozaar.wordpress.wpapi.v2.response.PagedResponse;
import com.afrozaar.wordpress.wpapi.v2.util.ClientFactory;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteSource;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

public class ClientWireMockTest {

    private Logger LOG = LoggerFactory.getLogger(ClientWireMockTest.class);
    private final String baseUrl = "http://localhost:8089";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void foo() {
        assertThat("x").isEqualTo("x");
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

    @Test
    public void simpleStubTest_shouldReturnOk() throws MalformedURLException {

        // given
        final String endpoint = "/some/thing";
        final String body = "foo baz\nbar";
        URL url = new URL("http://localhost:8089" + endpoint);

        stubFor(get(urlEqualTo(endpoint))
                .willReturn(aResponse()
                        .withBody(body)
                        .withStatus(200)));

        try {

            // when
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            // then
            final int responseCode = httpURLConnection.getResponseCode();
            assertThat(responseCode).isEqualTo(200);

            ByteSource byteSource = new ByteSource() {
                @Override
                public InputStream openStream() throws IOException {
                    return httpURLConnection.getInputStream();
                }
            };
            final String output = new String(byteSource.read());
            assertThat(output).isEqualTo(body);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void postsTest() throws InterruptedException, IOException {
        // given
        stubFor(get(urlEqualTo("/wp-json/wp/v2/posts"))
                .withHeader("Authorization", WireMock.matching("^Basic\\ .*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(contentFor("/wp-json/wp/v2/posts"))
                        .withHeader("Content-Type", "application/json")
                        .withHeader(Strings.HEADER_TOTAL_PAGES, "3")
                        .withHeader("Link", "<http://localhost:8089/wp-json/wp/v2/posts?page=2>; rel=\"next\"")));

        String username = "";
        String password = "";

        // when
        final Wordpress client = ClientFactory.fromConfig(of(baseUrl, username, password, true));
        final PagedResponse<Post> response = client.fetchPosts(SearchRequest.posts());
        final Optional<String> next = response.getNext();

        // then
        assertThat(next).isPresent().isEqualTo(Optional.of("http://localhost:8089/wp-json/wp/v2/posts?page=2"));
        assertThat(response.getPrevious()).isEmpty();
    }

    private byte[] contentFor(String endpoint) throws IOException {
        return new ByteSource() {
            @Override
            public InputStream openStream() throws IOException {
                return ClientWireMockTest.class.getResourceAsStream("/mock-resources" + endpoint + ".json");
            }
        }.read();
    }
}