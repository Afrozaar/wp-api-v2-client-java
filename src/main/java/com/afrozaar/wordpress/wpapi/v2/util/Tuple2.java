package com.afrozaar.wordpress.wpapi.v2.util;

public class Tuple2<A, B> {
    public final A a;
    public final B b;

    private Tuple2(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public static <A, B> Tuple2<A, B> of(A a, B b) {
        return new Tuple2<>(a, b);
    }
}
