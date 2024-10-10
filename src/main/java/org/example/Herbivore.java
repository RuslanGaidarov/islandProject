package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Herbivore extends Animal {
    public Herbivore(double amountOfFood) {
        super(amountOfFood);
    }

    public void eat() {
        lock.lock();
        try{
        List<IslandObject> localCell = new ArrayList<>(islandObjects[y][x]);

            List<IslandObject> tempCell = new ArrayList<>(localCell);
            for (Iterator<IslandObject> it = localCell.iterator(); it.hasNext(); ) {
                IslandObject islandObject = it.next();
                if (islandObject instanceof Plant && !this.ate) {


                    this.ate = true;
                    tempCell.remove(islandObject);
                    this.currentAmountOfFood += ((Plant) islandObject).weight;

                        Island.plantsEaten++;

                }
            }
            islandObjects[y][x] = tempCell;
        } finally {
            lock.unlock();
        }


    }
}

