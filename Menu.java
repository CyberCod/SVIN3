package com.smiths;


import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import org.andengine.util.debug.Debug;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import com.smiths.OurPhysics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.GLES20;
import android.preference.PreferenceManager;




public class Menu extends MenuScene  implements  IOnMenuItemClickListener{

	boolean gameispaused;
	
	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT = MENU_RESET + 1;
	protected static final int MENU_RECAL = MENU_QUIT +1;
	protected static final int MENU_MUTE = MENU_RECAL +1;
	AnimatedSpriteMenuItem resetMenuItem;
	AnimatedSpriteMenuItem quitMenuItem;
	AnimatedSpriteMenuItem recalMenuItem;
	AnimatedSpriteMenuItem muteMenuItem;
	AnimatedSpriteMenuItem axismode0MenuItem;
	AnimatedSpriteMenuItem axismode1MenuItem;
	AnimatedSpriteMenuItem axismode2MenuItem;
	AnimatedSpriteMenuItem axismode3MenuItem;
	AnimatedSpriteMenuItem axismode4MenuItem;
	AnimatedSpriteMenuItem axismode5MenuItem;
	AnimatedSpriteMenuItem axisselectionMenuItem;
	AnimatedSpriteMenuItem axisinvertMenuItem;
	AnimatedSpriteMenuItem axismodeXinvertMenuItem;
	AnimatedSpriteMenuItem axismodeYinvertMenuItem;
	private TiledTextureRegion mMenuQuitTextureRegion;
	
	private TiledTextureRegion mMenuRecalTextureRegion;
	private TiledTextureRegion mMenuMuteTextureRegion;

	private TiledTextureRegion axismode0TextureRegion;
	private TiledTextureRegion axismode1TextureRegion;
	private TiledTextureRegion axismode2TextureRegion;
	private TiledTextureRegion axismode3TextureRegion;
	private TiledTextureRegion axismode4TextureRegion;
	private TiledTextureRegion axismode5TextureRegion;
	
	private TiledTextureRegion axisselectionTextureRegion;
	private TiledTextureRegion axisinvertTextureRegion;
	private TiledTextureRegion axismodeXinvertTextureRegion;
	private TiledTextureRegion axismodeYinvertTextureRegion;
	boolean invertXaxis = false;
	boolean invertYaxis = false;
	int axisSwitch;
	
	protected MenuScene menuSceneAxisSelection;
	protected MenuScene menuSceneAxisInvert;
	private BuildableBitmapTextureAtlas menuButtonTextureAtlas;
	TextureManager texman;
	OurPhysics currentphysics;
	Intent soundservice;
	VertexBufferObjectManager VBO;
	Camera currentCamera;
	Context context;
	Activity currentActivity;
	
	private BuildableBitmapTextureAtlas mMenuQuitTexture;
	private BuildableBitmapTextureAtlas mMenuRecalTexture;
	private BuildableBitmapTextureAtlas mMenuResumeTexture;
	private BuildableBitmapTextureAtlas mMenuMuteTexture;
	private TiledTextureRegion mMenuResumeTextureRegion;
	
	private BuildableBitmapTextureAtlas axismode0Texture;
	private BuildableBitmapTextureAtlas axismode1Texture;
	private BuildableBitmapTextureAtlas axismode2Texture;
	private BuildableBitmapTextureAtlas axismode3Texture;
	private BuildableBitmapTextureAtlas axismode4Texture;
	private BuildableBitmapTextureAtlas axismode5Texture;
	private BuildableBitmapTextureAtlas axisselectionTexture;
	private BuildableBitmapTextureAtlas axisinvertTexture;
	private BuildableBitmapTextureAtlas axismodeXinvertTexture;
	private BuildableBitmapTextureAtlas axismodeYinvertTexture;
	
	
	
	SharedPreferences preferences;// = PreferenceManager.getDefaultSharedPreferences(this);

	SharedPreferences.Editor settings_editor;// = preferences.edit();


	public Menu(TextureManager texturemanager, Activity act, Intent soundserv, OurPhysics physics,  VertexBufferObjectManager vbo, Camera cam, Context con)
	{	
		super(cam);
		 soundservice = soundserv;
		 VBO = vbo;
		 texman = texturemanager;
		 currentphysics = physics;
		 currentCamera = cam;
		 context = con;
		 currentActivity = act;
		 
	}
	
	
	//@Override
	public void LoadMenuResources()
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		settings_editor = preferences.edit();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");//setting path for textures	
	this.mMenuResumeTexture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.mMenuQuitTexture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.mMenuRecalTexture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.mMenuMuteTexture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.menuButtonTextureAtlas = new BuildableBitmapTextureAtlas(texman, 60,60, TextureOptions.BILINEAR);
	this.axismode0Texture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.axismode1Texture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.axismode2Texture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.axismode3Texture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.axismode4Texture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.axismode5Texture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.axisselectionTexture = new BuildableBitmapTextureAtlas(texman, 205, 305, TextureOptions.BILINEAR);
	this.axisinvertTexture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.axismodeXinvertTexture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	this.axismodeYinvertTexture = new BuildableBitmapTextureAtlas(texman, 205, 105, TextureOptions.BILINEAR);
	  
	
	this.mMenuResumeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mMenuResumeTexture, context, "menu_reset.png", 1, 2);
	
	this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mMenuQuitTexture, context, "menu_quit.png", 1, 2);
	this.mMenuRecalTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mMenuRecalTexture, context, "menu_recalibrate.png", 1, 2);
	this.mMenuMuteTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mMenuMuteTexture, context, "menu_mute.png", 1, 2);
	
	this.axismode0TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.axismode0Texture, context, "axismode0.png", 1, 2);
	this.axismode1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.axismode1Texture, context, "axismode1.png", 1, 2);
	this.axismode2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.axismode2Texture, context, "axismode2.png", 1, 2);
	this.axismode3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.axismode3Texture, context, "axismode3.png", 1, 2);
	this.axismode4TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.axismode4Texture, context, "axismode4.png", 1, 2);
	this.axismode5TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.axismode5Texture, context, "axismode5.png", 1, 2);
	this.axisselectionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.axisselectionTexture, context, "axisselection.png", 1, 6);
	this.axisinvertTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.axisinvertTexture, context, "invertaxes.png", 1, 2);
	this.axismodeXinvertTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.axismodeXinvertTexture, context, "horizontal.png", 1, 2);
	this.axismodeYinvertTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.axismodeYinvertTexture, context, "vertical.png", 1, 2);
	
	
	try {
		this.mMenuResumeTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.mMenuResumeTexture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	try {
		this.mMenuQuitTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.mMenuQuitTexture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	} 
	try {
		this.mMenuRecalTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.mMenuRecalTexture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	} 
	try {
		this.mMenuMuteTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.mMenuMuteTexture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	try {
		this.menuButtonTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.menuButtonTextureAtlas.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	
	
	try {
		this.axismode0Texture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.axismode0Texture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	try {
		this.axismode1Texture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.axismode1Texture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	try {
		this.axismode2Texture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.axismode2Texture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	try {
		this.axismode3Texture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.axismode3Texture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	try {
		this.axismode4Texture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.axismode4Texture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	try {
		this.axismode5Texture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.axismode5Texture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	try {
		this.axisselectionTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.axisselectionTexture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	try {
		this.axisinvertTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.axisinvertTexture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	try {
		this.axismodeXinvertTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.axismodeXinvertTexture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	try {
		this.axismodeYinvertTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
		this.axismodeYinvertTexture.load();
	} catch (TextureAtlasBuilderException e) {
		Debug.e(e);
	}
	
	
	
	
}
	
	
	IAnimationListener buttonanimationlistener= new IAnimationListener(){
		

		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			resetMenuItem.stopAnimation(0);
			//getChildScene().clearTouchAreas();
			clearChildScene();
			//menuSceneAxisInvert.
			clearTouchAreas();
			gameispaused = false;
			Debug.d("trying to resume");
			setOnMenuItemClickListener(null);
			
			//closeMenuScene();
			back();
			settings_editor.putBoolean("MenuDone", true);
			settings_editor.commit();

		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {


		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {


		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {


		}

	};


	IAnimationListener buttonanimationlistener1= new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			quitMenuItem.stopAnimation(0);
			Debug.d("trying to quit");
			
			currentActivity.finish();
		}

		

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {


		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {


		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {


		}

	};

	IAnimationListener buttonanimationlistener2= new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			recalMenuItem.stopAnimation(0);
			if(currentphysics!=null)
			{
				currentphysics.calibrateaccellerometers = true;
			}


		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {


		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {


		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {


		}

	};


	IAnimationListener buttonanimationlistener3= new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			muteMenuItem.stopAnimation(0);
			if (preferences.getBoolean("mute", false)== true){
				settings_editor.putBoolean("mute",false);
				settings_editor.commit();
			}
			else if (preferences.getBoolean("mute", false)== false){
				settings_editor.putBoolean("mute",true);
				settings_editor.commit();
			}
			ToggleMusic();

		}

		
		
		


		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {


		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {


		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {


		}

	};

	

	IAnimationListener buttonanimationlistener4= new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			createAxisMenuScene();
			setChildScene(menuSceneAxisSelection, false, true, true);
			axismode0MenuItem.setCurrentTileIndex(0);
			axismode1MenuItem.setCurrentTileIndex(0);
			axismode2MenuItem.setCurrentTileIndex(0);
			axismode3MenuItem.setCurrentTileIndex(0);
			axismode4MenuItem.setCurrentTileIndex(0);
			axismode5MenuItem.setCurrentTileIndex(0);
			switch(axisSwitch){
			
			case 0:
				axismode0MenuItem.setCurrentTileIndex(1);
				break;
			case 1:
				axismode1MenuItem.setCurrentTileIndex(1);
				break;
			case 2:
				axismode2MenuItem.setCurrentTileIndex(1);
				break;
			case 3:
				axismode3MenuItem.setCurrentTileIndex(1);
				break;
			case 4:
				axismode4MenuItem.setCurrentTileIndex(1);
				break;
			case 5:
				axismode5MenuItem.setCurrentTileIndex(1);
				break;
				
			}
			
			
			
		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {
		}

	};

	


	IAnimationListener buttonanimationlistener5= new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			
			SelectAxis(0);
		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {
		}

	};



	IAnimationListener buttonanimationlistener6= new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			
			SelectAxis(1);
		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {
		}

	};



	IAnimationListener buttonanimationlistener7= new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			
			SelectAxis(2);
		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {
		}

	};



	IAnimationListener buttonanimationlistener8= new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			
			SelectAxis(3);
		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {
		}

	};



	IAnimationListener buttonanimationlistener9= new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			
			SelectAxis(4);
		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {
		}

	};



	IAnimationListener buttonanimationlistener10= new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			
			SelectAxis(5);
		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {
		}

	};

	
	

	IAnimationListener buttonanimationlistener11= new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			
			createAxisInvertMenuScene();
			setChildScene(menuSceneAxisInvert, false, true, true);
		
			if(invertXaxis == true)
			{
				axismodeXinvertMenuItem.setCurrentTileIndex(1);
			}
			else
			{
				axismodeXinvertMenuItem.setCurrentTileIndex(0);
			}
			
			
			if(invertYaxis == true)
			{
				axismodeYinvertMenuItem.setCurrentTileIndex(1);
			}
			else
			{
				axismodeYinvertMenuItem.setCurrentTileIndex(0);
			}	
			
			
		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {
		}

	};

	

	IAnimationListener buttonanimationlistener12 = new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			
			
			if(invertXaxis == true)
			{
				invertXaxis = false;
				axismodeXinvertMenuItem.setCurrentTileIndex(0);
				settings_editor.putBoolean("HorizontalInverted", false);
			}
			else
			{
				invertXaxis = true;
				axismodeXinvertMenuItem.setCurrentTileIndex(1);
				settings_editor.putBoolean("HorizontalInverted", true);
			}
			settings_editor.commit();
			menuSceneAxisInvert.back();
			
			
		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {
		}

	};

	

	IAnimationListener buttonanimationlistener13 = new IAnimationListener(){
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			
			
			if(invertYaxis == true)
			{
				invertYaxis = false;
				axismodeYinvertMenuItem.setCurrentTileIndex(0);
				settings_editor.putBoolean("VerticalInverted", false);
			}
			else
			{
				invertYaxis = true;
				axismodeYinvertMenuItem.setCurrentTileIndex(1);
				settings_editor.putBoolean("VerticalInverted", true);
			}
			settings_editor.commit();
			menuSceneAxisInvert.back();
			
		}

		@Override
		public void onAnimationStarted(
				AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(
				AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex,
				int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(
				AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount,
				int pInitialLoopCount) {
		}

	};

	
	
	
	
	
	


	
	
	
	private void ToggleMusic() {

		if (preferences.getBoolean("mute", false) == true)
		{
			if(soundservice!=null)
			{
				currentActivity.stopService(soundservice);
			}
		}

		if (preferences.getBoolean("mute", false) == false)
		{
			if(soundservice!=null)
			{
			currentActivity.startService(soundservice);
			}
		}
	}
	
	
	void SelectAxis(int Axis)
	{
		axisSwitch = Axis;
		if(currentphysics!=null)
		{
			currentphysics.calibrateaccellerometers = true;
		}
		axisselectionMenuItem.setCurrentTileIndex(axisSwitch);
		settings_editor.putInt("AxisSwitch", Axis);
		settings_editor.commit();
		menuSceneAxisSelection.back();
	}
	
	public void createMenuScene() {
		
		
		resetMenuItem = new AnimatedSpriteMenuItem(0, this.mMenuResumeTextureRegion, VBO);
		resetMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		resetMenuItem.setCurrentTileIndex(0);
		this.addMenuItem(resetMenuItem);

		quitMenuItem = new AnimatedSpriteMenuItem(1, this.mMenuQuitTextureRegion, VBO);
		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		quitMenuItem.setCurrentTileIndex(0);
		this.addMenuItem(quitMenuItem);

		recalMenuItem = new AnimatedSpriteMenuItem(2, this.mMenuRecalTextureRegion, VBO);
		recalMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		recalMenuItem.setCurrentTileIndex(0);
		this.addMenuItem(recalMenuItem);

		muteMenuItem = new AnimatedSpriteMenuItem(3, this.mMenuMuteTextureRegion, VBO);
		muteMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		muteMenuItem.setCurrentTileIndex(0);
		this.addMenuItem(muteMenuItem);
		

		axisselectionMenuItem = new AnimatedSpriteMenuItem(4, this.axisselectionTextureRegion, VBO);
		axisselectionMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axisselectionMenuItem.setCurrentTileIndex(axisSwitch);
		this.addMenuItem(axisselectionMenuItem);
		
		axisinvertMenuItem = new AnimatedSpriteMenuItem(11, this.axisinvertTextureRegion, VBO);
		axisinvertMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axisinvertMenuItem.setCurrentTileIndex(0);
		this.addMenuItem(axisinvertMenuItem);

		this.buildAnimations();

		this.setBackgroundEnabled(true);

		this.setOnMenuItemClickListener(this);
	}

	private void createAxisInvertMenuScene() {
		
		this.menuSceneAxisInvert = new MenuScene(this.currentCamera);
	
		axismodeXinvertMenuItem = new AnimatedSpriteMenuItem(12, this.axismodeXinvertTextureRegion, VBO);
		axismodeXinvertMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismodeXinvertMenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisInvert.addMenuItem(axismodeXinvertMenuItem);

		
		axismodeYinvertMenuItem = new AnimatedSpriteMenuItem(13, this.axismodeYinvertTextureRegion, VBO);
		axismodeYinvertMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismodeYinvertMenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisInvert.addMenuItem(axismodeYinvertMenuItem);
		
		
		
		
		this.menuSceneAxisInvert.buildAnimations();

		this.menuSceneAxisInvert.setBackgroundEnabled(true);

		this.menuSceneAxisInvert.setOnMenuItemClickListener(this);
	}


	
	

	private void createAxisMenuScene() {
		
		this.menuSceneAxisSelection = new MenuScene(this.currentCamera);
		/*
		axismodeXinvertMenuItem;
		axismodeYinvertMenuItem;
		*/
		axismode0MenuItem = new AnimatedSpriteMenuItem(5, this.axismode0TextureRegion, VBO);
		axismode0MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode0MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode0MenuItem);

		
		axismode1MenuItem = new AnimatedSpriteMenuItem(6, this.axismode1TextureRegion, VBO);
		axismode1MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode1MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode1MenuItem);
		
		
		axismode2MenuItem = new AnimatedSpriteMenuItem(7, this.axismode2TextureRegion, VBO);
		axismode2MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode2MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode2MenuItem);
		
		
		axismode3MenuItem = new AnimatedSpriteMenuItem(8, this.axismode3TextureRegion, VBO);
		axismode3MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode3MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode3MenuItem);
		
		
		axismode4MenuItem = new AnimatedSpriteMenuItem(9, this.axismode4TextureRegion, VBO);
		axismode4MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode4MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode4MenuItem);
		
		
		axismode5MenuItem = new AnimatedSpriteMenuItem(10, this.axismode5TextureRegion, VBO);
		axismode5MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode5MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode5MenuItem);
		
		menuSceneAxisSelection.setParent(this);
		
		
		this.menuSceneAxisSelection.buildAnimations();

		this.menuSceneAxisSelection.setBackgroundEnabled(true);

		this.menuSceneAxisSelection.setOnMenuItemClickListener(this);
	}

	

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
		//rename for animation listeners?
		case 0:
			resetMenuItem.animate(100, 1, this.buttonanimationlistener);
			Debug.d("resume button pushed");
			return true;

		case 1:
			quitMenuItem.animate(100, 1, this.buttonanimationlistener1);
			Debug.d("quit button pushed");
			return true;

		case 2:
			recalMenuItem.animate(100, 1, this.buttonanimationlistener2);
			Debug.d("recalibrate button pushed");
			return true;

		case 3:
			muteMenuItem.animate(100, 1, this.buttonanimationlistener3);
			Debug.d("music toggle pushed");
			return true;

		case 4:
			axisselectionMenuItem.animate(0, 0, this.buttonanimationlistener4);
			Debug.d("Axis Selection XYZ menu opened");
			return true;

		case 5:
			axismode0MenuItem.animate(100, 1, this.buttonanimationlistener5);
			Debug.d("XYZ button 1");
			return true;
			
		case 6:
			axismode1MenuItem.animate(100, 1, this.buttonanimationlistener6);
			Debug.d("XYZ button 2");
			return true;
			
		case 7:
			axismode2MenuItem.animate(100, 1, this.buttonanimationlistener7);
			Debug.d("XYZ button 3");
			return true;
			
		case 8:
			axismode3MenuItem.animate(100, 1, this.buttonanimationlistener8);
			Debug.d("XYZ button 4");
			return true;
			
		case 9:
			axismode4MenuItem.animate(100, 1, this.buttonanimationlistener9);
			Debug.d("XYZ button 5");
			return true;
			
		case 10:
			axismode5MenuItem.animate(100, 1, this.buttonanimationlistener10);
			Debug.d("XYZ button 6");
			return true;
			
		case 11:
			axisinvertMenuItem.animate(100, 1, this.buttonanimationlistener11);     
			Debug.d("invert axes button pushed");
			return true;
			
		case 12:
			axismodeXinvertMenuItem.animate(100, 1, this.buttonanimationlistener12);
			Debug.d("invert axis 1");
			return true;
			
		case 13:
			axismodeYinvertMenuItem.animate(100, 1, this.buttonanimationlistener13);
			Debug.d("invert axis 2");
			return true;
			
			
		
			
			
		default:
			return false;
		}
	}



	
	
	
}