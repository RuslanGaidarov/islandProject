package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Island {
    static int WIDTH;
    static int HEIGHT;
    public List<IslandObject>[][] island;

    public Island(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        island = new ArrayList[HEIGHT][WIDTH];
        for (int i = 0; i < island.length; i++) {
            for (int j = 0; j < island[0].length; j++) {
                island[i][j] = new ArrayList<IslandObject>();
            }
        }
        createObjects();
        createInteractionMatrix();
    }

    private void createObjects() {
//        int numberOfObjects = 2;
//        int i = 1;
//        while (i <= numberOfObjects) {
//            for (IslandObjects objectType : IslandObjects.values()) {
//                Random random = new Random();
//                int x = random.nextInt(WIDTH);
//                int y = random.nextInt(HEIGHT);
//                IslandObject islandObject = objectType.createObject(island, x, y);
//                if (islandObject instanceof Animal) {
//                    ((Animal) islandObject).canMultiple = true;
//                }
//
//                island[y][x].add(islandObject);
//            }
//            i++;
//        }
        /*ТЕСТИРУЮЩИЙ КОД*/
        island[0][0].add(new Wolf(island, 0, 0));
        island[0][0].add(new Wolf(island, 0, 0));
        island[0][0].add(new Rabbit(island, 0, 0));
        island[0][0].add(new Rabbit(island, 0, 0));
        for (IslandObject islandObject : island[0][0]) {
            if (islandObject instanceof Animal) {
                ((Animal) islandObject).canMultiple = true;
            }
        }

    }

    /*Тестирующий метод*/
    public void move() {
        List<String> objects = new ArrayList<>();
        for (int y = 0; y < island.length; y++) {
            for (int x = 0; x < island[0].length; x++) {
                if (island[y][x].isEmpty()) {
                    continue;
                }
                List<Animal> cell = island[y][x].stream()
                        .filter(islandObject -> islandObject instanceof Animal)
                        .map(islandObject -> (Animal) islandObject)
                        .collect(Collectors.toList());
                ListIterator<Animal> iterator = cell.listIterator();

                while (iterator.hasNext()) {
                    Random random = new Random();

                    Animal currentObject = iterator.next();
                    Identificable currentObjectID = (Identificable) currentObject;

                    if (!objects.contains(currentObjectID.getID())) {
                        currentObject.currentSpeed = random.nextInt(1, currentObject.speed);
                        currentObject.chooseDirection();
                        objects.add(currentObjectID.getID());
                    }


                }
            }
        }

    }

    /*Тестирующий метод*/
    public void multiple() {
        for (int y = 0; y < island.length; y++) {
            for (int x = 0; x < island[0].length; x++) {
                List<Animal> children = new ArrayList<>();
                List<Animal> cell = island[y][x].stream()
                        .filter(islandObject -> islandObject instanceof Animal)
                        .map(islandObject -> (Animal) islandObject)
                        .collect(Collectors.toList());
                ListIterator<Animal> iterator = cell.listIterator();
                while (iterator.hasNext()) {
                    Animal currentObject = iterator.next();
                    Animal child = currentObject.multiple();
                    if (child != null) {
                        children.add(child);
                    }
                }
                cell.addAll(children);
                island[y][x].addAll(children);


            }

        }
    }

    public void eat() {
        for (int y = 0; y < island.length; y++) {
            for (int x = 0; x < island[0].length; x++) {
                List<Animal> cell = island[y][x].stream()
                        .filter(islandObject -> islandObject instanceof Animal)
                        .map(islandObject -> (Animal) islandObject)
                        .collect(Collectors.toList());
                ListIterator<Animal> iterator = cell.listIterator();
                while (iterator.hasNext()) {
                    Animal currentObject = iterator.next();
                    currentObject.eat();
                }
            }
        }

    }

    public void createInteractionMatrix() {
        Animal.interactionMatrix.put("Wolf", new HashMap<>());
        Animal.interactionMatrix.get("Wolf").put("Rabbit", 60);
    }

    public void plantGrowth() {
        Random random = new Random();

        for (int i = 0; i < 2; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            island[y][x].add(new Plant(island, x, y));
        }
    }
}


