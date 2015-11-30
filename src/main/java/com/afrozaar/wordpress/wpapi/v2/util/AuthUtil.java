package com.afrozaar.wordpress.wpapi.v2.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Base64;

public class AuthUtil {

    public static HttpHeaders createHeaders(String username, String password) {
        HttpHeaders httpHeaders = new HttpHeaders();
        final byte[] encodedAuth = Base64.getEncoder().encode((username + ":" + password).getBytes());
        //byte[] encodedAuth = Base64.encode((username + ":" + password).getBytes(Charset.forName("US-ASCII")));
        final String authHeader = "Basic " + new String(encodedAuth);
        httpHeaders.set("Authorization", authHeader);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }

    public static HttpEntity<String> basicAuth(String username, String password) {
        return new HttpEntity<>(createHeaders(username, password));
    }
}
