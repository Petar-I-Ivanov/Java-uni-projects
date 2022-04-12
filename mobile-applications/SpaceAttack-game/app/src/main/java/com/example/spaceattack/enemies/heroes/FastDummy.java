package com.example.spaceattack.enemies.heroes;

import android.content.Context;

import com.example.spaceattack.enemies.EnemyEntity;

public class FastDummy extends EnemyEntity {
    public FastDummy(Context context, int screenX, int screenY, float endDistance) {
        super(context, Type.FAST_DUMMY_1, screenX, screenY, endDistance);
    }
}
