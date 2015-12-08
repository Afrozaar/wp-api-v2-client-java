package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostMeta;
import com.afrozaar.wordpress.wpapi.v2.model.builder.PostBuilder;
import com.afrozaar.wordpress.wpapi.v2.model.builder.TitleBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class WordpressMockGenerator implements IWordpressMockGenerator {
    private ObjectMapper objectMapper = new ObjectMapper();
    Random random = new Random(System.currentTimeMillis());

    private String getRandomTitle() {
        return RandomStringUtils.randomAlphabetic(random.nextInt(5));
    }

    private List<Post> getPosts(int numOfPosts) {
        List<Post> posts = new ArrayList<>();

        IntStream.range(0, numOfPosts).forEach(id -> {
            Post post = PostBuilder.aPost()
                    .withId(id)
                    .withTitle(TitleBuilder.aTitle().withRendered(getRandomTitle()).build())
                    .build();
            posts.add(post);
        });

        return posts;
    }

    private Post getPost() {

        return PostBuilder.aPost()
                .withId(0)
                .withTitle(TitleBuilder.aTitle().withRendered(getRandomTitle()).build())
                .build();

    }

    private List<PostMeta> getMeta(int numOfMetas) {

        List<PostMeta> metas = new ArrayList<>();

        IntStream.range(0, numOfMetas).forEach(id -> {
            PostMeta postMeta = new PostMeta();
            postMeta.setId((long) id);
            postMeta.setKey("key " + id);
            postMeta.setValue("value " + id);
            metas.add(postMeta);
        });

        return metas;
    }

    @Override
    public String generateResponse(Enum type, int numOfItems) throws JsonProcessingException {
        if (type == MockObject.POSTS) {
            return objectMapper.writeValueAsString(getPosts(numOfItems));
        }else if (type == MockObject.POST){
            return objectMapper.writeValueAsString(getPost());
        }
        else if (type == MockObject.META) {
            return objectMapper.writeValueAsString(getMeta(numOfItems));
        } else {
            return objectMapper.writeValueAsString(getPosts(numOfItems));
        }
    }
}
