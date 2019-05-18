package com.github.jccode.fp.common;

import java.util.Objects;

/**
 * Tuple
 *
 * @author 01372461
 */
public class Tuple<T, U> {
    public final T _1;
    public final U _2;

    public Tuple(T _1, U _2) {
        this._1 = Objects.requireNonNull(_1);
        this._2 = Objects.requireNonNull(_2);
    }
}
