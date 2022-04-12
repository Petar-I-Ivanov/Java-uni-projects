package com.example.spaceattack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.spaceattack.R;

public abstract class Entity {

    private Bitmap mBitmap;
    private int mX, mY;
    private int mMaxX, mMinX;
    private int mSpeedX, mSpeedY;

    private Rect mHitBox;

    private Type mType;

    public enum Type {
        HERO_1,
        HERO_2,
        HERO_3,

        DUMMY_1,
        FAST_DUMMY_1,
        SUPER_FAST_DUMMY_1,
        BIG_DUMMY_1,
        HUNTER_1,
        FAST_HUNTER_1,
        BIG_HUNTER_1,
        ZIGZAG_1,

        SMALL_ASTEROID,
        BIG_ASTEROID,
    }

    public Entity(Context context, Type type, int screenX, int screenY) {

        switch (type) {
            case HERO_1:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero);
                break;
            case HERO_2:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero2);
                break;
            case HERO_3:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero3);
                break;
            case DUMMY_1:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dummy1);
                break;
            case FAST_DUMMY_1:
            case SUPER_FAST_DUMMY_1:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fast_dummy1);
                break;
            case BIG_DUMMY_1:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.big_dummy1);
                break;
            case HUNTER_1:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hunter1);
                break;
            case FAST_HUNTER_1:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fast_hunter1);
                break;
            case BIG_HUNTER_1:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.big_hunter1);
                break;
            case ZIGZAG_1:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.zigzag1);
                break;
            case SMALL_ASTEROID:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_asteroid);
                break;
            case BIG_ASTEROID:
                mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.big_asteroid);
                break;
            default:
                throw new AssertionError("Invalid type given to Entity()");
        }

        scaleBitmap(screenX, screenY);

        mType = type;

        mX = 50;
        mY = 50;
        mSpeedX = 0;
        mSpeedY = 0;

        mMaxX = screenX - mBitmap.getWidth();
        mMinX = 0;

        mHitBox = new Rect(mX, mY, mBitmap.getWidth(), mBitmap.getHeight());
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public int getX() {
        return mX;
    }

    protected void setX(int x) {
        mX = x;
    }

    public int getY() {
        return mY;
    }

    protected void setY(int y) {
        mY = y;
    }

    public int getMaxX() {
        return mMaxX;
    }

    public int getMinX() {
        return mMinX;
    }

    public int getSpeedX() {
        return mSpeedX;
    }

    protected void setSpeedX(int speed) {
        mSpeedX = speed;
    }

    public int getSpeedY() {
        return mSpeedY;
    }

    protected void setSpeedY(int speed) {
        mSpeedY = speed;
    }

    public Rect getHitBox() {
        return mHitBox;
    }

    protected void updateHitBox() {
        mHitBox.left = mX;
        mHitBox.top = mY;
        mHitBox.right = mX + mBitmap.getWidth();
        mHitBox.bottom = mY + mBitmap.getHeight();
    }

    public Type getType() {
        return mType;
    }

    public int getCenterX() {
        return mX + mBitmap.getWidth() / 2;
    }

    private void scaleBitmap(int screenX, int screenY) {
        float multiplierX = (float) screenX / 410;
        float multiplierY = (float) screenY / 730;
        mBitmap = Bitmap.createScaledBitmap(mBitmap,
                (int) (mBitmap.getWidth() * multiplierX),
                (int) (mBitmap.getHeight() * multiplierY),
                false);
    }
}
