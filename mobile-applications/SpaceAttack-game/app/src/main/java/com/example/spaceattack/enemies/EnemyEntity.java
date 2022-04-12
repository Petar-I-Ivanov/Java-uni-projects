package com.example.spaceattack.enemies;

import android.content.Context;

import com.example.spaceattack.Entity;

import java.util.Random;

public abstract class EnemyEntity extends Entity {

    private int mMaxY, mMinY;
    private float mEndDistance;
    private boolean mIsMarkedForRemoval;

    public EnemyEntity(Context context, Type type, int screenX, int screenY, float endDistance) {
        super(context, type, screenX, screenY);

        mMaxY = screenY + getBitmap().getHeight();
        mMinY = -1 * getBitmap().getHeight();

        mEndDistance = endDistance;
        mIsMarkedForRemoval = false;

        respawn();
        updateHitBox();
    }

    public float getEndDistance() {
        return mEndDistance;
    }

    public boolean isMarkedForRemoval() {
        return mIsMarkedForRemoval;
    }

    public void markForRemoval() {
        mIsMarkedForRemoval = true;
    }

    public boolean update(int playerSpeedY) {
        setX(getX() + getSpeedX());
        setY(getY() + playerSpeedY + getSpeedY());

        if (getY() > mMaxY) {
            if (mIsMarkedForRemoval) return false;
            else respawn();
        }

        updateHitBox();
        return true;
    }

    protected void respawn() {
        setRandomNewPosition();
        setRandomNewSpeedY();
    }

    private void setRandomNewSpeedY() {
        Random random = new Random();

        if (isAsteroid()) setSpeedY(random.nextInt(2) + 3);
        else if (isFastVariation()) setSpeedY(random.nextInt(3) + 9);
        else if (isSuperFastVariation()) setSpeedY(random.nextInt(3) + 15);
        else setSpeedY(random.nextInt(3) + 5);
    }

    private void setRandomNewPosition() {

        int y = mMinY;

        Random random = new Random();
        int x = random.nextInt(getMaxX());

        setX(x);
        setY(y);
    }

    public boolean isAsteroid() {
        switch (getType()) {
            case SMALL_ASTEROID:
            case BIG_ASTEROID:
                return true;
            default:
                return false;
        }
    }

    public boolean isFastVariation() {
        switch (getType()) {
            case FAST_DUMMY_1:
            case FAST_HUNTER_1:
                return true;
            default:
                return false;
        }
    }

    public boolean isSuperFastVariation() {
        switch (getType()) {
            case SUPER_FAST_DUMMY_1:
                return true;
            default:
                return false;
        }
    }

    public boolean isHunter() {
        switch (getType()) {
            case HUNTER_1:
            case BIG_HUNTER_1:
            case FAST_HUNTER_1:
                return true;
            default:
                return false;
        }
    }
}
