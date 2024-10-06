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

            List<IslandObject> tempCell = new ArrayList<>(localCell);
            for (Iterator<IslandObject> it = localCell.iterator(); it.hasNext(); ) {
                IslandObject islandObject = it.next();
                if (((islandObject instanceof Plant) || (islandObject instanceof Caterpillar)) && !this.ate) {


                    this.ate = true;
                    tempCell.remove(islandObject);
                    if (islandObject instanceof Plant) {
                        synchronized (this) {
                            Island.plantsEaten++;
                        }
                        this.currentAmountOfFood += ((Plant) islandObject).weight;
                    } else {
                        Random rand = new Random();
                        int eatingPoints = rand.nextInt(0, 101);
                        int probabilityOfEating = interactionMatrix.get(this.getClass().getSimpleName()).get(islandObject.getClass().getSimpleName());
                        if (eatingPoints < probabilityOfEating) {
                            this.ate = true;
                            ((Herbivore) islandObject).isAlive = false;
                            if (((Herbivore) islandObject).weight < this.amountOfFood) {
                                this.currentAmountOfFood += ((Herbivore) islandObject).weight;
                            } else {
                                this.currentAmountOfFood = this.amountOfFood;
                            }

                            Island.animalsEaten++;
                           // System.out.println("Животное " + this.id + " съело животное " + ((Herbivore) islandObject).id);
                        }
                    }
                }
            }
            islandObjects[y][x] = tempCell;
        } finally {
            lock.unlock();
        }
    }
}

