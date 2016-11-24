package com.lud.root.jetfighter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Arrays;

public class GameView extends SurfaceView implements Runnable{

    volatile boolean playing;
    private Thread gameThread = null;

    //Adding the player to this class
    private Player player;

    //Objects used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //Add stars list
    private ArrayList<Star> stars = new ArrayList<Star>();

    //Adding Enemies
    private Enemy[] enemies;  // only one enemy to decrease the difficulty
    private int enemyCount = 3; // Number Of Enemies

    private Boom boom;

    int screenX;
    private boolean isGameOver;

    int score;
    int highScore[] = new int[6];
    float distance[] = new float[enemyCount];

    public SharedPreferences sharedPreferences;
    static MediaPlayer gameOnSound;
    final MediaPlayer gameOverSound;

    //Context to be used in onTouchEvent on GameOver Screen , for transition from
    //GameOver Screen to Main Activity
    Context context;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        player = new Player(context, screenX, screenY);
        this.context = context;

        //initialize drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        int starNums = 50;
        for (int i = 0; i < starNums; i++){
            Star s = new Star(screenX,screenY);
            stars.add(s);
        }

        enemies = new Enemy[enemyCount];
        enemies[0] = new Enemy(context, screenX, screenY);  // This needs to be created so that the next enemies
                                                            // created can be kept apart and not overlapping
        for (int i=1; i<enemyCount; i++)
            enemies[i] = new Enemy(context, screenX, screenY, enemies[i-1].getY(),enemies[i-1].getRadius());

        boom = new Boom(context);
        this.screenX = screenX;
        isGameOver = false;

        score = 0;
        sharedPreferences = context.getSharedPreferences("Scores", Context.MODE_PRIVATE);

        //initialize the array of high scores
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);
        highScore[4] = sharedPreferences.getInt("score5",0);

        gameOnSound = MediaPlayer.create(context,R.raw.gameon);
        gameOverSound = MediaPlayer.create(context,R.raw.gameover);

        gameOnSound.setLooping(true);
        gameOnSound.start();

    }

    @Override
    public void run() {
        while (playing){
            update();
            draw();
            control();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                //stopping the booster when the screen is released
                player.stopSlowing();
                break;
            case MotionEvent.ACTION_DOWN:
                //starting the booster when the screen is released
                player.startSlowing();
                break;
        }
        if(isGameOver){
            if(event.getAction() == MotionEvent.ACTION_DOWN)
                context.startActivity(new Intent(context,MyActivity.class));
        }
        return true;
    }

    private void update() {
        //score based on the time passed
        score++;
        player.update();

        boom.setX(-250);
        boom.setY(-250);

        for(Star s : stars)
            s.update();

        // Below Code is for multiple enemy
        for(int i=0; i < enemyCount; i++){
            enemies[i].update((int) player.getSpeed() + 10 );
            distance[i] = (float) Math.sqrt((player.getX()-enemies[i].getX())*(player.getX()-enemies[i].getX()) + (player.getY()-enemies[i].getY())*(player.getY()-enemies[i].getY()));
            if(distance[i] < (30 + enemies[i].getRadius())){
                boom.setX(player.getX());
                boom.setY(player.getY());
                player.setX(-200);
                isGameOver = true;
                playing = false;
                gameOnSound.stop();
                gameOverSound.start();
                highScore[5] = score;
                Arrays.sort(highScore);
                Log.d("score","Score : "+score+"\nHighScores : "+highScore[0]+"\n"+highScore[1]+"\n"+highScore[2]+"\n"+highScore[3]+"\n"+highScore[4]+"\n"+highScore[5]);

                SharedPreferences pref;
                pref = context.getSharedPreferences("Scores", Context.MODE_PRIVATE);
                SharedPreferences.Editor e = pref.edit();
                for(int j=0;j<5;j++){
                    e.putInt("score"+(j+1),highScore[5-j]);
                    e.apply();
                   Log.d("score","score"+(j+1)+"\nHighScores : "+highScore[5-j]);
                }
            }
        }

    }

    private void draw() {
        if(surfaceHolder.getSurface().isValid()){
            //lock the canvas
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            //setting the paint color to white to draw the stars
            paint.setColor(Color.WHITE);

            //drawing all the stars
            for (Star s : stars){
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            paint.setTextSize(30);
            canvas.drawText("Score : "+score,100,50,paint);

            //The following line is for single enemy
            paint.setColor(Color.CYAN);
            for(int i = 0; i< enemyCount; i++)
                canvas.drawCircle(enemies[i].getX(), enemies[i].getY(), enemies[i].getRadius(), paint);

            canvas.drawBitmap(boom.getBitmap(),boom.getX(),boom.getY(),paint);
            paint.setColor(Color.RED);
            canvas.drawCircle(player.getX(), player.getY(), player.getRadius(), paint);
            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos = (int)((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()))/2);
                canvas.drawText("GAME OVER",canvas.getWidth()/2, yPos,paint);
            }

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(15);   //creating the frame rate to around 33fps
            canvas = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        //pausing the game , set the variable to false
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public static void stopMusic(){
        gameOnSound.stop();
        gameOnSound.release();
    }

    public static void pauseMusic(){
        gameOnSound.pause();
    }

    public static void resumeMusic(){
        gameOnSound.start();
    }



}
