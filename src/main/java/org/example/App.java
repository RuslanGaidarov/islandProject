package org.example;

import java.sql.Time;
import java.util.*;

public class App {

    //ДОБАВИТЬ ПОКАЗАТЕЛЬ ВРЕМЕНИ ДЛЯ ДОСТИЖЕНИЯ ФЕРТИЛЬНОСТИ
    static Island myIsland = Island.getInstance(100, 20, 30);

    public static void main(String[] args) {


        myIsland.getInfo();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int current = 0;
            int currentDifference = 0;
            double avgDifference = 0;


            public void run() {
                for (int i = 0; i < myIsland.island.length; i++) {
                    for (int j = 0; j < myIsland.island[0].length; j++) {
                        for (IslandObject islandObject : myIsland.island[i][j]) {
                            if (islandObject instanceof Animal && ((Animal) islandObject).isAlive) {
                                System.out.print(((Animal) islandObject).id + " ");
                            }
                        }
                    }
                }

                myIsland.simulate();
                Island.time++;
                myIsland.getInfo();

                int past = current;
                current = 0;
                for (int i = 0; i < myIsland.island.length; i++) {
                    for (int j = 0; j < myIsland.island[0].length; j++) {
                        for (IslandObject islandObject : myIsland.island[i][j]) {
                            if (islandObject instanceof Animal && ((Animal) islandObject).isAlive) {
                                System.out.print(((Animal) islandObject).id + " ");
                                current++;
                            }
                        }
                    }
                }
                int pastDifference = currentDifference;
               currentDifference = current - past - Island.children.get() + Island.animalsEaten.get();
               avgDifference = (avgDifference + currentDifference) / Island.time;


                System.out.println(" \n ВСЕГО ЖИВОТНЫХ НА ОСТРОВЕ " + current);
                System.out.println("ЩА РАЗНИЦА:" + currentDifference + " ");
                System.out.println("СРЕДНЯЯ РАЗНИЦА:" + avgDifference + " ");
                System.out.println(" ");
                myIsland.setFlags();

            }
        };
        timer.scheduleAtFixedRate(task, 0, 100);
    }
}



