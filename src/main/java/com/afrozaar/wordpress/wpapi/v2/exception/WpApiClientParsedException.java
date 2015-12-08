package com.afrozaar.wordpress.wpapi.v2.exception;

import static java.lang.String.format;

import org.springframework.web.client.HttpClientErrorException;

public class WpApiClientParsedException extends Exception {

    public WpApiClientParsedException(HttpClientErrorException cause) {
        this(format("%s - Response String: %s", cause.getMessage(), cause.getResponseBodyAsString()), cause);
    }

    public WpApiClientParsedException(String message, HttpClientErrorException cause) {
        super(message, cause);
    }
}
