package com.example.spaceattack.enemies.heroes;

import android.content.Context;

import com.example.spaceattack.enemies.EnemyEntity;

public class BigDummy1 extends EnemyEntity {

    public BigDummy1(Context context, int screenX, int screenY, float endDistance) {
        super(context, Type.BIG_DUMMY_1, screenX, screenY, endDistance);
    }
}
