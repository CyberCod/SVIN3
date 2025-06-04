package com.smiths;
import com.smiths.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
//sound effect manager
public class Soundbank {

	static SoundPool sounds;
	private static int attack1;
	private static int coin;
	
	private static int swoosh1;

	private static int yell1;
	private static int yell2;
	private static int yell3;
	private static int swordcollision;
	private static int dualswordcombo;
	private static int spinjump;
	private static int gong;
	
	
	private static int shurikenthrow;
	
	private static int shurikenhit;
	private static int hurt1;
	
	
	private static int levelupsound;
	private static int angelsound1;
	private static int angelsound2;
	private static int angelsound3;
	private static int timedown;
	private static int timeup;
	private static int smokebomb;
	private static int extraninja;
	private static int smokebombpickup;
	private static int swish;
	private static int swishkill;
	private static int gold;
	private static int shurikenpickup; 
	private static int smallgong;
	private static int groundpound;
	private static int healthup;
	private static int shurikencollision;         

	public Soundbank() {
			// TODO Auto-generated constructor stub
		
	}
	
	

	public void loadSound(Context context) {
	    //sound = true; // should there be sound?
	    sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    // three ref. to the sounds I need in the application
	    attack1 = sounds.load(context, R.raw.attack1, 1);
	    coin = sounds.load(context, R.raw.coin, 1);
	    swoosh1 = sounds.load(context, R.raw.swoosh1, 1);
	    dualswordcombo = sounds.load(context, R.raw.dualswordcombo, 1);
	    spinjump = sounds.load(context, R.raw.spinjump, 1);
	    
	    timedown = sounds.load(context, R.raw.timedown, 1);
	    timeup = sounds.load(context, R.raw.timeup, 1);
	    gong = sounds.load(context, R.raw.gong, 1);
	    yell1 = sounds.load(context, R.raw.yell1, 1);
	    yell2 = sounds.load(context, R.raw.yell2, 1);
	    yell3 = sounds.load(context, R.raw.yell3, 1);
	    shurikenthrow = sounds.load(context, R.raw.shurikenthrow, 1);
	    shurikenhit = sounds.load(context, R.raw.shurikenhit, 1);
	    shurikencollision = sounds.load(context,R.raw.shurikencollision,1);
	    hurt1 = sounds.load(context, R.raw.hurt1, 1);
	    swish = sounds.load(context, R.raw.swish, 1);
	    swishkill=sounds.load(context, R.raw.swishkill, 1);
	    swordcollision=sounds.load(context, R.raw.swordcollide, 1);
	    
	
	    angelsound1 = sounds.load(context, R.raw.angelsound1, 1);
	    angelsound2 = sounds.load(context, R.raw.angelsound2, 1);
	    angelsound3 = sounds.load(context, R.raw.angelsound3, 1);
	    levelupsound = sounds.load(context, R.raw.levelupsound, 1);
	    
	    smokebomb = sounds.load(context, R.raw.smokebomb, 1);
	    extraninja = sounds.load(context, R.raw.extraninja, 1);
	    smokebombpickup = sounds.load(context, R.raw.smokebombpickup, 1);
	    gold = sounds.load(context, R.raw.gold, 1);
	    shurikenpickup = sounds.load(context, R.raw.shurikenpickup, 1);
	    smallgong = sounds.load(context, R.raw.smallgong, 1);
	    groundpound = sounds.load(context, R.raw.groundpound, 1);
	    healthup = sounds.load(context, R.raw.healthup, 1);
	 
	    
	    
	    
	    
	    
	    
	    
	    // the music that is played at the beginning and when there is only 10 seconds left in a game
	    //music = MediaPlayer.create(context, R.raw.sakurakoto);
	}
	

	
	
	public void playHurt(boolean sound, boolean slowed) {if (sound == false) {return;}
		double random = Math.random();
		double pitch = .7 + (random*.5);
		if(slowed == true){pitch = pitch -.5;}
			sounds.play(hurt1, 1, 1, 1, 0, (float) pitch);
		
	}

	public void playTimeDOWN(boolean sound) {if (sound == false) {return;}
	double random = Math.random();
	double pitch = .7 + (random*.5);
	
		sounds.play(timedown, 1, 1, 1, 0, (float) pitch);
	
	}
	
	public void playTimeUP(boolean sound) {if (sound == false) {return;}
	double random = Math.random();
	double pitch = .7 + (random*.5);
	
		sounds.play(timeup, 1, 1, 1, 0, (float) pitch);
	
	}


	public void playHit(boolean sound, boolean slowed) {if (sound == false) {return;}
		double random = Math.random();
		double pitch = .7 + (random*.5);
		if(slowed == true){pitch = pitch -.5;}
		
			sounds.play(attack1, 1, 1, 1, 0, (float) pitch);
		
	}
	
	
	public void playCoin(boolean sound, boolean slowed) {if (sound == false) {return;}
	double random = Math.random();
	double pitch = .8 + (random*.3);
	if(slowed == true){pitch = pitch -.5;}
	
		sounds.play(coin, 1, 1, 1, 0, (float) pitch);
	
}


	public void playSpinjump(boolean sound, boolean slowed) {if (sound == false) {return;}
		double random = Math.random();
		double pitch = .7 + (random*.5);
		if(slowed == true){pitch = pitch -.5;}
		
			sounds.play(spinjump, 1, 1, 1, 0, (float) pitch);
		
	}


	public void playDualSwordCombo(boolean sound, boolean slowed) {if (sound == false) {return;}
		double random = Math.random();
		double pitch = .7 + (random*.5);
		if(slowed == true){pitch = pitch -.5;}
		
			sounds.play(dualswordcombo, 1, 1, 1, 0, (float) pitch);
		
	}

	
	
	
	public void playShurikenCollision(boolean sound, boolean slowed) {if (sound == false) {return;}
	double random = Math.random();
	double pitch = .7 + (random*.5);
	if(slowed == true){pitch = pitch -.5;}
	
		sounds.play(shurikencollision, 1, 1, 1, 0, (float) pitch);
	
}
	
	public void playYell(boolean sound, boolean slowed) {if ((sound == false)||(slowed == true)) {return;}
		int random = (int)(Math.random()*3);
		if(random == 0)
		{
			sounds.play(yell1, 1, 1, 1, 0, 1);
		}
		if(random == 1)
		{
			sounds.play(yell2, 1, 1, 1, 0, 1);
		}
		if(random == 2)
		{
			sounds.play(yell3, 1, 1, 1, 0, 1);
		}
		if(random == 3)
		{
			sounds.play(yell3, 1, 1, 1, 0, 1);
		}
		
		
	    //if (sound == false) return; // if sound is turned off no need to continue
	   
	}
	
	public void playSwoosh(boolean sound, boolean slowed) {if (sound == false) {return;}
	int random = (int)(Math.random()*3);//this random is never used?
	double pitch = 1;
	if(slowed == true){pitch = pitch -.85;}
		sounds.play(swoosh1, 1, 1, 1, 0, (float) pitch);
		
	    //if (sound == false) return; // if sound is turned off no need to continue
	   
	}
	
	
	
	public void playAngel(boolean sound, boolean slowed) {if (sound == false) {return;}
		int random = (int)(Math.random()*3);
		double pitch = 1;
		if(slowed == true){pitch = .5;}
		if(random == 0)
		{
			sounds.play(angelsound1, 1, 1, 1, 0, (float) pitch);
		}
		if(random == 1)
		{
			sounds.play(angelsound2, 1, 1, 1, 0, (float) pitch);
		}
		if(random == 2)
		{
			sounds.play(angelsound3, 1, 1, 1, 0, (float) pitch);
		}
		if(random == 3)
		{
			sounds.play(angelsound3, 1, 1, 1, 0, (float) pitch);
		}
		
		
	}


	public void playGong(boolean sound) {if (sound == false) {return;}
		sounds.play(gong, 1, 1, 1, 0, 1);
	}
	
	public void playSmallGong(boolean sound) {if (sound == false) {return;}
	sounds.play(smallgong, 1, 1, 1, 0, 1);
}
	
	
	public void playLevelup(boolean sound) {if (sound == false) {return;}
	sounds.play(levelupsound, 1, 1, 1, 0, 1);
	}
	
	public void playShurikenthrow(boolean sound) {if (sound == false) {return;}
	double random = Math.random();
	double pitch = .7 + (random*.5);
	sounds.play(shurikenthrow, 1, 1, 1, 0, (float)pitch);
	}
	
	public void playShurikenhit(boolean sound, boolean slowed) {if (sound == false) {return;}
	double random = Math.random();
	double pitch = .7 + (random*.5);
	if(slowed == true){pitch = pitch - .5;}
	
	sounds.play(shurikenhit, 1, 1, 1, 0, (float)pitch);
	}



	public void playExplosion(boolean sound, boolean slowed) {if ((sound == false)||(slowed == true)) {return;}
		sounds.play(smokebomb, 1, 1, 1, 0, 1);
		
	}



	public void playNewNinja(boolean sound, boolean slowed) {if ((sound == false)||(slowed == true)) {return;}
	float pitch = 1;
	sounds.play(extraninja, 1, 1, 1, 0, pitch);
		
	}



	public void playShurikengrab(boolean sound, boolean slowed) {if ((sound == false)||(slowed == true)) {return;}
		// TODO Auto-generated method stub
	sounds.play(shurikenpickup, 1, 1, 1, 0, 1);
	}



	public void playHealthup(boolean sound, boolean slowed) {if ((sound == false)||(slowed == true)) {return;}
	sounds.play(healthup, 1, 1, 1, 0, 1);
		
	}



	public void playGroundpound(boolean sound, boolean slowed) {if ((sound == false)||(slowed == true)) {return;}
		// TODO Auto-generated method stub
	sounds.play(groundpound, 1, 1, 1, 0, 1);
	}



	public void playLevelComplete(boolean sound) {if (sound == false) {return;}
	sounds.play(gong, 1, 1, 1, 0, 1);
		
	}

	public void playGold(boolean sound) {if (sound == false) {return;}
	// TODO Auto-generated method stub
	sounds.play(gold, 1, 1, 1, 0, 1);
	}

	public void playSmokeBombPickup(boolean sound, boolean slowed) {if ((sound == false)||(slowed == true)) {return;}
		// TODO Auto-generated method stub
	sounds.play(smokebombpickup, 1, 1, 1, 0, 1);
	}



	public void playSwish(boolean sound, boolean slowed) {if (sound == false) {return;}
	double random = Math.random();
	double pitch = .7 + (random*.5);
	if(slowed == true){pitch = pitch - .5;}
		sounds.play(swish, 1, 1, 1, 0, (float) pitch);
	
	}
	
	public void playSwishKill(boolean sound, boolean slowed) {if (sound == false) {return;}
	double random = Math.random();
	double pitch = .7 + (random*.5);
	if(slowed == true){pitch = pitch - .5;}
	
	sounds.play(swishkill, 1, 1, 1, 0, (float) pitch);
		
	}



	public void playSwordCollision(boolean sound, boolean slowed) {if (sound == false) {return;}
	double random = Math.random();
	double pitch = .7 + (random*.5);
	if(slowed == true){pitch = pitch - .5;}
	
	sounds.play(swordcollision, 1, 1, 1, 0, (float) pitch);
	}



	public void playHadoken(boolean sFX) {
		// TODO Auto-generated method stub
		sounds.play(yell1, 1, 1, 1, 0, 1);
		sounds.play(smokebomb, 1, 1, 1, 0, 1);
	}



	public void playDSCombo1(boolean sFX, boolean sloMoON) {
		// TODO Auto-generated method stub
		
	}



	public void playJewel(boolean sFX, boolean sloMoON) {
		// TODO Auto-generated method stub
		
	}



	public void playBlackPearl(boolean sFX, boolean sloMoON) {
		// TODO Auto-generated method stub
		
	}
	
	
}