package com.example.spaceattack.enemies.heroes;

import android.content.Context;

import com.example.spaceattack.WaypointEnemy;

public class FastHunter extends WaypointEnemy {
    public FastHunter(Context context, int screenX, int screenY, float endDistance) {
        super(context, Type.FAST_HUNTER_1, screenX, screenY, endDistance, 3, 400);
    }
}
