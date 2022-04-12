package units.heroes;

import units.Boss;
import units.Background;
import units.Units;
import util.Console;

import java.util.ArrayList;

public class Dwarfs extends Heroes {

    private static int progressiveEvenNumber;

    public Dwarfs(int row, int col) {
        super("$", row, col);
    }

    @Override
    public boolean isMovementPossible(Units[][] gameBoard, int row, int col) {

        if (gameBoard[row][col] instanceof Background background) {

            int elementValue = background.getUniqueNumberInteger();

            if ((elementValue % 2 == 0) && (elementValue > progressiveEvenNumber)) {

                progressiveEvenNumber = elementValue;
                return true;
            }
        }

        return gameBoard[row][col] instanceof Boss;
    }

    @Override
    public void magic(Units[][] gameBoard) {

        int endRow = gameBoard.length - 1;
        int endCol = gameBoard[0].length - 1;

        int fromRow, fromCol;

        do {
            fromRow = pickRow("Въведете реда на позицията, която искате да размените: ", endRow);
            fromCol = pickCol("Въведете колоната на позицията, която искате да размените: ", endCol);

        } while (!(gameBoard[fromRow][fromCol] instanceof Background));

        ArrayList<Integer> selectedArray = getUsedSquare(gameBoard,
                ((Background) gameBoard[fromRow][fromCol]).getUniqueNumberInteger());

        if (selectedArray == null) {
            System.out.println("Числото е извън границите на героя.");
            magic(gameBoard);
        }

        int toRow, toCol;

        do {
            toRow = pickRow("Въведете реда на позицията, която искате да размените: ", endRow);
            toCol = pickCol("Въведете колоната на позицията, която искате да размените: ", endCol);

        } while (!(gameBoard[toRow][toCol] instanceof Background));

        if (!selectedArray.contains(((Background) gameBoard[toRow][toCol]).getUniqueNumberInteger())) {
            Console.println("Избраната позиция не е в същия квадрат, като първата.");
            return;
        }

        Units keeper = gameBoard[toRow][toCol];
        gameBoard[toRow][toCol] = gameBoard[fromRow][fromCol];
        gameBoard[fromRow][fromCol] = keeper;

        this.isUnitUsed = true;
    }

    // checks which array contains first chosen number, and returns it
    private ArrayList<Integer> getUsedSquare(Units[][] gameBoard, int searchingNumber) {

        ArrayList<Integer> firstSquare  = squareOnRight(gameBoard);
        ArrayList<Integer> secondSquare = squareOnBottom(gameBoard);
        ArrayList<Integer> thirdSquare  = squareOnLeft(gameBoard);
        ArrayList<Integer> fourthSquare = squareOnTop(gameBoard);

        if (firstSquare.contains(searchingNumber)) {
            return firstSquare;
        }

        if (secondSquare.contains(searchingNumber)) {
            return secondSquare;
        }

        if (thirdSquare.contains(searchingNumber)) {
            return thirdSquare;
        }

        if (fourthSquare.contains(searchingNumber)) {
            return fourthSquare;
        }

        Console.println("Въведената позиция не е в близост до героя.");
        return null;
    }

    /*
     * The logic is the same, the only difference is at
     *
     * @param       endRow endCol, rowBorder colBorder, and the loops
     *               some check the values after this.row, this.col (++)
     *               some check the values before this.row, this.col (--)
     *
     * @return is ArrayList containing values for each side ot the dwarfs
     */

    private ArrayList<Integer> squareOnRight(Units[][] gameBoard) {

        int endRow = gameBoard.length - 1;
        int endCol = gameBoard[0].length - 1;

        int rowBorder = this.row + 4;
        int colBorder = this.col + 5;

        ArrayList<Integer> keeper = new ArrayList<>();

        for (int row = this.row; row < rowBorder; row++) {

            if (row > endRow) break;

            for (int col = (this.col + 1); col < colBorder; col++) {

                if (col > endCol) break;

                if (gameBoard[row][col] instanceof Background background) {
                    keeper.add(background.getUniqueNumberInteger());
                }
            }
        }

        return keeper;
    }

    private ArrayList<Integer> squareOnBottom(Units[][] gameBoard) {

        int endRow = gameBoard.length - 1;
        int startCol = 0;

        int rowBorder = this.row + 5;
        int colBorder = this.col - 4;

        ArrayList<Integer> keeper = new ArrayList<>();

        for (int row = (this.row + 1); row < rowBorder; row++) {

            if (row > endRow) break;

            for (int col = this.col; col > colBorder; col--) {

                if (col < startCol) break;

                if (gameBoard[row][col] instanceof Background background) {
                    keeper.add(background.getUniqueNumberInteger());
                }
            }
        }

        return keeper;
    }

    private ArrayList<Integer> squareOnLeft(Units[][] gameBoard) {

        int startRow = 0;
        int startCol = 0;

        int rowBorder = this.row - 4;
        int colBorder = this.col - 5;

        ArrayList<Integer> keeper = new ArrayList<>();

        for (int row = this.row; row > rowBorder; row--) {

            if (row < startRow) break;

            for (int col = (this.col - 1); col > colBorder; col--) {

                if (col < startCol) break;

                if (gameBoard[row][col] instanceof Background background) {
                    keeper.add(background.getUniqueNumberInteger());
                }
            }
        }

        return keeper;
    }

    private ArrayList<Integer> squareOnTop(Units[][] gameBoard) {

        int startRow = 0;
        int endCol = gameBoard[0].length - 1;

        int rowBorder = this.row - 5;
        int colBorder = this.col + 4;

        ArrayList<Integer> keeper = new ArrayList<>();

        for (int row = (this.row - 1); row > rowBorder; row--) {

            if (row < startRow) break;

            for (int col = this.col; col < colBorder; col++) {

                if (col > endCol) break;

                if (gameBoard[row][col] instanceof Background background) {
                    keeper.add(background.getUniqueNumberInteger());
                }
            }
        }

        return keeper;
    }
}