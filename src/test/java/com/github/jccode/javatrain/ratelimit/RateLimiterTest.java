package com.github.jccode.javatrain.ratelimit;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;


/**
 * RateLimiterTest
 *
 * @author 80265925
 * @version 1.0.0
 * @date 2019/8/21
 */
public class RateLimiterTest {

    @Test
    public void test01() {
        RateLimiter rateLimiter = RateLimiter.create(2);
        int start = ZonedDateTime.now().getSecond();
        rateLimiter.acquire(1);
        doSomeLimitedOperation();
        rateLimiter.acquire(1);
        doSomeLimitedOperation();;
        int elapsedTime = ZonedDateTime.now().getSecond() - start;
        assertTrue(elapsedTime <= 1);
    }

    @Test
    public void test02() {
        RateLimiter rateLimiter = RateLimiter.create(100);
        int start = ZonedDateTime.now().getSecond();
        rateLimiter.acquire(100);
        doSomeLimitedOperation();
        int elapsedTime = ZonedDateTime.now().getSecond() - start;
        assertTrue(elapsedTime <= 1);
    }

    @Test
    public void test03() {
        RateLimiter rateLimiter = RateLimiter.create(100);
        int start = ZonedDateTime.now().getSecond();
        IntStream.range(0, 1000).forEach(i -> {
            rateLimiter.acquire();
            doSomeLimitedOperation();
        });
        int elapsedTime = ZonedDateTime.now().getSecond() - start;
        System.out.println("elapsedTime: "+elapsedTime);
        assertTrue(elapsedTime >= 10);
    }

    private void doSomeLimitedOperation() {}
}
