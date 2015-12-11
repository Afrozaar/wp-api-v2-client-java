package com.afrozaar.wordpress.wpapi.v2.model.builder;

import com.afrozaar.wordpress.wpapi.v2.model.Title;

public class TitleBuilder {
    private String raw;
    private String rendered;

    private TitleBuilder() {
    }

    public static TitleBuilder aTitle() {
        return new TitleBuilder();
    }

    public TitleBuilder withRaw(String raw) {
        this.raw = raw;
        return this;
    }

    public TitleBuilder withRendered(String rendered) {
        this.rendered = rendered;
        return this;
    }

    public TitleBuilder but() {
        return aTitle().withRaw(raw).withRendered(rendered);
    }

    public Title build() {
        Title title = new Title();
        title.setRaw(raw);
        title.setRendered(rendered);
        return title;
    }
}
