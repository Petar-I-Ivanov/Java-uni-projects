package bank.money.util;

import bank.money.*;

import java.util.Scanner;

public class MoneyCreator {

    public static Money create(String name, double value) {
        return switch (name) {

            case "USD", "CAD", "JAP", "CHF" -> new Fiat(name, value);

            case "GLD" -> new Gold(value);

            case "BTC", "SBB", "RTA", "QAQ" -> new Hash(name, value);

            case "NFT" -> new NFT(getNameForNft(), value);

            default -> new NFT(name, value);
        };
    }

    private static String getNameForNft() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Въведете код за NFT-то.");
        return scanner.nextLine();
    }
}