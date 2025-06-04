package com.smiths;


import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public class SmokePuff extends GameObject {
	
	
	public SmokePuff( int x, int y,TiledTextureRegion texture, VertexBufferObjectManager OM) 
	{
		super(x, y, texture, OM);
	    
	 
	   this.setVisible(true);
	}
	public void collect()
	{

		 this.setVisible(false);
		 this.stopAnimation();
		 this.detachSelf();
	
	}
	
	public void reset()
	{
		this.setVisible(true);
	
	}
	
}