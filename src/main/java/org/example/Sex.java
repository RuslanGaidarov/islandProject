package org.example;

import java.util.Random;

public enum Sex {
    MALE, FEMALE;

    public static Sex getRandomSex() {
        Random rand = new Random();
        int randomSex = rand.nextInt(2);
        if (randomSex == 0) return FEMALE;
        else return MALE;
    }
}

