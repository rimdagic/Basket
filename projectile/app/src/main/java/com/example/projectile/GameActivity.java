package com.example.projectile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity {

    Point screenSize;
    private GameEngine first;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Make the activity fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Get the screensize to variable point size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //Check if user want music or not
        Intent intent = getIntent();
        boolean music = intent.getBooleanExtra("playMusic", true);

        //Create a new TestLevelClass named first
        first = new GameEngine(this, size.x, size.y, music);
        setContentView(first);
    }

    //Lifecycle methods
    @Override
    protected void onResume() {
        super.onResume();
        first.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        first.pause();
    }
}