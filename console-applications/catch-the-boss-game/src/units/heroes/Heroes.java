package units.heroes;

import units.Units;
import util.Console;

public abstract class Heroes extends Units {

    protected int row;
    protected int col;
    protected boolean isUnitUsed;

    protected Heroes(String sign, int row, int col) {
        super(sign);
        this.row    = row;
        this.col    = col;
    }

    public boolean getIsUnitUsed() {
        return this.isUnitUsed;
    }

    public void setIsUnitUsed(boolean isUnitUsed) {
        this.isUnitUsed = isUnitUsed;
    }

    public void move(Units[][] gameBoard) {

        if (this.isUnitUsed) {
            Console.println("Този герой е използван този ход.");
            return;
        }

        int moveRow = pickRow("Изберете ред на предвижване: ", gameBoard.length);
        int moveCol = pickCol("Изберете колона на предвижване: ", gameBoard[0].length);

        if (isMoveInRange(moveRow, moveCol) &&
                this.isMovementPossible(gameBoard, moveRow, moveCol)) {

            Units keeper = gameBoard[moveRow][moveCol];
            gameBoard[moveRow][moveCol] = gameBoard[this.row][this.col];
            gameBoard[this.row][this.col] = keeper;

            this.row = moveRow;
            this.col = moveCol;
            this.isUnitUsed = true;

            Console.println("Героят се придвижи успешно.");
            return;
        }

        Console.println("Героят не може да се придвижи там.");
    }

    protected static int pickRow(String message, int endRow) {

        int pickRow = Console.inputInt(message);

        if (pickRow < 0 || pickRow > endRow) {

            Console.println("Моля изберете ред в границата  0 " + endRow + ".");
            return pickRow(message, endRow);
        }

        return pickRow;
    }
    protected static int pickCol(String message, int endCol) {

        int pickCol = Console.inputInt(message);

        if (pickCol < 0 || pickCol > endCol) {

            Console.println("Моля изберете колона в границата  0 " + endCol + ".");
            return pickCol(message, endCol);
        }

        return pickCol;
    }

    private boolean isMoveInRange(int toRow, int toCol) {

        int rowCoefficient = Math.abs(toRow - this.row);
        int colCoefficient = Math.abs(toCol - this.col);

        return (rowCoefficient == 1 && colCoefficient == 0) ||
                (rowCoefficient == 0 && colCoefficient == 1);
    }

    protected  abstract boolean isMovementPossible(Units[][] gameBoard, int row, int col);
    public abstract void magic(Units[][] gameBoard);
}