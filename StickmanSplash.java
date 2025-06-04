package com.smiths;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import com.flurry.android.FlurryAgent;
import com.smiths.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;



public class StickmanSplash extends BaseGameActivity implements IUpdateHandler {

	//initiial splash


	private final static int screenscale = 400;
	int modeHistory;
	int tutorialpages=18;
	Menu localmenuscene;
	private float bgscale;
	private boolean startcalibrationwarning = false;
	private int warningtimer =0;
	private boolean firstrun = false;
	private int width;
	private int height;
	private int screenwidth;
	private int screenheight;
	private boolean touch1;
	private boolean touch2;
	boolean selectionmade = false;
	Scene modescene;
	Scene tutorialscene;
	Scene practicemodescene;
	private Font REDmFont;
	private Font VersionFont;
	private ITextureRegion REDmFontTextureRegion;
	private ITextureRegion VersionFontTextureRegion;
	private BitmapTextureAtlas REDmFontTexture;
	private BitmapTextureAtlas VersionFontTexture;
	private BitmapTextureAtlas calibrationwarningatlas;
	private BitmapTextureAtlas AndengineLogoAtlas;
	private BitmapTextureAtlas SmithSquaredLogoAtlas;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private BuildableBitmapTextureAtlas startgametextureatlas;
	private BuildableBitmapTextureAtlas tutorialbuttontextureatlas;
	private BitmapTextureAtlas[] tutorialpictextureatlas;
	private Sprite[] tutorial;
	ITextureRegion[] tutorialpictexture;
	private BuildableBitmapTextureAtlas menubuttontextureatlas;
	Context context;
	
	//--------------DEFINE MODES
	int Practice1on1 = 1;
	int PracticeVsBoxes = 2;
	int EasyMode = 3;
	int NormalMode = 4;
	int GamerMode = 5;
	int StoryMode = 6;
	int TestMode = 7;
	
	
	private ITextureRegion calibrationwarningtexture;
	private ITextureRegion mSplashImageTextureRegion;
	private ITextureRegion SmithSquaredLogoRegion;
	private ITextureRegion AndengineLogoRegion;
	private ITiledTextureRegion startgametexture;
	private ITiledTextureRegion tutorialbuttontexture;
	private ITiledTextureRegion menubuttontexture;
	
	 private BuildableBitmapTextureAtlas PracticeModeButtontextureatlas;
	 private BuildableBitmapTextureAtlas EasyModeButtontextureatlas;
	 private BuildableBitmapTextureAtlas NormalModeButtontextureatlas;
	 private BuildableBitmapTextureAtlas GamerModeButtontextureatlas;
	 private BuildableBitmapTextureAtlas Practice1on1Buttontextureatlas;
	 private BuildableBitmapTextureAtlas PracticeVsBoxesButtontextureatlas;
	 
	 
	 private AnimatedSprite				 PracticeModeButton;
	 private AnimatedSprite 			 EasyModeButton;
	 private AnimatedSprite 			 NormalModeButton;
	 private AnimatedSprite 			 GamerModeButton;
	 private AnimatedSprite 			 Practice1on1Button;
	 private AnimatedSprite 			 PracticeVsBoxesButton;
	 
	 
	 private ITiledTextureRegion 		 PracticeModeButtontexture;
	 private ITiledTextureRegion 		 EasyModeButtontexture;
	 private ITiledTextureRegion 		 NormalModeButtontexture;
	 private ITiledTextureRegion		 GamerModeButtontexture;
	 private ITiledTextureRegion		 Practice1on1Buttontexture;
	 private ITiledTextureRegion		 PracticeVsBoxesButtontexture;
		
	 
	
	 final Scene scene = new Scene();
	
	Sprite calibrationwarning;
	Sprite AELogo;
	Sprite SSLogo;
	Sprite splashimage;
	private Display display;
	
	private EngineOptions engineOptions;

	private HUD mHud;
	
	
	
	private Camera mCamera;
	
	
	private AnimatedSprite fightHUD;
	private AnimatedSprite tutorialHUD;
	//private AnimatedSprite menubuttonHUD;


	

	SharedPreferences preferences;// = PreferenceManager.getDefaultSharedPreferences(this);

	SharedPreferences.Editor settings_editor;// = preferences.edit();

	private UIsoundbank uisoundbank = new UIsoundbank();
	private boolean tutorialcompleted;
	private boolean OnScreenControlsPref;

	



	

	//scene.setChildScene(mMenuScene, false, true, true);

	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {
		this.mEngine = new LimitedFPSEngine(this.engineOptions, 32);



		return this.mEngine;
	}



	@Override
	public EngineOptions onCreateEngineOptions() {
		this.display = getWindowManager().getDefaultDisplay();
		this.screenwidth  = display.getWidth();
		this.screenheight = display.getHeight();
		float screenratio;

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

		this.tutorial = new Sprite[tutorialpages]; 
		this.tutorialpictexture = new ITextureRegion[tutorialpages];
		this.tutorialpictextureatlas = new BitmapTextureAtlas[tutorialpages];
		
		screenwidth = width;
		screenheight = height;
		this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
		settings_editor = preferences.edit();
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		this.uisoundbank.loadSound(this);
		this.mCamera = new Camera(0, 0, screenwidth, screenheight); //Simplest camera set up
		engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), this.mCamera);
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		localmenuscene = new Menu(this.getTextureManager(), this, null, null, this.getVertexBufferObjectManager(), mCamera, this);
		localmenuscene.LoadMenuResources();
		//localmenuscene.createMenuScene();
		 context = this;
	       FlurryAgent.onStartSession(context, "5CBX77VX5QW2NDVPQ34H");
			
		this.AndengineLogoAtlas = new BitmapTextureAtlas(this.getTextureManager(), 70,70, TextureOptions.NEAREST);
		this.SmithSquaredLogoAtlas = new BitmapTextureAtlas(this.getTextureManager(), 70,70, TextureOptions.NEAREST);
		this.startgametexture = SetupTiledTexture(startgametextureatlas, 260, 120, "startgame.png", 1, 2);
		this.tutorialbuttontexture = SetupTiledTexture(tutorialbuttontextureatlas, 260, 120, "tutorialbutton.png", 1, 2);
		
		for(int x = 0;x<tutorialpages;x++)
		{
			tutorialpictextureatlas[x] = new BitmapTextureAtlas(this.getTextureManager(), 305,200, TextureOptions.NEAREST);
		}
		this.tutorialpictexture[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[0], this, "tutorial00.jpg", 0, 0);
		this.tutorialpictexture[1]= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[1], this, "tutorial01.jpg", 0, 0);
		this.tutorialpictexture[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[2], this, "tutorial02.jpg", 0, 0);
		this.tutorialpictexture[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[3], this, "tutorial03.jpg", 0, 0);
		this.tutorialpictexture[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[4], this, "tutorial04.jpg", 0, 0);
		this.tutorialpictexture[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[5], this, "tutorial05.jpg", 0, 0);
		this.tutorialpictexture[6] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[6], this, "tutorial06.jpg", 0, 0);
		this.tutorialpictexture[7] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[7], this, "tutorial07.jpg", 0, 0);
		this.tutorialpictexture[8] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[8], this, "tutorial08.jpg", 0, 0);
		this.tutorialpictexture[9] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[9], this, "tutorial09.jpg", 0, 0);
		this.tutorialpictexture[10] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[10], this, "tutorial10.jpg", 0, 0);
		this.tutorialpictexture[11]  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[11], this, "tutorial11.jpg", 0, 0);
		this.tutorialpictexture[12] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[12], this, "tutorial12.jpg", 0, 0);
		this.tutorialpictexture[13] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[13], this, "tutorial13.jpg", 0, 0);
		this.tutorialpictexture[14] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[14], this, "tutorial14.jpg", 0, 0);
		this.tutorialpictexture[15] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[15], this, "tutorial15.jpg", 0, 0);
		this.tutorialpictexture[16] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[16], this, "tutorial16.jpg", 0, 0);
		this.tutorialpictexture[17] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.tutorialpictextureatlas[17], this, "tutorial17.jpg", 0, 0);
		
		
		                       
		
		
		
		
		
		
		this.menubuttontexture = SetupTiledTexture(menubuttontextureatlas, 260, 120, "titlemenu.png", 1, 2);
		this.AndengineLogoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.AndengineLogoAtlas, this, "AElogo.png", 0,0);
		this.SmithSquaredLogoRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.SmithSquaredLogoAtlas, this, "smallSSlogo.png", 0,0);
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 400,300, TextureOptions.NEAREST);//container for textures
		this.mSplashImageTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "Title.png", 0, 0);//image from file
		this.AndengineLogoAtlas.load();
		this.SmithSquaredLogoAtlas.load();
		this.firstrun=preferences.getBoolean("firstrun", true);
		this.modeHistory=preferences.getInt("modeHistory", 0);
		this.OnScreenControlsPref = preferences.getBoolean("OnScreenControlsPref", false);
		this.calibrationwarningatlas = new BitmapTextureAtlas(this.getTextureManager(), 300,200, TextureOptions.NEAREST);//container for textures;

		this.calibrationwarningtexture= BitmapTextureAtlasTextureRegionFactory.createFromAsset(calibrationwarningatlas, this, "calibration.png", 0, 0);//image from file;
		this.Practice1on1Buttontexture = SetupTiledTexture(this.Practice1on1Buttontextureatlas, 210, 120, "practicemode1on1.png", 1, 2);
		this.EasyModeButtontexture = SetupTiledTexture(this.EasyModeButtontextureatlas,  210, 120, "easy.png", 1, 2);
		this.NormalModeButtontexture = SetupTiledTexture(this.NormalModeButtontextureatlas,  210, 120, "normalmode.png", 1, 2);
		this.GamerModeButtontexture = SetupTiledTexture(this.GamerModeButtontextureatlas,  210, 120, "gamer.png", 1, 2);
		this.PracticeModeButtontexture = SetupTiledTexture(this.PracticeModeButtontextureatlas,  210, 120, "titlepractice.png", 1, 2);
		this.PracticeVsBoxesButtontexture = SetupTiledTexture(this.PracticeVsBoxesButtontextureatlas,  210, 120, "practicemodevsboxes.png", 1, 2);
		
		this.REDmFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.VersionFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mEngine.getTextureManager().loadTexture(this.REDmFontTexture);
		this.mEngine.getTextureManager().loadTexture(this.VersionFontTexture);
		this.REDmFont = new Font(this.getFontManager(), this.REDmFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC), 32, true, Color.RED);
		this.REDmFont.load();
		this.VersionFont = new Font(this.getFontManager(), this.VersionFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC), 15, true, Color.RED);
		this.VersionFont.load();
		
		
		for(int x = 0;x<tutorialpages;x++)
		{
			tutorialpictextureatlas[x].load();
		}
		this.calibrationwarningatlas.load();
		this.mBitmapTextureAtlas.load();//load texture from file to active memory
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onResume() {
	    super.onResume();
	   
	}

	@Override
	public void onPause() {
	    super.onPause();
	   
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(context);
	}
	
	
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception{
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mEngine.registerUpdateHandler(this);
		
		createHud();
		DrawTitleScene();
		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	private void DrawTitleScene() {
		/*
		int children = scene.getChildCount();                //.getChildScene().clearTouchAreas();         //.detachChildren();        //.clearChildScene();
		if(children>0)
		{
			Debug.d(""+children);
			scene.getChildScene().clearTouchAreas();
		}
		*/
		scene.clearChildScene();
		settings_editor.putBoolean("MenuDone", false);
		settings_editor.commit();

		scene.setBackground(new Background(Color.WHITE));
		
		
		 
        this.runOnUiThread(new Runnable(){
       	 
            public void run(){}
        });
		
        String app_ver = "";
		try {
			app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /* Calculate the coordinates for the logo, so its centered on the camera. */
		final int centerX = (int) (this.screenwidth  / 2);
		final int centerY = (int) (this.screenheight  / 2);
		
		Text VersionText = new Text(0, 0,this.VersionFont, (CharSequence)(app_ver), this.getVertexBufferObjectManager());
		VersionText.setVisible(true);
		scene.attachChild(VersionText);
		VersionText.setPosition(width - (VersionText.getWidth()+2),0);
		
		
		SSLogo = new Sprite(0,height - 64,SmithSquaredLogoRegion,this.getVertexBufferObjectManager()){
			//private boolean OnScreenControlsPref;

			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) 
				{
					if(touch2 == false)
					{
						touch2 = true;
					}
					else if(touch2 == true)
					{
						touch2 = false;
					}
					return true;
				}
				if(pSceneTouchEvent.isActionUp())
				{
					//Feedback();
					if(OnScreenControlsPref == false){
					OnScreenControlsPref = true;}
					else{OnScreenControlsPref = false;} 
					
					
					settings_editor.putBoolean("OnScreenControlsPref", OnScreenControlsPref);
					settings_editor.commit();

					return true;
				}
				return false;
			}
		};;

		AELogo = new Sprite(width-64,height - 64,AndengineLogoRegion,this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) 
				{
					
					uisoundbank.playSwishKill();
    				ModeChoice(TestMode);

				}
				if(pSceneTouchEvent.isActionUp())
				{
					return true;
				}
				return false;
			}
		};;

		this.calibrationwarning = new Sprite((int)(width/2)-128,(int)(height/2)-64,calibrationwarningtexture,this.getVertexBufferObjectManager());
		

		SSLogo.setVisible(true);
		AELogo.setVisible(true);
		splashimage = new Sprite(centerX, centerY, this.mSplashImageTextureRegion, this.getVertexBufferObjectManager());
		splashimage.setPosition(centerX - splashimage.getWidthScaled() /2, (centerY-30) -splashimage.getHeightScaled() /2);
		calibrationwarning.setVisible(false);
		bgscale = (float)((screenwidth)/splashimage.getWidth());


		this.fightHUD = new AnimatedSprite((int)centerX-100, (int)(this.screenheight)-100, this.startgametexture, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) 
				{
					if(selectionmade == false)
					{
						clearScreen();
						uisoundbank.playSwishKill();
						ModeSelect();
					}	
					return true;
				}
				if(pSceneTouchEvent.isActionUp())
				{
					return true;
				}
				return false;
			}
		};
		
		
		this.tutorialHUD = new AnimatedSprite((int)centerX-100, (int)(this.screenheight)-50, this.tutorialbuttontexture, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) 
				{
					if(selectionmade == false)
					{
						clearScreen();
						uisoundbank.playSwishKill();
						StartTutorial();
					}	
					return true;
				}
				if(pSceneTouchEvent.isActionUp())
				{
					return true;
				}
				return false;
			}
		};
		
		
/*
		this.menubuttonHUD = new AnimatedSprite((int)centerX-100, (int)(this.screenheight)-50, this.menubuttontexture, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionDown()) 
				{
					if(selectionmade == false)
					{
						uisoundbank.playHit();
						ShowMenu();
					}
					return true;
				}
				if(pSceneTouchEvent.isActionUp())
				{
					return true;
				}
				return false;
			}
		};
		
		*/
		//fightHUD.detachSelf();
		//menubuttonHUD.detachSelf();
		SSLogo.detachSelf();
		AELogo.detachSelf();
		this.mHud.attachChild(fightHUD);
		this.mHud.registerTouchArea(fightHUD);
		this.mHud.attachChild(tutorialHUD);
		this.mHud.registerTouchArea(tutorialHUD);
		
		//this.mHud.attachChild(this.menubuttonHUD);
		//this.mHud.registerTouchArea(this.menubuttonHUD);
		this.mHud.attachChild(SSLogo);
		this.mHud.registerTouchArea(SSLogo);
		this.mHud.attachChild(AELogo);
		this.mHud.registerTouchArea(AELogo);
		this.fightHUD.setCurrentTileIndex(0);
		this.tutorialHUD.setCurrentTileIndex(0);

		fightHUD.setVisible(false);
		tutorialHUD.setVisible(false);
		
		//this.menubuttonHUD.setCurrentTileIndex(0);

		//menubuttonHUD.setVisible(false);

		
		/* Create the logo and add it to the scene. */


		SequenceEntityModifier animation = new SequenceEntityModifier(//container for entity modifiers
				new DelayModifier(0.f),
				new ParallelEntityModifier(//container that runs modifiers simultaneously 
						new ScaleModifier(0.5f,0.01f,bgscale),
						new AlphaModifier(1.0f, 0,1)
						)
				//new DelayModifier(0.5f)


				);
		animation.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				StickmanSplash.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						uisoundbank.playHit();
						fightHUD.setVisible(true);
						tutorialHUD.setVisible(true);
						//menubuttonHUD.setVisible(true);
					}
				});
			}

			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				StickmanSplash.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						uisoundbank.playSwoosh();
					}
				});
			}
		});            
		splashimage.setAlpha(0);
		splashimage.setScale(1);
		splashimage.registerEntityModifier(animation); //register the modifier with the entity it will be animating        
		scene.attachChild(splashimage);
		

	}



	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();//can't forget this call back under Andengine gles2

	}

	@Override
	public void onUpdate(float pSecondsElapsed) {

		if(preferences.getBoolean("MenuDone", false) == true)
		{
			DrawTitleScene();
			
			
		}
		
		if(startcalibrationwarning ==true)
		{
			warningtimer++;

		}

		if (warningtimer == 10)
		{
			CalibrationWarning();
		}

		if (warningtimer ==200)
		{
			StartGame();
		}



	}
	

	
	void ShowMenu()
	{
		//TODO
		clearScreen();
		mHud.clearTouchAreas();
		localmenuscene.createMenuScene();
		mEngine.getScene().setChildScene(localmenuscene, false, true, true);
		
		
	}
	
	
	void Feedback()
	{
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

		String[] recipients = new String[]{"smithsquaredsoftware@gmail.com", "",};

		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);

		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "FEEDBACK: Stickman Vs Infinite Ninjas");

		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Your message:  ");

		emailIntent.setType("text/plain");

		startActivity(Intent.createChooser(emailIntent, "Send mail..."));

		finish();
		
	
		
	}
	
	
	
	
	String modeString(int prefSwitch)
	{

		/*--------------DEFINE MODES
		int Practice1on1 = 1;
		int PracticeVsBoxes = 2;
		int EasyMode = 3;
		int NormalMode = 4;
		int GamerMode = 5;*/
		String mode = "unset";
	
		if(prefSwitch == 1)
		{
			mode = "Practice1on1";
		}
		else if(prefSwitch == 2)
		{
			mode = "PracticeVsBoxes";
		}
		else if(prefSwitch == 3)
		{
			mode = "EasyMode";
		}
		else if(prefSwitch == 4)
		{
			mode = "NormalMode";
		}
		else if(prefSwitch == 5)
		{
			mode = "GamerMode";
		}
		
		else if(prefSwitch == 6)
		{
			mode = "StoryMode";
		}
		else if(prefSwitch == 7)
		{
			mode = "TestMode";
		}
		
		return mode;
	}
	
	
	
	void ModeChoice(int prefSwitch)
	{
		String Flurrymode = "Chose "+ modeString(prefSwitch);
		
		FlurryAgent.logEvent(Flurrymode);
	
		
		
		settings_editor.putInt("Mode",prefSwitch);
		settings_editor.commit();
		if(firstrun ==false)
		{
			StartGame();
		}
		else
		{
			PrepareFirstRun();
		}
	}
	
	
	
	void StartTutorial()
	{
		tutorialscene = new Scene();
		//scene.setChildScene(thisscene);
		this.mEngine.setScene(tutorialscene);
		
		tutorialscene.setBackground(new Background(Color.BLACK));
	
		Text tutorialinstructions;
		String Question = "TOUCH IMAGE TO CONTINUE";
		tutorialinstructions = new Text(0, 0,this.VersionFont, (CharSequence)(Question), this.getVertexBufferObjectManager());
		tutorialinstructions.setPosition((float)(width -tutorialinstructions.getWidth())/2, height - 50);
		tutorialscene.attachChild(tutorialinstructions);

		
		
		ShowTutorial(0,tutorialscene);
		
		  //PracticeModeButton
          //EasyModeButton
		
	}
	

	
	void ShowTutorial(final int x, final Scene scene)
	{
		
		if(x > 0)
		{
			scene.detachChild(tutorial[x-1]);
			scene.unregisterTouchArea(tutorial[x-1]);
		}
		
		if(x == tutorialpages)
		{
			//start over at beginning
			Debug.d("X is "+x);
			Intent intent = getIntent();
			finish();
			startActivity(intent);
			return;
		}
		tutorial[x] = new Sprite(0,0, this.tutorialpictexture[x], this.getVertexBufferObjectManager()){
	        @Override
	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	
	        	
	    			if(pSceneTouchEvent.isActionDown()) 
	    			{
	    				
	    				uisoundbank.playHit();
	    				ShowTutorial(x+1, scene);
	    				
	    				return true;
	    			
	    			}
	    			if(pSceneTouchEvent.isActionUp()){
	    				
	    				return true;
	    			}
	    		
	    		return false;
	                        }};
	
	                       scene.attachChild(tutorial[x]);
	             	  	   scene.registerTouchArea(tutorial[x]);
	             	  	tutorial[x].setPosition((float)(width -tutorial[x].getWidth())/2, (float)(height -tutorial[x].getHeight())/2);
	
	}
	
	
	void ModeSelect()
	{
		modescene = new Scene();
		//scene.setChildScene(thisscene);
		this.mEngine.setScene(modescene);
		
		modescene.setBackground(new Background(Color.BLACK));
		Text ModeQuestion;
		String Question = "SELECT MODE";
		ModeQuestion = new Text(0, 0,this.REDmFont, (CharSequence)(Question), this.getVertexBufferObjectManager());
		ModeQuestion.setPosition((float)(width -ModeQuestion.getWidth())/2, 30);
		modescene.attachChild(ModeQuestion);
		
		
		
		  //PracticeModeButton
          //EasyModeButton
		PracticeModeButton = new AnimatedSprite(0,0, this.PracticeModeButtontexture, this.getVertexBufferObjectManager()){
  	        @Override
  	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

  	        	
  	    			if(pSceneTouchEvent.isActionDown()) 
  	    			{
  	    				PracticeModeSelect();
  	    				uisoundbank.playSwishKill();
  	    				//PracticeModeButton.setCurrentTileIndex(1);
  	    				EasyModeButton.setCurrentTileIndex(1);
  	    				NormalModeButton.setCurrentTileIndex(1);
  	    				GamerModeButton.setCurrentTileIndex(1);
  	    				return true;
  	    			
  	    			}
  	    			if(pSceneTouchEvent.isActionUp()){
  	    				
  	    				return true;
  	    			}
  	    		
  	    		return false;
  	                        }};
  	                        
	      EasyModeButton = new AnimatedSprite(0,0, this.EasyModeButtontexture, this.getVertexBufferObjectManager()){
	        @Override
	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	
	        	
	    			if(pSceneTouchEvent.isActionDown()) 
	    			{
	    				//EasyModeButton.setCurrentTileIndex(1);
	    				PracticeModeButton.setCurrentTileIndex(1);
	    				NormalModeButton.setCurrentTileIndex(1);
	    				GamerModeButton.setCurrentTileIndex(1);
	    				uisoundbank.playSwishKill();
	    				ModeChoice(EasyMode);

	    				return true;
	    			
	    			}
	    			if(pSceneTouchEvent.isActionUp()){
	    				
	    				return true;
	    			}
	    		
	    		return false;
	                        }};

  	      NormalModeButton = new AnimatedSprite(0,0, this.NormalModeButtontexture, this.getVertexBufferObjectManager()){
  	        @Override
  	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
  	
  	        	
  	    			if(pSceneTouchEvent.isActionDown()) 
  	    			{
  	    				EasyModeButton.setCurrentTileIndex(1);
	    				PracticeModeButton.setCurrentTileIndex(1);
  	    				//NormalModeButton.setCurrentTileIndex(1);
  	    				GamerModeButton.setCurrentTileIndex(1);
  	    				uisoundbank.playSwishKill();
  	    				ModeChoice(NormalMode);
  	    				
  	    				return true;
  	    			
  	    			}
  	    			if(pSceneTouchEvent.isActionUp()){
  	    				
  	    				return true;
  	    			}
  	    		
  	    		return false;
  	                        }};  
            
  	      GamerModeButton= new AnimatedSprite(0,0, this.GamerModeButtontexture, this.getVertexBufferObjectManager()){
  	        @Override
  	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
  	
  	        	
  	    			if(pSceneTouchEvent.isActionDown()) 
  	    			{
  	    				EasyModeButton.setCurrentTileIndex(1);
	    				PracticeModeButton.setCurrentTileIndex(1);
  	    				NormalModeButton.setCurrentTileIndex(1);
  	    				//GamerModeButton.setCurrentTileIndex(1);
  	    				uisoundbank.playSwishKill();
  	    				ModeChoice(GamerMode);
  	    				
  	    				return true;
  	    			
  	    			}
  	    			if(pSceneTouchEvent.isActionUp()){
  	    				
  	    				return true;
  	    			}
  	    		
  	    		return false;
  	                        }};
	              	 	                        
  	       PracticeModeButton.setPosition((int)((width - PracticeModeButton.getWidth())/2), 70);
  	       EasyModeButton.setPosition((int)((width - EasyModeButton.getWidth())/2), 130);
  	       NormalModeButton.setPosition((int)((width - NormalModeButton.getWidth())/2), 190);
   	       GamerModeButton.setPosition((int)((width - GamerModeButton.getWidth())/2), 250);
   	    /*--------------DEFINE MODES
   		int Practice1on1 = 1;
   		int PracticeVsBoxes = 2;
   		int EasyMode = 3;
   		int NormalMode = 4;
   		int GamerMode = 5;*/
   	       
	  	   this.modescene.attachChild(this.PracticeModeButton);
	  	   this.modescene.registerTouchArea(this.PracticeModeButton);
	  	   this.PracticeModeButton.setCurrentTileIndex(0);
	  	   this.modescene.attachChild(this.EasyModeButton);
	  	   this.modescene.attachChild(this.NormalModeButton);
	  	   this.modescene.attachChild(this.GamerModeButton);
	  	   if((modeHistory>1)||(tutorialcompleted))
	  	   {
	  		 this.modescene.registerTouchArea(this.EasyModeButton);
		  	 this.EasyModeButton.setCurrentTileIndex(0);
		   }else{
			   this.EasyModeButton.setCurrentTileIndex(1);
		   }
	  	   
	  	 if(modeHistory>2)
	  	   {
	  		 this.modescene.registerTouchArea(this.NormalModeButton);
		  	 this.NormalModeButton.setCurrentTileIndex(0);
		   }else{
			   this.NormalModeButton.setCurrentTileIndex(1);
		   }
	  	if(modeHistory>3)
	  	   {
	  		 this.modescene.registerTouchArea(this.GamerModeButton);
		  	 this.GamerModeButton.setCurrentTileIndex(0);
		   }else{
			   this.GamerModeButton.setCurrentTileIndex(1);
		   }
	  	  
	  	
	     }
	

	

	void PracticeModeSelect()
	{
		practicemodescene = new Scene();
		//scene.setChildScene(thisscene);
		this.mEngine.setScene(practicemodescene);
		
		
		practicemodescene.setBackground(new Background(Color.BLACK));
		Text ModeQuestion;
		String Question = "SELECT PRACTICE MODE";
		ModeQuestion = new Text(0, 0,this.REDmFont, (CharSequence)(Question), this.getVertexBufferObjectManager());
		ModeQuestion.setPosition((float)(width -ModeQuestion.getWidth())/2, 40);
		practicemodescene.attachChild(ModeQuestion);
		
		
		
		  //Practice1on1Button
          //EasyModeButton
		Practice1on1Button = new AnimatedSprite(0,0, this.Practice1on1Buttontexture, this.getVertexBufferObjectManager()){
  	        @Override
  	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

  	        	
  	    			if(pSceneTouchEvent.isActionDown()) 
  	    			{
  	    				uisoundbank.playSwishKill();
  	    				ModeChoice(Practice1on1);
  	    				PracticeVsBoxesButton.setCurrentTileIndex(1);
  	    				return true;
  	    			
  	    			}
  	    			if(pSceneTouchEvent.isActionUp()){
  	    				
  	    				return true;
  	    			}
  	    		
  	    		return false;
  	                        }};
  	                        
	      PracticeVsBoxesButton = new AnimatedSprite(0,0, this.PracticeVsBoxesButtontexture, this.getVertexBufferObjectManager()){
	        @Override
	        public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	
	        	
	    			if(pSceneTouchEvent.isActionDown()) 
	    			{uisoundbank.playSwishKill();
	    				
	    				Practice1on1Button.setCurrentTileIndex(1);
	    				ModeChoice(PracticeVsBoxes);
	    				return true;
	    			
	    			}
	    			if(pSceneTouchEvent.isActionUp()){
	    				
	    				return true;
	    			}
	    		
	    		return false;
	                        }};
	             Practice1on1Button.setPosition((int)((width - Practice1on1Button.getWidth())/2), 120);
  	       PracticeVsBoxesButton.setPosition((int)((width - PracticeVsBoxesButton.getWidth())/2), 180);
	  	   this.practicemodescene.attachChild(this.Practice1on1Button);
	  	   this.practicemodescene.registerTouchArea(this.Practice1on1Button);
	  	   this.Practice1on1Button.setCurrentTileIndex(0);
	  	   this.practicemodescene.attachChild(this.PracticeVsBoxesButton);
	  	   this.practicemodescene.registerTouchArea(this.PracticeVsBoxesButton);
	  	   this.PracticeVsBoxesButton.setCurrentTileIndex(0);
	}

	
	
	
	void PrepareFirstRun()
	{
		settings_editor.putInt("previouspolls", 0);
		settings_editor.putInt("gamesplayed", 0);
		settings_editor.putInt("experience", 0);
		settings_editor.putInt("AxisSwitch", 0);
		settings_editor.putBoolean("HorizontalInvert", false);
		settings_editor.putBoolean("VerticalInvert", false);
		settings_editor.commit();
		startcalibrationwarning = true;
	}

	@Override
	public void reset() {


	}

	private TiledTextureRegion SetupTiledTexture(BuildableBitmapTextureAtlas newAtlas, int AtlasWidth, int AtlasHeight,  String ImageName, int Rows, int Columns)
	{

		TiledTextureRegion tiledTexture;
		newAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), AtlasWidth, AtlasHeight, TextureOptions.NEAREST);
		tiledTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(newAtlas, this, ImageName, Rows, Columns);
		try {
			newAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			newAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		return tiledTexture;
	}




	private void StartGame() {
		Intent intent = new Intent(StickmanSplash.this,  StickmanActivity.class);
		startActivity(intent);
		finish();
	}
	
	private void CalibrationWarning() {
		
		clearScreen();
		Scene warning = new Scene();
		mEngine.setScene(warning);
		modescene.setBackground(new Background(Color.BLACK));
		calibrationwarning.detachSelf();
		warning.attachChild(calibrationwarning);
		calibrationwarning.setVisible(true);

	}

	private void clearScreen()
	{
		AELogo.setVisible(false);
		SSLogo.setVisible(false);
		splashimage.setVisible(false);
		fightHUD.detachSelf();
		tutorialHUD.detachSelf();
		//menubuttonHUD.detachSelf();
		
		selectionmade = true;

	}



	private void createHud(){
		this.mHud = new HUD();
		this.mCamera.setHUD(mHud);

		this.mHud.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {

			}
		}));
	}
	

	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {

		

		if (pKeyCode == KeyEvent.KEYCODE_BACK
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(this, com.smiths.StickmanSplash.class);
			

			startActivity(intent);

			this.finish();

			return true;
		}
	
		return false;
	}

}



