package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.model.Term;

import java.util.List;

public interface Terms {

    Term createTerm(Term term);

    List<Term> getTerms(String taxonomySlug);

    Term getTerm(Long id, String taxonomySlug);

    Term updateTerm(Term term);

    Term deleteTerm(Term term);

}
