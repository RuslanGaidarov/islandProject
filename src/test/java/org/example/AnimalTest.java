package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    @Test
    public void testAnimalMovement() {
        Island island = Island.getInstance(5, 5, 0);
        Wolf wolf = new Wolf(island.island, 2, 2);
        island.island[2][2].add(wolf);
        wolf.currentSpeed = 3;
        int initialX = wolf.x;
        int initialY = wolf.y;

        wolf.chooseDirection();

        assertTrue(wolf.x >= 0 && wolf.x < 100);
        assertTrue(wolf.y >= 0 && wolf.y < 20);
        assertFalse(wolf.x == initialX && wolf.y == initialY);
    }
}