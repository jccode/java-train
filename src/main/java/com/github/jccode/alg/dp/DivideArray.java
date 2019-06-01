package com.github.jccode.alg.dp;

import java.util.Arrays;
import java.util.Objects;

/**
 * DivideArray 数组分割(均分)问题
 *
 * 有一个没有排序，元素个数为2N的正整数数组。要求把它分割为元素个数为N的两个数组，并使两个子数组的和最接近。
 *
 * 假设数组A[1..2N]所有元素的和是SUM. 因为分割成两半,一半只有N个,且各半的和基本相等.
 * 整个过程只需要关注和不大于SUM/2的那个子数组即可.
 *
 * TODO: 有问题.
 * https://blog.csdn.net/Hackbuteer1/article/details/7638305
 * http://www.cppblog.com/baby-fly/archive/2009/09/24/92392.html
 *
 * @author 01372461
 */
public class DivideArray {

    /**
     * 转换为背包问题,取N件物品,总和不超过(m/2)+1. 最大值是多少.
     *
     * @param n 物品
     * @param s 总和
     * @param a 数组(注意下标是2N)
     * @return
     */
    public static int[][] dp(int n, int s, int[] a) {
        int m = s/2 + 1;
        int[][] c = new int[n+1][m+1]; // c[i][j]:表示取i个物品,总和不超过j时,所能取到的最大值.
        for (int i = 0; i < m+1; i++) {
            c[0][i] = 0;
        }

        for (int e = 1; e < 2 * n + 1; e++) {
            // 状态转移方程
            for (int i = 1; i <= Math.min(n, e); i++) {
                for (int j = 1; j <= m; j++) {
                    if (a[e-1] <= j) {
                        c[i][j] = Math.max(c[i-1][j], c[i-1][j-a[e-1]] + a[e-1]);
                    }
                }
            }
        }

        return c;
    }


    public static void testDivideAlg(int[] arr) {
        Objects.requireNonNull(arr);
        int n = arr.length / 2;
        int sum = Arrays.stream(arr).sum();

        int[][] dp = dp(n, sum, arr);
        for (int[] r : dp) {
            System.out.println(Arrays.toString(r));
        }
        System.out.println(dp[n][sum/2+1]);
    }

    public static void main(String[] args) {
        int[] arr = {1,5,7,8,9,6,3,11,20,17};
        testDivideAlg(arr);
    }
}
