package com.afrozaar.wordpress.wpapi.v2.util.builder;

import com.afrozaar.wordpress.wpapi.v2.model.Content;

/**
 * @author johan
 */
public class ContentBuilder {
    private String rendered;

    private ContentBuilder() {
    }

    public static ContentBuilder aContent() {
        return new ContentBuilder();
    }

    public ContentBuilder withRendered(String rendered) {
        this.rendered = rendered;
        return this;
    }

    public ContentBuilder but() {
        return aContent().withRendered(rendered);
    }

    public Content build() {
        Content content = new Content();
        content.setRendered(rendered);
        return content;
    }
}
