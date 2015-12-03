package com.afrozaar.wordpress.wpapi.v2;

import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.Title;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class WordpressInstance implements WordpressInstanceInterface {
    JSONArray jsonArray;

    public String randomTitleGenerator(){
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        int i = (int)(Math.random()*5) + 5;
        return randomStringUtils.random(i);
    }

    @Override
    public Post[] generateRandomPosts(int numOfPosts) {
        Post[] posts = new Post[numOfPosts];

        for (int i = 0; i < numOfPosts; i++){
            Title title = new Title();
            title.setRendered(randomTitleGenerator());

            Post post = new Post();
            post.setId(i);
            post.setTitle(title);

            posts[i] = post;
        }
        return posts;
    }

    public void generateJson(Post[] posts) throws JSONException {
        jsonArray = new JSONArray(posts);
    }

    public void printJsonObject() throws JSONException {
        Post[] posts = generateRandomPosts(2);
        generateJson(posts);
        System.out.println(jsonArray.toString());
    }

    public Map<String, String> generateResponseHeader(String link, int numOfPosts, int numOfPages){
        Map<String, String> header = new HashMap<>();
        header.put("Link", link);
        header.put("X-WP-Total", Integer.toString(numOfPosts));
        header.put("X-WP-TotalPages",Integer.toString(numOfPages));

        return header;
    }

    public void generateResponse(){

    }

    public class ResponseBody(){

    }

    public class ResponseHeader()

}
