
package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class AnimalMovementTest {

    @Test
    public void testChooseDirectionMultithreadedWithLargeIsland() throws InterruptedException {
        int islandWidth = 100;
        int islandHeight = 20;
        int numberOfAnimals = 10;
        int numberOfThreads = 12;

        // Инициализируем остров 100x20
        List<IslandObject>[][] island = new ArrayList[islandHeight][islandWidth];
        for (int i = 0; i < islandHeight; i++) {
            for (int j = 0; j < islandWidth; j++) {
                island[i][j] = new ArrayList<>();
            }
        }


        // Создаем 30 животных, распределенных по острову
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < numberOfAnimals; i++) {
            int x = (int) (Math.random() * islandWidth);  // случайная координата X
            int y = (int) (Math.random() * islandHeight); // случайная координата Y
            Animal animal;

            if (i % 2 == 0) {
                animal = new Rabbit(island, x, y);  // Создаем кроликов
            } else {
                animal = new Wolf(island, x, y);   // Создаем волков
            }

            island[y][x].add(animal);  // Добавляем животное в клетку
            animals.add(animal);
        }

        // Инициализируем пул потоков на 12 потоков
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        // Параллельное перемещение всех животных
        for (Animal animal : animals) {
            executor.submit(animal::chooseDirection);
        }

        // Ожидание завершения всех потоков
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Подсчет общего количества животных на острове
        int totalAnimals = 0;
        for (int i = 0; i < islandHeight; i++) {
            for (int j = 0; j < islandWidth; j++) {
                totalAnimals += island[i][j].size();
            }
        }

        // Проверка: количество животных на острове должно остаться равным 30
        assertEquals(numberOfAnimals, totalAnimals);
    }

}
