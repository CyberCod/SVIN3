package com.smiths;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class Ninjapool extends GenericPool<GameCharacter> {
	
	private TiledTextureRegion mTextureRegion;
	private VertexBufferObjectManager VBO;
	public Animator animator;
	
	public Ninjapool(TiledTextureRegion ninjaTexture, VertexBufferObjectManager vbo, Animator anima) {
		  if (ninjaTexture == null) {
		   // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
		   throw new IllegalArgumentException("The texture region must not be NULL");
		  }
		  mTextureRegion = ninjaTexture;
		  VBO = vbo;
		  animator = anima;
		 }

	@Override
	protected GameCharacter onAllocatePoolItem() {
		// TODO Auto-generated method stub
		return new GameCharacter(0, 0, mTextureRegion, VBO, animator);
	}
	
	/**
	 * Called when a Bullet is sent to the pool
	 */
	 @Override
	 protected void onHandleRecycleItem(final GameCharacter ninja) {
		 //ninja.setIgnoreUpdate(true);
		ninja.collect();
		}

	 @Override
	 protected void onHandleObtainItem(final GameCharacter ninja) {
	  ninja.resetCharacter();
	 }
	 
	 
	 
	 
}
