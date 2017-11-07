package com.github.jccode.javatrain.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Semaphore 信号量, 用来控制同时访问的线程个数.
 *
 * 例子:
 * 1. 工厂有8个工人,却只有5台机器;
 * 2. 每台机器只能同时被一个人使用;
 * 3. 使用完之后,才能被另外的人继续使用.
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        int numMachines = 5;
        int numWorkers = 8;

        Semaphore semaphore = new Semaphore(numMachines);
        for (int i = 0; i < numWorkers; i++) {
            new Thread(new Worker(i, semaphore)).start();
        }
    }

    static class Worker implements Runnable {

        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("WORKER " + num + " is working");
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 30));
                System.out.println("WORKER " + num + " finish.");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
