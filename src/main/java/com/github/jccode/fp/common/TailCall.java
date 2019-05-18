package com.github.jccode.fp.common;

/**
 * TailCall
 *
 * @author 01372461
 */
public abstract class TailCall<T> {

    public abstract T eval();
    public abstract TailCall<T> resume();
    public abstract boolean isSuspend();

    public static <T> Return<T> ret(T value) {
        return new Return<>(value);
    }

    public static <T> Suspend<T> sus(Supplier<TailCall<T>> resume) {
        return new Suspend<>(resume);
    }


    public static class Suspend<T> extends TailCall<T> {
        final Supplier<TailCall<T>> resume;

        public Suspend(Supplier<TailCall<T>> resume) {
            this.resume = resume;
        }

        @Override
        public T eval() {
            TailCall<T> t = this;
            while (t.isSuspend()) {
                t = t.resume();
            }
            return t.eval();
        }

        @Override
        public TailCall<T> resume() {
            return resume.get();
        }

        @Override
        public boolean isSuspend() {
            return true;
        }
    }

    public static class Return<T> extends TailCall<T> {
        final T value;

        public Return(T value) {
            this.value = value;
        }

        @Override
        public T eval() {
            return value;
        }

        @Override
        public TailCall<T> resume() {
            throw new IllegalStateException("Return has no resume");
        }

        @Override
        public boolean isSuspend() {
            return false;
        }
    }
}
