package com.smiths;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import com.flurry.android.FlurryAgent;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.smiths.HighScoreDB;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.FrameLayout;

//game overadRequest
public class GameOver extends BaseGameActivity implements IUpdateHandler{
	
	

	private boolean paid = false;
	
	
	boolean liked = false;
	int pollnumber = 2;
	boolean FBvisit = false;
	Context context;
	int screenscale = 500;
	boolean alreadyvoted;
	Scene HighScoresScene;
	Scene StatsScene;
	Scene pollscene;
	Scene fullscreenAdvert;
	boolean start = true;
	public static final int APP_ID = 1041;
	public static final String APP_AUTH = "ddb305dfb779ab28fbb5481d09fb7e79";
	String GameShare = "https://play.google.com/store/apps/details?id=com.smiths.free";

	DelayModifier delay =new DelayModifier(1.5f);

	final int VOTED_DUALSWORDS_ID = 2933;
	final int VOTED_BO_STAFF_ID = 2931;
	final int VOTED_BOWS_ID = 2935;
	
	
	
	
	
	
	
	
	int gamesplayed;
	private int width;
	private int height;
	private EngineOptions engineOptions;
	SharedPreferences preferences;// = PreferenceManager.getDefaultSharedPreferences(this);

	SharedPreferences.Editor settings_editor;// = preferences.edit();

    UIsoundbank uisoundbank = new UIsoundbank();
    private Text Score;
    private Text Level;
    private Text NinjasKilled;
    private Text BiggestNinja;
    private Text Combo;
    private Text Streak;
    private Text Wave;
    private Text Bank;
    //private Menu menu;
	 private HUD mHud;
	 private boolean onHighScoresScreen = false;
	 private boolean onVoteScreen = false;
	 private BuildableBitmapTextureAtlas startgametextureatlas;
	 private BuildableBitmapTextureAtlas sharegametextureatlas;
	 private BuildableBitmapTextureAtlas adfreetextureatlas;
	 private BuildableBitmapTextureAtlas practicetextureatlas;
	 private BuildableBitmapTextureAtlas highscorestextureatlas;
	 private BuildableBitmapTextureAtlas facebooktextureatlas;
	 
	 private AnimatedSprite startgameHUD;
	 private AnimatedSprite sharegameHUD;

	 private AnimatedSprite adfreeHUD;
	 
	 private AnimatedSprite highscoresHUD;
	 private AnimatedSprite practiceHUD;
	 private AnimatedSprite facebookHUD;
	 private BuildableBitmapTextureAtlas pollchoice1textureatlas;
	 private BuildableBitmapTextureAtlas pollchoice2textureatlas;
	 private BuildableBitmapTextureAtlas pollchoice3textureatlas;
	 private AnimatedSprite				 pollchoice1;
	 private AnimatedSprite 			 pollchoice2;
	 private AnimatedSprite 			 pollchoice3;
	 private ITiledTextureRegion 		 pollchoice1texture;
	 private ITiledTextureRegion 		 pollchoice2texture;
	 private ITiledTextureRegion		 pollchoice3texture;
	 
	 private ITiledTextureRegion startgametexture;
	 private ITiledTextureRegion sharegametexture;
	 private ITiledTextureRegion adfreetexture;
	 private ITiledTextureRegion facebooktexture;
	 private ITiledTextureRegion practicetexture;
	 private ITiledTextureRegion highscorestexture;
	 
	private int ourscore;
	private int ourninjaskilled;
	private int ourbiggestninjakilled;
	private int ourlevel;
	private int combo;
	private int streak;
	private int wave;
	private int bank;
	
	private Display display;
	private int screenwidth;
	private int screenheight;
	private Camera mCamera;
	private Font mFont;
	private Font REDmFont;
	private LinkedList<String> scorelist;

	
	FPSLogger fps;
	HighScoreDB dbase;
	private BitmapTextureAtlas REDmFontTexture;
	private BitmapTextureAtlas mFontTexture;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		
		
		
	
		this.display = getWindowManager().getDefaultDisplay();//get screen
		this.screenwidth  = display.getWidth();
		this.screenheight = display.getHeight();
		float screenratio;
		this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
		settings_editor = preferences.edit();
		context = this;
		int Mode =preferences.getInt("Mode", 0);
		
		if(screenwidth>screenheight)
			{
			screenratio=screenwidth/screenheight;
			height = screenscale;
			width = (int)(screenscale*screenratio);
			}
		else
			{
			screenratio=screenheight/screenwidth;
			width = screenscale;
			height = (int) (screenscale*screenratio);
			}
		
		screenwidth = width;
		screenheight = height;

		this.uisoundbank.loadSound(this);
		this.mCamera = new Camera(0, 0, screenwidth, screenheight); //Simplest camera set up
		engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), this.mCamera);
		return engineOptions;
	}
	
	
	
	public boolean isOnline(Context context) {
		final ConnectivityManager conMgr =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		    //notify user you are online
		} else {
		    //notify user you are not online
			return false;
		} 
	}

	
	

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		
		
		FlurryAgent.onStartSession(context , "5CBX77VX5QW2NDVPQ34H");
		
		Bundle extras = getIntent().getExtras();
		
		extras = getIntent().getExtras();
	    if(extras != null) {
	    
	    	ourscore = extras.getInt("score");
	       	ourninjaskilled= extras.getInt("kills");
	    	ourbiggestninjakilled= extras.getInt("biggestkill");
	    	ourlevel= extras.getInt("level");
	    	combo= extras.getInt("combo");
	    	streak= extras.getInt("streak");
	    	wave= extras.getInt("wave");
	    	bank= extras.getInt("money");
	    	Debug.d(""+ourscore);
	    }
	    else
	    {
	    	Debug.d("gameover extras are null");
	    }
	    
		//setting path for textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.pollchoice1texture = SetupTiledTexture(this.pollchoice1textureatlas, 130, 270, "caltropspollchoice.png", 1, 2);
		this.pollchoice2texture = SetupTiledTexture(this.pollchoice2textureatlas, 130, 270, "staffpollchoice.png", 1, 2);
		this.pollchoice3texture = SetupTiledTexture(this.pollchoice3textureatlas, 130, 270, "bowpollchoice.png", 1, 2);
		
		this.facebooktexture = SetupTiledTexture(this.facebooktextureatlas, 280, 400, "facebook.png", 1, 2);
		
		this.startgametexture = SetupTiledTexture(this.startgametextureatlas, 260, 120, "FIGHT.png", 1, 2);
		this.sharegametexture = SetupTiledTexture(this.sharegametextureatlas, 260, 120, "SHARE.png", 1, 2);
		this.adfreetexture = SetupTiledTexture(this.adfreetextureatlas, 260, 120, "AdFree.png", 1, 2);
		
		this.practicetexture = SetupTiledTexture(this.practicetextureatlas, 260, 120, "practice.png", 1, 2);
		this.highscorestexture = SetupTiledTexture(this.highscorestextureatlas, 260, 120, "highscores.png", 1, 2);
		this.scorelist = new LinkedList<String>();
		this.dbase = new HighScoreDB(this);
		this.mFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		
		this.REDmFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mEngine.getTextureManager().loadTexture(this.REDmFontTexture);
		this.screenwidth  = display.getWidth();
		this.screenheight = display.getHeight();
		

		
		liked = preferences.getBoolean("visitedFacebook", false);
		
		
		
		
		
		this.GetscoreList_asString();//this behaves slightly strange strings are loaded into score list
		clean_scorelist(); //before this is called
		
	    
	
	    
	    
	    
	    
	    this.runOnUiThread(new Runnable(){
	       	 
            public void run(){
            	
            }
		 });
	    this.runOnUiThread(new Runnable(){
	       	 
            public void run(){
            	
            }
		});
	    
	    //this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
	    this.mFont = new Font(this.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC), 32, true, Color.WHITE);
	    this.REDmFont = new Font(this.getFontManager(), this.REDmFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC), 32, true, Color.RED);
	    this.mEngine.getTextureManager().loadTexture(this.REDmFontTexture);
	    this.mFont.load();
	    this.REDmFont.load();
		/*
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256,128, TextureOptions.BILINEAR);
        this.BackgroundTex = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "GAMEOVER.png", 0, 0);
        this.mBitmapTextureAtlas.load();

		*/
		pOnCreateResourcesCallback.onCreateResourcesFinished();
		
	}
	
	
	private void GetscoreList_asString(){
		Cursor list = this.dbase.GetAllRows();
		startManagingCursor(list);
		list.moveToFirst();
		//Debug.d("SCORES", "scorelist size =  "+ list.getCount());
		for (int i = 0;i < list.getCount() -1; i++){// for each row
			scorelist.add(i, list.getString(2)); //get the String in column 2
			list.moveToNext();
			
		}
	}
	

	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) 
	{
		if(pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN)
		{
			Intent intent = new Intent(this , StickmanSplash.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			this.finish();
			return true;
		}
		
	 
		else {
		return super.onKeyDown(pKeyCode, pEvent);
		}
}
	
	
	private void clean_scorelist(){
		int listsize = scorelist.size();
		for (int i = 0; i<listsize; i++){
			if ( i > 10){
				dbase.deleteRow(i);
				scorelist.remove(11);
			}
			
		}
	}
	protected void createHud(){
		this.mHud = new HUD();
		this.mCamera.setHUD(mHud);
		
		this.mHud.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                    
            }
    }));
	}
	
	
	
	
	
	private void Getnamelist(){

		//TODO col 1 in the database is name but is not currently being used
	
	}
	
	
	
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		//register update handlers
		this.fps = new FPSLogger();
		this.mEngine.registerUpdateHandler(this.fps);
		this.mEngine.registerUpdateHandler(this);
		this.createHud();
		
		
		

		PopulateHUD();
		

		pOnCreateSceneCallback.onCreateSceneFinished(StatsScene);
	}

	
	

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
		
	}

	
	@Override
	public void onUpdate(float pSecondsElapsed) {
        
    	   if((alreadyvoted == false)&&(onVoteScreen == false))
    	   {
    		  CheckVoted();                    
        	  Debug.d("Checking for previous vote");
    	   }
    	   
    		   
    	
		
		if(start == true)
		{
			start = false;
			DisplayStats();
		}
		
		
		//this.mEngine.unregisterUpdateHandler(this.fps);
		this.mEngine.unregisterUpdateHandler(this);
		
		
	}
	
	
	
	@Override
	public void reset() {
		
	}
	
	void DisplayStats()
	{
		highscoresHUD.setCurrentTileIndex(0);
		onHighScoresScreen = false;
		StatsScene = new Scene();
		//scene.setChildScene(thisscene);
		this.mEngine.setScene(StatsScene);
		this.StatsScene.setBackground(new Background(Color.BLACK));
		this.StatsScene.registerEntityModifier(delay);
		
		
		//create text from extras
		Level = new Text((float)5, (float)120,this.mFont, (CharSequence)("Level = "+ String.valueOf(ourlevel)), this.getVertexBufferObjectManager());
		NinjasKilled = new Text( (float)5,(float)(240),this.mFont, (CharSequence)("Kills = "+ String.valueOf(ourninjaskilled)), this.getVertexBufferObjectManager());
		Score = new Text( (float)5,(float)(80),this.mFont, (CharSequence)("Earned = "+ String.valueOf(ourscore)), this.getVertexBufferObjectManager());
		BiggestNinja = new Text( (float)5,(float)(160),this.mFont, (CharSequence)("Toughest = "+ String.valueOf(ourbiggestninjakilled)), this.getVertexBufferObjectManager());
		Combo = new Text( (float)5,(float)(200),this.mFont, (CharSequence)("Combo = "+ String.valueOf(combo)), this.getVertexBufferObjectManager());
		Streak = new Text( (float)5,(float)(280),this.mFont, (CharSequence)("Streak = "+ String.valueOf(streak)), this.getVertexBufferObjectManager());
		Wave = new Text( (float)5,(float)(320),this.mFont, (CharSequence)("Waves = "+ String.valueOf(wave)), this.getVertexBufferObjectManager());
		Bank = new Text( (float)5,(float)(360),this.mFont, (CharSequence)("Bank = "+ String.valueOf(bank)), this.getVertexBufferObjectManager());

		//attach to scene
		this.StatsScene.attachChild(Level);
		this.StatsScene.attachChild(NinjasKilled);
		this.StatsScene.attachChild(Score);
		this.StatsScene.attachChild(BiggestNinja);
		this.StatsScene.attachChild(Combo);
		this.StatsScene.attachChild(Streak);
		this.StatsScene.attachChild(Wave);
		this.StatsScene.attachChild(Bank);
		
		    
	}
	
	
	
	void PopulateHUD()
	{
		
		this.startgameHUD = new AnimatedSprite((int)width -200, (int)(this.height)-105, this.startgametexture, this.getVertexBufferObjectManager()){
  	        @Override
  	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

  	        	
  	    			if(pSceneTouchEvent.isActionDown()) 
  	    			{
  	    				//start new game here
  	    				startgameHUD.setCurrentTileIndex(1);
  	    				uisoundbank.playSwishKill();
  	    				FlurryAgent.logEvent("Fighting again!");
  	    				
  	    				return true;
  	    			
  	    			}
  	    			if(pSceneTouchEvent.isActionUp()){
  	    				Intent intent = new Intent(GameOver.this,  StickmanActivity.class);
  	    				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
  	    				return true;
  	    			}
  	    		
  	    		return false;
  	                        }};
  	                      
  	                   	 this.mHud.attachChild(this.startgameHUD);
  	                 	   this.mHud.registerTouchArea(this.startgameHUD);
  	                 	   this.startgameHUD.setCurrentTileIndex(0);
  	                   	


  		this.sharegameHUD = new AnimatedSprite((int)width -200, (int)(this.height)-205, this.sharegametexture, this.getVertexBufferObjectManager()){
    	        @Override
    	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

    	        	
    	    			if(pSceneTouchEvent.isActionDown()) 
    	    			{
    	    				//start new game here
    	    				sharegameHUD.setCurrentTileIndex(1);
    	    				uisoundbank.playSwishKill();
    	    				FlurryAgent.logEvent("Sharing the game!");
    	    				
    	    				return true;
    	    			
    	    			}
    	    			if(pSceneTouchEvent.isActionUp()){
    	    				
    	    				ShareGame();
    	    				
    	    				return true;
    	    			}
    	    		
    	    		return false;
    	                        }};
    	                        


    	                   	 this.mHud.attachChild(this.sharegameHUD);
    	                 	   this.mHud.registerTouchArea(this.sharegameHUD);
    	                 	   this.sharegameHUD.setCurrentTileIndex(0);
    	                 	   
  	  	         /*       	                 	   
          	this.adfreeHUD = new AnimatedSprite(5, 0, this.adfreetexture, this.getVertexBufferObjectManager()){
    	        @Override
    	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

    	        	
    	    			if(pSceneTouchEvent.isActionDown()) 
    	    			{
    	    				//start new game here
    	    				adfreeHUD.setCurrentTileIndex(1);
    	    				uisoundbank.playSwishKill();
    	    				FlurryAgent.logEvent("Adfree button pressed");
    	    				VisitAdFree();
    	    				return true;
    	    			
    	    			}
    	    			if(pSceneTouchEvent.isActionUp()){
    	    				return true;
    	    			}
    	    		
    	    		return false;
    	                        }};
    	                        
    	                      
    	                        this.mHud.attachChild(this.adfreeHUD);
    	                        this.mHud.registerTouchArea(this.adfreeHUD);
    	                 	   	this.adfreeHUD.setCurrentTileIndex(0);
        	              */     	

	this.facebookHUD = new AnimatedSprite(0,0, this.facebooktexture, this.getVertexBufferObjectManager()){
	        @Override
	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

	        	
	    			if(pSceneTouchEvent.isActionDown()) 
	    			{
	    				//start new game here
	    				
	    				uisoundbank.playSwishKill();
	    				
	    				return true;
	    			
	    			}
	    			if(pSceneTouchEvent.isActionUp()){
	    				if(isOnline(context))
	    				{
	    				VisitFacebook();
	    				facebookHUD.setCurrentTileIndex(1);
	    				}
	    				return true;
	    			}
	    		
	    		return false;
	                        }};
  	                	       
	                        if((liked == true)||(paid == true))
	                		{
	                        	facebookHUD.setCurrentTileIndex(1);

	                		}
	                        else
	                		{
	                        	facebookHUD.setCurrentTileIndex(0);
		                        	
	                		}

	                        facebookHUD.setPosition(width -facebookHUD.getWidth(), 90);
	                        this.mHud.attachChild(this.facebookHUD);
	                 	   this.mHud.registerTouchArea(this.facebookHUD);
	                 	   this.startgameHUD.setCurrentTileIndex(0);
	                   	  
	                 	    
  	                        
        if(ourninjaskilled <25)
        {
        	if(preferences.getInt("tipnumber", 0)>10)
        	{
        		settings_editor.putInt("tipnumber" , 0);
        		settings_editor.commit();
        
        	}
        	
        	
			this.practiceHUD = new AnimatedSprite((int)width -200, (int)(this.height)-155, this.practicetexture, this.getVertexBufferObjectManager()){
			        @Override
			        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			
			        	
			    			if(pSceneTouchEvent.isActionDown()) 
			    			{
			    				//start new game here
			    				practiceHUD.setCurrentTileIndex(1);
			    				uisoundbank.playSwishKill();
			    				
			    				settings_editor.putInt("Mode", 1);
		  	    				settings_editor.commit();
			    				return true;
			    			
			    			}
			    			if(pSceneTouchEvent.isActionUp()){
			    				Intent intent = new Intent(GameOver.this,  StickmanActivity.class);
			    				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    				FlurryAgent.logEvent("Practice Button on Stats screen");
			                  startActivity(intent);
			                  finish();
			    				return true;
			    			}
			    		
			    		return false;
			                        }};
			                          	                        
			    
	  	                        
	  	                        
	  	   this.mHud.attachChild(this.practiceHUD);
	  	   this.mHud.registerTouchArea(this.practiceHUD);
	  	   this.practiceHUD.setCurrentTileIndex(0);
        }
  	   
  	              	//startgameHUD.setVisible(false);
  	              	
  	    this.highscoresHUD = new AnimatedSprite((int)width -200, (int)(height)-55, this.highscorestexture, this.getVertexBufferObjectManager()){
		@Override
        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

        	
    			if(pSceneTouchEvent.isActionDown()) 
    			{
    				if(onHighScoresScreen == false)
    				{
    					uisoundbank.playHit();
    					FlurryAgent.logEvent("Checked High Scores");
    					DisplayHighScores();
    				
    				}
    				else
    				{
    					uisoundbank.playHit();
    					FlurryAgent.logEvent("Checked Stats");
    					DisplayStats();
    				
    				}
    			return true;
    			
    			}
    			if(pSceneTouchEvent.isActionUp()){
    				
    				return true;
    			}
    		
    		return false;
                        }

		};
                        
                    this.mHud.attachChild(this.highscoresHUD);
                    this.mHud.registerTouchArea(this.highscoresHUD);
              		this.highscoresHUD.setCurrentTileIndex(0);
           
		}
	
	
	private void DisplayHighScores() {
		onHighScoresScreen = true;
		highscoresHUD.setCurrentTileIndex(1);
		HighScoresScene = new Scene();
		//scene.setChildScene(thisscene);
		this.mEngine.setScene(HighScoresScene);
		//Debug.d("SORELIST ","list = " +scorelist.size());
		int pos = 0;
		Cursor list = this.dbase.GetAllRows();
		list.moveToFirst();
		
		String colonString = "";
		HighScoresScene.setBackground(new Background(Color.BLACK));
		for(int i=0; i < scorelist.size() -1;i++){
			pos += 28;
			if(i==0)
			{
				colonString = "1st";
			}
			else if(i==1)
			{
				colonString = "2nd";
			}
			else if(i==2)
			{
				colonString = "3rd";
			}
			else if(i==3)
			{
				colonString = "4th";
			}
			else if(i==4)
			{
				colonString = "5th";
			}
			else if(i==5)
			{
				colonString = "6th";
			}
			else if(i==6)
			{
				colonString = "7th";
			}
			else if(i==7)
			{
				colonString = "8th";
			}
			else if(i==8)
			{
				colonString = "9th";
			}
			else if(i==9)
			{
				colonString = "10th";
			}
			
			Font currentFont;
			if(list.getInt(2)==ourscore)
			
			{
				currentFont = REDmFont;
				ShowPlacementGFX(i, colonString, HighScoresScene);
			}
			else
			{
				currentFont = mFont;
			}
		
				
			Text numberText = new Text((float)5, (float) pos+47, currentFont,(CharSequence)(colonString), this.getVertexBufferObjectManager());
			Text text = new Text((float)75, (float)pos+47,currentFont, (CharSequence)(" - " + String.valueOf(scorelist.get(i))), this.getVertexBufferObjectManager());
		
			HighScoresScene.attachChild(numberText);
			HighScoresScene.attachChild(text);
			list.moveToNext();
		}
		
		
		
	}
	
	private void ShowPlacementGFX(int i, String place, Scene thisscene) {
		
		String messg = "You got "+ place + " place!";
		Text congrats = new Text(0,0, REDmFont, (CharSequence)(messg), this.getVertexBufferObjectManager());
		congrats.setPosition(5, height - 40);
		thisscene.attachChild(congrats);
	}

	TiledTextureRegion SetupTiledTexture(BuildableBitmapTextureAtlas newAtlas, int AtlasWidth, int AtlasHeight,  String ImageName, int Columns,int Rows)
	{
		
		TiledTextureRegion tiledTexture;
		newAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), AtlasWidth, AtlasHeight, TextureOptions.NEAREST);
		tiledTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(newAtlas, this, ImageName, Columns, Rows );
		try {
			newAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			newAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
	return tiledTexture;
	}
	
	
	
	
	void VisitFacebook(){
		
		FlurryAgent.logEvent("Visited Facebook");
		settings_editor.putBoolean("visitedFacebook", true);
		FBvisit = true;
		settings_editor.commit();
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/StickmanVsInfiniteNinjas"));
		browserIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		startActivity(browserIntent);
	}


	
	
	


	void ShareGame()
	{
		try
		{ Intent i = new Intent(Intent.ACTION_SEND);  
		  i.setType("text/plain");
		  i.putExtra(Intent.EXTRA_SUBJECT, "Stickman Vs Infinite Ninjas");
		  String sAux = "\n Check out this game!!\n\n";
		  sAux = sAux + GameShare;
		  i.putExtra(Intent.EXTRA_TEXT, sAux);  
		  startActivity(Intent.createChooser(i, "choose one"));
		}
		catch(Exception e)
		{ //e.toString();
		}   
	}
	
	
	//adview
	//////////////////////////
	/*
	protected void onSetContentView() {
        
        if(paid == true){return;}
        final FrameLayout frameLayout = new FrameLayout(this);
        final FrameLayout.LayoutParams frameLayoutLayoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                                             FrameLayout.LayoutParams.FILL_PARENT);
 
        final AdView adView = new AdView(this, AdSize.BANNER, "a14ff7315c477c7");
 
        adView.refreshDrawableState();
        adView.setVisibility(AdView.VISIBLE);
        final FrameLayout.LayoutParams adViewLayoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                                             FrameLayout.LayoutParams.WRAP_CONTENT,
                                             Gravity.RIGHT|Gravity.TOP);
       
 
        AdRequest adRequest = new AdRequest();
        adRequest.addTestDevice("5E61BAA9790E5F743068A04E63454D9D"); //Jesse's phone
        adRequest.addTestDevice("43D9A5943CC0933D5C1CDCA0274A71D0"); //JXD tablet
        adRequest.addTestDevice("8C4E1DA1DB269AD32367885D94AD9DAA"); //Jeff's Aria
        adView.loadAd(adRequest);
 
        this.mRenderSurfaceView = new RenderSurfaceView(this);
        mRenderSurfaceView.setRenderer(mEngine, this);
 
        final android.widget.FrameLayout.LayoutParams surfaceViewLayoutParams =
                new FrameLayout.LayoutParams(super.createSurfaceViewLayoutParams());
 
        frameLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams);
        frameLayout.addView(adView, adViewLayoutParams);
 
        this.setContentView(frameLayout, frameLayoutLayoutParams);
    }
	
	*/
	void CheckVoted()
	{
		
		alreadyvoted = true;
		int previouspolls = preferences.getInt("previouspolls", 0);
		
		if(previouspolls <  pollnumber){alreadyvoted = false;}
		
		if(alreadyvoted == true)
		{
			return;
		}
		
		
		
		if((alreadyvoted == false)&&((liked == true)||(paid == true))&&(isOnline(this)))
		{	
			start = false;
			RunPoll();
		}
				
				
	}

	void SayThanks()
	{
		
	}
	
	void RunPoll()
	{
		hideButtons(true);
		onVoteScreen = true;
		Debug.d("running poll!");
		facebookHUD.setPosition(5000, 5000);
		pollscene = new Scene();
		//this.mEngine.getScene().setChildScene(pollscene);
		this.mEngine.setScene(pollscene);
		pollscene.setBackground(new Background(Color.BLACK));
		Text PollQuestion;
		String Question = "Which should we add next?";
		PollQuestion = new Text(0, 0,this.mFont, (CharSequence)(Question), this.getVertexBufferObjectManager());
		PollQuestion.setPosition((float)(width -PollQuestion.getWidth())/2, 90);
		pollscene.attachChild(PollQuestion);
		
		Text Stipulation;
		String stip = "you may only vote once";
		Stipulation = new Text(0, 0,this.mFont, (CharSequence)(stip), this.getVertexBufferObjectManager());
		Stipulation.setPosition((float)(width -Stipulation.getWidth())/2, 130);
		pollscene.attachChild(Stipulation);
		
		
		pollchoice1 = new AnimatedSprite((int)((width/4)-64), (int)((height/2)-64), this.pollchoice1texture, this.getVertexBufferObjectManager()){
  	        @Override
  	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

  	        	
  	    			if(pSceneTouchEvent.isActionDown()) 
  	    			{
  	    				if(alreadyvoted == true){return false;}
  	    				PollChoice(1, pollscene);
  	    				alreadyvoted = true;
  	    				start = true;
  	    				pollscene.dispose();
  	    				return true;
  	    			
  	    			}
  	    			if(pSceneTouchEvent.isActionUp()){
  	    				
  	    				return true;
  	    			}
  	    		
  	    		return false;
  	                        }};
	      pollchoice2 = new AnimatedSprite((int)((width/2)-64), (int)((height/2)-64), this.pollchoice2texture, this.getVertexBufferObjectManager()){
	        @Override
	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	
	        	
	    			if(pSceneTouchEvent.isActionDown()) 
	    			{
	    				if(alreadyvoted == true){return false;}
	    				PollChoice(2, pollscene);
	    				alreadyvoted = true;
	    				start = true;
	    				pollscene.dispose();
	    				return true;
	    			
	    			}
	    			if(pSceneTouchEvent.isActionUp()){
	    				
	    				return true;
	    			}
	    		
	    		return false;
	                        }};
	        
  	      pollchoice3= new AnimatedSprite((int)((width*3/4)-64),(int)((height/2)-64), this.pollchoice3texture, this.getVertexBufferObjectManager()){
  	        @Override
  	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
  	
  	        	
  	    			if(pSceneTouchEvent.isActionDown()) 
  	    			{
  	    				if(alreadyvoted == true){return false;}
  	    				PollChoice(3, pollscene);
  	    				alreadyvoted = true;
  	    				start = true;
  	    				pollscene.dispose();
  	    				return true;
  	    			
  	    			}
  	    			if(pSceneTouchEvent.isActionUp()){
  	    				
  	    				return true;
  	    			}
  	    		
  	    		return false;
  	                        }};
	              	 	                        
  	                        
	  	   this.pollscene.attachChild(this.pollchoice1);
	  	   this.pollscene.registerTouchArea(this.pollchoice1);
	  	   this.pollchoice1.setCurrentTileIndex(0);
	  	   this.pollscene.attachChild(this.pollchoice2);
	  	   this.pollscene.registerTouchArea(this.pollchoice2);
	  	   this.pollchoice2.setCurrentTileIndex(0);
	  	   this.pollscene.attachChild(this.pollchoice3);
	  	   this.pollscene.registerTouchArea(this.pollchoice3);
	  	   this.pollchoice3.setCurrentTileIndex(0);
	}
	
	
	void hideButtons(boolean choice){
		boolean hidden = false;
		if(choice == true)
		{
			hidden = false;
		}
		else if(choice == false)
		{
			hidden = true;
		}
		this.sharegameHUD.setVisible(hidden);
		this.highscoresHUD.setVisible(hidden);
		if(this.practiceHUD!=null)
		{
			this.practiceHUD.setVisible(hidden);
		}
		this.startgameHUD.setVisible(hidden);
	}
	
	
	void PollChoice(int choice, Scene scene)
	{
		
		scene.detachChildren();
		scene.back();
		hideButtons(false);
		scene.setVisible(false);
		this.mEngine.setScene(HighScoresScene);
		if(alreadyvoted == true){return;}
		DisplayHighScores();
		
		
		if(choice == 1)
		{
			FlurryAgent.logEvent("Poll 2 Caltrops");
		}
		if(choice == 2)
		{
			FlurryAgent.logEvent("Poll 2 Bo Staff");
		}
		if(choice == 3)
		{
			FlurryAgent.logEvent("Poll 2 Bows");
		}
		
		settings_editor.putInt("previouspolls", pollnumber);
		settings_editor.commit();
	}
	
	
	

}
