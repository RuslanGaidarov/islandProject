package org.example;

import javax.sound.midi.Soundbank;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class Animal implements IslandObject, Identificable {
    public int x;
    public int y;
    public List<IslandObject>[][] islandObjects;
    public double weight;
    public int maxQuantity;
    public int speed;
    public int currentSpeed;
    public double amountOfFood;
    public boolean canMultiple;
    Sex sex;

    public abstract void eat();

    public Animal multiple() {
        Animal newAnimal = null;
        List<Animal> animalCell = islandObjects[y][x].stream()
                .filter(islandObject -> islandObject instanceof Animal)
                .map(islandObject -> (Animal) islandObject)
                .collect(Collectors.toList());
        for (Animal animal : animalCell) {
            if ((animal.getClass() == this.getClass()) && !(animal.getID().equals(this.getID()))) {
                try {
                    Constructor<?> constructor = this.getClass().getDeclaredConstructor(List[][].class, int.class, int.class);
                    constructor.setAccessible(true);
                    newAnimal = (Animal) constructor.newInstance(islandObjects, x, y);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (newAnimal != null) {
                canMultiple = false;
                return newAnimal;
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
            if (x == islandObjects[0].length - 1) {
                if (directions.contains("RIGHT"))
                    directions.remove(directions.indexOf("RIGHT"));
            } else if (x == 0) {
                if (directions.contains("LEFT"))
                    directions.remove(directions.indexOf("LEFT"));
            }
            if (y == islandObjects.length - 1) {
                if (directions.contains("UP"))
                    directions.remove(directions.indexOf("UP"));
            } else if (y == 0) {
                if (directions.contains("DOWN"))
                    directions.remove(directions.indexOf("DOWN"));
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
}
