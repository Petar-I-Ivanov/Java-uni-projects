package com.example.spaceattack.enemies.heroes;

import android.content.Context;

import com.example.spaceattack.enemies.EnemyEntity;

public class SmallAsteroid extends EnemyEntity {

    public SmallAsteroid(Context context, int screenX, int screenY,
                         float endDistance) {
        super(context, Type.SMALL_ASTEROID, screenX, screenY, endDistance);
    }
}
