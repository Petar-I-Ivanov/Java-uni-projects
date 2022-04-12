import java.util.Scanner;
import java.util.Random;

public class Main {

    private static final int LENGTH = pickLength();
    private static int[] array = new int[LENGTH];

    public static void main(String[] args) {

        mainAction();
    }

    private static void mainAction() {

        fillArray();

        System.out.print(LENGTH + " на брой числа: ");
        printOfArray();

        int menuPick;

        do {
            menuPick = pickMenuChoice();
            menuAction(menuPick);

        } while (menuPick > 0 && menuPick < 12);
    }

    private static int pickLength() {

        Scanner scanner = new Scanner(System.in);
        int numberForLength;

        do {
            System.out.print("Въведете колко на брой числа искате в масива: ");
            numberForLength = scanner.nextInt();

        } while (numberForLength < 1);

        return numberForLength;
    }

    private static int pickMenuChoice() {

        Scanner scanner = new Scanner(System.in);
        int menuChoice = 0;

        while (menuChoice < 1 || menuChoice > 12) {

            System.out.println("1. Сортиране на въведените числа във възходящ ред.");
            System.out.println("2. Сортиране на въведените числа в низходящ ред.");
            System.out.println("3. Търсене на позиция на конкретно число.");
            System.out.println("4. Разбъркване на числата.");
            System.out.println("5. Изчисляване на сбора на всички числа.");
            System.out.println("6. Намиране на най-голямото.");
            System.out.println("7. Намиране на най-малкото.");
            System.out.println("8. Намиране на средно-аритметично на числата.");
            System.out.println("9. Проверка за симетричност на масива от числа.");
            System.out.println("10. Обръщане на масива от числа.");
            System.out.println("11. Визуализирай въведените числа.");
            System.out.println("12. Изход.");

            menuChoice = scanner.nextInt();
        }

        return menuChoice;
    }

    private static void menuAction(int menuChoice) {

        switch (menuChoice) {

            case 1  -> sort(true);
            case 2  -> sort(false);
            case 3  -> search();
            case 4  -> shuffle();
            case 5  -> System.out.println("Сбора от всички числа е " + sum() + ".");
            case 6  -> System.out.println("Най-голямто число е " + minMax(false) + ".");
            case 7  -> System.out.println("Най-малкото число е " + minMax(true) + ".");
            case 8  -> System.out.println("Средно-аритметичното на числата е " + average() + ".");
            case 9  -> symmetricalArray();
            case 10 -> swapOfArray();
            case 11 -> printOfArray();
            case 12 -> System.out.println("Довиждане.");
            default -> System.out.println("Грешка.");
        }
    }

    private static void fillArray() {

        Random random = new Random();

        for (int i = 0; i < LENGTH; i++) {
            array[i] = random.nextInt(100) + 1;
        }
    }

    private static void sort( boolean isAscending) {

        for (int trailNumber = 0; trailNumber < LENGTH; trailNumber++) {
            for (int numberPosition = 0; numberPosition < LENGTH - 1 - trailNumber; numberPosition++) {

                int leftHandNumberPosition = numberPosition;
                int leftHandNumber         = array[numberPosition];

                int rightHandNumberPosition = numberPosition + 1;
                int rightHandNumber         = array[numberPosition + 1];

                boolean isSwapping = (isAscending)
                        ? rightHandNumber < leftHandNumber
                        : rightHandNumber > leftHandNumber;

                if (isSwapping) {
                    array[leftHandNumberPosition]  = rightHandNumber;
                    array[rightHandNumberPosition] = leftHandNumber;
                }
            }
        }
    }

    private static void search() {

        Scanner scanner = new Scanner(System.in);
        boolean isNumberFound = false;

        System.out.print("Въведете търсеното число: ");
        int searchNumber = scanner.nextInt();

        for (int i = 0; i < LENGTH; i++) {

            if (array[i] == searchNumber) {

                System.out.println("Числото " + searchNumber + " е намерено на позиция " + i + ".");
                isNumberFound = true;
            }
        }

        if(!isNumberFound) {
            System.out.println("Числото " + searchNumber + " не е намерено.");
        }
    }

    private static void shuffle() {

        Random random = new Random();

        int keeperOfNumber;

        for (int i = LENGTH - 1; i > 0; i--) {

            int randomIndex = random.nextInt(i + 1);

            keeperOfNumber     = array[randomIndex];
            array[randomIndex] = array[i];
            array[i]           = keeperOfNumber;
        }
    }

    private static int sum() {

        int sum = 0;

        for (int number : array) {
            sum += number;
        }

        return sum;
    }

    private static int minMax(boolean isMin) {

        int keeper = array[0];

        for (int i = 1; i < LENGTH; i++) {

            keeper = ((isMin && keeper > array[i]) ||
                     (!isMin && keeper < array[i]))
                     ? array[i]
                     : keeper;
        }

        return keeper;
    }

    private static double average() {

        return ((double) sum()) / LENGTH;
    }

    private static void symmetricalArray() {

        int startIndex  = 0;
        int endIndex    = LENGTH - 1;
        int middleIndex = (startIndex + endIndex) / 2;

        for (int i = 0; i < middleIndex; i++) {

            if (array[i] != array[endIndex - i]) {

                System.out.println("Масивът не е симетричен.");
                printOfArray();
                return;
            }
        }

        System.out.println("Масивът е симетричен.");
        printOfArray();
    }

    private static void swapOfArray() {

        int keeperOfNumber;
        int endIndex    = LENGTH - 1;
        int middleIndex = LENGTH / 2;

        for (int i = 0; i < middleIndex; i++) {

            keeperOfNumber      = array[i];
            array[i]            = array[endIndex - i];
            array[endIndex - i] = keeperOfNumber;
        }

        printOfArray();
    }

    private static void printOfArray() {

        for (int number : array) {
            System.out.print(number + " ");
        }

        System.out.println();
    }
}