package com.afrozaar.wordpress.wpapi.v2.response;

import static org.assertj.core.api.Assertions.assertThat;

import com.afrozaar.wordpress.wpapi.v2.model.DeleteResponse;
import com.afrozaar.wordpress.wpapi.v2.model.Media;
import com.afrozaar.wordpress.wpapi.v2.model.Term;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomRenderableParserTest {

    @Test
    public void parse() throws Exception {

        // GIVEN
        String response_4_7 = getResourceAsString("/json/media-4.7.json");
        String response_4_6 = getResourceAsString("/json/media-4.6.json");

        // WHEN
        final Media parse = CustomRenderableParser.parseMedia(response_4_7);
        System.out.println("parse = " + parse);

        // WHEN
        final Media media = CustomRenderableParser.parseMedia(response_4_6);
        System.out.println("media = " + media);
    }

    private String getResourceAsString(String resource) throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(CustomRenderableParserTest.class.getResource(resource).toURI())).stream().collect(Collectors.joining("\n"));
    }

    @Test
    public void parseArrayOfMedia() throws Exception {
        // GIVEN
        String mediaArray = getResourceAsString("/json/multi-media-response.json");

        // WHEN
        final Media[] parse = CustomRenderableParser.parse(mediaArray, Media[].class);

        System.out.println("parse = " + Arrays.toString(parse));

        // THEN
        assertThat(parse[0].getCaption()).isEqualTo("YmWDXWlmDNyITKTplpvndMPyAWcdDdiWpoQZaXKDOwkCTBIQBf");
    }

    @Test
    public void parseDeleteTagResponse() throws IOException, URISyntaxException {
        // GIVEN
        String response = getResourceAsString("/json/delete-term-response.json");

        // WHEN
        final DeleteResponse<Term> deserialized = CustomRenderableParser.parseDeleteResponse(response, Term.class);

        Term term = deserialized.getPrevious();

        // THEN
        assertThat(term.getDescription()).isEqualTo("kAUst");
        assertThat(term.getName()).isEqualTo("TKA");
        assertThat(term.getTaxonomySlug()).isEqualTo("post_tag");

    }

}