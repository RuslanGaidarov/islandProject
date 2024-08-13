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

//При проверке появляется дополнительный объект, дебажить и проверять

    public static void main(String[] args) {
        Island myIsland = new Island(5, 5);

        //myIsland.move();
        myIsland.island[0][0].add(new Wolf(myIsland.island,0,0));
        myIsland.island[0][0].add(new Wolf(myIsland.island,0,0));
        System.out.println("Остров создан!");
          myIsland.multiple();
        System.out.println("Все.");
    }
}

