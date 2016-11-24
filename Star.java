package com.lud.root.jetfighter;

import java.util.Random;

public class Star {
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    public Star(int screenX, int screenY){
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        Random generator = new Random();
        speed = generator.nextInt(10);

        //generate random coordinate but keeping them inside the screen
        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);
    }

    public void update(){
        //To animate the stars on the left side
        //Used here is the player's speed
        x -= 10;
        x -=speed;
        if(x < 0){
            //Again start the stars from the right edge
            //Thus creating an infinite background effect
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            speed = generator.nextInt(15);
        }
    }

    public float getStarWidth(){
        //Randomising the star width , for aesthetics
        float minX = 1.0f;
        float maxX = 4.0f;
        Random rand = new Random();
        return rand.nextFloat() * (maxX - minX) + minX;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
