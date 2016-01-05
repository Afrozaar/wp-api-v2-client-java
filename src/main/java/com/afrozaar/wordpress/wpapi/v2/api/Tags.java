package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.TermNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.Term;

import java.util.List;

public interface Tags {

    Term createTag(Term tagTerm);

    List<Term> getTags();

    Term getTag(Long id) throws TermNotFoundException;

    Term deleteTag(Term tagTerm) throws TermNotFoundException;

    Term updateTag(Term tag);

    Term createPostTag(Post post, Term tag) throws WpApiParsedException;

    List<Term> getPostTags(Post post);

    Term deletePostTag(Post post, Term tagTerm, boolean force) throws TermNotFoundException;

    Term getPostTag(Post post, Term tagTerm) throws TermNotFoundException;
}
