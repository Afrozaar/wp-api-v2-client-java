package com.afrozaar.wordpress.wpapi.v2.exception;

import org.springframework.web.client.HttpClientErrorException;

public class PostCreateException extends WpApiParsedException {

    public PostCreateException(HttpClientErrorException cause) {
        super(ParsedRestException.of(cause));
    }
}
