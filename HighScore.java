package com.lud.root.jetfighter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HighScore extends AppCompatActivity {

    TextView tv1, tv2, tv3, tv4;
    RelativeLayout relativeLayout;
    SharedPreferences sharedPreferences;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        relativeLayout = (RelativeLayout)findViewById(R.id.activity_high_score);
        tv1 = (TextView)findViewById(R.id.textView);
        tv2 = (TextView)findViewById(R.id.textView2);
        tv3 = (TextView)findViewById(R.id.textView3);
        tv4 = (TextView)findViewById(R.id.textView4);

        sharedPreferences = getApplicationContext().getSharedPreferences("Scores", 0);

        tv1.setText("1. "+sharedPreferences.getInt("score1",0)+"\n2. "+sharedPreferences.getInt("score2",0)
                +"\n3. "+sharedPreferences.getInt("score3",0)+"\n4. "+sharedPreferences.getInt("score4",0));

        relativeLayout.setBackgroundResource(R.drawable.mainscreen);

        animationDrawable = (AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.start();
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
        Log.d("ActivityCycle","OnDestroy Called");
        unbindDrawables(findViewById(R.id.activity_high_score));
        System.gc();
    }

    private void unbindDrawables(View view) {
        Log.d("ActivityCycle","unbindDrawables Called");
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


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
                startActivity(new Intent(this,MyActivity.class));
            return true;
        }
}
