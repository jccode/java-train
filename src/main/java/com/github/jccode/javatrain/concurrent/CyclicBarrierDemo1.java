package com.github.jccode.javatrain.concurrent;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

/**
 * CyclicBarrier 可以实现让一组线程等待至某个状态之后再全部同时执行。
 *
 * 举个例子:
 * 1. 裁判(Referee)一声令下(Starting gun)后;
 * 2. 所有的运动员(Athlete)才可以开跑;
 * 3. 所有的运动员全部到达终点后,等待裁判的终点哨,表示结束;
 * 4. 终点哨完成后,运动员各自步行至颁奖台;
 * 5. 进行颁奖典礼(Awards).
 *
 */
public class CyclicBarrierDemo1 {

    public static void main(String[] args) throws InterruptedException {

        int numAthlete = 10;  // 10个运动员

        CountDownLatch startGun = new CountDownLatch(1);
        CountDownLatch awardSignal = new CountDownLatch(numAthlete); // 颁奖信号,所有运动员到达颁奖地点后.
        CyclicBarrier barrier = new CyclicBarrier(numAthlete, () -> {
            System.out.println("=================");
            System.out.println("Referee: Race is over, pls move towards the awarding ceremony.");
        });


        for (int i = 0; i < numAthlete; i++) {
            // 所有运动员, 各就各备
            new Thread(new Athlete(i, startGun, awardSignal, barrier)).start();
        }

        // 一声令下
        startGun.countDown();

        // 开跑

        // 等大家到达颁奖场馆
        awardSignal.await();

        // 开始颁奖
        award();
    }

    private static void award() {
        System.out.println("Award.");
    }


    static class Athlete implements Runnable {

        private int num;
        private CountDownLatch startSignal;
        private CountDownLatch finishSignal;
        private CyclicBarrier barrier;

        public Athlete(int num, CountDownLatch startSignal, CountDownLatch finishSignal, CyclicBarrier barrier) {
            this.num = num;
            this.startSignal = startSignal;
            this.finishSignal = finishSignal;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                // 等待裁判一声令下
                startSignal.await();

                // 开跑
                play();

                // 等待裁判终点哨
                barrier.await();

                // 步行至颁奖场
                gotoAwardVenue();

                finishSignal.countDown();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void gotoAwardVenue() throws InterruptedException {
            long s = System.currentTimeMillis();
            System.out.println("Runner "+ num + " is moving to venue, at " + s);
            Thread.sleep(ThreadLocalRandom.current().nextInt(1,100));
            System.out.println("Runner "+ num + " arrived.");
        }

        private void play() throws InterruptedException {
            long s = System.currentTimeMillis();
            System.out.println("Runner "+ num + " start at " + s);

            for (int i = 0; i <= 100; i++) {
                System.out.println("Runner " + num + ": " + i + "%");
                Thread.sleep(ThreadLocalRandom.current().nextInt(1,11));
            }

            long cost = System.currentTimeMillis() - s;
            System.out.println("Runner " + num + ": ----------- YEAH. I did it. Cost: " + cost);
        }
    }
}
