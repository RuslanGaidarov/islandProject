package org.example;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IslandTest {
    @Test
    public void testIslandInitialization() {
        Island island = Island.getInstance(100, 20, 100);
        assertEquals(100, island.WIDTH);
        assertEquals(20, island.HEIGHT);
        assertNotNull(island.island);
    }
}