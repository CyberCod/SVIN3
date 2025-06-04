package com.smiths;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class Corpsepool extends GenericPool<Corpse> {
	
	private TiledTextureRegion mTextureRegion;
	private VertexBufferObjectManager VBO;
	
	public Corpsepool(TiledTextureRegion corpseTexture, VertexBufferObjectManager vbo) {
		  if (corpseTexture == null) {
		   // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
		   throw new IllegalArgumentException("The texture region must not be NULL");
		  }
		  mTextureRegion = corpseTexture;
		  VBO = vbo;
		 }

	@Override
	protected Corpse onAllocatePoolItem() {
		// TODO Auto-generated method stub
		return new Corpse(0, 0, mTextureRegion, VBO);
	}
	
	/**
	 * Called when a Bullet is sent to the pool
	 */
	 @Override
	 protected void onHandleRecycleItem(final Corpse stiff) {
		 //stiff.setIgnoreUpdate(true);
		stiff.collect();
		}

	 @Override
	 protected void onHandleObtainItem(final Corpse stiff) {
	  stiff.reset();
	 }
	 
	 
	 
	 
}
