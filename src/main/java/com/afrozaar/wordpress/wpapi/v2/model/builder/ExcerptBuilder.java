package com.afrozaar.wordpress.wpapi.v2.model.builder;

import com.afrozaar.wordpress.wpapi.v2.model.Excerpt;

/**
 * @author johan
 */
public class ExcerptBuilder {
    private String raw;
    private String rendered;

    private ExcerptBuilder() {
    }

    public static ExcerptBuilder anExcerpt() {
        return new ExcerptBuilder();
    }

    public ExcerptBuilder withRaw(String raw) {
        this.raw = raw;
        return this;
    }

    public ExcerptBuilder withRendered(String rendered) {
        this.rendered = rendered;
        return this;
    }

    public ExcerptBuilder but() {
        return anExcerpt().withRaw(raw).withRendered(rendered);
    }

    public Excerpt build() {
        Excerpt excerpt = new Excerpt();
        excerpt.setRaw(raw);
        excerpt.setRendered(rendered);
        return excerpt;
    }
}
