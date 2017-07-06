package com.afrozaar.wordpress.wpapi.v2.api;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nullable;

import java.util.Map;

/**
 * @author johan
 */
public interface CustomCalls {
    <T, B> ResponseEntity<T> doCustomExchange(String context, HttpMethod method, Class<T> typeRef, Object[] buildAndExpand,
                                                     Map<String, Object> queryParams, B body, @Nullable MediaType mediaType);
}
