package com.afrozaar.wordpress.wpapi.v2.exception;

import org.springframework.web.client.HttpStatusCodeException;

public class UsernameAlreadyExistsException extends WpApiParsedException {

    public UsernameAlreadyExistsException(HttpStatusCodeException cause) {
        super(ParsedRestException.of(cause));
    }
    

    public UsernameAlreadyExistsException(ParsedRestException parsed) {
        super(parsed);
    }


    public RuntimeException orRuntime(String runtimeMessage) throws UsernameAlreadyExistsException {
        if (ExceptionCodes.EXISTING_USER_LOGIN.equals(this.getCode())) {
            throw this;
        } else {
            throw new RuntimeException(runtimeMessage + ": " + super.toString(), super.getCause());
        }
    }
}
