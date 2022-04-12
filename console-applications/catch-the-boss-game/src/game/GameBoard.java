package game;

import units.*;
import units.heroes.*;
import util.*;

import java.util.ArrayList;

public class GameBoard {

    private static final int BOARD_ROW = 12;
    private static final int BOARD_COL = 18;

    private static final Units[][] gameBoard = new Units[BOARD_ROW][BOARD_COL];

    private static int turns = 15;

    private GameBoard() {
        // private constructor that doesn't allow new instances of that class
    }

    public static void game() {

        createBoard();

        do {
            gameAction();

        } while (!isGameOver());
    }

    private static void createBoard() {

        ArrayList<Units> units = new ArrayList<>();
        units.add(new Dwarfs(0, 0));
        units.add(new Gnome(0, 0));
        units.add(new Wizard(0, 0));
        units.add(new EmptyCorner());

        for (int row = 0; row < BOARD_ROW; row++) {
            for (int col = 0; col < BOARD_COL; col++) {

                if (row == 0 && (col == 0 || col == BOARD_COL - 1) ||
                        (row == BOARD_ROW - 1 && (col == 0 || col == BOARD_COL - 1))) {

                    int random = CustomRandom.random(units.size());

                    switch (units.get(random).getSign()) {

                        case "$" -> gameBoard[row][col] = new Dwarfs(row, col);
                        case "#" -> gameBoard[row][col] = new Gnome(row, col);
                        case "%" -> gameBoard[row][col] = new Wizard(row, col);
                        case "@" -> gameBoard[row][col] = new EmptyCorner();
                    }

                    units.remove(random);
                    continue;
                }

                if ((row == 5 && (col == 8 || col == 9)) ||
                        (row == 6 && (col == 8 || col == 9))) {
                    gameBoard[row][col] = new Boss();
                    continue;
                }

                gameBoard[row][col] = new Background();
            }
        }
    }

    private static void printBoard() {

        String message;

        for (int row = 0; row < BOARD_ROW; row++) {
            for (int col = 0; col < BOARD_COL; col++) {

                message = gameBoard[row][col].getSign();

                switch (message.length()) {
                    case 1 -> message = " ".concat(message).concat("  ");
                    case 2 -> message = message.concat("  ");
                    case 3 -> message = message.concat(" ");
                }

                Console.print(message);
            }

            Console.println("");
        }
    }

    private static void gameAction() {

        printBoard();

        Heroes gnome  = searchingHero(1);
        Heroes dwarfs = searchingHero(2);
        Heroes wizard = searchingHero(3);

        if (gnome.getIsUnitUsed() && dwarfs.getIsUnitUsed() && wizard.getIsUnitUsed()) {

            gnome.setIsUnitUsed(false);
            dwarfs.setIsUnitUsed(false);
            wizard.setIsUnitUsed(false);

            turns--;
        }

        Console.println("Остават ви " + turns + " хода.");

        action(pickHero(gnome, dwarfs, wizard));
    }

    private static Heroes pickHero(Heroes gnome, Heroes dwarfs, Heroes wizard) {

        if (!gnome.getIsUnitUsed())
            Console.print("1. Гном (" + gnome.getSign() + ")  ");

        if (!dwarfs.getIsUnitUsed())
            Console.print("2. Джудже (" + dwarfs.getSign() + ")  ");

        if (!wizard.getIsUnitUsed())
            Console.print("3. Магьосник (" + wizard.getSign() + ")  ");

        Console.println("");

        int heroChoice = Console.inputInt("Изберете с кой герой ще действате: ");

        while ( (heroChoice == 1 && gnome.getIsUnitUsed()) ||
                (heroChoice == 2 && dwarfs.getIsUnitUsed()) ||
                (heroChoice == 3 && wizard.getIsUnitUsed()) ||
                (heroChoice < 1 || heroChoice > 3)) {

            heroChoice = Console.inputInt("Изберете от показаните: ");
        }

        return searchingHero(heroChoice);
    }

    private static Heroes searchingHero(int pickedHero) {

        for (int row = 0; row < BOARD_ROW; row++) {
            for (int col = 0; col < BOARD_COL; col++) {

                switch (pickedHero) {
                    case 1 -> {
                        if (gameBoard[row][col] instanceof Gnome gnome)
                            return gnome;
                    }
                    case 2 -> {
                        if (gameBoard[row][col] instanceof Dwarfs dwarfs)
                            return dwarfs;
                    }

                    case 3 -> {
                        if (gameBoard[row][col] instanceof Wizard wizard)
                            return wizard;
                    }
                }
            }
        }

        return null;
    }

    private static void action(Heroes pickedHero) {

        Console.println("1. Движение  2. Магия");

        int choice = Console.inputInt("Изберете действие: ");

        while (choice != 1 && choice != 2) {
            choice = Console.inputInt("Изберете от посочените: ");
        }

        switch (choice) {
            case 1 -> pickedHero.move(gameBoard);
            case 2 -> pickedHero.magic(gameBoard);
        }
    }

    private static boolean isGameOver() {

        if (Boss.isBossNotOnPlace(gameBoard)) {
            Console.println("Вие печелите!**");
            return true;
        }

        if (turns == 0) {
            Console.println("Вие загубихте.");
            return true;
        }

        return false;
    }
}