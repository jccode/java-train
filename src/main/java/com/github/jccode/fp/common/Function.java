package com.github.jccode.fp.common;

/**
 * Function
 *
 * @author 01372461
 */
public interface Function<T, U> {
    U apply(T arg);

    default <V> Function<V, U> compose(Function<V, T> g) {
        return (V t) -> apply(g.apply(t));
    }

    default <R> Function<T, R> andThen(Function<U, R> g) {
        return (T t) -> g.apply(apply(t));
    }

    static <T> Function<T, T> identity() {
        return t -> t;
    }

    static <T, U, V> Function<V, U> compose(Function<T, U> f, Function<V, T> g) {
        return v -> f.apply(g.apply(v));
    }

    static <T, U, R> Function<T, R> andThen(Function<T, U> f, Function<U, R> g) {
        return t -> g.apply(f.apply(t));
    }

}
