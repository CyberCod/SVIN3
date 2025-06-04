package com.smiths;


import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class Shurikenpool extends GenericPool<Shuriken> {

	 private TiledTextureRegion mTextureRegion;
	private VertexBufferObjectManager VBO;
	 
	 public Shurikenpool(TiledTextureRegion shurikenTexture, VertexBufferObjectManager vbo) {
	  if (shurikenTexture == null) {
	   // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
	   throw new IllegalArgumentException("The texture region must not be NULL");
	  }
	  mTextureRegion = shurikenTexture;
	  VBO = vbo;
	 }
	
	
	
	@Override
	protected Shuriken onAllocatePoolItem() {
		// TODO Auto-generated method stub
		
		return new Shuriken(0, 0, mTextureRegion, VBO);
		
	}

	
	 @Override
	 protected void onHandleRecycleItem(final Shuriken currentshuriken) {
		 currentshuriken.collect();
	 }
	 
	
	 @Override
	 protected void onHandleObtainItem(final Shuriken currentshuriken) {
		 currentshuriken.reset();
	 }
}
