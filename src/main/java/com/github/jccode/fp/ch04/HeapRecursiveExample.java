package com.github.jccode.fp.ch04;

import com.github.jccode.fp.common.TailCall;

import java.util.List;

import static com.github.jccode.fp.common.TailCall.*;
import static com.github.jccode.fp.common.CollectionUtilities.*;

/**
 * HeapRecursiveExample
 *
 * @author 01372461
 */
public class HeapRecursiveExample {

    static class Add {
        static TailCall<Integer> addRec(int x, int y) {
            return y == 0 ?
                    ret(x) :
                    sus(() -> addRec(x + 1, y - 1));
        }

        public static void main(String[] args) {
            System.out.println(addRec(10, 30005).eval());
        }
    }

    static class Sum {
        static TailCall<Integer> sumTail(List<Integer> list, Integer acc) {
            return list.isEmpty() ?
                    ret(acc) :
                    sus(() -> sumTail(tail(list), head(list) + acc));
        }

        static Integer sum(List<Integer> list) {
            return sumTail(list, 0).eval();
        }

        public static void main(String[] args) {
            System.out.println(sum(range(1, 10000)));
        }
    }
}
