package org.example;

import java.util.List;

public class Plant implements IslandObject {
    public int x;
    public int y;
    public List<IslandObject>[][] islandObjects;
    public double weight;
    public int maxQuantity = 200;
    public int plantCounter = 0;

    public Plant(List<IslandObject>[][] island, int x, int y)
    {
        plantCounter++;
        this.islandObjects = island;
        this.x = x;
        this.y = y;
        this.weight = 1.0;
        this.maxQuantity = 200;
    }
}
