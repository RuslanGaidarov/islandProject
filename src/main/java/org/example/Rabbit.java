package org.example;

import org.example.Herbivore;

import java.util.List;

public class Rabbit extends Herbivore {
    public Rabbit(List<IslandObject>[][] island, int x, int y) {
        this.islandObjects = island;
        this.x = x;
        this.y = y;
        this.weight = 2.0;
        this.maxQuantity = 150;
        this.speed = 2;
        this.amountOfFood = 0.45;


    }
}
