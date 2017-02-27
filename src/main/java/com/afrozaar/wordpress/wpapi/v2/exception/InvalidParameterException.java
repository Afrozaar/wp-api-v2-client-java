package com.afrozaar.wordpress.wpapi.v2.exception;

public class InvalidParameterException extends WpApiParsedException {

    public InvalidParameterException(ParsedRestException parsed) {
        super(parsed);
    }
}
