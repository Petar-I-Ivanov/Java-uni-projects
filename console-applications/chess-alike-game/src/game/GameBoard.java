package game;

import units.*;
import util.*;
import units.heroes.Heroes;

public class GameBoard {

    private static final int BOARD_ROWS = 7;
    private static final int BOARD_COLS = 9;

    private static Units[][] gameBoard = new Units[BOARD_ROWS][BOARD_COLS];

    private GameBoard() {
        // private constructor that doesn't allow new instances of GameBoard
    }

    public static void game() {

        do {
            startOfGame();
            gameProgress();
        } while (isGameResetting());
    }

    private static void startOfGame() {

        create();

        do {
            Player.firstPhase(gameBoard);
            print();
        } while (!Player.isFirstPhaseOver());

        generateObjects();

        Console.println("After generating objects.");
        print();
    }

    private static void gameProgress() {

        do {
            Player.gameAction(gameBoard);
            print();
        } while (Player.isGameWon(gameBoard));

        Player.listKills();
    }

    private static boolean isGameResetting() {

        final String POSITIVE = "Yes";
        final String NEGATIVE = "No";

        String pickAnswer = Console.inputString("Do you want to restart the game? Yes/No");

        switch (pickAnswer) {

            case POSITIVE -> {
                Player.reset();
                return true;
            }

            case NEGATIVE -> {
                Console.println("End Game");
                return false;
            }

            default -> {
                Console.println("Invalid input, try again.");
                return isGameResetting();
            }
        }
    }

    private static void create() {

        for(int row = 0; row < BOARD_ROWS; row++) {
            for(int col = 0; col < BOARD_COLS; col++) {
                gameBoard[row][col] = new Terrain();
            }
        }
    }

    private static void print() {

        for(int row = 0; row < BOARD_ROWS; row++) {
            for(int col = 0; col < BOARD_COLS; col++) {

                if(gameBoard[row][col] instanceof Heroes) {
                    Console.print(((Heroes) gameBoard[row][col]).getSignAndType());
                }
                else {
                   Console.print(gameBoard[row][col].getSign() + " ");
                }
            }

            Console.println("");
        }
    }

    private static void generateObjects() {

        int randomNumberOfObjects = CustomRandom.dice(5);

        int randomRow = CustomRandom.dice(2);
        int randomCol = CustomRandom.random(BOARD_COLS);
        int kindObject = 1;

        for (int i = 0; i < randomNumberOfObjects; i++) {

            switch (kindObject) {
                case 1 -> gameBoard[randomRow + 2][randomCol] = new Wall();
                case 2 -> gameBoard[randomRow + 2][randomCol] = new Barricade();
            }

            randomRow = CustomRandom.dice(2);
            randomCol = CustomRandom.random(BOARD_COLS);
            kindObject = CustomRandom.dice(2);
        }
    }
}