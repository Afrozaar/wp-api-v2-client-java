package com.afrozaar.wordpress.wpapi.v2.exception;

public class UserEmailAlreadyExistsException extends WpApiParsedException {

    public UserEmailAlreadyExistsException(ParsedRestException parsed) {
        super(parsed);
    }

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(UserEmailAlreadyExistsException.class);

}
