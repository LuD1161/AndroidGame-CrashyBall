package com.lud.root.jetfighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Boom {

    private Bitmap bitmap;
    private int x,y;

    public Boom(Context context){
        //get the image from drawable
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.boom);

        //set the coordinate outside the screen so that it won't be
        //shown on the screen
        //it will be visible for only a fraction of a second
        x = -250;
        y = -250;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
