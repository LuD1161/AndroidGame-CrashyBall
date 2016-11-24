package com.lud.root.jetfighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

public class Player {
    private Bitmap bitmap;

    //coordinates
    private int x;
    private int y;

    private float speed ;
    private boolean slowing, goingDown;
    private int GRAVITY = -10;    // For the gravity effect on the ship

    //Boundaries
    private int maxY;
    private int minY;
    private int screenY;
    private float radius;

    private final float MINRADIUS = 30;
    private final float MAXRADIUS = 100;

    private final int POSGRAVITY = +10;
    private final int NEGGRAVITY = -10;

    private final int SLOWPOSGRAVITY = +4;
    private final int SLOWNEGGRAVITY = -4;

    public Player(Context context, int screenX, int screenY){
        x = 30; //initial x-position
        y = 50; //initial y-position
        speed = -4;
        radius = MINRADIUS;
        maxY = screenY - 30;
        this.screenY = screenY;
        minY = 30;  //equal to initial radius
        slowing = false;   //initially slowing is false
        goingDown = true;
    }

    public void startSlowing(){
        slowing = true;
        if(radius > MAXRADIUS)
            radius = MAXRADIUS;
        else radius += 1.5f;
        if(goingDown)
        {
            speed = -0.3f;
            GRAVITY = SLOWNEGGRAVITY;
        }
        else{
            speed = 0.3f;
            GRAVITY = SLOWPOSGRAVITY;
        }

    }

    public void stopSlowing(){
        slowing = false;
        if(goingDown)
        {
            speed = -4.0f;
            GRAVITY = NEGGRAVITY;
        }
        else{
            speed = 4.0f;
            GRAVITY = POSGRAVITY;
        }
    }

    public void update(){
        minY = (int) getRadius();
        maxY = screenY - (int) getRadius();

        if(!slowing)
            if(radius < MINRADIUS)
                radius = MINRADIUS;
            else radius -= 0.5f;
        if(slowing)
            if(radius > MAXRADIUS)
                radius = MAXRADIUS;
            else radius += 0.5f;
        // Moving the ball down
        y -= speed + GRAVITY;
        if(y < minY)
        {
            y = minY;
            if(slowing)
            {
                speed = -0.3f;
                GRAVITY = SLOWNEGGRAVITY;
            }
            else
            {
                speed = -4;
                GRAVITY = NEGGRAVITY;
            }
            goingDown = true;
            Log.d("down","Gravity : "+ GRAVITY + " Speed : "+speed+" y : "+y+" goingDown : "+goingDown);
        }
        if( y > maxY)
        {
            y = maxY;
            if(slowing)
            {
                speed = 0.3f;
                GRAVITY = SLOWPOSGRAVITY;
            }
            else
            {
                speed = 4;
                GRAVITY = POSGRAVITY;
            }
            goingDown = false;
            Log.d("down","Gravity : "+ GRAVITY + " Speed : "+speed+" y : "+y+" goingDown : "+goingDown);
        }
    }

    public float getSpeed() {
        return speed;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return (int) (getRadius());
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getRadius() {
        return radius;
    }
}