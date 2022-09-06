package com.example.projectile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;

public class Projectile_test {

    // These are the member variables (fields)
    // They all have the m prefix
    // They are all private
    // because direct access is not required
    private RectF mRect;
    private float mXVelocity;
    private float mYVelocity;
    private float mProjectileWidth;
    private float mProjectileHeight;
    private float kValue=0;
    private float screenSizeX;
    private float screenSizeY;
    private boolean up=true;
    private float platsX;
    private boolean boolPlatsX=false;
    private float power;
    Bitmap projectileBitmap;
    Point startPosition;


    Projectile_test(int screenX, int screenY, int width, int height){
        //Bollens storlek baserat på skärmens storlek
        screenSizeX=screenX;
        screenSizeY=screenY;

        // Make the ball square and 1% of screen width
        // of the screen width
        mProjectileWidth = width;
                //screenX / 100;
        mProjectileHeight = height;
                //screenX / 100;

        // Initialize the RectF with 0, 0, 0, 0
        // We do it here because we only want to
        // do it once.
        // We will initialize the detail
        // at the start of each game
        mRect = new RectF();


    }


    // Return a reference to mRect to game (getter)
    RectF getRect(){
        return mRect;
    }


    void setProjectileDirection(float direction){
        kValue=direction;
    }
    void setStrength(float pow){power=pow;}

    // Update the ball position.
    // Called each frame/loop
    void update(long fps){
        // Move the ball based upon the
        // horizontal (mXVelocity) and
        // vertical(mYVelocity) speed
        // and the current frame rate(fps)



        platsX = mRect.right-(screenSizeX/4)*power/700;

        //The path of the projectile
        //        mYVelocity = (float) -Math.pow(0.002*platsX, 2)+(kValue*platsX);
        mYVelocity = (float) -Math.pow(0.002*platsX, 2)+(kValue*platsX);

        //System.out.println(mYVelocity);
        //   System.out.println(mRect.top + " Bollens plats     " + mRect.left);
        mXVelocity = screenSizeX /10+(power/4);


        if(kValue!=0) {
            // Move the top left corner
            mRect.left = mRect.left + (mXVelocity / fps);
            mRect.top = mRect.top + (mYVelocity / fps);

            // Match up the bottom right corner
            // based on the size of the ball
            mRect.right = mRect.left + mProjectileWidth;
            mRect.bottom = mRect.top + mProjectileHeight;
        }
        else{
            mRect.top=startPosition.y-220;
            mRect.left=startPosition.x+75;
            mRect.bottom=mRect.top+ mProjectileHeight;
            mRect.right=mRect.left+mProjectileWidth;
        }
    }

    void startPosition(int x, int y){

        // Initialise the four points of
        // the rectangle which defines the ball
    //    mRect.left = x/20;
     //   mRect.top = y-mProjectileHeight;
     //   mRect.right = x/20 + mProjectileWidth;

        mRect.top=startPosition.y-220;
        mRect.left=startPosition.x+75;
        mRect.bottom=mRect.top+ mProjectileHeight;
        mRect.right=mRect.left+mProjectileWidth;
    }

    void stopProjectile(){
        power=0;
        kValue = 0;
    }

    float getHeightVelocity(){
        return mYVelocity;
    }

    public void setStart(Point start){
        startPosition=start;
    }
}