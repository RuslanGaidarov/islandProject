package org.example;

import java.util.List;
import java.util.Random;

public class Wolf extends Predator {
  private static int wolfCounter = 0;
  private String id;
  public Wolf(List<IslandObject>[][] island, int x, int y)
  {
    wolfCounter++;
    id = "Wolf" + wolfCounter;
    this.islandObjects = island;
    this.x = x;
    this.y = y;
    this.weight = 50.0;
    this.maxQuantity = 30;
    this.speed = 3;
    this.amountOfFood = 8;
    this.canMultiple = false;
    this.sex = Sex.getRandomSex();


  }
  @Override
  public String getID() {
    return id;
  }
}
