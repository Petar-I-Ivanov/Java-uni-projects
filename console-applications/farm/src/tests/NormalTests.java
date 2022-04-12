package tests;

import farm.CreateAnimal;
import farm.animals.Animal;
import farm.enums.FoodTypeEnum;

public class NormalTests {

    public static void test() {

        System.out.println("Normal test one:");
        testOne();

        System.out.println("Normal test two:");
        testTwo();

        System.out.println("Normal test three:");
        testThree();
    }

    private static void testOne() {

        Animal animal = CreateAnimal.create();
        animal.printCharacteristics();
        animal.specialAbility();
        animal.printCharacteristics();
    }

    private static void testTwo() {

        Animal animal = CreateAnimal.create();
        animal.printCharacteristics();
        animal.eating(FoodTypeEnum.FIGHTING);
        animal.specialAbility();
        animal.printCharacteristics();
    }

    private static void testThree() {

        Animal animal = CreateAnimal.create();
        animal.eating(FoodTypeEnum.FOOD);
        animal.eating(FoodTypeEnum.FIGHTING);
        animal.printCharacteristics();
    }
}