package com.github.jccode.alg.dp;

import com.github.jccode.fp.common.Function;

/**
 * Fibonacci
 *
 *  fab(n) = 1 , if n = 0
 *  fab(n) = 1 , if n = 1
 *  fab(n) = fab(n-1) + fab(n-2), if n > 1
 *
 * @author 01372461
 */
public class Fibonacci {

    static class Recur {

        public static int fab(int n) {
            if (n == 0) return 0;
            if (n == 1) return 1;
            return fab(n-1) + fab(n-2);
        }

    }

    static class Dp {

        public static int fab(int n) {
            if (n <= 0) return 0;
            int[] memo = new int[n+1];
            memo[0] = 0;
            memo[1] = 1;
            for (int i = 2; i <= n; i++) {
                memo[i] = memo[i - 1] + memo[i - 2];
            }
            return memo[n];
        }
    }

    static <T, R> R exec(Function<T, R> f, T arg) {
        long start = System.currentTimeMillis();
        R r = f.apply(arg);
        long end = System.currentTimeMillis();
        System.out.println("Call " + f + " consumes [" + (end-start) + "] ms");
        return r;
    }

    public static void main(String[] args) {
        int arg = 43;
        System.out.println(exec(Recur::fab, arg));
        System.out.println(exec(Dp::fab, arg));
    }
}
