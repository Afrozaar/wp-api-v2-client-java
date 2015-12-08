package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.TermNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.Term;

import java.util.List;

public interface Terms {

    Term createTerm(String taxonomy, Term term);

    List<Term> getTerms(String taxonomy);

    Term getTerm(String taxonomy, Long id) throws TermNotFoundException;

    Term updateTerm(String taxonomy, Term term);

    Term deleteTerm(String taxonomy, Term term) throws TermNotFoundException;

}
