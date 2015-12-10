package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Media;

import org.springframework.core.io.Resource;

import java.util.List;

public interface Medias {

    Media createMediaItem(Media media, Resource resource) throws WpApiParsedException;

    List<Media> getMedia();
    Media getMedia(Integer id);

//    Media updateMediaItem();
//
//    boolean deleteMediaItem();
}
