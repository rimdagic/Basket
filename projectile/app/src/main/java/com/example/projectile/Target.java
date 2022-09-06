package com.example.projectile;


import android.graphics.Point;
import android.graphics.RectF;

public class Target {
    private float screenSizeX;
    private float screenSizeY;
    private Point basketBottomLeft;
    private RectF successfulPoint;
    private RectF board;
    private RectF ringBounce;
    private int currentLevel;

    //Constructor
    public Target(int screenX, int screenY){
        screenSizeX=screenX;
        screenSizeY=screenY;

        board=new RectF();
        basketBottomLeft =new Point();
        ringBounce = new RectF();
        successfulPoint = new RectF();

        //Initial position of basket case, level one.
        basketBottomLeft.x = (int)screenSizeX / 3;
        basketBottomLeft.y=(int)(screenSizeY/5)*4;

        setHitBoxes();
    }

    private void setHitBoxes(){

        //Hitbox for basket board (will result in point)
        board.left= basketBottomLeft.x+100;
        board.top= basketBottomLeft.y-370;
        board.right=board.left+30;
        board.bottom=board.top+80;

        //Hitbox side of basket case ring (will not result in point)
        ringBounce.top= basketBottomLeft.y-305;
        ringBounce.left= basketBottomLeft.x;
        ringBounce.bottom=ringBounce.top+20;
        ringBounce.right= ringBounce.left+10;

        //Hitbox success (will result in point)
        successfulPoint.top= basketBottomLeft.y-305;
        successfulPoint.left= basketBottomLeft.x+15;
        successfulPoint.bottom=ringBounce.top+20;
        successfulPoint.right= ringBounce.left+100;
    }


    Point getBasket(){
        return basketBottomLeft;
    }
    RectF getSuccessfulPoint(){
        return successfulPoint;
    }
    RectF getBoard(){
        return board;
    }
    RectF getRingBounce(){
        return ringBounce;
    }

    //Move basket depending on what level
    public void setCurrentLevel(int level){
        currentLevel=level;
        switch (currentLevel) {
            case 1:
                basketBottomLeft.x = (int) screenSizeX / 3;
                break;
            case 2:
                basketBottomLeft.x = (int) screenSizeX / 2;
                break;
            case 3:
                basketBottomLeft.x = (int) screenSizeX / 3 * 2;
                break;
            case 4:
                basketBottomLeft.x = (int) screenSizeX / 6 * 5;
                break;
        }
                //Move hit-boxes to match new basket-position
                setHitBoxes();
        }
    }