package com.example.projectile;

import android.graphics.RectF;

public class Projectile {

    // These are the member variables (fields)
    // They all have the m prefix
    // They are all private
    // because direct access is not required
    private RectF mRect;
    private float mXVelocity;
    private float mYVelocity;
    private float mProjectileWidth;
    private float mProjectileHeight;
    private float kValue;
    private float screenSizeX;
    private float screenSizeY;
    private boolean up=true;


    Projectile(int screenX, int screenY){

        screenSizeX=screenX;
        screenSizeY=screenY;

        // Make the ball square and 1% of screen width
        // of the screen width
        mProjectileWidth = screenX / 100;
        mProjectileHeight = screenX / 100;

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
        System.out.println("RIKTNING: "+direction);
        kValue=direction/2;
      //  kValue= 0.5F;
     //   startPosition(1, 2);

    }

    // Update the ball position.
    // Called each frame/loop
    void update(long fps){
        // Move the ball based upon the
        // horizontal (mXVelocity) and
        // vertical(mYVelocity) speed
        // and the current frame rate(fps)


    //Yvel=-0.2PlatsX^2
    float platsX = mRect.top;
  //  float platsX2=mRect.top;

if(up) {
    mYVelocity = (float) -Math.pow(kValue * platsX, 2) * platsX / 111500;
}
else if (!up) {
    mYVelocity = (float) Math.pow((kValue) * platsX, 2)/500;
}

    System.out.println(mRect.top + " Bollens plats     " + mRect.left);
    mXVelocity = screenSizeX / 20;

    // Move the top left corner
    mRect.left = mRect.left + (mXVelocity / fps);
    mRect.top = mRect.top + (mYVelocity / fps);

    // Match up the bottom right corner
    // based on the size of the ball
    mRect.right = mRect.left + mProjectileWidth;
    mRect.bottom = mRect.top + mProjectileHeight;

    System.out.println(kValue*400);

    if(platsX<kValue*900) {
        up = false;
    }
}

/*
if(up==false){
    //Yvel=-0.2PlatsX^2
    float platsX = mRect.top;

    mYVelocity = (float) Math.pow((kValue) * platsX, 2)/100000;

    System.out.println(mRect.top + " Bollens plats     " + mRect.left);
    System.out.println(mYVelocity + " HASTIGHHETEN        NÄSTA");
    mXVelocity = screenSizeX / 20;

    // Move the top left corner
    mRect.left = mRect.left + (mXVelocity / fps);
    mRect.top = mRect.top + (mYVelocity / fps);

    // Match up the bottom right corner
    // based on the size of the ball
    mRect.right = mRect.left + mProjectileWidth;
    mRect.bottom = mRect.top + mProjectileHeight;
} */



/*
    void update (long fps){


            float platsX = mRect.top;

if(up) {
    //    mYVelocity = (float) -Math.pow(kValue * platsX, 2)/1000;
    float mYVelocitySpar=mYVelocity;
    mYVelocity = (float) -Math.pow(0.05 * platsX, 2) + 2 * platsX + 2;


    System.out.println(mRect.top + " Bollens plats     " + mRect.left);
    mXVelocity = screenSizeX / 20;

    // Move the top left corner
    mRect.left = mRect.left + (mXVelocity / fps);
    mRect.top = mRect.top + (mYVelocity / fps);

    // Match up the bottom right corner
    // based on the size of the ball
    mRect.right = mRect.left + mProjectileWidth;
    mRect.bottom = mRect.top + mProjectileHeight;

    System.out.println(kValue * 400);
    if (mYVelocitySpar==mYVelocity){
        up=false;
    }
}
else if (!up){
    mYVelocity = (float) Math.pow(0.015 * platsX, 2);

    System.out.println(mRect.top + " Bollens plats     " + mRect.left);
    mXVelocity = screenSizeX / 20;

    // Move the top left corner
    mRect.left = mRect.left + (mXVelocity / fps);
    mRect.top = mRect.top + (mYVelocity / fps);

    // Match up the bottom right corner
    // based on the size of the ball
    mRect.right = mRect.left + mProjectileWidth;
    mRect.bottom = mRect.top + mProjectileHeight;

    System.out.println(kValue * 400);
}
    }
*/


    void startPosition(int x, int y){

        // Initialise the four points of
        // the rectangle which defines the ball
        mRect.left = x / 20;
        mRect.top = y-mProjectileHeight;
        mRect.right = x / 20 + mProjectileWidth;
    }

    float getHeightVelocity(){
        return mYVelocity;
    }

}