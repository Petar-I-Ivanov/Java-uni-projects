package com.example.spaceattack.enemies.heroes;

import android.content.Context;

import com.example.spaceattack.enemies.EnemyEntity;

public class BigAsteroid extends EnemyEntity {

    public BigAsteroid(Context context, int screenX, int screenY, float endDistance) {
        super(context, Type.BIG_ASTEROID, screenX, screenY, endDistance);
    }
}
