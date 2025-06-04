package com.smiths;


import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.Shape;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.smiths.GameCharacter;

public class Sword extends GameObject {
	//wondering how much of this is duplicated and is already inheriting from GameObject
	public Rectangle bodyRect;
	public Shape collider;
	public float currentscale = 1;
	public float scalingfactor = (float) 0.3;
	public boolean collision = false;
	public int antigravity = 0;
	public int ypos;
	public int xpos;
	public int weapon = 1;
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
	
	public boolean attack = true;
	public boolean isHit = false;
	public boolean isAlive = true;
	public int hitDirection = 0;
	public int hitTimer = 0;
	public boolean isHittable = true;
	public int level = 1;
	public int hitPower = 50;
	public int totalHealth=1;
	public int currenthealth=totalHealth;
	public int deathanim = 0;
	public boolean healthPowerUp = false;
	
	public int specificAttack =100;
	public boolean isPlayer = false;
	public int kills = 0;
	public int experience = 0;
	public int rotationcounter=0;
	public boolean locationlocked = false;
	public int ymomentum=0;
	public int xmomentum=0;
	public boolean attacksuccessful=true;
	public GameCharacter thrower;	
	public Rectangle leftSide;
	public Rectangle rightSide;
	public boolean isDropped =false;
	public boolean startattack = true;
	public int damagemomentum;
	
	public Sword( int x, int y,TiledTextureRegion texture, VertexBufferObjectManager OM) 
	{
		super(x, y, texture, OM);
	    this.isSword = true;
	    this.leftSide = new Rectangle(0,30,32,4,OM);
	    this.rightSide = new Rectangle(32,30,32,4,OM);
	    leftSide.setVisible(false);
	    rightSide.setVisible(false);
	    this.attachChild(leftSide);
	    this.attachChild(rightSide);
	    bodyRect = new Rectangle(10,30, 44,4,OM);
		this.attachChild(bodyRect);
		self.setVisible(false);
		bodyRect.setVisible(false);
		this.setVisible(true);
	}

	
	@Override
	public Rectangle AttackCollisionRect()
	{
		return bodyRect;
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