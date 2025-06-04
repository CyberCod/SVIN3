package com.smiths;


import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;

public class Hadokenpool extends GenericPool<Hadoken> {

	 private TiledTextureRegion mTextureRegion;
	private VertexBufferObjectManager VBO;
	 
	 public Hadokenpool(TiledTextureRegion HadokenTexture, VertexBufferObjectManager vbo) {
	  if (HadokenTexture == null) {
	   // Need to be able to create a Sprite so the Pool needs to have a TextureRegion
	   throw new IllegalArgumentException("The texture region must not be NULL");
	  }
	  mTextureRegion = HadokenTexture;
	  VBO = vbo;
	 }
	
	
	
	@Override
	protected Hadoken onAllocatePoolItem() {
		// TODO Auto-generated method stub
		
		return new Hadoken(0, 0, mTextureRegion, VBO);
		
	}

	
	 @Override
	 protected void onHandleRecycleItem(final Hadoken currentHadoken) {
		 currentHadoken.collect();
	 }
	 
	
	 @Override
	 protected void onHandleObtainItem(final Hadoken currentHadoken) {
		 currentHadoken.reset();
	 }
}
