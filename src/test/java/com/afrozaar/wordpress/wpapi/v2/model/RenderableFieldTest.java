package com.afrozaar.wordpress.wpapi.v2.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;

/**
 * @author johan
 */
public class RenderableFieldTest {

    @Test
    public void RenderableFieldTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String json = "{\"raw\": \"<p>This is Raw!</p>\", \"rendered\": \"This is Raw!\"}";
        final Guid guid = mapper.readValue(json, Guid.class);

        Assertions.assertThat(guid.getRaw()).isEqualTo("<p>This is Raw!</p>");
        Assertions.assertThat(guid.getRendered()).isEqualTo("This is Raw!");

    }

}