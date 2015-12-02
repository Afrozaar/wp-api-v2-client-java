package com.afrozaar.wordpress.wpapi.v2.util;

public class Two<K, V> {
    public final K k;
    public final V v;

    public Two(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public static <K, V> Two<K, V> of(K k, V v) {
        return new Two<>(k, v);
    }
}
