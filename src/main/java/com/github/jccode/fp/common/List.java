package com.github.jccode.fp.common;

import java.util.Arrays;

import static com.github.jccode.fp.common.TailCall.*;

/**
 * List
 *
 * @author 01372461
 */
public abstract class List<T> {

    public abstract T head();
    public abstract List<T> tail();
    public abstract boolean isEmpty();
    public abstract List<T> setHead(T h);
    public abstract int length();
    public abstract <U> U foldLeft(U identity, Function<U, Function<T, U>> f);
    public abstract List<T> reverse();
    public abstract List<T> take(int n);
    public abstract List<T> drop(int n);

    public static final List NIL = new Nil<>();

    public static <T> List<T> list() {
        return NIL;
    }

    @SafeVarargs
    public static <T> List<T> list(T... elements) {
        return list_(list(), elements).eval();
    }

    private static <T> TailCall<List<T>> list_(List<T> acc, T[] as) {
        return as.length == 0 ?
                ret(acc) :
                sus(() -> list_(acc.cons(as[as.length-1]), Arrays.copyOfRange(as, 0, as.length -1)));
    }

    @SafeVarargs
    public static <T> List<T> list_impresive(T... elements) {
        List<T> list = list();
        for (int i = elements.length-1; i >= 0; i--) {
            list = new Cons(elements[i], list);
        }
        return list;
    }

    public List<T> cons(T t) {
        return new Cons<>(t, this);
    }

    private static class Nil<T> extends List<T> {
        @Override
        public T head() {
            throw new IllegalStateException("head call on an empty list");
        }

        @Override
        public List<T> tail() {
            throw new IllegalStateException("tail call on an empty list");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public List<T> setHead(T h) {
            throw new IllegalStateException("setHead call on an empty list");
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public <U> U foldLeft(U identity, Function<U, Function<T, U>> f) {
            return identity;
        }

        @Override
        public List<T> reverse() {
            return this;
        }

        @Override
        public List<T> take(int n) {
            return this;
        }

        @Override
        public List<T> drop(int n) {
            return this;
        }

        @Override
        public String toString() {
            return "[NIL]";
        }
    }

    private static class Cons<T> extends List<T> {
        private final T head;
        private final List<T> tail;

        public Cons(T head, List<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public List<T> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public List<T> setHead(T h) {
            return new Cons<>(h, tail);
        }

        @Override
        public int length() {
//            return length_recursive(0, this).eval();
            return foldLeft(0, x -> y -> x + 1);
        }

        @Override
        public <U> U foldLeft(U identity, Function<U, Function<T, U>> f) {
            return foldLeft_(identity, f, this).eval();
        }

        @Override
        public List<T> reverse() {
            return foldLeft(list(), x -> y -> new Cons<>(y, x));
        }

        @Override
        public List<T> take(int n) {
            return take_(this, list(), n).eval().reverse();
        }

        @Override
        public List<T> drop(int n) {
            return drop_(this, n).eval();
        }

        private TailCall<List<T>> drop_(List<T> list, int n) {
            return list.isEmpty() || n <= 0 ?
                    ret(list) :
                    sus(() -> drop_(list.tail(), n - 1));
        }

        private TailCall<List<T>> take_(List<T> list, List<T> acc, int n) {
            return n <= 0 || list.isEmpty()?
                    ret(acc) :
                    sus(() -> take_(list.tail(), acc.cons(list.head()), n-1));
        }


        private <U> TailCall<U> foldLeft_(U acc, Function<U, Function<T, U>> f, List<T> list) {
            return list.isEmpty() ?
                    ret(acc) :
                    sus(() -> foldLeft_(f.apply(acc).apply(list.head()), f, list.tail()));
        }

        private TailCall<Integer> length_recursive(int acc, List<T> list) {
            return list.isEmpty() ?
                    ret(acc) :
                    sus(() -> length_recursive(acc + 1, list.tail()));
        }

        public int length_impresive() {
            List t = tail;
            int len = 1;
            while (!t.isEmpty()) {
                t = t.tail();
                len++;
            }
            return len;
        }

        @Override
        public String toString() {
            return "[" +
                    toString(new StringBuilder(), this).eval().toString() +
                    "NIL]";
        }

        private TailCall<StringBuilder> toString(StringBuilder acc, List<T> list) {
            return list.isEmpty() ?
                    ret(acc) :
                    sus(() -> toString(acc.append(list.head().toString()).append(","), list.tail()));
        }

    }

}
