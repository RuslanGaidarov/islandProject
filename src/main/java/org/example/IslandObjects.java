package org.example;

import java.util.List;

public enum IslandObjects {
    WOLF, RABBIT, PLANT;

    public IslandObject createObject(List<IslandObject>[][] island, int x, int y) {
        switch (this) {
            case WOLF:
                return new Wolf(island, x, y);
            case RABBIT:
                return new Rabbit(island, x, y);

            case PLANT:
                Plant plant = new Plant(island, x, y);
                return plant;

            default:
                throw new IllegalArgumentException("Unknown animal type: " + this);
        }
    }
}
