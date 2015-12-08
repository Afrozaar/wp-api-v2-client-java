package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.TermNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.Term;

import java.util.List;

public interface Terms {

    Term createTerm(Term term, String taxonomy);

    List<Term> getTerms(String taxonomy);

    Term getTerm(String taxonomy, Long id) throws TermNotFoundException;

    Term updateTerm(Term term);

    Term deleteTerm(Term term, String taxonomy) throws TermNotFoundException;

}
