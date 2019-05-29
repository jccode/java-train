package com.github.jccode.javatrain;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * SortTest
 *
 * @author 01372461
 */
public class SortTest {

    public int[] quickSort(int[] a) {
        if (a == null) return a;
        if (a.length <= 1) return a;

        int idx = new Random().nextInt(a.length);
        int n = a[idx];

        int[][] ret = splitArray(a, n);
        int[] lesser = ret[0];
        int[] equaler = ret[1];
        int[] greater = ret[2];

        int[] sortedLesser = quickSort(lesser);
        int[] sortedGreater = quickSort(greater);

        int[] result = new int[a.length];
        int iResult = 0;
        System.arraycopy(sortedLesser, 0, result, 0, sortedLesser.length);
        iResult += sortedLesser.length;
        System.arraycopy(equaler, 0, result, iResult, equaler.length);
        iResult += equaler.length;
        System.arraycopy(sortedGreater, 0, result, iResult, greater.length);

        return result;
    }

    private int[][] splitArray(int[] a, int n) {
        int[] lesser = new int[a.length];
        int[] equaler = new int[a.length];
        int[] greater = new int[a.length];
        int iLesser = 0, iEqualer = 0, iGreater = 0;
        for (int i = 0, len = a.length; i < len; i++) {
            int e = a[i];
            if (e == n) {
                equaler[iEqualer++] = e;
            } else if (e < n) {
                lesser[iLesser++] = e;
            } else {
                greater[iGreater++] = e;
            }
        }
        return new int[][]{Arrays.copyOf(lesser, iLesser), Arrays.copyOf(equaler, iEqualer), Arrays.copyOf(greater, iGreater)};
    }

    @Test
    public void testQuickSort() {
        int[] a = new int[]{3,42,5,6,20,7,1,234,21,22,344,522,11,22,34,55,11};
        int[] sorted = quickSort(a);
        System.out.println("Before:"+Arrays.toString(a));
        System.out.println("After :"+Arrays.toString(sorted));
    }
}
