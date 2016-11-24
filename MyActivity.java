package com.lud.root.jetfighter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    // Image Button
    private ImageButton buttonPlay, buttonScore, buttonInstruction, buttonDeveloper;
    private RelativeLayout relativeLayout;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //To portarit
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        relativeLayout = (RelativeLayout)findViewById(R.id.rlMy);
        buttonPlay = (ImageButton)findViewById(R.id.buttonPlay);
        buttonScore = (ImageButton)findViewById(R.id.buttonScore);
        buttonInstruction = (ImageButton)findViewById(R.id.buttonInstruction);
        buttonDeveloper = (ImageButton)findViewById(R.id.buttonDeveloper);
        buttonPlay.setOnClickListener(this);
        buttonScore.setOnClickListener(this);
        buttonInstruction.setOnClickListener(this);
        buttonDeveloper.setOnClickListener(this);

        relativeLayout.setBackgroundResource(R.drawable.mainscreen);

        animationDrawable = (AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.start();
    }

    private void clearData() {
        try {
            // clearing app data
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear com.lud.root.jetfighter;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonPlay:
                startActivity(new Intent(this, GameActivity.class));
                break;
            case R.id.buttonScore:
                startActivity(new Intent(this, HighScore.class));
                break;
            case R.id.buttonDeveloper:
                startActivity(new Intent(this, WelcomeActivity.class));
                break;

            case R.id.buttonInstruction:
                startActivity(new Intent(this, Instruction.class));
                break;

        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure you want to exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameView.stopMusic();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.gc();
        animationDrawable.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindDrawables(findViewById(R.id.rlMy));
        clearData();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
