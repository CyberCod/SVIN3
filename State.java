package com.smiths;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.Shape;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.badlogic.gdx.math.Vector2;



public class State{
	
public GameCharacter gamechar;
public boolean jump;
public String role = "initializeMe";
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
public int velocity_y = 0;                                                
public int velocity_x = 0;                                                
public float jumplevel = -20;                                               
public int difference = 0;                                         
public int xdirection = 0;                                         
public int ydirection = 0;                                         
public int rotationcounter =0;                                     
public int hitDirection = 0;                                        //	the direction character was hit
public int antigravity = 1;  //	this gets set equal to gravity if character is standing on something
public double xmovement =0;              
public GameObject assailant;  
public int deathanim = 0;  
public int oldanim = 0;   
public int newanim= 0;//
public int timer = 0;    
public int level = 1;             
public int hitPower = 10 + level;  
public float currentscale = 1;              //	set at calling time or defaults to 1
public float scalingfactor = (float) 0.3;  
public boolean isHit = false;           
public boolean currentlyjumping = false;      
public boolean topofjump = false; 
public boolean isAlive = true;  
public int deathTimer = 0;
public int totalHealth=(level*2)+3;                                 //
public int currenthealth=totalHealth;  
public Shape collider;	
public int deaths = 0;
public GameCharacter target;
public int lastxdirection;
public int xmomentum;  
public boolean isSword=false;
public boolean isGrounded;
public boolean isThrown;
public int specificAttack =0;
public int headshots=0;
public boolean isBoss = false;
//public int Ytrigger = 0;
public boolean hideboxes = true;
public int shuriken = 0;                                           
public GameCharacter rightBuddy = null;                             
public GameCharacter leftBuddy = null;                             	
public int weapon = 0;
public TiledTextureRegion reftexture;       //	this is set as a reference to what texture the character is using
public int slope = 0;
public int sloperotation = 0;
//	change how much bigger characters get for each level up
//private float effectivescale =1;     // this is getHeightScaled() / getHeight() set in scaleCollisionRect() which is called by ScaleCharacter()
                                                                 

//	references
public int feet = 0;
public int body = 1;
public int head = 2;                                           
                                                        
                                                        
// Boolean states of being                              
                                
public boolean isSliding = false;                                   	
public boolean uninterruptible = false;                           
                          
public boolean GameIsOver = false;                          
public boolean currentlyattacking = false;                          
public boolean jumpAttempt = false;                        
                    
           
public boolean looping =false;                   
                            
//experience                
public int kills = 0;       
public int experience = 0;
public int biggestkill;
                            
                            
//inventory                 
                            
public int currentweapon = 0;      
                            
                                        
//Initialization                                
                                    
public int speed = level + 2;                    
                                        
            
                                  
public int score = 0;                                              
public boolean justAppeared = true;                                
public double acceleration=0;                                      


public int AttackY =0;
public int AttackX =0;

public int attackspeed =75; // this levels up by one, increasing the animation speed of the attack by one.


public boolean bouncy = false;

//collision rectangles
                                              
                                              
                                                                       
                                                                       public int ShurikenLimit = 3;
public int angelsReleased =0;
public int TotalNinjasLeftToSpawn;


//specific attack values for attack collision boxes
public int kickRight = 1;
public int kickLeft = -1;
public int punchRight = 2;
public int punchLeft = -2;
public int midkickRight = 3;
public int midkickLeft = -3;
public int jumpkickUpRight = 4;
public int jumpkickUpLeft = -4;
public int jumpkickDownRight = 5;
public int jumpkickDownLeft = -5;

                                                         //   swordoverhandR	
public int swordoverhandRight	 = 6;                            //   swordunderhandR	
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


public boolean touching;
public int mostactiveninjas = 0;
public int hitvelocity_y = 0;
public int hitvelocity_x = 0;
public boolean flinchstarted = false;

public int loopcount = 0;
public int highestCombo = 0;
public int hit = 0;
public boolean AnimateMe = false;
public int practicehits = 0;
public Vector2 movementVector;
public boolean box2d = false;

public boolean isDropped;

public GameCharacter thrower;

public int rotationspeed;

public int powerupType;
public PhysicsConnector phcon;
public int damagemomentum;  
	
	
	
	
	
	
	
	
	
	
}