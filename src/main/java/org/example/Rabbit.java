package org.example;

import org.example.Herbivore;

import java.util.List;

public class Rabbit extends Herbivore {
    private static int rabbitCounter = 0;
    private String id;
    public Rabbit(List<IslandObject>[][] island, int x, int y) {
        rabbitCounter++;
        id = "Rabbit" + rabbitCounter;
        this.islandObjects = island;
        this.x = x;
        this.y = y;
        this.weight = 2.0;
        this.maxQuantity = 150;
        this.speed = 2;
        this.amountOfFood = 0.45;
        this.canMultiple = false;
        this.sex = Sex.getRandomSex();

    }
    @Override
    public String getID() {
        return id;
    }



}
