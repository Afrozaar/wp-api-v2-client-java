package com.afrozaar.wordpress.wpapi.v2.model;

public enum PostStatus {
    draft,
    publish,
    pending,
    future,
    private_("private");

    public final String value;

    PostStatus() {
        this.value = this.name().toLowerCase();
    }

    PostStatus(String value) {
        this.value = value;
    }
}
