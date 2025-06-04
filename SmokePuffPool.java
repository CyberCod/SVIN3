package com.smiths;


import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class SmokePuffPool extends GenericPool<SmokePuff> {

	 private TiledTextureRegion mTextureRegion;
	private VertexBufferObjectManager VBO;
	 
	 public SmokePuffPool(TiledTextureRegion SmokePuffTexture, VertexBufferObjectManager vbo) {
	  if (SmokePuffTexture == null) {
	   // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
	   throw new IllegalArgumentException("The texture region must not be NULL");
	  }
	  mTextureRegion = SmokePuffTexture;
	  VBO = vbo;
	 }
	
	
	
	@Override
	protected SmokePuff onAllocatePoolItem() {
		// TODO Auto-generated method stub
		
		return new SmokePuff(0, 0, mTextureRegion, VBO);
		
	}

	
	 @Override
	 protected void onHandleRecycleItem(final SmokePuff currentSmokePuff) {
		 currentSmokePuff.collect();
	 }
	 
	
	 @Override
	 protected void onHandleObtainItem(final SmokePuff currentSmokePuff) {
		 currentSmokePuff.reset();
	 }
}
