package org.example;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Deer extends Herbivore{
    public static AtomicInteger deerCounter = new AtomicInteger(0);
    public Deer(List<IslandObject>[][] island, int x, int y) {
        super(50);
        synchronized (Rabbit.class) {
            deerCounter.incrementAndGet();
            this.id = "Deer" + deerCounter;
        }
        this.islandObjects = island;
        this.x = x;
        this.y = y;
        this.weight = 300.0;
        this.maxQuantity = 20;
        this.speed = 4;
        this.canMultiple = false;
        this.sex = Sex.getRandomSex();
        this.maximumHunger = 8;
    }
}

