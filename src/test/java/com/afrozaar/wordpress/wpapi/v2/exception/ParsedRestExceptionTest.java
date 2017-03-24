package com.afrozaar.wordpress.wpapi.v2.exception;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.io.ByteSource;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public class ParsedRestExceptionTest {
    @Test
    public void testAdditionalError_MustHaveAdditionalErrors() throws Exception {

        byte[] errorWithAdditionalErrors = readResource.apply("/json/additional-errors-response.json");
        final ParsedRestException parsedRestExceptionWithAdditionalErrors = ParsedRestException.of(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "err", errorWithAdditionalErrors, StandardCharsets.UTF_8));

        assertThat(parsedRestExceptionWithAdditionalErrors.getAdditionalErrors()).isNotNull();

        assertThat(parsedRestExceptionWithAdditionalErrors.getAdditionalErrors())
                .isNotNull()
                .isNotEmpty()
                .extracting("code").contains("user_name", "user_email");
    }

    @Test
    public void testNormalError_MustNotHaveAdditionalErrors() {

        final byte[] normalError = readResource.apply("/json/error-response.json");
        final ParsedRestException parsedRestExceptionNormalError = ParsedRestException.of(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "err", normalError, StandardCharsets.UTF_8));

        assertThat(parsedRestExceptionNormalError.getAdditionalErrors()).isEmpty();
    }

    private final Function<String, byte[]> readResource = uri -> {
        try {
            return new ByteSource() {
                @Override
                public InputStream openStream() throws IOException {
                    return ParsedRestExceptionTest.class.getResourceAsStream(uri);
                }
            }.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

}