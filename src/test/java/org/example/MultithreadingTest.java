package org.example;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

public class MultithreadingTest {

    @Test
    public void testSimulationMultithreading() throws InterruptedException {
        Island island = Island.getInstance(100, 20, 100);
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 4; i++) {
            executor.execute(island::simulate);
        }

        executor.shutdown();
        boolean finished = executor.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(finished); // Проверяем, что все потоки завершились
    }
}