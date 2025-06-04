package com.smiths;

import java.util.ArrayList;


import org.andengine.engine.Engine;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.util.debug.Debug;




import android.content.SharedPreferences;





public class NewPhysics
{
//simple physics class
	private  boolean icelevel;
	private  double friction;
	
	SharedPreferences preferences;// = PreferenceManager.getDefaultSharedPreferences(this);
    
    SharedPreferences.Editor settings_editor;// = preferences.edit();

	
	public boolean RandomizerIsOpen =true;
	boolean ceilingcollision = false;
	
	GameCharacter sprite;
	

	int accelX;
	int accelY;
	
	public float Yoffset = 0;
	
	float velocity_y;
	float velocity_x;
	int jumplevel = -15;
	boolean jumptrigger;
	 int gravity = 2;
	boolean collision = false;
	boolean falling;
	boolean rising;
	boolean currentlyjumping;
	 boolean calibrateaccellerometers;
	 ArrayList<Rectangle> horizontalshapearray;
	 ArrayList<Rectangle> verticalshapearray;
	int feet = 0;
	int body = 1;
	int head = 2;
	private float height;
	private float width;
	final int up = -1;
	final int down = 1;
	final int left = -1;
	final int right = 1;
	int xrange;
	int yrange;
	int ninjacount;
	ArrayList<Rectangle> collidables;
	private int AIxdistance;
	//ArrayList<GameCharacter> ninjas = new ArrayList<GameCharacter>();
	private int AIydistance;
	private double currentvelocity_y;
	public Box2D box2d;

	
	
	public NewPhysics(float phoneheight, float phonewidth, ArrayList<Rectangle> horizontals, ArrayList<Rectangle> verticals, SharedPreferences prefs,  Engine engine, Scene scene)
	{
		this.horizontalshapearray = horizontals;
		this.verticalshapearray=verticals;
		this.box2d = new Box2D(horizontals, verticals,  engine, scene);
		this.height = phoneheight;
		this.width = phonewidth;
		this.preferences = prefs;
		settings_editor = prefs.edit();
		if(prefs.getBoolean("calibrated",false)==false)
		{
			calibrateaccellerometers = true;
		}
		else
		{
			calibrateaccellerometers = false;
			Yoffset = prefs.getFloat("Yoffset", 0.0f);
		}
		
		
	}
	
	
	
	
	
	
	
	void PlaceSprite(GameObject object)
	{
		if((object.physicsBody!= null)&&(object.box2d == true))
		{
			box2d.TransformToBox2d(object);
		}
		else
		{
			object.bodyRect.setPosition(object.xpos, object.ypos);
		}
	}
	
	void MoveSprite(GameObject sprite)
	{
		if((sprite.physicsBody!= null)&&(sprite.box2d == true))
		{
		
			sprite.physicsBody.setLinearVelocity(sprite.movementVector);
		}
		else
		{
			sprite.bodyRect.setPosition(sprite.xpos, sprite.ypos);
		}
	}
	
	

	
	void SwitchPhysics(GameObject sprite, boolean box2dON)
	{
		sprite.setBox2DPhysics(box2dON);	
		if(box2dON == true)
		{
			box2d.TransformToBox2d(sprite);
		}
		else
		{
			box2d.TransformFromBox2d(sprite);
			
		}
		
		
	}

	
	
	

	private void TranslateFromPhysics(GameCharacter sprite) {
		// TODO Auto-generated method stub
		box2d.TransformFromBox2d((GameObject)sprite);
		
	}


	private void TranslateToPhysics(GameCharacter sprite) {
		// TODO Auto-generated method stub
		box2d.TransformToBox2d((GameObject)sprite);
	}

	
	
	public  void updatePosition(GameCharacter currentsprite, GameObject fXbox) {
		
    	updateSpritePosition(currentsprite, 0, horizontalshapearray, fXbox);
    	
	}
	
	
	

	
	public  void updateSpritePosition(GameCharacter player, int vehicle, ArrayList<Rectangle> horizontalshapearray, GameObject fXbox ) 
	{
	    if(vehicle ==0){
	    	
	        player.oldxpos = player.xpos;
	        player.oldypos = player.ypos;
	        
	        if(player.xdirection != 0){player.lastxdirection = player.xdirection;}
	        double speedmultiplier = 0;
	  
	        
	        
	        if(player.lastxdirection!=player.xdirection)
	        {
	        	player.uninterruptible=false;
	        }
	        
	        int currentxvector = (int)player.xmovement; 
	        if(currentxvector < 0){currentxvector = currentxvector * -1;}
	        if(currentxvector != 0)//changed this from transx
	        {
	        	speedmultiplier = currentxvector * .10;
	        }
	        else
	        {
	        	speedmultiplier = 1;
	        }
	        
	        
	            if(player.xdirection == 0)   
	            	{
	            		player.acceleration = 0; 
	            	}
	            else if((currentxvector > 0)&&(player.acceleration < 0))  // if trying to go right and acceleration is left
	            	{
	            		player.acceleration=0;
	            	}
	            else if((currentxvector < 0)&&(player.acceleration > 0)) // if trying to go left and acceleration is right
	            	{
	            		player.acceleration=0;
	            	}
	            else if(player.lastxdirection == player.xdirection)  //continuing movement
		    		{
		        		if((Math.abs(player.acceleration))<(player.speed*3))  //if accleration isn't maxxed at 3x speed
		        		{
		        			
							player.acceleration = player.acceleration + (player.xdirection*speedmultiplier); //accleration increases in previous direction
		        		}
		        		else
		        		{
		        			player.acceleration = player.xdirection * player.speed*2;
		        		}
		        	}
	        	else
		        	{
		        		player.acceleration = 0;
		        	}    
		   
	     
	        
	        int Yvector = 0;
	        if(player.jumpAttempt == true){Yvector = 1;}
	        if(player.isCrouching == true){Yvector = -1;}
	        
	        if(floorCollision(player, horizontalshapearray) == true)			
			{
	        	if((player.falling ==true)||(player.topofjump==true))
	        	{
	        		player.stopAnimation();
	        		player.uninterruptible = false;
	        		player.currentlyattacking=false;
	        		player.currentlyjumping=false;
	        		player.rising = false;
	        	
	        	//Debug.d("Collision from updateSpritePos");
				player.collision = true; 
				player.falling = false;
				player.antigravity = gravity;
	        	}
			}
	        else
			{
				player.collision = false;
				player.antigravity = 0;
			}
	        
	        
	        float newypos = (float) (player.ypos + jumploop(player,Yvector, horizontalshapearray));
	        if((player.currentlyattacking == true)&&(player.currentlyjumping==true))
	        {
	        	jumpRotate(player);
	        }
	        
	        if(((player.isHit==false)&&(player.currentlyattacking==false))||(player.collision==true))
	        {
	        	player.setRotation(0);
	        }
	        
	        int newxpos;
	        if(player.isHit==true)
	        {
	        	newxpos = (int) (player.xpos + player.hitvelocity_x);
	        	if(player.collision==true)
	        	{
		        	slideFriction(player);
		        }
	        	if(wallCollision(player,verticalshapearray)!= 0)
	        	{
	        		player.hitvelocity_x = player.hitvelocity_x*-1;
	        	}
	        	
	        	
	        	
	        }
	        else
	        {
	        	newxpos = (int) (player.xpos + (int)player.xmovement);
	        }
	        
	        player.xpos = newxpos;
	        
			player.ypos = newypos;
			
			int wall = wallCollision(player, verticalshapearray);
		        if(wall!=0)
		        {
		        	player.xpos = wall;
		        }
	        
		        
		    
	 
	        
			
	
	        //player.YDIRECTION GETS SET HERE
	        if(player.oldypos > newypos)
		        {
		        	player.ydirection = -1;
		        } //UP
	        else if(player.oldypos < newypos)
		        {
		        	player.ydirection = 1;
		        } //DOWN
	        else if(player.oldypos == newypos)
		        {
		        	player.ydirection = 0;
		        } //not moving
	    
	    
	      player.setPosition(player.xpos, player.ypos);
	        
	        fXbox.setPosition(player);
	    }
	       
    }
	
	
	
	
	
	

	public  boolean ceilingCollision(GameCharacter sprite, ArrayList<Rectangle> horizontalshapearray )
	{
		boolean iscollided = false;
		
		for (int i=0; i<horizontalshapearray.size(); i++)
		{
			
			if(sprite.bodyRect.collidesWith(horizontalshapearray.get(i))== true)
			{
				//sprite.collider = horizontalshapearray.get(i);
				iscollided = true;
				
				return iscollided;
			}
			else
			{
				iscollided = false;
				
			}
		}
		return iscollided;
		} 
	
	
	
	public  boolean floorCollision(GameCharacter sprite, ArrayList<Rectangle> horizontalshapearray )
	{
		boolean iscollided = false;
		
			for (int i=0; i<horizontalshapearray.size(); i++)
			{
				
				if(sprite.feetRect.collidesWith(horizontalshapearray.get(i))== true)
				{
					sprite.collider = horizontalshapearray.get(i);
					sprite.ypos = horizontalshapearray.get(i).getY()-sprite.Offset();
					sprite.setRotation(0);
					//Debug.d("collision in floor method");
					iscollided = true;
					return iscollided;
				}
				
			}
	return iscollided;
	}
	
	
	
	public  boolean floorCollision(GameObject sprite, ArrayList<Rectangle> horizontalshapearray )
	{
		boolean iscollided = false;
		
		for (int i=0; i<horizontalshapearray.size(); i++)
		{
			
			if(sprite.feetRect.collidesWith(horizontalshapearray.get(i))== true)
			{
				sprite.collider = horizontalshapearray.get(i);
				sprite.ypos = horizontalshapearray.get(i).getY()-sprite.Offset();
				sprite.setRotation(0);
				//Debug.d("collided in floor method GO");
				iscollided = true;
				return iscollided;
			}
			
		}
		return iscollided;
		}
	
	
	
	
	
	
	

	 double jumploop(GameCharacter sprite, int Ytrigger,ArrayList<Rectangle> horizontalshapearray)
	{  
		double difference;
		double localypos = sprite.ypos;
		int jumpamount = (int) sprite.jumplevel;
		
		
		
		
		sprite.antigravity = 0;
		
				
		if(floorCollision(sprite, horizontalshapearray) == true)			
			{
				//Debug.d("COLLISION! in jumploop");
				sprite.collision = true; 
				sprite.falling = false;
				sprite.antigravity = gravity;
			}
		else
			{
				sprite.collision = false;
				sprite.antigravity = 0;
			}
		
		
		
		
			if(sprite.velocity_y > 0)    // Decides based on velocity_y whether the sprite is in a state falling or rising,
			{
				sprite.falling = true;
				
				sprite.rising = false;
				
				if(sprite.isHit == false){sprite.currentlyjumping = true;}
				
			}
			else if(sprite.velocity_y < 0)
			{
				sprite.rising = true;
				sprite.falling = false;
				
				if(sprite.isHit == false){sprite.currentlyjumping = true;}
				
			}
			else if(sprite.velocity_y == 0)
			{
				sprite.rising = false;
				sprite.falling = false;
			}
		
		
			
		
			
			
			
			
			
			if(sprite.collision == true)
			{
				if(sprite.velocity_y == 0)
				{
					
					sprite.currentlyjumping = false;
					
					
				}
			}
		
			if(sprite.rising == true)
			{
				if(sprite.velocity_y == 0)
				{
					sprite.topofjump = true;
					if(sprite.touching==false)
					{
					sprite.stopAnimation();
					sprite.uninterruptible = false;
					sprite.setRotation(0);
					}
				}
			}
			else
			{
				sprite.topofjump = false;
			}
		
			if(sprite.falling == true)  // If it hits something while falling, it stops downward velocity.  Bouncy stuff goes in here later
			{
				if(sprite.collision == true)
				{
					if(sprite.bouncy == true)
					{
						sprite.velocity_y = (int) -(sprite.velocity_y*.8);
					}
					else
					{
					sprite.velocity_y = 0;
					sprite.stopAnimation();
					sprite.AnimateMe = true;
					sprite.uninterruptible = false;
					localypos = sprite.collider.getY()-sprite.Offset();
					sprite.velocity_y = 0;
					sprite.falling = false;
					
					
					}
				}
			}
		
		
		if(ceilingcollision == true)
		{
			if(sprite.rising == true)
			{	
				if(ceilingCollision(sprite, horizontalshapearray) == true)		
				{sprite.ypos = sprite.ypos +5;sprite.currentlyjumping=false;sprite.velocity_y=0;
				sprite.stopAnimation();
					sprite.uninterruptible = false;}
				
				// put some top collision here later
			}
		}
		
		if(sprite.isAlive == true)
		{
			if(sprite.isHit == false)
			{
				if(sprite.jumpAttempt == true)   //  starts jump if not already jumping, is standing on ground, and if Ytrigger is true.
				{
					if(sprite.collision == true)
					{
						if(sprite.rising == false) 
						{
							sprite.stopAnimation();
					        sprite.uninterruptible = false;
							sprite.velocity_y = jumpamount;
						
						}
					}
				}
			}
		}
		
		
		if((sprite.collision == true) && (sprite.falling == true)) // find floor and level sprite to it
		{
			//hold for a minute
		}
	
		if(sprite.hitvelocity_y != 0)
		{
			currentvelocity_y = sprite.hitvelocity_y;
			sprite.hitvelocity_y = 0;
		}
		else
		{
		currentvelocity_y = sprite.velocity_y + gravity - sprite.antigravity;
		}
		if(currentvelocity_y > 35){currentvelocity_y = 35;}
		sprite.velocity_y = currentvelocity_y;
		localypos = localypos + sprite.velocity_y;
		difference =  localypos - sprite.ypos;
		
		return difference;
				
	
}
	
	
	
	
	 

	 void Yaccelcheck(GameCharacter sprite, float accellerometerSpeedY, boolean zflip)  // THIS STAYS IN MAIN UNTIL WE MAKE A CONTROL CLASS
	{
		
		float accy;
		if(calibrateaccellerometers == true)
		{
			Yoffset =  accellerometerSpeedY;
			settings_editor.putFloat("Yoffset", Yoffset);
			settings_editor.putBoolean("calibrated",true);
			settings_editor.putBoolean("firstrun", false);
			settings_editor.commit();
			calibrateaccellerometers = false;
		} 
		
		if(zflip == true)
		{
			accy = 20 - accellerometerSpeedY - Yoffset;
		}
		else
		{
			accy = accellerometerSpeedY - Yoffset; 
		}
		
		////Debug.d("accel2", ("accy: "+ accy +"   y: "+accellerometerSpeedY));
		if(sprite.isAlive== true)
		{
			if((accy <= -2)&&(sprite.collision == true))
			{
				sprite.jumpAttempt = true;
				sprite.isCrouching = false;
				sprite.uninterruptible=false;
				//sprite.stopAnimation();
				sprite.uninterruptible = false;
			}
			else if(accy >= 1)
			{
				sprite.jumpAttempt = false;
				sprite.isCrouching = true;
			}
			else
			{	
				sprite.jumpAttempt = false;
				sprite.isCrouching = false;
			}
		}
		

		if((sprite.falling == true)||(sprite.topofjump==true))
		{
			if(sprite.touching==false)
			{
				sprite.stopAnimation();
			}
			
			if((accy>0)&&(sprite.touching == true)&&(sprite.isHit == false))
			{
				if((sprite.intendedDirection>-1)&&(sprite.intendedDirection<1))
				sprite.groundpound =true;
			}	
		}
		
	}
	
	 void jumpRotate(GameCharacter sprite)
	 {
		
		sprite.rotationcounter=sprite.rotationcounter+30;
		if(sprite.rotationcounter>359){sprite.rotationcounter=0;}
		if(sprite.rotationcounter<-359){sprite.rotationcounter=0;}
		sprite.setRotationCenter(32, 50);
		int direction;
		if(sprite.xdirection==0)
		{
			direction = sprite.lastxdirection;
		}
		else
		{
			direction = sprite.xdirection;
		}
		
		if((sprite.groundpound==false)&&(sprite.groundpounded==false))
		{
			if((sprite.currentlyattacking==true)&&(sprite.currentlyjumping==true))
			{
				if(sprite.weapon==1)
				{
					sprite.setRotation((float)(direction * sprite.rotationcounter));
				}
				else if(sprite.weapon==0)
				{
					sprite.setRotation((float)(direction * sprite.velocity_y));
				}
			}
		}
			
	 }
	
	
	 void grabvectorx(GameCharacter sprite, float accellerometerSpeedX)   
    {
		if(icelevel == true)
		{
			friction = 0.005;
		}
		else 
		{
			friction = 0.04;
		}
		

		sprite.intendedDirection = (int)accellerometerSpeedX;


		
		
		
		if((sprite.isHit == true)&&(sprite.isAlive ==true))
    	{
	    	if(sprite.collision == false)
			{
	    		
	    		if(sprite.xdirection == -1)
	    		{
	    		//sprite.animate(50,1, tuckr);
	    		}
	    		else if (sprite.xdirection == 1)
	    		{
	    			//sprite.animate(50, 1, tuckl);
	    		}
	    		
	    		if(sprite.assailant==null)
	    		{
	    			//Debug.d("assailant not set!");
	    		}
	    		
	    		sprite.rotationcounter=(int) (sprite.rotationcounter+(2*sprite.assailant.hitPower));
				if(sprite.rotationcounter>359){sprite.rotationcounter=0;}
				if(sprite.rotationcounter<-359){sprite.rotationcounter=0;}
				sprite.xdirection = sprite.hitDirection;
				sprite.xmovement = (sprite.hitDirection * sprite.assailant.hitPower);
				sprite.setRotationCenter(32, 50);
				sprite.setRotation((float) (sprite.hitDirection*sprite.rotationcounter));
				//sprite.deathanim = sprite.assailant.hitDirection;
				
			}
			else if(sprite.collision == true)
			{
				sprite.xdirection = 0;
				sprite.xmovement = 0;
				sprite.rotationcounter=0;
				sprite.setRotation(sprite.rotationcounter);
			}
    	}
		else
			{
			float accx = accellerometerSpeedX;
			
			
			
			
			if(sprite.isAnimationRunning()==true)
			{
				if(accx <0)
				{
				if(sprite.isFlippedHorizontal()==false)
				{
					sprite.stopAnimation();
				}
				
				}
				else if(accx >0)
				{
					if(sprite.isFlippedHorizontal()==true)
					{
						sprite.stopAnimation();
					}
					
				}
			}
			
			
			
			if(sprite.isCrouching == true)
				{ 	
				
					if(sprite.xmovement > -0.2)
					{
						if(sprite.xmovement < 0.2)
						{
							//sprite.isCrouching = false;
							sprite.xmovement = 0;
						}
					}
				
					if(sprite.xmovement > 0){sprite.xmovement = sprite.xmovement - friction;}
					else if(sprite.xmovement < 0){sprite.xmovement = sprite.xmovement + friction;}
				}
			else
				{
					
				    if(accx > 1) 
				    	{ 
				    	sprite.xmovement = 2;
				    	sprite.xdirection = 1;
				    	if(accx > 2)
				    		{
				    		sprite.xmovement = 6;
				    		sprite.xdirection = 1;
				    		}
				    	}
				    if(accx < -1)
				    	{
				    	sprite.xmovement=-2;
				    	sprite.xdirection = -1;
				    	if(accx < -2)
				    		{
				    		sprite.xmovement = -6;
				    		sprite.xdirection = -1;
				    		}
				    	}
				    
				    
				    if(accx > -1 && accx < 1)
						{
				    	sprite.xdirection=0;
						sprite.xmovement = 0; 
						}
				}
		}
		if(sprite.groundpound==true)
		{
		sprite.xdirection=0;
		sprite.xmovement = 0;
		}
	}
	
	
	
	
	
	 void UpdateEnemySpritePosition(GameCharacter enemy, GameCharacter player, int iteration) {
			
			// find out if sprite is hit and what direction
			// Set the Boundary limits
		 	this.xrange = (int) (width);
			this.yrange = (int) (height - player.Offset());
	        int xgoal = (int) player.xpos;
	        int ygoal = (int) player.ypos;
	        int newxpos = (int) enemy.xpos;
	        int xdistance = (int) (enemy.xpos - xgoal);
	        if(xdistance < 0){xdistance = xdistance * -1;}
	    	int ydistance = (int) enemy.ypos - ygoal;
	    	if(ydistance < 0){ydistance = ydistance * -1;}
	    	
	    	
	    	
	    	
	    	if((xdistance > xrange)||(ydistance> 1.5*yrange))//Randomize if too far away
	    	{
	    		
	    		QueueRandomizeSprite(enemy);
	    		return;
	    	}
	    	
	    	
	    	
	    	if((enemy.justAppeared==true)&&(enemy.collision==true))
	    	{
	    		//FXsmokepuff(enemy);//animate smokepuff
	    		enemy.setVisible(true);
	    		enemy.isAlive = true;
	    		enemy.isHit = false;
	    		enemy.hit = 0;
	    	
	    		enemy.justAppeared = false;
	    	}
	    	
	    	if(player.isAlive == false){enemy.startattack = false; enemy.jumpAttempt = false; xdistance = 0; ydistance = 0; return;}
	    	
	        if((enemy.isHit == false) && (enemy.isAlive == true))
	        {
	        	
	        	
	        	
		       
		        	if(xgoal > enemy.xpos)
			        {
			        	enemy.xdirection = 1;
			        	enemy.lastxdirection=-1;
			        }
			        else if(xgoal < enemy.xpos)
			        {
			        	enemy.xdirection = -1;
			        	enemy.lastxdirection=-1;
			        }
		        	
		        	int xmovement = 6;
		        	
		        	if(enemy.isBoss==false)
		        	{
			        	
			        	if(xdistance < 100)
			        	{
				        		xmovement = 3;
					    }
			        	
			        	if(xdistance < 50)
			        	{
				        	xmovement = enemy.speed;
				        }
				        
			        	if(xdistance < 5)
				        {
				        	xmovement = 0;
				        }
		        	}
		        	else
		        	{
		        		if(xdistance < width)
			        	{
				        	xmovement = enemy.speed;
				        }
				        
			        	if(xdistance < (width/4))
				        {
				        	xmovement = 0;
				        }
		        	}
		        	
		        	
		        	
		        	
		        enemy.xmovement = xmovement * enemy.xdirection;
		        
		        int randomjumpdistance = (int)(Math.random()*100)+50;
	        
		        if(xdistance < randomjumpdistance)
		        {
			        if(ygoal > enemy.ypos)
			        {
			        	enemy.jumpAttempt = false;
			        	enemy.isCrouching = true;
			        }
			        else if(ygoal < enemy.ypos -50)
			        {
			        	enemy.jumpAttempt = true;
			        	enemy.isCrouching = false;
			        }
			        else
			        {
			        	enemy.jumpAttempt = false;
			        	enemy.isCrouching = false;
			        }
			    }       
		        else 
		        {
		        	enemy.jumpAttempt = false;
		        	enemy.isCrouching = false;
		        	AIxdistance = (int)Math.abs(ygoal - enemy.xpos);
		        	AIydistance = (int)Math.abs(ygoal - enemy.ypos);
		        	if((AIxdistance>150)&&(xdistance < (width *.5))&&(AIydistance < 50))
		        	{
		        		
		        		enemy.throwingrange = true;
		        		
		        	}
		        	else
		        	{
		        		enemy.throwingrange = false;
		        	}
		        	
		        }
		        
		        
		        
	           if(enemy.currentlyattacking == false)
	           {
			        if(((ydistance < 30) && (xdistance < 50) &&(enemy.weapon == 0))||((ydistance <100)&&(xdistance <100)&&(enemy.weapon == 1)))
			        {										
			        	int randomseed = (int) (Math.random()*100);
						
						if(randomseed < enemy.aggression)
						{
			        	
			        		enemy.startattack = true;
			        		enemy.hit = 0;
						}
				        else 
				        {
				        	enemy.startattack = false;
				        }			
			        }
			        else 
			        {
			        	enemy.startattack = false;
			        }
		        }
	        }
	        
	        
		        enemy.oldxpos = enemy.xpos;
		        enemy.oldypos = enemy.ypos;
		        
		        
		        // Calculate New X,Y Coordinates within Limits
		        int Yvector = 0;
		        if(enemy.jumpAttempt == true){Yvector = 1;}
		        if(enemy.isCrouching == true){Yvector = -1;}
		        
		        
		        
		        
		        
		        
		        if(floorCollision(enemy, horizontalshapearray) == true)  			
				{
		        	
			        if((enemy.falling ==true)||(enemy.topofjump==true))
		        	{
			        	enemy.stopAnimation();
			        	if(enemy.falling == true)
			        	{
			        		enemy.AnimateMe = true;
			        	}
		        		if(enemy.currentlyattacking==true)
		        		{
		        			enemy.currentlyattacking=false;
		        		}
		        	
					enemy.collision = true; 
					enemy.falling = false;
					enemy.rising = false;
					enemy.antigravity = gravity;
		        	}
					if(enemy.justAppeared==true)
					{
						enemy.smokebomb = true;
						enemy.justAppeared = false;
					}
				
					
				}
		        else
				{
					enemy.collision = false;
					enemy.antigravity = 0;
				}
		        
		        int newypos = (int) (enemy.ypos + jumploop(enemy,Yvector,horizontalshapearray));
		        
		        if((enemy.currentlyattacking == true)&&(enemy.currentlyjumping==true))
		        {
		        	jumpRotate(enemy);
		        }
		        int difference = AILoopX(enemy);
		        if((xdistance > 28) && (enemy.isAlive == true))
		        {
		        	newxpos = (int) (enemy.xpos + difference);
		        }     
		        else 
		        {
		        	newxpos = (int) enemy.xpos;
		        }
		        
		        enemy.ypos = newypos; 
		        enemy.xpos = newxpos;
		        
		       
		        
		        
	        

	        
	        //Compare previous frame values to detect direction   --KEEP THIS FOR ANIMATOR
	        //if(enemy.oldxpos > newxpos){enemy.xdirection = -1;} //LEFT
	        //else if(enemy.oldxpos<newxpos){enemy.xdirection = 1;} //RIGHT
	        //else if(enemy.oldxpos == newxpos){enemy.xdirection = 0;}
	        
	        
	        if(enemy.oldypos > newypos){enemy.ydirection = -1;} //UP
	        else if(enemy.oldypos < newypos){enemy.ydirection = 1;} //DOWN
	        else if(enemy.oldypos == newypos){enemy.ydirection = 0;} //not moving
	        
	        enemy.setPosition(enemy.xpos, enemy.ypos);
	    
	    
		}

		
		
		
		
		void QueueRandomizeSprite(GameCharacter sprite) 
		{
		sprite.RandomizeMe = true;
		sprite.setVisible(false);
		}


		private int AILoopX(GameCharacter enemy) 
		{
			int movement = 0;
			int rotation = 0;
			
			if(enemy.isAlive ==true)
			{
				if(enemy.isHit == true)
		    	{	movement = enemy.hitvelocity_x;
			    	if(enemy.collision == false)
					{
						enemy.rotationcounter=(int) (enemy.rotationcounter+enemy.assailant.hitPower);
						if(enemy.rotationcounter>359){enemy.rotationcounter=0;}
						if(enemy.rotationcounter<-359){enemy.rotationcounter=0;}
						enemy.xdirection = enemy.hitDirection;
						enemy.xmovement = (enemy.hitDirection * enemy.assailant.hitPower);
						enemy.setRotationCenter(32, 58);
						//enemy.rotate((float) (enemy.hitDirection*enemy.rotationcounter));
						//enemy.deathanim = enemy.assailant.hitDirection;
						
					}
					else if(enemy.collision == true)
					{
						enemy.rotationcounter=0;
						slideFriction(enemy);
						
					}
			    	if(wallCollision(enemy,verticalshapearray)!= 0)
		        	{
			    		enemy.hitvelocity_x = enemy.hitvelocity_x*-1;
		        	}
			    	
			    	rotation = enemy.hitDirection*enemy.rotationcounter;
					enemy.xdirection = enemy.hitDirection;
				
					enemy.setRotation((float)rotation);
					
				}
				else
				{
					movement =(int)(enemy.xmovement);
				
					if(movement <0)
					{
					if(enemy.isFlippedHorizontal()==false)
					{
						enemy.stopAnimation();
					}
					
					}
					else if(movement >0)
					{
						if(enemy.isFlippedHorizontal()==true)
						{
							enemy.stopAnimation();
						}
						
					}
				}
				
				if(enemy.justAppeared==true){movement = 0;}
				
		
			}
			return movement;
		}
		
		
		
		
		
		
		private void slideFriction(GameCharacter sprite) 
		{
			if(sprite.hitvelocity_x != 0)
			{
				if(sprite.hitvelocity_x > 0)
				{
					if(sprite.hitvelocity_x<1)
					{
						sprite.hitvelocity_x = 0;
						sprite.isHit = false;
						return;
					}
					else
					{
						sprite.hitvelocity_x--;
						if(sprite.hitvelocity_x == 0)
						{
							sprite.isHit = false;
						}
						return;
					}
					
				}
				else if(sprite.hitvelocity_x<0)
				{
					if(sprite.hitvelocity_x>-1)
					{
						sprite.hitvelocity_x = 0;
						sprite.isHit = false;
						return;
					}
					else
					{
						sprite.hitvelocity_x++;
						if(sprite.hitvelocity_x == 0)
						{
							sprite.isHit = false;
						}
						return;
					}
					
				}
			}
			
		}


		void randomizeSprite(GameCharacter sprite, GameCharacter player, boolean SpawnBoss)
		{
				int level = sprite.level;
				int xgoal = (int) player.xpos;
				int ygoal = (int) player.ypos;
				
				int randomx = (int)(Math.random()*xrange*(.5));
				
				int minimumdistance = (int) (3*player.getWidth());
				int randomseed = (int) (Math.random()*100);
				int xdistance;
				if(randomseed < 50)
				{
					xdistance = -randomx - minimumdistance;
				}
				else 
				{
					xdistance = randomx + minimumdistance;
				}
				
				
				
				if(sprite.isAlive == false)
				{
					sprite.resetCharacter();
					
					if(SpawnBoss==true)
					{
						level = player.level+ 1;
						sprite.isBoss=true;
					}
					
					sprite.levelUp(level);
					
					if(sprite.isBoss == false)
					{
						sprite.currenthealth=level;
					}
					//sprite.ScaleCharacter(toggleScaling); 
				}
				
				//NinjaColor(sprite);
					
				int xresult = xgoal + xdistance;
				int yresult = (int) (ygoal - (height *.5));
				
				sprite.xpos = xresult;
				sprite.ypos = yresult;
				sprite.setPosition(sprite.xpos, sprite.ypos);
				sprite.RandomizeMe=false;
				sprite.isHit = false;
				sprite.hitvelocity_x = 0;
				sprite.hitvelocity_y = 0;
				sprite.justAppeared = true;
				
			}

			
   void	randomizePlayer(GameCharacter sprite,float accellerometerSpeedX)
			{
				
				sprite.setVisible(false);
				int minimumdistance = (int) (3*sprite.getWidth());
				int randomseed = (int) (Math.random()*100);
				
				int currentxpos = (int) sprite.xpos;
				int currentypos = (int) sprite.ypos;
				int xdistance = randomseed + minimumdistance;
				
				if(accellerometerSpeedX >1)
				{
					sprite.xpos = currentxpos + xdistance;
				}
				else if(accellerometerSpeedX < -1)
				{
					sprite.xpos = currentxpos - xdistance;
				}
				else
				{
				sprite.xpos = currentxpos;
				}
				
				int randomY = (int) ((Math.random()*(height*.5))+(sprite.getHeight()*2));
				sprite.ypos = currentypos - randomY;
				sprite.setPosition(sprite.xpos,sprite.ypos);
				
			}

   
   
   public  int wallCollision(GameCharacter sprite, ArrayList<Rectangle> verticalshapearray )
	{
		int collideddirection = 0;
		
		

			for (int i=0; i<verticalshapearray.size(); i++)
			{
				Rectangle X = verticalshapearray.get(i);
				if(sprite.collidesWith(X)== true)
				{
					sprite.vertcollider=X;
					
		        	int colliderhalfpoint = (int)((X.getWidth()*.5) + X.getX());
		        	int spritehalfpoint = (int) (sprite.xpos + 32);
		        	if(colliderhalfpoint < spritehalfpoint)
		        	{
		        		collideddirection = (int) (X.getX()+X.getWidth()+1);
		        		return collideddirection;
		        	}
		        	else
		        	{
			        	if(colliderhalfpoint > spritehalfpoint)
			        	{
			        		collideddirection = (int) (X.getX()-sprite.getWidth()-1);
			        		
			        		return collideddirection;
			        	}
		        	}
		        	
				}
				
				
				
			}
			return collideddirection;
		
		
	}
	
				
   
   
   
				
public  int wallCollision(Sword sprite, ArrayList<Rectangle> verticalshapearray )
{
	int collideddirection = 0;
	
		for (int i=0; i<verticalshapearray.size(); i++)
		{
			
			if(sprite.leftSide.collidesWith(verticalshapearray.get(i))== true)
			{
				
				if(sprite.rightSide.collidesWith(verticalshapearray.get(i))== true)
				{
					collideddirection = 2;
					return collideddirection;
				}
				collideddirection = -1;
				return collideddirection;
			}
			
			if(sprite.rightSide.collidesWith(verticalshapearray.get(i))== true)
			{
				
				collideddirection = 1;
				return collideddirection;
			}
			
			
			
		}
return collideddirection;
}			
				
   
   
   	
	
	
	
	
	
	
	
}



















