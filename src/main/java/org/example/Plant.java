package org.example;

import java.util.List;

public class Plant implements IslandObject {
    public int x;
    public int y;
    public List<IslandObject>[][] islandObjects;
    public double weight;
    public int maxQuantity = 200;
    private static int plantCounter = 0;
    private String id;


    public Plant(List<IslandObject>[][] island, int x, int y) {
        plantCounter++;
        id = "Plant" + plantCounter;

        this.islandObjects = island;
        this.x = x;
        this.y = y;
        this.weight = 2.0;
        this.maxQuantity = 200;
    }

    public static int getPlantCounter() {
        return plantCounter;
    }
}
