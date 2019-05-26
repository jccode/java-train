package com.github.jccode.fp.ch04;

import com.github.jccode.fp.common.Function;
import com.github.jccode.fp.common.Memoizer;

/**
 * MemoizerExample
 *
 * @author 01372461
 */
public class MemoizerExample {

    private static Function<Integer, Integer> longCalculation = x -> {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) { }
        return x * 2;
    };

    private static void runTest(Function<Integer, Integer> f, Integer x) {
        long start = System.currentTimeMillis();
        Integer result = f.apply(x);
        long end = System.currentTimeMillis();
        System.out.println("logCalculation with " + x + ", Result is " + result + ", Consumer " + (end - start) + "ms");
    }

    public static void main(String[] args) {
        runTest(longCalculation, 1);
        runTest(longCalculation, 1);
        runTest(longCalculation, 1);

        System.out.println("-------------------");

        Function<Integer, Integer> memoLongCalcution = Memoizer.memoize(longCalculation);
        runTest(memoLongCalcution, 1);
        runTest(memoLongCalcution, 1);
        runTest(memoLongCalcution, 1);
    }
}
