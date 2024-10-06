package org.example;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Wolf extends Predator {
    public static AtomicInteger wolfCounter = new AtomicInteger();


    public Wolf(List<IslandObject>[][] island, int x, int y) {
        super(8);
        synchronized (Wolf.class) {
            wolfCounter.incrementAndGet();
            this.id = "Wolf" + wolfCounter;
        }
        this.islandObjects = island;
        this.x = x;
        this.y = y;
        this.weight = 50.0;
        this.maxQuantity = 30;
        this.speed = 3;
        this.canMultiple = false;
        this.sex = Sex.getRandomSex();
        this.hunger = 0;
        this.maximumHunger = 7;


    }
}
