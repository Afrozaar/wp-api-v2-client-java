package com.afrozaar.wordpress.wpapi.v2.response;

import static java.util.Optional.ofNullable;

import com.afrozaar.wordpress.wpapi.v2.model.Media;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.function.Consumer;
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

    public static <T> T parse(ResponseEntity<String> response, Class<T> clazz) {
        return parse(response.getBody(), clazz);
    }

    public static <T> T parse(String response, Class<T> clazz) {
        LOG.debug("Parsing response for {}", clazz.getCanonicalName());

        try {
            final JsonNode jsonNode = objectMapper.readValue(response, JsonNode.class);

            if (clazz.isArray()) {
                jsonNode.iterator().forEachRemaining(transformNode);
            } else {
                transformNode.accept(jsonNode);
            }

            return objectMapper.convertValue(jsonNode, clazz);
        } catch (IOException e) {
            LOG.error("Error ", e);
            throw new HttpClientErrorException(HttpStatus.I_AM_A_TEAPOT, "There was an error parsing the response body.", response.getBytes(), StandardCharsets.UTF_8);
        }
    }

    private static final Function<Iterator<Map.Entry<String, JsonNode>>, Stream<Map.Entry<String, JsonNode>>> streamIterator = iterator -> StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);

    private static final Function<JsonNode, Stream<String>> findRenderableFields = jsonNode -> streamIterator.apply(jsonNode.fields())
            .filter(entry -> entry.getValue().fields().hasNext())
            .filter(entry -> streamIterator.apply(entry.getValue().fields())
                    .filter(field -> "raw".equals(field.getKey()) || "rendered".equals(field.getKey()))
                    .count() > 0)
            .map(Map.Entry::getKey);

    private static final Consumer<JsonNode> transformNode = node -> findRenderableFields.apply(node)
            .filter(modifiableFields::contains)
            .forEach(
                    renderableField -> ofNullable(node.get(renderableField).get("raw"))
                            .ifPresent(raw -> ((ObjectNode) node).put(renderableField, raw.asText()))
            );
}
