package util;

import java.util.Random;

public class CustomRandom {

    private CustomRandom() {
        // private constructor that doesn't allow new instances of CustomRandom
    }

    public static int dice(int bound) {
        Random random = new Random();
        return random.nextInt(bound) + 1;
    }

    public static int random(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }
}