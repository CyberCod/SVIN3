package com.smiths;

import com.smiths.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


//Our current audio format is too fat wasting memory

public class BackgroundSoundService extends Service {
    //private static final String TAG = null;
    MediaPlayer player;
    public IBinder onBind(Intent arg0) {
		return null;

        
    }
    @Override
    public void onCreate() {
        super.onCreate();


        player = MediaPlayer.create(this, R.raw.sakurakoto2);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);

    }
    public int onStartCommand(Intent intent, int flags, int startId) {


        player.start();

        return 1;
    }

    public void onStart(Intent intent, int startId) {
        // TODO
    	player.start();
    	


    }
    public IBinder onUnBind(Intent arg0) {
        // TODO Auto-generated method stub

        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {

        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {
    	player.stop();

    }
}
