package com.lud.root.jetfighter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

public class GameActivity extends AppCompatActivity{
    private GameView gameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the display object
        Display display = getWindowManager().getDefaultDisplay();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Get the screen Resolution
        Point size = new Point();
        display.getSize(size);
        //initialize the gameview object
        gameView = new GameView(this, size.x,size.y);
        setContentView(gameView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        gameView.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.gc();
        gameView.resume();
        gameView.resumeMusic();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MyActivity.class));
    }
}
