package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Duck extends Herbivore {
    public static AtomicInteger duckCounter = new AtomicInteger(0);

    public Duck(List<IslandObject>[][] island, int x, int y) {
        super(60);
        synchronized (Rabbit.class) {
            duckCounter.incrementAndGet();
            this.id = "Duck" + duckCounter;
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

    @Override
    public void eat() {
        lock.lock();
        try {
            List<IslandObject> localCell = new ArrayList<>(islandObjects[y][x]);
            Iterator<IslandObject> iterator = localCell.iterator();
            while (iterator.hasNext()) {
                IslandObject islandObject = iterator.next();
                if (((islandObject instanceof Plant) || (islandObject instanceof Caterpillar)) && !this.ate) {

                    this.ate = true;
                    iterator.remove();  // Удаляем съеденный объект (растение или гусеницу)
                    if (islandObject instanceof Plant) {
                        Island.plantsEaten++;
                        this.currentAmountOfFood += ((Plant) islandObject).weight;
                    } else if (islandObject instanceof Caterpillar) {
                        Island.animalsEaten.incrementAndGet();
                        this.currentAmountOfFood += ((Caterpillar) islandObject).weight;
                    }
                }
            }
            islandObjects[y][x] = localCell;  // Обновляем клетку
        } finally {
            lock.unlock();
        }
    }

}

