package com.github.jccode.alg;

public class BinarySearch {

    public static void main(String[] args) {
        int[] a = new int[]{1,2,3,5,6,7,8,9,11,34,56,66};
        System.out.println(bs(a, 7));
        System.out.println(bs(a, 11));
        System.out.println(bs(a, 30));

        System.out.println("------------");

        System.out.println(bs2(a, 7));
        System.out.println(bs2(a, 11));
        System.out.println(bs2(a, 30));
    }

    public static int bs(int[] a, int t) {
        if (a == null || a.length == 0) return -1;
        return bsRecursive(a, t, 0, a.length-1);
    }

    private static int bsRecursive(int[] a, int t, int l, int h) {
        if (l > h) return -1;

        int m = (l + h) / 2;
        if (a[m] == t) {
            return m;
        } else if (a[m] > t){
            return bsRecursive(a, t, l, m-1);
        } else {
            return bsRecursive(a, t, m+1, h);
        }
    }

    public static int bs2(int[] a, int t) {
        if (a == null || a.length == 0) return -1;
        int l = 0, h = a.length - 1;
        while (l <= h) {
            int m = (l + h) / 2;
            if (a[m] == t) {
                return m;
            } else if(a[m] > t) {
                h = m-1;
            } else {
                l = m+1;
            }
        }
        return -1;
    }
}
