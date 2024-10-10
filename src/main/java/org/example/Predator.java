package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class Predator extends Animal {

    public Predator(double amountOfFood) {
        super(amountOfFood);
    }

    public void eat() {
        lock.lock();
        try {
            List<IslandObject> tempCell = new ArrayList<>(islandObjects[y][x]);
            Iterator<IslandObject> iterator = tempCell.iterator();
            while (iterator.hasNext()) {
                IslandObject islandObject = iterator.next();
                if (this.isAlive && islandObject instanceof Herbivore && ((Herbivore) islandObject).isAlive && !this.ate) {

                    Random rand = new Random();
                    int eatingPoints = rand.nextInt(0, 101);
                    int probabilityOfEating = interactionMatrix.get(this.getClass().getSimpleName()).get(islandObject.getClass().getSimpleName());
                    if (eatingPoints < probabilityOfEating) {
                        this.ate = true;
                        ((Herbivore) islandObject).isAlive = false;
                        iterator.remove();  // Удаляем травоядное после поедания
                        System.out.printf("Животное " + ((Herbivore) islandObject).id + " СЪЕДЕНО. ");
                        Island.animalsEaten.incrementAndGet();
                        if (((Herbivore) islandObject).weight < this.amountOfFood) {
                            this.currentAmountOfFood += ((Herbivore) islandObject).weight;
                        } else {
                            this.currentAmountOfFood = this.amountOfFood;
                        }
                    }
                }
            }
            islandObjects[y][x].clear();
            islandObjects[y][x].addAll(tempCell);// Обновляем клетку
        } finally {
            lock.unlock();
        }
    }

}
