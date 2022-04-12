package com.example.spaceattack.enemies.heroes;

import android.content.Context;

import com.example.spaceattack.WaypointEnemy;

public class Hunter extends WaypointEnemy {

    public Hunter(Context context, int screenX, int screenY, float endDistance) {
        super(context, Type.HUNTER_1, screenX, screenY, endDistance, 2, 500);
    }
}
