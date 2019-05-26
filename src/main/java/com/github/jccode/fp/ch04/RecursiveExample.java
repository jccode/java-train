package com.github.jccode.fp.ch04;

import java.util.List;
import static com.github.jccode.fp.common.CollectionUtilities.*;

/**
 * AddRecursiveExample
 *
 * @author 01372461
 */
public class RecursiveExample {

    static class Add {
        static int addV1(int x, int y) {
            while (y > 0) {
                x++;
                y--;
            }
            return x;
        }

        static int addV2(int x, int y) {
            while (y-- > 0) ++x;
            return x;
        }

        static int addV3(int x, int y) {
            while (--y >= 0) ++x;
            return x;
        }

        static int addRec(int x, int y) {
            return y == 0 ?
                    x :
                    addRec(++x, --y);
        }

        public static void main(String[] args) {
            normalTest();
            stackOverflowTest();
        }

        static void normalTest() {
            System.out.println(addV1(10, 5));
            System.out.println(addV2(10, 5));
            System.out.println(addV3(10, 5));
            System.out.println(addRec(10, 5));
        }

        static void stackOverflowTest() {
            System.out.println(addRec(10, 30005));
        }
    }

    static class Sum {
        static Integer sum(List<Integer> list) {
            return list.isEmpty() ?
                    0 :
                    head(list) + sum(tail(list));
        }

        static Integer sumTail(List<Integer> list, Integer acc) {
            return list.isEmpty() ?
                    acc :
                    sumTail(tail(list), head(list) + acc);
        }

        static Integer sum2(List<Integer> list) {
            return sumTail(list, 0);
        }

        public static void main(String[] args) {
            normalTest();
            stackOverflowTest();
        }

        static void normalTest() {
            System.out.println(sum(list(1, 2, 3, 4, 5)));
            System.out.println(sum2(list(1, 2, 3, 4, 5)));
        }

        static void stackOverflowTest() {
            System.out.println(sum2(range(1, 10000)));
        }
    }
}
