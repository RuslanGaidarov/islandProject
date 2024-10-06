package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalEatingTest {

    @Test
    public void testAnimalEating() {
        Island island = Island.getInstance(100, 20, 100);
        Wolf wolf = new Wolf(island.island, 5, 5);
        Rabbit rabbit = new Rabbit(island.island, 5, 5);

        island.island[5][5].add(rabbit);
        wolf.eat();

        assertTrue(wolf.ate); // Проверяем, что волк съел кролика
        assertFalse(rabbit.isAlive); // Проверяем, что кролик мертв
        assertTrue(wolf.currentAmountOfFood > 0); // Проверяем, что количество еды у волка увеличилось
    }
}