package com.afrozaar.wordpress.wpapi.v2.model.builder;

import com.afrozaar.wordpress.wpapi.v2.model.Excerpt;

/**
 * @author johan
 */
public class ExcerptBuilder {
    private String rendered;

    private ExcerptBuilder() {
    }

    public static ExcerptBuilder anExcerpt() {
        return new ExcerptBuilder();
    }

    public ExcerptBuilder withRendered(String rendered) {
        this.rendered = rendered;
        return this;
    }

    public ExcerptBuilder but() {
        return anExcerpt().withRendered(rendered);
    }

    public Excerpt build() {
        Excerpt excerpt = new Excerpt();
        excerpt.setRendered(rendered);
        return excerpt;
    }
}
