package com.example.spaceattack.svg;

import android.graphics.Color;

import java.util.Random;

public class SpaceDust {

    private int mX, mY;
    private int mSpeedY;
    private final int SPEED_UPPER_BOUND = 10;

    private int mMaxX;

    private int mMaxY;
    private int mMinY;

    public final static int COLOR = Color.argb(255, 255, 255, 255);

    public SpaceDust(int screenX, int screenY) {
        mMaxX = screenX;
        mMaxY = screenY;
        mMinY = 0;

        Random random = new Random();
        mSpeedY = random.nextInt(SPEED_UPPER_BOUND);
        mX = random.nextInt(mMaxX);
        mY = random.nextInt(mMaxY);
    }

    public void update(int playerSpeedY) {
        mY += playerSpeedY;
        mY += mSpeedY;

        if (mY > mMaxY) {

            mY = mMinY;
            Random random = new Random();
            mX = random.nextInt(mMaxX);
            mSpeedY = random.nextInt(SPEED_UPPER_BOUND);
        }
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }
}