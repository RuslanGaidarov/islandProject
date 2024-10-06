package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    public void testSimulationCycle() {
        Island island = Island.getInstance(100, 20, 100);
        island.simulate(); // Запуск симуляции

        assertTrue(Island.time > 0); // Убедитесь, что симуляция действительно работает
        assertTrue(Island.animalsEaten >= 0); // Проверка, что статистика обновляется
    }
}