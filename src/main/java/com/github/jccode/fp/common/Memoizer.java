package com.github.jccode.fp.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Memoizer
 *
 * @author 01372461
 */
public class Memoizer<T, U> {

    private Map<T, U> cache = new ConcurrentHashMap<>();

    public static <T, U> Function<T, U> memoize(Function<T, U> function) {
        return new Memoizer<T, U>().doMemoize(function);
    }

    private Function<T, U> doMemoize(Function<T, U> function) {
        return t -> cache.computeIfAbsent(t, function::apply);
    }
}
