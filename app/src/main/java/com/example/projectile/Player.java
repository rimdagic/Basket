package com.example.projectile;

import android.graphics.Point;

public class Player {

    Point playerLocation= new Point();

    //COUNSTRUCTOR
    public Player(int X, int Y){

        //Set player location on the screen
        playerLocation.x=X/20;
        playerLocation.y= (Y/5)*4;
    }

}