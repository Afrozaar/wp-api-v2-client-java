package com.afrozaar.wordpress.wpapi.v2.model.builder;

import com.afrozaar.wordpress.wpapi.v2.model.Content;

/**
 * @author johan
 */
public class ContentBuilder {
    private String raw;
    private String rendered;

    private ContentBuilder() {
    }

    public static ContentBuilder aContent() {
        return new ContentBuilder();
    }

    public ContentBuilder withRaw(String raw) {
        this.raw = raw;
        return this;
    }

    public ContentBuilder withRendered(String rendered) {
        this.rendered = rendered;
        return this;
    }

    public ContentBuilder but() {
        return aContent().withRaw(raw).withRendered(rendered);
    }

    public Content build() {
        Content content = new Content();
        content.setRaw(raw);
        content.setRendered(rendered);
        return content;
    }
}
