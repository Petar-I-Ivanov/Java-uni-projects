package com.example.spaceattack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class MainActivity extends Activity {

    private static Entity.Type whichPLayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }

    public static Entity.Type getChosenPlayer() {
        return whichPLayer;
    }

    public void onPlayClick(View view) {
        whichPLayer = resIdToEntityType(view.getId());
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    public static Entity.Type resIdToEntityType(int playerId) {
        if (playerId == R.id.ibHero) return Entity.Type.HERO_1;
        else if (playerId == R.id.ibHero2) return Entity.Type.HERO_2;
        else if (playerId == R.id.ibHero3) return Entity.Type.HERO_3;
        else throw new AssertionError("Didn't find entity corresponding to res id");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return false;
    }
}