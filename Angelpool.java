package com.smiths;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class Angelpool extends GenericPool<Angel>{
	public TiledTextureRegion mTextureRegion;
	public VertexBufferObjectManager VBO;
	
	public Angelpool(TiledTextureRegion angelTexture, VertexBufferObjectManager vbo) {
		  if (angelTexture == null) {
		   // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
		   throw new IllegalArgumentException("The texture region must not be NULL");
		  }
		  mTextureRegion = angelTexture;
		  VBO = vbo;
		 }


	/**
	 * Called when a Bullet is required but there isn't one in the pool
	 */
	@Override
	protected Angel onAllocatePoolItem() {
		// TODO Auto-generated method stub
		return new Angel(0, 0, mTextureRegion, VBO);
	}

	/**
	 * Called when a Bullet is sent to the pool
	 */
	 @Override
	 protected void onHandleRecycleItem(final Angel pangel) {
		 //pangel.setIgnoreUpdate(true);
		 pangel.collect();
	 }
	 
	 /**
	  * Called just before a Bullet is returned to the caller, this is where you write your initialize code
	  * i.e. set location, rotation, etc.
	  */
	 @Override
	 protected void onHandleObtainItem(final Angel pangel) {
	  pangel.reset();
	 }
}


