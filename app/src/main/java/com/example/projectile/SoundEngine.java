package com.example.projectile;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Build;

import java.io.IOException;

public class SoundEngine {

        // for playing sound effects
        private SoundPool mSP;
        private int throw_ID = -1;
        private int plank_ID = -1;
        private int net_ID = -1;
        private int floor_ID = -1;
        private int ring_ID = -1;
  //      private int music_ID= -1;



    SoundEngine(Context c){
        // Initialize the SoundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes =
                    new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes
                                    .CONTENT_TYPE_SONIFICATION)
                            .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            AssetManager assetManager = c.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("throw.ogg");
            throw_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("plank.ogg");
            plank_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("net.ogg");
            net_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("floor.ogg");
            floor_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("ring.ogg");
            ring_ID = mSP.load(descriptor, 0);

      /*      descriptor = assetManager.openFd("side_a.ogg");
            music_ID = mSP.load(descriptor, 0);
*/

        } catch (IOException e) {
            // Error
        }

    }

    private void playMusic() {
    }

    void playThrow(){
        mSP.play(throw_ID,1, 1, 0, 0, 1);
    }

    void playPlank(){
        mSP.play(plank_ID,1, 1, 0, 0, 1);
    }

    void playNet(){
        mSP.play(net_ID,1, 1, 0, 0, 1);
    }

    void playFloor(){
        mSP.play(floor_ID,1, 1, 0, 0, 1);
    }

    void playRing(){
        mSP.play(ring_ID,1, 1, 0, 0, 1);
    }

}