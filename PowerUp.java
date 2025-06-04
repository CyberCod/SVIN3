package com.smiths;

//power up sub
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public class PowerUp extends GameObject {
	
	public Rectangle bodyRect;
	
	
    public int powerupType;
	
    public int isBlank= 0;
	public int isExtraNinja= 1;
	public int isHealth=2;
	public int isShurikensPowerUp = 3;
	public int isSmokebomb = 4;
	public int isSlowmo = 5;
	public int isSuperHealth= 6;
	public int isHadokenBuff = 7;
	public int isGoldCoin= 8;
	public int isLevelUp = 9;

	public int hit = 0;


	public int cycletimer = 0;


	public boolean disabled = true;


	public boolean chestitemdropped = false;


	public int chesttimer =0;


	public boolean chestIsOpen = false;
	
	
	
	public PowerUp( int x, int y,TiledTextureRegion texture, VertexBufferObjectManager OM) 
	{
		super(x, y, texture, OM);
		bodyRect = new Rectangle(10, 10, 44,44,OM);
		this.attachChild(bodyRect);
		bodyRect.setVisible(false);
		this.setVisible(true);
		this.powerupType=0;
		
	}

	
	
	
	public int Offset() {
		
		int Yoffset = ((int)this.getHeightScaled()-1);
		
		return Yoffset;
		
	}
	
	public void reset()
	{
	this.setVisible(true);
	this.powerupType=0;
	this.timer=0;
	this.hit = 0;
	
	}
	
	
	
	
	public void collect()
	{
	this.setVisible(false);
	this.powerupType=0;
	this.timer=0;
	this.hit = 0;
	this.detachSelf();
	}
	
	
	
	
	
	
	
}