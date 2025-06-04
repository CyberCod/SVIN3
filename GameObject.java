package com.smiths;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;


//root game object class
public class GameObject extends AnimatedSprite {
	
	// public boolean states of being  
	
	public boolean isPlayer = false; 
	public boolean isShuriken;
	public boolean collision = false;
	public boolean rising = false;                                            
	public boolean falling = false;                                           
	public boolean startattack = false;
	public boolean isCrouching = false;
	//Position and forces
	public float ypos;                                                        
	public float xpos;                                                        
	public float oldxpos;                                              
	public float oldypos;                                              
	public double velocity_y = 0;                                                
	public int velocity_x = 0;                                                
	public float jumplevel = -20;                                               
	public int difference = 0;                                         
	public int xdirection = 0;                                         
	public int ydirection = 0;                                         
	public int rotationcounter =0;                                     
	public int hitDirection = 0;                                        //	the direction character was hit
	public double antigravity = 1;  //	this gets set equal to gravity if character is standing on something
	public double xmovement =0;              
	public GameObject assailant;  
	public int deathanim = 0;  
	public int oldanim = 0;   
	public int newanim= 0;//
	public int timer = 0;    
	public int level = 1;             
	public int hitPower = 10 + level;  
	  
	public float currentscale = 1;              //	set at calling time or defaults to 1
	public float scalingfactor = ( float) 0.3;  
	public boolean isHit = false;           
	public boolean currentlyjumping = false;      
	public boolean topofjump = false; 
	public Rectangle self = new Rectangle(0, 32,64,32,this.getVertexBufferObjectManager());

      
	
	public boolean isAlive = true;  
	public int deathTimer = 0;
	public int totalHealth=(level*2)+3;                                 //
	public int currenthealth=totalHealth;  
	public Rectangle feetRect = new Rectangle(28,60,8,24,this.getVertexBufferObjectManager());
	public Shape collider;	
	public int deaths = 0;
	public GameObject target;
	public int lastxdirection;
	public int xmomentum;  
	public boolean isSword=false;
	public boolean isGrounded;
	public boolean isThrown;
	public int specificAttack =0;
	public int headshots=0;
	public boolean isBoss = false;
	public Body physicsBody;
	public Vector2 movementVector;
	public Rectangle bodyRect;
	public boolean box2d = false;
	public boolean disabled = false;
	
	
	
	
	public GameObject( int x, int y,TiledTextureRegion texture, VertexBufferObjectManager OM) 
	{
	super(x, y, texture, OM);
	this.attachChild(self);
	this.attachChild(feetRect);
	self.setVisible(false);
	feetRect.setVisible(false);
	this.setVisible(true);
}
	
	
	
	
public int Offset() {
		
		int Yoffset = ((int)this.getHeight()-1);
		
		return Yoffset;
		
	}
	
	
public Rectangle AttackCollisionRect()
{
	return self;
}




public void setBox2DPhysics( boolean box2dON) {
	// TODO Auto-generated method stub
	
}
	
	
	
}