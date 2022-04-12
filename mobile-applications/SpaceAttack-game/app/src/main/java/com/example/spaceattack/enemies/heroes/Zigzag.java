package com.example.spaceattack.enemies.heroes;

import android.content.Context;

import com.example.spaceattack.WaypointEnemy;

import java.util.Random;

public class Zigzag extends WaypointEnemy {

    private int mWaypoint1;
    private int mWaypoint2;
    private int mCurrWaypoint;
    private final int mDistanceBetweenWaypoints;

    public Zigzag(Context context, int screenX, int screenY, float endDistance) {
        super(context, Type.ZIGZAG_1, screenX, screenY, endDistance, 4, 700);

        mDistanceBetweenWaypoints = screenX / 3;
        decideWaypoints();
    }

    private void decideWaypoints() {
        mWaypoint1 = Math.max(getCenterX() - mDistanceBetweenWaypoints / 2, getMinX());
        mWaypoint2 = Math.min(mWaypoint1 + mDistanceBetweenWaypoints, getMaxX());

        Random generator = new Random();
        mCurrWaypoint = generator.nextInt(2) + 1;
        setWaypointX((mCurrWaypoint == 1) ? mWaypoint1 : mWaypoint2);
    }

    @Override
    protected void respawn() {
        super.respawn();

        decideWaypoints();
    }

    @Override
    public boolean update(int playerSpeedY) {
        boolean changedWaypoint =
                setWaypointX((mCurrWaypoint == 1) ? mWaypoint2 : mWaypoint1);
        if (changedWaypoint)
            mCurrWaypoint = (mCurrWaypoint == 1) ? 2 : 1;

        return super.update(playerSpeedY);
    }
}
