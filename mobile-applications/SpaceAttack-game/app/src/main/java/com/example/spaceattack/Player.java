package com.example.spaceattack;

import android.content.Context;

public class Player extends Entity {

    private boolean mIsPressingRight = false;
    private boolean mIsPressingLeft = false;

    private int mMaxSpeedX = 0;
    private int mMaxSpeedY = 0;

    public Player(Context context, int screenX, int screenY) {
        super(context, MainActivity.getChosenPlayer(), screenX, screenY);

        setX(screenX / 2);
        setY(screenY - 200);

        mMaxSpeedY = decideMaxSpeedY();
        mMaxSpeedX = decideMaxSpeedX();

        setSpeedY(mMaxSpeedY);
    }

    public void setPressingRight(boolean isPressingRight) {
        mIsPressingRight = isPressingRight;
    }

    public void setPressingLeft(boolean isPressingLeft) {
        mIsPressingLeft = isPressingLeft;
    }

    private int decideMaxSpeedY() {
        switch (getType()) {
            case HERO_1:
                return 2;
            case HERO_2:
                return 3;
            case HERO_3:
                return 2;
            default:
                throw new AssertionError("Invalid player type ");
        }
    }

    private int decideMaxSpeedX() {
        switch (getType()) {
            case HERO_1:
                return 10;
            case HERO_2:
                return 8;
            case HERO_3:
                return 15;
            default:
                throw new AssertionError("Invalid player type ");
        }
    }

    public void update() {
        setX(getX() + updateSpeedX());
        keepPlayerOnScreen();
        updateHitBox();
    }

    private int updateSpeedX() {
        int newSpeed;

        if (mIsPressingLeft && mIsPressingRight) newSpeed = 0;
        else if (mIsPressingLeft) newSpeed = -mMaxSpeedX;
        else if (mIsPressingRight) newSpeed = mMaxSpeedX;
        else newSpeed = 0;

        setSpeedX(newSpeed);
        return newSpeed;
    }

    private void keepPlayerOnScreen() {
        int x = getX();
        int minX = getMinX();
        int maxX = getMaxX();

        if (x < minX) setX(minX);
        if (x > maxX) setX(maxX);
    }
}