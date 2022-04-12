package units;

import util.Console;

public class Boss extends Units {

    public Boss() {
        super("X");
    }

    public static boolean isBossNotOnPlace(Units[][] gameBoard) {

        int bossStartRow = 5;
        int bossEndRow = 6;

        int bossStartCol = 8;
        int bossEndCol = 9;

        for (int row = bossStartRow; row <= bossEndRow; row++) {
            for (int col = bossStartCol; col <= bossEndCol; col++) {

                if (!(gameBoard[row][col] instanceof Boss)) {
                    Console.println("Вие печелите!");
                    return true;
                }
            }
        }

        return false;
    }
}