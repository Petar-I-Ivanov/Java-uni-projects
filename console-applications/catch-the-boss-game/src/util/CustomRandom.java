package util;

import java.util.Random;

public class CustomRandom {

    public static int random(int bound) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(bound);
    }
}