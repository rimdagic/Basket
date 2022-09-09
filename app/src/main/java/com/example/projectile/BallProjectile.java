package com.example.projectile;

import android.graphics.Point;
import android.graphics.RectF;

public class BallProjectile {

    //Member variables
    final RectF mRect;
    private final float mProjectileWidth;
    private final float mProjectileHeight;
    private float kValue=0;
    private final float screenSizeX;
    private float power;
    Point startPosition;


    BallProjectile(int screenX, int width, int height){
        //Initiate screen size
        screenSizeX=screenX;

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
        float positionX = mRect.right - (screenSizeX / 4) * power / 700;

        //The path of the projectile
        float mYVelocity = (float) -Math.pow(0.002 * positionX, 2) + (kValue * positionX);
        float mXVelocity = screenSizeX / 10 + (power / 4);

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

    void startPosition(){

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

    public void setStart(Point start){
        startPosition=start;
    }
}