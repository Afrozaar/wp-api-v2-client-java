package com.afrozaar.wordpress.wpapi.v2.util;

import com.afrozaar.wordpress.wpapi.v2.util.Tuples.Tuple2;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Base64;

public class AuthUtil {

    public static HttpHeaders createHeaders(String username, String password) {
        HttpHeaders httpHeaders = new HttpHeaders();
        final Tuple2<String, String> authHeader = authTuple(username, password);
        httpHeaders.set(authHeader.v1, authHeader.v2);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }

    public static Tuple2<String, String> authTuple(String username, String password) {
        final byte[] encodedAuth = Base64.getEncoder().encode((username + ":" + password).getBytes());
        return Tuples.tuple("Authorization", "Basic " + new String(encodedAuth));
    }

    public static HttpEntity<String> basicAuth(String username, String password) {
        return new HttpEntity<>(createHeaders(username, password));
    }
}
