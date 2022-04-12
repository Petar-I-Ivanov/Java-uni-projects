package units.heroes;

import game.PlayerTypeEnum;
import units.Barricade;
import units.Terrain;
import units.Units;

import java.util.ArrayList;

public abstract class Heroes extends Units {

    protected int attack;
    protected int armor;
    protected int health;
    protected int range;
    protected int speed;

    protected boolean isHealthPotionUsed;

    protected int row;
    protected int col;

    PlayerTypeEnum playerType;

    protected Heroes (PlayerTypeEnum playerType, String sign, int row, int col) {

        super(sign);

        this.playerType = playerType;
        this.row        = row;
        this.col        = col;
    }

    public String getSignAndType() {

        return (this.playerType == PlayerTypeEnum.BLACK)
                ? "B" + this.sign
                : "R" + this.sign;
    }

    public PlayerTypeEnum getPlayerType() {
        return this.playerType;
    }

    public int getHealth() {
        return this.health;
    }

    public int getArmor() {
        return this.armor;
    }

    public void move(int newRow, int newCol) {

        this.row = newRow;
        this.col = newCol;
    }

    public void attack(Units[][] gameBoard, int row, int col, ArrayList<Units> kills) {

        if (gameBoard[row][col] instanceof Heroes element) {

            element.subHealth(this.attack - element.getArmor());

            if (element.getHealth() <= 0) {

                element.removeRowCol();
                kills.add(element);
                gameBoard[row][col] = new Terrain();
            }
        }

        if (gameBoard[row][col] instanceof Barricade) {
            kills.add(gameBoard[row][col]);
            gameBoard[row][col] = new Terrain();
        }
    }

    public void heal(int healthToAdd) {
        this.health += healthToAdd;
    }

    public void removeRowCol() {
        this.row = -1;
        this.col = -1;
    }

    public boolean isHealthPotionUsed() {
        return this.isHealthPotionUsed;
    }

    public void setHealthPotionUsed() {
        this.isHealthPotionUsed = true;
    }

    private void subHealth(int healthToSub) {
        this.health -= healthToSub;
    }

    public abstract boolean isAttackPossible(Units[][] gameBoard, int toRow, int toCol);
    public abstract boolean isMovePossible(Units[][] gameBoard, int toRow, int toCol);
}