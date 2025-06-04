package com.smiths;


import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class Arrowpool extends GenericPool<Arrow> {

	 private TiledTextureRegion mTextureRegion;
	private VertexBufferObjectManager VBO;
	 
	 public Arrowpool(TiledTextureRegion ArrowTexture, VertexBufferObjectManager vbo) {
	  if (ArrowTexture == null) {
	   // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
	   throw new IllegalArgumentException("The texture region must not be NULL");
	  }
	  mTextureRegion = ArrowTexture;
	  VBO = vbo;
	 }
	
	
	
	@Override
	protected Arrow onAllocatePoolItem() {
		// TODO Auto-generated method stub
		
		return new Arrow(0, 0, mTextureRegion, VBO);
		
	}

	
	 @Override
	 protected void onHandleRecycleItem(final Arrow currentArrow) {
		 currentArrow.collect();
	 }
	 
	
	 @Override
	 protected void onHandleObtainItem(final Arrow currentArrow) {
		 currentArrow.reset();
	 }
}
