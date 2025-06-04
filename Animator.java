package com.smiths;



import org.andengine.util.debug.Debug;

import com.smiths.GameCharacter;




  
//animation manager
public class Animator
{
	
	
				//actions for animcheck	
	public int  actionCOMBO              =    0   ;
	public int	actionpoundground        =    1   ;       
	public int	actionpoundgroundbegin   =    2   ;       
	public int	actionflinchr            =    3   ;                         
	public int	actionflinchl            =    4   ;                         
    public int	actionslider             =    5   ;                         
	public int	actioncrouch             =    6   ;                         
	public int	actionwalkr              =    7   ;                         
	public int	actionrunr               =    8   ;                  
	public int	actionstand1             =    9   ;                  
	public int	actionkickr              =    10  ;                 
	public int	actionpunchr             =    11  ;                         
	public int	actionmidkickr           =    12  ;                         
	public int	actionswordoverhandR     =    13  ;                         
	public int	actionswordunderhandR    =    14  ;                         
	public int	actionswordstabmidR      =    15  ;                         
	public int	actionswordhighstabR     =    16  ;                         
	public int	actionswordsidelowR      =    17  ;
	public int	actionswordsidemidR      =    18  ;
	public int	actionjumprup            =    19  ;
	public int	actionjumprdown          =    20  ;
	public int	actionjumptop            =    21  ;
	public int	actionjumpup             =    22  ;
	public int	actionjumpdown           =    23  ;
	public int	actionjumpkickrup        =    24  ;
	public int	actionjumpkickrdown      =    25  ;
	public int	actionswordjumpR         =    26  ;
	public int	actionslidel             =    27  ;
	public int	actionwalkl              =    28  ;
	public int	actionrunl               =    29  ;
	public int	actionkickl              =    30  ;
	public int	actionpunchl             =    31  ;
	public int	actionmidkickl           =    32  ;
	public int	actionswordoverhandl     =    33  ;
	public int	actionswordunderhandl    =    34  ;
	public int	actionswordstabmidl      =    35  ;
	public int	actionswordhighstabl     =    36  ;
	public int	actionswordsidelowl      =    37  ;
	public int	actionswordsidemidl      =    38  ;
	public int	actionjumplup            =    39  ;
	public int	actionjumpldown          =    40  ;
	public int	actionjumpkicklup        =    41  ;
	public int	actionjumpkickldown      =    42  ;
	public int	actionswordjumpl         =    43  ;
                               
	                           
	                   
	             
	                         
	
	
	
	public Soundbank soundbank;
	public int attackdirection;
	//creating all the bloody arrays
        
     public boolean SloMo;
     public boolean SFX;
  
    public long[] specialAttack = new long[24];
    
    final public Animations animations;
    
    final public int up = -1;
	final public int down = 1;
	final public int left = -1;
	final public int right = 1;
	private boolean debugging;
	
	
	
	
	public Animator(Soundbank sb, boolean sfx, boolean slomo, boolean debug)
	{
	this.animations = new Animations(sb, sfx, slomo);
	this.SFX=sfx;
	this.debugging = debug;
	this.SloMo=slomo;
	this.soundbank = sb;
	
}
	
	
	public void SpecialCases(GameCharacter player)
	{
		if(player.oldanim == 43)
		{
			if(player.collision == true)
			{
				player.stopAnimation();
				player.AnimateMe = true;
			}
		}
		else if(player.oldanim == 26)
		{
			if(player.collision == true)
			{
				player.stopAnimation();
				player.AnimateMe = true;
			}
		}
		
	}
	
	
	
	
	
	public void playAttack(GameCharacter sprite)
	{
		if(sprite.currentlyattacking==true){return;}
		
		if(sprite.isPlayer)
		{
			
			if(sprite.weapon==0)
			{
				soundbank.playSwoosh(SFX, SloMo);
			}
			else if((sprite.weapon == 1)||(sprite.weapon == 2))
			{
				soundbank.playSwish(SFX, SloMo);
			}
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
	
	
	
	
	void SpecialFinish(GameCharacter sprite, int specialattack)
	{
		sprite.uninterruptible = false;
	
		sprite.startattack = false;
		sprite.specialattack[specialattack].setCurrentTileIndex(0);
		sprite.specialattack[specialattack].setVisible(false);
		sprite.currentlyattacking= false;
		sprite.specialInprogress= 0;
		sprite.AnimateMe=true;
		animations.actionstand1(sprite);
	}
	
	public boolean AnimCheck(boolean specialrun, GameCharacter sprite, int action)
	{
		boolean running = sprite.isAnimationRunning();
		int special = sprite.specialInprogress;
		boolean important = sprite.uninterruptible;
		int oldaction = sprite.oldanim;
		boolean answer = false;
		
			if(special != 0)
			{		
				if(sprite.specialattack[special].isAnimationRunning() == true)
				{
					return true;
				}
				else
				{
					sprite.SpecialFinish(special);	
				}
				
			}
		if(sprite.isVisible() == false)
		{
			sprite.setVisible(true);
		}
		
			if(sprite.AnimateMe == true)
			{
				sprite.oldanim = 999999;
				sprite.uninterruptible = false;
				sprite.stopAnimation();
				sprite.AnimateMe = false;
				return false;
			}
				
			
	
		
		if((running)&&((important)&&(specialrun == false)))
		{
			
			answer = true;
		}
		if((action == oldaction)&&(running))
		{
			sprite.loopcount++;
			answer = true;
		}
		else
		{
			sprite.loopcount=0;
		}
		
		if((!running)&&(sprite.specialInprogress == 0))
		{
			answer = false;
		}
		sprite.oldanim = action;
		return answer;
	}
	
	
	
	void SpecialMovement(GameCharacter sprite)
	{
		//             Special Movement
		
		   sprite.specialxmovement = sprite.swipeX;
		
		
			
	}
	
	
	
	boolean SwipeCheck(GameCharacter sprite)
	{
		if(sprite.weapon != 2){return false;}
		if(sprite.isPlayer == false){return false;}
		//Special special = null;
		int inprogress = sprite.specialInprogress;
		
		//int part = 0;
	
		if(inprogress!= 0)  //if a special is already running
		{
			if(sprite.specialattack[inprogress].isAnimationRunning() == true) //return and don't interrupt special that is running
			{
				sprite.setRotation(0);
				return true;
			}
		}
		
		sprite.specialxmovement = 0;
		sprite.specialymovement = 0;		
		
		if((sprite.swipeX!= 0)||(sprite.swipeY!= 0))
		{
			Debugger("swipeX = "+sprite.swipeX+" swipeY = "+sprite.swipeY);	
		}
		
		if(sprite.swipeup == true)//if(sprite.swipeY!= 0)
		{
			sprite.swipeup = false;
			// SpecialMovement(sprite);
			sprite.specialymovement = sprite.swipeY;
			
				int xdir;
				if(sprite.xdirection == 0){xdir = sprite.lastxdirection;} else {xdir = sprite.xdirection;}
				if(xdir > 0)
				{
					Debugger("right spin");
					//if(AnimCheck(true, sprite, 3)==true){return true;}
					animations.specialspinright(sprite);
					soundbank.playSpinjump(SFX, SloMo);
					return true;
				}
				else if(xdir < 0)
				{
					Debugger("left spin");
					animations.specialspinleft(sprite);
					soundbank.playSpinjump(SFX, SloMo);
					return true;
				}
				
				
			
			
			
				
		}
		else if((sprite.frenzy == true))//if(sprite.swipeX != 0) // if swipe in X direction an not the Y
		{
			int sstreak = (int)(sprite.level/2);
			sprite.frenzy = false;
			 //stickman.swipeleft = true;
		    sprite.swipeX = sprite.intendedDirection * sprite.level;
		    //stickman.swipeY = 0;
		
			if(sprite.weapon == 2)//&&(sprite.specialstreak< sstreak)) //if weapon is dual swords
			{
				//rotate move in order if next part doesn't exist
				Debugger("specialstreak = "+sprite.specialstreak+" sstreak = "+sstreak);
				
			
				if(sprite.specialattack[2].specialpartInprogress + 1 > sprite.specialattack[2].totalparts ) 
				{
					sprite.specialattack[2].specialpartInprogress = 0;
				}
				int part = sprite.specialattack[2].specialpartInprogress;//part readied for setting frames
				
				//get swipe movement 
				
				
				sprite.specialxmovement = sprite.swipeX;
				sprite.specialstreak++;
				
				
				if(sprite.swipeX > 0)    //if swipe right
				{  //RIGHT
					//if(AnimCheck(true, sprite, 2)==true){return true;}
					Debugger("swipeX right, starting special2, no swipeY");
					animations.specialcombo1right(sprite, part);
					soundbank.playDualSwordCombo(SFX, SloMo);
				
					return true;
					
				}
				else if(sprite.swipeX < 0)                                     
				{  //LEFT                                                 
					Debugger("swipeX left, starting special2, no swipeY");
					//if(AnimCheck(true, sprite, 2)==true){return true;}
					animations.specialcombo1left(sprite, part);
					soundbank.playDualSwordCombo(SFX, SloMo);
				
					return true;
				}
				
			}
			else{sprite.frenzy=false;}
		}
		
			return false;
	}
	
	
	public void Animationswitch(GameCharacter sprite, boolean sloMoON) 
	{
		animations.SpeedSwitch(sprite,sloMoON);
		
		SpecialCases(sprite);
		
		if(SwipeCheck(sprite)== true)
		{
			return;
		}
	
		
		
		
		
		
		
		if((sprite.isAnimationRunning()==false)&&((sprite.specialInprogress == 0)||(sprite.specialattack[sprite.specialInprogress].isAnimationRunning() == false)))
		{
			sprite.currentlyattacking=false;
		}
		
		
		
		boolean attack = false;
		if(sprite.startattack==true)
		{
			attack =true;
		}
		
		
		
		
		
		if(sprite.xdirection == 0){attackdirection = sprite.lastxdirection;} else {attackdirection = sprite.xdirection;}
		
		if(sprite.groundpound == true)
		{
			if(sprite.collision ==true)
			{if(AnimCheck(false, sprite,actionpoundground )==true){return;}
				animations.actionpoundground1(sprite);return;
			}
			else
			{if(AnimCheck(false, sprite, actionpoundgroundbegin)==true){return;}
				animations.actionpoundgroundbegin(sprite);return;
			}
			
			
		}
		if(sprite.isHit == true)
		{if(sprite.flinchstarted == true){return;}
								//we need hit location specific animations here
			if(sprite.hitDirection < 1)
			{if(AnimCheck(false, sprite, actionflinchl)==true){return;}
				animations.actionflinchl(sprite);return;			
			}
			else if(sprite.hitDirection > 1)
			{if(AnimCheck(false, sprite, actionflinchr)==true){return;}
				animations.actionflinchr(sprite);return;				
			}
			return;	
		}
		else
		{
			if(sprite.currentlyjumping == false)
			{ sprite.setRotation(0);
				if(sprite.isCrouching == true)
				{
					if(sprite.groundpounded == true)
					{if(AnimCheck(false, sprite,actionpoundground )==true){return;}
						animations.actionpoundground2(sprite);return;	
					}
					Debugger("crouching/sliding " + sprite.xmovement);
					if(sprite.xmovement > 3)
					{if(AnimCheck(false, sprite, actionslider)==true){return;}
					sprite.setCurrentTileIndex(36);
						animations.actionslider(sprite);return;	
					}
					else if(sprite.xmovement < -3)
					{if(AnimCheck(false, sprite, actionslidel)==true){return;}
					sprite.setCurrentTileIndex(36);
						animations.actionslidel(sprite);return;	
					}
					else if(sprite.xmovement==0)
					{if(AnimCheck(false, sprite, actioncrouch)==true){return;}
						animations.actioncrouch(sprite);return;	
					}
				}
				else{sprite.isSliding = false;}
				
				if((attack == false)&&(sprite.currentlyattacking==false))
				{
					
					if(sprite.bunkerdown)
					{if(AnimCheck(false, sprite, actionstand1)==true){return;}
					
						animations.actionstand1(sprite);
						sprite.bunkerdown=false;
						return;
					}
				
					
					if(sprite.xdirection > 0)
						{
						
						if(sprite.xmovement <= 3)
							{if(AnimCheck(false, sprite, actionwalkr)==true){return;}
								animations.actionwalkr(sprite);return;	
							}
						else if(sprite.xmovement >3)
							{if(AnimCheck(false, sprite, actionrunr)==true){return;}
								animations.actionrunr(sprite);return;	
							}
						}
					else if(sprite.xdirection < 0)
						{
						
						if(sprite.xmovement >= -3)
							{if(AnimCheck(false, sprite, actionwalkl)==true){return;}
							animations.actionwalkl(sprite);return;
							
							}	
						else if(sprite.xmovement < -3)
							{if(AnimCheck(false, sprite, actionrunl)==true){return;}
							animations.actionrunl(sprite);return;
							
							}
						}
					else if(sprite.xdirection == 0)
						{if(AnimCheck(false, sprite, actionstand1)==true){return;}
						
						animations.actionstand1(sprite);return;
					}
					
					
					
					
				}
				else if(attack == true)
				{  
					playAttack(sprite);
					
					sprite.specialstreak = 0;
					
					if(sprite.weapon == 0)
					{ 
						int rndAttack = (int)(Math.random()*100);
						
						if(attackdirection > 0)
							{  //RIGHT
							
							if(rndAttack >60)
								{if(AnimCheck(false, sprite, actionkickr)==true){return;} 
								animations.actionkickr(sprite);return;
								}
							if((rndAttack <=60)&&(rndAttack>30))
								{if(AnimCheck(false, sprite,actionpunchr )==true){return;} 
								animations.actionpunchr(sprite);return;
								}
							if(rndAttack <=30)
								{if(AnimCheck(false, sprite, actionmidkickr)==true){return;} 
								animations.actionmidkickr(sprite);return;
								}
							
							
							return;
							}
						
						else if(attackdirection < 0)
							{  //LEFT
						
							if(rndAttack >60)
								{if(AnimCheck(false, sprite, actionkickl)==true){return;} 
								animations.actionkickl(sprite);return;
								}
							if((rndAttack <=60)&&(rndAttack>30))
								{if(AnimCheck(false, sprite,actionpunchl )==true){return;} 
								animations.actionpunchl(sprite);return;
								}
							if(rndAttack <=30)
								{if(AnimCheck(false, sprite,actionmidkickl )==true){return;} 
								animations.actionmidkickl(sprite);return;
								}
							
						
							return;
							}
						
						
					}
					else if((sprite.weapon == 1)||(sprite.weapon == 2))           //STICKMAN CAN HAZ SWORD?  YES, STICKMAN CAN HAZ SWORD.
					{
						
						int	rndAttack = (int)(Math.random()*100);
					
						if(attackdirection > 0)
							{  //RIGHT
				
							if(rndAttack >85)
								{if(AnimCheck(false, sprite, actionswordoverhandR)==true){return;} 
								animations.actionswordoverhandR(sprite);return;
								}
							if((rndAttack <=85)&&(rndAttack>70))
								{if(AnimCheck(false, sprite, actionswordunderhandR)==true){return;} 
								animations.actionswordunderhandR(sprite);return;
								}
							if((rndAttack <=70)&&(rndAttack>55))
								{if(AnimCheck(false, sprite,actionswordstabmidR )==true){return;} 
								animations.actionswordstabmidR(sprite);return;
								}
							if((rndAttack <=55)&&(rndAttack>40))
								{if(AnimCheck(false, sprite, actionswordhighstabR)==true){return;} 
								animations.actionswordhighstabR(sprite);return;
								}
							if((rndAttack <=40)&&(rndAttack>20))
								{if(AnimCheck(false, sprite, actionswordsidelowR)==true){return;} 
								animations.actionswordsidelowR(sprite);return;
								}
							if(rndAttack <=20)
								{if(AnimCheck(false, sprite, actionswordsidemidR)==true){return;}
								animations.actionswordsidemidR(sprite);return;
								}
							
							return;
							}                                                            
						                                                                 
						else if(attackdirection < 0)                                     
							{  //LEFT                                                    
							                                     
							                                     
							                                     
							
							
							
							if(rndAttack >85)
								{if(AnimCheck(false, sprite,actionswordoverhandl )==true){return;}  
								animations.actionswordoverhandl(sprite);return;
								}
							if((rndAttack <=85)&&(rndAttack>70))
								{if(AnimCheck(false, sprite, actionswordunderhandl)==true){return;} 
								animations.actionswordunderhandl(sprite);return;
								}
							if((rndAttack <=70)&&(rndAttack>55))
								{if(AnimCheck(false, sprite, actionswordstabmidl)==true){return;} 
								animations.actionswordstabmidl(sprite);return;
								}
							if((rndAttack <=55)&&(rndAttack>40))
								{if(AnimCheck(false, sprite, actionswordhighstabl)==true){return;} 
								animations.actionswordhighstabl(sprite);return;
								}
							if((rndAttack <=40)&&(rndAttack>20))
								{if(AnimCheck(false, sprite, actionswordsidelowl)==true){return;} 
								animations.actionswordsidelowl(sprite);return;
								}
							if(rndAttack <=20)
								{if(AnimCheck(false, sprite, actionswordsidemidl)==true){return;} 
								animations.actionswordsidemidl(sprite);return;
							}
						
						return;
						}  
					}
				
					
				} // end of attacking
			}	//end standing /crouching
			else if(sprite.currentlyjumping == true)
			{
				if((attack == false)&&(sprite.currentlyattacking==false))
				{
					sprite.setRotation(0);
				
					if(sprite.xdirection > 0)  //if running right
						{
										  
							if(sprite.rising == true)
								{if(AnimCheck(false, sprite, actionjumprup)==true){return;}
								animations.actionjumprup(sprite);return;
								}
							else if(sprite.falling == true)
								{if(AnimCheck(false, sprite, actionjumprdown)==true){return;}
								animations.actionjumprdown(sprite);return;
								}
						}												
					else if(sprite.xdirection < 0) // if running left
						{ 
					
							if(sprite.rising == true)
								{if(AnimCheck(false, sprite,actionjumplup )==true){return;} 
								animations.actionjumplup(sprite);return;
								}
							else if(sprite.falling == true)
								{if(AnimCheck(false, sprite, actionjumpldown)==true){return;}
								animations.actionjumpldown(sprite);return;
								}
						}
						else if(sprite.xdirection == 0) // not running
						{
						
							if(sprite.rising == true)
								{ //no weapon
									if(sprite.velocity_y == 0)
									{if(AnimCheck(false, sprite, actionjumptop)==true){return;}
									animations.actionjumptop(sprite);return;
									}
									else
									{if(AnimCheck(false, sprite,actionjumpup )==true){return;}
									animations.actionjumpup(sprite);return;
									}
								}
							else if(sprite.falling == true)
								{if(AnimCheck(false, sprite,actionjumpdown )==true){return;}
								animations.actionjumpdown(sprite);return;
								}
						}
					
				    }
				else if(attack == true)
					{
					playAttack(sprite);
					sprite.specialstreak = 0;
						if(sprite.weapon == 0)
						{
							
								if(attackdirection > 0)  //if running right
								{if(AnimCheck(false, sprite, actionjumpkickrup)==true){return;}
								animations.actionjumpkickrup(sprite);return;
								}
								else if(attackdirection < 0) // if running left
								{if(AnimCheck(false, sprite,actionjumpkicklup )==true){return;}
								animations.actionjumpkicklup(sprite);return;
								}
								
								
								
						
							
						}
						else if (sprite.weapon==1)
						{

							
								if(attackdirection > 0)  //if running right
								{if(AnimCheck(false, sprite, actionswordjumpR)==true){return;}
								animations.actionswordjumpR(sprite);return;
								}
								else if(attackdirection < 0) // if running left
								{if(AnimCheck(false, sprite, actionswordjumpl)==true){return;}
								animations.actionswordjumpl(sprite);return;
								}
								
								
								return;
							
							
						}
						else if(sprite.weapon == 2)
						{
							if(attackdirection > 0)  //if running right
							{if(AnimCheck(false, sprite, actionswordjumpR)==true){return;}
							animations.actionswordjumpR(sprite);return;
							}
							else if(attackdirection < 0) // if running left
							{if(AnimCheck(false, sprite, actionswordjumpl)==true){return;}
							animations.actionswordjumpl(sprite);return;
							}
							
						}
						
					}
			}
			}
		return;
		}
	
	
	
	
	
	public void jumpRotate(GameCharacter sprite)
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
		
		if(sprite.weapon==1)
		{
			if((sprite.groundpound==false)&&(sprite.groundpounded==false))
			{
				sprite.setRotation((float)(direction * sprite.rotationcounter));
			}
			
		}
		else if(sprite.weapon==0)
		{
			if((sprite.groundpound==false)&&(sprite.groundpounded==false))
			{
				sprite.setRotation((float)(direction * sprite.velocity_y));
			}
		}
			
	 }
	public void Debugger(String message){
		if(debugging){
			Debug.d(message);
		}
	}
	
	
}