package com.afrozaar.wordpress.wpapi.v2.exception;

import static java.lang.String.format;

import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WpApiParsedException extends Exception {

    private static ObjectMapper mapper;

    private final String code;
    private final String errorMessage;
    private final Object data;

    public WpApiParsedException(ParsedRestException parsed) {
        this(parsed.getCause().getMessage(), parsed.getCause(), parsed.getErrorMessage(), parsed.getCode(), parsed.getData());
    }

    private WpApiParsedException(String message, HttpStatusCodeException cause, String errorMessage, String code, Object data) {
        super(format("%s - %s", message, errorMessage), cause);
        this.code = code;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public static WpApiParsedException of(ParsedRestException parsed) {
        return new WpApiParsedException(parsed.getCause().getMessage(), parsed.getCause(), parsed.getErrorMessage(), parsed.getCode(), parsed.getData());
    }

    public static WpApiParsedException of(HttpStatusCodeException cause) {
        return of(ParsedRestException.of(cause));
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

    private static ObjectMapper getMapper() {
        return mapper == null ? mapper = new ObjectMapper() : mapper;
    }
}
