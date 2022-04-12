package com.example.spaceattack;

import android.content.Context;

import com.example.spaceattack.enemies.EnemyEntity;

import java.util.Random;

public class WaypointEnemy extends EnemyEntity {

    private int mMaxSpeedX;
    private int mWaypointX;
    private long mLastWaypointSetTime;
    private long mWaypointUpdatePeriod;

    public WaypointEnemy(Context context, Type type, int screenX,
                         int screenY, float endDistance, int maxSpeedX,
                         long waypointUpdatePeriod) {
        super(context, type, screenX, screenY, endDistance);

        mMaxSpeedX = maxSpeedX;

        Random random = new Random();
        mWaypointX = random.nextInt(screenX);

        mLastWaypointSetTime = System.currentTimeMillis();
        mWaypointUpdatePeriod = waypointUpdatePeriod;
    }

    public boolean setWaypointX(int newWaypointX) {
        if (System.currentTimeMillis() >
                mLastWaypointSetTime + mWaypointUpdatePeriod) {
            mLastWaypointSetTime = System.currentTimeMillis();
            mWaypointX = newWaypointX;
            return true;
        } else
            return false;
    }

    @Override
    public boolean update(int playerSpeedY) {

        int centerX = getCenterX();

        if (mWaypointX < centerX)
            setSpeedX(-mMaxSpeedX);
        else if (mWaypointX > centerX)
            setSpeedX(mMaxSpeedX);
        else
            setSpeedX(0);

        return super.update(playerSpeedY);
    }
}