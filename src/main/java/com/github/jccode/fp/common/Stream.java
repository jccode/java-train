package com.github.jccode.fp.common;

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

    private final static Stream EMPTY = new Empty<>();

    public static <T> Stream<T> empty() {
        return EMPTY;
    }

    public static <T> Stream<T> cons(Supplier<T> head, Supplier<Stream<T>> tail) {
        return new Cons<>(head, tail);
    }

    public static Stream<Integer> from(int i) {
        return cons(() -> i, () -> from(i + 1));
    }

    public List<T> toList() {
        return toList(this);
    }

    private List<T> toList(Stream<T> stream) {
        return stream.isEmpty() ?
                List.list() :
                toList(stream.tail()).cons(stream.head());
    }


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

        @Override
        public Stream<T> take(int n) {
            return isEmpty() || n <= 0 ?
                    this :
                    Stream.cons(head, () -> take(n - 1));
        }

        @Override
        public Stream<T> drop(int n) {
            return null;
        }
    }
}
