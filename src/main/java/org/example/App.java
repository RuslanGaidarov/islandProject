package org.example;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Hello world!
 */
public class App {

//ДОБАВИТЬ ПОКАЗАТЕЛЬ ВРЕМЕНИ ДЛЯ ДОСТИЖЕНИЯ ФЕРТИЛЬНОСТИ
    // ДОБАВИТЬ УЧЕТ МАКСИМАЛЬНОГО КОЛИЧЕСТВА ОБЪЕКТОВ ДАННОГО ТИПА В ЯЧЕЙКЕ ОСТРОВА

    public static void main(String[] args) {
        Island myIsland = new Island(5, 5);

        //myIsland.move();

        System.out.println("Остров создан!");
        myIsland.eat();
        System.out.println("Все кролики съедены!");
    }
}

