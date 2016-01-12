package com.afrozaar.wordpress.wpapi.v2.exception;

import org.springframework.web.client.HttpStatusCodeException;

public class UserNotFoundException extends WpApiParsedException {

    public UserNotFoundException(HttpStatusCodeException cause) {
        super(ParsedRestException.of(cause));
    }
}
