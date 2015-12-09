package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.WpApiClientParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Media;

import java.util.List;

public interface Medias {

    Media createMediaItem(Media media) throws WpApiClientParsedException;

    List<Media> getMedia();
    Media getMedia(Integer id);

//    Media updateMediaItem();
//
//    boolean deleteMediaItem();
}
