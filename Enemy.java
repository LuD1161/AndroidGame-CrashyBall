package com.lud.root.jetfighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Enemy {

    private int x;
    private int y;
    private int speed = -1;
    private int radius = 20;

    private int maxX, minX;
    private int maxY, minY;

    //Create a rect object to detect collision
    private Rect detectCollision;

    public Enemy(Context context, int screenX, int screenY){
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        // Randomly generating enemy position
        Random generator = new Random();
        speed = 25;
        radius = 35;
        x = screenX;
        y = generator.nextInt(maxY) + 10 - radius;
    }

    public Enemy(Context context, int screenX, int screenY, int prevY, int prevRad){
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        // Randomly generating enemy position
        Random generator = new Random();
        speed = 25;
        radius = 35;
        x = screenX;
        do{
            y = generator.nextInt(maxY) + 10 - radius;
        }while ((y - prevY) < (prevRad + radius + 60));
    }

    public void update(int playerSpeed){
        // As the enemy moves from right to left
        x -= playerSpeed;
        x -= speed;
        Random generator = new Random();
        if(x < minX - this.getRadius()){
            speed = generator.nextInt(10) + 10;
            radius = generator.nextInt(20) + 30;
            x = maxX;
            y = generator.nextInt(maxY) - radius;
        }
    }

    //This setter is used for changing the x coordinate after collision
    public void setX(int x) {
        this.x = x;
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public int getSpeed() {
        return speed;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getRadius() {
        return radius;
    }
}
