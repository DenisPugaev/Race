package com.geekbrains;

import com.geekbrains.stage.Road;
import com.geekbrains.stage.Tunnel;

import java.util.concurrent.CyclicBarrier;
/*ЗАДАЧА:
 Организуем гонки:
 Все участники должны стартовать одновременно, несмотря на то, что на подготовку у каждого из них уходит разное время.
 В туннель не может заехать одновременно больше половины участников (условность).
 Попробуйте всё это синхронизировать.
Только после того как все завершат гонку, нужно выдать объявление об окончании.
Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.*/

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static final int HALF_CARS_COUNT = CARS_COUNT / 2;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        CyclicBarrier cb = new CyclicBarrier(5);
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cb);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            cb.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}


