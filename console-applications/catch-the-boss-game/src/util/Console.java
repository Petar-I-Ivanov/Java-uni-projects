package util;

import java.util.Scanner;

public class Console {

    public static void print(String message) {
        System.out.print(message);
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static String inputString(String message) {
        Scanner scanner = new Scanner(System.in);
        print(message);
        return scanner.nextLine();
    }

    public static int inputInt(String message) {
        Scanner scanner = new Scanner(System.in);
        print(message);
        return scanner.nextInt();
    }

    public static boolean inputBoolean(String message) {
        Scanner scanner = new Scanner(System.in);
        print(message);
        return scanner.nextBoolean();
    }
}