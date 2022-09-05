package com.example.projectile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class level1 extends Activity {

    Point screenSize;
    private TestLevelClass first;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Intent intent = getIntent();
        boolean music = intent.getBooleanExtra("playMusic", true);

        System.out.println("MUSIC: "+music);
     //   boolean music=true;
        first = new TestLevelClass(this, size.x, size.y, music);
        setContentView(first);
    }
    @Override
    protected void onResume() {
        super.onResume();

        // More code here later in the chapter
        first.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // More code here later in the chapter
        first.pause();
    }
}