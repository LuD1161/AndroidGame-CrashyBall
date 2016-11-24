package com.lud.root.jetfighter;


import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

public class Instruction extends AppCompatActivity implements View.OnClickListener{

    private AnimationDrawable tap, collision;
    private VideoView imv1, imv3;
    private LinearLayout ll;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.gc();
        setContentView(R.layout.activity_instruction);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ll = (LinearLayout)findViewById(R.id.instruction);
        ll.setOnClickListener(this);

        imv1 = (VideoView) findViewById(R.id.instruction_imv1);
        imv3 = (VideoView) findViewById(R.id.instruction_imv3);

        // Load and start the movie
        Uri video1 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.touch);
        imv1.setVideoURI(video1);
        imv1.start();

        imv1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        Uri video2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.collision);
        imv3.setVideoURI(video2);
        imv3.start();

        imv3.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startActivity(new Intent(this, MyActivity.class));
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, MyActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindDrawables(findViewById(R.id.instruction));
        System.gc();
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
