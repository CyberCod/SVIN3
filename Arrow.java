package com.smiths;


import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.Shape;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.graphics.Rect;

import com.smiths.GameCharacter;
//Arrow sub
public class Arrow extends GameObject {
	public Rectangle bodyRect;
	public Rectangle attackRect;
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
	public int hits;	
	
	public Arrow( int x, int y,TiledTextureRegion texture, VertexBufferObjectManager OM) 
	{
		super(x, y, texture, OM);
	    
	 
	    bodyRect = new Rectangle(0,0, 32,3,OM);
	    attackRect = new Rectangle(0,0, 5,3,OM);
		this.attachChild(bodyRect);
		this.attachChild(attackRect);
		this.self=bodyRect;
		self.setVisible(true);
		bodyRect.setColor(Color.BLACK);
		
		bodyRect.setVisible(true);
		this.setVisible(true);
	}

	
	


	public void collect()
	{
		this.disabled = true;
		 this.setVisible(false);
		 this.stopAnimation();
		 this.detachSelf();
		 this.isHit = false;
		 this.hits = 0;
	
	}
	
	public void reset()
	{
		this.disabled = false;
		
		this.setVisible(true);
		this.isHit = false;
		this.hits = 0;

	}
	
	public Rectangle AttackCollisionRect()
	{
		return attackRect;
	}
		

	
}