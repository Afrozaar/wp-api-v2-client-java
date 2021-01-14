package com.afrozaar.wordpress.wpapi.v2.response;

import com.afrozaar.wordpress.wpapi.v2.Strings;
import com.afrozaar.wordpress.wpapi.v2.request.Request;

import org.springframework.http.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class PagedResponse<T> {

    final Class<T> clazz;

    public static final Function<PagedResponse<?>, String> NEXT = response -> response.getNext().get();
    public static final Function<PagedResponse<?>, String> PREV = response -> response.getPrevious().get();

    private static final Logger LOG = LoggerFactory.getLogger(PagedResponse.class);
    // captures the state of a response from wordpress
    final String self;
    final String next;
    final String previous;
    final int pages;
    final List<T> list;
    final int total;

    public int getPages() {
        return pages;
    }

    public int getTotal() {
        return total;
    }

//    public PagedResponse(Class<T> clazz, String self, String next, String previous, int pages, List<T> list) {
//        this.clazz = clazz;
//        this.self = self;
//        this.next = next;
//        this.previous = previous;
//        this.pages = pages;
//        this.list = list;
//    }

    public PagedResponse(Class<T> clazz, String self, String next, String previous, int pages, List<T> list, int total) {
        this.clazz = clazz;
        this.self = self;
        this.next = next;
        this.previous = previous;
        this.pages = pages;
        this.list = list;
        this.total = total;
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

    public URI getUri(Function<PagedResponse<?>, String> direction) {
        return Request.fromLink(direction.apply(this));
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public static class Builder<BT> {
        private String next;
        // captures the state of a response from wordpress
        private String self;
        private String previous;
        private List<BT> posts;
        private int pages;
        private Class<BT> t1;
        private int total;

        private Builder(Class<BT> t1) {
            this.t1 = t1;
        }

        public static <BT> Builder<BT> aPagedResponse(Class<BT> t) {
            return new Builder<>(t);
        }

        public Builder<BT> withNext(Optional<String> next) {
            next.ifPresent(n -> this.next = n);
            return this;
        }

        public Builder<BT> withSelf(String self) {
            this.self = self;
            return this;
        }

        public Builder<BT> withPrevious(Optional<String> previous) {
            previous.ifPresent(p -> this.previous = p);
            return this;
        }

        public Builder<BT> withPosts(List<BT> posts) {
            this.posts = posts;
            return this;
        }

        public PagedResponse<BT> build() {
            return new PagedResponse<>(t1, self, next, previous, pages, posts, total);
        }

        public Builder<BT> withPages(int pages) {
            this.pages = pages;
            return this;
        }

        public Builder<BT> withPages(int pages, int total) {
            this.pages = pages;
            this.total = total;
            return this;
        }

        public Builder<BT> withPages(HttpHeaders headers) {
            Optional<String> totalPages = headers.keySet().stream().filter(x -> Strings.HEADER_TOTAL_PAGES.compareToIgnoreCase(x) == 0).findFirst();
            Optional<String> totalOptional = headers.keySet().stream().filter(x -> Strings.HEADER_TOTAL.compareToIgnoreCase(x) == 0).findFirst();
            LOG.debug("found pages {}, total {} from headers {}", totalPages, totalOptional, headers);
            Stream<String> totalPageHeader = totalPages.map(x -> headers.get(x)).map(x -> x.stream()).orElse(Stream.empty());
            int page = Integer.valueOf(totalPageHeader.findFirst().get());

            Stream<String> totalHeader = totalOptional.map(x -> headers.get(x)).map(x -> x.stream()).orElse(Stream.empty());
            totalHeader.findFirst().ifPresent(total -> Builder.this.withPages(page, Integer.valueOf(total)));

            return this;
        }

    }
}
