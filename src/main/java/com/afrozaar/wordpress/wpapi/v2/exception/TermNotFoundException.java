package com.afrozaar.wordpress.wpapi.v2.exception;

import org.springframework.web.client.HttpClientErrorException;

public class TermNotFoundException extends WpApiParsedException {

    public TermNotFoundException(HttpClientErrorException cause) {
        super(ParsedRestException.of(cause));
    }
}
