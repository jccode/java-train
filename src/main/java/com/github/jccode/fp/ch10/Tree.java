package com.github.jccode.fp.ch10;

/**
 * Tree
 *
 * @author 01372461
 */
public abstract class Tree<A extends Comparable<A>> {

    private static Tree EMPTY = new Empty<>();
    abstract A value();
    abstract Tree<A> left();
    abstract Tree<A> right();

    private static class Empty<A extends Comparable<A>> extends Tree<A> {

        @Override
        A value() {
            throw new IllegalStateException("value() call on empty");
        }

        @Override
        Tree<A> left() {
            throw new IllegalStateException("left() call on empty");
        }

        @Override
        Tree<A> right() {
            throw new IllegalStateException("right() call on empty");
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
        A value() {
            return value;
        }

        @Override
        Tree<A> left() {
            return left;
        }

        @Override
        Tree<A> right() {
            return right;
        }

        @Override
        public String toString() {
            return String.format("(T %s %s %s)", left, value, right);
        }
    }

    public static <A extends Comparable<A>> Tree<A> empty() {
        return EMPTY;
    }
}
