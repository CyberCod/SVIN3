package com.smiths;



import org.andengine.engine.Engine;






import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;

//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
import android.content.Context;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.options.EngineOptions;
//import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
//import org.andengine.entity.scene.background.ColorBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.input.touch.detector.SurfaceGestureDetectorAdapter;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import com.badlogic.gdx.math.Vector2;
import com.flurry.android.FlurryAgent;
import com.smiths.ParallaxLayer;
import com.smiths.ParallaxLayer.ParallaxEntity;
import com.smiths.R;
import com.smiths.Shuriken;
import com.smiths.Shurikenpool;
import com.smiths.Arrow;
import com.smiths.Arrowpool;


//import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Toast;

public class StickmanActivity extends BaseGameActivity implements
		IUpdateHandler, IOnMenuItemClickListener, IAccelerationListener,
		IOnAreaTouchListener {

	boolean debugging = false;
	private boolean paid = false;
	String packagename;
	boolean backbuttonpress1 = false;
	boolean HadokenFuryStart = false;
	boolean menuOpen = false;
	/*
	 * String parallax1 = "flatcity1.png"; String parallax2 = "flatcity2.png";
	 * String parallax3 = "flatcity3.png"; String parallaxclouds =
	 * "flatcityclouds.png";
	 */
	String parallax1;
	String parallax2;
	String parallax3;
	String parallaxclouds;
	AnalogOnScreenControl BowAOSC;
	AnalogOnScreenControl MovementAOSC;
	float oldAnalogX;
	float oldAnalogY;
	int exp = 0;
	int money = 0;
	int backbuttontimer = 0;
	int moneyModifier = 1; // change this with buffs to increase coin value
	boolean hadokenPiercing = true; // this buff allows hadokens to continue
									// after hitting powerups
	boolean midasHadokens = false; // this buff enables full health to be able
									// to switch to gold
	int currenttipnum;
	private int ChargeTimer = 0;
	private int chargetime = 15;
	private float shurikenHPratio = 2;
	private float chargedelayratio = (float) 0.7; // adjust this to tweak the
													// charge delay - it goes
													// down with level increase
													// anyway
	private ParallaxLayer backgroundParallax;
	SurfaceGestureDetectorAdapter SGDA;
	Context context;
	// DEBUGGING MODES
	String mode;
	int Mode = 0;
	FPSLogger fps;
	boolean swarmenabled = false;
	boolean AllAlone = false;
	boolean shurikentouch = false;
	private boolean BossSpawned = false;
	boolean debug = false;
	float AnalogStickRatio = 5f;    
	
	int startinglevel = 2;
	// --------------- DEFINE MODES
	private int NinjaFrenzyLimit = 300;     //NINJA FRENZY TIMER LIMIT HERE <-------
	int highestwavecompleted = 0;
	int Practice1on1 = 1;
	int PracticeVsBoxes = 2;
	int EasyMode = 3;
	int NormalMode = 4;
	int GamerMode = 5;
	int StoryMode =  6;
	int TestMode = 7;
	

	float downX = 0;
	float downY = 0;
	float upX = 0;
	float upY = 0;
	float diffX;
	float diffY;
	AnimatedSprite stickmanDualSwords; // stickDualSwordsTextureAtlas;
	AnimatedSprite stickmanStaff; // stickStaffTextureAtlas;
	AnimatedSprite stickmanBow; // stickBowTextureAtlas;

	Text BoxText;
	Text ShurikenCountText;
	Text SmokebombCountText;

	Rectangle[] Target;
	Boolean[] TargetActivated;
	int BoxesActive = 0;
	private int cameradistance = 100;
	float Yoffset;
	int[] pastdirections = new int[6];
	int directiontimer = 0;
	int[] movetimer = new int[6];
	boolean invertXaxis = false;
	boolean invertYaxis = false;
	int axisSwitch;
	int playLevel = 0;
	Boolean DamageOFF = false;
	private float screenwidth;
	private float screenheight;
	int screenscale = 300; // this is the scaled length of the shorter edge of
							// the screen
	private int intensity = 20;
	private int totalninjasleft = 20;
	private int totalninjasforlevel = 20;
	boolean SFX = true;
	// THIS IS WHERE YOU CHANGE POWERUP ORDER
	public int isBlank = 0;
	public int isExtraNinja = 1;
	public int isHealth = 2;
	public int isShurikensPowerUp = 3;
	public int isSmokebomb = 4;
	public int isArrows = 5;
	public int isSlowmo = 6;
	public int isSuperHealth = 7;
	public int isHadokenBuff = 8;
	public int isLevelUp = 9;
	public int isFrenzy = 10;
	
	public int isCopperCoin = 11;
	public int isSilverCoin = 12;
	public int isGoldCoin = 13;
	public int isTopaz = 14;
	public int isAmnethyst = 15;
	public int isEmerald = 16;
	public int isRuby = 17;
	public int isSaphire = 18;
	public int isBlackPearl = 19;
	public int isDiamond = 20;
	public int isChest = 21;
	public int isKey = 22;
	public int isHat  = 23;
	public int isShoes = 24;
	public int isGloves = 25;	
	public int isShirt = 26;
	public int isPants = 27;
	public int isFace = 28;
	public int isWings = 29;
	
	private boolean slowmoskip = false;
	private int width;
	private int height;
	private int halfwidth;
	private int halfheight;

	private Camera mCamera;
	private Display display;
	FPSCounter fpsCounter;

	VertexBufferObjectManager VBO;
	private HUD mHud;
	private Font mFont;
	private Font tooltipFont;
	private Font countersFont;
	Soundbank soundbank = new Soundbank();
	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT = MENU_RESET + 1;
	protected static final int MENU_RECAL = MENU_QUIT + 1;
	protected static final int MENU_MUTE = MENU_RECAL + 1;
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
	private ArrayList<Rectangle> horizontals = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> verticals = new ArrayList<Rectangle>();
	// private ArrayList<PowerUp> PowerUps = new ArrayList<PowerUp>();

	// private ArrayList<Shuriken> Shurikens = new ArrayList<Shuriken>() ;
	// private ArrayList<Angel> Angels = new ArrayList<Angel>();
	int[] healthicon = new int[1];
	int[] arrowsicon = new int[1];
	int[] bighealthicon = new int[1];
	int[] hadokenbuff = new int[1];
	int[] slomoicon = new int[1];
	int[] smokebomb = new int[1];
	int[] levelup = new int[1];
	int[] shurikens = new int[1];
	int[] coppercoin = new int[1];
	int[] silvercoin = new int[1];
	int[] goldcoin = new int[1];
	int[] topaz = new int[1];
	int[] amnethyst = new int[1];
	int[] emerald = new int[1];
	int[] ruby = new int[1];
	int[] saphire = new int[1];
	int[] diamond = new int[1];
	int[] blackpearl = new int[1];
	int[] chestclosed = new int[1];
	
	int[] chestopen = new int[2];
	int[] key = new int[1];
	int[] explosion = new int[8];
	int[] blank = new int[1];
	int[] frenzy = new int[1];
	int[] bigninjaface = new int[1];
	int[] lilninjaface = new int[1];
	int[] hatsitem = new int[1];
	int[] glovesitem = new int[1];
	int[] pantsitem = new int[1];
	int[] shoesitem = new int[1];
	int[] facesitem = new int[1];
	int[] shirtsitem = new int[1];
	int[] wingsitem = new int[1];
	
	int[] lilbomb = new int[1];
	
	

	int[] hadokenstart = new int[4];
	int[] hadokenfly = new int[4];
	int[] hadokenhit = new int[5];

	int[] corpsel = new int[1];
	int[] corpser = new int[1];
	int[] shurikenl = new int[4];
	int[] shurikenr = new int[4];
	int[] FX8count = new int[8];
	int[] thrownswordl = new int[1];
	int[] thrownswordr = new int[1];
	int[] groundedsword = new int[1];
	int[] wallstucksword = new int[1];
	int[] angel = new int[10];

	long[] oneFrame = new long[1];
	long[] twoFrame = new long[2];
	long[] threeFrame = new long[3];
	long[] fourFrame = new long[4];
	long[] fiveFrame = new long[5];
	long[] sixFrame = new long[6];
	long[] sevenFrame = new long[7];
	long[] eightFrame = new long[8];
	long[] eightFrameFAST = new long[8];
	long[] eightFrameSLOW = new long[8];
	long[] eightFrameVARIABLE = new long[8];

	long[] hadokenfiveFrame = new long[5];
	long[] hadokenfourFrame = new long[4];
	long[] tenFrame = new long[10];

	private Scene scene;
//	private SceneManager SM;
	protected MenuScene mMenuScene;
	protected MenuScene menuSceneAxisSelection;
	protected MenuScene menuSceneAxisInvert;

	boolean ceilingcollision = false;

	private Ninjapool NinjaPool;
	private LinkedList<GameCharacter> ninjaslist;

	private Ninjapool BossPool;

	private Poweruppool PowerUpPool;
	private LinkedList<PowerUp> poweruplist;

	private Shurikenpool ShurikenPool;
	private LinkedList<Shuriken> shurikenlist;
	private LinkedList<Shuriken> enemyshurikenlist;

	private LinkedList<Arrow> arrowlist;
	private LinkedList<Arrow> enemyarrowlist;
	private Arrowpool ArrowPool;

	private Hadokenpool HadokenPool;
	private SmokePuffPool smokepuffpool;

	private LinkedList<Hadoken> hadokenlist;
	private LinkedList<SmokePuff> smokepufflist;

	private Swordpool SwordPool;
	private LinkedList<Sword> swordlist;

	private Angelpool AngelPool;
	private LinkedList<Angel> angellist;

	private Corpsepool CorpsePool;
	private LinkedList<Corpse> corpselist;

	private ITextureRegion GameOverTex;
	private ITextureRegion flatcity1TEX;
	private ITextureRegion flatcity2TEX;
	private ITextureRegion flatcity3TEX;
	private ITextureRegion flatcityCloudsTEX;
	
	private ITextureRegion blood1TEX;
	private ITextureRegion blood2TEX;
	private ITextureRegion blood3TEX;
	private ITextureRegion blood4TEX;
	private ITextureRegion blood5TEX;
	private ITextureRegion blood6TEX;
	private ITextureRegion blood7TEX;
	private ITextureRegion mMenuResetTextureRegion;
	private TiledTextureRegion mMenuQuitTextureRegion;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TextureRegion mFaceTextureRegion;

	private BitmapTextureAtlas mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;

	private BitmapTextureAtlas mFontTexture;
	private BitmapTextureAtlas tooltipFontTexture;
	private BitmapTextureAtlas countersFontTexture;
	private BitmapTextureAtlas mMenuTexture;
	private BitmapTextureAtlas GOTexture;
	private BitmapTextureAtlas flatcity1Texture;
	private BitmapTextureAtlas flatcity2Texture;
	private BitmapTextureAtlas flatcity3Texture;
	private BitmapTextureAtlas flatcityCloudsTexture;

	private BitmapTextureAtlas blood1Texture;
	private BitmapTextureAtlas blood2Texture;
	private BitmapTextureAtlas blood3Texture;
	private BitmapTextureAtlas blood4Texture;
	private BitmapTextureAtlas blood5Texture;
	private BitmapTextureAtlas blood6Texture;
	private BitmapTextureAtlas blood7Texture;
	
	
	
	
	private BuildableBitmapTextureAtlas stickTextureAtlas;
	private BuildableBitmapTextureAtlas stickDualSwordsTextureAtlas;
	private BuildableBitmapTextureAtlas stickDualSwordspinTextureAtlas;
	private BuildableBitmapTextureAtlas stickStaffTextureAtlas;
	private BuildableBitmapTextureAtlas stickBowTextureAtlas;
	private BuildableBitmapTextureAtlas ninjaTextureAtlas;
	private BuildableBitmapTextureAtlas bossTextureAtlas;
	private BuildableBitmapTextureAtlas HudDoodadsTextureAtlas;
	private BuildableBitmapTextureAtlas ArrowTextureAtlas;
	private BuildableBitmapTextureAtlas ShurikenTextureAtlas;
	private BuildableBitmapTextureAtlas HadokenTextureAtlas;
	private BuildableBitmapTextureAtlas SwordTextureAtlas;
	private BuildableBitmapTextureAtlas CorpsesTextureAtlas;
	private BuildableBitmapTextureAtlas AngelsTextureAtlas;
	private BuildableBitmapTextureAtlas FXSmokepuffTextureAtlas;
	private BuildableBitmapTextureAtlas FXSidepuffsTextureAtlas;
	private BuildableBitmapTextureAtlas FXLevelUpTextureAtlas;
	private BuildableBitmapTextureAtlas FXSmokeBombPickupTextureAtlas;
	private BuildableBitmapTextureAtlas FXSlowMoTextureAtlas;
	private BuildableBitmapTextureAtlas FXLevelCompleteTextureAtlas;
	private BuildableBitmapTextureAtlas FXShurikengrabTextureAtlas;
	private BuildableBitmapTextureAtlas FXHealthUpTextureAtlas;
	private BuildableBitmapTextureAtlas FXNewNinjaTextureAtlas;
	private TiledTextureRegion stickmantex;
	private TiledTextureRegion stickmanDualSwordstex;
	private TiledTextureRegion stickmanDualSwordspintex;
	private TiledTextureRegion stickmanStafftex;
	private TiledTextureRegion stickmanBowtex;
	private TiledTextureRegion ninjatexture1;
	private TiledTextureRegion ninjatexture2;
	private TiledTextureRegion HudDoodadsTexture;
	private TiledTextureRegion ArrowTexture;
	private TiledTextureRegion AngelsTexture;
	private TiledTextureRegion CorpsesTexture;
	private TiledTextureRegion ShurikenTexture;
	private TiledTextureRegion HadokenTexture;
	private TiledTextureRegion SwordTexture;
	private TiledTextureRegion FXHealthUpTexture;
	private TiledTextureRegion FXNewNinjaTexture;
	private TiledTextureRegion FXLevelUpTexture;
	private TiledTextureRegion FXSmokeBombPickupTexture;
	private TiledTextureRegion FXSlowMoTexture;
	private TiledTextureRegion FXShurikengrabTexture;
	private TiledTextureRegion FXSidepuffsTexture;
	private TiledTextureRegion FXSmokepuffTexture;
	private TiledTextureRegion FXLevelCompleteTexture;

	Text ComboText;
	Text StreakText;
	Text ScoreAddText;
	Text LevelCompleteText;
	private Text bankText;
	private Text PopupText;

	private GameCharacter stickman;
	private Sprite Backgroundsprite;
	private SpriteBackground bg;
	private Sprite flatcity1Sprite;
	private Sprite flatcity2Sprite;
	private Sprite flatcity3Sprite;
	private Sprite flatcityCloudsSprite;
	private SpriteBackground flatcityCloudsBG;

	

	private Sprite blood1Sprite;
	private Sprite blood2Sprite;
	private Sprite blood3Sprite;
	private Sprite blood4Sprite;
	private Sprite blood5Sprite;
	private Sprite blood6Sprite;
	private Sprite blood7Sprite;
	
	ArrayList<Integer> bloodlist;
	int bloodcounter = 0;
	int bloodtimer;
	
	
	private GameObject Levelupsprite;
	private GameObject Smokebombpickupsprite;
	private GameObject SlowMosprite;
	private GameObject Shurikengrabsprite;
	private GameObject Healthupsprite;

	private GameObject Newninjasprite;
	private float accellerometerSpeedX;
	private float accellerometerSpeedY;
	private float accellerometerSpeedZ;

	int accelX;
	int accelY;

	private boolean zflip;

	float velocity_y;
	float velocity_x;
	int jumplevel = -15;
	boolean jumptrigger;
	int gravity = 5;
	boolean collision = false;
	boolean falling;
	boolean rising;
	boolean currentlyjumping;

	int feet = 0;
	int body = 1;
	int head = 2;

	private OurPhysics Physics;
	EngineOptions engineOptions;
	Animator animator;
	Rectangle camerabox;
	GameObject FXbox;
	private boolean runbroken = true;

	final int up = -1;
	final int down = 1;
	final int left = -1;
	final int right = 1;
	int[] ninjaCorpseflinchl = new int[3];
	int[] ninjaCorpseflinchr = new int[3];
	int[] ninjadeathl = new int[1];
	int[] ninjadeathr = new int[1];

	boolean tips;
	int tipcounter = 0;
	//private AnimatedSprite healthhud;
	private Intent soundservice;
	private boolean gameispaused;
	//private Text healtext;
	private AnimatedSprite shurikenhud;
	private AnimatedSprite AttackHUDButton;
	private AnimatedSprite BowHUDButton;
	private AnimatedSprite swordthrowhud;
	private AnimatedSprite smokebombhud;
	private AnimatedSprite addninjahud;

	private int hudtimer;

	// private GameObject FXSmokePuffInstance;

	private GameObject FXGroundPoundInstance;
	GameCharacter CurrentNinja;

	private Rectangle ChaseBlock;
	private boolean StopSpawningNinjas = false;
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

	private int gamelevel = 1;

	// defining preferences
	SharedPreferences preferences;// =
									// PreferenceManager.getDefaultSharedPreferences(this);

	SharedPreferences.Editor settings_editor;// = preferences.edit();

	IAnimationListener buttonanimationlistener = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			resetMenuItem.stopAnimation(0);

			scene.clearChildScene();
			gameispaused = false;
			mMenuScene.reset();

		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {

		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {

		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {

		}

	};

	IAnimationListener buttonanimationlistener1 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			quitMenuItem.stopAnimation(0);

			finish();
		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {

		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {

		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {

		}

	};

	IAnimationListener buttonanimationlistener2 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			recalMenuItem.stopAnimation(0);
			Physics.calibrateaccellerometers = true;

		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {

		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {

		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {

		}

	};

	IAnimationListener buttonanimationlistener3 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			muteMenuItem.stopAnimation(0);
			if (preferences.getBoolean("mute", false) == true) {
				settings_editor.putBoolean("mute", false);
				settings_editor.commit();
			} else if (preferences.getBoolean("mute", false) == false) {
				settings_editor.putBoolean("mute", true);
				settings_editor.commit();
			}
			ToggleMusic();

		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {

		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {

		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {

		}

	};

	IAnimationListener buttonanimationlistener4 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

			mMenuScene.setChildScene(menuSceneAxisSelection, false, true, true);
			axismode0MenuItem.setCurrentTileIndex(0);
			axismode1MenuItem.setCurrentTileIndex(0);
			axismode2MenuItem.setCurrentTileIndex(0);
			axismode3MenuItem.setCurrentTileIndex(0);
			axismode4MenuItem.setCurrentTileIndex(0);
			axismode5MenuItem.setCurrentTileIndex(0);
			switch (axisSwitch) {

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
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {
		}

	};

	IAnimationListener buttonanimationlistener5 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

			SelectAxis(0);
		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {
		}

	};

	IAnimationListener buttonanimationlistener6 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

			SelectAxis(1);
		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {
		}

	};

	IAnimationListener buttonanimationlistener7 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

			SelectAxis(2);
		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {
		}

	};

	IAnimationListener buttonanimationlistener8 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

			SelectAxis(3);
		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {
		}

	};

	IAnimationListener buttonanimationlistener9 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

			SelectAxis(4);
		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {
		}

	};

	IAnimationListener buttonanimationlistener10 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

			SelectAxis(5);
		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {
		}

	};

	IAnimationListener buttonanimationlistener11 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

			mMenuScene.setChildScene(menuSceneAxisInvert, false, true, true);

			if (invertXaxis == true) {
				axismodeXinvertMenuItem.setCurrentTileIndex(1);
			} else {
				axismodeXinvertMenuItem.setCurrentTileIndex(0);
			}

			if (invertYaxis == true) {
				axismodeYinvertMenuItem.setCurrentTileIndex(1);
			} else {
				axismodeYinvertMenuItem.setCurrentTileIndex(0);
			}

		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {
		}

	};

	IAnimationListener buttonanimationlistener12 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

			if (invertXaxis == true) {
				invertXaxis = false;
				axismodeXinvertMenuItem.setCurrentTileIndex(0);
				settings_editor.putBoolean("HorizontalInverted", false);
			} else {
				invertXaxis = true;
				axismodeXinvertMenuItem.setCurrentTileIndex(1);
				settings_editor.putBoolean("HorizontalInverted", true);
			}
			settings_editor.commit();
			menuSceneAxisInvert.back();

		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {
		}

	};

	IAnimationListener buttonanimationlistener13 = new IAnimationListener() {
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {

			if (invertYaxis == true) {
				invertYaxis = false;
				axismodeYinvertMenuItem.setCurrentTileIndex(0);
				settings_editor.putBoolean("VerticalInverted", false);
			} else {
				invertYaxis = true;
				axismodeYinvertMenuItem.setCurrentTileIndex(1);
				settings_editor.putBoolean("VerticalInverted", true);
			}
			settings_editor.commit();
			menuSceneAxisInvert.back();

		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {
		}

	};

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
	private boolean StartNextWave = false;
	private int wavedelay = 0;
	private boolean WaveEnded = false;
	private int WaveScoreModifier;
	private boolean showHUD = false;
	private boolean hideHUD = false;

	HighScoreDB dbase;// / = new HighScoreDB(this);
	private boolean SloMoON;
	private LinkedList<Integer> scorelist;
	private boolean SpawnBoss = false;
	private int ninjasspawned = 0;
	private boolean firstRun;
	private int killcombo;

	private int killtimer;
	private int scoreaddtimer;
	private int Boxtimer;
	private boolean menukey;
	private Object menuButtonTexture;
	private BuildableBitmapTextureAtlas menuButtonTextureAtlas;
	private TiledTextureRegion menubuttonTextureRegion;
	private AnimatedSprite menuhud;
	private boolean displaymenukey;
	private boolean HadokenreadyRight;
	private boolean HadokenreadyLeft;
	private boolean liked = false;
	//private TiledTextureRegion StickmanDualSwordslayeredTextureRegion;
	private LayeredAssetsBitmapTextureAtlasSource Stickmanlabtas;
	private TiledTextureRegion StickmanlayeredTextureRegion;
	private LayeredAssetsBitmapTextureAtlasSource StickmanDualSwordslabtas;
	private TiledTextureRegion StickmanDualSwordslayeredTextureRegion;
	private LayeredAssetsBitmapTextureAtlasSource StickmanDualSwordspinlabtas;
	private TiledTextureRegion StickmanDualSwordspinlayeredTextureRegion;

	
	//@SuppressLint("NewApi")
	//@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@SuppressWarnings("NewApi")
	@Override
	public EngineOptions onCreateEngineOptions() {

		SGDA = setupSGDA();
		this.display = getWindowManager().getDefaultDisplay();// get screen from
																// system
		this.screenwidth = display.getWidth();
		this.screenheight = display.getHeight();
		int version = Integer.valueOf(android.os.Build.VERSION.SDK);
		if (version >= 14) {
			if (ViewConfiguration.get(this).hasPermanentMenuKey() == true) {
				this.displaymenukey = false;
				this.menukey = true;
			} else {
				this.menukey = false;
				this.displaymenukey = true;
			}
		} else {
			this.menukey = true;
			this.displaymenukey = false;
		}

		float screenratio;

		if (screenwidth > screenheight) {
			screenratio = screenwidth / screenheight;
			height = screenscale;
			width = (int) (screenscale * screenratio);
		} else {
			screenratio = screenheight / screenwidth;
			width = screenscale;
			height = (int) (screenscale * screenratio);
		}

		screenwidth = width;
		screenheight = height;

		this.halfwidth = (int) (width * .5);
		this.halfheight = (int) (height * .5);
		this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
		settings_editor = preferences.edit();
		
	
		// set defaults from xml only if this is the first time this method has
		// been called
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);// <---thats
																			// this
																			// false
																			// flag
		// this.Physics = new OurPhysics(screenheight, screenwidth, horizontals,
		// verticals, preferences);//init our physics
		this.modeHistory=preferences.getInt("modeHistory", 0);
		this.soundbank.loadSound(this);
		this.mCamera = new SmoothCamera(0, 0, width, height, 1000, 1000, 10);// create
																				// smooth
																				// scrolling
																				// camera
		this.engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						screenratio), mCamera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);

		if(MultiTouch.isSupported(this)) {
			if(MultiTouch.isSupportedDistinct(this)) {
				Toast.makeText(this, "MultiTouch detected --> Both controls will work properly!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "MultiTouch detected, but your device has problems distinguishing between fingers.\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "Sorry your device does NOT support MultiTouch!\n\n(Falling back to SingleTouch.)\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
		}

		
		return engineOptions;

	}

	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {

		/*--------------DEFINE MODES
		int Practice1on1 = 1;
		int PracticeVsBoxes = 2;
		int EasyMode = 3;
		int NormalMode = 4;
		int GamerMode = 5;*/

		this.mEngine = new LimitedFPSEngine(this.engineOptions, ModeSelection());

		return this.mEngine;
	}

	public int ModeSelection() {
		Mode = preferences.getInt("Mode", 0);
		int Framerate;
		if (Mode == EasyMode) {
			Framerate = 24;
			shurikenHPratio = (float) .5;
		} else if (Mode == NormalMode) {
			Framerate = 28;
			shurikenHPratio = (float) 1;
		} else {
			Framerate = 32;
			shurikenHPratio = (float) 2;
		}
		return Framerate;
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this.getApplicationContext());
	}

	String modeString(int prefSwitch) {

		/*--------------DEFINE MODES
		int Practice1on1 = 1;
		int PracticeVsBoxes = 2;
		int EasyMode = 3;
		int NormalMode = 4;
		int GamerMode = 5;*/
		String mode = "unset";

		if (prefSwitch == 1) {
			mode = "Practice1on1";
		} else if (prefSwitch == 2) {
			mode = "PracticeVsBoxes";
		} else if (prefSwitch == 3) {
			mode = "EasyMode";
		} else if (prefSwitch == 4) {
			mode = "NormalMode";
		} else if (prefSwitch == 5) {
			mode = "GamerMode";
		
		} else if (prefSwitch == 6) {
			mode = "StoryMode";
		
		} else if (prefSwitch == 7) {
			mode = "TestMode";
		}
		return mode;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");// setting
																		// path
																		// for
																		// textures

		context = this;

		this.dbase = new HighScoreDB(this);
		FlurryAgent.onStartSession(context, "5CBX77VX5QW2NDVPQ34H");
		if (preferences.getBoolean("firstrun", false) == true) {
			firstRun = true;
			this.dbase.insertDefaultData();
			settings_editor.putInt("experience", 0);
			settings_editor.putInt("money", 0);
			settings_editor.commit();

		}

		this.VBO = this.getVertexBufferObjectManager();
		this.soundservice = new Intent(this, BackgroundSoundService.class);
		if (preferences.getBoolean("mute", false) == false) {

			startService(soundservice); // OR stopService(soundservice);
		}

		int randomlevelbackground = (int) (Math.random() * 100);
		if (randomlevelbackground <20) {
			parallax1 = "flatjungle1.png";
			parallax2 = "flatjungle2.png";
			parallax3 = "flatjungle3.png";
			parallaxclouds = "flatjungleclouds.png";
		} else if ((randomlevelbackground >=20)&& (randomlevelbackground <40)){
			parallax1 = "flatcity1red.png";
			parallax2 = "flatcity2red.png";
			parallax3 = "flatcity3red.png";
			parallaxclouds = "flatcitycloudsred.png";
		

		} else if ((randomlevelbackground >=40)&& (randomlevelbackground <60)){
			parallax1 = "flatcity1blue.png";
			parallax2 = "flatcity2blue.png";
			parallax3 = "flatcity3blue.png";
			parallaxclouds = "flatcitycloudsblue.png";
		
		
		} else if ((randomlevelbackground >=60)&& (randomlevelbackground <80)){
			parallax1 = "flatcity1grey.png";
			parallax2 = "flatcity2grey.png";
			parallax3 = "flatcity3grey.png";
			parallaxclouds = "flatcitycloudsgrey.png";
		} else {
		parallax1 = "flatcity1dark.png";
		parallax2 = "flatcity2dark.png";
		parallax3 = "flatcity3dark.png";
		parallaxclouds = "flatcitycloudsdark.png";
	}
		
		
		liked = preferences.getBoolean("visitedFacebook", false);

		mode = "GameTimer " + modeString(Mode);
		FlurryAgent.logEvent(mode, true);

		/*
		 * int Practice1on1 = 1; int PracticeVsBoxes = 2; int EasyMode = 3; int
		 * NormalMode = 4; int GamerMode = 5;
		 */

		if ((Mode == Practice1on1) || (Mode == PracticeVsBoxes)) {
			DamageOFF = true;
			AllAlone = true;
			
			
			if(modeHistory<2){
				settings_editor.putInt("modeHistory", 2);
				settings_editor.commit();
				
			}
		} else {
			DamageOFF = false;
		}

		this.runOnUiThread(new Runnable() {

			public void run() {
				// Debugger("callback");

			}
		});

		fpsCounter = new FPSCounter();
		mEngine.registerUpdateHandler(fpsCounter);
		/*
		 * scene.registerUpdateHandler(new TimerHandler(1f, true,new
		 * ITimerCallback() {
		 * 
		 * @Override public void onTimePassed( final TimerHandler pTimerHandler)
		 * { //fpsText.setText("FPS: " + fpsCounter.getFPS()); //<- HERE IT IS
		 * pTimerHandler.reset(); } }));
		 */

		arrowlist = new LinkedList<Arrow>();
		enemyarrowlist = new LinkedList<Arrow>();
		shurikenlist = new LinkedList<Shuriken>();
		enemyshurikenlist = new LinkedList<Shuriken>();
		hadokenlist = new LinkedList<Hadoken>();
		smokepufflist = new LinkedList<SmokePuff>();
		swordlist = new LinkedList<Sword>();
		angellist = new LinkedList<Angel>();
		poweruplist = new LinkedList<PowerUp>();
		corpselist = new LinkedList<Corpse>();
		ninjaslist = new LinkedList<GameCharacter>();
		scorelist = new LinkedList<Integer>();
		// create and populate animation array
		this.healthicon[0] = 5;
		this.arrowsicon[0] = 12;
		this.bighealthicon[0] = 1;
		this.hadokenbuff[0] = 4;
		this.slomoicon[0] = 3;
		this.smokebomb[0] = 0;
		this.levelup[0] = 2;
		this.shurikens[0] = 7;
		this.coppercoin[0] = 16;
		this.silvercoin[0] = 17;
		this.goldcoin[0] = 18;
		this.topaz[0] =  32;
		this.amnethyst[0] = 33; 
		this.emerald[0] = 34 ;
		this.ruby[0] =  35;
		this.saphire[0] =  36;
		this.diamond[0] =  38;
		this.blackpearl[0] = 37 ;
		this.chestclosed[0] =  21;
		this.chestopen[0] =  22;
		this.chestopen[1] =  23;
		this.key[0] =  20;
		this.explosion[0] =  24;
		this.explosion[1] =  25;
		this.explosion[2] =  26;
		this.explosion[3] =  27;
		this.explosion[4] =  28;
		this.explosion[5] =  29;
		this.explosion[6] =  30;
		this.explosion[7] =  31;
				
				
		this.blank[0] = 15;
		this.frenzy[0] = 13;
		this.bigninjaface[0] = 6;
		this.lilninjaface[0] = 10;
		
		this.hatsitem[0] = 41;
		this.glovesitem[0] = 44;
		this.pantsitem[0] = 46;
		this.shoesitem[0] = 47;
		this.facesitem[0] = 48;
		this.shirtsitem[0] = 45;
		this.wingsitem[0] = 49;
		
		
		
		
		this.lilbomb[0] = 11;

		this.pastdirections[0] = 0;
		this.pastdirections[1] = 0;
		this.pastdirections[2] = 0;
		this.pastdirections[3] = 0;
		this.pastdirections[4] = 0;
		this.pastdirections[5] = 0;

		this.movetimer[0] = 0;
		this.movetimer[1] = 0;
		this.movetimer[2] = 0;
		this.movetimer[3] = 0;
		this.movetimer[4] = 0;
		this.movetimer[5] = 0;

		this.shurikenl[0] = 0;
		this.shurikenl[1] = 1;
		this.shurikenl[2] = 2;
		this.shurikenl[3] = 3;

		this.hadokenstart[0] = 0;
		this.hadokenstart[1] = 1;
		this.hadokenstart[2] = 2;
		this.hadokenstart[3] = 3;

		this.hadokenfly[0] = 0;
		this.hadokenfly[1] = 1;
		this.hadokenfly[2] = 2;
		this.hadokenfly[3] = 3;

		this.hadokenhit[0] = 5;
		this.hadokenhit[1] = 6;
		this.hadokenhit[2] = 7;
		this.hadokenhit[3] = 8;
		this.hadokenhit[4] = 9;

		this.shurikenr[0] = 3;
		this.shurikenr[1] = 2;
		this.shurikenr[2] = 1;
		this.shurikenr[3] = 0;

		this.thrownswordl[0] = 0;

		this.thrownswordr[0] = 1;

		this.groundedsword[0] = 2;
		this.wallstucksword[0] = 3;

		this.FX8count[0] = 0;
		this.FX8count[1] = 1;
		this.FX8count[2] = 2;
		this.FX8count[3] = 3;
		this.FX8count[4] = 4;
		this.FX8count[5] = 5;
		this.FX8count[6] = 6;
		this.FX8count[7] = 7;

		this.ninjaCorpseflinchl[0] = 2;
		this.ninjaCorpseflinchl[1] = 1;
		this.ninjaCorpseflinchl[2] = 0;

		this.ninjaCorpseflinchr[0] = 6;
		this.ninjaCorpseflinchr[1] = 5;
		this.ninjaCorpseflinchr[2] = 4;

		this.angel[0] = 5;
		this.angel[1] = 4;
		this.angel[2] = 3;
		this.angel[3] = 2;
		this.angel[4] = 1;
		this.angel[5] = 0;
		this.angel[6] = 1;
		this.angel[7] = 2;
		this.angel[8] = 3;
		this.angel[9] = 4;

		this.ninjadeathr[0] = 0;
		this.ninjadeathl[0] = 2;

		this.oneFrame[0] = 100;
		this.twoFrame[0] = 100;
		this.twoFrame[1] = 100;
		this.threeFrame[0] = 100;
		this.threeFrame[1] = 100;
		this.threeFrame[2] = 100;
		this.fourFrame[0] = 100;
		this.fourFrame[1] = 100;
		this.fourFrame[2] = 100;
		this.fourFrame[3] = 100;
		this.fiveFrame[0] = 100;
		this.fiveFrame[1] = 100;
		this.fiveFrame[2] = 100;
		this.fiveFrame[3] = 100;
		this.fiveFrame[4] = 100;
		this.sixFrame[0] = 100;
		this.sixFrame[1] = 100;
		this.sixFrame[2] = 100;
		this.sixFrame[3] = 100;
		this.sixFrame[4] = 100;
		this.sixFrame[5] = 100;
		this.sevenFrame[0] = 100;
		this.sevenFrame[1] = 100;
		this.sevenFrame[2] = 100;
		this.sevenFrame[3] = 100;
		this.sevenFrame[4] = 100;
		this.sevenFrame[5] = 100;
		this.sevenFrame[6] = 100;

		this.eightFrame[0] = 100;
		this.eightFrame[1] = 100;
		this.eightFrame[2] = 100;
		this.eightFrame[3] = 100;
		this.eightFrame[4] = 100;
		this.eightFrame[5] = 100;
		this.eightFrame[6] = 100;
		this.eightFrame[7] = 100;

		this.eightFrameFAST[0] = 50;
		this.eightFrameFAST[1] = 50;
		this.eightFrameFAST[2] = 50;
		this.eightFrameFAST[3] = 50;
		this.eightFrameFAST[4] = 50;
		this.eightFrameFAST[5] = 50;
		this.eightFrameFAST[6] = 50;
		this.eightFrameFAST[7] = 50;

		this.eightFrameSLOW[0] = 250;
		this.eightFrameSLOW[1] = 250;
		this.eightFrameSLOW[2] = 250;
		this.eightFrameSLOW[3] = 250;
		this.eightFrameSLOW[4] = 250;
		this.eightFrameSLOW[5] = 250;
		this.eightFrameSLOW[6] = 250;
		this.eightFrameSLOW[7] = 250;

		this.tenFrame[0] = 200;
		this.tenFrame[1] = 200;
		this.tenFrame[2] = 200;
		this.tenFrame[3] = 200;
		this.tenFrame[4] = 200;
		this.tenFrame[5] = 200;
		this.tenFrame[6] = 200;
		this.tenFrame[7] = 200;
		this.tenFrame[8] = 200;
		this.tenFrame[9] = 200;

		this.hadokenfourFrame[0] = 40;
		this.hadokenfourFrame[1] = 40;
		this.hadokenfourFrame[2] = 40;
		this.hadokenfourFrame[3] = 40;

		this.hadokenfiveFrame[0] = 40;
		this.hadokenfiveFrame[1] = 40;
		this.hadokenfiveFrame[2] = 40;
		this.hadokenfiveFrame[3] = 40;
		this.hadokenfiveFrame[4] = 40;

		this.mOnScreenControlTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_knob.png", 128, 0);
		this.mOnScreenControlTexture.load();

		this.mMenuResumeTexture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.mMenuQuitTexture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.mMenuRecalTexture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.mMenuMuteTexture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.GOTexture = new BitmapTextureAtlas(this.getTextureManager(), 256,
				128, TextureOptions.BILINEAR);
		this.flatcity1Texture = new BitmapTextureAtlas(
				this.getTextureManager(), 600, 400, TextureOptions.BILINEAR);
		this.flatcity2Texture = new BitmapTextureAtlas(
				this.getTextureManager(), 600, 400, TextureOptions.BILINEAR);
		this.flatcity3Texture = new BitmapTextureAtlas(
				this.getTextureManager(), 600, 400, TextureOptions.BILINEAR);
		this.flatcityCloudsTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 600, 400, TextureOptions.BILINEAR);
		
		this.blood1Texture = new BitmapTextureAtlas(
				this.getTextureManager(), 600, 400, TextureOptions.BILINEAR);
		this.blood2Texture = new BitmapTextureAtlas(
				this.getTextureManager(), 600, 400, TextureOptions.BILINEAR);
		this.blood3Texture = new BitmapTextureAtlas(
				this.getTextureManager(), 600, 400, TextureOptions.BILINEAR);
		this.blood4Texture = new BitmapTextureAtlas(
				this.getTextureManager(), 600, 400, TextureOptions.BILINEAR);
		this.blood5Texture = new BitmapTextureAtlas(
				this.getTextureManager(), 600, 400, TextureOptions.BILINEAR);
		this.blood6Texture = new BitmapTextureAtlas(
				this.getTextureManager(), 600, 400, TextureOptions.BILINEAR);
		this.blood7Texture = new BitmapTextureAtlas(
				this.getTextureManager(), 600, 400, TextureOptions.BILINEAR);
		
		
		
		
		this.menuButtonTextureAtlas = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 60, 60, TextureOptions.BILINEAR);
		this.axismode0Texture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.axismode1Texture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.axismode2Texture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.axismode3Texture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.axismode4Texture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.axismode5Texture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.axisselectionTexture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 305, TextureOptions.BILINEAR);
		this.axisinvertTexture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.axismodeXinvertTexture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);
		this.axismodeYinvertTexture = new BuildableBitmapTextureAtlas(
				this.getTextureManager(), 205, 105, TextureOptions.BILINEAR);

		this.mMenuTexture = new BitmapTextureAtlas(this.getTextureManager(),
				800, 450, TextureOptions.BILINEAR);
		// this.mFontTexture = new BitmapTextureAtlas(this.getTextureManager(),
		// 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		// this.tooltipFontTexture = new
		// BitmapTextureAtlas(this.getTextureManager(), 256, 256,
		// TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mFontTexture = new BitmapTextureAtlas(this.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.tooltipFontTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.countersFontTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mMenuResumeTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMenuResumeTexture, this,
						"menu_reset.png", 1, 2);

		this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMenuQuitTexture, this,
						"menu_quit.png", 1, 2);
		this.mMenuRecalTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMenuRecalTexture, this,
						"menu_recalibrate.png", 1, 2);
		this.mMenuMuteTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMenuMuteTexture, this,
						"menu_mute.png", 1, 2);
		this.GameOverTex = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.GOTexture, this, "GAMEOVER.png", 0, 0);
		this.flatcity1TEX = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.flatcity1Texture, this, parallax1, 0, 0);
		this.flatcity2TEX = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.flatcity2Texture, this, parallax2, 0, 0);
		this.flatcity3TEX = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.flatcity3Texture, this, parallax3, 0, 0);
		this.flatcityCloudsTEX = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.flatcityCloudsTexture, this,
						parallaxclouds, 0, 0);
		
		this.blood1TEX = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.blood1Texture, this, "blood1.png", 0, 0);
		this.blood2TEX = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.blood2Texture, this, "blood2.png", 0, 0);
		this.blood3TEX = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.blood3Texture, this, "blood3.png", 0, 0);
		this.blood4TEX = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.blood4Texture, this, "blood4.png", 0, 0);
		this.blood5TEX = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.blood5Texture, this, "blood5.png", 0, 0);
		this.blood6TEX = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.blood6Texture, this, "blood6.png", 0, 0);
		this.blood7TEX = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.blood7Texture, this, "blood7.png", 0, 0);
		
		this.menubuttonTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.menuButtonTextureAtlas, this,
						"menu.png", 1, 2);
		this.axismode0TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.axismode0Texture, this,
						"axismode0.png", 1, 2);
		this.axismode1TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.axismode1Texture, this,
						"axismode1.png", 1, 2);
		this.axismode2TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.axismode2Texture, this,
						"axismode2.png", 1, 2);
		this.axismode3TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.axismode3Texture, this,
						"axismode3.png", 1, 2);
		this.axismode4TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.axismode4Texture, this,
						"axismode4.png", 1, 2);
		this.axismode5TextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.axismode5Texture, this,
						"axismode5.png", 1, 2);
		this.axisselectionTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.axisselectionTexture, this,
						"axisselection.png", 1, 6);
		this.axisinvertTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.axisinvertTexture, this,
						"invertaxes.png", 1, 2);
		this.axismodeXinvertTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.axismodeXinvertTexture, this,
						"horizontal.png", 1, 2);
		this.axismodeYinvertTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.axismodeYinvertTexture, this,
						"vertical.png", 1, 2);

		// stickDualSwordsTextureAtlas = new
		// BuildableBitmapTextureAtlas(this.getTextureManager(), 850, 350,
		// TextureOptions.NEAREST);
		// this.stickmantex = SetupTiledTexture(stickTextureAtlas, 532, 440,
		// "stickman-master.png", 8, 6);

		
		this.stickTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 532, 440, TextureOptions.NEAREST);
	
	      
	      
	      
	      Stickmanlabtas = new LayeredAssetsBitmapTextureAtlasSource(this.getBaseContext(), "blank-master.png");
	      // ITextureRegion layeredTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource(    stickmantex, labtas, 0, 0);
	      
	       StickmanlayeredTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource(stickTextureAtlas, Stickmanlabtas, 8, 6);
	        
	        try 
	        {
	        	stickTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
	        	stickTextureAtlas.load();
	        } 
	        catch (final TextureAtlasBuilderException e)
	        {
	           Debug.e(e);
	        }
	        

		/*
		 * this.stickDualSwordsTextureAtlas = new
		 * BuildableBitmapTextureAtlas(this.getTextureManager(),900,400,
		 * TextureOptions.NEAREST); this.stickmanDualSwordstex =
		 * BitmapTextureAtlasTextureRegionFactory
		 * .createTiledFromAsset(this.stickDualSwordsTextureAtlas, this,
		 * "stickman-dualswords.png", 8, 3); try {
		 * this.stickDualSwordsTextureAtlas.build(new
		 * BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,
		 * BitmapTextureAtlas>(0, 0, 1));
		 * this.stickDualSwordsTextureAtlas.load(); } catch
		 * (TextureAtlasBuilderException e) { Debug.e(e); }
		 */

		// stickmanDualSwords; stickDualSwordsTextureAtlas;
		// stickmanStaff; stickStaffTextureAtlas;
		// stickmanBow; stickBowTextureAtlas;
	        
	        
	        
	        //this.stickmanDualSwordstex = SetupTiledTexture(stickDualSwordsTextureAtlas, 850, 350,"stickman-dualswords.png", 8, 3);
			this.stickDualSwordsTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 850, 350, TextureOptions.NEAREST);
		
		      
		      
		      
		      StickmanDualSwordslabtas = new LayeredAssetsBitmapTextureAtlasSource(this.getBaseContext(), "base/dualswords.png");
		      //final boolean addedLayer3 = Stickmanlabtas.addLayer("stickman-dualswords.png", 0, 0);
		      final boolean addedLayer4 = StickmanDualSwordslabtas.addLayer("hats/0/dualswords.png", 0, 0);
		   
		      
		       StickmanDualSwordslayeredTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource(stickDualSwordsTextureAtlas, StickmanDualSwordslabtas, 8, 3);
		        
		        try 
		        {
		        	stickDualSwordsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
		        	stickDualSwordsTextureAtlas.load();
		        } 
		        catch (final TextureAtlasBuilderException e)
		        {
		           Debug.e(e);
		        }
	        
	        
	        
	        
	        
	        
	     
	        
	        
	        
		//this.stickmanDualSwordspintex = SetupTiledTexture(stickDualSwordspinTextureAtlas, 650, 70, "dualswordspin.png",	10, 1);
		
		        this.stickDualSwordspinTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 650, 70, TextureOptions.NEAREST);
				
			      
			      
			      
			      StickmanDualSwordspinlabtas = new LayeredAssetsBitmapTextureAtlasSource(this.getBaseContext(), "base/dualswordspin.png");
			      //final boolean addedLayer5 = Stickmanlabtas.addLayer("dualswordspin.png", 0, 0);
			      
			      final boolean addedLayer6 = StickmanDualSwordspinlabtas.addLayer("hats/0/dualswordspin.png", 0, 0);
			  
			      
			       StickmanDualSwordspinlayeredTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource(stickDualSwordspinTextureAtlas, StickmanDualSwordspinlabtas, 10, 1);
			        
			        try 
			        {
			        	stickDualSwordspinTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
			        	stickDualSwordspinTextureAtlas.load();
			        } 
			        catch (final TextureAtlasBuilderException e)
			        {
			           Debug.e(e);
			        }
		
		
		// stickmanStafftex = SetupTiledTexture(stickStaffTextureAtlas, 810,
		// 310, "", 8, 3);
		// stickmanBowtex = SetupTiledTexture(stickBowTextureAtlas, 810, 310,
		// "", 8, 3);

		this.ninjatexture1 = SetupTiledTexture(ninjaTextureAtlas, 532, 440,
				"ninja-master.png", 8, 6);
		this.ninjatexture2 = SetupTiledTexture(bossTextureAtlas, 532, 440,
				"ninja-sword.png", 8, 6);
		this.HudDoodadsTexture = SetupTiledTexture(HudDoodadsTextureAtlas, 532, 532, "HUDdoodads.png", 8,8);
		this.ArrowTexture = SetupTiledTexture(ArrowTextureAtlas, 40, 10,
				"Arrow.png", 1, 2);
		this.AngelsTexture = SetupTiledTexture(AngelsTextureAtlas, 70, 400,
				"angel.png", 1, 6);
		this.CorpsesTexture = SetupTiledTexture(CorpsesTextureAtlas, 130, 130,
				"corpse-ninja.png", 2, 2);
		this.ShurikenTexture = SetupTiledTexture(ShurikenTextureAtlas, 60, 30,
				"shuriken.png", 2, 2);
		this.HadokenTexture = SetupTiledTexture(HadokenTextureAtlas, 330, 130,
				"hadoken.png", 5, 2);

		this.SwordTexture = SetupTiledTexture(SwordTextureAtlas, 130, 130,
				"thrownsword.png", 2, 2);
		this.FXSmokepuffTexture = SetupTiledTexture(FXSmokepuffTextureAtlas,
				70, 700, "FXsmokepuff.png", 1, 8);
		this.FXSidepuffsTexture = SetupTiledTexture(FXSidepuffsTextureAtlas,
				150, 700, "FXsidepuffs.png", 1, 8);
		this.FXHealthUpTexture = SetupTiledTexture(FXHealthUpTextureAtlas, 70,
				600, "FXhealthUp.png", 1, 8);
		this.FXNewNinjaTexture = SetupTiledTexture(FXNewNinjaTextureAtlas, 70,
				600, "FXnewNinja.png", 1, 8);
		this.FXLevelUpTexture = SetupTiledTexture(FXLevelUpTextureAtlas, 70,
				600, "FXlevelUp.png", 1, 8);
		this.FXShurikengrabTexture = SetupTiledTexture(
				FXShurikengrabTextureAtlas, 70, 600, "FXShurikenPickup.png", 1,
				8);
		this.FXSmokeBombPickupTexture = SetupTiledTexture(
				FXSmokeBombPickupTextureAtlas, 70, 600,
				"FXsmokebombpickup.png", 1, 8);
		this.FXSlowMoTexture = SetupTiledTexture(FXSlowMoTextureAtlas, 70, 600,
				"FXSlowMo.png", 1, 8);
		this.FXLevelCompleteTexture = SetupTiledTexture(
				FXLevelCompleteTextureAtlas, 150, 600, "FXLevelComplete.png",
				1, 8);

		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getTextureManager().loadTexture(this.tooltipFontTexture);
		this.mEngine.getTextureManager().loadTexture(this.countersFontTexture);
		try {
			this.mMenuResumeTexture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.mMenuResumeTexture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.mMenuQuitTexture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.mMenuQuitTexture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.mMenuRecalTexture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.mMenuRecalTexture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.mMenuMuteTexture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.mMenuMuteTexture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.menuButtonTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.menuButtonTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		try {
			this.axismode0Texture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.axismode0Texture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.axismode1Texture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.axismode1Texture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.axismode2Texture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.axismode2Texture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.axismode3Texture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.axismode3Texture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.axismode4Texture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.axismode4Texture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.axismode5Texture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.axismode5Texture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.axisselectionTexture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.axisselectionTexture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.axisinvertTexture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.axisinvertTexture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.axismodeXinvertTexture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.axismodeXinvertTexture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		try {
			this.axismodeYinvertTexture
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.axismodeYinvertTexture.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		this.axisSwitch = preferences.getInt("AxisSwitch", 0);
		this.ControlStick = preferences.getBoolean("OnScreenControlsPref", false);
		this.invertYaxis = preferences.getBoolean("VerticalInverted", false);
		this.invertXaxis = preferences.getBoolean("HorizontalInverted", false);

		// assign assets to texture atlas's

		this.mFont = new Font(this.mEngine.getFontManager(), this.mFontTexture,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 36, true,
				Color.RED);
		this.tooltipFont = new Font(this.mEngine.getFontManager(),
				this.tooltipFontTexture, Typeface.create(Typeface.DEFAULT,
						Typeface.BOLD), 16, true, Color.RED);
		this.countersFont = new Font(this.mEngine.getFontManager(),
				this.countersFontTexture, Typeface.create(Typeface.DEFAULT,
						Typeface.BOLD), 10, true, Color.WHITE);

		// load and build textures
		this.GOTexture.load();
		this.flatcity1Texture.load();
		this.flatcity2Texture.load();
		this.flatcity3Texture.load();
		this.flatcityCloudsTexture.load();
		this.blood1Texture.load();
		this.blood2Texture.load();
		this.blood3Texture.load();
		this.blood4Texture.load();
		this.blood5Texture.load();
		this.blood6Texture.load();
		this.blood7Texture.load();
		this.mEngine.getFontManager().loadFont(this.mFont);
		this.mEngine.getFontManager().loadFont(this.tooltipFont);
		this.mEngine.getFontManager().loadFont(this.countersFont);
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getTextureManager().loadTexture(this.tooltipFontTexture);
		this.mEngine.getTextureManager().loadTexture(this.countersFontTexture);
		// create object pools
		ShurikenPool = new Shurikenpool(this.ShurikenTexture, this.VBO);
		ArrowPool = new Arrowpool(this.ArrowTexture, this.VBO);

		HadokenPool = new Hadokenpool(this.HadokenTexture, this.VBO);
		smokepuffpool = new SmokePuffPool(this.FXSmokepuffTexture, this.VBO);
		SwordPool = new Swordpool(this.SwordTexture, this.VBO);
		AngelPool = new Angelpool(this.AngelsTexture, this.VBO);
		PowerUpPool = new Poweruppool(this.HudDoodadsTexture, this.VBO);
		CorpsePool = new Corpsepool(this.CorpsesTexture, this.VBO);
		NinjaPool = new Ninjapool(this.ninjatexture1, this.VBO, animator);
		BossPool = new Ninjapool(this.ninjatexture2, this.VBO, animator);
		ComboText = new Text(0, 0, mFont, "", 32, this.VBO);
		StreakText = new Text(0, 0, mFont, "", 32, this.VBO);
		ScoreAddText = new Text(0, 0, mFont, "", 32, this.VBO);
		BoxText = new Text(0, 0, tooltipFont, "", 32, this.VBO);
		ShurikenCountText = new Text(0, 0, countersFont, "", 32, this.VBO);
		SmokebombCountText = new Text(0, 0, countersFont, "", 32, this.VBO);
		LevelCompleteText = new Text(0, 0, tooltipFont, "", 32, this.VBO);
		ScoreAddText.setVisible(false);
		BoxText.setVisible(false);
		ShurikenCountText.setVisible(false);
		SmokebombCountText.setVisible(false);
		ComboText.setVisible(false);
		StreakText.setVisible(false);
		LevelCompleteText.setVisible(false);
		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	private int StreakTextPosition;
	private int ComboTextPosition;
	private boolean BoxTextCondition;
	private int BoxTextConditionNumber;
	private int HadokenFuryTimer;
	private int HadokenReadyTimer = 0;
	private int modeHistory;
	private String levelstring;
	private float HowClose = 99999998;
	private int WhosClosest;
	private int NinjaFrenzy = 0;
	private boolean ControlStick;
	public boolean ATTACK;
	public boolean BOW;
	private Rectangle MainButtonPlacer;
	private Rectangle SecondaryButtonPlacer1;
	private Rectangle SecondaryButtonPlacer2;
	private Rectangle SecondaryButtonPlacer3;
	private Rectangle healthbar;
	private Scene splashscene;
	
	
	
	
	
	

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		fps = new FPSLogger();
		this.mEngine.registerUpdateHandler(fps);
		// final VertexBufferObjectManager vertexBufferObjectManager = this.VBO;
		this.scene = new Scene();
		this.splashscene = new Scene();
		this.splashscene.setBackground(new Background(0,0,0));
		this.mEngine.setScene(splashscene);

		SGDA.setEnabled(true);

		this.scene.setOnSceneTouchListener(SGDA);

		this.createMenuScene();
		this.createHud();
		this.createAxisMenuScene();
		this.createAxisInvertMenuScene();
		this.animator = new Animator(soundbank, SFX, SloMoON, debugging);
		if (firstRun == false) {
			money = preferences.getInt("money", 0);
		}
		this.mEngine.registerUpdateHandler(this);
		// this.scene.setOnSceneTouchListener(this);
		this.scene.setOnAreaTouchListener(this);

		// this.scene.setBackground(new Background(Color.WHITE));

		this.flatcity1Sprite = new Sprite(-500, 0, this.screenwidth,
				this.screenheight, this.flatcity1TEX, this.VBO);
		this.flatcity2Sprite = new Sprite(-500, 0, this.screenwidth,
				this.screenheight, this.flatcity2TEX, this.VBO);
		this.flatcity3Sprite = new Sprite(-500, 0, this.screenwidth,
				this.screenheight, this.flatcity3TEX, this.VBO);
		this.flatcityCloudsSprite = new Sprite(0, 0, this.screenwidth,
				this.screenheight, this.flatcityCloudsTEX, this.VBO);
		
		this.flatcityCloudsBG = new SpriteBackground(flatcityCloudsSprite);
		this.scene.setBackground(flatcityCloudsBG);

		this.blood1Sprite = new Sprite(0, 0, this.screenwidth, this.screenheight, this.blood1TEX, this.VBO);
		this.blood2Sprite = new Sprite(0, 0, this.screenwidth, this.screenheight, this.blood2TEX, this.VBO);
		this.blood3Sprite = new Sprite(0, 0, this.screenwidth, this.screenheight, this.blood3TEX, this.VBO);
		this.blood4Sprite = new Sprite(0, 0, this.screenwidth, this.screenheight, this.blood4TEX, this.VBO);
		this.blood5Sprite = new Sprite(0, 0, this.screenwidth, this.screenheight, this.blood5TEX, this.VBO);
		this.blood6Sprite = new Sprite(0, 0, this.screenwidth, this.screenheight, this.blood6TEX, this.VBO);
		this.blood7Sprite = new Sprite(0, 0, this.screenwidth, this.screenheight, this.blood7TEX, this.VBO);
		this.mHud.attachChild(blood1Sprite);
		this.mHud.attachChild(blood2Sprite);
		this.mHud.attachChild(blood3Sprite);
		this.mHud.attachChild(blood4Sprite);
		this.mHud.attachChild(blood5Sprite);
		this.mHud.attachChild(blood6Sprite);
		this.mHud.attachChild(blood7Sprite);
		
		
		blood1Sprite.setVisible(false);
		blood2Sprite.setVisible(false);
		blood3Sprite.setVisible(false);
		blood4Sprite.setVisible(false);
		blood5Sprite.setVisible(false);
		blood6Sprite.setVisible(false);
		blood7Sprite.setVisible(false);
		
		RandomizeBloodSplatter();
		
		
		backgroundParallax = new ParallaxLayer(mCamera, true, true, 4000, 4000);
		backgroundParallax.setParallaxScrollFactorX(.1f);
		backgroundParallax.setParallaxScrollFactorY(.1f);

		backgroundParallax.attachParallaxEntity(new ParallaxEntity(-8f, -8f,
				flatcity3Sprite, true, true));
		backgroundParallax.attachParallaxEntity(new ParallaxEntity(-5f, -5f,
				flatcity2Sprite, true, true));
		backgroundParallax.attachParallaxEntity(new ParallaxEntity(-1f, -1f,
				flatcity1Sprite, true, true));

		this.scene.attachChild(backgroundParallax);

		LevelSelect();

		this.Physics = new OurPhysics(screenheight, screenwidth, horizontals,
				verticals, preferences, mEngine, this.scene, this.debugging);// init our physics

		this.stickman = new GameCharacter(300, 200, this.StickmanlayeredTextureRegion, this.VBO,
				animator);
		stickman.xpos = 300;
		stickman.ypos = 200;
		
		  stickman.base = this.preferences.getInt("stickmanbase", 0);
		  stickman.hats = this.preferences.getInt("stickmanhats", 0);
		 stickman.pants = this.preferences.getInt("stickmanpants", 0);
		stickman.gloves = this.preferences.getInt("stickmangloves", 0);
		 stickman.faces = this.preferences.getInt("stickmanfaces", 0);
		stickman.shirts = this.preferences.getInt("stickmanshirts", 0);
		 stickman.shoes = this.preferences.getInt("stickmanshoes", 0);
		 stickman.wings = this.preferences.getInt("stickmanwings", 0);
	
		 
		 
		 
		
	
	      
		// stickmanDualSwords = new AnimatedSprite(0,0, stickmanDualSwordstex,
		// this.VBO);
		// stickman.AddSpecialAttack(-16, -32,ShurikenTexture, 0,
		// 1);//placeholder for special 0 to avoid null errors. Smallest texture
		// used
		// stickman.specialattack[2].AddPart(0, 0, 0);//place holder for special
		// 0 part 0 to avoid null errors

		stickman.AddSpecialAttack(-16, -32, StickmanDualSwordslayeredTextureRegion, 2, 2);// stickmanDualSwords;
		stickman.specialattack[2].AddPart(0, 1, 13);
		stickman.specialattack[2].AddPart(1, 14, 23);

		stickman.AddSpecialAttack(2, 4, StickmanDualSwordspinlayeredTextureRegion, 3, 1);
		stickman.specialattack[3].AddPart(0, 1, 9);

		
		textureswitch();	 
		weapontextureswitch();
		
		
		
		camerabox = new Rectangle(1,1, 1, 1, this.VBO);
		//camerabox.animate(oneFrame, this.blank, -1);
		camerabox.setColor(Color.TRANSPARENT);
		camerabox.setVisible(true);
		this.scene.attachChild(camerabox);
		FXbox = SetupGameObject(HudDoodadsTexture);
		FXbox.animate(oneFrame, this.blank, -1);
		FXbox.setVisible(true);
		this.scene.attachChild(FXbox);
		MainButtonPlacer = new Rectangle(this.screenwidth - 128, (int)((this.screenheight/2)-64), 1, 1, this.VBO);
		SecondaryButtonPlacer1 = new Rectangle(this.screenwidth - 74, this.screenheight - 64, 1, 1, this.VBO);
		SecondaryButtonPlacer2 = new Rectangle(this.screenwidth - 138, this.screenheight - 64, 1, 1, this.VBO);
		SecondaryButtonPlacer3 = new Rectangle(this.screenwidth - 206, this.screenheight - 64, 1, 1, this.VBO);
		
		MainButtonPlacer.setColor(Color.PINK);
		SecondaryButtonPlacer1.setColor(Color.PINK);
		SecondaryButtonPlacer2.setColor(Color.PINK);
		SecondaryButtonPlacer3.setColor(Color.PINK);
		this.mHud.attachChild(MainButtonPlacer);
		this.mHud.attachChild(SecondaryButtonPlacer1);
		this.mHud.attachChild(SecondaryButtonPlacer2);
		this.mHud.attachChild(SecondaryButtonPlacer3);
		tips = true;

		if (tips == true) {
			if (firstRun == true) {
				currenttipnum = 0;
			} else {
				currenttipnum = preferences.getInt("tipnumber", 0);
			}

			int plusone = currenttipnum + 1;
			if (plusone > 60) {
				plusone = 11;
			}
			this.PopupText = new Text(0, 0, this.tooltipFont,
					tooltip(currenttipnum), tooltip(currenttipnum).length(),
					this.VBO);
			settings_editor.putInt("tipnumber", plusone);
			settings_editor.commit();
		}

		this.bankText = new Text(5, 5, this.mFont, "", "XXXXXXXXXX".length(),
				this.VBO);
		//this.healtext = new Text(this.screenwidth - 70, 10, this.mFont, " ","XXXX".length(), this.VBO);
		//this.healthhud = new AnimatedSprite(this.screenwidth - 125, -15,this.HudDoodadsTexture, this.VBO);
		//TODO HEALTH HUD REWORK HERE
		// this.levelupfx = new FX(0, 0, this.HudDoodadsTexture, this.VBO);

		this.healthbar = new Rectangle(20,20,20,20, this.VBO);
		//this.healthbar.setPosition(this.screenwidth -healthbar.getWidth(), 10);
		this.healthbar.setColor(Color.TRANSPARENT);
		
		
		
		BowAOSC = new AnalogOnScreenControl(
				BowAOSCPostionX(),
				BowAOSCPostionY(),
				this.mCamera, this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion, 0.1f, 200, VBO,
				new IAnalogOnScreenControlListener() {
					@Override
					public void onControlChange(
							final BaseOnScreenControl pBaseOnScreenControl,
							final float pValueX, final float pValueY) {
						// physicsHandler.setVelocity(pValueX * 100, pValueY *
						// 100);
						Debugger("Analog X = " + pValueX + " Y = " + pValueY);

						if (((oldAnalogX != 0) || (oldAnalogY != 0))
								&& (pValueX == 0) && (pValueY == 0)) {
							float magnitude = (float) Math.abs(Math
									.sqrt((oldAnalogX * oldAnalogX)
											+ (oldAnalogY * oldAnalogY)));

							Debugger("Arrow Magnitude = " + magnitude);
							if ((stickman.arrows > 0) && (magnitude > .2)) {
								stickman.arrows--;
								DrawArrow(-oldAnalogX, -oldAnalogY);
							}

						}

						oldAnalogX = pValueX;
						oldAnalogY = pValueY;
					}

					@Override
					public void onControlClick(
							final AnalogOnScreenControl pAnalogOnScreenControl) {
						Debugger("analog control stick click has occurred");

					}
				});
		
		
		BowAOSC.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		BowAOSC.getControlBase().setAlpha(0.2f);
		BowAOSC.getControlBase().setScaleCenter(0, 128);
		BowAOSC.getControlBase().setScale(1f);
		BowAOSC.getControlKnob().setScale(1f);
		BowAOSC.refreshControlKnobPosition();

		if(ControlStick){
		MovementAOSC = new AnalogOnScreenControl(
				(float) (10),	(float) (screenheight - this.mOnScreenControlBaseTextureRegion.getHeight() - 10),
				this.mCamera, this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion, 0.1f, 200, VBO,
				new IAnalogOnScreenControlListener() {
					@Override
					public void onControlChange(
							final BaseOnScreenControl pBaseOnScreenControl,
							final float pValueX, final float pValueY) {
						// physicsHandler.setVelocity(pValueX * 100, pValueY *
						// 100);
						Debug.d("Analog X = " + pValueX + " Y = " + pValueY);
						
						Physics.Yaccelcheck(stickman, pValueY*AnalogStickRatio);
						Physics.grabvectorx(stickman, pValueX*AnalogStickRatio);
						// Debugger("accel","x: "+ accellerometerSpeedX + "  Y: "+
						// accellerometerSpeedY +"   z: "+accellerometerSpeedZ);
						Yoffset = preferences.getFloat("Yoffset", 0.0f);
						MoveMemory(pValueX, pValueY - Yoffset);
						
					
					}

					@Override
					public void onControlClick(
							final AnalogOnScreenControl pAnalogOnScreenControl) {
						Debugger("analog control stick click has occurred");

					}
				});
		
		
		MovementAOSC.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		MovementAOSC.getControlBase().setAlpha(0.2f);
		MovementAOSC.getControlBase().setScaleCenter(0, 128);
		MovementAOSC.getControlBase().setScale(1f);
		MovementAOSC.getControlKnob().setScale(1f);
		MovementAOSC.refreshControlKnobPosition();

		
		this.AttackHUDButton = new AnimatedSprite(
				(int) (this.screenwidth - 64), (int)(this.screenheight-64),
				this.HudDoodadsTexture, this.VBO) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {

					if (stickman != null) {

							stickman.touching = true;
							ChargeTimer = 1;

						}
						 AttackHUDButton.setScale(2f);
						 AttackHUDButton.setPosition(MainButtonPlacer);
						 BowAOSC.setVisible(false);
						 BowHUDButton.setScale(1f);
						 BowHUDButton.setPosition(SecondaryButtonPlacer1);
						 // bow stuff here

						 
							shurikenhud.setCurrentTileIndex(8);
							swordthrowhud.setCurrentTileIndex(9);
							smokebombhud.setCurrentTileIndex(0);

							/*
							 * if(stickman.isHit==false) { if(HadokenreadyLeft ==
							 * true) { HadokenreadyLeft = false; Hadoken(left);
							 * 
							 * }
							 * 
							 * else if(HadokenreadyRight == true) {
							 * HadokenreadyRight = false; Hadoken(right);
							 * 
							 * } else if(stickman.currentlyattacking == false) {
							 * stickman.startattack = true; } }
							 */
							

							// set all buttons back to their default state here

					
							if ((stickman.isHit == false)
									&& (stickman.currentlyattacking == false)) {

								Debugger("onDoubleTap");

								if (stickman.isHit == false) {
									if (HadokenreadyLeft == true) {
										ClearMoveMemory();
										HadokenreadyLeft = false;
										HadokenReadyTimer = 0;
										Hadoken(left);

									}

									else if (HadokenreadyRight == true) {
										ClearMoveMemory();
										HadokenreadyRight = false;
										HadokenReadyTimer = 0;
										Hadoken(right);

									} else if (stickman.currentlyattacking == false) {
										stickman.startattack = true;
									}
								}
							}
					
					
					
					ATTACK = true;
					// shurikenhud.setCurrentTileIndex(12);
					AttackHUDButton.setCurrentTileIndex(28);
					ShowText("ATTACKING", 100);
					/*
					 * if(stickman.shuriken > 0) { throwShuriken(stickman);
					 * stickman.shuriken--; }
					 */
					return true;

				}
				
				if (pSceneTouchEvent.isActionUp()) {
					AttackHUDButton.setCurrentTileIndex(40);
					stickman.touching = false;

					int time = (int) (chargetime - (chargedelayratio * stickman.level));
					if (time < 10) {
						time = 10;
					}
					if (ChargeTimer > time) {
						stickman.frenzy = true;
						ChargeTimer = 0;
					}

					//Debugger("swipe diffx diffy " + diffX + " " + diffY);

					ATTACK = false;
					return true;
				}

				return false;
			}
		};

		
		
		this.BowHUDButton = new AnimatedSprite(
				(int) ((this.screenwidth) - 128), -1,
				this.HudDoodadsTexture, this.VBO) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {

					BOW = true;
					// shurikenhud.setCurrentTileIndex(12);
					if(stickman.arrows>0){
					 AttackHUDButton.setScale(1f);
					 AttackHUDButton.setPosition(SecondaryButtonPlacer1);
					 BowHUDButton.setScale(1f);
					 BowHUDButton.setPosition(SecondaryButtonPlacer2);
					//BowAOSC.setVisible(true);
					//BowAOSC.setScale(2f);
					//BowAOSC.setPosition(MainButtonPlacer);
					BowAOSC.setVisible(true);
					BowHUDButton.setCurrentTileIndex(39);
					//BowHUDButton.setPosition(BowAOSC.getControlKnob());
					//BowHUDButton.setParent(BowAOSC.getControlKnob());
					
					
					
					
					
					ShowText("BowButton", 100);
					/*
					 * if(stickman.shuriken > 0) { throwShuriken(stickman);
					 * stickman.shuriken--; }
					 */
					}
					return true;

				}
				if (pSceneTouchEvent.isActionUp()) {
					BowHUDButton.setCurrentTileIndex(39);

					BOW = false;
					return true;
				}

				return false;
			}
		};

		
		
		}
		
		
		this.shurikenhud = new AnimatedSprite(
				(int) ((this.screenwidth / 2) - 64), -1,
				this.HudDoodadsTexture, this.VBO) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {

					shurikentouch = true;
					// shurikenhud.setCurrentTileIndex(12);

					ShowText("SWIPE LEFT/RIGHT TO THROW", 100);
					/*
					 * if(stickman.shuriken > 0) { throwShuriken(stickman);
					 * stickman.shuriken--; }
					 */
					return true;

				}
				if (pSceneTouchEvent.isActionUp()) {
					shurikenhud.setCurrentTileIndex(8);

					shurikentouch = false;
					return true;
				}

				return false;
			}
		};

		this.swordthrowhud = new AnimatedSprite(15, this.screenheight - 225,
				this.HudDoodadsTexture, this.VBO) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {
					if (stickman.weapon > 0) {
						// swordthrowhud.setCurrentTileIndex(12);
						throwSword(stickman);
						if (stickman.weapon == 1) {
							changeWeapon(stickman, 0);
							return true;
						} else if (stickman.weapon == 2) {
							changeWeapon(stickman, 1);
							return true;
						}

					}
					return true;

				}
				if (pSceneTouchEvent.isActionUp()) {
					swordthrowhud.setCurrentTileIndex(9);

					return true;
				}

				return false;
			}

		};

		this.smokebombhud = new AnimatedSprite((int) (this.screenwidth / 2),
				-1, this.HudDoodadsTexture, this.VBO) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {

					ShowText("SWIPE DOWN TO TELEPORT", 100);
					/*
					 * if(stickman.smokebombs>0) {
					 * smokebombhud.setCurrentTileIndex(12);
					 * 
					 * stickman.isHit=false; SmokeBomb(stickman);
					 * stickman.smokebombs--; }
					 */
					return true;

				}
				if (pSceneTouchEvent.isActionUp()) {
					smokebombhud.setCurrentTileIndex(0);

					return true;
				}

				return false;
			}

		};

		if (Mode == Practice1on1) {
			
			
			this.addninjahud = new AnimatedSprite(75, this.screenheight - 225,
					this.HudDoodadsTexture, this.VBO) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
						final float pTouchAreaLocalX,
						final float pTouchAreaLocalY) {

					if (pSceneTouchEvent.isActionDown()) {
						if (fps.getFPS() > 15) {
							BoxTextCondition = true;
							int Ninjas = ninjaHeadCount();
							if (Ninjas > 30) {
								ShowText("Now this is just getting silly", 100);
							} else {
								if ((Ninjas / 2) == (Math.abs(Ninjas / 2))) {
									stickman.levelUp(1);
									FXplayerLevelUp();
									for (int x = 0; x < ninjaslist.size(); x++) {
										ninjaslist.get(x).levelUp(1);
									}
								}
								getninjafrompool();
								ShowText("VS " + ninjaHeadCount() + " Ninjas",
										100);

							}

						} else {
							ShowText("MAY CAUSE LAG OR CRASH", 100);
						}

						return true;

					}
					if (pSceneTouchEvent.isActionUp()) {
						return true;
					}

					return false;
				}

			};
			this.mHud.registerTouchArea(addninjahud);
			this.addninjahud.setCurrentTileIndex(6);
			this.mHud.attachChild(this.addninjahud);

		}

		if (this.displaymenukey == true) {
			this.menuhud = new AnimatedSprite(0, 0,
					this.menubuttonTextureRegion, this.VBO) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
						final float pTouchAreaLocalX,
						final float pTouchAreaLocalY) {

					if (pSceneTouchEvent.isActionDown()) {
						/* Attach the menu. */
						menuhud.setCurrentTileIndex(1);

						scene.setChildScene(mMenuScene, false, true, true);
						axisselectionMenuItem.setCurrentTileIndex(axisSwitch);
						gameispaused = true;
						smokebombhud.setVisible(false);
						shurikenhud.setVisible(false);
						if(ControlStick){AttackHUDButton.setVisible(false);
						BowHUDButton.setVisible(false);}
						ShurikenCountText.setVisible(false);
						SmokebombCountText.setVisible(false);
						return true;
					}

					if (pSceneTouchEvent.isActionUp()) {
						menuhud.setCurrentTileIndex(0);
						return true;
					}
					return false;

				}
			};
			this.menuhud.setCurrentTileIndex(0);
			this.mHud.registerTouchArea(menuhud);
			this.mHud.attachChild(this.menuhud);
			menuhud.setPosition(halfwidth - (menuhud.getWidth() / 2), height
					- menuhud.getHeight());
		}
		this.mHud.registerTouchArea(smokebombhud);
		this.mHud.registerTouchArea(shurikenhud);
		if(ControlStick){this.mHud.registerTouchArea(AttackHUDButton);
		this.mHud.registerTouchArea(BowHUDButton);}
		
		this.mHud.registerTouchArea(swordthrowhud);
		this.swordthrowhud.setCurrentTileIndex(9);
		this.smokebombhud.setCurrentTileIndex(0);
		this.shurikenhud.setCurrentTileIndex(8);
		if(ControlStick){this.AttackHUDButton.setCurrentTileIndex(40);
		this.BowHUDButton.setCurrentTileIndex(39);}
		//this.healthhud.setCurrentTileIndex(1);
		//this.mHud.attachChild(this.healthhud);
		this.mHud.attachChild(this.bankText);
		//this.mHud.attachChild(this.healtext);
		this.mHud.attachChild(this.shurikenhud);
		if(ControlStick){this.mHud.attachChild(this.AttackHUDButton);
		this.mHud.attachChild(this.BowHUDButton);}
		this.mHud.attachChild(this.swordthrowhud);
		this.mHud.attachChild(this.smokebombhud);
		this.mHud.attachChild(this.PopupText);
		

		// this.stickman.animate(new long[] { 100, 100, 100, 100,100,100 },
		// this.running, -1);//the last arg is the loopcount -1 makes it
		// continuous
		ChaseBlock = new Rectangle(0, 0, 1, 1, this.VBO);// may not need this
		this.scene.attachChild(this.stickman);
		this.stickman.attachChild(this.stickman.specialattack[2]);
		this.stickman.attachChild(this.stickman.specialattack[3]);
		stickman.isPlayer = true;
		// FXSmokePuffInstance = SetupGameObject(FXSmokepuffTexture);
		// this.scene.attachChild(FXSmokePuffInstance);

		/*
		 * int Practice1on1 = 1; int PracticeVsBoxes = 2; int EasyMode = 3; int
		 * NormalMode = 4; int GamerMode = 5;
		 */

		if (Mode == Practice1on1) {
			stickman.shuriken = 999;
			stickman.smokebombs = 999;
			stickman.arrows = 999;

			DropSword(stickman);
			DropSword(stickman);
		} else if (Mode == EasyMode) {
			stickman.arrows = 40;
			stickman.shuriken = 40;
			stickman.smokebombs = 10;
			startinglevel = 5;
		} else if (Mode == NormalMode) {
			stickman.arrows = 20;
			stickman.shuriken = 20;
			stickman.smokebombs = 5;
			startinglevel = 3;
		} else if (Mode == GamerMode) {
			startinglevel = 0;
			stickman.arrows = 0;
		}

		if (Mode == PracticeVsBoxes) {
			AllAlone = true;
			
			stickman.shuriken = 999;
			stickman.arrows = 999;
			DrawTargetBox();
		}

		//TODO     TEST MODE PARAMETERS %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		if (Mode == TestMode){
			stickman.shuriken = 999;
			stickman.arrows = 999;
			
			DropSword(stickman);
			DropSword(stickman);
			//stickman.weapon = 2;
			//stickman.currentweapon = 2;
		}//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		
		if (AllAlone == false) {
			GameCharacter firstninja = getninjafrompool();
			ninjasspawned++;
			firstninja.currenthealth = 10;
			// this.scene.attachChild(firstninja);
			// firstninja.RandomizeMe = true;
		}

		// implement starting level

		stickman.levelUp(startinglevel);

		FXGroundPoundInstance = SetupGameObject(FXSidepuffsTexture);
		FXGroundPoundInstance.setVisible(false);
		FXGroundPoundInstance.disabled = true;
		this.scene.attachChild(FXGroundPoundInstance);

		FXGroundPoundInstance.self = new Rectangle(0, 0, 128, 64, this.VBO);
		FXGroundPoundInstance.attachChild(FXGroundPoundInstance.self);
		FXGroundPoundInstance.self.setVisible(false);

		Levelupsprite = SetupGameObject(FXLevelUpTexture);
		// Levelupsprite = new GameObject(0,0, this.FXLevelUpTexture, this.VBO);
		Smokebombpickupsprite = SetupGameObject(FXSmokeBombPickupTexture);
		SlowMosprite = SetupGameObject(FXSlowMoTexture);
		Shurikengrabsprite = SetupGameObject(FXShurikengrabTexture);
		Healthupsprite = SetupGameObject(FXHealthUpTexture);
		Newninjasprite = SetupGameObject(FXNewNinjaTexture);

		Healthupsprite.setVisible(false);
		Levelupsprite.setVisible(false);
		Newninjasprite.setVisible(false);
		Shurikengrabsprite.setVisible(false);

		Smokebombpickupsprite.setVisible(false);
		SlowMosprite.setVisible(false);
		// sprite.animate(1, blank);
		FXbox.attachChild(Levelupsprite);
		FXbox.attachChild(Shurikengrabsprite);
		FXbox.attachChild(Healthupsprite);

		FXbox.attachChild(Newninjasprite);
		FXbox.attachChild(Smokebombpickupsprite);
		FXbox.attachChild(SlowMosprite);

		this.mCamera.setChaseEntity(camerabox);

		soundbank.playSmallGong(SFX);

		
		if(ControlStick){
			scene.setChildScene(MovementAOSC);
			MovementAOSC.setChildScene(BowAOSC);
			}else{
				scene.setChildScene(BowAOSC);
				}
		this.mEngine.setScene(scene);
		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	private float BowAOSCPostionX() {
		
		float x = 0f;
		if(!ControlStick){
		x = (float) (screenwidth - this.mOnScreenControlBaseTextureRegion.getWidth() - 30);
		}
		else
		{
			x = MainButtonPlacer.getX();
		}
		
		return x;
	}
	
	private float BowAOSCPostionY() {
		float y = 0f;
		if(!ControlStick){
		y = (float) (screenheight - this.mOnScreenControlBaseTextureRegion.getHeight() - 30);
		}
		else
		{
			y = MainButtonPlacer.getY();
		}
		
		return y;
	}

	private void RandomizeBloodSplatter() {
		// TODO Auto-generated method stub
		String orderstring = "";
		
		        bloodlist = new ArrayList<Integer>();
		        for (int i=1; i<8; i++) {
		            bloodlist.add(new Integer(i));
		        }
		        Collections.shuffle(bloodlist);
		        for (int i=0; i<7; i++) {
		        	orderstring = orderstring+Integer.toString(bloodlist.get(i));
		        }
		  
			Debug.d("bloodspatter order is "+orderstring);
	}

	void LevelSelect() {
		/*
		 * int Practice1on1 = 1; int PracticeVsBoxes = 2; int EasyMode = 3; int
		 * NormalMode = 4; int GamerMode = 5;
		 */

		playLevel = 2;
		if (Mode == PracticeVsBoxes) {
			playLevel = 3;
		}

		if ((Mode == GamerMode) || (Mode == NormalMode)) // LevelR is the
															// randomly
															// generated level
		{
			LevelR CurrentLevel = new LevelR(VBO, this.scene, horizontals,
					verticals, Color.BLACK);
			this.levelstring = CurrentLevel.LevelString;
			return;
		}

		if (playLevel == 1) {
			// old level
			Level1 CurrentLevel = new Level1(VBO, this.scene, horizontals);
		} else if (playLevel == 2) {
			Level2 CurrentLevel = new Level2(VBO, this.scene, horizontals,
					verticals);

		} else if (playLevel == 3) { // LevelP is practice level
			LevelP CurrentLevel = new LevelP(VBO, this.scene, horizontals,
					verticals);

		}

		if (playLevel == 6) {
			LevelSlope CurrentLevel = new LevelSlope(VBO, this.scene,
					horizontals, verticals);
		}

	}

	String tooltip(int tipnumber) {

		/*
		 * int Practice1on1 = 1; int PracticeVsBoxes = 2; int EasyMode = 3; int
		 * NormalMode = 4; int GamerMode = 5;
		 */

		if (Mode == PracticeVsBoxes) {
			return "ATTACK THE RED BOX";
		}

		FlurryAgent.logEvent("Tipnumber " + tipnumber);

		if (tipnumber == 0) {
			return getString(R.string.a);
		} else if (tipnumber == 1) {
			return getString(R.string.b);
		} else if (tipnumber == 2) {
			return getString(R.string.c);
		} else if (tipnumber == 3) {
			return getString(R.string.d);
		} else if (tipnumber == 4) {
			return getString(R.string.e);
		} else if (tipnumber == 5) {
			return getString(R.string.f);
		} else if (tipnumber == 6) {
			return getString(R.string.g);
		} else if (tipnumber == 7) {
			return getString(R.string.h);
		} else if (tipnumber == 8) {
			return getString(R.string.i);
		} else if (tipnumber == 9) {
			return getString(R.string.j);
		} else if (tipnumber == 10) {
			return getString(R.string.k);
		} else if (tipnumber == 11) {
			return getString(R.string.l);
		} else if (tipnumber == 12) {
			return getString(R.string.m);
		} else if (tipnumber == 13) {
			return getString(R.string.n);
		} else if (tipnumber == 14) {
			return getString(R.string.o);
		} else if (tipnumber == 15) {
			return getString(R.string.p);
		} else if (tipnumber == 16) {
			return getString(R.string.q);
		} else if (tipnumber == 17) {
			return getString(R.string.r);
		} else if (tipnumber == 18) {
			return getString(R.string.s);
		} else if (tipnumber == 19) {
			return getString(R.string.t);
		} else if (tipnumber == 20) {
			return getString(R.string.u);
		} else if (tipnumber == 21) {
			return getString(R.string.v);
		} else if (tipnumber == 22) {
			return getString(R.string.w);
		} else if (tipnumber == 23) {
			return getString(R.string.x);
		} else if (tipnumber == 24) {
			return getString(R.string.y);
		} else if (tipnumber == 25) {
			return getString(R.string.z);
		} else if (tipnumber == 26) {
			return getString(R.string.aa);
		} else if (tipnumber == 27) {
			return getString(R.string.bb);
		} else if (tipnumber == 28) {
			return getString(R.string.cc);
		} else if (tipnumber == 29) {
			return getString(R.string.dd);
		} else if (tipnumber == 30) {
			return getString(R.string.ee);
		} else if (tipnumber == 31) {
			return getString(R.string.ff);
		} else if (tipnumber == 32) {
			return getString(R.string.gg);
		} else if (tipnumber == 33) {
			return getString(R.string.hh);
		} else if (tipnumber == 34) {
			return getString(R.string.ii);
		} else if (tipnumber == 35) {
			return getString(R.string.jj);
		} else if (tipnumber == 36) {
			return getString(R.string.kk);
		} else if (tipnumber == 37) {
			return getString(R.string.ll);
		} else if (tipnumber == 38) {
			return getString(R.string.mm);
		} else if (tipnumber == 39) {
			return getString(R.string.nn);
		} else if (tipnumber == 40) {
			return getString(R.string.oo);
		} else if (tipnumber == 41) {
			return getString(R.string.pp);
		} else if (tipnumber == 42) {
			return getString(R.string.qq);
		} else if (tipnumber == 43) {
			return getString(R.string.rr);
		} else if (tipnumber == 44) {
			return getString(R.string.ss);
		} else if (tipnumber == 45) {
			return getString(R.string.tt);
		} else if (tipnumber == 46) {
			return getString(R.string.uu);
		} else if (tipnumber == 47) {
			return getString(R.string.vv);
		} else if (tipnumber == 48) {
			return getString(R.string.ww);
		} else if (tipnumber == 49) {
			return getString(R.string.xx);
		} else if (tipnumber == 50) {
			return getString(R.string.yy);
		} else if (tipnumber == 51) {
			return getString(R.string.zz);
		} else if (tipnumber == 52) {
			return getString(R.string.aaa);
		} else if (tipnumber == 53) {
			return getString(R.string.bbb);
		} else if (tipnumber == 54) {
			return getString(R.string.ccc);
		} else if (tipnumber == 55) {
			return getString(R.string.ddd);
		} else if (tipnumber == 56) {
			return getString(R.string.eee);
		} else if (tipnumber == 57) {
			return getString(R.string.fff);
		} else if (tipnumber == 58) {
			return getString(R.string.ggg);
		} else if (tipnumber == 59) {
			return getString(R.string.hhh);
		} else if (tipnumber == 60) {
			return getString(R.string.iii);
		} else
			return "GO KILL NINJAS!";
		/*
		 * else if(tipnumber == 61){return getString(R.string.);} else
		 * if(tipnumber == 62){return getString(R.string.);} else if(tipnumber
		 * == 63){return getString(R.string.);} else if(tipnumber == 64){return
		 * getString(R.string.);} else if(tipnumber == 65){return
		 * getString(R.string.);} else if(tipnumber == 66){return
		 * getString(R.string.);} else if(tipnumber == 67){return
		 * getString(R.string.);} else if(tipnumber == 68){return
		 * getString(R.string.);} else if(tipnumber == 69){return
		 * getString(R.string.);} else if(tipnumber == 70){return
		 * getString(R.string.);} else if(tipnumber == 71){return
		 * getString(R.string.);} else if(tipnumber == 72){return
		 * getString(R.string.);} else if(tipnumber == 73){return
		 * getString(R.string.);} else if(tipnumber == 74){return
		 * getString(R.string.);} else if(tipnumber == 75){return
		 * getString(R.string.);} else if(tipnumber == 76){return
		 * getString(R.string.);} else if(tipnumber == 77){return
		 * getString(R.string.);} else if(tipnumber == 78){return
		 * getString(R.string.);} else if(tipnumber == 79){return
		 * getString(R.string.);} else if(tipnumber == 80){return
		 * getString(R.string.);}
		 */
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();

	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {

		return false;
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		
		if(ControlStick){return;}
		
		if (axisSwitch == 0) {
			accellerometerSpeedX = pAccelerationData.getX();
			accellerometerSpeedY = pAccelerationData.getY();
			accellerometerSpeedZ = pAccelerationData.getZ();
		} else if (axisSwitch == 1) {
			accellerometerSpeedX = pAccelerationData.getX();
			accellerometerSpeedZ = pAccelerationData.getY();
			accellerometerSpeedY = pAccelerationData.getZ();

		} else if (axisSwitch == 2) {
			accellerometerSpeedY = pAccelerationData.getX();
			accellerometerSpeedX = pAccelerationData.getY();
			accellerometerSpeedZ = pAccelerationData.getZ();
		} else if (axisSwitch == 3) {
			accellerometerSpeedY = pAccelerationData.getX();
			accellerometerSpeedZ = pAccelerationData.getY();
			accellerometerSpeedX = pAccelerationData.getZ();
		} else if (axisSwitch == 4) {
			accellerometerSpeedZ = pAccelerationData.getX();
			accellerometerSpeedX = pAccelerationData.getY();
			accellerometerSpeedY = pAccelerationData.getZ();
		} else if (axisSwitch == 5) {
			accellerometerSpeedZ = pAccelerationData.getX();
			accellerometerSpeedY = pAccelerationData.getY();
			accellerometerSpeedX = pAccelerationData.getZ();
		}

		if (invertXaxis == true) {
			accellerometerSpeedX = -1 * accellerometerSpeedX;
		}

		if (invertYaxis == true) {
			accellerometerSpeedY = -1 * accellerometerSpeedY;
		}

		if (accellerometerSpeedZ < 0) {
			zflip = true;
		} else {
			zflip = false;
		}

		Debug.d("AccelerometerSpeedX = "+accellerometerSpeedX+" AccelerometerSpeedY = "+accellerometerSpeedY);
		
		Physics.Yaccelcheck(stickman, accellerometerSpeedY, zflip);
		Physics.grabvectorx(stickman, accellerometerSpeedX);
		// Debugger("accel","x: "+ accellerometerSpeedX + "  Y: "+
		// accellerometerSpeedY +"   z: "+accellerometerSpeedZ);
		Yoffset = preferences.getFloat("Yoffset", 0.0f);
		MoveMemory(accellerometerSpeedX, accellerometerSpeedY - Yoffset);

	}

	float magnitude(float x, float y) {
		return (float) Math.abs(Math.sqrt((x * x) + (y * y)));
	}

	private void MoveMemory(float acX, float acY) {
		int movedirection = 0;
		boolean l;
		boolean r;
		boolean u;
		boolean d;

		if (acX > 1) {
			r = true;
		} else {
			r = false;
		}
		if (acX < -1) {
			l = true;
		} else {
			l = false;
		}
		if (acY > 1) {
			d = true;
		} else {
			d = false;
		}
		if (acY < -1) {
			u = true;
		} else {
			u = false;
		}

		if ((u == true) && (d == false) && (l == false) && (r == false)) {
			movedirection = 1;
		} else if ((u == true) && (d == false) && (l == false) && (r == true)) {
			movedirection = 2;
		} else if ((u == false) && (d == false) && (l == false) && (r == true)) {
			movedirection = 3;
		} else if ((u == false) && (d == true) && (l == false) && (r == true)) {
			movedirection = 4;
		} else if ((u == false) && (d == true) && (l == false) && (r == false)) {
			movedirection = 5;
		} else if ((u == false) && (d == true) && (l == true) && (r == false)) {
			movedirection = 6;
		} else if ((u == false) && (d == false) && (l == true) && (r == false)) {
			movedirection = 7;
		} else if ((u == true) && (d == false) && (l == true) && (r == false)) {
			movedirection = 8;
		} else if ((u == false) && (d == false) && (l == false) && (r == false)) {
			movedirection = 0;
		}

		// Debugger("movedirection = "+movedirection);
		if (movedirection == pastdirections[0]) // return if direction is
												// unchanged
		{
			directiontimer++;

			return;
		}

		for (int g = 5; g >= 1; g--)

		{
			int x = g - 1;
			pastdirections[g] = pastdirections[x];
			movetimer[g] = movetimer[x];

			// Debugger("Rolling space " + x +
			// "("+pastdirections[x]+") to space "+ g + "("+g+")" );
		}

		movetimer[0] = directiontimer;
		directiontimer = 0;
		pastdirections[0] = movedirection;
		// Debugger("Pastdirections "+ pastdirections[0] +" "+ pastdirections[1]
		// +" "+ pastdirections[2] +" "+ pastdirections[3] +" "+
		// pastdirections[4] +" "+ pastdirections[5] );

		int movementtime = movetimer[0] + movetimer[1] + movetimer[2];

		if (pastdirections[0] == 3) {
			if (pastdirections[1] == 4) {
				if (pastdirections[2] == 5) {
					if (movementtime < 25) {
						ClearMoveMemory();
						HadokenreadyRight = true;
						HadokenReadyTimer = 1;

					}
				}
			}
		}

		if (pastdirections[0] == 2) {
			if (pastdirections[1] == 3) {
				if (pastdirections[2] == 4) {
					if (movementtime < 25) {
						ClearMoveMemory();
						HadokenreadyRight = true;
						HadokenReadyTimer = 1;

					}
				}
			}
		}

		if (pastdirections[0] == 4) {
			if (pastdirections[1] == 5) {
				if (pastdirections[2] == 6) {
					if (movementtime < 25) {
						ClearMoveMemory();
						HadokenreadyRight = true;
						HadokenReadyTimer = 1;

					}
				}
			}
		}

		if (pastdirections[0] == 7) {
			if (pastdirections[1] == 6) {
				if (pastdirections[2] == 5) {
					if (movementtime < 25) {
						ClearMoveMemory();
						HadokenreadyLeft = true;
						HadokenReadyTimer = 1;
					}

				}
			}
		}

		if (pastdirections[0] == 6) {
			if (pastdirections[1] == 5) {
				if (pastdirections[2] == 4) {
					if (movementtime < 25) {
						ClearMoveMemory();
						HadokenreadyLeft = true;
						HadokenReadyTimer = 1;
					}

				}
			}
		}

		if (pastdirections[0] == 8) {
			if (pastdirections[1] == 7) {
				if (pastdirections[2] == 6) {
					if (movementtime < 25) {
						ClearMoveMemory();
						HadokenreadyLeft = true;
						HadokenReadyTimer = 1;
					}

				}
			}
		}

	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {

	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();

		this.gameispaused = false;
		smokebombhud.setVisible(true);
		shurikenhud.setVisible(true);
		if(ControlStick){AttackHUDButton.setVisible(true);
		BowHUDButton.setVisible(true);}
		ShurikenCountText.setVisible(true);
		SmokebombCountText.setVisible(true);
		FlurryAgent.endTimedEvent("Game Paused");
		this.enableAccelerationSensor(this);
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();

		this.gameispaused = true;
		smokebombhud.setVisible(false);
		shurikenhud.setVisible(false);
		if(ControlStick){AttackHUDButton.setVisible(false);
		BowHUDButton.setVisible(false);}
		ShurikenCountText.setVisible(false);
		SmokebombCountText.setVisible(false);
		FlurryAgent.logEvent("Game Paused", true);
		this.disableAccelerationSensor();
	}

	@Override
	public void onGameDestroyed() {
		super.onGameDestroyed();
		this.dbase.close();

		stopService(soundservice);

	}

	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {

		if (pKeyCode == KeyEvent.KEYCODE_HOME
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(this, com.smiths.StickmanSplash.class);
			FlurryAgent.endTimedEvent(mode);
			FlurryAgent.logEvent("Home button exit from gameplay");

			ConfirmationButtion(intent);

			return true;
		}

		if (pKeyCode == KeyEvent.KEYCODE_BACK
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(this, com.smiths.StickmanSplash.class);
			FlurryAgent.endTimedEvent(mode);
			FlurryAgent.logEvent("Back button exit from gameplay");

			ConfirmationButtion(intent);

			return true;
		}
		if (pKeyCode == KeyEvent.KEYCODE_MENU
				&& pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			// if(this.scene.getChildByTag(111)) {
			if (menuOpen == true) {
				Debugger("already has child scene");
				/* Remove the menu and reset it. */
				FlurryAgent.logEvent("closed menu");
				this.mMenuScene.back();
				menuOpen = false;
				this.gameispaused = false;
				smokebombhud.setVisible(true);
				shurikenhud.setVisible(true);
				if(ControlStick){AttackHUDButton.setVisible(true);
				BowHUDButton.setVisible(true);}
				ShurikenCountText.setVisible(true);
				SmokebombCountText.setVisible(true);
			} else {
				/* Attach the menu. */
				Debugger("opened menu");
				menuOpen = true;
				this.scene.setChildScene(this.mMenuScene, false, true, true);
				axisselectionMenuItem.setCurrentTileIndex(axisSwitch);
				this.gameispaused = true;
				mMenuScene.setTag(111);
				smokebombhud.setVisible(false);
				shurikenhud.setVisible(false);
				if(ControlStick){AttackHUDButton.setVisible(false);
				BowHUDButton.setVisible(false);}
				ShurikenCountText.setVisible(false);
				SmokebombCountText.setVisible(false);
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}

	private void ConfirmationButtion(Intent intent) {

		ShowText("PRESS TWICE TO EXIT", (int) this.screenheight / 2);
		if (backbuttonpress1 == false) {
			backbuttontimer = 1;
			backbuttonpress1 = true;
			return;
		}

		startActivity(intent);

		this.finish();
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		// rename for animation listeners?
		case 0:
			resetMenuItem.animate(100, 1, this.buttonanimationlistener);
			return true;

		case 1:
			quitMenuItem.animate(100, 1, this.buttonanimationlistener1);
			return true;

		case 2:
			recalMenuItem.animate(100, 1, this.buttonanimationlistener2);
			return true;

		case 3:
			muteMenuItem.animate(100, 1, this.buttonanimationlistener3);
			return true;

		case 4:
			axisselectionMenuItem.animate(0, 0, this.buttonanimationlistener4);

			return true;

		case 5:
			axismode0MenuItem.animate(100, 1, this.buttonanimationlistener5);
			return true;

		case 6:
			axismode1MenuItem.animate(100, 1, this.buttonanimationlistener6);
			return true;

		case 7:
			axismode2MenuItem.animate(100, 1, this.buttonanimationlistener7);
			return true;

		case 8:
			axismode3MenuItem.animate(100, 1, this.buttonanimationlistener8);
			return true;

		case 9:
			axismode4MenuItem.animate(100, 1, this.buttonanimationlistener9);
			return true;

		case 10:
			axismode5MenuItem.animate(100, 1, this.buttonanimationlistener10);
			return true;

		case 11:
			axisinvertMenuItem.animate(100, 1, this.buttonanimationlistener11);
			return true;

		case 12:
			axismodeXinvertMenuItem.animate(100, 1,
					this.buttonanimationlistener12);
			return true;

		case 13:
			axismodeYinvertMenuItem.animate(100, 1,
					this.buttonanimationlistener13);
			return true;

		default:
			return false;
		}
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {

		TextTimers();
		bloodTimer();
		NinjaFrenzyTimer();
		
		if (backbuttontimer > 0) {

			if (backbuttontimer == 1) {
				gameispaused = !gameispaused;
			}
			backbuttontimer++;

			if (backbuttontimer > 26) {
				backbuttonpress1 = false;
				backbuttontimer = 0;

			}

		}

		if (this.gameispaused == true) { // pauses everything below here
			return;
		}

		if (ChargeTimer != 0) {
			ChargeTimer++;
		}

		if (Mode == PracticeVsBoxes) {
			for (int x = 0; x < 5; x++) {
				updateHitCollision(x);
			}
		}

		if (stickman.isHit == true) {
			if (stickman.killstreak > 1) {
				FXShowStreak(stickman.killstreak);
			}

			stickman.killstreak = 0;

		}

		if (tips == true) {

			PopupText.setVisible(true);
			tipcounter++;
			if (tipcounter < 100) {

				PopupText.setPosition((int) halfwidth
						- (PopupText.getWidth() / 2), halfheight - 50);
				PopupText.setVisible(true);

			} else {
				PopupText.setVisible(false);
				PopupText.detachSelf();
				tips = false;
			}

		}
		//
		HadokenFury();
		ComboLoop();
		PowerUpCombiner();
		GameOverDialog(stickman.GameIsOver);
		SlowMotionTimer();
		hitCheck(stickman);
		if (slowmoskip == false) {
			Physics.updatePosition(stickman, FXbox);
			SwordPosition();
			CorpsePosition();
			PowerUpPosition();
			AngelPosition();
			ShurikenPosition();
			HadokenPosition();
		}
		CameraChase();
		animator.Animationswitch(stickman, SloMoON);
		PowerupCollision();

		corpseSliding();
		FXGroundPound();
		ArrowUpdate();
		WhoIsClosestNinja();
		NinjaLoop();
		MoreNinjas();
		FXCleanup();
		WaveTimer();

		updateHUD();

		swordRecovery(stickman);
		BugTrap();

	}

	private void NinjaFrenzyTimer() {
		
		if(stickman.NinjaFrenzy==true)
		{
			NinjaFrenzy++;
			if(NinjaFrenzy> NinjaFrenzyLimit)
			{
				stickman.NinjaFrenzy = false;
				NinjaFrenzy = 0;
			}
		}
	}

	@Override
	public void reset() {

	}

	// ABSTRACTIONS RULE!!!!!
	private GameObject SetupGameObject(TiledTextureRegion tempTexture) {
		GameObject temporary;
		temporary = new GameObject(0, 0, tempTexture, this.VBO);
		temporary.setVisible(false);
		return temporary;
	}

	private TiledTextureRegion SetupTiledTexture(
			BuildableBitmapTextureAtlas newAtlas, int AtlasWidth,
			int AtlasHeight, String ImageName, int Rows, int Columns) {

		TiledTextureRegion tiledTexture;
		newAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(),
				AtlasWidth, AtlasHeight, TextureOptions.NEAREST);
		tiledTexture = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(newAtlas, this, ImageName, Rows, Columns);
		try {
			newAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 0, 1));
			newAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		return tiledTexture;
	}

	private void Popup(String say_me)// hook for easy debug dialogs
	{
		Toast.makeText(this, say_me, Toast.LENGTH_LONG).show();
	}

	private void BugTrap()// prints state variables of the player to log
	{
		if ((debug == true) && (shurikentouch == true)) {
			// if(stickman. == true){Debugger(" ");}
			if (stickman.isSliding == true) {
				Debugger("isSliding");
			}
			if (stickman.jumpAttempt == true) {
				Debugger("jumpAttempt");
			}
			if (stickman.startattack == true) {
				Debugger("startattack");
			}
			if (stickman.currentlyattacking == true) {
				Debugger("currentlyattacking");
			}
			if (stickman.flipped == true) {
				Debugger("flipped");
			}
			if (stickman.groundpound == true) {
				Debugger("groundpound");
			}
			if (stickman.groundpounded == true) {
				Debugger("groundpounded");
			}
			if (stickman.touching == true) {
				Debugger("touching");
			}
			if (stickman.collision == true) {
				Debugger("collision");
			}
			if (stickman.currentlyjumping == true) {
				Debugger("currentlyjumping");
			}
			if (stickman.rising == true) {
				Debugger("rising");
			}
			if (stickman.falling == true) {
				Debugger("falling");
			}
			if (stickman.isCrouching == true) {
				Debugger("isCrouching");
			}
			if (stickman.isHit == true) {
				Debugger("isHit");
			}
			if (stickman.jumpAttempt == true) {
				Debugger("jumpAttempt");
			}
			if (stickman.isAlive == true) {
				Debugger("isAlive");
			}

		}

	}

	private void createMenuScene() {
		this.mMenuScene = new MenuScene(this.mCamera);

		resetMenuItem = new AnimatedSpriteMenuItem(0,
				this.mMenuResumeTextureRegion, VBO);
		resetMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		resetMenuItem.setCurrentTileIndex(0);
		this.mMenuScene.addMenuItem(resetMenuItem);

		quitMenuItem = new AnimatedSpriteMenuItem(1,
				this.mMenuQuitTextureRegion, VBO);
		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		quitMenuItem.setCurrentTileIndex(0);
		this.mMenuScene.addMenuItem(quitMenuItem);

		recalMenuItem = new AnimatedSpriteMenuItem(2,
				this.mMenuRecalTextureRegion, VBO);
		recalMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		recalMenuItem.setCurrentTileIndex(0);
		this.mMenuScene.addMenuItem(recalMenuItem);

		muteMenuItem = new AnimatedSpriteMenuItem(3,
				this.mMenuMuteTextureRegion, VBO);
		muteMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		muteMenuItem.setCurrentTileIndex(0);
		this.mMenuScene.addMenuItem(muteMenuItem);

		axisselectionMenuItem = new AnimatedSpriteMenuItem(4,
				this.axisselectionTextureRegion, VBO);
		axisselectionMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axisselectionMenuItem.setCurrentTileIndex(axisSwitch);
		this.mMenuScene.addMenuItem(axisselectionMenuItem);

		axisinvertMenuItem = new AnimatedSpriteMenuItem(11,
				this.axisinvertTextureRegion, VBO);
		axisinvertMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axisinvertMenuItem.setCurrentTileIndex(0);
		this.mMenuScene.addMenuItem(axisinvertMenuItem);

		this.mMenuScene.buildAnimations();

		this.mMenuScene.setBackgroundEnabled(true);

		this.mMenuScene.setOnMenuItemClickListener(this);
	}

	private void createAxisInvertMenuScene() {

		this.menuSceneAxisInvert = new MenuScene(this.mCamera);

		axismodeXinvertMenuItem = new AnimatedSpriteMenuItem(12,
				this.axismodeXinvertTextureRegion, VBO);
		axismodeXinvertMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismodeXinvertMenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisInvert.addMenuItem(axismodeXinvertMenuItem);

		axismodeYinvertMenuItem = new AnimatedSpriteMenuItem(13,
				this.axismodeYinvertTextureRegion, VBO);
		axismodeYinvertMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismodeYinvertMenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisInvert.addMenuItem(axismodeYinvertMenuItem);

		this.menuSceneAxisInvert.buildAnimations();

		this.menuSceneAxisInvert.setBackgroundEnabled(true);

		this.menuSceneAxisInvert.setOnMenuItemClickListener(this);
	}

	private void createAxisMenuScene() {

		this.menuSceneAxisSelection = new MenuScene(this.mCamera);
		/*
		 * axismodeXinvertMenuItem; axismodeYinvertMenuItem;
		 */
		axismode0MenuItem = new AnimatedSpriteMenuItem(5,
				this.axismode0TextureRegion, VBO);
		axismode0MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode0MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode0MenuItem);

		axismode1MenuItem = new AnimatedSpriteMenuItem(6,
				this.axismode1TextureRegion, VBO);
		axismode1MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode1MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode1MenuItem);

		axismode2MenuItem = new AnimatedSpriteMenuItem(7,
				this.axismode2TextureRegion, VBO);
		axismode2MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode2MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode2MenuItem);

		axismode3MenuItem = new AnimatedSpriteMenuItem(8,
				this.axismode3TextureRegion, VBO);
		axismode3MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode3MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode3MenuItem);

		axismode4MenuItem = new AnimatedSpriteMenuItem(9,
				this.axismode4TextureRegion, VBO);
		axismode4MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode4MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode4MenuItem);

		axismode5MenuItem = new AnimatedSpriteMenuItem(10,
				this.axismode5TextureRegion, VBO);
		axismode5MenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA,
				GLES20.GL_ONE_MINUS_SRC_ALPHA);
		axismode5MenuItem.setCurrentTileIndex(0);
		this.menuSceneAxisSelection.addMenuItem(axismode5MenuItem);

		this.menuSceneAxisSelection.buildAnimations();

		this.menuSceneAxisSelection.setBackgroundEnabled(true);

		this.menuSceneAxisSelection.setOnMenuItemClickListener(this);
	}

	void SelectAxis(int Axis) {
		axisSwitch = Axis;
		Physics.calibrateaccellerometers = true;
		axisselectionMenuItem.setCurrentTileIndex(axisSwitch);
		settings_editor.putInt("AxisSwitch", Axis);
		settings_editor.commit();
		menuSceneAxisSelection.back();
	}

	private void createHud() {
		this.mHud = new HUD();
		this.mCamera.setHUD(mHud);

		this.mHud.registerUpdateHandler(new TimerHandler(1 / 20.0f, true,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						bankText.setText(Integer.toString(money));
						//healtext.setText(Integer.toString((int) (stickman.currenthealth) / 10));
					}
				}));
	}

	private void ToggleMusic() {

		if (preferences.getBoolean("mute", false) == true) {

			stopService(soundservice);
		}
		if (preferences.getBoolean("mute", false) == false) {

			startService(soundservice);
		}
	}

	private void SlowMotionTimer() {

		if (stickman.slowmotimer > 0) {
			stickman.slowmotimer++;
			SloMoON = true;
			eightFrameVARIABLE = eightFrameSLOW;
			boolean temp;
			if (slowmoskip == true) {
				temp = false;
			} else {
				temp = true;
			}
			slowmoskip = temp;

			if (stickman.slowmotimer == 125) {
				soundbank.playTimeUP(SFX);
			}

			if (stickman.slowmotimer > 200) {
				eightFrameVARIABLE = eightFrameFAST;
				SloMoON = false;
				slowmoskip = false;
				stickman.slowmotimer = 0;

				return;
			}

		} else {
			eightFrameVARIABLE = eightFrameFAST;
			SloMoON = false;
			slowmoskip = false;
		}

	}

	private void CameraChase()
	// updates the position that dictates the camera center and follows stickman
	{
		int distanceX = (int) Math.abs(stickman.xpos - camerabox.getX());
		int distanceY = (int) Math.abs(stickman.ypos - camerabox.getY());
		if (distanceX > cameradistance) {
			if (stickman.xpos > camerabox.getX()) {
				camerabox.setX(stickman.xpos - cameradistance);
			} else {
				camerabox.setX(stickman.xpos + cameradistance);
			}
		}

		if (distanceY > cameradistance) {
			if (stickman.ypos > camerabox.getY()) {
				camerabox.setY(stickman.ypos - cameradistance);
			} else {
				camerabox.setY(stickman.ypos + cameradistance);
			}

		}
		camerabox.setPosition(camerabox.getX(), camerabox.getY());
	}

	public void WhoIsClosestNinja(){
		HowClose = 999998;
		for (int c = 0; c < ninjaslist.size(); c++) {
			CurrentNinja = ninjaslist.get(c);
		
		
			float xdist = (float) Math.abs(CurrentNinja.xpos - stickman.xpos);
			if ((xdist < HowClose)&&(CurrentNinja.isAlive)&&(!CurrentNinja.disabled)){
			HowClose = xdist;
			WhosClosest = c;
			}
		
		
		
		CurrentNinja.myTurn = false;
		}// end of ninja loop
		
		if(ninjaslist.size()>0){
				if(!ninjaslist.get(WhosClosest).disabled)
				{
					ninjaslist.get(WhosClosest).myTurn = true;
			}
		}
	}
	
	
	private void NinjaLoop() {
		if (ninjaslist.size() > stickman.mostactiveninjas) // tallies highest
															// number enemies
															// fought at once
															// each game
		{
			stickman.mostactiveninjas = ninjaslist.size();
		}

		if (shurikenlist.size() > 0) {
			for (int j = 0; j < shurikenlist.size(); j++) {
				Shuriken shuriken = shurikenlist.get(j);
				shuriken.startattack = true;
				shuriken.isShuriken = true;
				updateHitCollision(stickman, shuriken);
			}
		}
		//if(ninjaslist.get(WhosClosest)==null){WhoIsClosestNinja();}
		
		for (int i = 0; i < ninjaslist.size(); i++) {// beginning of ninja loop
			CurrentNinja = ninjaslist.get(i);
			
			
			if ((CurrentNinja.loopcount > 50) && (!CurrentNinja.isPlayer)) // keeps
																			// ninjas
																			// from
																			// doing
																			// the
																			// same
																			// action
																			// over
																			// and
																			// over
			{
				if (CurrentNinja.disabled == false) {
					CurrentNinja.RandomizeMe = true;
					CurrentNinja.setVisible(false);
					// FXSmokePuff(CurrentNinja);
				}
			}

			RandomizeNinjaLocation(CurrentNinja);
			
			if (CurrentNinja.disabled == false) {

				if (CurrentNinja.RandomizeMe == false) 
				{
					if (CurrentNinja.dropweapontimer != 0) 
					{
						CurrentNinja.dropweapontimer++;
						
						if (CurrentNinja.dropweapontimer > 50) 
						{
							CurrentNinja.dropweapontimer = 0;
						}
					}
					hitCheck(CurrentNinja);

					if (slowmoskip == false) {
						Physics.UpdateEnemySpritePosition(CurrentNinja,
								stickman, i);
					}

					if ((WaveEnded == true) && (CurrentNinja.isBoss == false)) {
						int ydist = (int) Math.abs(CurrentNinja.ypos
								- camerabox.getY());
						if (ydist > (halfheight + 50)) {
							sendninjatopool(CurrentNinja);
						}

					}

					animator.Animationswitch(CurrentNinja, SloMoON);

					updateHitCollision(CurrentNinja, stickman);

					updateHitCollision(stickman, CurrentNinja);

					if ((FXGroundPoundInstance.isAnimationRunning() == true)
							&& (SloMoON == false)) {
						FXGroundPoundInstance.hitPower = stickman.hitPower;

						updateHitCollision(CurrentNinja, FXGroundPoundInstance);

					}

					swordRecovery(CurrentNinja);

					if ((CurrentNinja.level > 3)
							|| (CurrentNinja.isBoss == true)) {

						CurrentNinja.shurikenTimer++;

						if (CurrentNinja.shurikenTimer > (CurrentNinja.attackspeed / 2)) {
							if ((CurrentNinja.throwingrange == true)
									&& (CurrentNinja.isHit == false)) {
								int throwseed = (int) (Math.random() * 50);
								if (throwseed < CurrentNinja.level) {
									CurrentNinja.shurikenTimer = 1;
									throwShuriken(CurrentNinja,
											CurrentNinja.xdirection);
								}
							}
						}

					}
					for (int s = 0; s < swordlist.size(); s++) {
						if (swordlist.get(s).isThrown == true) {
							if (swordlist.get(s).thrower.isPlayer == true) {
								updateHitCollision(CurrentNinja,
										swordlist.get(s));
							} else {
								updateHitCollision(stickman, swordlist.get(s));
							}
						}
					}
					// corpse collision
					if (corpselist.size() > 0) {
						for (int j = 0; j < corpselist.size(); j++) {
							Corpse corpse = corpselist.get(j);

							if (corpse.collision == false) {
								corpse.startattack = true;
								updateHitCollision(CurrentNinja, corpse);
							}
						}
					}

					if (shurikenlist.size() > 0) {
						for (int j = 0; j < shurikenlist.size(); j++) {
							Shuriken shuriken = shurikenlist.get(j);

							shuriken.startattack = true;
							shuriken.isShuriken = true;
							updateHitCollision(CurrentNinja, shuriken);
						}
					}
					if (hadokenlist.size() > 0) {
						for (int h = 0; h < hadokenlist.size(); h++) {
							Hadoken hadoken = hadokenlist.get(h);
							hadoken.startattack = true;
							updateHitCollision(CurrentNinja, hadoken);
						}
					}
				}
			}
			CurrentNinja.myTurn = false;
		}// end of ninja loop
		
	}

	private void WaveComplete() {
		if (WaveEnded == true) {
			WaveCompleteGOLD();
			WaveCompleteChest();
			
			if(modeHistory<Mode){
				settings_editor.putInt("modeHistory", Mode);
				
				
			}
			FXLevelComplete();
			highestwavecompleted = gamelevel;
			settings_editor.putInt("ContinueLevel", highestwavecompleted);
			settings_editor.putInt("ContinueWeapon", stickman.weapon);
			settings_editor.putInt("ContinueArrows", stickman.arrows);
			settings_editor.putInt("ContinueShuriken", stickman.shuriken);
			settings_editor.putInt("ContinueSmokeBombs", stickman.smokebombs);
			settings_editor.putInt("ContinueCaltrops", stickman.caltrops);
			settings_editor.putString("ContinueLevel", levelstring);
			settings_editor.commit();
			soundbank.playGong(SFX);
			showHUD = true;
			GameCharacter sprite;
			for (int i = 0; i < ninjaslist.size(); i++) {
				sprite = ninjaslist.get(i);
				if (sprite.isAlive == true) {

					DropPowerup(sprite);
					FXSmokePuff(sprite);
					ninjaslist.remove(sprite);
					sendninjatopool(sprite);

				}
			}
			StartNextWave = true;
			WaveEnded = false;

		}
	}

	private void WaveCompleteChest(){
			if (runbroken == false) {
				return;
			}

			
			PowerUp sprite = null;
			// Toast.makeText(this,
			// "Currently there are "+PowerUps.size()+" angels",
			// Toast.LENGTH_SHORT).setVisible(true);

			sprite = this.getpowfrompool();

			sprite.detachSelf();
			sprite.animate(oneFrame, this.blank, -1);
			this.scene.attachChild(sprite);
			sprite.setVisible(true);
			sprite.cycletimer = 0;
			// Debugger("FPS: "+fpsCounter.getFPS());
			sprite.hit = 0;
			sprite.isHit = false;
			sprite.timer = 1;
			sprite.xpos = camerabox.getX();
			sprite.ypos = camerabox.getY() - 65;
			sprite.velocity_y = -20;
			this.poweruplist.add(sprite);
			sprite.powerupType = isChest;
			sprite.animate(oneFrame, this.chestclosed, -1);
			sprite.setPosition(sprite.xpos, sprite.ypos);
		
	}

	
	
	
	
	private void WaveTimer() {
		if (StartNextWave == true) {
			wavedelay++;
			//WaveScoreModifier = stickman.level * gamelevel;

			stickman.isHit = false;

			if ((wavedelay > 100) && (wavedelay < 200)) {
				//soundbank.playGold(SFX);
				//stickman.score = stickman.score + WaveScoreModifier;
			}

		}

		if (wavedelay > 250) {
			// LevelCompleteText.setVisible(false);
			NextWave();

		}
	}

	private void changeWeapon(GameCharacter sprite, int weapon) {
		
		FXShowNewWeapon();
		if (sprite.isPlayer == true) {
			if (weapon == 2) {
				sprite.weapon = 2;
				 textureswitch();
				return;
			}
			if (weapon == 1) {
				sprite.weapon = 1;
				textureswitch();
				return;
			} else if (weapon == 0) {
				sprite.weapon = 0;
				textureswitch();
				return;
			}
		} else if (sprite.isPlayer == false) {
			if (weapon == 1) {
				GameCharacter newsprite = getbossfrompool();
				statecopy(sprite, newsprite);
				newsprite.weapon = 1;
				sendninjatopool(sprite);
				return;
			} else if (weapon == 0) {
				GameCharacter newsprite = getninjafrompool();

				statecopy(sprite, newsprite);
				newsprite.weapon = 0;
				sendbosstopool(sprite);
				return;
			}
		}

	}

	

	private void statecopy(GameCharacter sprite, GameCharacter newsprite) {
		newsprite.currenthealth = sprite.currenthealth;
		newsprite.disabled = sprite.disabled;
		newsprite.level = sprite.level;
		newsprite.xpos = sprite.xpos;
		newsprite.ypos = sprite.ypos;
		newsprite.assailant = sprite.assailant;
		newsprite.attackspeed = sprite.attackspeed;
		newsprite.aggression = sprite.aggression;
		newsprite.antigravity = sprite.antigravity;
		newsprite.hitvelocity_x = sprite.hitvelocity_y;
		newsprite.collision = sprite.collision;
		newsprite.collider = sprite.collider;
		newsprite.currentlyattacking = false;
		newsprite.currentlyjumping = sprite.currentlyjumping;
		newsprite.deaths = sprite.deaths;
		newsprite.falling = sprite.falling;
		newsprite.flipped = sprite.flipped;
		newsprite.hitPower = sprite.hitPower;
		newsprite.hitDirection = sprite.hitDirection;
		newsprite.intendedDirection = sprite.intendedDirection;
		newsprite.isAlive = sprite.isAlive;
		newsprite.isBoss = sprite.isBoss;
		newsprite.isCrouching = sprite.isCrouching;
		newsprite.isGrounded = sprite.isGrounded;
		newsprite.isHit = sprite.isHit;
		newsprite.isPlayer = sprite.isPlayer;
		newsprite.isSliding = sprite.isSliding;
		newsprite.rotationcounter = sprite.rotationcounter;
		newsprite.jumpAttempt = sprite.jumpAttempt;
		newsprite.justAppeared = sprite.justAppeared;
		newsprite.justRandomized = sprite.justRandomized;
		newsprite.jumplevel = sprite.jumplevel;
		newsprite.lastxdirection = sprite.lastxdirection;
		newsprite.RandomizeMe = sprite.RandomizeMe;
		newsprite.rising = sprite.rising;
		newsprite.specificAttack = sprite.specificAttack;
		newsprite.speed = sprite.speed;
		newsprite.startattack = sprite.startattack;
		newsprite.target = sprite.target;
		newsprite.timer = sprite.timer;
		newsprite.topofjump = sprite.topofjump;
		newsprite.uninterruptible = sprite.uninterruptible;
		newsprite.velocity_x = sprite.velocity_x;
		newsprite.velocity_y = sprite.velocity_y;
		newsprite.vertcollider = sprite.vertcollider;
		newsprite.xdirection = sprite.xdirection;
		newsprite.xmomentum = sprite.xmomentum;
		newsprite.xmovement = sprite.xmovement;
		newsprite.ydirection = sprite.ydirection;
		newsprite.setVisible(true);
		sprite.setVisible(false);
		newsprite.setPosition(sprite);
		newsprite.setColor(sprite.getColor());
		if (sprite.isFlippedHorizontal() == true) {
			newsprite.setFlippedHorizontal(true);
		}
		if (sprite.isRotated() == true) {
			newsprite.setRotation(newsprite.rotationcounter);
		}
	}

	private Sword getswordfrompool() {
		return this.SwordPool.obtainPoolItem();
	}

	private void sendswordtopool(GameObject pItem) {
		this.swordlist.remove(pItem);
		pItem.setVisible(false);
		this.SwordPool.recyclePoolItem((Sword) pItem);

	}

	private void DropSword(GameCharacter boss) {
		boss.dropweapontimer = 1;
		Sword sword = getswordfrompool();
		swordlist.add(sword);
		sword.detachSelf();
		this.scene.attachChild(sword);
		sword.setPosition(boss);
		sword.xpos = (int) boss.xpos;
		sword.ypos = (int) boss.ypos;
		sword.xmomentum = 0;
		sword.animate(oneFrame, groundedsword, -1);
		sword.setVisible(true);
		sword.isDropped = true;

	}

	private void DropSword(GameObject box) {

		Sword sword = getswordfrompool();
		swordlist.add(sword);
		sword.detachSelf();
		this.scene.attachChild(sword);
		sword.setPosition(box);

		sword.xpos = (int) box.xpos;
		sword.ypos = (int) box.ypos;
		sword.xmomentum = 0;
		sword.animate(oneFrame, groundedsword, -1);
		sword.setVisible(true);
		sword.isDropped = true;

	}
	
	
	private void DropSword(Rectangle box) {
		
		Sword sword = getswordfrompool();
		swordlist.add(sword);
		sword.detachSelf();
		this.scene.attachChild(sword);
		sword.setPosition(box.getX(), box.getY());

		sword.xpos = (int) box.getX();
		sword.ypos = (int) box.getY();
		sword.xmomentum = 0;
		sword.animate(oneFrame, groundedsword, -1);
		sword.setVisible(true);
		sword.isDropped = true;

	}

	private void SwordPosition() {
		for (int i = 0; i < swordlist.size(); i++) {
			Sword currentSword = swordlist.get(i);

			if (currentSword.isVisible() == false) {
				return;
			}
			if (currentSword.isThrown == true) {
				int xpos = (int) (currentSword.xpos + currentSword.xmomentum);
				int ypos = (int) currentSword.ypos;// + Sword.ymomentum;
				currentSword.setPosition(xpos, ypos);
				currentSword.xpos = xpos;
				currentSword.ypos = ypos;

				if ((xpos < -1000) || (xpos > 3000)) {
					sendswordtopool(currentSword);
				}

				int wallstuck = Physics.wallCollision(currentSword, verticals);
				if (wallstuck != 0) {
					StickSwordinWall(currentSword, wallstuck);
				}
			}
			if (currentSword.isDropped == true) {

				if (Physics.floorCollision(currentSword, horizontals)) {
					StandSwordinGround(currentSword);
				} else

				{
					currentSword.ypos = currentSword.ypos + 15;
					currentSword.setPosition(currentSword.xpos,
							currentSword.ypos);
				}
			}
		}
	}

	private void StandSwordinGround(Sword sword) {

		sword.xmomentum = 0;
		sword.isGrounded = true;
		sword.isDropped = false;
		sword.isThrown = false;
		sword.animate(oneFrame, groundedsword, -1);

	}

	private void StickSwordinWall(Sword sword, int direction) {

		sword.xmomentum = 0;
		sword.isGrounded = true;
		sword.isThrown = false;
		sword.isDropped = false;
		sword.animate(oneFrame, wallstucksword, -1);
		if (direction == -1) {
			sword.setFlippedHorizontal(true);
		} else {
			sword.setFlippedHorizontal(false);
		}

	}

	private void throwSword(GameCharacter thrower) {

		Sword tempSword = getswordfrompool();
		swordlist.add(tempSword);
		tempSword.detachSelf();
		this.scene.attachChild(tempSword);
		if (thrower.weapon == 2) {
			tempSword.damagemomentum = 1000;
		} else {
			tempSword.damagemomentum = 3 * thrower.level;
		}
		tempSword.isSword = true;
		tempSword.thrower = thrower;

		int xpos = (int) thrower.xpos;
		int ypos = (int) thrower.ypos;
		tempSword.setFlippedHorizontal(false);
		tempSword.setVisible(true);
		tempSword.isGrounded = false;
		tempSword.isThrown = true;
		soundbank.playShurikenthrow(SFX);
		tempSword.xpos = xpos;
		tempSword.ypos = ypos;
		tempSword.setPosition(tempSword.xpos, tempSword.ypos);
		if (thrower.isPlayer == false) {
			if (thrower.xdirection == 0) {
				tempSword.xdirection = thrower.lastxdirection;
			} else {
				tempSword.xdirection = thrower.xdirection;
			}
		} else if (thrower.isPlayer == true) {
			if (thrower.intendedDirection == 0) {
				tempSword.xdirection = thrower.lastxdirection;
			} else {
				if (thrower.intendedDirection > 0) {
					tempSword.xdirection = 1;
				} else if (thrower.intendedDirection < 0) {
					tempSword.xdirection = -1;
				}
			}
		}

		if (tempSword.xdirection != 0) {
			tempSword.xmomentum = 15 * tempSword.xdirection;
		}

		if (tempSword.xdirection == -1) {

			tempSword.animate(oneFrame, thrownswordl, -1);
		} else if (tempSword.xdirection == 1) {

			tempSword.animate(oneFrame, thrownswordr, -1);
		}

		return;

	}

	private void swordRecovery(GameCharacter sprite) {// TODO Dual Swords code
														// goes in here for
														// pickup purposes

		if (sprite.dropweapontimer != 0) {
			return;
		}
		for (int i = 0; i < swordlist.size(); i++) {
			Sword sword = swordlist.get(i);
			if ((sprite.collidesWith(sword.bodyRect)) && (sword.isVisible())
					&& (sword.isGrounded == true)) {

				if (sprite.weapon == 0) {
					if (sprite.isHit == false) {
						sprite.disarmed = false;
						sword.startattack = false;
						sendswordtopool(sword);
						changeWeapon(sprite, 1);
						return;
					}
				}
				if (sprite.weapon == 1) {
					   
					if (sprite.isHit == false) {

						sword.startattack = false;
						sendswordtopool(sword);
						changeWeapon(sprite, 2);
						return;
					}
				}
				if (sprite.weapon == 2) {
					return;
				}
				if (sprite.weapon > 2) {
					sprite.weapon = 2;
				}

			}
		}
	}

	private void NextWave() {

		LevelCompleteText.setVisible(false);
		LevelCompleteText.detachSelf();

		gamelevel++;

		StartNextWave = false;
		wavedelay = 0;
		showHUD = false;
		totalninjasleft = stickman.level * 8;
		totalninjasforlevel = totalninjasleft;
		ninjasspawned = 0;
		BossSpawned = false;
		soundbank.playSmallGong(SFX);
		StopSpawningNinjas = false;
		if (stickman.totalHealth > stickman.currenthealth) {
			stickman.currenthealth = stickman.totalHealth;
		}
		ninjaslist.clear();

		for (int e = 0; e < gamelevel; e++) {
			getninjafrompool();
		}
	}

	
	
	

	   private void textureswitch() {
	      
		   String ImageName = null;
		   String hats = null;
		   String shoes= null;
		   String faces= null;
		   String gloves= null;
		   String base= null;
		   String pants= null;
		   String shirts= null;
		   String wings= null;
		   
		   if (stickman.weapon == 2) {
			weapontextureswitch();
			}
			if ((stickman.weapon == 1)||(stickman.weapon == 2)) {

				ImageName = "base/sword.png";

			} else if (stickman.weapon == 0) {

				ImageName="base/master.png";

			}
		   
	       if(ImageName== "base/master.png")
	       {
	           hats = "hats/"+Integer.toString(stickman.hats)+"/master.png";
	           shoes = "shoes/"+Integer.toString(stickman.shoes)+"/master.png";
	           gloves = "gloves/"+Integer.toString(stickman.gloves)+"/master.png";
	           shirts = "shirts/"+Integer.toString(stickman.shirts)+"/master.png";
	           pants = "pants/"+Integer.toString(stickman.pants)+"/master.png";
	           faces = "faces/"+Integer.toString(stickman.faces)+"/master.png";
	           wings = "wings/"+Integer.toString(stickman.wings)+"/master.png";

	        }
	       else
	       {
	          hats = "hats/"+Integer.toString(stickman.hats)+"/sword.png";
	          shoes = "shoes/"+Integer.toString(stickman.shoes)+"/sword.png";
	          gloves = "gloves/"+Integer.toString(stickman.gloves)+"/sword.png";
	          shirts = "shirts/"+Integer.toString(stickman.shirts)+"/sword.png";
	          pants = "pants/"+Integer.toString(stickman.pants)+"/sword.png";
	          faces = "faces/"+Integer.toString(stickman.faces)+"/sword.png";
	          wings = "wings/"+Integer.toString(stickman.wings)+"/sword.png";
	          
	          
	          
	       }
	       
	       
		   
		   Debug.d("textureswitch running");
           if(this.stickTextureAtlas.isLoadedToHardware()){  this.stickTextureAtlas.unload(); }
	       this.stickTextureAtlas.clearTextureAtlasSources();
	         Stickmanlabtas.clearAssetLayer();
	         Stickmanlabtas.addLayer(ImageName, 0, 0);
	         Stickmanlabtas.addLayer(wings, 0, 0);
	         Stickmanlabtas.addLayer(faces, 0, 0);
	         Stickmanlabtas.addLayer(hats, 0, 0);
	         Stickmanlabtas.addLayer(shoes, 0, 0);
	         Stickmanlabtas.addLayer(gloves, 0, 0);
	         Stickmanlabtas.addLayer(pants, 0, 0);
	         Stickmanlabtas.addLayer(shirts, 0, 0);

	         StickmanlayeredTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource(stickTextureAtlas, Stickmanlabtas, 8, 6);	         
	           
	           try 
	           {
	              stickTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
	              stickTextureAtlas.load();
	           } 
	           catch (final TextureAtlasBuilderException e)
	           {
	              Debug.e(e);
	              
	           }
	
	           
	           Debug.d("textureswitch done");
	}
	   
	   
	   
	   
	   
	   


	   private void weapontextureswitch() {
		   Debug.d("weapontextureswitch running");	      
		   String ImageName = null;
		   String hats = null;
		   String shoes= null;
		   String faces= null;
		   String gloves= null;
		   String base= null;
		   String pants= null;
		   String shirts= null;
		   String wings= null;
		   ImageName = "base/dualswords.png";

			
		       hats = "hats/"+Integer.toString(stickman.hats)+"/dualswords.png";
	           shoes = "shoes/"+Integer.toString(stickman.shoes)+"/dualswords.png";
	           gloves = "gloves/"+Integer.toString(stickman.gloves)+"/dualswords.png";
	           shirts = "shirts/"+Integer.toString(stickman.shirts)+"/dualswords.png";
	           pants = "pants/"+Integer.toString(stickman.pants)+"/dualswords.png";
	           faces = "faces/"+Integer.toString(stickman.faces)+"/dualswords.png";
	           wings = "wings/"+Integer.toString(stickman.wings)+"/dualswords.png";

	     
	       
	       
		   

           if(this.stickDualSwordsTextureAtlas.isLoadedToHardware()){  this.stickDualSwordsTextureAtlas.unload(); }
	       this.stickDualSwordsTextureAtlas.clearTextureAtlasSources();
	         StickmanDualSwordslabtas.clearAssetLayer();
	         StickmanDualSwordslabtas.addLayer(ImageName, 0, 0);
	         StickmanDualSwordslabtas.addLayer(wings, 0, 0);
	         StickmanDualSwordslabtas.addLayer(faces, 0, 0);
	         StickmanDualSwordslabtas.addLayer(hats, 0, 0);
	         StickmanDualSwordslabtas.addLayer(shoes, 0, 0);
	         StickmanDualSwordslabtas.addLayer(gloves, 0, 0);
	         StickmanDualSwordslabtas.addLayer(pants, 0, 0);
	         StickmanDualSwordslabtas.addLayer(shirts, 0, 0);

	         StickmanDualSwordslayeredTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource(stickDualSwordsTextureAtlas, StickmanDualSwordslabtas, 8, 6);	         
	           
	           try 
	           {
	              stickDualSwordsTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
	              stickDualSwordsTextureAtlas.load();
	           } 
	           catch (final TextureAtlasBuilderException e)
	           {
	              Debug.e(e);
	              
	           }
	
	           
	           
	           ImageName = "base/dualswordspin.png";

				
		       hats = "hats/"+Integer.toString(stickman.hats)+"/dualswordspin.png";
	           shoes = "shoes/"+Integer.toString(stickman.shoes)+"/dualswordspin.png";
	           gloves = "gloves/"+Integer.toString(stickman.gloves)+"/dualswordspin.png";
	           shirts = "shirts/"+Integer.toString(stickman.shirts)+"/dualswordspin.png";
	           pants = "pants/"+Integer.toString(stickman.pants)+"/dualswordspin.png";
	           faces = "faces/"+Integer.toString(stickman.faces)+"/dualswordspin.png";
	           wings = "wings/"+Integer.toString(stickman.wings)+"/dualswordspin.png";

	     
	       
	       
		   

           if(this.stickDualSwordspinTextureAtlas.isLoadedToHardware()){  this.stickDualSwordspinTextureAtlas.unload(); }
	       this.stickDualSwordspinTextureAtlas.clearTextureAtlasSources();
	         StickmanDualSwordspinlabtas.clearAssetLayer();
	         StickmanDualSwordspinlabtas.addLayer(ImageName, 0, 0);
	         StickmanDualSwordspinlabtas.addLayer(wings, 0, 0);
	         StickmanDualSwordspinlabtas.addLayer(faces, 0, 0);
	         StickmanDualSwordspinlabtas.addLayer(hats, 0, 0);
	         StickmanDualSwordspinlabtas.addLayer(shoes, 0, 0);
	         StickmanDualSwordspinlabtas.addLayer(gloves, 0, 0);
	         StickmanDualSwordspinlabtas.addLayer(pants, 0, 0);
	         StickmanDualSwordspinlabtas.addLayer(shirts, 0, 0);

	         StickmanDualSwordspinlayeredTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromSource(stickDualSwordspinTextureAtlas, StickmanDualSwordspinlabtas, 8, 6);	         
	           
	           try 
	           {
	              stickDualSwordspinTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
	              stickDualSwordspinTextureAtlas.load();
	           } 
	           catch (final TextureAtlasBuilderException e)
	           {
	              Debug.e(e);
	              
	           }
	    
	           Debug.d("weapontextureswitch done");
	}
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
/*
	private void OLDtextureswitch(String ImageName) {  //for nonlayered gamechars
		// does not work if object was created with setuptiled texture
		// abstraction
		this.stickTextureAtlas.clearTextureAtlasSources();

		this.stickmantex = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.stickTextureAtlas, this, ImageName,
						8, 6);
		try {
			this.stickTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 0, 1));
			this.stickTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}

	}
*/
	private void ComboLoop() {
		killtimer++;
		if (ComboText.isVisible()) {
			ComboTextPosition = ComboTextPosition - 3;
			ComboText.setPosition((int) (width - ComboText.getWidth()) / 2,
					ComboTextPosition);

		}

		if (StreakText.isVisible()) {
			StreakTextPosition = StreakTextPosition - 3;
			StreakText.setPosition((int) (width - ComboText.getWidth()) / 2,
					StreakTextPosition);
		}

		if (killtimer == 50) {
			ComboText.setVisible(false);
			ComboText.detachSelf();
			StreakText.setVisible(false);
			StreakText.detachSelf();

		}

		if (killtimer == 25) {
			if (killcombo > 1) {
				FXShowCombo(killcombo);
				if (killcombo > stickman.highestCombo) {
					stickman.highestCombo = killcombo;
				}

				killcombo = 0;
				
			} else if (killcombo == 1) {
				killcombo = 0;
			}
		}

	}

	private void FXShowCombo(int killcombo2) {
		ComboText.detachSelf();
		String ComboString = killcombo2 + "X Combo!";
		ComboText = new Text(0, 0, mFont, ComboString, 32, this.VBO);
		ComboTextPosition = 50 + killcombo2;
		ComboText.setPosition((int) (width - ComboText.getWidth()) / 2,
				ComboTextPosition);

		ComboText.setVisible(true);
		mHud.attachChild(ComboText);

	}

	private void FXShowStreak(int streak) {
		StreakText.detachSelf();
		if (streak > stickman.highestStreak) {
			stickman.highestStreak = streak;
		}

		String ComboString = streak + "X Streak!";
		StreakText = new Text(0, 0, mFont, ComboString, 32, this.VBO);
		StreakTextPosition = 75 + streak;
		StreakText.setPosition((int) (width - StreakText.getWidth()) / 2,
				StreakTextPosition);
		StreakText.setVisible(true);
		mHud.attachChild(StreakText);

	}

	private void Deathloop(GameCharacter defender, int level,
			int specificAttack, float xpos, int xdirection) {
		
		
		killtimer = 1;
		killcombo++;
		stickman.killstreak++;
		totalninjasleft--;
		defender.myTurn = false;
		
		soundbank.playHurt(SFX, SloMoON);
		defender.setVisible(false);
		defender.disabled = true;
		defender.isAlive = false;
		defender.deaths++;

		defender.deathanim = xdirection;

		if (defender.collision == true) {
			FXDeathAnimation(defender, specificAttack);
		} else {
			dropCorpse(defender);
		}
		

		stickman.kills++;
		if (defender.level > stickman.biggestkill) {
			stickman.biggestkill = defender.level;
		}
		if (Mode != EasyMode) {
			if (defender.deaths > defender.level) {
				defender.level++;
				defender.deaths = 0;
			}
		}

		if (stickman.kills > (stickman.level * 2 * stickman.level)) {
			stickman.levelUp(1);
			FXplayerLevelUp();
			soundbank.playLevelup(SFX);
		}

		if (defender.weapon == 0) {
			if(!defender.isBoss){DropPowerup(defender);}
			if(defender.myTurn){
				defender.xpos = 9999999;
				WhosClosest = ninjaslist.size() - 1;
				defender.setPosition(defender.xpos, defender.ypos);
				defender.myTurn = false;
			}
			defender.RandomizeMe = true;
			defender.setVisible(false);
		} else if (defender.weapon >= 1) {
			Debugger("boss should drop sword");
			DropSword(defender);
			if(defender.myTurn){
				defender.xpos = 9999999;
				WhosClosest = ninjaslist.size() - 1;
				defender.setPosition(defender.xpos, defender.ypos);
				defender.myTurn = false;
			}
			sendbosstopool(defender);
			if (StopSpawningNinjas == false) {

				getninjafrompool();
			}
		}

	}

	private void hitCheck(GameCharacter sprite) {
		if ((sprite.hitvelocity_x == 0)
				&& (sprite.isAnimationRunning() == false)) {
			sprite.isHit = false;
			sprite.flinchstarted = false;
		}
	}

	private void updateHitCollision(GameCharacter defender, GameObject explosion) {
		if (defender.disabled) {
			return;
		}
		if (explosion.isAnimationRunning() == false) {
			return;
		}

		if (defender.isHit == true) {
			return;
		}

		if (defender.bodyRect.collidesWith(explosion.self)) {
			defender.hit++;
			defender.startattack = false;
			int explosioncenterpoint = (int) (explosion.xpos + 64);
			int defendercenterpoint = (int) (defender.xpos + 32);
			if (defendercenterpoint < explosioncenterpoint) {
				defender.hitDirection = -1;
			} else if (defendercenterpoint > explosioncenterpoint) {
				defender.hitDirection = 1;
			}
			int distance = Math.abs(explosioncenterpoint - defendercenterpoint);
			double explosionpercentage = (64 - distance) * 0.015625;
			// Debugger("explosionpercentage = "+ explosionpercentage*100 +
			// ", distance "+distance);

			defender.assailant = stickman;
			defender.collision = false;
			int hit_y = (int) (-1 * explosion.hitPower * explosionpercentage);
			int hit_x = (int) (defender.hitDirection * (explosion.hitPower + (defender.hit * 2)));
			defender.hitvelocity_y = hit_y;
			defender.hitvelocity_x = hit_x;

			int damage = (int) (explosion.hitPower * explosionpercentage);
			if (DamageOFF == false) {
				if (defender.isHit == false) {

					defender.currenthealth = defender.currenthealth - damage;
				} else {
					defender.currenthealth = (int) (defender.currenthealth - (damage / 2));
				}
			}
			defender.isHit = true;
			if(defender.isPlayer==true){
				if((defender.currenthealth<(int)(defender.totalHealth/5))||(defender.currenthealth<4)){
					FXShowBloodSplatter();
				}
					
					
			}
			if (defender.currenthealth < 1) // DEFENDER DIED
			{
				Deathloop(defender, explosion.level, explosion.specificAttack,
						explosion.xpos + 32, explosion.xdirection);

			}

		}
	}

	private void CorpseGroundPound(Corpse corpse, GameObject explosion) {

		if (corpse.bodyRect.collidesWith(explosion.AttackCollisionRect())) {
			if (explosion.disabled) {
				return;
			}
			corpse.startattack = false;
			corpse.isHit = true;
			int explosioncenterpoint = (int) (explosion.xpos + 64);
			int corpsecenterpoint = (int) (corpse.xpos + 32);
			if (corpsecenterpoint < explosioncenterpoint) {
				corpse.hitDirection = -1;
			} else if (corpsecenterpoint > explosioncenterpoint) {
				corpse.hitDirection = 1;
			}
			int distance = Math.abs(explosioncenterpoint - corpsecenterpoint);
			double explosionpercentage = (64 - distance) * 0.015625;
			// Debugger("explosionpercentage = "+ explosionpercentage*100 +
			// ", distance "+distance);

			corpse.assailant = stickman;
			corpse.collision = false;
			int hit_y = (int) (-1 * explosion.hitPower * explosionpercentage);
			int hit_x = (int) (explosion.hitPower * corpse.hitDirection);
			corpse.hitvelocity_y = hit_y;
			corpse.hitvelocity_x = hit_x;

		}
	}

	private void ShowText(String string, int i) {
		// TODO Auto-generated method stub
		if (BoxText.hasParent()) {
			BoxText.detachSelf();
		}

		BoxText = new Text(0, 0, tooltipFont, (CharSequence) string, 32,
				this.VBO);
		BoxText.setPosition(halfwidth - (BoxText.getWidth() / 2), i);
		mHud.attachChild(BoxText);
		BoxText.setVisible(true);

	}

	void TextTimers() {
		if (ScoreAddText.isVisible() == true) {
			scoreaddtimer++;
			if (scoreaddtimer > 26) {
				ScoreAddText.setVisible(false);
				scoreaddtimer = 0;
				ScoreAddText.detachSelf();
			}
		}

		if (BoxText.isVisible() == true) {
			// Debugger("Boxtext is visible");
			Boxtimer++;
			if (Boxtimer > 26)// &&(BoxTextCondition == true))
			{
				BoxText.setVisible(false);
				Boxtimer = 0;
				BoxText.detachSelf();
				BoxTextCondition = false;

			}
		}

		ShowShurikenCount(stickman.shuriken);
		ShowSmokebombCount(stickman.smokebombs);

	}

	private void ShowShurikenCount(int i) {
		// TODO Auto-generated method stub
		String string = Integer.toString(i);
		ShurikenCountText.detachSelf();
		ShurikenCountText = new Text(0, 0, countersFont, (CharSequence) string,
				32, this.VBO);
		ShurikenCountText.setPosition((this.screenwidth / 2)
				- (ShurikenCountText.getWidth() / 2) - 35, 12);
		mHud.attachChild(ShurikenCountText);
		ShurikenCountText.setVisible(true);

	}

	private void ShowSmokebombCount(int i) {
		// TODO Auto-generated method stub
		String string = Integer.toString(i);
		SmokebombCountText.detachSelf();
		SmokebombCountText = new Text(0, 0, countersFont,
				(CharSequence) string, 32, this.VBO);
		SmokebombCountText.setPosition((this.screenwidth / 2)
				- (SmokebombCountText.getWidth() / 2) + 35, 12);
		mHud.attachChild(SmokebombCountText);
		SmokebombCountText.setVisible(true);

	}

	void DrawTargetBox() {
		Target = new Rectangle[7];
		TargetActivated = new Boolean[7];
		for (int t = 0; t < 7; t++) {
			Target[t] = new Rectangle(0, 0, 32, 32, VBO);
			// scene.attachChild(Target[t]);
			if (t == 0) {
				Target[t].setColor(Color.RED);
			} // hit by fists or swords
			if (t == 1) {
				Target[t].setColor(Color.GREEN);
			} // hit by sword only
			if (t == 2) {
				Target[t].setColor(Color.BLACK);
			} // hit by shuriken only
			if (t == 3) {
				Target[t].setColor(Color.WHITE);
			} // hit by sliding only
			if (t == 4) {
				Target[t].setColor(Color.BLUE);
			} // hit by hadokens only
			if (t == 5) {
				Target[t].setColor(Color.YELLOW);
				//hit by arrows only
				
			} 
			Physics.box2d.addObject(Target[t], 1, "box", false, false);
			horizontals.add(Target[t]);

			TargetActivated[t] = false;
		}

		Target[0].setPosition(100, 250);
		TargetActivated[0] = true;
		// BoxText(0);

	}

	int randomboxx() {

		return (int) (Math.random() * 450);
	}

	int randomboxy() {
		return (int) ((Math.random() * 120) + 150);
	}

	void RandomizeBox(int iteration) {
		int randomy = randomboxy();
		int randomx = randomboxx();
		if (iteration == 3) {
			Target[iteration].setPosition(randomx, 275);
			Target[iteration].setVisible(true);
			return;
		}

		Target[iteration].setPosition(randomx, randomy);

		Target[iteration].setVisible(true);

	}

	void ActivateBox(int iteration) {
		if (TargetActivated[iteration] == true) {
			return;
		}

		if (iteration == 1) {
			DropSword(camerabox);
		}

		ShowBoxText(iteration);
		RandomizeBox(iteration);
		TargetActivated[iteration] = true;
	}

	private void ShowBoxText(int t) {
		BoxText.detachSelf();
		String BoxTextString = "";
		BoxTextConditionNumber = t;
		BoxTextCondition = false;
		if (t == 1) {
			BoxTextString = "USE sword ON GREEN BOX";
		}
		if (t == 2) {
			BoxTextString = "USE SHURIKEN ON BLACK BOX";
		}
		if (t == 3) {
			BoxTextString = "SLIDE TO HIT WHITE BOX";
		}
		if (t == 4) {
			BoxTextString = "THROW FIREBALL AT BLUE BOX";
		}
		if (t == 5) {
			BoxTextString = "SHOOT AN ARROW AT THE YELLOW BOX";
		}
		BoxText = new Text(0, 0, tooltipFont, (CharSequence) BoxTextString, 32,
				this.VBO);
		BoxText.setPosition(halfwidth - (BoxText.getWidth() / 2), 75);
		mHud.attachChild(BoxText);
		BoxText.setVisible(true);
	}

	void NextBox(int hit) {
		if (BoxTextCondition == true) {
			stickman.practicehits++;
		}
		// RandomizeBox(hit);

		if (stickman.practicehits > 5) {
			ActivateBox(1);
		}
		if (stickman.practicehits > 10) {
			ActivateBox(2);
		}
		if (stickman.practicehits > 15) {
			ActivateBox(3);
		}
		if (stickman.practicehits > 20) {
			ActivateBox(4);
		}
		
		
	}

	private void updateHitCollision(int iteration) {

		/*
		 * BoxTextConditionNumber = t; if(t == 0){ BoxTextString =
		 * "ATTACK THE RED BOX";} if(t == 1){ BoxTextString =
		 * "USE sword ON GREEN BOX";} if(t == 2){ BoxTextString =
		 * "USE SHURIKEN ON BLACK BOX";} if(t == 3){ BoxTextString =
		 * "SLIDE TO HIT WHITE BOX";} if(t == 4){ BoxTextString =
		 * "THROW FIREBALL AT BLUE BOX";}
		 */

		Rectangle defender = Target[iteration];

		if (TargetActivated[iteration] == false) {
			return;
		}
		if (defender.isVisible() == false) {
			return;
		}

		if (iteration == 1) {
			if (stickman.weapon == 0) {
				return;
			}
		}

		if (iteration == 2) {
			for (int x = 0; x < shurikenlist.size(); x++) {
				Shuriken attacker = shurikenlist.get(x);
				if ((defender.collidesWith(attacker.AttackCollisionRect()))) {
					if (BoxTextConditionNumber == 2) {
						BoxTextCondition = true;
					}
					soundbank.playHit(SFX, false);
					// defender.setVisible(false);
					int hit_x = (attacker.xdirection
							* (attacker.thrower.hitPower) / 6);
					HitBox2d(defender, hit_x, -3);
					NextBox(iteration);
					return;
				}
			}
			return;
		}

		if (iteration == 3) {
			GameCharacter attacker = stickman;
			if (stickman.isSliding == false) {
				return;
			} else if (stickman.isSliding == true) {
				if (defender.collidesWith(stickman.feetRect)) {
					if (BoxTextConditionNumber == 3) {
						BoxTextCondition = true;
					}
					soundbank.playHit(SFX, false);
					// defender.setVisible(false);
					int hit_x = attacker.xdirection * -5;
					int hit_y = -5;
					HitBox2d(defender, hit_x, hit_y);
					NextBox(iteration);
					return;
				}
			}
			return;
		}

		if (iteration == 4) {
			for (int i = 0; i < hadokenlist.size(); i++) {
				Hadoken attacker = hadokenlist.get(i);
				if ((defender.collidesWith(attacker.AttackCollisionRect()))) {
					if (BoxTextConditionNumber == 4) {
						BoxTextCondition = true;
					}
					soundbank.playHit(SFX, false);
					// defender.setVisible(false);
					int hit_x = (attacker.thrower.hitPower * attacker.xdirection);

					HitBox2d(defender, hit_x, 0);
					NextBox(iteration);
					return;
				}

			}
			return;
		}

		GameCharacter attacker = stickman;
		if ((defender.collidesWith(attacker.AttackCollisionRect()))
				&& ((attacker.startattack == true) || (attacker.currentlyattacking == true))) {

			if ((BoxTextConditionNumber == 0) && (iteration == 0)) {
				BoxTextCondition = true;
			}
			if ((BoxTextConditionNumber == 1) && (iteration == 1)) {
				BoxTextCondition = true;
			}

			soundbank.playHit(SFX, false);
			// defender.setVisible(false);
			int hit_y = 3 - attacker.hitPower; // TODO attack specific hit
												// vectors
			int hit_x = (int) ((attacker.hitPower * attacker.xdirection) / 2);

			HitBox2d(defender, hit_x, hit_y);
			NextBox(iteration);

		}
	}

	private void updateTargetHitCollision(GameObject defender, int iteration,
			Object attacker, int attacktype) {

		if (TargetActivated[iteration] == false) {
			return;
		}
		if (defender.isVisible() == false) {
			return;
		}

		if (attacktype == 1) {
			// Gamecharacter hit
			GameCharacter character = (GameCharacter) attacker;
			if (character.isSliding == false) {
				if ((defender.collidesWith(character.AttackCollisionRect()))
						&& ((character.startattack == true) || (character.currentlyattacking == true))) {
					soundbank.playHit(SFX, false);
					// defender.setVisible(false);
					int hit_y = 3 - character.hitPower; // TODO attack specific
														// hit vectors
					int hit_x = (int) ((character.hitPower * character.xdirection) / 2);

					HitBox2d(defender, hit_x, hit_y);
					NextBox(iteration);

				}

			} else if (character.isSliding == true) {
				if (defender.collidesWith(character.feetRect)) {
					soundbank.playHit(SFX, false);
					// defender.setVisible(false);
					int hit_x = character.xdirection * -5;
					int hit_y = -5;
					HitBox2d(defender, hit_x, hit_y);
					NextBox(iteration);
					return;
				}
			}

		}

		if (attacktype == 2) {
			// Shuriken hit
			for (int x = 0; x < shurikenlist.size(); x++) {
				Shuriken shuriken = (Shuriken) attacker;
				if ((defender.collidesWith(shuriken.AttackCollisionRect()))) {
					soundbank.playHit(SFX, false);
					// defender.setVisible(false);
					int hit_x = (shuriken.xdirection
							* (shuriken.thrower.hitPower) / 6);
					HitBox2d(defender, hit_x, -3);
					NextBox(iteration);
					return;
				}
			}
			return;
		}

		if (attacktype == 3) {
			// sliding hit
			GameObject explosion = (GameObject) attacker;

			return;
		}

		if (attacktype == 4) { // Hadoken hit
			for (int i = 0; i < hadokenlist.size(); i++) {
				Hadoken hadoken = (Hadoken) attacker;
				if (defender.collidesWith(hadoken.AttackCollisionRect())) {
					soundbank.playHit(SFX, false);
					// defender.setVisible(false);
					int hit_x = (hadoken.thrower.hitPower * hadoken.xdirection);

					HitBox2d(defender, hit_x, 0);
					NextBox(iteration);
					return;
				}

			}
			return;
		}
		
		if (attacktype == 5) 
		{ // arrow hit
			for (int i = 0; i < arrowlist.size(); i++) 
			{
				Arrow arrow = (Arrow) attacker;
				if(defender.collidesWith(arrow.AttackCollisionRect()))
				{
					soundbank.playHit(SFX, false);
					HitBox2d(defender, (int)arrow.physicsBody.getLinearVelocity().x,
							(int)arrow.physicsBody.getLinearVelocity().y);
					NextBox(iteration);
					return;
				}
			}
		}
	}
					
				
		
	

	void HitBox2d(Rectangle defender, int hitX, int hitY) {
		Physics.box2d.hit(defender, hitX, hitY);

	}

	void HitBox2d(GameObject defender, int hitX, int hitY) {
		Physics.box2d.hit(defender, hitX, hitY);

	}

	private void updateHitCollision(GameCharacter defender, Shuriken shuriken) {

		if (defender.disabled) {
			return;
		}
		if (((defender.isCrouching) && (defender.collision == true))
				|| (defender.isSliding)) {
			return;
		}

		if (defender.groundpound == true) {
			return;
		}

		if (defender == shuriken.thrower) {
			return;
		}

		if ((shuriken.thrower.isPlayer == false)
				&& (defender.isPlayer == false)
				&& (shuriken.xdirection == defender.xdirection)) {
			return;
		}

		boolean autoblock = false;
		if ((defender.isPlayer == false) && (defender.weapon == 1)) {
			int chance = Math.round((float) (Math.random()));
			if (chance == 0) {
				autoblock = true;
			} else if (chance == 1) {
				autoblock = false;
			}

			if ((defender.isBoss == true) && (ninjaslist.size() > 1)) {
				autoblock = true;
			}
		}

		if ((((defender.startattack == true) || (defender.currentlyattacking == true) || (defender.specialInprogress != 0)) && (defender.weapon > 0))
				|| (autoblock == true)) // shuriken swatting
		{
			if (defender.isPlayer == true) {
				if ((defender.lastxdirection != shuriken.xdirection)
						&& (shuriken.collidesWith(defender
								.AttackCollisionRect()))) {

					shuriken.xmomentum = (int)(shuriken.xmomentum * -1.5);
					shuriken.xdirection = defender.xdirection;

					shuriken.thrower = defender;
					shuriken.hitPower = (int) (shuriken.thrower.hitPower / 2);
					soundbank.playShurikenCollision(SFX, SloMoON);
					return;
				}
			} else {
				if ((defender.lastxdirection != shuriken.xdirection)
						&& (shuriken.collidesWith(defender.bodyRect))) {
					shuriken.xmomentum = shuriken.xmomentum * -1;
					shuriken.xdirection = defender.lastxdirection;

					shuriken.thrower = defender;
					shuriken.hitPower = (int) (shuriken.thrower.hitPower / 2);
					soundbank.playShurikenCollision(SFX, SloMoON);
					defender.animate(animator.animations.threeFrame,
							animator.animations.swordoverhandR, 0);
					return;

				}
			}
		}

		if (defender.isHit == false) {
			boolean hit = false;
			boolean headshot = false;
			if (defender.isHit == false) {
				if (defender.bodyRect.collidesWith(shuriken
						.AttackCollisionRect())) {
					if (defender.headRect.collidesWith(shuriken
							.AttackCollisionRect())) {
						headshot = true;
					}
					hit = true;
				}
			}
			if (hit == true) {
				visibilityCheck(defender);
				defender.hit++;
				if (headshot == true) {
					shuriken.thrower.headshots++;
				}
				defender.stopAnimation();
				soundbank.playShurikenhit(SFX, SloMoON);
				defender.startattack = false;
				defender.assailant = shuriken.thrower;
				defender.hitDirection = shuriken.xdirection;
				int hit_x = (shuriken.xdirection
						* (shuriken.thrower.hitPower + (defender.hit * 2)) / 6);
				defender.hitvelocity_x = hit_x;
				// defender.hitvelocity_y = 0;

				defender.isHit = true;

				int damage = (int) (shuriken.thrower.hitPower * shurikenHPratio);

				if (headshot == true) {
					damage = damage * 2;
				}
				if (DamageOFF == false) {
					if (defender.isPlayer == true) {
						//healthhud.setVisible(true);
					}
					defender.currenthealth = defender.currenthealth - damage;
				}
				this.sendshurikentopool(shuriken);

				if(defender.isPlayer==true){
					if((defender.currenthealth<(int)(defender.totalHealth/5))||(defender.currenthealth<4)){
						FXShowBloodSplatter();
					}
						
						
				}
				
				
				if (defender.currenthealth < 1) // DEFENDER DIED
				{
					if (defender.isPlayer == false) {
						Deathloop(defender, shuriken.level,
								shuriken.specificAttack, shuriken.xpos,
								shuriken.xdirection);
						FXbloodsplatter(shuriken.AttackCollisionRect(),
								shuriken.xdirection);
					} else {
						stickman.GameIsOver = true;
						return;
					}
				}
			} else {
				defender.isHit = false;

			}
		}
	}

	private void updateHitCollision(GameCharacter defender, Arrow arrow) {

		if ((defender.disabled) || (arrow.disabled)) {
			return;
		}

		if (defender == arrow.thrower) {
			return;
		}

		if ((arrow.thrower.isPlayer == false) && (defender.isPlayer == false)
				&& (arrow.xdirection == defender.xdirection)) {
			return;
		}

		boolean autoblock = false;
		if ((defender.isPlayer == false) && (defender.weapon == 1)) {
			int chance = Math.round((float) (Math.random()));
			if (chance == 0) {
				autoblock = true;
			} else if (chance == 1) {
				autoblock = false;
			}

			if ((defender.isBoss == true) && (ninjaslist.size() > 1)) {
				autoblock = true;
			}
		}

		/*
		 * if((((defender.startattack==true)||(defender.currentlyattacking==true)
		 * )&&(defender.weapon>0))||(autoblock == true)) //arrow swatting {
		 * if(defender.isPlayer == true) { if((defender.lastxdirection !=
		 * arrow.xdirection
		 * )&&(arrow.collidesWith(defender.AttackCollisionRect()))) {
		 * 
		 * arrow.xmomentum= arrow.xmomentum*-1;
		 * arrow.xdirection=defender.xdirection;
		 * 
		 * arrow.thrower = defender; arrow.hitPower=(int)
		 * (arrow.thrower.hitPower/2); soundbank.playShurikenCollision(SFX,
		 * SloMoON); return; } } else { if((defender.lastxdirection !=
		 * arrow.xdirection)&&(arrow.collidesWith(defender.bodyRect))) {
		 * arrow.xmomentum= arrow.xmomentum*-1;
		 * arrow.xdirection=defender.lastxdirection;
		 * 
		 * arrow.thrower = defender; arrow.hitPower=(int)
		 * (arrow.thrower.hitPower/2); soundbank.playShurikenCollision(SFX,
		 * SloMoON);
		 * defender.animate(animator.animations.threeFrame,animator.animations
		 * .swordoverhandR, 0); return;
		 * 
		 * } } }
		 */

		if (defender.isHit == false) {
			boolean hit = false;
			boolean headshot = false;
			if (defender.isHit == false) {
				if (defender.bodyRect.collidesWith(arrow.AttackCollisionRect())) {
					if (defender.headRect.collidesWith(arrow
							.AttackCollisionRect())) {
						headshot = true;
					}
					hit = true;
				}
			}
			if (hit == true) {
				visibilityCheck(defender);
				defender.hit++;
				if (headshot == true) {
					arrow.thrower.headshots++;
				}
				defender.stopAnimation();
				soundbank.playShurikenhit(SFX, SloMoON);
				defender.startattack = false;
				defender.assailant = arrow.thrower;
				defender.hitDirection = arrow.xdirection;
				int hit_x = (arrow.xdirection
						* (arrow.thrower.hitPower + (defender.hit * 2)) / 6);
				defender.hitvelocity_x = hit_x;
				// defender.hitvelocity_y = 0;

				defender.isHit = true;
				float force = magnitude(
						arrow.physicsBody.getLinearVelocity().x,
						arrow.physicsBody.getLinearVelocity().y);
				int damage = (int) (Math.abs(force) * stickman.arrowbuff * 10);// arrowHPratio);

				if (headshot == true) {
					damage = damage * 2;
				}
				if (DamageOFF == false) {
					if (defender.isPlayer == true) {
						//healthhud.setVisible(true);
					}
					defender.currenthealth = defender.currenthealth - damage;
				}
				this.sendarrowtopool(arrow);

				
				if(defender.isPlayer==true){
					if((defender.currenthealth<(int)(defender.totalHealth/5))||(defender.currenthealth<4)){
						FXShowBloodSplatter();
					}
						
						
				}
				
				
				if (defender.currenthealth < 1) // DEFENDER DIED
				{
					if (defender.isPlayer == false) {
						Deathloop(defender, arrow.thrower.level,
								arrow.specificAttack, arrow.xpos,
								arrow.xdirection);
						FXbloodsplatter(arrow.AttackCollisionRect(),
								arrow.xdirection);
					} else {
						stickman.GameIsOver = true;
						return;
					}
				}
			} else {
				defender.isHit = false;

			}
		}
	}

	

	private void updateHitCollision(GameCharacter defender, Hadoken hadoken) {
		if (defender.disabled) {
			return;
		}
		if (defender.isHit == false) {
			if (hadoken.isHit == true) {
				hadoken.hits++;
				if (hadoken.hits > stickman.level) {
					return;
				}
			}
			boolean hit = false;
			boolean headshot = false;
			if (defender.isHit == false) {
				if (defender.bodyRect.collidesWith(hadoken
						.AttackCollisionRect())) {
					if (defender.headRect.collidesWith(hadoken
							.AttackCollisionRect())) {
						headshot = true;
					}
					hit = true;
				}
			}
			if (hit == true) {
				visibilityCheck(defender);
				defender.hit++;
				defender.isHit = true;
				if (defender.weapon > 0) {
					Debugger("boss should drop weapon due to hadoken");
					dropWeapon(defender);
					defender.weapon = 0;
				}
				hadoken.animate(hadokenfiveFrame, hadokenhit, 0);
				if (headshot == true) {
					hadoken.thrower.headshots++;
				}
				defender.stopAnimation();
				soundbank.playExplosion(SFX, SloMoON);
				defender.startattack = false;
				defender.assailant = hadoken.thrower;
				defender.hitDirection = hadoken.xdirection;
				int hit_x = ((hadoken.thrower.hitPower + (defender.hit * 2)) * hadoken.xdirection);
				defender.hitvelocity_x = hit_x;
				// defender.hitvelocity_y = 0;
				int damage = (int) (hadoken.thrower.hitPower / 2);
				if (headshot == true) {
					damage = damage * 2;
				}
				if (DamageOFF == false) {
					defender.currenthealth = defender.currenthealth - damage;
				}
				hadoken.isHit = true;
				
				if(defender.isPlayer==true){
					if((defender.currenthealth<(int)(defender.totalHealth/5))||(defender.currenthealth<4)){
						FXShowBloodSplatter();
					}
						
						
				}
				if (defender.currenthealth < 1) // DEFENDER DIED
				{
					if (defender.isPlayer == false) {
						Deathloop(defender, hadoken.level,
								hadoken.specificAttack, hadoken.xpos,
								hadoken.xdirection);
						FXbloodsplatter(hadoken.AttackCollisionRect(),
								hadoken.xdirection);
					} else {
						stickman.GameIsOver = true;
						return;
					}
				}
			}
		}
	}

	private void updateHitCollision(PowerUp defender, Hadoken hadoken) {
		if (defender.disabled) {
			return;
		}
		if ((defender.cycletimer == 0)
				&& (defender.collision)
				&& (defender.bodyRect.collidesWith(hadoken
						.AttackCollisionRect()))) {
			hadoken.animate(hadokenfiveFrame, hadokenhit, 0);
			defender.cycletimer = 1;
			soundbank.playExplosion(SFX, SloMoON);
			if (!hadokenPiercing) {
				hadoken.isHit = true;
			}
			CyclePowerup(defender);
			hadoken.hits++;
		}
	}

	private void dropWeapon(GameCharacter defender) {
		// TODO Auto-generated method stub

		if (defender.weapon == 1) {
			defender.disarmed = true;
			DropSword(defender);
			changeWeapon(defender, 0);
		}

		if (defender.weapon == 2) {
			defender.weapon = 1;
			DropSword(defender);
		}
	}

	private void updateHitCollision(GameCharacter defender,
			GameCharacter attacker) {
		if (defender.disabled) {
			return;
		}
		if (defender.feetRect.collidesWith(attacker.feetRect)) // slide tripping
		{
			if ((attacker.isSliding == true) && (attacker.isPlayer == true)) {
				defender.stopAnimation();
				defender.isHit = true;
				defender.startattack = false;
				defender.currentlyattacking = false;
				defender.hitDirection = -(attacker.xdirection);
				defender.assailant = attacker;
				defender.hitvelocity_y = -15;
				defender.hitvelocity_x = -1 * (attacker.xdirection * 10);
				return;
			}

			if ((defender.isSliding == true) && (defender.isPlayer == true)) {
				attacker.stopAnimation();
				attacker.isHit = true;
				attacker.startattack = false;
				attacker.currentlyattacking = false;
				attacker.hitDirection = -(defender.xdirection);
				attacker.assailant = defender;
				attacker.hitvelocity_y = -15;
				attacker.hitvelocity_x = -1 * (defender.xdirection * 10);
				return;
			}
		}

		if ((defender.groundpound == true) || (defender.specialInprogress != 0)) {
			return;
		}

		if (defender.isHit == false) {
			boolean hit = false;
			boolean headshot = false;
			int hitdirection = attacker.xdirection;
			if (defender.isHit == false) {
				if (defender.bodyRect.collidesWith(attacker
						.AttackCollisionRect())) {
					if (defender.headRect.collidesWith(attacker
							.AttackCollisionRect())) {
						if ((attacker.specificAttack == 4)
								|| (attacker.specificAttack == -4)
								|| (attacker.specificAttack == 5)
								|| (attacker.specificAttack == -5)) {
							headshot = true;
						}
					}
					if (attacker.currentlyattacking == true) {
						if (((hitdirection >= 0) && (attacker.xpos < defender.xpos))
								|| ((hitdirection <= 0) && (attacker.xpos > defender.xpos))) {
							hit = true;

						}
					}
				}
			}

			if (hit == true) {
				visibilityCheck(defender);
				defender.hit++;
				defender.attackstreak = 0;
				attacker.hit = 0;
				attacker.attackstreak++;
				attacker.specialxmovement = 0;
				/*
				 * if(attacker.specialInprogress != 0) {
				 * animator.SpecialFinish(attacker, attacker.specialInprogress);
				 * }
				 */

				if (((attacker.weapon == 1) && (defender.weapon == 1))
						|| ((attacker.weapon == 2) && (defender.weapon == 2))) // sword
																				// collision
																				// double
																				// knockback
				{
					if ((defender.currentlyattacking == true)
							&& (defender.AttackCollisionRect().collidesWith(
									attacker.AttackCollisionRect()) == true)) {
						attacker.isHit = true;
						defender.isHit = true;
						attacker.assailant = defender;
						defender.assailant = attacker;
						soundbank.playSwordCollision(SFX, SloMoON);
						if (defender.xpos > attacker.xpos) {
							defender.hitvelocity_x = attacker.hitPower;
							attacker.hitvelocity_x = -1 * defender.hitPower;
						} else if (defender.xpos < attacker.xpos) {
							defender.hitvelocity_x = -1 * attacker.hitPower;
							attacker.hitvelocity_x = defender.hitPower;
						}

						if (defender.ypos > attacker.ypos) {
							if (defender.collision == false) {
								defender.hitvelocity_y = attacker.hitPower;
							}
							attacker.hitvelocity_y = -1 * defender.hitPower;
						} else if (defender.ypos < attacker.ypos) {
							defender.hitvelocity_y = attacker.hitPower;
							if (attacker.collision == false) {
								attacker.hitvelocity_y = -1 * defender.hitPower;
							}
						}
						return;
					}
				}

				if (headshot == true) {
					attacker.headshots++;
				}
				defender.stopAnimation();
				if (attacker.weapon == 0) {
					soundbank.playHit(SFX, SloMoON);
					if (attacker.currentlyjumping == false) {
						attacker.currentlyattacking = false;
						attacker.startattack = false;
						attacker.stopAnimation();
					}
				} else if (attacker.weapon == 1) {
					soundbank.playSwishKill(SFX, SloMoON);
				} else if (attacker.weapon == 2) {
					soundbank.playDSCombo1(SFX, SloMoON);
				}

				defender.startattack = false;
				defender.assailant = attacker;
				defender.xdirection = attacker.xdirection;
				int hitD = 0;
				if (attacker.xdirection == 0) {
					hitD = attacker.lastxdirection;
				} else {
					hitD = attacker.xdirection;
				}

				defender.hitDirection = hitD;

				int hit_y = 3 - attacker.hitPower; // TODO attack specific hit
													// vectors
				int hit_x = (int) ((attacker.hitPower + attacker.weapon + (defender.hit * 2)) * defender.hitDirection) / 2;
				defender.hitvelocity_y = hit_y;
				defender.hitvelocity_x = hit_x;

				defender.isHit = true;

				int damage = (int) (attacker.hitPower + (attacker.weapon * 10));
				if (headshot == true) {

					damage = damage * 2;
					
				}
				if (DamageOFF == false) {
					if (defender.isPlayer == true) {
						//healthhud.setVisible(true);
					}
					defender.currenthealth = defender.currenthealth - damage;
				}
				if(defender.isPlayer==true){
					if((defender.currenthealth<(int)(defender.totalHealth/5))||(defender.currenthealth<4)){
						FXShowBloodSplatter();
					}
						
						
				}
				if (defender.currenthealth < 1) // DEFENDER DIED
				{
					if (defender.isPlayer == false) {
						soundbank.playYell(SFX, SloMoON);
						Deathloop(defender, attacker.level,
								attacker.specificAttack, attacker.xpos,
								attacker.xdirection);
					} else {
						stickman.GameIsOver = true;
						return;
					}
				}
			}
		}
	}

	private void updateHitCollision(GameCharacter defender, Corpse corpse) {
		if (defender.disabled) {
			return;
		}
		if (defender.isHit == false) {
			boolean hit = false;
			boolean headshot = false;
			int hitdirection = corpse.xdirection;
			if (defender.isHit == false) {
				if (defender.bodyRect
						.collidesWith(corpse.AttackCollisionRect())) {
					if (((hitdirection >= 0) && (corpse.xpos < defender.xpos))
							|| ((hitdirection <= 0) && (corpse.xpos > defender.xpos))) {
						hit = true;

					}
				}
			}

			if (hit == true) {
				visibilityCheck(defender);
				defender.hit++;
				if (headshot == true) {
					corpse.headshots++;
				}
				defender.stopAnimation();
				soundbank.playHit(SFX, SloMoON);
				defender.startattack = false;
				defender.assailant = corpse;
				defender.xdirection = corpse.xdirection;
				if (corpse.xdirection == 0) {
					defender.hitDirection = corpse.lastxdirection;
				} else {
					defender.hitDirection = corpse.xdirection;
				}
				defender.collision = false;
				int hit_y = (int) (1.2 * corpse.hitvelocity_y);
				int hit_x = (corpse.hitPower + (defender.hit * 2))
						* defender.hitDirection;
				defender.hitvelocity_y = hit_y;
				defender.hitvelocity_x = hit_x;

				defender.isHit = true;

				int damage = corpse.hitPower;
				if (DamageOFF == false) {
					if (defender.isPlayer == true) {
						//healthhud.setVisible(true);
					}
					defender.currenthealth = defender.currenthealth - damage;
				}
				corpse.hitPower = (int) (corpse.hitPower * .5);

				if (defender.currenthealth < 1) // DEFENDER DIED
				{
					if (defender.isPlayer == false) {
						Deathloop(defender, corpse.level,
								corpse.specificAttack, corpse.xpos,
								corpse.xdirection);
					} else {
						stickman.GameIsOver = true;
						return;
					}

				}
			}
		}
	}

	private void updateHitCollision(GameCharacter defender, Sword sword) {
		if (defender.disabled) {
			return;
		}
		if (sword.damagemomentum == 0) {
			return;
		}
		if ((defender.isCrouching) || (defender.isSliding)) {
			return;
		}
		if (((defender.startattack == true) || (defender.currentlyattacking == true))
				&& (defender.weapon > 0)) // thrownsword swatting
		{
			if ((defender.lastxdirection != sword.xdirection)
					&& (sword.collidesWith(defender.AttackCollisionRect()))) {
				sword.xmomentum = sword.xmomentum * -1;
				sword.xdirection = defender.xdirection;
				sword.thrower = defender;
				return;
			}
		}
		if (defender.isHit == false) {
			boolean hit = false;
			boolean headshot = false;
			if (defender.isHit == false) {
				if (defender.bodyRect.collidesWith(sword.AttackCollisionRect())) {
					if (defender.headRect.collidesWith(sword
							.AttackCollisionRect())) {
						headshot = true;
					}
					hit = true;
				}
			}

			if (hit == true) {
				visibilityCheck(defender);
				if (headshot == true) {
					sword.thrower.headshots++;
				}
				defender.stopAnimation();
				defender.startattack = false;
				defender.assailant = sword;
				defender.xdirection = sword.xdirection;
				if (sword.xdirection == 0) {
					defender.hitDirection = sword.lastxdirection;
				} else {
					defender.hitDirection = sword.xdirection;
				}
				defender.hitvelocity_y = -3;
				defender.hitvelocity_x = 20 * defender.hitDirection;

				defender.isHit = true;

				int damage = sword.damagemomentum;
				sword.damagemomentum = sword.damagemomentum
						- defender.currenthealth;
				if (sword.damagemomentum < 0) {
					sword.damagemomentum = 0;
				}
				if (headshot == true) {
					damage = damage * 2;
				}
				if (DamageOFF == false) {
					if (defender.isPlayer == true) {
						//healthhud.setVisible(true);
					}
					defender.currenthealth = defender.currenthealth - damage;
				}
				if(defender.isPlayer==true){
					if((defender.currenthealth<(int)(defender.totalHealth/5))||(defender.currenthealth<4)){
						FXShowBloodSplatter();
					}
						
						
				}
				if (defender.currenthealth < 1) // DEFENDER DIED
				{
					soundbank.playSwishKill(SFX, SloMoON);
					if (defender.isPlayer == false) {
						Deathloop(defender, sword.level, sword.specificAttack,
								sword.xpos, sword.xdirection);
					} else {
						stickman.GameIsOver = true;
						return;
					}
				}
			}
		}
	}

	private void updateHUD() {
		UpdateHealthbar();
		
		if (stickman.isHit == true) {
			//healtext.setVisible(true);

			//healthhud.setVisible(true);
		}

		shurikenhud.setVisible(true);
		if(ControlStick){AttackHUDButton.setVisible(true);
		BowHUDButton.setVisible(true);}

		if ((stickman.weapon == 0) || (hideHUD == true)) {
			swordthrowhud.setVisible(false);
			// ninjasleftText.setVisible(false);
		} else {
			swordthrowhud.setVisible(true);
		}

		smokebombhud.setVisible(true);

		if ((hideHUD == true) || (stickman.xdirection != 0)
				|| (stickman.ydirection != 0) || (stickman.startattack == true)
				|| (stickman.currentlyjumping == true)) {

			//healtext.setVisible(false);
			bankText.setVisible(false);
			//healthhud.setVisible(false);

			/*
			 * shurikensText.setVisible(false);
			 * ninjasleftText.setVisible(false);
			 * currentlevelText.setVisible(false);
			 * ninjaslist.size()Text.setVisible(false);
			 */
			hudtimer = 0;
		} else {
			hudtimer++;
		}

		if ((hudtimer > 15) || (showHUD == true)) {
			//healtext.setVisible(true);
			bankText.setVisible(true);
			//healthhud.setVisible(true);
			ScoreAddText.setVisible(false);
		}
		if ((Healthupsprite.isAnimationRunning() == true)
				|| (Levelupsprite.isAnimationRunning() == true)) {
			//healthhud.setVisible(true);
			//healtext.setVisible(true);
		}

	}

	private void GameOverDialog(boolean gameisover) {
		if (gameisover == true) {
			hideHUD = true;
			ShurikenCountText.setVisible(false);
			SmokebombCountText.setVisible(false);

			slowmoskip = false;
			stickman.slowmotimer = 0;
			//healtext.setVisible(false);
			bankText.setVisible(false);
			//healthhud.setVisible(false);
			shurikenhud.setVisible(false);
			if(ControlStick){AttackHUDButton.setVisible(false);
			BowHUDButton.setVisible(false);}
			smokebombhud.setVisible(false);
			ScoreAddText.setVisible(false);
			StreakText.setVisible(false);
			ComboText.setVisible(false);
			ScoreAddText.detachSelf();
			StreakText.detachSelf();
			ComboText.detachSelf();
			//healtext.detachSelf();
			bankText.detachSelf();
			//healthhud.detachSelf();
			shurikenhud.detachSelf();
			if(ControlStick){	AttackHUDButton.detachSelf();
			BowHUDButton.detachSelf();}
			smokebombhud.detachSelf();
			SmokebombCountText.detachSelf();
			ShurikenCountText.detachSelf();

			if (firstRun == true) {
				exp = (int) stickman.kills;

			} else {
				int e = preferences.getInt("experience", 0);

				exp = e + stickman.kills;

			}
			// Debugger("money = "+money);
			settings_editor.putInt("experience", exp);
			settings_editor.putInt("money", money);
			settings_editor.commit();

			soundbank.playGong(SFX);
			// quick Game Over Splash before transition to Score screen
			Scene thisscene = new Scene();
			// scene.setChildScene(thisscene);
			this.mEngine.setScene(thisscene);
			this.Backgroundsprite = new Sprite(0, 0, this.screenwidth,
					this.screenheight, this.GameOverTex, this.VBO);
			this.bg = new SpriteBackground(Backgroundsprite);

			thisscene.setBackground(this.bg);
			DelayModifier pause = new DelayModifier(1.5f);

			pause.addModifierListener(new IModifierListener<IEntity>() {
				@Override
				public void onModifierStarted(
						final IModifier<IEntity> pModifier, final IEntity pItem) {
					StickmanActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {

						}
					});
				}

				@Override
				public void onModifierFinished(
						final IModifier<IEntity> pEntityModifier,
						final IEntity pEntity) {
					StickmanActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {

							loadGO();
						}
					});
				}
			});
			thisscene.registerEntityModifier(pause);
			this.mEngine.unregisterUpdateHandler(this);

		}
	}

	public void loadGO() {

		this.mEngine.unregisterUpdateHandler(this);

		//update_scorelist();
		stopService(soundservice);
		Intent intent = new Intent(this, GameOver.class);

		FlurryAgent.endTimedEvent(mode);
		FlurryAgent.logEvent("Player got to wave " + highestwavecompleted);

		intent.putExtra("score", stickman.score);// name, value pair
		intent.putExtra("level", stickman.level);
		intent.putExtra("biggestkill", stickman.biggestkill);
		intent.putExtra("kills", stickman.kills);
		intent.putExtra("combo", stickman.highestCombo);
		intent.putExtra("streak", stickman.highestStreak);
		intent.putExtra("wave", highestwavecompleted);
		intent.putExtra("money", money);

		startActivity(intent);

		// reloading=true;
		finish();
	}

	private void update_scorelist() {
		int cur_score = this.stickman.score;
		GetscoreList_asint();
		for (int i = 0; i < scorelist.size(); i++) {
			if (cur_score > scorelist.get(i)) {
				this.dbase.createRow("player1", this.stickman.score);
				this.dbase.close();
				return;
			}
		}

	}

	private void GetscoreList_asint() {
		Cursor list = this.dbase.GetAllRows();
		startManagingCursor(list);
		list.moveToFirst();
		// Debugger("SCORES", "scorelist size =  "+ list.getCount());
		for (int i = 0; i < list.getCount(); i++) {// for each row
			this.scorelist.add(i, list.getInt(2)); // get the String in column 2
			list.moveToNext();

		}
	}

	private void dropCorpse(GameCharacter stiff) {
		// float scale = stiff.currentscale;

		int xpos = (int) stiff.xpos;
		int ypos = (int) stiff.ypos;

		int direction = stiff.deathanim;
		// Toast.makeText(this,
		// "Currently there are "+Corpses.size()+" Corpses",
		// Toast.LENGTH_SHORT).setVisible(true);
		Corpse sprite = this.getcorpsefrompool();
		this.corpselist.add(sprite);

		sprite.timer = 1;
		sprite.assailant = stiff.assailant;
		sprite.isPlayer = stiff.isPlayer;
		sprite.hitPower = (int) (stiff.assailant.hitPower * .75);
		sprite.hitDirection = direction;
		sprite.deathanim = direction;
		sprite.hitvelocity_y = (int) (stiff.hitvelocity_y * 1.5);
		sprite.hitvelocity_x = stiff.hitvelocity_x * 2;
		sprite.rising = true;
		// sprite.setScale(scale, scale);
		// sprite.currentscale=scale;
		sprite.xpos = xpos;
		sprite.ypos = ypos;
		sprite.isHit = true;
		sprite.setColor(stiff.getColor());
		sprite.detachSelf();
		this.scene.attachChild(sprite);
		sprite.setPosition(sprite.xpos, sprite.ypos);

	}

	private Corpse getcorpsefrompool() {
		return this.CorpsePool.obtainPoolItem();
	}

	private void sendcorpsetopool(Corpse stiff) {
		this.corpselist.remove(stiff);
		this.CorpsePool.recyclePoolItem(stiff);

	}

	private int corpseFallLoop(Corpse sprite) {
		int difference;
		double localypos = (int) sprite.ypos;

		if (sprite.hitvelocity_y > 0) // Decides based on velocity_y whether the
										// sprite is in a state falling or
										// rising,
		{
			sprite.falling = true;
			sprite.rising = false;

		} else if (sprite.hitvelocity_y < 0) {
			sprite.falling = false;
			sprite.rising = true;

		}
		if (sprite.rising == true) {
			if (sprite.hitvelocity_y == 0) {
				sprite.topofjump = true;
			}
		} else {
			sprite.topofjump = false;
		}
		if ((sprite.collision == true) && (sprite.falling == true)) // find
																	// floor and
																	// level
																	// sprite to
																	// it
		{
			localypos = (int) (sprite.collider.getY() - sprite.Offset());
			/*
			 * if(sprite.hitvelocity_y>4) { sprite.hitvelocity_y =(int)
			 * (sprite.hitvelocity_y * -.25); sprite.rising = true;
			 * sprite.falling = false; sprite.collision = false; } else {
			 */
			sprite.hitvelocity_y = 0;
			sprite.antigravity = gravity;

			sprite.falling = false;

		}
		sprite.hitvelocity_y = sprite.hitvelocity_y + gravity
				- sprite.antigravity;
		if (sprite.hitvelocity_y > 40) {
			sprite.hitvelocity_y = 40;
		}
		localypos = localypos + sprite.hitvelocity_y;
		difference = (int) (localypos - sprite.ypos);

		return difference;

	}

	private void CorpsePosition() {

		if (corpselist.size() > 0) {
			for (int i = 0; i < corpselist.size(); i++) {

				Corpse sprite = corpselist.get(i);

				CorpseGroundPound(sprite, FXGroundPoundInstance);

				if (Physics.floorCollision(sprite, horizontals) == true) {
					sprite.antigravity = gravity;
					sprite.collision = true;
					sprite.startattack = false;

				} else {
					sprite.collision = false;
					sprite.antigravity = 0;
				}

				if (sprite.isHit == true) {

					if (sprite.collision == false) {
						sprite.rotationcounter = (int) (sprite.rotationcounter + (sprite.assailant.hitPower * .5));
						if (sprite.rotationcounter > 359) {
							sprite.rotationcounter = 0;
						}
						if (sprite.rotationcounter < -359) {
							sprite.rotationcounter = 0;
						}
						sprite.xdirection = sprite.hitDirection;
						sprite.xmovement = (sprite.hitDirection * sprite.assailant.hitPower);
						sprite.setRotationCenter(32, 58);
						// sprite.rotate((float)
						// (sprite.hitDirection*sprite.rotationcounter));
						// sprite.deathanim = sprite.assailant.hitDirection;
						sprite.ypos = sprite.ypos + corpseFallLoop(sprite);
						sprite.xpos = (int) (sprite.xpos + sprite.hitvelocity_x);

					} else {

						slideFriction(sprite);

						if (sprite.hitvelocity_x == 0) {

							sprite.startattack = false;
							sprite.isHit = false;
							sprite.setRotation(0);
						}
						// sprite.rotate(sprite.rotationcounter);
						sprite.ypos = sprite.ypos;
						sprite.xpos = sprite.xpos + sprite.hitvelocity_x;

					}

				}

				CorpseAnimator(sprite);

				// sprite.ypos = sprite.ypos - transY;

				int diffX = (int) Math.abs(camerabox.getX() - sprite.xpos);
				int diffY = (int) Math.abs(camerabox.getY() - sprite.ypos);

				sprite.setPosition(sprite.xpos, sprite.ypos);
				if ((diffX > (.5 * width)) || (diffY > (.5 * height))) {

					sendcorpsetopool(sprite);
				}

			}
		}
	}

	private int corpseFlopover(Corpse sprite) {
		if (sprite.rotationcounter > 360) {
			sprite.rotationcounter = sprite.rotationcounter - 360;
		}
		if (sprite.rotationcounter == 0) {
			return 1;
		}
		if (sprite.rotationcounter == 180) {
			return 1;
		}
		int rotationdifference1 = Math.abs(sprite.rotationcounter - 180);
		int rotationdifference2 = Math.abs(sprite.rotationcounter - 360);
		if ((rotationdifference2 < sprite.rotationspeed)
				|| (sprite.rotationcounter < sprite.rotationspeed)) {
			sprite.rotationspeed = 0;
			sprite.rotationcounter = 0;
			sprite.setRotation(sprite.rotationcounter);
			return 1;
		}
		if (rotationdifference1 < sprite.rotationspeed) {
			sprite.rotationspeed = 0;
			sprite.rotationcounter = 180;
			sprite.setRotation(sprite.rotationcounter);
			return 1;
		}

		if ((sprite.rotationcounter < 90) && (sprite.rotationcounter > 0)) {
			sprite.rotationspeed--;
			sprite.rotationcounter = sprite.rotationcounter
					- sprite.rotationspeed;
			sprite.setRotation(sprite.rotationcounter);
			return 0;
		}
		if ((sprite.rotationcounter >= 90) && (sprite.rotationcounter < 180)) {
			sprite.rotationspeed++;
			sprite.rotationcounter = sprite.rotationcounter
					+ sprite.rotationspeed;
			sprite.setRotation(sprite.rotationcounter);
			return 0;
		}
		if ((sprite.rotationcounter > 180) && (sprite.rotationcounter < 270)) {
			sprite.rotationspeed--;
			sprite.rotationcounter = sprite.rotationcounter
					- sprite.rotationspeed;
			sprite.setRotation(sprite.rotationcounter);
			return 0;
		}
		if ((sprite.rotationcounter >= 270) && (sprite.rotationcounter < 360)) {
			sprite.rotationspeed++;
			sprite.rotationcounter = sprite.rotationcounter
					+ sprite.rotationspeed;
			sprite.setRotation(sprite.rotationcounter);
			return 0;
		}
		return 0;
	}

	private void CorpseAnimator(Corpse sprite) {

		if (sprite.deathanim == left) {
			sprite.animate(oneFrame, this.ninjadeathl, -1);
		}
		if (sprite.deathanim == right) {
			sprite.animate(oneFrame, this.ninjadeathr, -1);
		}

	}

	private boolean corpseanimcheck(Corpse sprite, int anim) {// if(animcheck(sprite,1)
																// == true){
																// return;}
		boolean answer1 = false;

		// Debugger(String.format("count: %d", count));

		if (anim == sprite.oldanim) {
			answer1 = true;

		}
		sprite.oldanim = anim;
		return answer1;
	}

	private void corpseSliding() {
		if (corpselist.size() > 0) {

			for (int i = 0; i < corpselist.size(); i++) {

				Corpse csprite = corpselist.get(i);
				if (csprite.timer != 0) {
					csprite.timer++;
					if (csprite.timer > 200) {

						csprite.timer = 0;
						csprite.setVisible(false);
						showAngel(csprite);

					}
				}

				if ((stickman.collidesWith(csprite.bodyRect, feet))
						&& (stickman.isCrouching == true)) // corpse collection
				{

					stickman.angelsReleased++;
					csprite.timer = 0;
					csprite.setVisible(false);
					showAngel(csprite);

					sendcorpsetopool(csprite);
				}
			}
		}
	}

	private void slideFriction(Corpse sprite) {
		if (sprite.hitvelocity_x != 0) {
			if (sprite.hitvelocity_x > 0) {
				if (sprite.hitvelocity_x < 1) {
					sprite.hitvelocity_x = 0;
					sprite.isHit = false;
					return;
				} else {
					sprite.hitvelocity_x--;
					if (sprite.hitvelocity_x == 0) {
						sprite.isHit = false;
					}
					return;
				}

			} else if (sprite.hitvelocity_x < 0) {
				if (sprite.hitvelocity_x > -1) {
					sprite.hitvelocity_x = 0;
					sprite.isHit = false;
					return;
				} else {
					sprite.hitvelocity_x++;
					if (sprite.hitvelocity_x == 0) {
						sprite.isHit = false;
					}
					return;
				}

			}
		}

	}

	private int ninjaHeadCount() {
		int currentninjasLeftAlive = 0;
		for (int i = 0; i < ninjaslist.size(); i++) {
			if ((ninjaslist.get(i).isAlive == true)
					&& (ninjaslist.get(i).disabled == false)) {
				currentninjasLeftAlive++;
			}
		}
		return currentninjasLeftAlive;
	}

	private void MoreNinjas() {
		if (StartNextWave == false) {
			int currentcount = ninjaHeadCount();
			int ninjasleftinlevel = totalninjasforlevel - ninjasspawned;
			if ((ninjasleftinlevel < 2) && (BossSpawned == false)) {
				Debugger("Spawnboss set true in MoreNinjas");
				SpawnBoss = true;
			}
			// Debugger("tot "+ totalninjasforlevel + " spnd "+ ninjasspawned +
			// " left " + ninjasleftinlevel);
			if (totalninjasleft <= currentcount) {
				totalninjasleft = currentcount;
				StopSpawningNinjas = true;
				for (int p = 0; p < poweruplist.size(); p++) {
					if (poweruplist.get(p).powerupType == isExtraNinja) {
						poweruplist.get(p).powerupType++;
						poweruplist.get(p).powerupType = isHealth;
						poweruplist.get(p).animate(oneFrame, this.healthicon,
								-1);
					}
				}
			}

			if ((totalninjasleft < 1) && (StopSpawningNinjas == true)) // in
																		// case
																		// one
																		// is
																		// stuck
																		// somewhere,
																		// it'll
																		// fish
																		// it
																		// out
			{
				for (int i = 0; i < ninjaslist.size(); i++) {
					sendninjatopool(ninjaslist.get(i));
					// if(ninjaslist.get(i).isAlive ==
					// true){ninjaslist.get(i).RandomizeMe=true;}
				}

				WaveEnded = true;
				WaveComplete();
				soundbank.playGong(SFX);
			}

		}
	}

	private PowerUp getpowfrompool() {
		PowerUp temp = this.PowerUpPool.obtainPoolItem();
		temp.disabled = false;
		return temp;
	}

	private void sendpowtopool(PowerUp pItem) {
		pItem.setVisible(false);
		pItem.setColor(Color.WHITE);
		pItem.disabled = true;
		pItem.powerupType = 0;
		this.poweruplist.remove(pItem);
		pItem.detachSelf();
		this.PowerUpPool.recyclePoolItem(pItem);

	}

	private GameCharacter getninjafrompool() {
		GameCharacter ninja = this.NinjaPool.obtainPoolItem();
		ninja.disabled = false;
		ninja.isAlive = true;
		ninja.weapon = 0;
		ninja.level = gamelevel;
		ninja.getlevel();
		ninja.currenthealth = ninja.level * 10;
		ninja.justAppeared = true;
		ninja.setVisible(false);

		ninja.RandomizeMe = true;

		/*
		 * for(int g=0;g<levels;g++) { sprite.levelUp(); }
		 */
		ninjasspawned++;

		ninjaslist.add(ninja);
		ninja.detachSelf();
		scene.attachChild(ninja);

		return ninja;
	}

	private void sendninjatopool(GameCharacter ninja) {
		this.ninjaslist.remove(ninja);
		ninja.xpos = 99999999;
		ninja.setPosition(ninja.xpos, ninja.ypos);
		ninja.disabled = true;
		ninja.myTurn = false;
		ninja.isAlive = false;
		ninja.detachSelf();
		this.NinjaPool.recyclePoolItem(ninja);
	}

	private GameCharacter getbossfrompool() {
		Debugger("spawned boss from pool");
		GameCharacter boss = this.BossPool.obtainPoolItem();
		boss.disabled = false;
		ninjaslist.add(boss);
		BossSpawned = true;
		SpawnBoss = false;
		boss.level = stickman.level + 1;
		boss.getlevel();
		boss.isBoss = true;
		boss.weapon = 1;
		boss.detachSelf();
		scene.attachChild(boss);
		boss.setVisible(false);
		boss.isAlive = true;
		boss.justAppeared = true;
		boss.RandomizeMe = true;

		return boss;
	}

	private void sendbosstopool(GameCharacter boss) {
		int before = ninjaslist.size();
		ninjaslist.remove(boss);
		int after = ninjaslist.size();
		boss.myTurn = false;
		boss.xpos = 99999999;
		boss.setPosition(boss.xpos, boss.ypos);
		Debugger("boss returned to pool " + before + ", " + after);

		boss.detachSelf();
		boss.setVisible(false);
		boss.isAlive = false;
		boss.disabled = true;
		this.BossPool.recyclePoolItem(boss);
	}

	private void WaveCompleteGOLD() {

		for (int c = 0; c < this.poweruplist.size(); c++) {

			if (runbroken == false) {
				return;
			}

			// Toast.makeText(this,
			// "Currently there are "+PowerUps.size()+" angels",
			// Toast.LENGTH_SHORT).setVisible(true);

			PowerUp psprite = this.poweruplist.get(c);

			// psprite.setVisible(true);

			// Debugger("FPS: "+fpsCounter.getFPS());

			psprite.ypos = psprite.ypos - 65;
			psprite.setPosition(psprite.xpos, psprite.ypos);
			psprite.hit = 0;
			psprite.timer = 1;
			psprite.velocity_y = -20;
			psprite.collision = false;
			boolean justturned = false;
			psprite.isHit = false;
			int holder = psprite.powerupType;
			if(psprite.powerupType<isCopperCoin)
			{
				psprite.powerupType = isCopperCoin;
				psprite.animate(oneFrame, this.coppercoin, -1);
				justturned = true;
			}
			else if((psprite.powerupType<isSilverCoin)&&(justturned == false))
			{
				psprite.powerupType = isSilverCoin;
				psprite.animate(oneFrame, this.silvercoin, -1);
				justturned = true;
			}
			else if((psprite.powerupType<isGoldCoin)&&(justturned == false))
			{
				psprite.powerupType = isGoldCoin;
				psprite.animate(oneFrame, this.goldcoin, -1);
				justturned = true;
			}
			else if((psprite.powerupType<isTopaz)&&(justturned == false))
			{
				psprite.powerupType = isTopaz;
				psprite.animate(oneFrame, this.topaz, -1);
				justturned = true;
			}
			else if((psprite.powerupType<isAmnethyst)&&(justturned == false))
			{
				psprite.powerupType = isAmnethyst;
				psprite.animate(oneFrame, this.amnethyst, -1);
				justturned = true;
			}
			else if((psprite.powerupType<isEmerald)&&(justturned == false))
			{
				psprite.powerupType = isEmerald;
				psprite.animate(oneFrame, this.emerald, -1);
				justturned = true;
			}
			else if((psprite.powerupType<isSaphire)&&(justturned == false))
			{
				psprite.powerupType = isSaphire;
				psprite.animate(oneFrame, this.saphire, -1);
				justturned = true;
			}
			else if((psprite.powerupType<isRuby)&&(justturned == false))
			{
				psprite.powerupType = isRuby;
				psprite.animate(oneFrame, this.ruby, -1);
				justturned = true;	
			}
			else if((psprite.powerupType<isBlackPearl)&&(justturned == false))
			{
				psprite.powerupType = isBlackPearl;
				psprite.animate(oneFrame, this.blackpearl, -1);
				justturned = true;	
			}
			else if((psprite.powerupType<isDiamond)&&(justturned == false))
			{
				psprite.powerupType = isDiamond;
				psprite.animate(oneFrame, this.diamond, -1);
					
			}
			
			Debugger("Powerup Changed to gold from " + holder);

			

		}

	}

	private void DropPowerup(GameObject defender) {
		if (runbroken == false) {
			return;
		}

		int randomseed = (int) (Math.random() * 100);

		PowerUp sprite = null;
		// Toast.makeText(this,
		// "Currently there are "+PowerUps.size()+" angels",
		// Toast.LENGTH_SHORT).setVisible(true);

		sprite = this.getpowfrompool();

		sprite.detachSelf();
		sprite.animate(oneFrame, this.blank, -1);
		this.scene.attachChild(sprite);
		sprite.setVisible(true);
		sprite.cycletimer = 0;
		// Debugger("FPS: "+fpsCounter.getFPS());
		sprite.hit = 0;
		sprite.isHit = false;
		sprite.timer = 1;
		sprite.xpos = defender.xpos;
		sprite.ypos = defender.ypos - 65;
		sprite.velocity_y = -20;
		this.poweruplist.add(sprite);

		if (defender.isBoss == true) {
			randomseed = 98;
		}

		if (Mode != EasyMode) {
			if ((StopSpawningNinjas == false) && (ninjaHeadCount() < 2)) {

				sprite.powerupType = isExtraNinja;
				sprite.animate(oneFrame, this.lilninjaface, -1);
				sprite.setPosition(sprite.xpos, sprite.ypos);
				return;
			}
		}
		if (Mode == EasyMode) {
			intensity = 7;
		}

		if ((randomseed > (40 - intensity + ninjaslist.size()))
				&& (randomseed <= 39)) {

			if ((StopSpawningNinjas == true) || (fpsCounter.getFPS() < 20)) {
				fpsCounter.reset();
				sprite.powerupType = isHealth;
				sprite.animate(oneFrame, this.healthicon, -1);
				sprite.setPosition(sprite.xpos, sprite.ypos);
				return;

			} else {
				fpsCounter.reset();
				sprite.powerupType = isExtraNinja;
				sprite.animate(oneFrame, this.lilninjaface, -1);
				sprite.setPosition(sprite.xpos, sprite.ypos);
				return;
			}

		} else if ((randomseed >= 40) && (randomseed <= 54)) {
			sprite.powerupType = isArrows;
			sprite.animate(oneFrame, this.arrowsicon, -1);
			sprite.setPosition(sprite.xpos, sprite.ypos);
			return;
		} else if ((randomseed >= 55) && (randomseed <= 70)) {

			sprite.powerupType = isShurikensPowerUp;
			sprite.animate(oneFrame, this.shurikens, -1);
			sprite.setPosition(sprite.xpos, sprite.ypos);
			return;

		} else if ((randomseed > 70) && (randomseed < 85)) {
			sprite.powerupType = isHealth;
			sprite.animate(oneFrame, this.healthicon, -1);
			sprite.setPosition(sprite.xpos, sprite.ypos);
			return;
		} else if ((randomseed >= 85) && (randomseed < 90)
				&& (SloMoON == false)) {
			sprite.powerupType = isSlowmo;
			sprite.animate(oneFrame, this.slomoicon, -1);
			sprite.setPosition(sprite.xpos, sprite.ypos);
			return;
		} else if ((randomseed >= 90) && (randomseed < 97)) {
			sprite.powerupType = isSmokebomb;
			sprite.animate(oneFrame, this.lilbomb, -1);
			sprite.setPosition(sprite.xpos, sprite.ypos);
			return;
		} else if (randomseed >= 97) {

			int randomseed2 = (int) (Math.random() * 100);

			if (randomseed2 < 60) {
				sprite.powerupType = isSuperHealth;
				sprite.animate(oneFrame, this.bighealthicon, -1);
				sprite.setPosition(sprite.xpos, sprite.ypos);
				return;
			} else if ((randomseed2 >= 60) && (randomseed2 < 90)) {
				sprite.powerupType = isHadokenBuff;
				sprite.animate(oneFrame, this.hadokenbuff, -1);
				sprite.setPosition(sprite.xpos, sprite.ypos);
				return;
			} else if ((randomseed2 >= 90)) {
				sprite.powerupType = isLevelUp;
				sprite.animate(oneFrame, this.levelup, -1);
				sprite.setPosition(sprite.xpos, sprite.ypos);
				return;
			}

		}

	}

	private void PowerUpCombiner() {

		if (poweruplist.size() != 0) {
			PowerUp P1;
			PowerUp P2;
			for (int p1 = 0; p1 < poweruplist.size(); p1++) {
				for (int p2 = 0; p2 < poweruplist.size(); p2++) {
					if (p1 == p2) {
						break;
					}
					Debugger("P1 = " + p1 + " P2 = " + p2);
					if (poweruplist.get(p1) != null) {
						P1 = poweruplist.get(p1);
					} else {
						break;
					}
					if (poweruplist.get(p1) != null) {
						P2 = poweruplist.get(p2);
					} else {
						break;
					}

					
					
					if(P1.chesttimer > 0)
					{
						P1.chesttimer++;
						
						if(P1.chesttimer>100){
							P1.chesttimer = 0;
							P1.chestitemdropped = false;
							sendpowtopool(P1);
							return;
							
						}
					}
					if(P2.chesttimer != 0)
					{
						P2.chesttimer++;
						
						if(P2.chesttimer>100){
							P2.chesttimer = 0;
							P1.chestitemdropped = false;
							sendpowtopool(P2);
							return;
							
						}
					}
					if (P1.bodyRect.collidesWith(P2.bodyRect)) {
						if (P1.powerupType >= P2.powerupType) {
							CyclePowerup(P1);
							FXSmokePuff(P2);
							sendpowtopool(P2);
							return;
						} else {
							CyclePowerup(P2);
							FXSmokePuff(P1);
							sendpowtopool(P1);
							return;
						}
					}
				}
			}
		}
	}

	private void CyclePowerup(PowerUp psprite) {
		if (runbroken == false) {
			return;
		}

		// Toast.makeText(this,
		// "Currently there are "+PowerUps.size()+" angels",
		// Toast.LENGTH_SHORT).setVisible(true);
		
		if (psprite.powerupType == isGloves) {
			return;
		}
		if (psprite.powerupType == isHat) {
			return;
		}
		if (psprite.powerupType == isPants) {
			return;
		}
		if (psprite.powerupType == isShirt) {
			return;
		}
		if (psprite.powerupType == isShoes) {
			return;
		}
		if (psprite.powerupType == isFace) {
			return;
		}
		if (psprite.powerupType == isWings) {
			return;
		}
		
		if (psprite.powerupType == isCopperCoin) {
			return;
		}
		if (psprite.powerupType == isSilverCoin) {
			return;
		}
		if (psprite.powerupType == isGoldCoin) {
			return;
		}
		if (psprite.powerupType == isTopaz) {
			return;
		}
		if (psprite.powerupType == isAmnethyst) {
			return;
		}
		if (psprite.powerupType == isEmerald) {
			return;
		}
		if (psprite.powerupType == isRuby) {
			return;
		}
		if (psprite.powerupType == isSaphire) {
			return;
		}
		if (psprite.powerupType == isBlackPearl) {
			return;
		}
		if (psprite.powerupType == isDiamond) {
			return;
		}
		if (psprite.powerupType == isChest) {
			return;
		}
		if (psprite.powerupType == isKey) {
			return;
		}
		
		
		
	
		
		
		
		// psprite.setVisible(true);

		// Debugger("FPS: "+fpsCounter.getFPS());
		psprite.hit = 0;
		psprite.timer = 1;
		psprite.velocity_y = -20;
		psprite.ypos = psprite.ypos - 65;
		psprite.setPosition(psprite.xpos, psprite.ypos);
		psprite.collision = false;
		psprite.isHit = false;
		int holder = psprite.powerupType;
		psprite.powerupType = psprite.powerupType + 1;
		if (psprite.powerupType > isFrenzy) {
			psprite.powerupType = isExtraNinja;
		}
		if (psprite.powerupType == isExtraNinja) {
			if (StopSpawningNinjas) {
				psprite.powerupType = psprite.powerupType + 1;
			}
		}
		// if(!midasHadokens){if(psprite.powerupType>6){psprite.powerupType =
		// holder;}}
		Debugger("PowerupType Changed to " + psprite.powerupType + " from "
				+ holder);

		if (psprite.powerupType == isShurikensPowerUp) {

			psprite.animate(oneFrame, this.shurikens, -1);

			return;
		}

		else if (psprite.powerupType == isHealth) {

			psprite.animate(oneFrame, this.healthicon, -1);

			return;
		} else if (psprite.powerupType == isExtraNinja) {

			psprite.animate(oneFrame, this.lilninjaface, -1);

			return;
		} else if (psprite.powerupType == isSlowmo) {
			psprite.animate(oneFrame, this.slomoicon, -1);

			return;
		} else if (psprite.powerupType == isSmokebomb) {

			psprite.animate(oneFrame, this.lilbomb, -1);

			return;
		} else if (psprite.powerupType == isSuperHealth) {

			psprite.animate(oneFrame, this.bighealthicon, -1);

			return;
		} else if (psprite.powerupType == isArrows) {
			psprite.animate(oneFrame, this.arrowsicon, -1);

			return;
		} else if (psprite.powerupType == isHadokenBuff) {

			psprite.animate(oneFrame, this.hadokenbuff, -1);

			return;
		} else if (psprite.powerupType == isFrenzy) {

			psprite.animate(oneFrame, this.frenzy, -1);

			return;
		} else if (psprite.powerupType == isLevelUp) {

			psprite.animate(oneFrame, this.levelup, -1);

			return;
		}

	}

	int PowerUpFallLoop(PowerUp sprite) {
		int difference;

		sprite.antigravity = 0;

		if (Physics.floorCollision(sprite, horizontals) == true) {

			sprite.antigravity = gravity;
			sprite.xdirection = 0;
			sprite.collision = true;
			sprite.setPosition(sprite.xpos, sprite.ypos);
			sprite.velocity_y = 0;
		} else {
			sprite.collision = false;
			sprite.antigravity = 0;
		}

		double localypos = (int) sprite.ypos;

		sprite.velocity_y = sprite.velocity_y + gravity - sprite.antigravity;
		if (sprite.velocity_y > 20) {
			sprite.velocity_y = 20;
		}
		localypos = localypos + sprite.velocity_y;
		difference = (int) (localypos - sprite.ypos);

		return difference;

	}

	void PowerUpPosition() {
		if (poweruplist.size() > 0) {
			for (int i = 0; i < poweruplist.size(); i++) {
				PowerUp sprite = poweruplist.get(i);
				if (sprite.cycletimer != 0) {
					sprite.cycletimer++;
					if (sprite.cycletimer > 50) {
						sprite.cycletimer = 0;
					}

				}

				sprite.ypos = sprite.ypos + PowerUpFallLoop(sprite);
				sprite.xpos = sprite.xpos;
				sprite.setPosition(sprite.xpos, sprite.ypos);

			}
		}

	}

	void PowerupCollision() {
		if (poweruplist.size() > 0) {
			for (int i = 0; i < poweruplist.size(); i++) {
				PowerUp psprite = poweruplist.get(i);

				if (hadokenlist.size() > 0) {
					for (int h = 0; h < hadokenlist.size(); h++) {
						Hadoken hadoken = hadokenlist.get(h);

						hadoken.startattack = true;

						updateHitCollision(psprite, hadoken);

					}
				}
				
				
				
/***
				if (psprite.timer != 0) {
					psprite.timer++;
					if (psprite.timer > 2000) {
						this.sendpowtopool(psprite);
						return;
					}
				}
***/
				if (stickman.collidesWith(psprite.bodyRect, body)) {

					if (psprite.powerupType == isShurikensPowerUp)// collided
																	// sprite is
																	// a
																	// ShurikenPowerUp
					{
						FXShurikenGrab();
						stickman.shuriken = stickman.shuriken + 5;

						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isHealth)// collided
																// sprite is a
																// Health Boost
					{

						stickman.currenthealth = stickman.currenthealth + 10;
						FXHealthUp();

						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isSlowmo) {

						stickman.slowmotimer = 1;
						FXSlowMo();

						this.sendpowtopool(psprite);
						return;
					}	else if (psprite.powerupType == isSmokebomb) {
						stickman.smokebombs = stickman.smokebombs + 3;
						FXSmokeBombPickup();

						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isArrows) {
						stickman.arrows = stickman.arrows + 7;
						stickman.arrowbuff=stickman.arrowbuff+1;
						// FXSmokeBombPickup();

						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isSuperHealth) {

						/*
						 * if(stickman.currenthealth<stickman.totalHealth) {
						 * stickman.currenthealth=stickman.totalHealth; } else {
						 */
						stickman.currenthealth = stickman.currenthealth + 50;
						// }
						FXHealthUp();
						FXSmokePuff(psprite);
						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isCopperCoin) {

						money = money + 1;
						stickman.score=stickman.score+1;
						// FXHealthUp();s
						FXAddCoin(1);
						this.soundbank.playCoin(SFX, SloMoON);
						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isSilverCoin) {

						money = money + 10;
						stickman.score=stickman.score+10;
						// FXHealthUp();s
						FXAddCoin(10);
						this.soundbank.playCoin(SFX, SloMoON);
						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isGoldCoin) {

						money = money + 50;
						stickman.score=stickman.score+50;
						// FXHealthUp();s
						FXAddCoin(50);
						this.soundbank.playCoin(SFX, SloMoON);
						this.sendpowtopool(psprite);
						return;
					}else if (psprite.powerupType == isTopaz) {

						money = money + 50;
						stickman.score=stickman.score+50;
						stickman.Topaz++;
						stickman.smokebombs= stickman.smokebombs+50;
						if(stickman.smokebombs>99){stickman.smokebombs = 99;}
						// FXHealthUp();s
						FXAddCoin(50);
						this.soundbank.playJewel(SFX, SloMoON);
						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isAmnethyst) {

						money = money + 75;
						stickman.score=stickman.score+75;
						stickman.Amnethyst++;
						stickman.arrows= stickman.arrows+50;
						if(stickman.arrows>99){stickman.arrows = 99;}
						// FXHealthUp();s
						FXAddCoin(75);
						this.soundbank.playJewel(SFX, SloMoON);
						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isEmerald) {

						money = money + 100;
						stickman.score=stickman.score+100;
						
						// FXHealthUp();s
						FXAddCoin(100);
						this.soundbank.playJewel(SFX, SloMoON);
						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isRuby) {

						money = money + 150;
						stickman.score=stickman.score+150;
						// FXHealthUp();s
						FXAddCoin(150);
						stickman.currenthealth=stickman.currenthealth+25;
						this.soundbank.playJewel(SFX, SloMoON);
						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isSaphire) {

						money = money + 200;
						stickman.score=stickman.score+200;
						stickman.shuriken=99;
						
						// FXHealthUp();s
						FXAddCoin(200);
						this.soundbank.playJewel(SFX, SloMoON);
						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isBlackPearl) {

						money = money - 500;
						stickman.score=stickman.score - 500;
						if(money < 0){money=0;}
						if(stickman.score<0){stickman.score = 0;}
						// FXHealthUp();s
						FXAddCoin(-500);
						this.soundbank.playBlackPearl(SFX, SloMoON);
						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isDiamond) {

						money = money + 1000;
						stickman.score=stickman.score+1000;
						// FXHealthUp();s
						FXAddCoin(50);
						this.soundbank.playJewel(SFX, SloMoON);
						this.sendpowtopool(psprite);
						return;
					} else if (psprite.powerupType == isChest) {
						FXOpenChest(psprite);
						
						if(!psprite.chestitemdropped){
						 DropRandomItem(psprite);
						 psprite.chestitemdropped = true;
						 }
						
						
					} else if (psprite.powerupType == isShoes) {
						stickman.shoes++;
					   if (stickman.shoes ==8) {stickman.shoes =1;}
					   settings_editor.putInt("stickmanshoes", stickman.shoes);
					   settings_editor.commit();
					   this.sendpowtopool(psprite);
					   textureswitch();
						return;
						
					
					} else if (psprite.powerupType == isGloves) {
						stickman.gloves++;
					   if (stickman.gloves ==1) {stickman.gloves =0;}
					   settings_editor.putInt("stickmangloves", stickman.gloves);
					   settings_editor.commit();
					   this.sendpowtopool(psprite);
					   textureswitch();
						return;
						
					
					} else if (psprite.powerupType == isHat) {
						stickman.hats++;
					   if (stickman.hats ==9) {stickman.hats =1;}
					   settings_editor.putInt("stickmanhats", stickman.hats);
					   settings_editor.commit();
					   this.sendpowtopool(psprite);
					   textureswitch();
						return;
						
					
					} else if (psprite.powerupType == isPants) {
						stickman.pants++;
					   if (stickman.pants ==8) {stickman.pants =1;}
					   settings_editor.putInt("stickmanpants", stickman.pants);
					   settings_editor.commit();
					   this.sendpowtopool(psprite);
					   textureswitch();
						return;
						
					
					} else if (psprite.powerupType == isShirt) {
						stickman.shirts++;
					   if (stickman.shirts ==8) {stickman.shirts =0;}
					   settings_editor.putInt("stickmanshirts", stickman.shirts);
					   settings_editor.commit();
					   this.sendpowtopool(psprite);
					   textureswitch();
						return;
					
					} else if (psprite.powerupType == isWings) {
						stickman.wings++;
					   if (stickman.wings ==1) {stickman.wings =0;}
					   settings_editor.putInt("stickmanwings", stickman.wings);
					   settings_editor.commit();
					   this.sendpowtopool(psprite);
					   textureswitch();
						return;
						
					
					} else if (psprite.powerupType == isFace) {
						stickman.faces++;
					   if (stickman.faces ==1) {stickman.faces =0;}
					   settings_editor.putInt("stickmanwings", stickman.wings);
					   settings_editor.commit();
					   this.sendpowtopool(psprite);
					   textureswitch();
						return;
						
					
					} else if (psprite.powerupType == isKey) {

						
						stickman.Key++;
						this.soundbank.playCoin(SFX, SloMoON);
						this.sendpowtopool(psprite);
						return;
						
						
					} else if (psprite.powerupType == isLevelUp) {

						stickman.levelUp(1);
						FXplayerLevelUp();
						this.sendpowtopool(psprite);
						return;
						
					
						
					} else if (psprite.powerupType == isFrenzy) {

						stickman.NinjaFrenzy = true;
						this.sendpowtopool(psprite);
						return;
						
						
					} else if (psprite.powerupType == isHadokenBuff) {
						stickman.hadokenbuff++;
						stickman.hadokenspeed++;
						HadokenFuryStart = true;
						HadokenFuryTimer = 0;

						this.sendpowtopool(psprite);
						return;
					} else if ((StopSpawningNinjas == false)
							&& (psprite.powerupType == isExtraNinja)) {

						FXNewNinja();
						getninjafrompool();
						this.sendpowtopool(psprite);
						return;
						// Debugger("picked up ninja token");

					} else {
						this.sendpowtopool(psprite);
					}
					Debugger("powerup type messed up");

				}
			}
			}
		}

	



	private void DropRandomItem(PowerUp chest) {
	
		int itemtype = (int) (Math.random() * 6);
		int itemstyle = (int) (Math.random() * 100);
		int itemstrength = 1;
		String itemname ="";
		
		
		
			if (runbroken == false) {
				return;
			}

		
			PowerUp sprite = null;
			// Toast.makeText(this,
			// "Currently there are "+PowerUps.size()+" angels",
			// Toast.LENGTH_SHORT).setVisible(true);

			sprite = this.getpowfrompool();

			sprite.detachSelf();
			sprite.animate(oneFrame, this.blank, -1);
			this.scene.attachChild(sprite);
			sprite.setVisible(true);
			//sprite.cycletimer = 0;
			// Debugger("FPS: "+fpsCounter.getFPS());
			sprite.hit = 0;
			sprite.isHit = false;
			sprite.timer = 1;
			sprite.xpos = chest.xpos;
			sprite.ypos = chest.ypos - 65;
			sprite.velocity_y = -40;
			this.poweruplist.add(sprite);
			if(itemtype ==0){sprite.powerupType = isHat;sprite.animate(oneFrame, this.hatsitem, -1);}
			if(itemtype ==1){sprite.powerupType = isShoes;sprite.animate(oneFrame, this.shoesitem, -1);}
			if(itemtype ==2){sprite.powerupType = isShirt;sprite.animate(oneFrame, this.shirtsitem, -1);}
			if(itemtype ==3){sprite.powerupType = isFace;sprite.animate(oneFrame, this.facesitem, -1);}
			if(itemtype ==4){sprite.powerupType = isGloves;sprite.animate(oneFrame, this.glovesitem, -1);}
			if(itemtype ==5){sprite.powerupType = isPants;sprite.animate(oneFrame, this.pantsitem, -1);}
			if(itemtype ==6){sprite.powerupType = isWings;sprite.animate(oneFrame, this.wingsitem, -1);}
			sprite.setColor(Color.PINK);

			
			
		
			sprite.setPosition(sprite.xpos, sprite.ypos);
			return;

		}

		
		
	

	private void HadokenFury() {
		// TODO Auto-generated method stub
		if (HadokenFuryStart) {
			HadokenFuryTimer++;

			if (HadokenFuryTimer == 1) {
				Hadoken(-1);
				Hadoken(1);
				if (stickman.hadokenbuff == 1) {
					HadokenFuryFinish();
				}
			}
			if (HadokenFuryTimer == 10) {
				Hadoken(-1);
				Hadoken(1);
				if (stickman.hadokenbuff == 2) {
					HadokenFuryFinish();
				}
			}
			if (HadokenFuryTimer == 20) {
				Hadoken(-1);
				Hadoken(1);
				if (stickman.hadokenbuff == 3) {
					HadokenFuryFinish();
				}
			}
			if (HadokenFuryTimer == 30) {
				Hadoken(-1);
				Hadoken(1);
				if (stickman.hadokenbuff == 4) {
					HadokenFuryFinish();
				}
			}
			if (HadokenFuryTimer == 40) {
				Hadoken(-1);
				Hadoken(1);
				if (stickman.hadokenbuff == 5) {
					HadokenFuryFinish();
				}
			}
			if (HadokenFuryTimer == 50) {
				Hadoken(-1);
				Hadoken(1);
				if (stickman.hadokenbuff == 6) {
					HadokenFuryFinish();
				}
			}
			if (HadokenFuryTimer == 60) {
				Hadoken(-1);
				Hadoken(1);
				if (stickman.hadokenbuff == 7) {
					HadokenFuryFinish();
				}
			}
			if (HadokenFuryTimer == 70) {
				Hadoken(-1);
				Hadoken(1);
				if (stickman.hadokenbuff == 8) {
					HadokenFuryFinish();
				}
			}
			if (HadokenFuryTimer == 80) {
				Hadoken(-1);
				Hadoken(1);
				if (stickman.hadokenbuff == 9) {
					HadokenFuryFinish();
				}
			}
			if (HadokenFuryTimer == 90) {
				Hadoken(-1);
				Hadoken(1);
				if (stickman.hadokenbuff == 10) {
					HadokenFuryFinish();
				}
			}

			if (HadokenFuryTimer > 100) {
				HadokenFuryFinish();
			}
		}
	}

	private void HadokenFuryFinish() {

		HadokenFuryTimer = 0;
		HadokenFuryStart = false;
	}

	private Angel getangelfrompool() {
		return this.AngelPool.obtainPoolItem();
	}

	private void sendangeltopool(Angel pItem) {
		this.angellist.remove(pItem);
		this.AngelPool.recyclePoolItem(pItem);

	}

	private void AngelPosition() {
		if (angellist.size() > 0) {
			Angel sprite;
			for (int i = 0; i < angellist.size(); i++) {
				sprite = angellist.get(i);

				int xpos = (int) sprite.xpos;
				// sprite.ypos = sprite.ypos - transY;
				int ypos = (int) (sprite.ypos - 10);
				// Toast.makeText(this, "Showing angel at " + sprite.xpos + " "+
				// sprite.ypos, Toast.LENGTH_SHORT).setVisible(true);
				sprite.setPosition(xpos, ypos);
				sprite.xpos = xpos;
				sprite.ypos = ypos;
				int diffX = (int) Math.abs(stickman.xpos - sprite.xpos);
				int diffY = (int) Math.abs(stickman.ypos - sprite.ypos);

				if ((diffX > width) || (diffY > height)) {

					this.sendangeltopool(sprite);
				}
			}
		} else {
			return;
		}
	}

	private void showAngel(Corpse stiff) {
		int xpos = (int) stiff.xpos;
		int ypos = (int) stiff.ypos;

		Angel sprite = this.getangelfrompool();

		this.angellist.add(sprite);
		sprite.detachSelf();
		this.scene.attachChild(sprite);

		sprite.xpos = xpos;
		sprite.ypos = ypos;
		soundbank.playAngel(SFX, SloMoON);
		sprite.setPosition(sprite.xpos, sprite.ypos);
		sprite.animate(tenFrame, angel, -1);
		sprite.setVisible(true);
		return;
	}

	private void SmokeBomb(GameCharacter stickman) {
		FXSmokePuff(stickman);
		Physics.randomizePlayer(stickman, accellerometerSpeedX);
		FXSmokePuff(stickman);
		stickman.setVisible(true);

	}

	private void ClearMoveMemory() {
		for (int h = 0; h < 6; h++) // clears the movememory
		{
			pastdirections[h] = 0;
		}
	}

	private void Hadoken(int direction) {

		// Debugger("Hadoken "+ direction);

		ClearMoveMemory();

		int xpos = (int) (stickman.xpos);
		int ypos = (int) (stickman.ypos);
		Hadoken sprite = getHadokenfrompool();
		this.hadokenlist.add(sprite);
		sprite.isHit = false;

		sprite.detachSelf();

		sprite.setVisible(true);
		this.scene.attachChild(sprite);

		sprite.thrower = stickman;
		sprite.hitPower = (int) (stickman.hitPower + stickman.hadokenbuff);

		soundbank.playHadoken(SFX);
		sprite.xpos = xpos;
		sprite.ypos = ypos;
		sprite.setPosition(sprite.xpos, sprite.ypos);

		sprite.xdirection = direction;

		if (sprite.xdirection != 0) {
			sprite.xmomentum = stickman.hadokenspeed * sprite.xdirection;
		}

		sprite.animate(hadokenfourFrame, hadokenstart, 0);
		if (sprite.xdirection == -1) {
			sprite.setFlippedHorizontal(true);

		} else if (sprite.xdirection == 1) {
			sprite.setFlippedHorizontal(false);

		}
		return;

	}

	private Hadoken getHadokenfrompool() {
		return this.HadokenPool.obtainPoolItem();
	}

	private void sendHadokentopool(Hadoken pItem) {
		this.hadokenlist.remove(pItem);
		pItem.isHit = false;
		pItem.hits = 0;
		this.HadokenPool.recyclePoolItem(pItem);

	}

	private SmokePuff getSmokePufffrompool() {
		SmokePuff temp = this.smokepuffpool.obtainPoolItem();
		smokepufflist.add(temp);
		this.scene.attachChild(temp);
		temp.setVisible(true);
		return temp;
	}

	private void sendSmokePufftopool(SmokePuff pItem) {
		this.smokepufflist.remove(pItem);

		this.smokepuffpool.recyclePoolItem(pItem);

	}

	private void HadokenPosition() {

		if (HadokenReadyTimer != 0) {
			HadokenReadyTimer++;
			if (HadokenReadyTimer > 10) {
				HadokenreadyLeft = false;
				HadokenreadyRight = false;
				HadokenReadyTimer = 0;
			}
		}

		if (hadokenlist.size() != 0) {
			Hadoken sprite;

			for (int i = 0; i < hadokenlist.size(); i++) {
				sprite = hadokenlist.get(i);

				if (sprite.isHit == false) {
					int xpos = sprite.xpos + sprite.xmomentum;
					sprite.setPosition(xpos, sprite.ypos);
					sprite.xpos = xpos;
				}
				int diffX = (int) Math.abs(camerabox.getX() - sprite.xpos);
				int diffY = (int) Math.abs(camerabox.getY() - sprite.ypos);
				if ((sprite.isAnimationRunning() == false)
						&& (sprite.isHit == false)) {
					sprite.animate(hadokenfourFrame, hadokenfly, -1);
				}

				if ((sprite.isAnimationRunning() == false)
						&& (sprite.isHit == true)) {
					sendHadokentopool(sprite);
				}

				if ((diffX > halfwidth + 50) || (diffY > halfheight + 50)) {
					sendHadokentopool(sprite);
				}
			}
		}
	}

	private Shuriken getshurikenfrompool() {
		Shuriken temp = this.ShurikenPool.obtainPoolItem();
		temp.disabled = false;
		return temp;
	}

	private void sendshurikentopool(GameObject pItem) {

		pItem.disabled = true;
		for (int c = 0; c < enemyshurikenlist.size(); c++) {

			if (pItem == enemyshurikenlist.get(c)) {
				this.enemyshurikenlist.remove(pItem);
			}
		}

		this.shurikenlist.remove(pItem);

		this.ShurikenPool.recyclePoolItem((Shuriken) pItem);

	}

	void ArrowUpdate() {

		if(!ControlStick){
			if (stickman.arrows < 1) {
				this.BowAOSC.setVisible(false);
			} else {
				this.BowAOSC.setVisible(true);
			}
		}
		else
		{
			if (stickman.arrows < 1) {
		
				this.BowAOSC.setVisible(false);
			}
		}

		if (arrowlist.size() == 0) {
			return;
		}

		for (int a = 0; a < arrowlist.size(); a++) {

			Arrow arrow = arrowlist.get(a);
			Vector2 velocity = arrow.physicsBody.getLinearVelocity();
			float angleInRad = (float) (Math.atan2(velocity.y, velocity.x));
			arrow.physicsBody.setTransform(arrow.physicsBody.getWorldCenter(),
					angleInRad);
			float magnitude = (float) Math
					.abs(Math.sqrt((velocity.x * velocity.x)
							+ (velocity.y * velocity.y)));

			if (velocity.x > 0) {
				arrow.xdirection = 1;
			}
			if (velocity.x < 0) {
				arrow.xdirection = -1;
			}

			Vector2 position = arrow.physicsBody.getPosition();
			arrow.xpos = (int) (position.x * 32);
			arrow.ypos = (int) (position.y * 32);
			arrow.timer++;
			if ((arrow.timer > 500) || (magnitude < .5)) {
				arrow.timer = 0;
				sendarrowtopool(arrow);
			}

			for (int n = 0; n < ninjaslist.size(); n++) {
				updateHitCollision(ninjaslist.get(n), arrow);
			}
		}

	}

	void BowVisibility() {
		if (stickman.arrows <= 0) {
			this.BowAOSC.setVisible(false);
		}

	}

	void DrawArrow(float x, float y) {
		// float magnitude = (float) Math.abs(Math.sqrt((x*x)+(y*y)));
		
		if(stickman.triplearrows){DrawExtraArrows(x,y);}
		Arrow arrow = getarrowfrompool();
		double deltax = (double) x;
		double deltay = (double) y;
		arrow.thrower = stickman;
		// scene.attachChild(Target[t]);
		int arrowbuff = stickman.arrowbuff;
		if (arrowbuff == 0) {
			arrow.setColor(Color.RED);
		}
		if (arrowbuff == 1) {
			arrow.setColor(Color.GREEN);
		}
		if (arrowbuff == 2) {
			arrow.setColor(Color.BLACK);
		}
		if (arrowbuff == 3) {
			arrow.setColor(Color.WHITE);
		}
		if (arrowbuff == 4) {
			arrow.setColor(Color.BLUE);
		}

		// Physics.box2d.addObject(arrow.bodyRect, 1, "box", false, false);
		float angleInDegrees = (float) (Math.atan2(deltay, deltax) * 180 / Math.PI);
		float angleInRad = (float) (Math.atan2(deltay, deltax));
		// arrow.setRotation(angleInDegrees);

		final Vector2 velocity = new Vector2(x * 40, y * 40);
		// arrow.physicsBody.set
		arrow.physicsBody.setLinearVelocity(velocity);
		arrow.physicsBody.setTransform(arrow.physicsBody.getWorldCenter(),
				angleInRad);

		Vector2Pool.recycle(velocity);

	}
	
	
	void DrawExtraArrows(float x, float y) {
		// float magnitude = (float) Math.abs(Math.sqrt((x*x)+(y*y)));
		Arrow arrow1 = getarrowfrompool();
		Arrow arrow2 = getarrowfrompool();
		double deltax = (double) x;
		double deltay = (double) y;
		arrow1.thrower = stickman;
		arrow2.thrower = stickman;
		// scene.attachChild(Target[t]);
		int arrowbuff = stickman.arrowbuff;
		if (arrowbuff == 0) {
			arrow1.setColor(Color.RED);
			arrow2.setColor(Color.RED);
		}
		if (arrowbuff == 1) {
			arrow1.setColor(Color.GREEN);
			arrow2.setColor(Color.GREEN);
		}
		if (arrowbuff == 2) {
			arrow1.setColor(Color.BLACK);
			arrow2.setColor(Color.BLACK);
		}
		if (arrowbuff == 3) {
			arrow1.setColor(Color.WHITE);
			arrow2.setColor(Color.WHITE);
		}
		if (arrowbuff == 4) {
			arrow1.setColor(Color.BLUE);
			arrow2.setColor(Color.BLUE);
		}

		// Physics.box2d.addObject(arrow.bodyRect, 1, "box", false, false);
		float angleInDegrees = (float) (Math.atan2(deltay, deltax) * 180 / Math.PI);
		float angleInRad1 = (float) ((Math.atan2(deltay, deltax))*1.2);
		float angleInRad2 = (float) ((Math.atan2(deltay, deltax))*.8);
		// arrow.setRotation(angleInDegrees);

		final Vector2 velocity = new Vector2(x * 40, y * 40);
		// arrow.physicsBody.set
		arrow1.physicsBody.setLinearVelocity(velocity);
		arrow1.physicsBody.setTransform(arrow1.physicsBody.getWorldCenter(),
				angleInRad1);
		arrow2.physicsBody.setLinearVelocity(velocity);
		arrow2.physicsBody.setTransform(arrow2.physicsBody.getWorldCenter(),
				angleInRad2);

		Vector2Pool.recycle(velocity);

	}

	private Arrow getarrowfrompool() {
		Arrow temp = this.ArrowPool.obtainPoolItem();
		this.arrowlist.add(temp);
		temp.disabled = false;
		temp.timer = 0;
		// this.scene.attachChild(temp);
		temp.setPosition(stickman.xpos + 32, stickman.ypos + 20);

		Physics.box2d.addArrow(temp, 1, "arrow", false, true);
		return temp;
	}

	private void sendarrowtopool(Arrow arrow) {

		arrow.disabled = true;
		for (int c = 0; c < enemyarrowlist.size(); c++) {

			if (arrow == enemyarrowlist.get(c)) {
				this.enemyarrowlist.remove(arrow);
			}
		}
		arrow.detachSelf();
		arrow.setVisible(false);
		Physics.box2d.mPhysicsWorld.destroyBody(arrow.physicsBody);
		this.arrowlist.remove(arrow);
		arrow.timer = 0;
		this.ArrowPool.recyclePoolItem((Arrow) arrow);

	}

	private void throwShuriken(GameCharacter thrower, int direction) {
		if (thrower.isPlayer == false) {
			if (this.enemyshurikenlist.size() > this.gamelevel) {
				return;
			}
		}
		int xpos = (int) (thrower.xpos + (thrower.getHeight() / 2));
		int ypos = (int) (thrower.ypos + (thrower.getWidth() / 2));
		Shuriken sprite = getshurikenfrompool();
		this.shurikenlist.add(sprite);
		if (thrower.isPlayer == false) {
			this.enemyshurikenlist.add(sprite);
		}

		sprite.setVisible(true);
		sprite.detachSelf();
		this.scene.attachChild(sprite);

		sprite.thrower = thrower;
		sprite.hitPower = (int) (thrower.hitPower / 2);

		soundbank.playShurikenthrow(SFX);
		sprite.xpos = xpos;
		sprite.ypos = ypos;
		sprite.setPosition(sprite.xpos, sprite.ypos);
		if (thrower.isPlayer == false) {
			if (thrower.xdirection == 0) {
				sprite.xdirection = thrower.lastxdirection;
			} else {
				sprite.xdirection = thrower.xdirection;
			}
		} else if (thrower.isPlayer == true) {
			sprite.xdirection = direction;
		}

		if (sprite.xdirection != 0) {
			sprite.xmomentum = 15 * sprite.xdirection;
		}

		if (sprite.xdirection == -1) {

			sprite.animate(fourFrame, shurikenl, -1);
		} else if (sprite.xdirection == 1) {

			sprite.animate(fourFrame, shurikenr, -1);
		}
		return;
	}

	private void ShurikenPosition() {

		if (shurikenlist.size() != 0) {
			Shuriken sprite;
			Shuriken sprite2;

			for (int i = 0; i < shurikenlist.size(); i++) {
				sprite = shurikenlist.get(i);
				int xpos = sprite.xpos + sprite.xmomentum;
				// sprite.ypos = sprite.ypos - transY;
				int ypos = sprite.ypos;// + sprite.ymomentum;
				// Toast.makeText(this, "Showing angel at " + sprite.xpos + " "+
				// sprite.ypos, Toast.LENGTH_SHORT).setVisible(true);
				sprite.setPosition(xpos, ypos);
				sprite.xpos = xpos;
				sprite.ypos = ypos;

				int diffX = (int) Math.abs(camerabox.getX() - sprite.xpos);
				int diffY = (int) Math.abs(camerabox.getY() - sprite.ypos);

				if (shurikenlist.size() > 1) {
					for (int y = 0; y < shurikenlist.size(); y++) {
						sprite2 = shurikenlist.get(y);
						if (sprite.collidesWith(sprite2)) {
							if (((sprite.thrower.isPlayer == true) && (sprite2.thrower.isPlayer == false))
									|| ((sprite2.thrower.isPlayer == true) && (sprite.thrower.isPlayer == false)))

							{
								sendshurikentopool(sprite);
								sendshurikentopool(sprite2);
							}

						}
					}
				}
				if ((diffX > halfwidth + 50) || (diffY > halfheight + 50)) {
					sendshurikentopool(sprite);
				}
			}
		}
	}

	private void NinjaColor(GameCharacter sprite) {
		// if(sprite.getColor()!= Color.WHITE){return;}

		if (sprite.level == 1) {
			sprite.setColor(Color.WHITE);
		} else if (sprite.level == 2) {
			sprite.setColor(Color.WHITE);
		} else if (sprite.level == 3) {
			sprite.setColor(Color.WHITE);
		} else if (sprite.level == 4) {
			sprite.setColor(Color.YELLOW);
		} else if (sprite.level == 5) {
			sprite.setColor(Color.YELLOW);
		} else if (sprite.level == 6) {
			sprite.setColor(Color.YELLOW);
		} else if (sprite.level == 7) {
			sprite.setColor(Color.GREEN);
		} else if (sprite.level == 8) {
			sprite.setColor(Color.GREEN);
		} else if (sprite.level == 9) {
			sprite.setColor(Color.GREEN);
		} else if (sprite.level == 10) {
			sprite.setColor(Color.BLUE);
		} else if (sprite.level == 11) {
			sprite.setColor(Color.BLUE);
		} else if (sprite.level == 12) {
			sprite.setColor(Color.BLUE);
		} else if (sprite.level == 13) {
			sprite.setColor(Color.CYAN);
		} else if (sprite.level == 14) {
			sprite.setColor(Color.CYAN);
		} else if (sprite.level == 15) {
			sprite.setColor(Color.CYAN);
		} else if (sprite.level == 16) {
			sprite.setColor(Color.RED);
		} else if (sprite.level == 17) {
			sprite.setColor(Color.RED);
		} else if (sprite.level == 18) {
			sprite.setColor(Color.RED);
		} else if (sprite.level == 19) {
			sprite.setColor(Color.BLACK);
		} else if (sprite.level == 20) {
			sprite.setColor(Color.BLACK);
		} else if (sprite.level == 21) {
			sprite.setColor(Color.BLACK);
		} else if (sprite.level == 21) {
			sprite.setColor(Color.PINK);
		} else if (sprite.level >= 22) {
			sprite.setColor(Color.TRANSPARENT);
		}

	}

	private void RandomizeNinjaLocation(GameCharacter ninja) {

		if (SpawnBoss) {
			getbossfrompool();
			
		}

		if (ninja.RandomizeMe == true) {
			// Debugger("Gamelevel = "+gamelevel + " SpawnBoss = "+SpawnBoss);
			// if(FXSmokePuffInstance.isAnimationRunning()==false)
			// {

			if ((StopSpawningNinjas == false) || (ninja.isAlive == true)) 
			{
				if (ninja.isAlive == false) 
				{
					ninjasspawned++;
					// ninja.isBoss=false;
					ninja.revive();
				}
				else 
				{
					FXSmokePuff(ninja);
				}
				Physics.randomizeSprite(ninja, stickman);

				FXSmokePuff(ninja);
				NinjaColor(ninja);
				ninja.setVisible(true);

				return;
			}
			else 
			{
				sendninjatopool(ninja);

				if (BossSpawned)
				{
					if(ninja.isBoss) 
					{
						WaveComplete();
					}
				} 
				else 
				{
					SpawnBoss = true;
					Debugger("SpawnBoss set true by RunRandomizerQueue");

				}
			}

			if ((!AllAlone) && (!StopSpawningNinjas)) 
			{ //AllAlone is for practice vs boxes where no ninjas are necessary
				getninjafrompool();
			}

		}
	}

	private void FXOpenChest(PowerUp sprite) {
		// TODO Auto-generated method stub
		if(sprite.chestIsOpen){
			return;
		}
		sprite.chestIsOpen = true;
		sprite.chesttimer++;
		sprite.animate(twoFrame, this.chestopen, 1);
		
	
		
		
		
		
		
	}
	
	private void FXShowBloodSplatter() {
		
		Debug.d("Should show bloodsplatter #"+bloodlist.get(bloodcounter));
		if(bloodlist.get(bloodcounter)==1){
			blood1Sprite.setVisible(true);
		}
		if(bloodlist.get(bloodcounter)==2){
			blood2Sprite.setVisible(true);
		}
		if(bloodlist.get(bloodcounter)==3){
			blood3Sprite.setVisible(true);
		}
		if(bloodlist.get(bloodcounter)==4){
			blood4Sprite.setVisible(true);
		}
		if(bloodlist.get(bloodcounter)==5){
			blood5Sprite.setVisible(true);
		}
		if(bloodlist.get(bloodcounter)==6){
			blood6Sprite.setVisible(true);
		}
		if(bloodlist.get(bloodcounter)==7){
			blood7Sprite.setVisible(true);
		}
		bloodcounter++;
		
		if(bloodcounter>6){bloodcounter = 0;}
		bloodtimer = 1;
		
	}
	
	
	
	private void FXShowNewWeapon() {
		// TODO Auto-generated method stub
		
	}
	
	
	private void FXSmokePuff(GameObject target) {
		int xpos = (int) target.xpos;
		int ypos = (int) target.ypos;
		SmokePuff FXSmokePuffInstance = getSmokePufffrompool();

		FXSmokePuffInstance.target = target;
		FXSmokePuffInstance.xpos = xpos;
		FXSmokePuffInstance.ypos = ypos;
		if (target.isPlayer) {
			soundbank.playExplosion(SFX, SloMoON);
		}
		FXSmokePuffInstance.setPosition(xpos, ypos);
		FXSmokePuffInstance.animate(eightFrameVARIABLE, FX8count, 0);
		return;
	}

	private void FXbloodsplatter(Rectangle attackCollisionRect, int xdirection) {
		// TODO Auto-generated method stub
		
		
		
	}
	
	private void UpdateHealthbar(){
		this.healthbar.detachSelf();
		this.healthbar = new Rectangle(0,0,10,stickman.currenthealth, this.VBO);
		//this.healthbar.setPosition(this.screenwidth -healthbar.getWidth(), -10);
		if(stickman.currenthealth<= (int)(stickman.totalHealth/4)){
			this.healthbar.setColor(Color.RED);
		}
		else if((stickman.currenthealth<= (stickman.totalHealth/2))&&(stickman.currenthealth>=(int)(stickman.totalHealth/5))){
			this.healthbar.setColor(Color.YELLOW);
		}else{
		this.healthbar.setColor(Color.GREEN);
		}
		healthbar.setVisible(true);
		
		healthbar.setPosition(0,0);
		mHud.attachChild(healthbar);
		
	}
	
	private void bloodTimer()
	{
		
		if(bloodtimer == 0){return;}
		if(stickman.currenthealth>(stickman.totalHealth/8)){
			bloodtimer++;
			
			if(bloodtimer>20){
				blood1Sprite.setVisible(false);
				blood2Sprite.setVisible(false);
				blood3Sprite.setVisible(false);
				blood4Sprite.setVisible(false);
				blood5Sprite.setVisible(false);
				blood6Sprite.setVisible(false);
				blood7Sprite.setVisible(false);
				bloodtimer = 0;
			}
			
		}
	}

	private void FXDeathAnimation(GameCharacter defender, int specificAttack) {

		dropCorpse(defender);
	}

	private void FXAddCoin(int addamount) {
		ScoreAddText.detachSelf();
		String ScoreAddString = "+" + addamount;
		ScoreAddText = new Text(0, 0, mFont, ScoreAddString, 32, this.VBO);
		ScoreAddText.setPosition(5, 20);
		mHud.attachChild(ScoreAddText);
		ScoreAddText.setVisible(true);

	}

	private void FXShurikenGrab() {
		Shurikengrabsprite.setVisible(true);
		soundbank.playShurikengrab(SFX, SloMoON);
		Shurikengrabsprite.animate(eightFrameVARIABLE, FX8count, 0);
		return;

	}

	private void FXplayerLevelUp() {
		Levelupsprite.setVisible(true);
		soundbank.playLevelup(SFX);
		Levelupsprite.animate(eightFrame, FX8count, 0);
		return;
	}

	private void FXSlowMo() {
		SlowMosprite.setVisible(true);
		soundbank.playTimeDOWN(SFX);
		SlowMosprite.animate(eightFrameSLOW, FX8count, 0);
		return;
	}

	private void FXHealthUp() {
		Healthupsprite.setVisible(true);
		soundbank.playHealthup(SFX, SloMoON);
		Healthupsprite.animate(eightFrame, FX8count, 0);
		return;
	}

	private void FXNewNinja() {
		Newninjasprite.setVisible(true);
		soundbank.playNewNinja(SFX, SloMoON);
		Newninjasprite.animate(eightFrameVARIABLE, FX8count, 0);
		return;
	}

	private void FXSmokeBombPickup() {
		Smokebombpickupsprite.setVisible(true);
		soundbank.playSmokeBombPickup(SFX, SloMoON);
		Smokebombpickupsprite.animate(eightFrameVARIABLE, FX8count, 0);
		// textureswitch();
		return;
	}

	void FXGroundPound() {
		if (stickman.groundpounded == true) {
			stickman.groundpounded = false;
			soundbank.playGroundpound(SFX, SloMoON);
			FXGroundPoundInstance.disabled = false;
			FXGroundPoundInstance.setPosition((stickman.xpos - 32),
					stickman.ypos);
			FXGroundPoundInstance.xpos = stickman.xpos - 32;
			FXGroundPoundInstance.setVisible(true);
			FXGroundPoundInstance.animate(eightFrameVARIABLE, FX8count, 0);
		}
	}

	private void FXLevelComplete() {

		String LvlCmplt = "WAVE " + gamelevel + " COMPLETE";
		LevelCompleteText.detachSelf();
		LevelCompleteText = new Text(0, 0, mFont, LvlCmplt, 32, this.VBO);
		LevelCompleteText.setVisible(true);
		LevelCompleteText.setPosition(
				(int) (width - LevelCompleteText.getWidth()) / 2,
				(int) (height - LevelCompleteText.getHeight()) / 2);
		mHud.attachChild(LevelCompleteText);
		soundbank.playLevelComplete(SFX);

		return;
	}

	private void FXCleanup() {
		for (int x = 0; x < smokepufflist.size(); x++) {
			SmokePuff FXSmokePuffInstance = smokepufflist.get(x);
			if (FXSmokePuffInstance.isVisible() == true) {
				if (FXSmokePuffInstance.isAnimationRunning() == false) {
					sendSmokePufftopool(FXSmokePuffInstance);

				}
			}
		}
		if (Newninjasprite.isAnimationRunning() == false) {
			Newninjasprite.setVisible(false);
		}

		if (Smokebombpickupsprite.isAnimationRunning() == false) {
			Smokebombpickupsprite.setVisible(false);
		}
		if (SlowMosprite.isAnimationRunning() == false) {
			SlowMosprite.setVisible(false);
		}
		if (Shurikengrabsprite.isAnimationRunning() == false) {
			Shurikengrabsprite.setVisible(false);
		}

		if (Levelupsprite.isAnimationRunning() == false) {
			Levelupsprite.setVisible(false);
		}

		if (Healthupsprite.isAnimationRunning() == false) {
			Healthupsprite.setVisible(false);
		}

		if (FXGroundPoundInstance.isVisible() == true) {
			if (FXGroundPoundInstance.isAnimationRunning() == false) {
				FXGroundPoundInstance.disabled = true;
				FXGroundPoundInstance.setVisible(false);
			}
		}
	}

	public void visibilityCheck(GameCharacter sprite) {
		if (sprite.specialInprogress == 0) {
			if (sprite.isVisible() == false) {
				sprite.setVisible(true);
			}
		} else if (!sprite.specialattack[sprite.specialInprogress]
				.isAnimationRunning()) {
			animator.SpecialFinish(sprite, sprite.specialInprogress);
			if (sprite.isVisible() == false) {
				sprite.setVisible(true);
			}

		}

	}

	public void visibilityCheck(GameObject sprite) {
		if (sprite.isVisible() == false) {
			sprite.setVisible(true);
		}

	}

	public void SpecialFinish(GameCharacter sprite) {
		animator.SpecialFinish(sprite, sprite.specialInprogress);
	}

	public SurfaceGestureDetectorAdapter setupSGDA() {
		SurfaceGestureDetectorAdapter temporary = new SurfaceGestureDetectorAdapter(
				this, 50.0f) {

			@Override
			protected boolean onSwipeUp() {
				Debugger("onSwipeUp");
				stickman.swipeup = true;
				stickman.swipeY = (int) (-1.5 * stickman.jumplevel);
				stickman.swipeX = 0;
				return true;
			}

			@Override
			protected boolean onSwipeRight() {
				Debugger("onSwipeRight");
				if (stickman.shuriken > 0) {
					throwShuriken(stickman, right);
					stickman.shuriken--;
				}
				// stickman.swiperight = true;
				// stickman.swipeX = stickman.level;
				// stickman.swipeY = 0;
				return true;
			}

			@Override
			protected boolean onSwipeLeft() {
				Debugger("onSwipeLeft");
				if (stickman.shuriken > 0) {
					throwShuriken(stickman, left);
					stickman.shuriken--;
				}
				// stickman.swipeleft = true;
				// stickman.swipeX = -1 * stickman.level;
				// stickman.swipeY = 0;
				return true;
			}

			@Override
			protected boolean onSwipeDown() {
				Debugger("onSwipeDown");
				// stickman.swipedown = true;
				// stickman.isCrouching = true;
				// stickman.swipeY = 0;
				// stickman.swipeX = 0;

				if (stickman.smokebombs > 0) {
					// smokebombhud.setCurrentTileIndex(12);

					stickman.isHit = false;
					SmokeBomb(stickman);
					stickman.smokebombs--;
				}
				return true;
			}

			@Override
			protected boolean onSingleTap() {
				Debugger("onSingleTap");

				if ((stickman.isHit == false)
						&& (stickman.currentlyattacking == false)) {

					Debugger("onDoubleTap");

					if (stickman.isHit == false) {
						if (HadokenreadyLeft == true) {
							ClearMoveMemory();
							HadokenreadyLeft = false;
							HadokenReadyTimer = 0;
							Hadoken(left);

						}

						else if (HadokenreadyRight == true) {
							ClearMoveMemory();
							HadokenreadyRight = false;
							HadokenReadyTimer = 0;
							Hadoken(right);

						} else if (stickman.currentlyattacking == false) {
							stickman.startattack = true;
						}
					}
				}

				return true;
			}

			@Override
			protected boolean onDoubleTap() {
				Debugger("onDoubleTap");

				if (stickman.isHit == false) {
					if (HadokenreadyLeft == true) {
						ClearMoveMemory();
						HadokenreadyLeft = false;
						HadokenReadyTimer = 0;
						Hadoken(left);

					}

					else if (HadokenreadyRight == true) {
						ClearMoveMemory();
						HadokenreadyRight = false;
						HadokenReadyTimer = 0;
						Hadoken(right);

					} else if (stickman.currentlyattacking == false) {
						stickman.startattack = true;
					}
				}
				return true;
			}

			@Override
			public boolean onManagedTouchEvent(TouchEvent pSceneTouchEvent) {

				if (stickman != null) {

					if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
						stickman.touching = true;
						downX = pSceneTouchEvent.getX();
						downY = pSceneTouchEvent.getY();
						ChargeTimer = 1;

					}
					if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_UP) {
						upX = pSceneTouchEvent.getX();
						upY = pSceneTouchEvent.getY();
						
						diffX = upX - downX;
						diffY = upY - downY;

						int time = (int) (chargetime - (chargedelayratio * stickman.level));
						if (time < 10) {
							time = 10;
						}
						if (ChargeTimer > time) {
							stickman.frenzy = true;
							ChargeTimer = 0;
						}

						Debugger("swipe diffx diffy " + diffX + " " + diffY);

						// bow stuff here

						shurikenhud.setCurrentTileIndex(8);
						swordthrowhud.setCurrentTileIndex(9);
						smokebombhud.setCurrentTileIndex(0);

						/*
						 * if(stickman.isHit==false) { if(HadokenreadyLeft ==
						 * true) { HadokenreadyLeft = false; Hadoken(left);
						 * 
						 * }
						 * 
						 * else if(HadokenreadyRight == true) {
						 * HadokenreadyRight = false; Hadoken(right);
						 * 
						 * } else if(stickman.currentlyattacking == false) {
						 * stickman.startattack = true; } }
						 */
						stickman.touching = false;

						// set all buttons back to their default state here

					}
				}

				return super.onManagedTouchEvent(pSceneTouchEvent);
			}

		};
		return temporary;
	}

	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

		return false;
	}

	public void Debugger(String message){
		if(debugging){
			Debug.d(message);
		}
	}
	
	
}
