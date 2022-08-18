package com.geekbrains;

import org.slf4j.Logger;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Car.class);
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private static boolean winnerCheckpoint;
    private CyclicBarrier cb;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier cb) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cb = cb;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        checkWin(this);
        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void checkWin(Car car) {
        if (!winnerCheckpoint) {
            System.out.println(car.name + " WIN");
            winnerCheckpoint = true;
        }
    }

}
