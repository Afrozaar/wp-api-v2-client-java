package com.afrozaar.wordpress.wpapi.v2.exception;

import org.springframework.web.client.HttpClientErrorException;

public class TermNotFoundException extends WpApiClientParsedException {

    public TermNotFoundException(HttpClientErrorException cause) {
        super(cause);
    }

    public TermNotFoundException(String message, HttpClientErrorException cause) {
        super(message, cause);
    }
}
