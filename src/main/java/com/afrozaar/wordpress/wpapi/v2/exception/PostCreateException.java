package com.afrozaar.wordpress.wpapi.v2.exception;

import org.springframework.web.client.HttpClientErrorException;

public class PostCreateException extends WpApiClientParsedException {

    public PostCreateException(HttpClientErrorException cause) {
        super(cause);
    }

    public PostCreateException(String message, HttpClientErrorException cause) {
        super(message, cause);
    }
}
