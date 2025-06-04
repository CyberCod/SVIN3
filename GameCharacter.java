package com.smiths;



import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;






public class GameCharacter extends GameObject implements IAnimationListener {
	//game actor definition
	//public int Ytrigger = 0;
	int hats  =0;
	int shoes =0;
	int faces =0;
	int gloves=0;
	int base  =0;
	int pants =0;
	int shirts=0;
	int wings  =0;
	public Special[] specialattack;
	public Animator anims;
	public int specialInprogress = 0;  
	public int currentattack;
	public boolean hideboxes = true;
	                                         
	public GameCharacter rightBuddy = null;                             
	public GameCharacter leftBuddy = null;                             	
	public TiledTextureRegion reftexture;       //	this is set as a reference to what texture the character is using
	 
	public int slope = 0;
	public int sloperotation = 0;
	                                                                   	
	 //	change how much bigger characters get for each level up
	//public float effectivescale =1;     // this is getHeightScaled() / getHeight() set in scaleCollisionRect() which is called by ScaleCharacter()
	                                                                 
	
	//	
	public int feet = 0;
	public int body = 1;
	public int head = 2;                                           
	                                                        
	                                                        
	// Boolean states of being                              
	                                
	public boolean isSliding = false;                                   	
	public boolean isBoss = false;         
	public boolean uninterruptible = false;                           
	public boolean GameIsOver = false;                          
	public boolean currentlyattacking = false;                          
	public boolean jumpAttempt = false;                        
	public boolean startattack;       
	public boolean looping =false;                   
	                            
	//experience                
	public int kills = 0;       
	public int experience = 0;
	public int biggestkill;
	                            
	                            
	//inventory                 
	public 	int weapon = 0;
    public int currentweapon = 0;      
	public int Topaz = 0;
	public int Amnethyst = 0;
	public int Emerald = 0;
	public int Ruby = 0;
	public int Saphire = 0;
	public int Pearl = 0;
	public int Diamond = 0;
	public int Chest = 0;
	public int Key = 0;                         
	protected int arrows = 0;         
	public int shuriken = 0;  
	
	//Initialization                                
	                                    
	public int speed = level + 2;                    
	public int hitPower = 10 + level;                
	public int totalHealth=((level*2)+3)*10;              
	public int currenthealth=totalHealth;            
	                                        
	            
	                                  
	public int score = 0;                                              
	public boolean justAppeared = true;                                
	public double acceleration=0;                                      
	public int lastxdirection = 0;                                     

	
	public int AttackY =0;
	public int AttackX =0;
	
	public int attackspeed =75; // this levels up by one, increasing the animation speed of the attack by one.
	
	
	public boolean bouncy = false;
	public int specificAttack =0;
	
	//collision rectangles
	
	public Rectangle feetRect;
//	public Rectangle leftFootRect;
//	public Rectangle rightFootRect;
	public Rectangle bodyRect;
	public Rectangle headRect;
	
	public Rectangle kickRightRect;
	public Rectangle punchRightRect;
	public Rectangle kickLeftRect;
	public Rectangle punchLeftRect;
	public Rectangle midkickRightRect;
	public Rectangle midkickLeftRect;
	public Rectangle jumpkickUpRightRect;
	public Rectangle jumpkickDownRightRect;
	public Rectangle jumpkickUpLeftRect;
	public Rectangle jumpkickDownLeftRect;

		
		
	public 	Rectangle swordoverhandRightRect	;                                    
	public Rectangle swordoverhandLeftRect	    ;                                       
	public Rectangle swordunderhandRightRect   ;	                                     
	public Rectangle swordunderhandLeftRect	;                                    
	public Rectangle swordhighstabRightRect	;                                    
	public Rectangle swordhighstabLeftRect	    ;                                       
	public Rectangle swordstabmidRightRect	    ;	                                     
	public Rectangle swordstabmidLeftRect	    ;                                       
	public Rectangle swordsidelowRightRect	    ;                                      
	
	                                              
	                                              
	                                              
	                                              
	                                                                       
	                                                                       public int ShurikenLimit = 3;
	public int angelsReleased =0;
	public int TotalNinjasLeftToSpawn;
	
	
	//specific attack values for attack collision boxes
	public 	int kickRight = 1;
	public int kickLeft = -1;
	public int punchRight = 2;
	public int punchLeft = -2;
	public int midkickRight = 3;
	public int midkickLeft = -3;
	public int jumpkickUpRight = 4;
	public int jumpkickUpLeft = -4;
	public int jumpkickDownRight = 5;
	public int jumpkickDownLeft = -5;
	public int specialattackRight	 = 6;                             //
	public int specialattackLeft	 = -6;  
	                                                         //   swordoverhandR	
	public 	int swordoverhandRight	 = 6;                            //   swordunderhandR	
	public int swordoverhandLeft	 = -6;                           //   swordhighstabR	
	public int swordunderhandRight	 =7;                             //   swordstabmidR	
	public int swordunderhandLeft	 = -7;                           //   swordsidelowR   
	public int swordhighstabRight	 = 8;                            //   swordsidemidR	
	public int swordhighstabLeft	 = -8;                           //   swordjumpR		
	public int swordstabmidRight	 =9;                             //
	public int swordstabmidLeft	 = -9;                           //
	public int swordsidelowRight    =10;                            //   swordoverhandRight	
	public int swordsidelowLeft	 = -10;                          //   swordoverhandLeft	
	public int swordsidemidRight		 =11;                        //   swordunderhandRight	
	public int swordsidemidLeft		 = -11;                      //   swordunderhandLeft	
	public int swordjumpRight		 =12;                            //   swordhighstabRight	
	public int swordjumpLeft		 = -12;                          //   swordhighstabLeft	
	                                                         //   swordstabmidRight	
	public int aggression = 5;                               //   swordstabmidLeft	
	                                                         //   swordsidelowRight   
	public boolean flipped = false;                          //   swordsidelowLeft	
	public boolean justRandomized = false;                   //   swordsidemidRight	
	public boolean RandomizeMe = false;                      //   swordsidemidLeft	
	public boolean groundpound = false;                      //   swordjumpRight		
	public boolean groundpounded = false;                    //   swordjumpLeft		
	public boolean smokebomb = false;                        //
	public boolean throwingrange = false;                    //
	public int lungekicks = 0;                            //
	public int smokebombs = 0;                            //
	public int intendedDirection;                            //
	public Rectangle vertcollider;                           //
	public int slowmotimer = 0;                              //
	public int shurikenTimer = 0;                            //
                           
	public boolean animrunning;                                     //
	public boolean animready;                                       //
	
	public Rectangle swordjumpRightRect;
	public Rectangle swordjumpLeftRect;
	public Rectangle swordsliceRightRect;
	public Rectangle swordsliceLeftRect;
	public Rectangle swordstabRightRect;
	public Rectangle swordstabLeftRect;
	public boolean touching;
	public int mostactiveninjas = 0;
	public int hitvelocity_y = 0;
	public int hitvelocity_x = 0;
	public boolean flinchstarted = false;
	public int rotationcounter =0;
	public int loopcount = 0;
	public int highestCombo = 0;
	public int hit = 0;
	public boolean AnimateMe = false;
	public int practicehits = 0;
	public Object physicsBody;
	public int highestStreak;

	public int swipeY;
	public int swipeX;
	public int specialxmovement;
	public int specialymovement;
	public boolean swipedown;
	public boolean swipeup;
	public boolean swipeleft;
	public boolean swiperight;
	public int killstreak = 0;
	public int attackstreak = 0;
	public int specialstreak = 0;
	public boolean frenzy = false;
	
	
	public boolean disarmed =false;
	public int hadokenspeed = 10;
	public int hadokenbuff = 0;
	public int arrowbuff = 0;
	
	public int dropweapontimer = 0;
	public int caltrops = 0;
	
	public boolean triplearrows=true;
	public boolean bunkerdown = false;
	public boolean myTurn =false;
	public boolean NinjaFrenzy = false;
	public boolean IamRotating = false;

	
	
	
	
	public GameCharacter( int x, int y,TiledTextureRegion texture, VertexBufferObjectManager OM, Animator animator) 
	{
		
		
	super(x, y, texture, OM);
	this.reftexture = texture;
	//build collision boxes
	anims = animator;
	feetRect = new Rectangle(25, 45, 14, 19,OM);
	//leftFootRect = new Rectangle(25,45, 6, 19, OM);
	//rightFootRect = new Rectangle(33,45,6,19,OM);
	specialattack = new Special[10];
	headRect = new Rectangle(26, 13, 12,10,OM);
	bodyRect = new Rectangle(24, 13, 16,51,OM);
	slope = 0;
	sloperotation = 0;
	
	kickRightRect = new Rectangle(32, 27, 21, 5,OM);;
	punchRightRect = new Rectangle(32, 28, 18, 5,OM);
	kickLeftRect = new Rectangle(11, 27, 21, 5,OM);;
	punchLeftRect = new Rectangle(14, 28, 18, 5,OM);
	midkickRightRect = new Rectangle(32, 37, 20, 5,OM);
	midkickLeftRect = new Rectangle(12, 37, 20, 5,OM);
	jumpkickUpRightRect = new Rectangle(32, 53, 28, 5,OM);
	jumpkickDownRightRect = new Rectangle(32, 61, 24, 5,OM);
	jumpkickUpLeftRect = new Rectangle(4, 53, 28,5,OM);
	jumpkickDownLeftRect = new Rectangle(8, 61, 24, 5,OM);
	
	
	//need to adjust these values                                                                             
	//swordRightRect	 = new Rectangle(32, 5, 28, 40,OM);                     //   swordoverhandR	   
	//swordLeftRect	 = new Rectangle(4, 5, 28, 40,OM);                      //   swordunderhandR	   
	                                                                                //   swordhighstabR	   
	swordsliceRightRect	 = new Rectangle(32, 5, 28, 40,OM);                     //   swordstabmidR	       
	swordsliceLeftRect	 = new Rectangle(4, 5, 28, 40,OM);                      //   swordsidelowR        
	swordstabRightRect	 = new Rectangle(32, 30, 32, 8,OM);                     //   swordjumpR		   
	swordstabLeftRect	 = new Rectangle(0, 30, 32, 8,OM);                      //                        
	swordjumpRightRect	 = new Rectangle(26, 15, 25, 25,OM);                    //   swordoverhandRight	
	swordjumpLeftRect	 = new Rectangle(13, 15, 25, 25,OM);                    //   swordoverhandLeft	   
                                                                                    //   swordunderhandRight	
	                                                                                //   swordunderhandLeft	
	                                                                                //   swordhighstabRight	
	                                                                                //   swordhighstabLeft	   
	                                                                                //   swordstabmidRight	   
	                                                                                //   swordstabmidLeft	   
	//attach                                                                        //   swordsidelowRight    
	this.attachChild(feetRect				);                                      //   swordsidelowLeft	   
	//this.attachChild(leftFootRect			);
	//this.attachChild(rightFootRect			);
	this.attachChild(headRect				);                                      //   swordsidemidRight	   
	this.attachChild(bodyRect				);                                      //   swordsidemidLeft	   
	this.attachChild(kickRightRect			);                                      //   swordjumpRight		
	this.attachChild(punchRightRect			);                                      //   swordjumpLeft		   
	this.attachChild(kickLeftRect			);
	this.attachChild(punchLeftRect			);
	this.attachChild(midkickRightRect		);                                       //   swordoverhandRightRect	 
	this.attachChild(midkickLeftRect		);                                       //   swordoverhandLeftRect	 
	this.attachChild(jumpkickUpRightRect	);                                       //   swordunderhandRightRect	
	this.attachChild(jumpkickDownRightRect	);                                       //   swordunderhandLeftRect	 
	this.attachChild(jumpkickUpLeftRect		);                                       //   swordhighstabRightRect	 
	this.attachChild(jumpkickDownLeftRect	);                                       //   swordhighstabLeftRect	 
												                                     //   swordstabmidRightRect	 
											                                         //   swordstabmidLeftRect	 
	this.attachChild(swordsliceRightRect	);	        			                 //   swordsidelowRightRect   
	this.attachChild(swordsliceLeftRect	    );               						 //   swordsidelowLeftRect	 
	this.attachChild(swordstabRightRect		);                                       //   swordsidemidRightRect	 
	this.attachChild(swordstabLeftRect		);                                       //   swordsidemidLeftRect	 
	this.attachChild(swordjumpRightRect		);                                       //   swordjumpRightRect		 
	this.attachChild(swordjumpLeftRect		);                                       //   swordjumpLeftRect		 
	                                                                                
	                                                                                
	                                                                                
	                                                                                
	if(hideboxes == true)
	{
		feetRect.setVisible(false);                 
	//	leftFootRect.setVisible(false);	
	//	rightFootRect.setVisible(false);
		headRect.setVisible(false);                 
		bodyRect.setVisible(false);                 
		kickRightRect.setVisible(false);            
		punchRightRect.setVisible(false);           
		kickLeftRect.setVisible(false);             
		punchLeftRect.setVisible(false);            
		midkickRightRect.setVisible(false);         
		midkickLeftRect.setVisible(false);          
		jumpkickUpRightRect.setVisible(false);      
		jumpkickDownRightRect.setVisible(false);    
		jumpkickUpLeftRect.setVisible(false);       
		jumpkickDownLeftRect.setVisible(false);    
		swordsliceRightRect.setVisible(false);	                  
		swordsliceLeftRect.setVisible(false);	                  
													              
													              
		swordstabRightRect.setVisible(false);		              
		swordstabLeftRect.setVisible(false);		              
		swordjumpRightRect.setVisible(false);		              
		swordjumpLeftRect.setVisible(false);		              
	                                                              
	}                                                             
	else                                                          
	{                                                             
		feetRect.setVisible(true);   
		//leftFootRect.setVisible(true);
	//	rightFootRect.setVisible(true);
		headRect.setVisible(true);                 
		bodyRect.setVisible(true);
		feetRect.setColor(Color.BLUE);      
		//leftFootRect.setColor(Color.RED);
		//rightFootRect.setColor(Color.WHITE);
		headRect.setColor(Color.BLUE);        
		bodyRect.setColor(Color.BLUE);
		if(weapon == 0)
		{
			kickRightRect.setVisible(true);            
			punchRightRect.setVisible(true);           
			kickLeftRect.setVisible(true);             
			punchLeftRect.setVisible(true);            
			midkickRightRect.setVisible(true);         
			midkickLeftRect.setVisible(true);          
			jumpkickUpRightRect.setVisible(true);      
			jumpkickDownRightRect.setVisible(true);    
			jumpkickUpLeftRect.setVisible(true);       
			jumpkickDownLeftRect.setVisible(true); 
			kickRightRect.setColor(Color.CYAN);         
			punchRightRect.setColor(Color.RED);        
			kickLeftRect.setColor(Color.CYAN);          
			punchLeftRect.setColor(Color.RED);         
			midkickRightRect.setColor(Color.GREEN);         
			midkickLeftRect.setColor(Color.GREEN);       
			jumpkickUpRightRect.setColor(Color.YELLOW);   
			jumpkickDownRightRect.setColor(Color.PINK); 
			jumpkickUpLeftRect.setColor(Color.YELLOW);    
			jumpkickDownLeftRect.setColor(Color.PINK); 
		}
		else if(weapon==1)
		{
			swordsliceRightRect.setVisible(true);	                         
			swordsliceLeftRect.setVisible(true);	                         
			swordstabRightRect.setVisible(true);		                     
			swordstabLeftRect.setVisible(true);		                         
			swordjumpRightRect.setVisible(true);		                     
			swordjumpLeftRect.setVisible(true);	                             
			swordsliceRightRect.setColor(Color.YELLOW);	                     
			swordsliceLeftRect.setColor(Color.YELLOW);	                     
			                 
			                 
			swordstabRightRect.setColor(Color.RED);		
			swordstabLeftRect.setColor(Color.RED);		
			swordjumpRightRect.setColor(Color.CYAN);		
			swordjumpLeftRect.setColor(Color.CYAN);		
		}
	}
	
	
	
	
	
	
	}
	@Override
    protected void onManagedUpdate(float pSecondsElapsed) {
            // TODO Auto-generated method stub
		
            super.onManagedUpdate(pSecondsElapsed);
    }
	

	
	
	
	/**
	 * Returns whether the shape is collided with given rectangle.
	 * @param rectB rectangle to collide
	 * @return whether the shape is collided with given rectangle
	 */
	
	public boolean collidesWith(Rectangle rectB, int choice) 
	{
		
		Rectangle rectA = null; 
		if(choice == feet)
		{
		rectA = this.feetRect;
		}
		else if(choice == body)
		{
			rectA = this.bodyRect;
		}
		else if(choice ==head)
		{
			rectA = this.headRect;
		}
	
		
		return (rectA.collidesWith(rectB));		
			
		
		
	}
	
	
	@Override
	public Rectangle AttackCollisionRect()
	{
	
		
		
		if(this.specificAttack==kickRight			)		{return kickRightRect           ;}        //    swordsliceRightRect.                              
		if(this.specificAttack==kickLeft			)		{return kickLeftRect            ;}        //    swordsliceLeftRect.s                                       
		if(this.specificAttack==punchRight			)		{return punchRightRect			;}        //    swordstabRightRect.s                                       
		if(this.specificAttack==punchLeft			)		{return punchLeftRect           ;}        //    swordstabLeftRect.se                               
		if(this.specificAttack==midkickRight		)		{return midkickRightRect        ;}        //    swordjumpRightRect.s              
		if(this.specificAttack==midkickLeft			)		{return midkickLeftRect         ;}        //    swordjumpLeftRect.se               
		if(this.specificAttack==jumpkickUpRight		)		{return jumpkickUpRightRect     ;}        //                      
		if(this.specificAttack==jumpkickUpLeft		)		{return jumpkickUpLeftRect      ;}        //                   
		if(this.specificAttack==jumpkickDownRight	)		{return jumpkickDownRightRect   ;}        //         //   swordoverhandRight	                
		if(this.specificAttack==jumpkickDownLeft	)		{return jumpkickDownLeftRect    ;}               //   swordoverhandLeft	                
		if(this.specificAttack==swordoverhandRight	)		{return swordsliceRightRect	;}                   //   swordunderhandRight	                   
		if(this.specificAttack==swordoverhandLeft	)   	{return swordsliceLeftRect	;}                   //   swordunderhandLeft	                       
		if(this.specificAttack==swordunderhandRight )		{return swordsliceRightRect ;}                   //   swordhighstabRight	                         
		if(this.specificAttack==swordunderhandLeft	)		{return swordsliceLeftRect	;}                   //   swordhighstabLeft	                       
		if(this.specificAttack==swordhighstabRight	)		{return swordstabRightRect	;}                   //   swordstabmidRight	                       
		if(this.specificAttack==swordhighstabLeft	)	    {return swordstabLeftRect	;}                   //   swordstabmidLeft	                       
		if(this.specificAttack==swordstabmidRight	)	    {return swordstabRightRect	;}                   //   swordsidelowRight                         
		if(this.specificAttack==swordstabmidLeft	)		{return swordstabLeftRect	;}                   //   swordsidelowLeft	                           
		if(this.specificAttack==swordsidelowRight	)	    {return swordstabRightRect	;}                   //   swordsidemidRight	                         
		if(this.specificAttack==swordsidelowLeft	)	    {return swordstabLeftRect	;}                   //   swordsidemidRight
		if(this.specificAttack==swordjumpRight		)       {return swordjumpRightRect		;}                   //   swordsidemidLeft	                           
		if(this.specificAttack==swordjumpLeft		)	    {return swordjumpLeftRect		;}                   //   swordjumpRight		                       
																									             //   swordjumpLeft		                       
		                                                      
                                                                                                                  //   swordoverhandRightRect	    
		return null;                                                                                              //   swordoverhandLeftRect	        
	                                                                                                              //   swordunderhandRightRect	    
	}                                                                                                             //   swordunderhandLeftRect	    
                                                                                                                  //   swordhighstabRightRect	    
	                                                                                                              //   swordhighstabLeftRect	        
	                                                                                                              //   swordstabmidRightRect	        
	                                                                                                              //   swordstabmidLeftRect	        
	                                                                                                              //   swordsidelowRightRect         
	                                                                                                    		 //   swordsidelowLeftRect	        
	public void levelUp(int count)                                                                                         //   swordsidemidRightRect	        
	{                                                                                                             //   swordsidemidLeftRect	        
		level = level+count;                                                                                                 //   swordjumpRightRect		    
				                                                                                                  //   swordjumpLeftRect		        
		this.collision = false;                                                                                                                      
		this.attackspeed = this.attackspeed-(2*count);                                                                                                       
		if(this.attackspeed<40){this.attackspeed=40;}                                                                                                
		this.currentlyjumping = false;
		this.rising = false;
		this.falling = false;
		this.antigravity = 0;
		this.xdirection = 0;
		this.ydirection = 0;
		this.isCrouching = false;
		this.aggression = 4 + this.level;  
		if(this.level<10)
		{
		this.jumplevel = this.jumplevel -count;
		}
		else
		{
		this.jumplevel = (float) (this.jumplevel -(count*0.2));	
		}
		
		this.difference = 0;
		this.topofjump = false;
		this.startattack = false;
		this.isHit = false;
		this.jumpAttempt = false;
		this.oldanim = 0;
		this.newanim = 0;
		this.isAlive = true;
		this.deathTimer = 0;
		this.hitDirection = 0;
		
	
		this.speed = level + 2;
		this.hitPower = 10 + this.level;
		if (isPlayer == true)
		{
			totalHealth=((level*2)+3)*10;  //a  little cheating on behalf of the player
		}
		else if (isBoss == true)
		{
			totalHealth=((level*2)+5)*10;  //a  little cheating on behalf of the player
		}
		else
		{
			totalHealth = level*10;
		}
		if(totalHealth>this.currenthealth)
		{
			this.currenthealth=totalHealth;
		}
		this.deathanim = 0;
		
		score = score + (level*kills*experience);
		justAppeared = true;
		acceleration=0;
		lastxdirection = 0;
		xmovement =0;
		
		
	}
	
	
	
	public void revive(){
		this.isAlive = true;
		this.disabled = false;
		this.deaths++;
		if(this.deaths>this.level)
		{
			this.levelUp(1);
			this.deaths = 0;
		}
		if(!this.isBoss)
		{
			this.currenthealth = this.level;
		}
		else
		{
			this.currenthealth = this.totalHealth;
		}
	}
	
	
	public void getlevel()
	{
	
		this.resetCharacter();
		this.levelUp(this.level);
		
	}
	
	
	public void resetCharacter()
	{
		//this.isBoss=false;
		this.level= 1;
		this.attackspeed =75;
		this.currentscale = 1;
		this.collision = false;
		this.currentlyjumping = false;
		this.rising = false;
		this.falling = false;
		this.antigravity = 0;
		this.xdirection = 0;
		this.ydirection = 0;
		this.isCrouching = false;
		this.weapon=0;
		this.currentweapon = 0;
		this.jumplevel = -20;
		this.difference = 0;
		this.topofjump = false;
		this.startattack = false;
		this.isHit = false;
		this.jumpAttempt = false;
		this.oldanim = 0;
		this.isAlive = true;
		this.deathTimer = 0;
		this.hitDirection = 0;
		this.aggression = 5;
	
		this.speed = this.level + 2;
		this.hitPower = 10 + this.level;
		if(this.isPlayer ==false){this.totalHealth = this.level;}
		else{
			this.totalHealth=(this.level*2)+3;
		}
		this.currenthealth=this.totalHealth;
		this.deathanim = 0;
		
		this.score = 0;
		this.justAppeared = true;
		this.acceleration=0;
		this.lastxdirection = 0;
		this.xmovement =0;
		this.experience = 0;
	}
	
	
	public void SpecialFinish(int num)
	{
		
		this.specialInprogress= 0;
		this.startattack = false;
		this.specialattack[num].setCurrentTileIndex(0);
		this.specialattack[num].setVisible(false);
		this.currentlyattacking= false;
		anims.animations.actionstand1(this);
		this.AnimateMe = true;
		
	}
	
	
	
	
	
	public void Stop()
	{
		
		
		
		if(this.specialInprogress != 0)
		{
			if(this.specialInprogress == 2)
			{
			SpecialFinish(2);
			}
			
			if(this.specialInprogress == 3)  //and if it is the dual sword spin
			{
				
				
				if(this.specialattack[this.specialInprogress].isAnimationRunning() == true) //return and don't interrupt special that is running
				{
					return;
				}
				else
				{
					if((this.touching == true)||(this.collision == false))  //and player is still touching the screen
					{
						//begin spin again
						Debug.d("spin again");
						
						this.currentlyattacking = true; this.startattack = false;
						long[] durations = Speciallong(30, this.specialattack[3].partframes[0]);
						int[] frames = this.specialattack[3].part.get(0);
						this.specialattack[3].animate(durations, frames , 0);	
						this.swipeX = 0;
						this.swipeY = 0;
						
					}
					else  //special is finished, clean up
					{
						Debug.d("special is finished, cleaning up");
						SpecialFinish(3);
						
					}
				}
			}
		}
		else
		{
			this.stopAnimation();
		}
		
	}
	
	
	public void AddSpecialAttack(int offsetX, int offsetY, TiledTextureRegion tex, int specialnumber, int parts)
	{
		if(tex!= null)
		{
			this.specialattack[specialnumber] = new Special(offsetX, offsetY, tex, this.getVertexBufferObjectManager(), parts);
			this.specialattack[specialnumber].setCurrentTileIndex(0);
			//this.attachChild(specialattack[attacknumber]);
			
		}
		
	}


	public long[] Speciallong(int playerspeed, int frames)
	{
		long[] temp = new long[frames];
		for(int x = 0;x<frames;x++)
		{
			temp[x] = playerspeed;
			
		}
		return temp;
		
	}
	
	

	public void levelDown() 
	{
		this.level--;
		if(this.level <1){this.level =1;}
		
	}
	@Override
	public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
			int pInitialLoopCount) {
		// TODO Auto-generated method stub
		this.animrunning = true;
		this.animready=false;
	}
	
	@Override
	public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
			int pOldFrameIndex, int pNewFrameIndex) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
			int pRemainingLoopCount, int pInitialLoopCount) {
		// TODO Auto-generated method stub
		this.animrunning = false;
		this.animready = true;
	}
	
	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		this.animrunning = false;
		this.animready = true;
	}
	
	

	public void collect()
	{
	
		 this.setVisible(false);
		 this.stopAnimation();
		 this.detachSelf();
	}
	
	
	
	
	
	
}
