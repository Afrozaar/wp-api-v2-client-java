package com.afrozaar.wordpress.wpapi.v2.util;

public class Two<A, B> {
    public final A a;
    public final B b;

    public Two(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public static <A, B> Two<A, B> of(A a, B b) {
        return new Two<>(a, b);
    }
}
