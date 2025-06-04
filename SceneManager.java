package com.smiths;


import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;



public class SceneManager {

  AllScenes currentScene;

  private BaseGameActivity activity;

  private Engine engine;

  private Camera camera;

  private BitmapTextureAtlas splashTA;

  private ITextureRegion splashTR;

  private Scene splashScene, gameScene, menuScene;



  public enum AllScenes {

      SPLASH, MENU, GAME

  }



  public SceneManager(BaseGameActivity act, Engine eng, Camera cam) {

      // TODO Auto-generated constructor stub

      this.activity = act;

      this.engine = eng;

      this.camera = cam;

  }



  public void loadSplashResources() {

      BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

      splashTA = new BitmapTextureAtlas(this.activity.getTextureManager(), 256, 256);

      splashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTA, this.activity, "splash.png", 0, 0);

      splashTA.load();

  }



  public void loadGameResource() {



  }

   

  public void loadMenuResource() {



  }



  public Scene createSplashScene() {

      splashScene = new Scene();

      splashScene.setBackground(new Background(1,1,1));

       

      Sprite icon = new Sprite(0, 0, splashTR, engine.getVertexBufferObjectManager());

      icon.setPosition((camera.getWidth()-icon.getWidth())/2, (camera.getHeight()-icon.getHeight())/2);

      splashScene.attachChild(icon);

      return splashScene;

  }

   

  public Scene createMenuScene() {

      menuScene = new Scene();

      menuScene.setBackground(new Background(0,0,0));

       

      Sprite icon = new Sprite(0, 0, splashTR, engine.getVertexBufferObjectManager());

      icon.setPosition((camera.getWidth()-icon.getWidth())/2, (camera.getHeight()-icon.getHeight())/2);

      menuScene.attachChild(icon);

      return menuScene;

       

  }



  public Scene createGameScene() {

      return null;

  }



  public AllScenes getCurrentScene() {

      return currentScene;

  }



  public void setCurrentScene(AllScenes currentScene) {

      this.currentScene = currentScene;

      switch (currentScene) {

      case SPLASH:

          break;

      case MENU:

          engine.setScene(menuScene);

          break;

      case GAME:

          engine.setScene(gameScene);

          break;



      default:

          break;

      }

  }



}
