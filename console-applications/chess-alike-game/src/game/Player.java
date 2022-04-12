package game;

import units.*;
import units.heroes.*;
import util.*;

import java.util.ArrayList;

public class Player {

    private static int blackKnightQuantity = 2;
    private static int blackDwarfQuantity  = 2;
    private static int blackElfQuantity    = 2;

    private static int redKnightQuantity = 2;
    private static int redDwarfQuantity  = 2;
    private static int redElfQuantity    = 2;

    private static int player = 1;
    private static PlayerTypeEnum playerType;

    private static ArrayList<Units> blackPlayerKills = new ArrayList<>();
    private static ArrayList<Units> redPlayerKills   = new ArrayList<>();


    private Player() {
        // private constructor that doesn't allow new instances of Player
    }

    public static void firstPhase(Units[][] gameBoard) {

        String playerSign = (isBlackPlayer()) ? "Black" : "Red";
        Console.println(playerSign + " player turn.");

        int heroPick = pickHero();
        int rowPick  = pickRow(gameBoard, true);
        int colPick  = pickCol(gameBoard);

        if (!isPositionFree(gameBoard, rowPick, colPick)){
            Console.println("Pick free position. process will start from the start.");
            firstPhase(gameBoard);
        }

        setPlayerType();
        firstPhaseProcess(gameBoard, heroPick,rowPick, colPick);

        player++;
    }

    public static boolean isFirstPhaseOver() {
        return (blackKnightQuantity == 0 && blackDwarfQuantity == 0 && blackElfQuantity == 0
                && redKnightQuantity == 0 && redDwarfQuantity == 0 && redElfQuantity == 0);
    }

    public static void gameAction(Units[][] gameBoard) {

        setPlayerType();

        String playerSign = (isBlackPlayer()) ? "Black" : "Red";
        Console.println(playerSign + " player turn.");

        int choice = 0;

        do {
            Console.println("1. Move");
            Console.println("2. Attack");
            Console.println("3. Heal");

            choice = Console.inputInt("Choose action: ");
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1 -> heroMove(gameBoard);
            case 2 -> heroAttack(gameBoard);
            case 3 -> heroHeal(gameBoard);
            default -> Console.println("Error.");
        }

        player++;
    }

    public static boolean isGameWon(Units[][] gameBoard) {

        boolean areThereBlackHeroes = false;
        boolean areThereRedHeroes = false;

        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[0].length; col++) {

                if (gameBoard[row][col] instanceof Heroes element
                        && element.getHealth() > 0) {

                    if (element.getPlayerType() == PlayerTypeEnum.BLACK) {

                        areThereBlackHeroes = true;
                        continue;
                    }

                    areThereRedHeroes = true;
                }
            }
        }

        return areThereBlackHeroes && areThereRedHeroes;
    }

    public static void listKills() {

        Console.print("Black player kills: ");
        for (Units u : blackPlayerKills) {
            Console.print(u.getSign() + ", ");
        }

        Console.println("");

        Console.print("Red player kills: ");
        for (Units u : redPlayerKills) {
            Console.print(u.getSign() + ", ");
        }
    }

    public static void reset() {

        blackKnightQuantity = 2;
        blackDwarfQuantity  = 2;
        blackElfQuantity    = 2;
        blackPlayerKills    = new ArrayList<>();

        redKnightQuantity = 2;
        redDwarfQuantity  = 2;
        redElfQuantity    = 2;
        redPlayerKills    = new ArrayList<>();

        player = 1;
        setPlayerType();
    }

    private static boolean isBlackPlayer() {
        return player % 2 == 1;
    }

    private static void setPlayerType() {

        playerType = (isBlackPlayer())
                ? PlayerTypeEnum.BLACK
                : PlayerTypeEnum.RED;
    }

    private static int pickHero() {

        boolean isBlackPlayer = isBlackPlayer();

        boolean isKnightAvailable = (isBlackPlayer) ? blackKnightQuantity > 0 : redKnightQuantity > 0;
        boolean isDwarfAvailable  = (isBlackPlayer) ? blackDwarfQuantity > 0 : redDwarfQuantity > 0;
        boolean isElfAvailable    = (isBlackPlayer) ? blackElfQuantity > 0 : redElfQuantity > 0;

        Console.println("You have the following heroes: ");

        if(isKnightAvailable) {
            Console.println("1. Knight");
        }
        if(isDwarfAvailable) {
            Console.println("2. Dwarf");
        }
        if(isElfAvailable) {
            Console.println("3. Elf");
        }

        int heroChoice = Console.inputInt("Pick hero: ");

        if (    (heroChoice < 1 || heroChoice > 3)
                || (!isKnightAvailable && heroChoice == 1)
                || (!isDwarfAvailable && heroChoice == 2)
                || (!isElfAvailable && heroChoice == 3)) {

            Console.println("Pick listed options.");
            heroChoice = pickHero();
        }

        return heroChoice;
    }

    private static int pickRow(Units[][] gameBoard, boolean isFirstPhase) {

        int minRow = 0;
        int maxRow = gameBoard.length - 1;

        if (isFirstPhase) {
            minRow = (isBlackPlayer()) ? 5 : 0;
            maxRow = minRow + 1;
        }

        int row = Console.inputInt("Enter row: ");

        if (row < minRow || row > maxRow) {

            Console.println("Pick row inside the borders " + minRow + " - " + maxRow + ".");
            row = pickRow(gameBoard, isFirstPhase);
        }

        return row;
    }

    private static int pickCol(Units[][] gameBoard) {

        int minCol = 0;
        int maxCol = gameBoard[0].length - 1;

        int col = Console.inputInt("Enter column: ");

        if (col < minCol || col > maxCol) {

            Console.println("Pick column inside the borders " + minCol + " - " + maxCol + ".");
            col = pickCol(gameBoard);
        }

        return col;
    }

    private static boolean isPositionFree(Units[][] gameBoard, int row, int col) {
        return gameBoard[row][col] instanceof Terrain;
    }

    private static boolean isPositionSameTypeHero(Units[][] gameBoard, int row, int col) {
        return gameBoard[row][col] instanceof Heroes element
                && element.getPlayerType() == playerType;
    }

    private static boolean isPositionEnemyTypeHero(Units[][] gameBoard, int row, int col) {
        return gameBoard[row][col] instanceof Heroes element
                && element.getPlayerType() != playerType;
    }

    private static boolean isPositionBarricade(Units[][] gameBoard, int row, int col) {
        return gameBoard[row][col] instanceof Barricade;
    }

    private static void firstPhaseProcess(Units[][] gameBoard, int hero ,int row, int col) {

        switch (hero) {

            case 1 -> {
                gameBoard[row][col] = new Knight(playerType, row, col);

                if (isBlackPlayer()) { --blackKnightQuantity; }
                else { --redKnightQuantity; }
            }

            case 2 -> {
                gameBoard[row][col] = new Dwarf(playerType, row, col);

                if (isBlackPlayer()) { --blackDwarfQuantity; }
                else { --redDwarfQuantity; }
            }

            case 3 -> {
                gameBoard[row][col] = new Elf(playerType, row, col);

                if (isBlackPlayer()) { --blackElfQuantity; }
                else { --redElfQuantity; }
            }

            default -> {
                Console.println("Error.");
                System.exit(1);
            }
        }
    }

    private static void heroMove(Units[][] gameBoard) {

        Console.println("Pick position on your unit.");
        int fromRow = pickRow(gameBoard, false);
        int fromCol = pickCol(gameBoard);

        if (!isPositionSameTypeHero(gameBoard, fromRow, fromCol)) {
            Console.println("There's not hero from your player's type.");
            player--;
            return;
        }

        Console.println("Pick target position.");
        int toRow = pickRow(gameBoard, false);
        int toCol = pickCol(gameBoard);

        if (!isPositionFree(gameBoard, toRow, toCol)) {
            Console.println("Position is not free.");
            player--;
            return;
        }

        Units temp = gameBoard[fromRow][fromCol];

        if (temp instanceof Heroes && ((Heroes) temp).isMovePossible(gameBoard, toRow, toCol)) {

            ((Heroes) temp).move(toRow, toCol);
            gameBoard[fromRow][fromCol] = gameBoard[toRow][toCol];
            gameBoard[toRow][toCol] = temp;
            return;
        }

        Console.println("Move is impossible.");
        player--;
    }

    private static void heroAttack(Units[][] gameBoard) {

        Console.println("Pick position on your unit.");
        int fromRow = pickRow(gameBoard, false);
        int fromCol = pickCol(gameBoard);

        if (!isPositionSameTypeHero(gameBoard, fromRow, fromCol)) {
            Console.println("There's not hero from your player's type.");
            player--;
            return;
        }

        Console.println("Pick target position.");
        int toRow = pickRow(gameBoard, false);
        int toCol = pickCol(gameBoard);

        if (!isPositionBarricade(gameBoard, toRow, toCol)
            && !isPositionEnemyTypeHero(gameBoard, toRow, toCol)) {
            Console.println("There's nothing to attack.");
            player--;
            return;
        }

        if (gameBoard[fromRow][fromCol] instanceof Heroes element
                && element.isAttackPossible(gameBoard, toRow, toCol)) {

            ArrayList<Units> kills = (isBlackPlayer()) ? blackPlayerKills : redPlayerKills;

            element.attack(gameBoard, toRow, toCol, kills);
            return;
        }

        Console.println("Attack is impossible.");
        player--;
    }

    private static void heroHeal(Units[][] gameBoard) {

        int row = pickRow(gameBoard, false);
        int col = pickCol(gameBoard);

        if (!isPositionSameTypeHero(gameBoard, row, col)) {
            Console.println("There's not hero from your player's type.");
            player--;
            return;
        }

        if (gameBoard[row][col] instanceof Heroes element
                && !element.isHealthPotionUsed()) {

            element.heal(CustomRandom.dice(6));
            element.setHealthPotionUsed();
            return;
        }

        Console.println("Impossible healing.");
        player--;
    }
}