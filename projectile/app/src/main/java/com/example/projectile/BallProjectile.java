package com.example.projectile;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.RectF;

public class BallProjectile {

    //Member variables
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
    Point startPosition;


    BallProjectile(int screenX, int screenY, int width, int height){
        //Initiate screen size
        screenSizeX=screenX;
        screenSizeY=screenY;

        mProjectileWidth = width;
        mProjectileHeight = height;

        mRect = new RectF();
    }

    //Return a reference to mRect to game
    RectF getRect(){
        return mRect;
    }

    //Set projectile direction and power for new throw
    void setProjectileDirection(float direction){
        kValue=direction;
    }
    void setStrength(float pow){power=pow;}

    // Update the ball position.
    // Called each frame/loop
    void update(long fps){
        platsX = mRect.right-(screenSizeX/4)*power/700;

        //The path of the projectile
        mYVelocity = (float) -Math.pow(0.002*platsX, 2)+(kValue*platsX);
        mXVelocity = screenSizeX /10+(power/4);

        if(kValue!=0) {

            //Movement of the ball for each frame
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

        // Initialise the four points, start position
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