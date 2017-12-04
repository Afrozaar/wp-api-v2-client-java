package com.afrozaar.wordpress.wpapi.v2.util;

public class Tuples {

    public static <T1, T2> Tuple2<T1, T2> tuple(T1 v1, T2 v2) {
        return new Tuple2<>(v1, v2);
    }
    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> tuple(T1 v1, T2 v2, T3 v3, T4 v4, T5 v5) {
        return new Tuple5<>(v1, v2, v3, v4, v5);
    }

    public static class Tuple2<T1, T2> {
        public final T1 v1;
        public final T2 v2;

        private Tuple2(T1 v1, T2 v2) {
            this.v1 = v1;
            this.v2 = v2;
        }
    }

    public static class Tuple5<V1, V2, V3, V4, V5> {
        public final V1 v1;
        public final V2 v2;
        public final V3 v3;
        public final V4 v4;
        public final V5 v5;

        private Tuple5(V1 v1, V2 v2, V3 v3, V4 v4, V5 v5) {
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
            this.v4 = v4;
            this.v5 = v5;
        }
    }


}
