package com.example.projectile;

import android.graphics.Point;
import android.graphics.RectF;

public class Player {

    Point playerLocation= new Point();

    //COUNSTRUCTOR
    public Player(int X, int Y){

        playerLocation.x=X/20;
        playerLocation.y= (Y/5)*4;
    }

    public Point getLocation(){
        return playerLocation;
    }
}