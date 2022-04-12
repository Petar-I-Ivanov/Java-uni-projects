package units.heroes;

import units.Boss;
import units.Background;
import units.Units;
import util.Console;

public class Gnome extends Heroes {

    private double averageProgression = 0.0;

    public Gnome (int row, int col) {
        super("#", row, col);
    }

    /*
     * TODO:
     *  simplify averageFinder
     */

    @Override
    public boolean isMovementPossible(Units[][] gameBoard, int row, int col) {

        double average = averageFinder(gameBoard, row, col);

        if (average > this.averageProgression) {

            this.averageProgression = average;
            return true;
        }

        return gameBoard[row][col] instanceof Boss;
    }

    @Override
    public void magic(Units[][] gameBoard) {

        int toRow, toCol;

        do {
            toRow = pickRow("Въведете реда на позицията: ", gameBoard.length);
            toCol = pickCol("Въведете колоната на позицията: ", gameBoard[0].length);
        } while (!(gameBoard[toRow][toCol] instanceof Background));

        if (!isPositionInLineWithGnome(toRow, toCol)) {
            Console.println("Невъзможен ход, пробвайте от начало.");
            magic(gameBoard);
        }

        int sum = sumFinder(gameBoard, toRow, toCol);

        int row = (toRow < this.row)
                ? this.row - 1
                : (toRow > this.row)
                ? this.row + 1
                : this.row;

        int col = (toCol < this.col)
                ? this.col - 1
                : (toCol > this.col)
                ? this.col + 1
                : this.col;

        gameBoard[row][col] = new Background(sum);
        this.isUnitUsed = true;
    }

    private int backgroundValue(Units element) {

        if (element instanceof Background background) {
            return background.getUniqueNumberInteger();
        }

        return 0;
    }

    private double averageFinder(Units[][] gameBoard, int row, int col) {

        int endRow = gameBoard.length - 1;
        int endCol = gameBoard[0].length - 1;

        boolean isUpperLeftCorner   = (row == 0 && col == 0);
        boolean isUpperBorder       = (row == 0 && (col != 0 && col != endCol));
        boolean isUpperRightCorner  = (row == 0 && col == endCol);
        boolean isRightBorder       = ((row != 0 && row != endRow) && col == endCol);
        boolean isLowerRightCorner  = (row == endRow && col == endCol);
        boolean isLowerBorder       = (row == endRow && (col != 0 && col != endCol));
        boolean isLowerLeftCorner   = (row == endRow && col == 0);
        boolean isLeftBorder        = ((row != 0 && row != endRow) && col == 0);

        int sum = 0;

        if (isUpperLeftCorner) {
            sum += backgroundValue(gameBoard[row]     [col]);
            sum += backgroundValue(gameBoard[row]     [col + 1]);
            sum += backgroundValue(gameBoard[row + 1] [col]);
            sum += backgroundValue(gameBoard[row + 1] [col + 1]);
            return (double)sum / 4;
        }

        if (isUpperBorder) {
            sum += backgroundValue(gameBoard[row]      [col]);
            sum += backgroundValue(gameBoard[row]      [col - 1]);
            sum += backgroundValue(gameBoard[row]      [col + 1]);
            sum += backgroundValue(gameBoard[row + 1]  [col]);
            sum += backgroundValue(gameBoard[row + 1]  [col + 1]);
            sum += backgroundValue(gameBoard[row + 1]  [col - 1]);
            return (double)sum / 6;
        }

        if (isUpperRightCorner) {
            sum += backgroundValue(gameBoard[row]     [col]);
            sum += backgroundValue(gameBoard[row]     [col - 1]);
            sum += backgroundValue(gameBoard[row + 1] [col]);
            sum += backgroundValue(gameBoard[row + 1] [col - 1]);
            return (double)sum / 4;
        }

        if (isRightBorder) {
            sum += backgroundValue(gameBoard[row]      [col]);
            sum += backgroundValue(gameBoard[row]      [col - 1]);
            sum += backgroundValue(gameBoard[row + 1]  [col]);
            sum += backgroundValue(gameBoard[row + 1]  [col - 1]);
            sum += backgroundValue(gameBoard[row - 1]  [col]);
            sum += backgroundValue(gameBoard[row - 1]  [col - 1]);
            return (double)sum / 6;
        }

        if (isLowerRightCorner) {
            sum += backgroundValue(gameBoard[row]     [col]);
            sum += backgroundValue(gameBoard[row]     [col - 1]);
            sum += backgroundValue(gameBoard[row - 1] [col]);
            sum += backgroundValue(gameBoard[row - 1] [col - 1]);
            return (double)sum / 4;
        }

        if (isLowerBorder) {
            sum += backgroundValue(gameBoard[row]      [col]);
            sum += backgroundValue(gameBoard[row]      [col - 1]);
            sum += backgroundValue(gameBoard[row]      [col + 1]);
            sum += backgroundValue(gameBoard[row - 1]  [col]);
            sum += backgroundValue(gameBoard[row - 1]  [col + 1]);
            sum += backgroundValue(gameBoard[row - 1]  [col - 1]);
            return (double)sum / 6;
        }

        if (isLowerLeftCorner) {
            sum += backgroundValue(gameBoard[row]     [col]);
            sum += backgroundValue(gameBoard[row]     [col + 1]);
            sum += backgroundValue(gameBoard[row - 1] [col]);
            sum += backgroundValue(gameBoard[row - 1] [col + 1]);
            return (double)sum / 4;
        }

        if (isLeftBorder) {
            sum += backgroundValue(gameBoard[row]      [col]);
            sum += backgroundValue(gameBoard[row]      [col + 1]);
            sum += backgroundValue(gameBoard[row + 1]  [col]);
            sum += backgroundValue(gameBoard[row + 1]  [col + 1]);
            sum += backgroundValue(gameBoard[row - 1]  [col]);
            sum += backgroundValue(gameBoard[row - 1]  [col + 1]);
            return (double)sum / 6;
        }

        sum += backgroundValue(gameBoard[row]      [col]);
        sum += backgroundValue(gameBoard[row]      [col + 1]);
        sum += backgroundValue(gameBoard[row]      [col - 1]);
        sum += backgroundValue(gameBoard[row + 1]  [col]);
        sum += backgroundValue(gameBoard[row + 1]  [col - 1]);
        sum += backgroundValue(gameBoard[row + 1]  [col + 1]);
        sum += backgroundValue(gameBoard[row - 1]  [col]);
        sum += backgroundValue(gameBoard[row - 1]  [col - 1]);
        sum += backgroundValue(gameBoard[row - 1]  [col + 1]);

        return (double)sum / 9;
    }

    private boolean isPositionInLineWithGnome(int toRow, int toCol) {

        int rowCoefficient = Math.abs(toRow - this.row);
        int colCoefficient = Math.abs(toCol - this.col);

        return ((rowCoefficient > 1 && rowCoefficient < 4) && colCoefficient == 0)
                || (rowCoefficient == 0 && (colCoefficient > 1 && colCoefficient < 4));
    }

    private int sumFinder(Units[][] gameBoard, int row, int col) {

        boolean isForward   = this.row > row;
        boolean isBackward  = this.row < row;
        boolean isLeft      = this.col > col;
        boolean isRight     = this.col < col;

        int numberOfSquares = (isForward || isBackward)
                ? Math.abs(this.row - row)
                : (isLeft || isRight)
                ? Math.abs(this.col - col)
                : null;

        int sum = 0;

        for (int i = 0; i < numberOfSquares; i++) {

            if (gameBoard[row][col] instanceof Background background) {

                sum += background.getUniqueNumberInteger();
                gameBoard[row][col] = new Background();

                row = (isForward)
                        ? row + 1
                        : (isBackward)
                        ? row - 1
                        : row;

                col = (isLeft)
                        ? col + 1
                        : (isRight)
                        ? col - 1
                        : col;
            }

            else {
                Console.println("Имате препядствие на пътя, изберете нова посока.");
                magic(gameBoard);
            }
        }

        return sum;
    }
}