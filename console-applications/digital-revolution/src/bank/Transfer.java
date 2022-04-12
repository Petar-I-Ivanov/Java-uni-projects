package bank;

import bank.money.Fiat;
import bank.money.Hash;
import bank.money.Money;
import bank.money.util.MoneyCreator;

import java.util.Scanner;

public class Transfer {

    private final static Wallet[] wallets = {
            new Wallet("Георги", "ОББ"),
            new Wallet("Иван", "ОББ"),
            new Wallet("Александър", "ДСК")
    };

    private final static String[] VALUES = {
            "USD", "CAD", "JAP", "CHF",
            "GLD",
            "BTC", "SBB", "RTA", "QAQ",
            "NFT"
    };

    public static void ManualProcess() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Изберете портмоне, от което ще теглите пари:");
        Wallet fromWallet = pickWallet();
        addMoney(fromWallet);

        System.out.println("Изберете портмоне, на което ще првежедате пари:");
        Wallet toWallet = pickWallet();

        if (fromWallet.getUserName().equals(toWallet.getUserName())) {
            System.out.println("Не може от едно и също портмоне.");
            return;
        }

        if (toWallet.isBlocked()) {
            System.out.println("Портфейлът получател е блокиран.");
            return;
        }

        Money pickedMoney = fromWallet.pickMoney();

        if (pickedMoney instanceof Hash && !((Hash) pickedMoney).getDirection()) {
            System.out.println("Избраните пари не могат да се изпращат.");
            return;
        }

        System.out.print("Въведете стойност: ");
        double value = scanner.nextDouble();

        Money money = MoneyCreator.create(pickedMoney.getCode(), value);

        Money tax = (fromWallet.getBankName().equals(toWallet.getBankName()))
                ? new Fiat("USD", 1)
                : MoneyCreator.create(pickedMoney.getCode(), value * 0.03);

        int choice = pickValue();

        Money temp = MoneyCreator.create(VALUES[choice - 1], Money.converter(money, VALUES[choice - 1]));

        if (fromWallet.isMoneyAvailable(money, tax)) {

            fromWallet.addOrSubMoney(money, false);
            fromWallet.addOrSubMoney(tax, false);

            toWallet.addOrSubMoney(temp, true);

            System.out.println("След края");
            fromWallet.listWallet();
            toWallet.listWallet();

            return;
        }

        System.out.println(fromWallet.getUserName() + " няма толкова пари в сметката.");
    }

    public static void AutomaticProcess(Wallet walletFrom, Wallet walletTo, String moneyType, double value) {

        if (walletFrom.getUserName().equals(walletTo.getUserName())) {
            System.out.println("Не може от едно и също портмоне.");
            return;
        }

        if (walletTo.isBlocked()) {
            System.out.println("Портфейлът получател е блокиран.");
            return;
        }

        Money pMoney = MoneyCreator.create("USD", 100);
        walletFrom.addOrSubMoney(pMoney, true);

        System.out.println("В началото");
        walletFrom.listWallet();
        walletTo.listWallet();

        Money money = MoneyCreator.create(pMoney.getCode(), value);

        Money tax = (walletFrom.getBankName().equals(walletTo.getBankName()))
                ? new Fiat("USD", 1)
                : MoneyCreator.create(pMoney.getCode(), value * 0.03);

        Money temp = MoneyCreator.create(moneyType, Money.converter(money, moneyType));

        if (walletFrom.isMoneyAvailable(money, tax)) {

            walletFrom.addOrSubMoney(money, false);
            walletFrom.addOrSubMoney(tax, false);

            walletTo.addOrSubMoney(temp, true);

            System.out.println("След края");
            walletFrom.listWallet();
            walletTo.listWallet();

            return;
        }

        System.out.println(walletFrom.getUserName() + " няма толкова пари в сметката.");
    }

    private static Wallet pickWallet() {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            int i = 1;

            for (Wallet w : wallets) {
                System.out.println(i++ + ". " + w.getUserName() + " - " + w.getBankName());
            }

            choice = scanner.nextInt();

        } while (choice < 1 || choice > wallets.length);

        return wallets[choice - 1];
    }

    private static void addMoney(Wallet wallet) {

        for (int i = 0; i < VALUES.length - 1; i++) {
            wallet.addOrSubMoney(MoneyCreator.create(VALUES[i], (i + 1) * 100), true);
        }
    }

    private static int pickValue() {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {

            System.out.println("Изберете в коя валута ги искате:");
            for (int i = 0; i < VALUES.length; i++) {
                System.out.println((i + 1) + ". " + VALUES[i]);
            }

            choice = scanner.nextInt();

        } while (choice < 1 || choice > VALUES.length);

        return choice;
    }
}