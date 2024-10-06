package org.example;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Caterpillar extends Herbivore{
    public static AtomicInteger caterpillarCounter = new AtomicInteger(0);
    public Caterpillar(List<IslandObject>[][] island, int x, int y) {
        super(0);
        synchronized (Rabbit.class) {
            caterpillarCounter.incrementAndGet();
            this.id = "Catterpillar" + caterpillarCounter;
        }
        this.islandObjects = island;
        this.x = x;
        this.y = y;
        this.weight = 0.01;
        this.maxQuantity = 1000;
        this.speed = 0;
        this.canMultiple = false;
        this.sex = Sex.getRandomSex();
        this.maximumHunger = 3;
    }
}

