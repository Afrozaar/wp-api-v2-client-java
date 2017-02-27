package com.afrozaar.wordpress.wpapi.v2.exception;

import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParsedRestException {

    private static final String FIELD_CODE = "code";
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_DATA = "data";
    private static final String FIELD_ADDITIONAL_ERRORS = "additional_errors";

    private static ObjectMapper mapper;

    private final String code;
    private final String errorMessage;
    private final Object data;
    private final HttpStatusCodeException cause;
    private final Collection<RestException> additionalErrors;

    private ParsedRestException(HttpStatusCodeException cause, String code, String errorMessage, Object data, Collection<RestException> additionalErrors) {
        this.cause = cause;
        this.code = code;
        this.errorMessage = errorMessage;
        this.data = data;
        this.additionalErrors = additionalErrors;
    }

    static class RestException {

        private final String code;
        private final String message;
        private final Object data;

        public static RestException fromRaw(Object mapObject) {
            final Map errorMap = (Map) mapObject;

            final String code = (String) errorMap.get(FIELD_CODE);
            final String message = (String) errorMap.get(FIELD_MESSAGE);
            final Object data = errorMap.get(FIELD_DATA);

            return new RestException(code, message, data);
        }

        RestException(String code, String message, Object data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public Object getData() {
            return data;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("RestException{");
            sb.append("code='").append(code).append('\'');
            sb.append(", message='").append(message).append('\'');
            sb.append(", data=").append(data);
            sb.append('}');
            return sb.toString();
        }
    }

    @SuppressWarnings("unchecked")
    private static final Function<Object, Collection<RestException>> ADDITIONAL_ERROR_PARSER = exceptionMap -> {
        Map em = (Map) exceptionMap;

        return Optional.ofNullable(em.get(FIELD_ADDITIONAL_ERRORS))
                .map(additionalErrors -> (List<RestException>) ((ArrayList) additionalErrors).stream()
                        .map(RestException::fromRaw)
                        .collect(Collectors.<RestException>toList()))
                .orElse(null);
    };

    public static ParsedRestException of(HttpStatusCodeException cause) {
        try {
            Map exceptionMap = getMapper().readValue(cause.getResponseBodyAsByteArray(), Map.class);
            final String errorMessage = (String) exceptionMap.get(FIELD_MESSAGE);
            final String code = (String) exceptionMap.get(FIELD_CODE);
            final Object data = exceptionMap.get(FIELD_DATA);

            return new ParsedRestException(cause, code, errorMessage, data, ADDITIONAL_ERROR_PARSER.apply(exceptionMap));
        } catch (IOException e) {
            throw new RuntimeException("Can not create ParsedRestException.", e);
        }
    }

    public String getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatusCodeException getCause() {
        return cause;
    }

    public Optional<Collection<RestException>> getAdditionalErrors() {
        return Optional.ofNullable(additionalErrors);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ParsedRestException{");
        sb.append("code='").append(code).append('\'');
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append(", data=").append(data);
        sb.append(", additionalErrors=").append(getAdditionalErrors());
        sb.append('}');
        return sb.toString();
    }

    private static ObjectMapper getMapper() {
        return mapper == null ? mapper = new ObjectMapper() : mapper;
    }
}
