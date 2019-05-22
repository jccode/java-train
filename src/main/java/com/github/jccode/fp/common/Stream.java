package com.github.jccode.fp.common;

import static com.github.jccode.fp.common.TailCall.*;

/**
 * Stream
 *
 * @author 01372461
 */
public abstract class Stream<T> {

    public abstract T head();
    public abstract Stream<T> tail();
    public abstract boolean isEmpty();
    public abstract Stream<T> take(int n);
    public abstract Stream<T> drop(int n);
    public abstract <U> U foldRight(U z, Function<T, Function<U, U>> f);
    public abstract <U> U foldLeft(U z, Function<U, Function<T, U>> f);

    public List<T> toList() {
        return toList(this, List.list()).eval().reverse();
    }

    public TailCall<List<T>> toList(Stream<T> stream, List<T> acc) {
        return stream.isEmpty() ?
                ret(acc) :
                sus(() -> toList(stream.tail(), acc.cons(stream.head())));
    }





    // instances
    // ------------------------

    private static class Empty<T> extends Stream<T> {

        @Override
        public T head() {
            throw new IllegalStateException("head call on empty");
        }

        @Override
        public Stream<T> tail() {
            throw new IllegalStateException("tail call on empty");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Stream<T> take(int n) {
            return this;
        }

        @Override
        public Stream<T> drop(int n) {
            return this;
        }

        @Override
        public <U> U foldRight(U z, Function<T, Function<U, U>> f) {
            return z;
        }

        @Override
        public <U> U foldLeft(U z, Function<U, Function<T, U>> f) {
            return z;
        }
    }

    private static class Cons<T> extends Stream<T> {

        private final Supplier<T> head;
        private final Supplier<Stream<T>> tail;

        private Cons(Supplier<T> head, Supplier<Stream<T>> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public T head() {
            return head.get();
        }

        @Override
        public Stream<T> tail() {
            return tail.get();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }


        /**
         * Take
         *
         * take 不需要考虑栈安全; 因为take本身已经是惰性的.
         * @param n
         * @return
         */
        @Override
        public Stream<T> take(int n) {
            return isEmpty() || n <= 0 ?
                    empty() :
                    cons(head, () -> tail().take(n - 1));
//            return take(this, n, empty()).eval();
//            return take(this, n);
        }

        /*
        public <T> Stream<T> take(Stream<T> s, int n) {
            return s.isEmpty() || n <= 0 ?
                    Stream.empty() :
                    Stream.cons(s::head, () -> take(s.tail(), n - 1));
        }
         */

        /*
        public <T> TailCall<Stream<T>> take(Stream<T> s, int n, Stream<T> acc) {
            return s.isEmpty() || n <= 0 ?
                    ret(acc) :
                    sus(() -> take(s.tail(), n - 1, Stream.<T>cons(s::head, acc)));
        }
         */

        /**
         * Drop
         *
         * drop 必须考虑栈安全的问题
         * @param n
         * @return
         */
        @Override
        public Stream<T> drop(int n) {
//            return isEmpty() || n <= 0 ?
//                    this :
//                    tail().drop(n - 1);
            return drop(this, n).eval();
        }


        public <T> TailCall<Stream<T>> drop(Stream<T> s, int n) {
            return s.isEmpty() || n <= 0 ?
                    ret(s) :
                    sus(() -> drop(s.tail(), n - 1));
        }

        @Override
        public <U> U foldRight(U z, Function<T, Function<U, U>> f) {
            //return f.apply(head()).apply(() -> tail().foldRight(z, f));
            return foldRight(this, z, f).eval();
        }

        @Override
        public <U> U foldLeft(U z, Function<U, Function<T, U>> f) {
            //return tail().foldLeft(f.apply(z).apply(head()), f);
            return foldLeft(this, z, f).eval();
        }

    }



    // static methods
    // ----------------

    private final static Stream EMPTY = new Empty<>();

    public static <T> Stream<T> empty() {
        return EMPTY;
    }

    public static <T> Stream<T> cons(Supplier<T> head, Supplier<Stream<T>> tail) {
        return new Cons<>(head, tail);
    }

    public static <T> Stream<T> cons(Supplier<T> head, Stream<T> tail) {
        return new Cons<>(head, () -> tail);
    }

    public static Stream<Integer> from(int i) {
        return cons(() -> i, () -> from(i + 1));
    }

    public static <T> Stream<T> stream(List<T> list) {
        return list.reverse().foldLeft(empty(), st -> el -> cons(() -> el, st));
    }

    public static <T> Stream<T> stream(T... ts) {
        return stream(List.list(ts));
    }

    public static <T> Stream<T> repeat(T t) {
        return cons(() -> t, () -> repeat(t));
    }

    public static <T> Stream<T> iterate(Supplier<T> seed, Function<T, T> f) {
        return cons(seed, () -> iterate(f.apply(seed.get()), f));
    }

    public static <T> Stream<T> iterate(T seed, Function<T, T> f) {
        return cons(() -> seed, () -> iterate(f.apply(seed), f));
    }


//    public static <T, U> TailCall<U> foldRight(Stream<T> s, Supplier<U> acc, Function<T, Function<Supplier<U>, U>> f) {
//        return s.isEmpty() ?
//                ret(acc.get()) :
//                sus(() -> foldRight(s.tail(), () -> f.apply(s.head()).apply(acc), f));
//    }

    public static <T, U> TailCall<U> foldRight(Stream<T> s, U acc, Function<T, Function<U, U>> f) {
        return s.isEmpty() ?
                ret(acc) :
                sus(() -> foldRight(s.tail(), f.apply(s.head()).apply(acc), f));
    }


    public static <T, U> TailCall<U> foldLeft(Stream<T> s, U acc, Function<U, Function<T, U>> f) {
        return s.isEmpty() ?
                ret(acc) :
                sus(() -> foldLeft(s.tail(), f.apply(acc).apply(s.head()), f));
    }
}
