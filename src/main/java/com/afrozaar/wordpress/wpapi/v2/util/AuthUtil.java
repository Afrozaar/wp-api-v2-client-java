package com.afrozaar.wordpress.wpapi.v2.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Base64;

public class AuthUtil {

    public static HttpHeaders createHeaders(String username, String password) {
        HttpHeaders httpHeaders = new HttpHeaders();
        final Two<String, String> authHeader = authTuple(username, password);
        httpHeaders.set(authHeader.a, authHeader.b);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }

    public static Two<String, String> authTuple(String username, String password) {
        final byte[] encodedAuth = Base64.getEncoder().encode((username + ":" + password).getBytes());
        return Two.of("Authorization", "Basic " + new String(encodedAuth));
    }

    public static HttpEntity<String> basicAuth(String username, String password) {
        return new HttpEntity<>(createHeaders(username, password));
    }
}
