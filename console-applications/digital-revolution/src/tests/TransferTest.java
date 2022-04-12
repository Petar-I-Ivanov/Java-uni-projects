package tests;

import bank.Transfer;
import bank.Wallet;

public class TransferTest {

    public static void test() {

        System.out.println("Same wallet test:");
        sameWalletsTest();

        System.out.println("Different wallet test:");
        diffWalletsTest();

        System.out.println("Border values test:");
        borderValuesTest();

        System.out.println("Blocked wallet test:");
        blockedWalletTest();
    }

    private static void sameWalletsTest() {

        Wallet walletIvan    = new Wallet("Иван", "ОББ");
        Wallet walletDimitar = new Wallet("Иван", "ОББ");

        Transfer.AutomaticProcess(walletIvan, walletDimitar, "GLD", 97);
    }

    private static void diffWalletsTest() {

        Wallet walletIvan    = new Wallet("Иван", "ОББ");
        Wallet walletDimitar = new Wallet("Димитър", "ДСК");

        Transfer.AutomaticProcess(walletIvan, walletDimitar, "BTC", 97);
    }

    private static void borderValuesTest() {

        Wallet walletIvan    = new Wallet("Иван", "ОББ");
        Wallet walletDimitar = new Wallet("Димитър", "ДСК");

        Transfer.AutomaticProcess(walletIvan, walletDimitar, "SBB", 98);
    }

    private static void blockedWalletTest() {

        Wallet walletIvan    = new Wallet("Иван", "ОББ");
        Wallet walletDimitar = new Wallet("Димитър", "ОББ");
        walletDimitar.setBlocked();

        Transfer.AutomaticProcess(walletIvan, walletDimitar, "QAQ", 97);
    }
}