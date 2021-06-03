package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.model.Comment;
import com.afrozaar.wordpress.wpapi.v2.request.Request;
import com.afrozaar.wordpress.wpapi.v2.request.SearchRequest;

public interface Comments {

  /**
   * Search request just returning the first page of comments.
   */
  static SearchRequest.Builder<Comment> listBuilder() {
    return SearchRequest.Builder.aSearchRequest(Comment.class)
        .withUri(Request.COMMENTS);
  }

  Comment updateComment(Comment comment);
}
