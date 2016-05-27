package com.afrozaar.wordpress.wpapi.v2.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.InputStream;
import java.util.Map;

/**
 * @author johan
 */
public class UserTest {

    @Test
    public void getCapabilities() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        final InputStream inputStream = UserTest.class.getResourceAsStream("/mock-resources/wp-json/wp/v2/user-edit-context.json");
        final User user = objectMapper.readValue(inputStream, User.class);

        final Map<String, Boolean> capabilities = user.getCapabilities();
        assertThat(capabilities.get("administrator")).isTrue();
        assertThat(capabilities).hasSize(62);
    }
}