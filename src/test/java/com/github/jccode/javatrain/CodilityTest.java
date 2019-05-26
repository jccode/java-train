package com.github.jccode.javatrain;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 * CodilityTest
 *
 * @author 01372461
 */
public class CodilityTest {

    class IntList {
        public int value;
        public IntList next;
    }

    public int lengthOfIntList(IntList L) {
        if (L == null) return 0;
        int len = 1;
        IntList current = L.next;
        while (current != null) {
            len++;
            current = current.next;
        }
        return len;
    }

    @Test
    public void depthPitTest() {
        int[] A = new int[]{0,1,3,-2,0,1,0,-3,2,3};
        assertEquals(depthPit(A), 4);
    }

    public int depthPit(int[] A) {
        if (A == null || A.length < 2) return -1;
        int p = 0, q = 1, r = 2;
        int result = -1;
        outer: while (p < q && q < r && r < A.length) {
            for (int i = p; i < q; i++) {
                if (A[i] > A[q]) {
                    // do nothing
                } else {
                    // move forward
                    p++;
                    if (p == q) q++;
                    if (q == r) r++;
                    continue outer;
                }
            }
            int leftPit = A[p] - A[q];

            for (int j = q; j < r; j++) {
                if (A[j] < A[r]) {
                    // do nothing
                } else {
                    // move forward
                    q++;
                    if (q == r) r++;
                    continue outer;
                }
            }
            int rightPit = A[r] - A[q];

            int pit = Math.min(leftPit, rightPit);
            result = Math.max(pit, result);

            // move forward
            r++;
        }
        return result;
    }

    @Test
    public void minAbsSliceTest() {
        int[] A = new int[]{2,-4,6,-3,9};
        assertEquals( 1, minAbsSlice(A));
    }

    public int minAbsSlice(int[] A) {
        if (A == null || A.length == 0) return -1;
        int result = Math.abs(A[0]);
        for (int q = 0; q < A.length; q++) {
            for (int p = 0; p <= q; p++) {
                // sum slice(p,q)
                int sum = 0;
                for (int i = p; i <= q; i++) {
                    sum += A[i];
                }
                int absSum = Math.abs(sum);
                result = Math.min(result, absSum);
            }
        }
        return result;
    }
}
