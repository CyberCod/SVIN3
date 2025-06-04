package com.smiths;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public class Store
{
	
	
	Inventory inventory;
	Engine engine;
	private Sprite Backgroundsprite;
	private VertexBufferObjectManager VBO;
	float scrw;
	float scrh;
	private SpriteBackground bg;
	private ITextureRegion StoreTex;
	private Scene storeScene;
	
	
	public Store(Inventory i, Engine e, ITextureRegion storetex, float screenwidth, float screenheight, VertexBufferObjectManager vbo){
		inventory = i;
		engine = e;
		StoreTex = storetex;
		scrw=screenwidth;
		scrh=screenheight;
		VBO = vbo;
	}
	
	
	
	
	
	
	
	public void LoadStore(){
		 storeScene = new Scene();
		// scene.setChildScene(storeScene)
		this.engine.setScene(storeScene);
		this.Backgroundsprite = new Sprite(0, 0, scrw,
				scrh, this.StoreTex, this.VBO);
		this.bg = new SpriteBackground(Backgroundsprite);
		storeScene.setBackground(this.bg);
		
		
	}
	
	
	
	
	
	
	
}