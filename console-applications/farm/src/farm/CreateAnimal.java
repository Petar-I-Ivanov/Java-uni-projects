package farm;

import farm.animals.*;

import java.util.Random;
import java.util.Scanner;

public class CreateAnimal {

    public static Animal create() {

        int weight     = getAttribute();
        int aggression = getAttribute();
        int speed      = getAttribute();
        int energy     = getAttribute();

        return switch (pickAnimal()) {

            case 1  -> new Cow(weight, aggression, speed, energy);
            case 2  -> new Sheep(weight, aggression, speed, energy);
            case 3  -> new Rooster(weight, aggression, speed, energy);
            case 4  -> new Horse(weight, aggression, speed, energy);
            case 5  -> new Dog(weight, aggression, speed, energy);
            case 6  -> new Camel(weight, aggression, speed, energy);
            default -> null;
        };
    }

    private static int pickAnimal() {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Pick animal:");

            System.out.println("1. Cow");
            System.out.println("2. Sheep");
            System.out.println("3. Rooster");
            System.out.println("4. Horse");
            System.out.println("5. Dog");
            System.out.println("6. Camel");

            choice = scanner.nextInt();

        } while (choice < 1 || choice > 6);

        return choice;
    }

    private static int getAttribute() {
        Random random   = new Random();
        return random.nextInt(10) + 1;
    }
}