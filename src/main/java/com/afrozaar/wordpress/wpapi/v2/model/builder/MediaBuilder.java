package com.afrozaar.wordpress.wpapi.v2.model.builder;

import com.afrozaar.wordpress.wpapi.v2.model.Media;
import com.afrozaar.wordpress.wpapi.v2.model.Title;

public class MediaBuilder {

    private Long id;
    private String uri;
    private Title title;

    private MediaBuilder(){
    }

    public MediaBuilder withId(Long id){
        this.id = id;
        return this;
    }

    public MediaBuilder withUri(String uri){
        this.uri = uri;
        return this;
    }

    public MediaBuilder withTitle(Title title){
        this.title = title;
        return this;
    }

    public static MediaBuilder aMediaBuilder(){
        return new MediaBuilder();
    }

    public Media build(){
        Media media = new Media();
        media.setId(id);
        media.setSourceUrl(uri);
        media.setTitle(title);

        return media;
    }
}
