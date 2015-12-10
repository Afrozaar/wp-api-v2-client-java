package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Media;
import com.afrozaar.wordpress.wpapi.v2.model.Post;

import org.springframework.core.io.Resource;

import java.util.List;

public interface Medias {

    Media createMedia(Media media, Resource resource) throws WpApiParsedException;

    List<Media> getMedia();
    Media getMedia(Long id);

    Media updateMedia(Media media);

    boolean deleteMedia(Media media);
    boolean deleteMedia(Media media, boolean force);

    Post setFeaturedImage(Media media);
}
