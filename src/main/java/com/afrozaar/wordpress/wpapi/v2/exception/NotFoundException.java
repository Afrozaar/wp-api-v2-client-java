package com.afrozaar.wordpress.wpapi.v2.exception;

import org.springframework.web.client.HttpStatusCodeException;

public class NotFoundException extends WpApiParsedException {

    public NotFoundException(HttpStatusCodeException cause) {
        super(ParsedRestException.of(cause));
    }
}
