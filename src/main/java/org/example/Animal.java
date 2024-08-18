package org.example;


import java.lang.reflect.Constructor;

import java.util.*;

public abstract class Animal implements IslandObject, Identificable {
    public int x;
    public int y;
    public List<IslandObject>[][] islandObjects;
    public double weight;
    public int maxQuantity;
    public int speed;
    public int currentSpeed;
    public double amountOfFood;
    public double currentAmountOfFood;
    public boolean canMultiple;
    Sex sex;

    public static Map <String, Map<String, Integer>> interactionMatrix = new HashMap<>();


    public abstract void eat();

    public Animal multiple() {
        Animal newAnimal = null;
        List<Animal> animalCell = islandObjects[y][x].stream()
                .filter(islandObject -> islandObject instanceof Animal)
                .map(islandObject -> (Animal) islandObject)
                .toList();
        int animalCount = 0;
        for(Animal animal : animalCell) {
            if(animal.getClass().equals(this.getClass())) {
                animalCount++;
            }
        }
        if (animalCount < maxQuantity) {
            for (Animal animal : animalCell) {
                if ((this.canMultiple) && (animal.canMultiple) && (animal.getClass() == this.getClass()) && !(animal.getID().equals(this.getID())) && !(animal.sex.equals(this.sex))) {
                    try {
                        Constructor<?> constructor = this.getClass().getDeclaredConstructor(List[][].class, int.class, int.class);
                        constructor.setAccessible(true);
                        newAnimal = (Animal) constructor.newInstance(islandObjects, x, y);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (newAnimal != null) {
                    this.canMultiple = false;
                    animal.canMultiple = false;
                    return newAnimal;
                }

            }
        }
        canMultiple = true;
        return null;
    }

    public void chooseDirection() {

        List<IslandObject> currentCell = new ArrayList<>(islandObjects[y][x]);
        List<IslandObject> tempCell = currentCell;
        int tempX = x;
        int tempY = y;
        for (int i = 1; i <= currentSpeed; i++) {
            List<String> directions = new ArrayList<>();
            directions.add("UP");
            directions.add("RIGHT");
            directions.add("LEFT");
            directions.add("DOWN");
            if ((x == islandObjects[0].length - 1) || !freeSpaceCheck(islandObjects[y][x + 1])) {
                directions.remove("RIGHT");
            } else if ((x == 0) || !freeSpaceCheck(islandObjects[y][x - 1])) {
                directions.remove("LEFT");
            }
            if ((y == islandObjects.length - 1) || !freeSpaceCheck(islandObjects[y + 1][x])) {
                directions.remove("UP");
            } else if ((y == 0) || !freeSpaceCheck(islandObjects[y - 1][x])) {
                directions.remove("DOWN");
            }
            Random random = new Random();
            String direction = directions.get(random.nextInt(directions.size()));

            switch (direction) {
                case "UP":
                    currentCell = islandObjects[y + 1][x];
                    y++;
                    break;
                case "DOWN":
                    currentCell = islandObjects[y - 1][x];
                    y--;
                    break;
                case "RIGHT":
                    currentCell = islandObjects[y][x + 1];
                    x++;
                    break;
                case "LEFT":
                    currentCell = islandObjects[y][x - 1];
                    x--;
                    break;
                default:
                    break;
            }

        }
        if (!(x == tempX) || !(y == tempY)) {
            tempCell.remove(this);
            currentCell.add(this);
            islandObjects[tempY][tempX] = tempCell;
            islandObjects[y][x] = currentCell;
            this.canMultiple = true;

        }
    }
    public boolean freeSpaceCheck(List<IslandObject> islandObjectsList) {
        int n = 0;
        for(IslandObject islandObject : islandObjectsList) {
            if (islandObject.getClass().equals(this.getClass())) {
                n++;
            }
        }
        return n < maxQuantity;
    }
}
