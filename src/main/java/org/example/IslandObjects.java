package org.example;

import java.util.HashMap;
import java.util.List;

public enum IslandObjects {
    WOLF, RABBIT, DEER, HORSE, DUCK, CATERPILLAR, PLANT;

    public IslandObject createObject(List<IslandObject>[][] island, int x, int y) {
        switch (this) {
            case WOLF:
                return new Wolf(island, x, y);
            case RABBIT:
                return new Rabbit(island, x, y);
            case DEER:
                return new Deer(island, x, y);
            case HORSE:
                return new Horse(island, x, y);
            case DUCK:
                return new Duck(island, x, y);
            case CATERPILLAR:
                return new Caterpillar(island, x, y);
            case PLANT:

                return new Plant(island, x, y);


            default:
                throw new IllegalArgumentException("Unknown animal type: " + this);
        }
    }

    public static void createInteractionMatrix() {
        Animal.interactionMatrix.put("Wolf", new HashMap<>());
        Animal.interactionMatrix.put("Duck", new HashMap<>());
        Animal.interactionMatrix.get("Wolf").put("Rabbit", 60);
        Animal.interactionMatrix.get("Wolf").put("Deer", 15);
        Animal.interactionMatrix.get("Wolf").put("Horse", 10);
        Animal.interactionMatrix.get("Wolf").put("Duck", 40);
        Animal.interactionMatrix.get("Wolf").put("Caterpillar", 0);
        Animal.interactionMatrix.get("Duck").put("Caterpillar", 90);
    }
}
