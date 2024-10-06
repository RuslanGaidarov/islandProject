package org.example;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Horse extends Herbivore{
    public static AtomicInteger horseCounter = new AtomicInteger(0);
    public Horse(List<IslandObject>[][] island, int x, int y) {
        super(60);
        synchronized (Rabbit.class) {
            horseCounter.incrementAndGet();
            this.id = "Horse" + horseCounter;
        }
        this.islandObjects = island;
        this.x = x;
        this.y = y;
        this.weight = 400.0;
        this.maxQuantity = 20;
        this.speed = 4;
        this.canMultiple = false;
        this.sex = Sex.getRandomSex();
        this.maximumHunger = 9;
    }
}

