package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.Title;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.RandomStringUtils;

public class WordpressInstance implements WordpressInstanceInterface {
    ObjectMapper objectMapper = new ObjectMapper();

    private String randomTitleGenerator() {
        int i = (int) (Math.random() * 5) + 5;
        return RandomStringUtils.randomAlphabetic(i);
    }

    private Post[] generatePosts(int numOfPosts) {
        Post[] posts = new Post[numOfPosts];

        for (int i = 0; i < numOfPosts; i++) {
            Title title = new Title();
            title.setRendered(randomTitleGenerator());

            Post post = PostBuilder.aPost()
                    .withId(i)
                    .withTitle(title)
                    .build();

            posts[i] = post;
        }
        return posts;
    }

    @Override
    public byte[] getJsonObject(int numOfPosts) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(generatePosts(numOfPosts)));
        return objectMapper.writeValueAsString(generatePosts(numOfPosts)).getBytes();
    }

}
