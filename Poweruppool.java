package com.smiths;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class Poweruppool extends GenericPool<PowerUp> {
	
	private TiledTextureRegion mTextureRegion;
	private VertexBufferObjectManager VBO;
	
	public Poweruppool(TiledTextureRegion powTexture, VertexBufferObjectManager vbo) {
		  if (powTexture == null) {
		   // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
		   throw new IllegalArgumentException("The texture region must not be NULL");
		  }
		  mTextureRegion = powTexture;
		  VBO = vbo;
		 }

	@Override
	protected PowerUp onAllocatePoolItem() {
		// TODO Auto-generated method stub
		return new PowerUp(0, 0, mTextureRegion, VBO);
	}
	
	/**
	 * Called when a Bullet is sent to the pool
	 */
	 @Override
	 protected void onHandleRecycleItem(final PowerUp pow) {
		 //pow.setIgnoreUpdate(true);
		pow.collect();
		}

	 @Override
	 protected void onHandleObtainItem(final PowerUp pow) {
	  pow.reset();
	 }
	 
	 
	 
	 
}
