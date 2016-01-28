package com.afrozaar.wordpress.wpapi.v2.api;

import com.afrozaar.wordpress.wpapi.v2.exception.TermNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.Term;

import java.util.List;

public interface Categories {

    List<Term> getCategories();

    Term getCategory(Long id);

    Term createCategory(Term categoryTerm);

    Term deleteCategory(Term categoryTerm) throws TermNotFoundException;
    Term deleteCategory(Term categoryTerm, boolean force) throws TermNotFoundException;

    List<Term> deleteCategories(Term... terms);
    List<Term> deleteCategories(boolean force,Term... terms);
}
