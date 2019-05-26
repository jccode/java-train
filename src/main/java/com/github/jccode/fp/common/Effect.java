package com.github.jccode.fp.common;

/**
 * Effect
 *
 * @author 01372461
 */
public interface Effect<T> {
    void apply(T t);
}
