package units.heroes;

import game.PlayerTypeEnum;
import units.Barricade;
import units.Terrain;
import units.Units;

public class Dwarf extends Heroes {

    public Dwarf(PlayerTypeEnum playerType, int row, int col) {

        super(playerType,"@", row, col);

        this.attack = 6;
        this.armor  = 2;
        this.health = 12;
        this.range  = 2;
        this.speed  = 2;

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

        boolean isInRange = (rowCoefficient <= this.speed && colCoefficient == 0)
                         || (rowCoefficient == 0 && colCoefficient <= this.speed);

        return isInRange && gameBoard[toRow][toCol] instanceof Terrain;
    }
}