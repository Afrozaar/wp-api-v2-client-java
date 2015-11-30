package com.afrozaar.wordpress.wpapi.v2;

import java.util.List;
import java.util.Objects;

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
    public String getPrevious() {
        return previous;
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

        public Builder<BT> withNext(String next) {
            this.next = next;
            return this;
        }

        public Builder<BT> withSelf(String self) {
            this.self = self;
            return this;
        }

        public Builder<BT> withPrevious(String previous) {
            this.previous = previous;
            return this;
        }

        public Builder<BT> withPosts(List<BT> posts) {
            this.posts = posts;
            return this;
        }

        public PagedResponse<BT> build() {
            return new PagedResponse<>(next, self, previous, posts);
        }

        public Builder<BT> withPages(int pages) {
            this.pages = pages;
            return this;
        }
    }
}
