package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Predator extends Animal {

    public void eat() {
        //Перебор по списку: Если объект при переборе не хищник, то удалить объект при переборе, насытить хищника на размер его веса.
        List<IslandObject> tempCell = new ArrayList<>(islandObjects[y][x]);
        for (IslandObject islandObject : islandObjects[y][x]) {
            if (islandObject instanceof Herbivore) {
                Random rand = new Random();
                int eatingPoints = rand.nextInt(0, 101);
                int probabilityOfEating = interactionMatrix.get(this.getClass().getSimpleName()).get(islandObject.getClass().getSimpleName());
                if (eatingPoints > probabilityOfEating) {
                    tempCell.remove(islandObject);
                    this.currentAmountOfFood += ((Herbivore) islandObject).weight;
                }


            }
        }
        islandObjects[y][x] = tempCell;

    }
}
