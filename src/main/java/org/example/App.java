package org.example;

import java.util.ArrayList;
import java.util.List;



/**
 * Hello world!
 *
 */
public class App 
{


    public static void main( String[] args )
    {
        Island island = new Island(5, 5);
        System.out.println("Остров создан!");
        island.move();
        System.out.println("Педики перемещены");

    }

}
