package com.github.jccode.javatrain.concurrent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * CountDownLatch是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行。
 *
 * 举个例子:
 *
 * 1. 裁判(Referee)一声令下(Starting gun)后;
 * 2. 所有的运动员(Athlete)才可以开跑;
 * 3. 所有的运动员全部到达终点后,比赛才算结束;
 * 4. 进行颁奖典礼(Awards).
 */
public class CountDownLatchDemo1 {

    public static void main(String[] args) throws InterruptedException {

        int numAthlete = 10;  // 10个运动员

        CountDownLatch startGun = new CountDownLatch(1);
        CountDownLatch finishSignal = new CountDownLatch(numAthlete);

        final List<Tuple<Integer, Long>> costs = new ArrayList<>(numAthlete);


        for (int i = 0; i < numAthlete; i++) {
            // 所有运动员, 各就各备
            new Thread(new Athlete(i, startGun, finishSignal, costs)).start();
        }

        // 一声令下
        startGun.countDown();

        // 开跑

        // 等大家完成比赛
        finishSignal.await();

        // 举行颁奖典礼
        award(costs);
    }

    private static void award(List<Tuple<Integer, Long>> costs) {
        System.out.println("Awards");
        System.out.println("---------------------");
        costs.sort(Comparator.comparingLong(Tuple::_2));
        // costs.sort(Comparator.<Tuple<Integer, Long>>comparingLong(Tuple::_2).reversed());
        for (int i = 0; i < costs.size(); i++) {
            Tuple<Integer, Long> t = costs.get(i);
            System.out.println("第"+(i+1)+"名\t"+t._1()+"号选手\t"+t._2());
        }
    }

    static class Athlete implements Runnable {

        private int num;
        private CountDownLatch startSignal;
        private CountDownLatch finishSignal;
        private List<Tuple<Integer, Long>> costs;

        public Athlete(int num, CountDownLatch startSignal, CountDownLatch finishSignal, List<Tuple<Integer, Long>> costs) {
            this.num = num;
            this.startSignal = startSignal;
            this.finishSignal = finishSignal;
            this.costs = costs;
        }

        @Override
        public void run() {
            try {
                startSignal.await();
                play();
                finishSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

            costs.add(new Tuple<>(num, cost));
        }
    }


}

class Tuple<A, B> {
    private final A a;
    private final B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A _1() {
        return a;
    }

    public B _2() {
        return b;
    }

    @Override
    public String toString() {
        return "(" + a + ", " + b + ')';
    }
}