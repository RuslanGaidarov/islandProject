package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalEatingStressTest {

    @Test
    public void testEatWithManyAnimals() {
        int islandWidth = 100;
        int islandHeight = 100;
        int numberOfAnimals = 100000;

        // Инициализируем остров
        List<IslandObject>[][] island = new ArrayList[islandHeight][islandWidth];
        for (int i = 0; i < islandHeight; i++) {
            for (int j = 0; j < islandWidth; j++) {
                island[i][j] = new ArrayList<>();
            }
        }

        // Создаем множество травоядных животных и распределяем их в одной клетке с растениями
        List<Animal> animals = new ArrayList<>();
        int x = 50, y = 50;  // Все животные и растения находятся в одной клетке
        for (int i = 0; i < numberOfAnimals; i++) {
            Herbivore rabbit = new Rabbit(island, x, y);
            island[y][x].add(rabbit);
            animals.add(rabbit);
        }

        // Добавляем множество растений в ту же клетку
        for (int i = 0; i < 100; i++) {
            Plant plant = new Plant(island, x, y);
            island[y][x].add(plant);
        }

        // Все животные пытаются поесть
        for (Animal animal : animals) {
            animal.eat();
        }

        // Проверяем, что некоторые животные смогли поесть
        long animalsThatAte = animals.stream().filter(a -> a.currentAmountOfFood > 0).count();
        assertTrue(animalsThatAte > 0);  // Убеждаемся, что животные ели
    }
}
