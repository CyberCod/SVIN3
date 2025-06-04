package com.smiths;


import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class Swordpool extends GenericPool<Sword> {

	 private TiledTextureRegion mTextureRegion;
	private VertexBufferObjectManager VBO;
	 
	 public Swordpool(TiledTextureRegion SwordTexture, VertexBufferObjectManager vbo) {
	  if (SwordTexture == null) {
	   // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
	   throw new IllegalArgumentException("The texture region must not be NULL");
	  }
	  mTextureRegion = SwordTexture;
	  VBO = vbo;
	 }
	
	
	
	@Override
	protected Sword onAllocatePoolItem() {
		// TODO Auto-generated method stub
		
		return new Sword(0, 0, mTextureRegion, VBO);
		
	}

	
	 @Override
	 protected void onHandleRecycleItem(final Sword currentSword) {
		 currentSword.collect();
	 }
	 
	
	 @Override
	 protected void onHandleObtainItem(final Sword currentSword) {
		 currentSword.reset();
	 }
}
