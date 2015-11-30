package com.afrozaar.wordpress.wpapi.v2;

import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PagedResponse<T> {

    // captures the state of a response from wordpress
    final String self;
    final String next;
    final String previous;
    final List<T> list;

    public PagedResponse(String self, String next, String previous, List<T> list) {
        this.self = self;
        this.next = next;
        this.previous = previous;
        this.list = list;
    }

    public boolean hasNext() {
        return Objects.nonNull(next);
    }

    public String getNext() {
        return next;
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

    public static class Builder<BT> {
        String next;
        // captures the state of a response from wordpress
        String self;
        String previous;
        List<BT> posts;
        int pages;

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
            return new PagedResponse<>(self, next, previous, posts);
        }

        public Builder<BT> withPages(int pages) {
            this.pages = pages;
            return this;
        }

        public Builder<BT> withPages(HttpHeaders headers) {
            return withPages(Integer.valueOf(headers.get(Strings.HEADER_TOTAL_PAGES).get(0)));
        }
    }
}
