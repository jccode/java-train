package com.github.jccode.javatrain.concurrent;

import java.util.concurrent.*;

public class GetValueFromAnotherThread {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        method1_by_thread_join_to_wait_for_result();
        method2_by_countDownLatch_to_wait_for_result();
        method3_by_future();
    }

    private static void method3_by_future() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            // Do a lot of work here.
            // And finally return the result
            int _result = 1000;
            return _result;
        });

        // Get the final result from future
        System.out.println("The final result is " + future.get());
        executor.shutdown();
    }

    private static void method1_by_thread_join_to_wait_for_result() throws InterruptedException {
        // Using a local final non-primitive type variable to capture the result from another thread.
        final int[] result = new int[1];

        Thread workerThread = new Thread(() -> {
            // Do a lot of work here.
            // And finally return the result
            int _result = 1000;

            // capture the result
            result[0] = _result;

        });

        workerThread.start();

        // Waiting for the result
        workerThread.join();

        // Yes, the result is ready now.
        System.out.println("The final result is " + result[0]);
    }

    private static void method2_by_countDownLatch_to_wait_for_result() throws InterruptedException {

        // Using a local final non-primitive type variable to capture the result from another thread.
        final int[] result = new int[1];

        // In order to capture the result, we need a countDownLatch to wait for the final result
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            // Do a lot of work here.
            // And finally return the result
            int _result = 1000;

            // capture the result
            result[0] = _result;

            latch.countDown();
        }).start();

        // Waiting for the result
        latch.await();

        // Yes, the result is ready now.
        System.out.println("The final result is " + result[0]);
    }
}
