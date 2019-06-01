package com.github.jccode.leetcode;

import java.util.*;

/**
 * LongestConsecutive
 *
 * @author 01372461
 */
public class LongestConsecutive {



    static class SortSolution {
        public int longestConsecutive(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            Arrays.sort(nums);
            List<Integer> ret = new ArrayList<>();
            int len = 1;
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] == nums[i-1] + 1) {
                    len++;
                }
                else if (nums[i] == nums[i-1]) {
                }
                else {
                    if (len != 1) {
                        ret.add(len);
                    }
                    len = 1;
                }
            }
            if (len != 1) ret.add(len);
            return ret.isEmpty() ? 1 : Collections.max(ret);
        }
    }

    /**
     * O(n)
     */
    static class MapSolution {
        public int longestConsecutive(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            Map<Integer, Integer> dict = new HashMap<>();
            int currLen = 0, maxLen = 0;
            for (int num : nums) {
                if (dict.containsKey(num)) continue;

                Integer left = dict.getOrDefault(num - 1, 0);
                Integer right = dict.getOrDefault(num + 1, 0);
                currLen = left + right + 1;

                // 更新区间端点
                dict.put(num, currLen);
                if (left != 0) dict.put(num - left, currLen);
                if (right != 0) dict.put(num + right, currLen);

                if (currLen > maxLen) {
                    maxLen = currLen;
                }
            }
            return maxLen;
        }
    }

    public static void main(String[] args) {
        int[] arr = {100, 4, 200, 1, 3, 2};
        System.out.println(new SortSolution().longestConsecutive(arr));
        System.out.println(new MapSolution().longestConsecutive(arr));

//        {100,4,200,1,3,5,2,6}
        int[] a2 = {1,2,0,1};
        System.out.println(new MapSolution().longestConsecutive(a2));
        System.out.println(new MapSolution().longestConsecutive(new int[]{100,4,200,1,3,5,2,6}));
    }
}
