package com.afrozaar.wordpress.wpapi.v2.exception;

import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class ParsedRestException {

    private static ObjectMapper mapper;

    private final String code;
    private final String errorMessage;
    private final Object data;
    private final HttpStatusCodeException cause;

    public ParsedRestException(HttpStatusCodeException cause, String code, String errorMessage, Object data) {
        this.cause = cause;
        this.code = code;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public static ParsedRestException of(HttpStatusCodeException cause) {
        try {
            Map exceptionMap = getMapper().readValue(cause.getResponseBodyAsByteArray(), Map.class);
            final String errorMessage = (String) exceptionMap.get("message");
            final String code = (String) exceptionMap.get("code");
            final Object data = exceptionMap.get("data");
            return new ParsedRestException(cause, code, errorMessage, data);
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

    private static ObjectMapper getMapper() {
        return mapper == null ? mapper = new ObjectMapper() : mapper;
    }
}
