package com.example.spaceattack.svg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.spaceattack.Entity;
import com.example.spaceattack.LevelData;
import com.example.spaceattack.Player;
import com.example.spaceattack.WaypointEnemy;
import com.example.spaceattack.enemies.heroes.BigAsteroid;
import com.example.spaceattack.enemies.EnemyEntity;
import com.example.spaceattack.enemies.EnemyEntityData;
import com.example.spaceattack.enemies.heroes.BigDummy1;
import com.example.spaceattack.enemies.heroes.BigHunter1;
import com.example.spaceattack.enemies.heroes.Dummy;
import com.example.spaceattack.enemies.heroes.FastDummy;
import com.example.spaceattack.enemies.heroes.FastHunter;
import com.example.spaceattack.enemies.heroes.Hunter;
import com.example.spaceattack.enemies.heroes.SmallAsteroid;
import com.example.spaceattack.enemies.heroes.SuperFastDummy;
import com.example.spaceattack.enemies.heroes.Zigzag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class SGView extends SurfaceView implements Runnable {

    private Context mContext;

    private int mScreenX;
    private int mScreenY;

    private volatile boolean mIsPlaying = true;
    private volatile boolean mShouldRestartGame;
    private Thread mGameThread = null;

    private Player mPlayer;
    private ArrayList<EnemyEntity> mEnemyEntities;
    private CopyOnWriteArrayList<EnemyEntityData> mEnemyData;

    private CopyOnWriteArrayList<SpaceDust> mDustList;
    private static final int NUMBER_OF_DUST = 175;

    private static final int IDEAL_FRAMES_PER_SECOND = 60;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final long SLEEP_TIME_MILLISECONDS = MILLISECONDS_PER_SECOND / IDEAL_FRAMES_PER_SECOND;

    private float mForwardDistanceRemaining;
    private float mForwardDistanceGoal;

    private boolean mFinishedGame = false;
    private boolean mFinishedLevel = false;
    private boolean mLost = false;

    private long mGameEndTime;
    private static final long GAME_END_WAIT_MILLISECONDS = 1000;

    private ArrayList<LevelData> mLevels;
    private LevelData mCurrentLevel;
    private int mLevelIndex;

    private Paint mPaint;
    private Canvas mCanvas;
    private SurfaceHolder mHolder;

    public SGView(Context context, int screenX, int screenY) {
        super(context);

        mContext = context;

        mScreenX = screenX;
        mScreenY = screenY;

        mHolder = getHolder();
        mPaint = new Paint();

        mEnemyEntities = new ArrayList<EnemyEntity>();
        mEnemyData = new CopyOnWriteArrayList<EnemyEntityData>();
        mDustList = new CopyOnWriteArrayList<SpaceDust>();

        restartGame();
        makeNewDustList();
    }

    private void initializeLevelData() {
        mLevels = new ArrayList<>();
        LevelData levelData;

        // Level 1
        levelData = new LevelData(1700);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.DUMMY_1, 0, 500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.DUMMY_1, 100, 700));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 0, 1700));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 50, 1700));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 100, 1700));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 600, 1700));
        mLevels.add(levelData);

        // Level 2
        levelData = new LevelData(1200);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.ZIGZAG_1, 0, 1200));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.ZIGZAG_1, 600, 1200));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 900, 1200));
        mLevels.add(levelData);

        // Level 3
        levelData = new LevelData(1500);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 0, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 50, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 100, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 150, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 200, 1000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 250, 1100));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 300, 1200));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 350, 1300));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 400, 1400));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_ASTEROID, 850, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_ASTEROID, 1000, 1500));
        mLevels.add(levelData);

        // Level 4
        levelData = new LevelData(1000);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 0, 1100));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 50, 1100));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.DUMMY_1, 0, 1000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.DUMMY_1, 50, 1000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 100, 1000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 300, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 600, 1500));
        mLevels.add(levelData);

        // Level 5
        levelData = new LevelData(1500);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 0, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 100, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 200, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 600, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 1000, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 1400, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 1700, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 1000, 2500));
        mLevels.add(levelData);

        // Level 6
        levelData = new LevelData(1800);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 0, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 100, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 200, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 120, 800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 300, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 400, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 200, 800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_HUNTER_1, 500, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_HUNTER_1, 800, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_ASTEROID, 850, 1800));
        mLevels.add(levelData);

        // Level 7
        levelData = new LevelData(1500);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_ASTEROID, 0, 500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_ASTEROID, 40, 500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_ASTEROID, 80, 500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 300, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.ZIGZAG_1, 600, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.ZIGZAG_1, 850, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.ZIGZAG_1, 1000, 1500));
        mLevels.add(levelData);

        // Level 8
        levelData = new LevelData(2300);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_DUMMY_1, 0, 500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_DUMMY_1, 100, 500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_HUNTER_1, 300, 1000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.ZIGZAG_1, 600, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.ZIGZAG_1, 850, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.ZIGZAG_1, 1000, 2300));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_HUNTER_1, 1700, 2300));
        mLevels.add(levelData);

        // Level 9
        levelData = new LevelData(1500);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 0, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 50, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.DUMMY_1, 100, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 150, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 200, 1000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.DUMMY_1, 250, 1100));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 300, 1200));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 350, 1300));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 400, 1400));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 500, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 600, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 700, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_ASTEROID, 850, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_ASTEROID, 1000, 1500));
        mLevels.add(levelData);

        // Level 10
        levelData = new LevelData(1500);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 0, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 100, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 100, 900));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 200, 1000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 250, 1100));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 300, 1200));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 400, 1400));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_HUNTER_1, 500, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 700, 1500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_HUNTER_1, 1000, 1500));
        mLevels.add(levelData);

        // Level 11
        levelData = new LevelData(1500);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 0, 2000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 200, 2000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 400, 2000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 600, 2000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 800, 2000));
        mLevels.add(levelData);

        // Level 12
        levelData = new LevelData(1800);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_HUNTER_1, 0, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.BIG_HUNTER_1, 100, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 900, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 1200, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 1500, 1800));
        mLevels.add(levelData);

        // Level 13
        levelData = new LevelData(4000);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 0, 4000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 300, 4000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 0, 800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 100, 800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 200, 800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 300, 1200));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_DUMMY_1, 400, 1200));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 800, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 1000, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 1200, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 1400, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 1600, 2500));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 2800, 4000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 2800, 4000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 2900, 4000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 3000, 4000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 3100, 4000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 3200, 4000));
        mLevels.add(levelData);

        // Level 14
        levelData = new LevelData(1800);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 0, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 100, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 180, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 250, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SMALL_ASTEROID, 320, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.ZIGZAG_1, 200, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.ZIGZAG_1, 500, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.ZIGZAG_1, 800, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 500, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 600, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.SUPER_FAST_DUMMY_1, 700, 1800));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 1200, 1800));
        mLevels.add(levelData);

        // Level 15
        levelData = new LevelData(1500);
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 0, 2000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.HUNTER_1, 200, 2000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_HUNTER_1, 400, 2000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_HUNTER_1, 600, 2000));
        levelData.enemyData.add(
                new EnemyEntityData(Entity.Type.FAST_HUNTER_1, 800, 2000));
        mLevels.add(levelData);
    }

    private void spawnEnemies() {
        float distanceTravelled = mForwardDistanceGoal - mForwardDistanceRemaining;

        for (EnemyEntityData eed : mEnemyData) {
            if (!eed.hasSpawned && distanceTravelled >= eed.startDistance) {

                switch (eed.type) {
                    case DUMMY_1:
                        mEnemyEntities.add(
                                new Dummy(mContext, mScreenX, mScreenY,
                                        eed.endDistance));
                        break;
                    case FAST_DUMMY_1:
                        mEnemyEntities.add(
                                new FastDummy(mContext, mScreenX, mScreenY,
                                        eed.endDistance));
                        break;
                    case SUPER_FAST_DUMMY_1:
                        mEnemyEntities.add(
                                new SuperFastDummy(mContext, mScreenX, mScreenY,
                                        eed.endDistance));
                        break;
                    case BIG_DUMMY_1:
                        mEnemyEntities.add(
                                new BigDummy1(mContext, mScreenX, mScreenY,
                                        eed.endDistance));
                        break;
                    case HUNTER_1:
                        mEnemyEntities.add(
                                new Hunter(mContext, mScreenX, mScreenY,
                                        eed.endDistance));
                        break;
                    case FAST_HUNTER_1:
                        mEnemyEntities.add(
                                new FastHunter(mContext, mScreenX, mScreenY,
                                        eed.endDistance));
                        break;
                    case BIG_HUNTER_1:
                        mEnemyEntities.add(
                                new BigHunter1(mContext, mScreenX, mScreenY,
                                        eed.endDistance));
                        break;
                    case ZIGZAG_1:
                        mEnemyEntities.add(
                                new Zigzag(mContext, mScreenX, mScreenY,
                                        eed.endDistance));
                        break;
                    case SMALL_ASTEROID:
                        mEnemyEntities.add(
                                new SmallAsteroid(mContext,
                                        mScreenX, mScreenY,
                                        eed.endDistance));
                        break;
                    case BIG_ASTEROID:
                        mEnemyEntities.add(
                                new BigAsteroid(mContext,
                                        mScreenX, mScreenY,
                                        eed.endDistance));
                        break;
                    default:
                        throw new AssertionError(
                                "Spawned enemy has invalid type");
                }

                eed.hasSpawned = true;
            }
        }
    }

    private void despawnEnemies() {
        float distanceTravelled = mForwardDistanceGoal - mForwardDistanceRemaining;

        for (Iterator<EnemyEntity> itr = mEnemyEntities.iterator(); itr.hasNext(); ) {
            EnemyEntity enemy = itr.next();
            if (!enemy.isMarkedForRemoval() && distanceTravelled >= enemy.getEndDistance())
                enemy.markForRemoval();
        }
    }

    private void restartGame() {
        if (mFinishedGame) {
            mLevelIndex = 0;
            initializeLevelData();
            mCurrentLevel = mLevels.get(mLevelIndex);
        } else if (mFinishedLevel) {
            ++mLevelIndex;
            mCurrentLevel = mLevels.get(mLevelIndex);
        } else if (mLost) {
            initializeLevelData();
            mCurrentLevel = mLevels.get(mLevelIndex);
        } else {
            initializeLevelData();
            mCurrentLevel = mLevels.get(mLevelIndex);
        }

        mShouldRestartGame = false;
        mFinishedGame = mFinishedLevel = mLost = false;

        mForwardDistanceGoal = mCurrentLevel.goalDistance;
        mForwardDistanceRemaining = mForwardDistanceGoal;

        mEnemyData = mCurrentLevel.enemyData;
        mPlayer = new Player(mContext, mScreenX, mScreenY);
        mEnemyEntities.clear();
        spawnEnemies();
    }

    private void makeNewDustList() {
        mDustList.clear();
        for (int i = 0; i < NUMBER_OF_DUST; i++) {
            SpaceDust speck = new SpaceDust(mScreenX, mScreenY);
            mDustList.add(speck);
        }
    }

    @Override
    public void run() {
        while (mIsPlaying) {
            update();
            draw();
            controlFrameRate();

            if (gameEnded() && mShouldRestartGame)
                restartGame();
        }
    }

    private void update() {
        if (!gameEnded()) {
            if (isCollision())
                resolveLoss();
        }

        mPlayer.update();

        int playerCenterX = mPlayer.getCenterX();
        int playerSpeedY = mPlayer.getSpeedY();
        updateEnemies(playerCenterX, playerSpeedY);

        for (SpaceDust sd : mDustList)
            sd.update(playerSpeedY);

        if (!gameEnded())
            updateRemainingDistance();
    }

    private boolean isCollision() {
        Rect playerHitBox = mPlayer.getHitBox();

        for (EnemyEntity es : mEnemyEntities) {
            if (Rect.intersects(playerHitBox, es.getHitBox()))
                return true;
        }

        return false;
    }

    private void updateEnemies(int playerCenterX, int playerSpeedY) {
        for (Iterator<EnemyEntity> itr = mEnemyEntities.iterator();
             itr.hasNext(); ) {
            EnemyEntity enemy = itr.next();

            if (enemy.isHunter()) {
                ((WaypointEnemy) enemy).setWaypointX(playerCenterX);
            }

            boolean shouldDestroy = !(enemy.update(playerSpeedY));
            if (shouldDestroy)
                itr.remove();
        }

        if (!gameEnded()) {
            spawnEnemies();
            despawnEnemies();
        }
    }

    private void updateRemainingDistance() {
        mForwardDistanceRemaining -= mPlayer.getSpeedY();

        if (mForwardDistanceRemaining < 0) {
            resolveWin();
        }
    }

    private void resolveLoss() {
        mLost = true;
        mGameEndTime = System.currentTimeMillis();
    }

    private void resolveWin() {

        mEnemyEntities.clear();

        if ((mLevelIndex + 1) == mLevels.size())
            mFinishedGame = true;
        else
            mFinishedLevel = true;

        mGameEndTime = System.currentTimeMillis();
    }

    private void draw() {
        if (mHolder.getSurface().isValid()) {

            mCanvas = mHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 0, 0, 0));

            mPaint.setColor(SpaceDust.COLOR);
            for (SpaceDust sd : mDustList)
                mCanvas.drawPoint(sd.getX(), sd.getY(), mPaint);

            if (!mLost)
                mCanvas.drawBitmap(
                        mPlayer.getBitmap(),
                        mPlayer.getX(),
                        mPlayer.getY(),
                        mPaint);

            for (EnemyEntity es : mEnemyEntities)
                mCanvas.drawBitmap(es.getBitmap(), es.getX(),
                        es.getY(), mPaint);

            if (!gameEnded())
                drawHUD();
            else {
                if (mFinishedGame)
                    drawFinishedGameScreen();
                else if (mFinishedLevel)
                    drawFinishedLevelScreen();
                else if (mLost)
                    drawLossScreen();
            }

            mHolder.unlockCanvasAndPost(mCanvas);
        }
    }

    private void drawHUD() {
        mPaint.setColor(Color.argb(255, 255, 255, 255));

        int levelTextSize = 25;
        mPaint.setTextSize(levelTextSize);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mCanvas.drawText("Level: " + (mLevelIndex + 1),
                20, levelTextSize, mPaint);

        int distanceTextSize = 25;
        mPaint.setTextSize(distanceTextSize);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mCanvas.drawText("Remaining: " + mForwardDistanceRemaining +
                " meters", mScreenX - 20, distanceTextSize, mPaint);
    }

    private void drawFinishedGameScreen() {
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(60);
        mPaint.setColor(Color.RED);
        mCanvas.drawText("Game finished!", mScreenX / 2, mScreenY / 2, mPaint);
        mPaint.setColor(Color.WHITE);

        mPaint.setTextSize(40);
        mCanvas.drawText("Tap to restart game.", mScreenX / 2,
                mScreenY / 2 + 60, mPaint);
    }

    private void drawFinishedLevelScreen() {
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(60);
        mCanvas.drawText("Level Completed!",
                mScreenX / 2, (mScreenY / 2) - 50, mPaint);

        mPaint.setTextSize(30);
        mCanvas.drawText("Completed: " + mForwardDistanceGoal,
                mScreenX / 2, mScreenY / 2 + 10, mPaint);

        mPaint.setTextSize(40);
        mPaint.setColor(Color.RED);
        mCanvas.drawText("Tap to advance.", mScreenX / 2,
                mScreenY / 2 + 100, mPaint);
    }

    private void drawLossScreen() {
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(60);
        mCanvas.drawText("You lost... :(", mScreenX / 2,
                (mScreenY / 2) - 50, mPaint);

        mPaint.setTextSize(30);
        mCanvas.drawText("Remaining: " + mForwardDistanceRemaining,
                mScreenX / 2, (mScreenY / 2) + 10, mPaint);

        mPaint.setTextSize(40);
        mPaint.setColor(Color.RED);
        mCanvas.drawText("Tap to restart level.", mScreenX / 2,
                (mScreenY / 2) + 100, mPaint);
    }

    private void controlFrameRate() {
        try {
            mGameThread.sleep(SLEEP_TIME_MILLISECONDS);
        } catch (InterruptedException e) {

        }
    }

    public void startGame() {
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!gameEnded()) {

            boolean left = false;
            boolean right = false;

            int pointerCount = motionEvent.getPointerCount();
            for (int i = 0; i < pointerCount; ++i) {
                int x = (int) motionEvent.getX(i);

                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        if (x < mScreenX / 2) {
                            left = true;
                        } else {
                            right = true;
                        }
                        break;
                }
            }

            mPlayer.setPressingLeft(left);
            mPlayer.setPressingRight(right);
        } else {
            mPlayer.setPressingLeft(false);
            mPlayer.setPressingRight(false);

            if (System.currentTimeMillis() >
                    mGameEndTime + GAME_END_WAIT_MILLISECONDS) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        mShouldRestartGame = true;
                        break;
                }
            }
        }

        return true;
    }

    private boolean gameEnded() {
        return mFinishedGame || mFinishedLevel || mLost;
    }

}
