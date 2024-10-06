package org.example;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


public class Island {
    public ReentrantLock[][] cellLocks;

    private static Island instance;
    private final Object islandObjectsLock = new Object();
    public static int animalsEaten = 0;
    public static int plantsEaten = 0;
    public static int children = 0;
    public static int moved = 0;
    public static int time = 0;
    public static boolean plantsGrown = false;

    private Island(int width, int height, int numberOfObjects) {
        WIDTH = width;
        HEIGHT = height;
        island = new ArrayList[HEIGHT][WIDTH];
        cellLocks = new ReentrantLock[HEIGHT][WIDTH];
        for (int i = 0; i < island.length; i++) {
            for (int j = 0; j < island[0].length; j++) {
                island[i][j] = new ArrayList<IslandObject>();
                cellLocks[i][j] = new ReentrantLock();
            }
        }

        createObjects(numberOfObjects);
        IslandObjects.createInteractionMatrix();
    }

    public static Island getInstance(int width, int height, int numberOfObjects) {
        if (instance == null) {
            instance = new Island(width, height, numberOfObjects);
        }
        return instance;
    }

    static int WIDTH;
    static int HEIGHT;
    public List<IslandObject>[][] island;

    private void createObjects(int numberOfObjects) {
        int i = 1;
        while (i <= numberOfObjects) {
            for (IslandObjects objectType : IslandObjects.values()) {
                Random random = new Random();
                int x = random.nextInt(WIDTH);
                int y = random.nextInt(HEIGHT);
                IslandObject islandObject = objectType.createObject(island, x, y);
                if (islandObject instanceof Animal) {
                    ((Animal) islandObject).canMultiple = true;
                }
                island[y][x].add(islandObject);
            }
            i++;
        }

    }

    public void simulate() {
        ForkJoinPool pool = new ForkJoinPool();
        IslandSimulationTask task = new IslandSimulationTask(this, 0, HEIGHT, 0, WIDTH);
        pool.invoke(task); // Запуск параллельной обработки
    }

    public void processCell(int i, int j) {
        cellLocks[i][j].lock();
        try {
            List<IslandObject> cell = new ArrayList<>(island[i][j]);
            Iterator<IslandObject> iterator = cell.iterator();

            while (iterator.hasNext()) {
                Random random = new Random();
                IslandObject islandObject = iterator.next();

                if (islandObject instanceof Animal animal) {

                    if (animal.speed != 0) {
                        animal.currentSpeed = random.nextInt(1, animal.speed);
                        animal.chooseDirection();
                    }

                   //animal.eat();

                    Animal child = animal.multiple();

                        if (child != null)
                            synchronized (island[child.y][child.x]) {
                            island[child.y][child.x].add(child);
                                //System.out.print(child.id + " добавлено ");
                    }

                    if (islandObject instanceof Animal && !((Animal) islandObject).isAlive) {
                        iterator.remove();
                    }
                }


            }
        } finally {
            cellLocks[i][j].unlock();
        }
    }


    public void plantGrowth() {
        if (!plantsGrown) {
            plantsGrown = true;
            Random random = new Random();

            for (int i = 0; i < 1; i++) {

                int x = random.nextInt(WIDTH);
                int y = random.nextInt(HEIGHT);
                cellLocks[y][x].lock();
                try {
                    island[y][x].add(new Plant(island, x, y));
                } finally {
                    cellLocks[y][x].unlock();
                }

            }
        }
    }

    public void getInfo() {
        int predators = 0;
        int herbivores = 0;
        int plants = 0;
        for (int i = 0; i < island.length; i++) {
            for (int j = 0; j < island[0].length; j++) {
                cellLocks[i][j].lock();
                try {
                    List<IslandObject> snapshot = new ArrayList<>(island[i][j]);
                    for (IslandObject islandObject : snapshot) {
                        if (islandObject instanceof Predator) {
                            if (((Predator) islandObject).isAlive) {
                                predators++;
                            }
                        } else if (islandObject instanceof Herbivore) {
                            if (((Herbivore) islandObject).isAlive) {
                                herbivores++;
                            }
                        } else if (islandObject instanceof Plant) {
                            plants++;
                        }
                    }
                } finally {
                    cellLocks[i][j].unlock();
                }
            }
        }
        System.out.println("Хищников: " + predators + " Травоядных: " + herbivores + " Растений: " + plants);
//        for (int i = 0; i < island.length; i++) {
//            for (int j = 0; j < island[0].length; j++) {
//                if (!island[i][j].isEmpty()) {
//                    System.out.println("Ячейка " + i + " " + j + ": ");
//                    for(IslandObject islandObject : island[i][j]) {
//                        if (islandObject instanceof Animal) {
//                            System.out.println(((Animal) islandObject).id + " ");
//                        }
//                    }
//                }
//            }
//        }
//        for (int i = 0; i < island.length; i++) {
//            for (int j = 0; j < island[0].length; j++) {
//                for (IslandObject animal : island[i][j]) {
//                    if (animal instanceof Animal) {
//                        System.out.println(((Animal) animal).id + " Координаты: y - " + ((Animal) animal).y + " x - " + ((Animal) animal).x);
//                    }
//                }
//            }
//        }
        System.out.println("Съедено животных в этот день - " + Island.animalsEaten + ", растений - " + Island.plantsEaten);
        System.out.println("Произведено на свет детенышей: " + Island.children);
        System.out.println("Итоговая прибыль населения: " + (Island.children - Island.animalsEaten));
        System.out.println("Время на острове: " + time);
    }

    public void setFlags() {

        for (int i = 0; i < island.length; i++) {
            for (int j = 0; j < island[i].length; j++) {
                ArrayList<IslandObject> islandObjects = new ArrayList<>(island[i][j]);
                Iterator<IslandObject> iterator = islandObjects.iterator();
                while (iterator.hasNext()) {
                    IslandObject islandObject = iterator.next();
                    if (islandObject instanceof Animal) {
                        ((Animal) islandObject).ate = false;
                        ((Animal) islandObject).moved = false;
                        ((Animal) islandObject).multipled = false;
                        ((Animal) islandObject).canMultiple = true;
                        ((Animal) islandObject).changedAmountOfFood = false;
                        Island.children = 0;
                        Island.plantsEaten = 0;
                        Island.animalsEaten = 0;
                        Island.plantsGrown = false;
                    }
                }
                island[i][j] = new ArrayList<>(islandObjects);
            }
        }

    }

}

