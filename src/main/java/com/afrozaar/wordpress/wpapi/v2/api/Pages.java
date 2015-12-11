package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.PageNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.Page;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;

public interface Pages {

    Page createPage(Page page, PostStatus postStatus);

    Page getPage(Long pageId) throws PageNotFoundException;
    Page getPage(Long pageId, String context);

    //List<Page> getPages();

    Page updatePage(Page page);

    Page deletePage(Page page);
    Page deletePage(Page page, boolean force);

}
