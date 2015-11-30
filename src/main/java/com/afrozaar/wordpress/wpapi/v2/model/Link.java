package com.afrozaar.wordpress.wpapi.v2.model;

/**
 * @author johan
 */
public class Link {

    private String href;

    private String rel;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public static Link of(String href, String rel) {
        return Builder.aLink().withHref(href).withRel(rel).build();
    }

    public static class Builder {
        private String href;
        private String rel;

        private Builder() {
        }

        public static Builder aLink() {
            return new Builder();
        }

        public Builder withHref(String href) {
            this.href = href;
            return this;
        }

        public Builder withRel(String rel) {
            this.rel = rel;
            return this;
        }

        public Builder but() {
            return aLink().withHref(href).withRel(rel);
        }

        public Link build() {
            Link link = new Link();
            link.setHref(href);
            link.setRel(rel);
            return link;
        }
    }
}
