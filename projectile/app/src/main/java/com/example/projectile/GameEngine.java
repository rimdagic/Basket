package com.example.projectile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameEngine extends SurfaceView implements Runnable{

    //Create variables needed use for user input aim
    private float aimOneX;
    private float aimOneY;
    private float aimTwoX;
    private float aimTwoY;
    private float kValue;
    private float power;

    // These objects are needed to do the drawing
    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;

    //Variable to store frames per second in the game loop
    private long mFPS;

    // The number of milliseconds in a second
    private final int MILLIS_IN_SECOND = 1000;

    // Holds the resolution of the screen
    private int mScreenX;
    private int mScreenY;


    //Create the basketball from class Projectile_test
    private BallProjectile mProjectile;

    //Integers and booleans needed to count points, throws and levels
    private int mScore;
    private int mBallsLeft;
    private int mCurrentLevel;
    private int neededPointsCurrentLevel;
    private int totalScore;
    private boolean addLevel;
    private boolean successScreen;
    private boolean failScreen;
    private boolean music=true;

    // Here is the Thread and control variables
    private Thread mGameThread = null;
    private volatile boolean mPlaying;
    private boolean mPaused = false;
    private boolean input=true;

    //Initiate player, target, sounds and music
    private Player mPlayer;
    private Target mBasket;
    private SoundEngine mSoundEngine;
    MediaPlayer mediaPlayer;

    //Create bitmaps to draw the graphics
    Bitmap projectileBitmap;
    Bitmap playerBitmap;
    Bitmap player2Bitmap;
    Bitmap basketBitmap;
    Bitmap defenderBitmap;

    //Constructor
    public GameEngine(Context context, int x, int y, boolean passMusic) {

        //Parent class constructor of SurfaceView
        super(context);

        mSoundEngine = new SoundEngine(context);
        mScreenX = x;
        mScreenY = y;

        mOurHolder = getHolder();
        mPaint = new Paint();

        basketBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.korg);
        projectileBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.boll);
        playerBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.player);
        player2Bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.player2);
        defenderBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.defender);

        // Initialize game objects
        mProjectile = new BallProjectile(mScreenX, mScreenY, projectileBitmap.getWidth(), projectileBitmap.getHeight());
        mPlayer = new Player(mScreenX, mScreenY);
        mBasket = new Target(mScreenX, mScreenY);
        mProjectile.setStart(mPlayer.playerLocation);

        //Get info if user choose music and assign to boolean music
        music=passMusic;

        //Play music if boolean music is true
        if(music) {
            mediaPlayer = MediaPlayer.create(context, R.raw.side_a);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }

        startNewGame();
    }

    //New game method
    private void startNewGame() {

        // Put the ball back to the starting position
        mProjectile.startPosition(mScreenX, mScreenY);

        // Reset the score and the player's chances
        mScore = 0;
        mBallsLeft = 10;
        mCurrentLevel=1;
        neededPointsCurrentLevel=3;
        successScreen=false;
        failScreen=false;
        addLevel=false;
        totalScore=0;
        mBasket.setCurrentLevel(1);
    }

    //Game loop
    @Override
    public void run() {

        while (mPlaying) {

            //Time at the start of the loop
            long frameStartTime = System.currentTimeMillis();

            // Provided the game isn't paused
            // call the update method
            if(!mPaused){
                    mProjectile.update(mFPS);

                //Draw background, player, ball and basket case.
                draw();

                //Check if ball hit wall or basket
                detectCollisions();
            }

        //Time at end of gameloop
        long timeThisFrame =
                System.currentTimeMillis() - frameStartTime;

            if (timeThisFrame > 0) {
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }

    //Called from lifecycle method
    public void resume() {

        //Start music
        if (music){
            mediaPlayer.start();
        }
        mPlaying = true;

        // Initialize the instance of Thread
        mGameThread = new Thread(this);

        // Start the thread
        mGameThread.start();
    }


    //Called from lifecycle method
    public void pause() {

        //Pause music
        if(music) {
            mediaPlayer.pause();
        }

        // Set mPlaying to false
        mPlaying = false;
        try {

            // Stop the thread
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    private void draw() {

            //Draw the game objects
            if (mOurHolder.getSurface().isValid()) {

                //Lock the canvas (graphics memory) ready to draw
                mCanvas = mOurHolder.lockCanvas();

                        //Fill the screen with a solid color
                        if (mCurrentLevel<4){
                        mCanvas.drawColor(Color.argb
                                (255, 0, 102, 135));
                        }
                        else {
                            mCanvas.drawColor(Color.argb
                                    (255, 51, 77, 77));
                            mCanvas.drawBitmap(defenderBitmap, mScreenX/2, (mScreenY/4)*2, mPaint);
                        }

                //Choose a color to paint with
                mPaint.setColor(Color.argb
                        (255, 255, 255, 255));

                //Draw the projectile, basket, player
                mCanvas.drawBitmap(basketBitmap,mBasket.getBasket().x,mBasket.getBasket().y-basketBitmap.getHeight(), mPaint);
                mCanvas.drawBitmap(projectileBitmap,mProjectile.getRect().left,mProjectile.getRect().top,mPaint);

                //If the player holds the ball, draw player with hands down.
                if (input==true) {
                    mCanvas.drawBitmap(playerBitmap, mPlayer.playerLocation.x, mPlayer.playerLocation.y - playerBitmap.getHeight(), mPaint);
                }

                //If the ball is currently in the air, draw player with hands up.
                if (input!=true){
                    mCanvas.drawBitmap(player2Bitmap, mPlayer.playerLocation.x, mPlayer.playerLocation.y - playerBitmap.getHeight(), mPaint);
                }

                //Draw text
                mPaint.setTextSize(mScreenY/15);
                mCanvas.drawText(" Level: " + mCurrentLevel +
                                " Score: " + mScore +"/"+neededPointsCurrentLevel+
                                " Throws: " + mBallsLeft +
                                " Total: "+totalScore,
                        mScreenX/20, mScreenY/10, mPaint);

                // Display the drawing on screen
                mOurHolder.unlockCanvasAndPost(mCanvas);
            }
    }


    private void detectCollisions(){

        //Detect if ball hits the wall or the floor
        if (mProjectile.getRect().left>mScreenX||mProjectile.getRect().bottom>mScreenY){
            mBallsLeft--;
            mProjectile.stopProjectile();
            mProjectile.startPosition(mScreenX, mScreenY);
            input=true;
            mSoundEngine.playFloor();
        }

        //Detect if ball hits plank on the basket
        else if (RectF.intersects(mProjectile.getRect(), mBasket.getBoard())){
            System.out.println("POÄNG!!!!!!!");
            mScore++;
            mProjectile.stopProjectile();
            mProjectile.startPosition(mScreenX, mScreenY);
            input=true;
            kValue=0;
            power=0;
            mSoundEngine.playPlank();
        }

        //Detect if ball hits directly into the basket
        else if (RectF.intersects(mProjectile.getRect(), mBasket.getSuccessfulPoint())){
            System.out.println("POÄNG!!!!!!!");
            mScore++;
            mProjectile.stopProjectile();
            mProjectile.startPosition(mScreenX, mScreenY);
            input=true;
            mSoundEngine.playNet();
        }

        //Detect if ball hits the ring of the basket horizontally
        else if (RectF.intersects(mProjectile.getRect(), mBasket.getRingBounce())){
            mBallsLeft--;
            mProjectile.stopProjectile();
            mProjectile.startPosition(mScreenX, mScreenY);
            input=true;
            mSoundEngine.playRing();
        }

        //Enough points get to the next level
        else if (mScore==neededPointsCurrentLevel){
            if (mCurrentLevel<4) {
                addLevel = true;
                nextLevel();
            }

            //If player succeeded final level
            else if (mCurrentLevel==4){
                totalScore=totalScore+mBallsLeft+mScore;
                successScreen=true;
                mPaused=true;
                mCanvas = mOurHolder.lockCanvas();

                mPaint.setColor(Color.argb
                        (255, 0, 128, 64));
                mCanvas.drawRect(mScreenX/20, mScreenY/20, (mScreenX/10)*8, (mScreenY/20)*8,mPaint );

                mPaint.setColor(Color.argb
                        (255, 0, 0, 0));
                mPaint.setTextSize(mScreenY/10);
                mCanvas.drawText(" You are the best! " + totalScore +" points!",
                        mScreenX/21, mScreenY/5, mPaint);

                mPaint.setTextSize(mScreenY/20);
                mCanvas.drawText(" Touch the screen to start again",
                        mScreenX/20, mScreenY/3, mPaint);
                mOurHolder.unlockCanvasAndPost(mCanvas);

                System.out.println("GRATTIS KLARAT SPELET! DIN POÄNG BLEV: "+totalScore);
            }
        }

        //If player has no balls left, game over method
        else if (mBallsLeft<1){
            gameOver();
        }
    }

    private void nextLevel(){
        if (addLevel==true) {
            mCurrentLevel++;
           neededPointsCurrentLevel=neededPointsCurrentLevel+2;
            addLevel=false;
            totalScore=totalScore+mScore+mBallsLeft;
            mScore=0;
            mBallsLeft=10;
            mBasket.setCurrentLevel(mCurrentLevel);
        }
    }

    private void gameOver(){
        System.out.println("Game over");
        mPaused=true;
        failScreen=true;

        mCanvas = mOurHolder.lockCanvas();
        mPaint.setColor(Color.argb
                (255, 102, 0, 0));
        mCanvas.drawRect(mScreenX/20, mScreenY/20, (mScreenX/10)*8, (mScreenY/20)*8,mPaint );

        mPaint.setColor(Color.argb
                (255, 255, 255, 255));

        mPaint.setTextSize(mScreenY/10);
        mCanvas.drawText(" Sorry, you failed! " + totalScore +" points!",
                mScreenX/20, mScreenY/5, mPaint);

        mPaint.setTextSize(mScreenY/20);
        mCanvas.drawText(" Touch the screen to try again",
                mScreenX/20, mScreenY/3, mPaint);
        mOurHolder.unlockCanvasAndPost(mCanvas);
    }

    // Handle all the screen touches
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        //If the player finished the game, touch screen to restart the game
        if (successScreen&&MotionEvent.ACTION_DOWN==0){
            startNewGame();
            mPaused=false;
        }

        //If the player failed, touch the screen to restart
        if (failScreen&&MotionEvent.ACTION_DOWN==0){
            mPaused=false;
            startNewGame();
        }

        //Input is open to take a shot
        if (input) {
            switch (motionEvent.getAction() &
                    MotionEvent.ACTION_MASK) {

                // The player has put their finger on the screen
                case MotionEvent.ACTION_DOWN:

                    // Save the coordinates, where the player touched the screen
                    aimOneX = (int) motionEvent.getX();
                    aimOneY = (int) motionEvent.getY();
                    break;

                // The player lifted its finger from the screen
                case MotionEvent.ACTION_UP:

                    //Save the coordinates where the player stopped touching the screen
                    aimTwoX = motionEvent.getX();
                    aimTwoY = motionEvent.getY();

                    //Decide the angle between the two points in kvalue variable.
                    float deltaX = aimOneX - aimTwoX;
                    float deltaY = aimTwoY - aimOneY;
                    kValue = deltaY / deltaX;

                    //Decide the distance between the two points in power variable
                    deltaX=(float)Math.pow(deltaX,2);
                    deltaY=(float)Math.pow(deltaY,2);
                    float power= (float) Math.sqrt(deltaX+deltaY);

                    //Throw the ball if the angle is between 0.2 and 3.5
                    if (kValue<3.5&&kValue>0.2) {
                        mSoundEngine.playThrow();
                        mProjectile.setProjectileDirection(kValue);
                        mProjectile.setStrength(power);

                        //Lock the input until throw is finished
                        input = false;
                        break;
                    }
            }
        }
            return true;

    }
}