package com.afrozaar.wordpress.wpapi.v2.model.builder;

import com.afrozaar.wordpress.wpapi.v2.model.Title;

public class TitleBuilder {
    private String rendered;

    private TitleBuilder() {
    }

    public static TitleBuilder aTitle() {
        return new TitleBuilder();
    }

    public TitleBuilder withRendered(String rendered) {
        this.rendered = rendered;
        return this;
    }

    public TitleBuilder but() {
        return aTitle().withRendered(rendered);
    }

    public Title build() {
        Title title = new Title();
        title.setRendered(rendered);
        return title;
    }
}
