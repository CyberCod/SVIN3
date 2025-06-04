package com.smiths;


//angel sub
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;



public class Angel extends GameObject {
	
	public Rectangle bodyRect;
	
	public Angel( int x, int y,TiledTextureRegion texture, VertexBufferObjectManager OM)//, float scale) 
	{
	super( x, y,texture, OM);
	bodyRect = new Rectangle(20, 16, 16,48,OM);
	this.attachChild(bodyRect);
	self.setVisible(false);
	bodyRect.setVisible(false);
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