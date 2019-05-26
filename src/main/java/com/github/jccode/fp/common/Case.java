package com.github.jccode.fp.common;

/**
 * Case
 *
 * @author 01372461
 */
public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result<T>>> {

    public Case(Supplier<Boolean> condition, Supplier<Result<T>> result) {
        super(condition, result);
    }

    public static <T> Case<T> mcase(Supplier<Boolean> condition, Supplier<Result<T>> result) {
        return new Case<>(condition, result);
    }

    public static <T> DefaultCase<T> mcase(Supplier<Result<T>> result) {
        return new DefaultCase<T>(() -> true, result);
    }

    public static <T> Result<T> match(DefaultCase<T> defaultCase, Case<T>... matchers) {
        for (Case<T> matcher : matchers) {
            if (matcher._1.get()) {
                return matcher._2.get();
            }
        }
        return defaultCase._2.get();
    }

    public static class DefaultCase<T> extends Case<T> {
        public DefaultCase(Supplier<Boolean> condition, Supplier<Result<T>> result) {
            super(condition, result);
        }
    }

}
