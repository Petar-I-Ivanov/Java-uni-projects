package units.heroes;

import game.PlayerTypeEnum;
import units.Barricade;
import units.Terrain;
import units.Units;

public class Knight extends Heroes {

    public Knight(PlayerTypeEnum playerType, int row, int col) {

        super(playerType,"%", row, col);

        this.attack = 8;
        this.armor  = 3;
        this.health = 15;
        this.range  = 1;
        this.speed  = 1;

        this.isHealthPotionUsed = false;
    }

    @Override
    public boolean isAttackPossible(Units[][] gameBoard, int toRow, int toCol) {

        int rowCoefficient = Math.abs(toRow - this.row);
        int colCoefficient = Math.abs(toCol - this.col);

        boolean isInRange = rowCoefficient == this.range
                         || colCoefficient == this.range;

        return isInRange && (gameBoard[toRow][toCol] instanceof Heroes ||
                             gameBoard[toRow][toCol] instanceof Barricade);
    }

    @Override
    public boolean isMovePossible(Units[][] gameBoard, int toRow, int toCol) {

        int rowCoefficient = Math.abs(toRow - this.row);
        int colCoefficient = Math.abs(toCol - this.col);

        boolean isInRange = (rowCoefficient == this.speed && colCoefficient == 0)
                         || (rowCoefficient == 0 && colCoefficient == this.speed);

        return isInRange && gameBoard[toRow][toCol] instanceof Terrain;
    }
}