package com.smiths;


import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.Shape;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.graphics.Rect;

import com.smiths.GameCharacter;
//shuriken sub
public class Shuriken extends GameObject {
	public Rectangle bodyRect;
	public Shape collider;
	public float currentscale = 1;
	public float scalingfactor = (float) 0.3;
	public boolean collision = false;
	public int antigravity = 0;
	public boolean startattack = true;
	public int ypos;
	public int xpos;
	public int weapon;
	public int oldxpos;
	public int oldypos;
	public int xdirection = 0;
	public int ydirection = 0;
	public int velocity_y = 0;
	public int velocity_x = 0;
	public int difference = 0;
	public boolean topofjump = false;
	public int oldanim = 0;
	public boolean isActive = true;
	public int speed;
	public boolean justAppeared = true;
	public double acceleration=0;
	public int lastxdirection = 0;
	public double xmovement =0;
	public int deathtimer =0;

	
	

	public boolean currentlyjumping = false;
	public boolean rising = false;
	public boolean falling = false;
	public boolean isCrouching = false;
    public int currentweapon = 0;
	public int jumplevel = -20;
    public boolean attack = true;
	public boolean isHit = false;
	public boolean jumpAttempt = false;

	public boolean isAlive = true;
	public int deathTimer = 0;
	public int hitDirection = 0;
	public int hitTimer = 0;
	public boolean isHittable = true;
	public int level = 1;

	public int hitPower = 1;
	public int totalHealth=1;
	public int currenthealth=totalHealth;
	public int deathanim = 0;
	public boolean healthPowerUp = false;
	public GameCharacter assailant;
	public int score = 0;
	public boolean isSword = false;
	

	public boolean isPlayer = false;

	public int kills = 0;
	public int experience = 0;
	public int rotationcounter=0;
	public boolean locationlocked = false;
	public int ymomentum=0;
	public int xmomentum=0;
	
	public GameCharacter thrower;	
	
	public Shuriken( int x, int y,TiledTextureRegion texture, VertexBufferObjectManager OM) 
	{
		super(x, y, texture, OM);
	    this.isShuriken=true;
	 
	    bodyRect = new Rectangle(0,0, 20,10,OM);
		this.attachChild(bodyRect);
		this.self=bodyRect;
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
	
	public Rectangle AttackCollisionRect()
	{
		return bodyRect;
	}
		

	
}