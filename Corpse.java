package com.smiths;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
//corpse sub
public class Corpse extends GameObject {
	public Rectangle bodyRect;
	public boolean hasSword =false;
	public boolean startattack = false;
	public double hitvelocity_y;
	public int hitvelocity_x;
	public int rotationspeed;
	
	public Corpse( int x, int y,TiledTextureRegion texture, VertexBufferObjectManager OM)
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
		this.timer=0;
		 this.setVisible(false);
		 this.stopAnimation();
		 this.detachSelf();
	}
	
	public void reset()
	{
		this.setVisible(true);
		this.timer= 0;
	}
	
}