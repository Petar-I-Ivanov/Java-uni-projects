package com.example.spaceattack.enemies.heroes;

import android.content.Context;

import com.example.spaceattack.WaypointEnemy;

public class BigHunter1 extends WaypointEnemy {

    public BigHunter1(Context context, int screenX, int screenY, float endDistance) {
        super(context, Type.BIG_HUNTER_1, screenX, screenY, endDistance, 2, 100);
    }
}
