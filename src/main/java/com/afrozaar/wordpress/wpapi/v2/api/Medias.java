package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Media;
import com.afrozaar.wordpress.wpapi.v2.model.Post;

import org.springframework.core.io.Resource;

import javax.annotation.Nullable;

import java.util.List;

public interface Medias {

    Media createMedia(Media media, Resource resource) throws WpApiParsedException;

    List<Media> getMedia();

    Media getMedia(Long id);

    /**
     * @param id The media's ID.
     * @param context One of {@code view}, {@code edit} or {@code embed}.
     *                If {@code context} is not provided, it will default to {@code edit}
     */
    Media getMedia(Long id, @Nullable String context);

    Media updateMedia(Media media);

    boolean deleteMedia(Media media);

    boolean deleteMedia(Media media, boolean force);

    Post setPostFeaturedMedia(Long postId, Media media);

    List<Media> getPostMedias(Long postId);

    List<Media> getPostMedias(Long postId, @Nullable String context);

}
