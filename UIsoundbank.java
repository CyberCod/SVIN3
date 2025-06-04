package com.smiths;
import com.smiths.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
//sound effect manager
public class UIsoundbank {

	static SoundPool sounds;
	private static int attack1;
	
	private static int swoosh1;
	private static int healthup;
	
	
	private static int swishkill;
	
	
	public UIsoundbank() {
			// TODO Auto-generated constructor stub
		
	}
	
	

	public void loadSound(Context context) {
	    //sound = true; // should there be sound?
	    sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    // three ref. to the sounds I need in the application
	    attack1 = sounds.load(context, R.raw.attack1, 1);
	
	    swoosh1 = sounds.load(context, R.raw.swoosh1, 1);
	    
	    
	    swishkill=sounds.load(context, R.raw.swishkill, 1);
	    healthup = sounds.load(context, R.raw.healthup, 1);
	}
	

	
	

	public void playHit() {
		
		
			sounds.play(attack1, 1, 1, 1, 0, 1);
		
	}

	
	public void playPractice() {
		
		
		sounds.play(healthup, 1, 1, 1, 0, 1);
	
}
	
	public void playSwoosh() {
		sounds.play(swoosh1, 1, 1, 1, 0, 1);
		
	   
	}
	
			public void playSwishKill() 
	{
	
	sounds.play(swishkill, 1, 1, 1, 0, 1);
		
	}
	
	
}