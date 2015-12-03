package com.afrozaar.wordpress.wpapi.v2.exception;

import static java.lang.String.format;

import org.springframework.web.client.HttpClientErrorException;

public class PostCreateException extends Exception {

    public PostCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostCreateException(HttpClientErrorException cause) {
        this(format("%s - Response String: %s", cause.getMessage(), cause.getResponseBodyAsString()), cause);
    }

}
