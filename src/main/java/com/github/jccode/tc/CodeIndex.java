package com.github.jccode.tc;


/**
 * https://cloud.tencent.com/info/d4c6a51ee7c0ea183235f535b69f08cf.html
 *
 */
public class CodeIndex {

    public static void main(String[] args) {
        System.out.println(stringIndex("a"));
        System.out.println(stringIndex("aa"));
        System.out.println(stringIndex("aaa"));
        System.out.println(stringIndex("aaaa"));
        System.out.println(stringIndex("aaab"));
        System.out.println(stringIndex("caab"));
    }

    static int stringIndex(String s) {
        char[] chars = s.toCharArray();
        int base4 = 1;
        int base3 = 25 * base4 + 1;
        int base2 = 25 * base3 + 1;
        int base1 = 25 * base2 + 1;
        int[] bases = new int[]{base1, base2, base3, base4};

        int code = 0;
        for (int i = 0; i < chars.length; i++) {
            code += (chars[i] - 'a') * bases[i % 4] + 1;
        }

        return code - 1;
    }
}
