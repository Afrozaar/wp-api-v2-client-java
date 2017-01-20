package com.afrozaar.wordpress.wpapi.v2.response;

import com.afrozaar.wordpress.wpapi.v2.model.Media;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility class that is able to handle the recent change where WP 4.7 now has {@code raw} and {@code rendered} fields for some display fields.
 * <p>
 * This class is temporary for the purpose of transitioning between 4.6 and 4.7.
 */
public final class CustomRenderableParser {

    private static final Logger LOG = LoggerFactory.getLogger(CustomRenderableParser.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Set<String> modifiableFields = new HashSet<>(Arrays.asList("description", "caption"));

    private CustomRenderableParser() {
        // Make other classes unable to instantiate.
    }

    public static Media parseMedia(String stringResponse) throws HttpClientErrorException {
        return parse(stringResponse, Media.class);
    }

    public static <T> T parse(String response, Class<T> clazz) {
        LOG.debug("Parsing response for {}", clazz);
        try {
            final JsonNode jsonNode = objectMapper.readValue(response, JsonNode.class);

            renderableFieldsFrom(jsonNode)
                    .filter(modifiableFields::contains)
                    .forEach(renderableField -> ((ObjectNode) jsonNode).put(renderableField, jsonNode.get(renderableField).get("raw").asText()));

            return objectMapper.convertValue(jsonNode, clazz);
        } catch (IOException e) {
            LOG.error("Error ", e);
            throw new HttpClientErrorException(HttpStatus.I_AM_A_TEAPOT, "There was an error parsing the response body.", response.getBytes(), StandardCharsets.UTF_8);
        }
    }

    private static Stream<String> renderableFieldsFrom(JsonNode jsonNode0) {
        final Function<JsonNode, Stream<String>> findRenderableFields = jsonNode -> stream(jsonNode)
                .filter(entry -> entry.getValue().fields().hasNext())
                .filter(entry -> stream(entry.getValue().fields())
                        .filter(field -> "raw".equals(field.getKey()) || "rendered".equals(field.getKey()))
                        .count() > 0)
                .map(Map.Entry::getKey);
        return findRenderableFields.apply(jsonNode0);
    }

    private static Stream<Map.Entry<String, JsonNode>> stream(JsonNode node) {
        return stream(node.fields());
    }

    private static Stream<Map.Entry<String, JsonNode>> stream(Iterator<Map.Entry<String, JsonNode>> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }
}
