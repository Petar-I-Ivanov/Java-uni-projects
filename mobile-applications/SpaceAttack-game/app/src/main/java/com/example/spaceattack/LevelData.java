package com.example.spaceattack;

import com.example.spaceattack.enemies.EnemyEntityData;

import java.util.concurrent.CopyOnWriteArrayList;

public class LevelData {
    public CopyOnWriteArrayList<EnemyEntityData> enemyData;
    public float goalDistance;

    public LevelData(int goalDistance) {
        this.enemyData = new CopyOnWriteArrayList<>();
        this.goalDistance = goalDistance;
    }
}