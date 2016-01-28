package com.afrozaar.wordpress.wpapi.v2.exception;

import org.springframework.web.client.HttpStatusCodeException;

public class PostNotFoundException extends WpApiParsedException {

    public PostNotFoundException(HttpStatusCodeException cause) {
        super(ParsedRestException.of(cause));
    }
}
