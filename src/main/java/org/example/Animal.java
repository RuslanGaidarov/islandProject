package org.example;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Animal implements IslandObject {
    protected String id;
    public int x;
    public int y;
    public List<IslandObject>[][] islandObjects;
    public double weight;
    public int maxQuantity;
    public int speed;
    public int currentSpeed;
    public double amountOfFood;

    public abstract void eat();

    public void multiple() {

    }




    public void chooseDirection() {

        List<String> directions = new ArrayList<>();
        directions.add("UP");
        directions.add("RIGHT");
        directions.add("LEFT");
        directions.add("DOWN");

        Random random = new Random();
        for (int i = 1; i <= currentSpeed; i++) {

            if (x == islandObjects[0].length - 1) {
                if (directions.contains("RIGHT"))
                    directions.remove(directions.indexOf("RIGHT"));
            } else if (x == 0) {
                if (directions.contains("LEFT"))
                    directions.remove(directions.indexOf("LEFT"));
            }
            if (y == islandObjects.length - 1) {
                if (directions.contains("UP"))
                    directions.remove(directions.indexOf("UP"));
            } else if (y == 0) {
                if (directions.contains("DOWN"))
                    directions.remove(directions.indexOf("DOWN"));
            }
        }
        List<IslandObject> currentCell = new ArrayList<>(islandObjects[y][x]);
        List<IslandObject> tempCell = new ArrayList<>(islandObjects[y][x]);
        int tempX = x;
        int tempY = y;
        for (int i = 1; i <= currentSpeed; i++) {

            String direction = directions.get(random.nextInt(directions.size()));

            switch (direction) {
                case "UP":
                    currentCell = islandObjects[y + 1][x];
                    y++;
                    break;
                case "DOWN":
                    currentCell = islandObjects[y - 1][x];
                    y--;
                    break;
                case "RIGHT":
                    currentCell = islandObjects[y][x + 1];
                    x++;
                    break;
                case "LEFT":
                    currentCell = islandObjects[y][x - 1];
                    x--;
                    break;
            }

        }
        tempCell.remove(this);
        currentCell.add(this);
        islandObjects[tempY][tempX] = tempCell;
        islandObjects[y][x] = currentCell;
        System.out.println("Контрольный");

    }
}
