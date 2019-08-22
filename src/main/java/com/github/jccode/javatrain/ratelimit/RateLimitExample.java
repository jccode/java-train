package com.github.jccode.javatrain.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RateLimitExample
 *
 * @author 80265925
 * @version 1.0.0
 * @date 2019/8/21
 */
public class RateLimitExample {

    private final AtomicInteger threadIndex = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        new RateLimitExample().rateLimitDemo();
    }

    public void rateLimitDemo() throws InterruptedException {
        //ExecutorService executorService = Executors.newFixedThreadPool(5);
        int poolSize = 10;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(poolSize, poolSize,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
                r -> new Thread(r, "worker-" + threadIndex.addAndGet(1)),
                new ThreadPoolExecutor.AbortPolicy());
        RateLimiter rateLimiter = RateLimiter.create(5);
        int taskNum = 10;
        CountDownLatch latch = new CountDownLatch(taskNum);
        long start = System.currentTimeMillis();
        for (int i = 0; i < taskNum; i++) {
            final int j = i;
            threadPoolExecutor.submit(() -> {
                rateLimiter.acquire(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Job " + Thread.currentThread().getName() + " process task " + j + " done.");
                latch.countDown();
            });
        }
        threadPoolExecutor.shutdown();
        latch.await();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Elapsed time: " + elapsedTime);
    }
}
