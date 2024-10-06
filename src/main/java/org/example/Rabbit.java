package org.example;

import org.example.Herbivore;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Rabbit extends Herbivore {
    public static AtomicInteger rabbitCounter = new AtomicInteger(0);

    public Rabbit(List<IslandObject>[][] island, int x, int y) {
        super(0.45);
        synchronized (Rabbit.class) {
            rabbitCounter.incrementAndGet();
            this.id = "Rabbit" + rabbitCounter;
        }

        this.islandObjects = island;
        this.x = x;
        this.y = y;
        this.weight = 2.0;
        this.maxQuantity = 150;
        this.speed = 2;
        this.canMultiple = false;
        this.sex = Sex.getRandomSex();
        this.maximumHunger = 5;

    }




}
