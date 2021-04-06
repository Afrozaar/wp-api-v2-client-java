package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.NotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * @author johan
 */
public interface CustomCalls {

    Object getCustom(String customPath, String context, Class clazz) throws NotFoundException;

    <T> List<T> getCustomObjects(String customPath, String context, Class clazz) throws NotFoundException;

    void putCustom(String customPath);

    <T, B> ResponseEntity<T> doCustomExchange(String context, HttpMethod method, Class<T> typeRef, Object[] buildAndExpand,
                                                     Map<String, Object> queryParams, B body, @Nullable MediaType mediaType);
}
