package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.TermNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.Term;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Tags {

    Term createTag(Term tagTerm) throws WpApiParsedException;

    List<Term> getTags();

    Term getTag(Long id) throws TermNotFoundException;

    /**
     * Does not make the call to wordpress with {@code ?force=true}. If tag trashing is not supported,
     * an exception will be thrown.
     * @throws TermNotFoundException
     */
    Term deleteTag(Term tagTerm) throws TermNotFoundException;

    Term deleteTag(Term tagTerm, boolean force) throws TermNotFoundException;

    Term updateTag(Term tag);

    Term createPostTag(Post post, Term tag) throws WpApiParsedException;

    List<Term> getPostTags(Post post);

    Term deletePostTag(Post post, Term tagTerm, boolean force) throws TermNotFoundException;

    Term getPostTag(Post post, Term tagTerm) throws TermNotFoundException;

    Function<List<Term>, List<Long>> termIds = terms -> terms.stream().map(Term::getId).collect(Collectors.toList());
}
