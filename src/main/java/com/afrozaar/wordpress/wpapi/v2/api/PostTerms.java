package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.Term;

import java.util.List;

public interface PostTerms {

    Term createPostTerm(Post post, String taxonomy, Term term) throws WpApiParsedException;

    Term updatePostTerm(Post post, String taxonomy, Term term);

    List<Term> getPostTerms(Post post, String taxonomy);

    Term deletePostTerm(Post post, String taxonomy, Term term);

    Term deletePostTerm(Post post, String taxonomy, Term term, boolean force);

    Term getPostTerm(Post post, String taxonomy, Term term) throws WpApiParsedException;
}
