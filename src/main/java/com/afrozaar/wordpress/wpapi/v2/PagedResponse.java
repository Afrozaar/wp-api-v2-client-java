package com.afrozaar.wordpress.wpapi.v2;

import org.springframework.http.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PagedResponse<T> {

    private static final Logger LOG = LoggerFactory.getLogger(PagedResponse.class);
    // captures the state of a response from wordpress
    final String self;
    final String next;
    final String previous;
    final int pages;
    final List<T> list;

    public PagedResponse(String self, String next, String previous, int pages, List<T> list) {
        this.self = self;
        this.next = next;
        this.previous = previous;
        this.pages = pages;
        this.list = list;
    }

    public boolean hasNext() {
        return Objects.nonNull(next);
    }

    public Optional<String> getNext() {
        return Optional.ofNullable(next);
    }

    public boolean hasPrevious() {
        return Objects.nonNull(previous);
    }

    public Optional<String> getPrevious() {
        return Optional.ofNullable(previous);
    }

    public String getSelf() {
        return self;
    }

    public List<T> getList() {
        return list;
    }

    public void debug() {
        LOG.trace("response.self      = {}", this.self);
        LOG.trace("response.prev      = {}", this.previous);
        LOG.trace("response.next      = {}", this.next);
        LOG.trace("response.pages     = {}", this.pages);
        LOG.trace("response.list.size = {}", this.list.size());
    }

    public static class Builder<BT> {
        private String next;
        // captures the state of a response from wordpress
        private String self;
        private String previous;
        private List<BT> posts;
        private int pages;

        private Builder() {
        }

        public static <BT> Builder<BT> aPagedResponse() {
            return new Builder<>();
        }

        public Builder<BT> withNext(Optional<String> next) {
            if (next.isPresent()) {
                this.next = next.get();
            }
            return this;
        }

        public Builder<BT> withSelf(String self) {
            this.self = self;
            return this;
        }

        public Builder<BT> withPrevious(Optional<String> previous) {
            if (previous.isPresent()) {
                this.previous = previous.get();
            }
            return this;
        }

        public Builder<BT> withPosts(List<BT> posts) {
            this.posts = posts;
            return this;
        }

        public PagedResponse<BT> build() {
            return new PagedResponse<>(self, next, previous, pages, posts);
        }

        public Builder<BT> withPages(int pages) {
            this.pages = pages;
            return this;
        }

        public Builder<BT> withPages(HttpHeaders headers) {
            headers.get(Strings.HEADER_TOTAL_PAGES).stream()
                    .findFirst()
                    .ifPresent(pages -> Builder.this.withPages(Integer.valueOf(pages)));
            return this;
        }
    }
}
