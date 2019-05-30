package com.github.jccode.fp.ch10;

import com.github.jccode.fp.common.List;

/**
 * Tree
 *
 * @author 01372461
 */
public abstract class Tree<A extends Comparable<A>> {

    private static Tree EMPTY = new Empty<>();
    public abstract A value();
    public abstract Tree<A> left();
    public abstract Tree<A> right();
    public abstract Tree<A> insert(A a);
    public abstract boolean member(A a);
    public abstract int size();
    public abstract int height();
    public abstract Tree<A> remove(A a);
    public abstract boolean isEmpty();
    public abstract Tree<A> merge(Tree<A> t);

    protected abstract Tree<A> removeMerge(Tree<A> t);


    private static class Empty<A extends Comparable<A>> extends Tree<A> {

        @Override
        public A value() {
            throw new IllegalStateException("value() call on empty");
        }

        @Override
        public Tree<A> left() {
            throw new IllegalStateException("left() call on empty");
        }

        @Override
        public Tree<A> right() {
            throw new IllegalStateException("right() call on empty");
        }

        @Override
        public Tree<A> insert(A a) {
            return new T<>(empty(), a, empty());
        }

        @Override
        public boolean member(A a) {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public int height() {
            return 0;
        }

        @Override
        public Tree<A> remove(A a) {
            throw new IllegalStateException("remove() call on empty");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Tree<A> merge(Tree<A> t) {
            return t;
        }

        @Override
        protected Tree<A> removeMerge(Tree<A> t) {
            return t;
        }

        @Override
        public String toString() {
            return "E";
        }
    }

    private static class T<A extends Comparable<A>> extends Tree<A> {

        private final Tree<A> left;
        private final Tree<A> right;
        private final A value;

        private T(Tree<A> left, A value, Tree<A> right) {
            this.left = left;
            this.value = value;
            this.right = right;
        }

        @Override
        public A value() {
            return value;
        }

        @Override
        public Tree<A> left() {
            return left;
        }

        @Override
        public Tree<A> right() {
            return right;
        }

        @Override
        public Tree<A> insert(A a) {
            return a.compareTo(value) < 0
                    ? new T<>(left.insert(a), value, right)
                    : a.compareTo(value) > 0
                      ? new T<>(left, value, right.insert(a))
                      : new T<>(left, a, right);
        }

        @Override
        public boolean member(A a) {
            return a.compareTo(value) == 0 || (a.compareTo(value) < 0
                    ? left.member(a)
                    : right.member(a));
        }

        @Override
        public int size() {
            return left.size() + 1 + right.size();
        }

        @Override
        public int height() {
            return 1 + Math.max(left.height(), right.height());
        }

        @Override
        public Tree<A> remove(A a) {
            return a.compareTo(value) < 0
                    ? new T<>(left.remove(a), value, right)
                    : a.compareTo(value) > 0 ?
                      new T<>(left, value, right.remove(a))
                      : left.removeMerge(right);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Tree<A> merge(Tree<A> t) {
            if (t.isEmpty()) return this;
            int r = value.compareTo(t.value());
            if (r < 0) {
//                return new T<>(, t.value(), )
                // TODO
                return null;
            } else if (r > 0) {
                // TODO
                return null;
            } else {
                return new T<>(left.merge(t.left()), value, right.merge(t.right()));
            }
        }

        @Override
        protected Tree<A> removeMerge(Tree<A> t) {
            if (t.isEmpty()) return this;
            if (value.compareTo(t.value()) < 0) {
                return new T<>(left.removeMerge(t), value, right);
            } else {
                return new T<>(left, value, right.removeMerge(t));
            }
        }

        @Override
        public String toString() {
            return String.format("(T %s %s %s)", left, value, right);
        }
    }

    public static <A extends Comparable<A>> Tree<A> empty() {
        return EMPTY;
    }

    public static <A extends Comparable<A>> Tree<A> tree(A... a) {
        return tree(List.list(a));
    }

    public static <A extends Comparable<A>> Tree<A> tree(List<A> list) {
        return list.foldLeft(empty(), t -> t::insert);
    }
}
