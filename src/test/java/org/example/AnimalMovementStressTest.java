package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalMovementStressTest {

    @Test
    public void testChooseDirectionWithManyObjects() throws InterruptedException {
        int islandWidth = 100;
        int islandHeight = 100;
        int numberOfAnimals = 2000;
        int numberOfThreads = 12;

        // Инициализируем остров
        List<IslandObject>[][] island = new ArrayList[islandHeight][islandWidth];
        for (int i = 0; i < islandHeight; i++) {
            for (int j = 0; j < islandWidth; j++) {
                island[i][j] = new ArrayList<>();
            }
        }

        // Создаем множество животных и распределяем их по острову
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < numberOfAnimals; i++) {
            int x = (int) (Math.random() * islandWidth);
            int y = (int) (Math.random() * islandHeight);
            Animal animal;

            // Чередуем кроликов и волков
            if (i % 2 == 0) {
                animal = new Rabbit(island, x, y);
            } else {
                animal = new Wolf(island, x, y);
            }

            island[y][x].add(animal);
            animals.add(animal);
        }

        // Параллельное перемещение всех животных
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        for (Animal animal : animals) {
            executor.submit(animal::chooseDirection);  // Одновременное перемещение животных
        }

        // Ожидание завершения всех потоков
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Логируем результаты (например, количество перемещений или коллизий)
        System.out.println("Все животные успешно перемещены.");

        // Тестируем, что перемещения прошли без ошибок
        assertTrue(animals.stream().allMatch(Animal::isAlive));
    }
}