package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Herbivore extends Animal {
    public void eat() {
        List<IslandObject> tempCell = new ArrayList<>(islandObjects[y][x]);
        for (IslandObject islandObject : islandObjects[y][x]) {
            if (islandObject instanceof Plant) {
                tempCell.remove(islandObject);
                this.currentAmountOfFood += ((Plant) islandObject).weight;
            }
        }
        islandObjects[y][x] = tempCell;
    }
}

