package tests;

import farm.animals.Animal;
import farm.animals.Sheep;
import farm.enums.FoodTypeEnum;

public class SpecialTests {

    public static void test() {

        System.out.println("Special test one:");
        testOne();

        System.out.println("Special test two:");
        testTwo();
    }

    private static void testOne() {

        Animal animal = new Sheep(0, 0, 0, 0);

        animal.eating(FoodTypeEnum.FIGHTING);
        animal.eating(FoodTypeEnum.FIGHTING);
        animal.eating(FoodTypeEnum.FIGHTING);
    }

    private static void testTwo() {

        Animal animal = new Sheep(1, 1, 1, 1);

        animal.eating(FoodTypeEnum.FIGHTING);
        animal.specialAbility();
        animal.printCharacteristics();

        animal.specialAbility();
        animal.printCharacteristics();

        animal.specialAbility();
        animal.printCharacteristics();

        animal.specialAbility();
        animal.printCharacteristics();
    }
}