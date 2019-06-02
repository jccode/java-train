package com.github.jccode.alg.dp;

import java.util.Arrays;

/**
 * Knapsack (0-1背包问题)
 *
 * @author 01372461
 */
public class Knapsack01 {


    static class Classic {
        /**
         * 动态规划求0-1背包问题;
         * @param m 背包容量
         * @param n 物品数量
         * @param p 价值数组
         * @param w 重量数组
         * @return 动态规划二维表
         */
        public static int[][] dp(int m, int n, int[] p, int[] w) {
            // c[i][v] 表示前i个物品放入背后获得最大价值v.
            int[][] c = new int[n+1][m+1];

            // c[0][v] = 0
            for (int j = 0; j < m+1; j++) {
                c[0][j] = 0;
            }

            // 状态转移方程
            for (int i = 1; i < n+1; i++) {
                for (int j = 1; j < m+1; j++) {
                    if (w[i-1] > j) {
                        c[i][j] = c[i-1][j];
                    } else {
                        c[i][j] = Math.max(c[i-1][j], c[i-1][j-w[i-1]]+p[i-1]);
                    }
                }
            }

            return c;
        }

        /**
         * 打印物品选择表(0,1)
         * @param m 背包容易
         * @param n 物品数量
         * @param p 价值数组
         * @param w 重量数组
         * @param dp 动态规划二维表
         * @return
         */
        public static int[] chooseResult(int m, int n, int[] p, int[] w, int[][] dp) {
            int[] choose = new int[n];
            for (int i = n; i >= 1; i--) {
                if (dp[i][m] == dp[i-1][m]) {
                    choose[i-1] = 0;
                } else {
                    choose[i-1] = 1;
                    m -= w[i-1];
                }
            }
            return choose;
        }
    }

    /**
     * 空间优化版本 O(V)
     *
     * <pre>
     *     def ZeroOnePack(F,C,W)
     *       for v ← V to C
     *         F[v] ← max(F[v],F[v−C]+W)
     * </pre>
     *
     * 则 0-1背包的伪码如下:
     * <pre>
     *     F [0..V] ← 0
     *     for i ← 1 to N
     *       ZeroOnePack(F, Ci, Wi)
     * </pre>
     *
     */
    static class Optimize {

        /**
         * 优化过后的,可以计算出最终结果.但没法求出物品数组,因为中间过程丢失了.
         *
         * @param N 物品数量
         * @param V 背包容易
         * @param C 费用数组
         * @param W 价值数组
         * @return
         */
        public static int[] dpOpt(int N, int V, int[] C, int[] W) {
            // 结果数组
            int F[] = new int[V+1];

            // F[0..V] <- 0
            for (int i = 0; i < V + 1; i++) {
                F[0] = 0;
            }

            // 状态转移方程
            for (int i = 1; i <= N; i++) {
                for (int v = V; v >= C[i-1]; v--) {
                    F[v] = Math.max(F[v], F[v - C[i-1]] + W[i-1]);
                }
            }

            return F;
        }

        /**
         * 经典求法,不作优化
         *
         * <pre>

         * F[0,0..V] ← 0
         * for i ← 1 to N
         *   for v ← Ci to V
         *     F[i,v] ← max{F[i − 1,v],F[i − 1,v − Ci] + Wi}
         *
         * </pre>
         *
         * @param N
         * @param V
         * @param C
         * @param W
         * @return
         */
        public static int[][] dp(int N, int V, int[] C, int[] W) {
            int[][] F = new int[N+1][V+1];

            // F[0..V] <- 0
            for (int i=0; i<V+1; i++) {
                F[0][i] = 0;
            }

            // 状态转移方程
            for (int i = 1; i <= N; i++) {
                for (int v = C[i-1]; v <= V; v++) {
                    F[i][v] = Math.max(F[i-1][v], F[i-1][v-C[i-1]]+W[i-1]);
                }
            }

            return F;
        }

        public static int[] chooseResult(int N, int V, int[] C, int[] W, int[][] F) {
            int[] choose = new int[N];
            int i = N;
            int v = V;
            while (i > 0) {
                if (F[i][v] == F[i-1][v]) {
                    choose[i-1] = 0;
                } else if(F[i][v] == F[i-1][v-C[i-1]] + W[i-1]) {
                    choose[i-1] = 1;
                    v = V - C[i-1];
                }
                i--;
            }
            return choose;
        }

    }


    static void optimizeTest(int w, int n, int[] value, int[] weight) {
        int[] ret = Optimize.dpOpt(n, w, weight, value);

        // print
        System.out.println("\n结果矩阵: ");
        System.out.println(Arrays.toString(ret));
        System.out.println("最高价值: " + ret[w]);

        int[][] r2 = Optimize.dp(n, w, weight, value);
        // print
        System.out.println("结果矩阵: ");
        for (int[] r : r2) {
            System.out.println(Arrays.toString(r));
        }
        System.out.println("物品选择结果:" + Arrays.toString(Optimize.chooseResult(n, w, weight, value, r2)));
    }

    static void classicTest(int w, int n, int[] value, int[] weight) {
        Classic c = new Classic();
        int[][] ret = c.dp(w, n, value, weight);

        // print
        System.out.println("结果矩阵: ");
        for (int[] r : ret) {
            System.out.println(Arrays.toString(r));
        }
        System.out.println("最高价值: " + ret[n][w]);
        // 对应的物品列表
        System.out.println("物品选择结果: " + Arrays.toString(c.chooseResult(w, n, value, weight, ret)));
    }


    public static void main(String[] args) {
        int n = 5, w = 10;
        int[] value = new int[]{6, 3, 5, 4, 6};
        int[] weight = new int[]{2, 2, 6, 5, 4};
        classicTest(w, n, value, weight);
        optimizeTest(w, n, value, weight);
    }
}
