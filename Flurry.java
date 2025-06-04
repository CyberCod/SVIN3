package com.smiths;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ConcurrentModificationException;
import java.util.Map;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.util.debug.Debug;

import com.flurry.android.*;

import com.smaato.soma.*;
import android.app.Activity;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
	


public class Flurry extends Activity{
			private boolean paid = true;
			String packagename; 
	
			static boolean _active = true;
			boolean _ok2 = false;
			private static Button continuebutton;
			private static Button noadsbutton;
			//show one ad every adFrequency games
			int adFrequency = 3;
			//don't show an ad until game number FirstAdAt
			int FirstAdAt = 5;
			
			//decide whether or not to run an ad based on game above adFrequency and FirstAdAt
			boolean RunAd = false;
			Context context;
			SharedPreferences preferences;

			SharedPreferences.Editor settings_editor;
			Intent intent;
			int score;
			int ninjaskilled;
			int biggestninjakilled;
			int level;
			int combo;
			int streak;
			int wave;
		
			

			//https://my.smaato.com/selfservice/myAdspaces.jsp
	 @Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       preferences = PreferenceManager.getDefaultSharedPreferences(this);
	       settings_editor = preferences.edit();
	       MoveStats();
	       context = this;
	       RunAd = AdController();
	       
	       if(paid = false)
			{
			packagename = "com.smiths.free.";
			}
			else
			{
				packagename = "com.smiths.paid.";
			}
	       
	       Button.OnClickListener continuebuttonOnClickListener = new Button.OnClickListener(){

	 		  @Override
	 		  public void onClick(View v) 
	 		  {
	 		   // TODO Auto-generated method stub
	 		   StatsScreen();
	 		  }
	 	  };
	 	 
	 	  Button.OnClickListener noadsbuttonOnClickListener = new Button.OnClickListener(){

	 		  @Override
	 		  public void onClick(View v) 
	 		  {
	 		   // TODO Auto-generated method stub
	 			 VisitAdFree();
	 		  }
	 	  };
	       
	      
	 	 
	       setContentView(R.layout.flurry);
	       BannerView mBanner = (BannerView)findViewById(R.id.bannerview);
	       mBanner.getAdSettings().setPublisherId(923863464);
	       mBanner.getAdSettings().setAdspaceId(65769522);
	       mBanner.getAdSettings().setbannerHeight(250);
	       mBanner. getUserSettings().setUserGender(UserSettings.Gender.MALE);
	       mBanner.setBackgroundColor(Color.BLACK);
	       mBanner.getUserSettings().setAge(20);
	       continuebutton = (Button)findViewById(R.id.continuebutton);
	       continuebutton.setOnClickListener(continuebuttonOnClickListener);
	      
	       noadsbutton = (Button)findViewById(R.id.noadsbutton);
	       noadsbutton.setOnClickListener(noadsbuttonOnClickListener);
	       
	      // mBanner.getAdSettings().setAdType(MediaType.TXT);
	       mBanner.asyncLoadNewBanner();
	    
	       
	       
	       continuebutton.setVisibility(View.INVISIBLE);
	       continuebutton.postDelayed(new Runnable() {
	    	    public void run() {
	    	    	continuebutton.setVisibility(View.VISIBLE);
	    	    }
	    	}, AdTimer());
	       
	 }
	 
	 
	 
	 private long AdTimer() {
		
		 if(RunAd == true)
		 {
			 return 7000;	 
		 }
		 else
		 {
			 return 0;
		 }
		
	}



	void VisitAdFree(){
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.smiths.paid"));
			browserIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			startActivity(browserIntent);
		}
	  
	  
	 @Override
	 public void onDestroy(){
		 super.onDestroy();
		 
			this.finish();
	 }
	 
	 
	    
		//hooks for Activity life cycle
	    @Override
	    protected void onStart() {
	        super.onStart();
	        
	        // The activity is about to become visible.
	    
	    }
	    
	    @Override
	    protected void onResume() {
	        super.onResume();
	        // The activity has become visible (it is now "resumed").
	        
	    }

		@Override
	    protected void onPause() {
	        super.onPause();
	        // Another activity is taking focus (this activity is about to be "paused").
	    
	    }
		
	    @Override
	    protected void onStop() {
	        super.onStop();
	        FlurryAgent.onEndSession(context);
			
	        // The activity is no longer visible (it is now "stopped")
	    }
	    
	    
	 
	 void MoveStats(){
		 

			Bundle extras = getIntent().getExtras();
			
			
			 if(extras != null) {
				    
			    	score = extras.getInt("score");
			        ninjaskilled= extras.getInt("kills");
			    	biggestninjakilled= extras.getInt("biggestkill");
			    	level= extras.getInt("level");
			    	combo= extras.getInt("combo");
			    	streak= extras.getInt("streak");
			    	wave= extras.getInt("wave");
			    	
			    	Debug.d(""+score);
			    }
			 else
			 {
				 Debug.d("flurry extras are null!");
			 }
			 
			 
		    intent = new Intent(this , GameOver.class);
		   
		   
		   		
			intent.putExtra("score", score);
			intent.putExtra("level", level);
			intent.putExtra("biggestkill", biggestninjakilled);
			intent.putExtra("kills", ninjaskilled);
			intent.putExtra("combo", combo);
			intent.putExtra("streak",streak);
			intent.putExtra("wave", wave);
			
		 
	 }
	 
	 
	 
	 
	 
	 private void StatsScreen() {
			//intent = new Intent(this , GameOver.class);	
			this.startActivity(intent);
			this.finish();
		}



	int GamesPlayed() 
	{
	int experience = preferences.getInt("experience",0) + ninjaskilled;
	int gamesplayed = preferences.getInt("gamesplayed",0)+1;
	
	settings_editor.putInt("experience", experience);
	settings_editor.putInt("gamesplayed", gamesplayed);
    settings_editor.commit();
    
    //put Map<String,String> here to push to Flurry
    
    return gamesplayed;
	}

	private boolean AdController() {
		//decides when to show ads
		int gamesplayed = GamesPlayed();

		if(gamesplayed > 20)
		{
			adFrequency = 2;
		}
		else if(gamesplayed > 50)
		{
			if(paid == false)
			{
				return true;
			}
		}
		
	    float divisorfloat = gamesplayed / adFrequency;
		int divisorint = (int) gamesplayed / adFrequency;
		
		if(paid == true)
		{
			return false;
		}
		
		
		if(gamesplayed>=FirstAdAt)
		{
		 if(divisorfloat == (float)divisorint)
		 {
			 return true;
		 }
		}
		
		return false;
	}
     	



     

	
}