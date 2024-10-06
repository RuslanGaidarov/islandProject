package org.example;

import java.sql.Time;
import java.util.*;

public class App {

    //ДОБАВИТЬ ПОКАЗАТЕЛЬ ВРЕМЕНИ ДЛЯ ДОСТИЖЕНИЯ ФЕРТИЛЬНОСТИ
    static Island myIsland = Island.getInstance(100, 20, 960);

    public static void main(String[] args) {


        myIsland.getInfo();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            public void run() {


                myIsland.simulate();
                Island.time++;
                myIsland.getInfo();
                myIsland.setFlags();
                int n = 0;
                for (int i = 0; i < myIsland.island.length; i++) {
                    for (int j = 0; j < myIsland.island[0].length; j++) {
                        for (IslandObject islandObject : myIsland.island[i][j]) {
                            if (islandObject instanceof Animal && ((Animal) islandObject).isAlive) {
                                //System.out.print(((Animal) islandObject).id + " ");
                                n++;
                            }
                        }
                    }
                }

                System.out.println(" \n ВСЕГО ЖИВОТНЫХ НА ОСТРОВЕ " + n);
                System.out.println(" ");



            }
        };
        timer.scheduleAtFixedRate(task, 0, 100);
    }
}



