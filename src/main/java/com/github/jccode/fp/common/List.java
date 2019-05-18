package com.github.jccode.fp.common;

/**
 * List
 *
 * @author 01372461
 */
public abstract class List<T> {

    public abstract T head();
    public abstract List<T> tail();
    public static final List NIL = new Nil<>();

    static class Nil<T> extends List<T> {
        @Override
        public T head() {
            throw new IllegalStateException("head call on an empty list");
        }

        @Override
        public List<T> tail() {
            throw new IllegalStateException("tail call on an empty list");
        }
    }

    static class Cons<T> extends List<T> {
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
    }

    public static <T> List<T> list() {
        return NIL;
    }

    @SafeVarargs
    public static <T> List<T> list(T... elements) {
        List<T> list = list();
        for (int i = elements.length-1; i <= 0; i--) {
            list = new Cons(elements[i], list);
        }
        return list;
    }
}
