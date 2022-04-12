package com.example.spaceattack.enemies.heroes;

import android.content.Context;

import com.example.spaceattack.enemies.EnemyEntity;

public class Dummy extends EnemyEntity {

    public Dummy(Context context, int screenX, int screenY, float endDistance) {
        super(context, Type.DUMMY_1, screenX, screenY, endDistance);
    }
}
