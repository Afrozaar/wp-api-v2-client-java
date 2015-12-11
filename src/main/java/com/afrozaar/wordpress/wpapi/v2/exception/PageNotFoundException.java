package com.afrozaar.wordpress.wpapi.v2.exception;

import org.springframework.web.client.HttpStatusCodeException;

public class PageNotFoundException extends WpApiParsedException {

    public PageNotFoundException(HttpStatusCodeException cause) {
        super(ParsedRestException.of(cause));
    }
}
