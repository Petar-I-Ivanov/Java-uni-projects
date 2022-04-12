package units.heroes;

import units.Boss;
import units.Background;
import units.Units;
import util.Console;

import java.util.ArrayList;
import java.util.Collections;

public class Wizard extends Heroes {

    private static int decreasingNumber = 1000;

    public Wizard(int row, int col) {
        super("%", row, col);
    }

    @Override
    public boolean isMovementPossible(Units[][] gameBoard, int row, int col) {

        if (gameBoard[row][col] instanceof Background background) {

            int elementValue = background.getUniqueNumberInteger();

            if (elementValue < decreasingNumber) {

                decreasingNumber = elementValue;
                return true;
            }
        }

        return gameBoard[row][col] instanceof Boss;
    }

    @Override
    public void magic(Units[][] gameBoard) {

        int endRow = gameBoard.length - 1;
        int endCol = gameBoard[0].length - 1;

        int fromRow = pickRow("Въведете реда на началната позиция: ", endRow);
        int fromCol = pickCol("Въведете колоната на началната позиция: ", endCol);

        int toRow = pickRow("Въведете реда на крайната позиция: ", endRow);;
        int toCol = pickCol("Въведете колоната на крайната позиция: ", endCol);;

        if (fromRow - toRow != 0 && fromCol - toCol != 0) {
            Console.println("Въведените стойности не са в една равнина.");
            magic(gameBoard);
        }

        if (fromRow > toRow) {
            int keeper = fromRow;
            fromRow = toRow;
            toRow = keeper;
        }

        if (fromCol > toCol) {
            int keeper = fromCol;
            fromCol = toCol;
            toCol = keeper;
        }

        boolean areRowsTheSame = (fromRow == toRow);

        ArrayList<Integer> numbers = fillArray  (gameBoard,
                                                fromRow, fromCol,
                                                toRow, toCol,
                                                areRowsTheSame);

        sortPickedArray(numbers);

        placeArrayInGame(gameBoard,
                        fromRow, fromCol,
                        toRow, toCol,
                        areRowsTheSame,
                        numbers);

        this.isUnitUsed = true;
    }

    private ArrayList<Integer> fillArray(Units[][] gameBoard,
                                         int fromRow, int fromCol,
                                         int toRow, int toCol,
                                         boolean areRowsTheSame) {
        ArrayList<Integer> numbers = new ArrayList<>();

        if (areRowsTheSame) {

            for (int col = fromCol; col <= toCol; col++) {

                if (gameBoard[fromRow][col] instanceof Background background) {
                    numbers.add(background.getUniqueNumberInteger());
                }
            }

            return numbers;
        }

        for (int row = fromRow; row <= toRow; row++) {

            if (gameBoard[row][fromCol] instanceof Background background) {
                numbers.add(background.getUniqueNumberInteger());
            }
        }

        return numbers;
    }

    private void sortPickedArray(ArrayList<Integer> numbers) {

        boolean isAscending = Console.inputBoolean("Възходящо ли да е подредено (true/false): ");

        Collections.sort(numbers);
        if (!isAscending) {
            Collections.reverse(numbers);
        }
    }

    private void placeArrayInGame(Units[][] gameBoard,
                                  int fromRow, int fromCol,
                                  int toRow, int toCol,
                                  boolean areRowsTheSame,
                                  ArrayList<Integer> numbers) {

        int i = 0;

        if (areRowsTheSame) {

            for (int col = fromCol; col <= toCol; col++) {

                if (gameBoard[fromRow][col] instanceof Background) {

                    gameBoard[fromRow][col] = new Background(numbers.get(i));
                    i++;
                }
            }

            return;
        }

        for (int row = fromRow; row <= toRow; row++) {

            if (gameBoard[row][fromCol] instanceof Background) {

                gameBoard[row][fromCol] = new Background(numbers.get(i));
                i++;
            }
        }
    }
}