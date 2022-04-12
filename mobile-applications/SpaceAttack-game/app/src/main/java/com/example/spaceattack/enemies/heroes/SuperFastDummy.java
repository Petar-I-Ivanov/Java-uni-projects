package com.example.spaceattack.enemies.heroes;

import android.content.Context;

import com.example.spaceattack.enemies.EnemyEntity;
import com.example.spaceattack.Entity;

public class SuperFastDummy extends EnemyEntity {
    public SuperFastDummy(Context context, int screenX, int screenY, float endDistance) {
        super(context, Entity.Type.SUPER_FAST_DUMMY_1, screenX, screenY, endDistance);
    }
}
