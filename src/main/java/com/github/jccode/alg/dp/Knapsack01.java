package com.github.jccode.alg.dp;

import java.util.Arrays;

/**
 * Knapsack (0-1背包问题)
 *
 * @author 01372461
 */
public class Knapsack01 {

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


    public static void main(String[] args) {
        int n = 5, w = 10;
        int[] value = new int[]{6, 3, 5, 4, 6};
        int[] weight = new int[]{2, 2, 6, 5, 4};
        int[][] ret = dp(w, n, value, weight);

        // print
        System.out.println("结果矩阵: ");
        for (int[] r : ret) {
            System.out.println(Arrays.toString(r));
        }

        System.out.println("\n最高价值: " + ret[n][w]);

        // 对应的物品列表
        System.out.println("\n物品选择结果: " + Arrays.toString(chooseResult(w, n, value, weight, ret)));
    }
}
