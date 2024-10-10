package org.example;


import java.lang.reflect.Constructor;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Animal implements IslandObject {
    protected final ReentrantLock lock = new ReentrantLock();

    public String id;
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
    public boolean isAlive = true;
    public boolean ate = false;
    public boolean moved = false;
    public boolean multipled = false;
    Sex sex;
    public int hunger;
    protected int maximumHunger;
    public boolean changedAmountOfFood = false;


    protected final Object islandObjectsLock1 = new Object();


    public static Map<String, Map<String, Integer>> interactionMatrix = new HashMap<>();


    public Animal(double amountOfFood) {
        this.amountOfFood = amountOfFood;
        this.currentAmountOfFood = 0.5 * amountOfFood;
    }

    public abstract void eat();

    public Animal multiple() {
        lock.lock();
        try {
            List<IslandObject> localList = new ArrayList<>(islandObjects[y][x]);
            Animal newAnimal = null;
            List<Animal> animalCell = new ArrayList<>();
            for (IslandObject islandObject : localList) {
                if (islandObject instanceof Animal) {
                    animalCell.add((Animal) islandObject);
                }
            }
            if (!freeSpaceCheck(islandObjects[y][x])) {
                return null;
            }
            if (this.isAlive) {
                for (Animal animal : animalCell) {
                    if ((this.canMultiple) && (animal.canMultiple) && (animal.getClass() == this.getClass()) && !(animal.equals(this)) &&
                            !(animal.sex.equals(this.sex)) && !this.multipled) {
                        this.multipled = true;
                        animal.multipled = true;
                        //System.out.print("Животное " + this.id + " ЕБАЛОСЬ С " + animal.id);
                        try {
                            Constructor<?> constructor = this.getClass().getDeclaredConstructor(List[][].class, int.class, int.class);
                            constructor.setAccessible(true);
                            newAnimal = (Animal) constructor.newInstance(islandObjects, x, y);

                           // System.out.print(newAnimal.id + " создано ");


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (newAnimal != null) {
                            this.canMultiple = false;
                            animal.canMultiple = false;
                            Island.children.incrementAndGet();

                            return newAnimal;


                        }

                    }

                }
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    public void chooseDirection() {
        lock.lock();
        try {
            if (this.isAlive && !this.moved) {
                this.moved = true;
                int tempX = x;
                int tempY = y;
                for (int i = 1; i <= currentSpeed; i++) {
                    List<String> directions = new ArrayList<>(List.of("UP", "RIGHT", "LEFT", "DOWN"));

                    if (x == islandObjects[0].length - 1 || !freeSpaceCheck(islandObjects[y][x + 1])) {
                        directions.remove("RIGHT");
                    }
                    if (x == 0 || !freeSpaceCheck(islandObjects[y][x - 1])) {
                        directions.remove("LEFT");
                    }
                    if (y == 0 || !freeSpaceCheck(islandObjects[y - 1][x])) {
                        directions.remove("UP");
                    }
                    if (y == islandObjects.length - 1 || !freeSpaceCheck(islandObjects[y + 1][x])) {
                        directions.remove("DOWN");
                    }
                    if (!directions.isEmpty()) {
                        Random random = new Random();
                        String direction = directions.get(random.nextInt(directions.size()));
                        //System.out.println("Животное " + this.id + " выбрало направление " + direction);
                        switch (direction) {
                            case "UP":
                                tempY--;
                                break;
                            case "DOWN":
                                tempY++;
                                break;
                            case "RIGHT":
                                tempX++;
                                break;
                            case "LEFT":
                                tempX--;
                                break;
                        }


                        if (tempX >= 0 && tempX < islandObjects[0].length && tempY >= 0 && tempY < islandObjects.length) {
                            synchronized (islandObjects[y][x]) {

                                //if (islandObjects[y][x].contains(this)) {
                                    islandObjects[y][x].remove(this);
                               // } else {
                                  //  System.out.println("Элемент не найден в списке.");
                              //  }
                            }

                            x = tempX;
                            y = tempY;

                            synchronized (islandObjects[tempY][tempX]) {
                                islandObjects[tempY][tempX].add(this);
                            }
                        }

                    }
                }
            }


        } finally {
            lock.unlock();
        }

    }

    public boolean freeSpaceCheck(List<IslandObject> islandObjectsList) {
        lock.lock();
        try {
            Iterator<IslandObject> iterator = new ArrayList<>(islandObjectsList).iterator();
            int n = 0;
            while (iterator.hasNext()) {
                IslandObject islandObject = iterator.next();
                if (islandObject == null) {
                    continue;
                }
                if (islandObject.getClass().equals(this.getClass())) {
                    n++;
                }
            }
            return n < maxQuantity;
        } finally {
            lock.unlock();
        }
    }

    public void changeCurrentAmountOfFood() {
        lock.lock();
        try {
            if (this.isAlive && !changedAmountOfFood) {
                changedAmountOfFood = true;
                if ((this.currentAmountOfFood > 0)) {
                    this.hunger = 0;
                    this.currentAmountOfFood = this.currentAmountOfFood - 0.2 * amountOfFood;
                } else {
                    this.hunger++;
                    if (this.hunger >= this.maximumHunger) {
                        this.isAlive = false;
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean isAlive() {
        return isAlive;
    }
}
