package com.example.projectile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity {

    //Create a checkbox
    CheckBox chbMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make the app fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Show main menu activity xml design
        setContentView(R.layout.activity_main);

        //Get buttons and checkbox from design
        Button start = (Button) findViewById(R.id.btnStart);
        Button how = (Button) findViewById(R.id.btnHow);
        chbMusic = (CheckBox) findViewById(R.id.chbMusic);

        //Check if user clicks the start button
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //User clicked the button, run method toStart()
                toStart();
            }
        });


        //Check if user clicks the "How to play"-button
        how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //User clicked the button, run method toStart()
                toHowTo();
            }
        });
    }

    private void toStart(){
        //Create an intent with the value of next activity
        Intent intent = new Intent (this, GameActivity.class);

        //Look if the checkbox for music checked, and send information to next activity
        intent.putExtra("playMusic", chbMusic.isChecked());

        //Start the next activity to play the game
        startActivity(intent);
    }

    private void toHowTo(){
        //Create an intent with the value of next activity
        Intent intent = new Intent (this, HowToPlay.class);

        //Start the next activity to play the game
        startActivity(intent);
    }
}