package com.smiths;

import org.andengine.util.debug.Debug;


public class Animations{
	
	public int attackdirection;
	//creating all the arrays
	public int[]  shurikenl;          
	public int[]  shurikenr;          
	public int[]  walkr;              
     public boolean SloMo;
     public boolean SFX;
              
	public int[]  idler;              
	public int[]  kickr;              
              
	public int[]  midkickr;           
  
	public int[]  tuckr;              
	public int[]  runr;               
	public int[]  stand1;             
	public int[]  jumprup;            
	public int[]  jumprtop;           
	public int[]  jumprdown;          
	public int[]  punchr;             
	public int[]  jumpkickrup;        
	public int[]  jumpkickrdown;     
	
	
	public int[]   swordoverhandR	;
	public int[]   swordunderhandR	;
	public int[]	swordhighstabR	;
	public int[]   swordstabmidR	;
	public int[]   swordsidelowR   ;
	public int[]   swordsidemidR	;
	public int[]   specialattackR	;
	public int[]   swordjumpR		;
	
	
	
	
	
	
	public int[]  slider;             
	public int[]  layr;               
	public int[]  jumpup;             
	public int[]  jumpdown;           
	public int[]  jumptop;            
	public int[]  crouch;             
	public int[]  flinchr;            
	public int[]  ninjaCorpseflinchr; 
	public int[]  stickmandeathr;     
	public int[] ninjadeathr;
    
    public int[]  healthicon;
    public int[]  speedboost;
    public int[]  levelup;
    public int[]  shurikens;
    public int[]  blank;
    public int[]  bigninjaface;
    public int[]  lilninjaface;
    public int[]  angel;
    public int[]  corpsel;
    public int[]  corpser;
    public int[]  poundground;
    public int[]  poundgroundbegin;
    public int[]  gutkicked;
    
    public long[] oneFrame = new long[1];
    public long[] twoFrame = new long[2];
    public long[] threeFrame = new long[3];
    public long[] walkthreeFrame = new long[3];
    public long[] fourFrame = new long[4];
    public long[] fiveFrame = new long[5];
    public long[] sixFrame = new long[6];
    public long[] sevenFrame = new long[7];
    public long[] eightFrame = new long[8];
	private Soundbank soundbank;
  
	
    public Animations(Soundbank sb, boolean sfx, boolean slomo)
	{
    	this.SFX=sfx;
    	this.SloMo=slomo;
    	this.soundbank = sb;
    	
    	init();

}
	
    

    
    
	public void actionpoundground1(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.setRotation(0);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(twoFrame, poundground, 0);
		//sprite.specificAttack = sprite.midkickRight;
		
		sprite.uninterruptible = true;//curranim =7;//punch right
		sprite.groundpound = false;
		sprite.groundpounded = true;
	}
	
	public void actionpoundground2(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(eightFrame, poundground, 0);sprite.uninterruptible = true;
	}
	
	
	public void actionpoundgroundbegin(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.setRotation(0);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(oneFrame, poundgroundbegin, 0);
		//sprite.specificAttack = sprite.midkickRight;
		
		sprite.uninterruptible = true;
	}
	
	
	
	public void actionflinchl(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.uninterruptible = false;
		sprite.animate(sixFrame,this.flinchr, 0);
		sprite.flinchstarted=true;
	}
	
	
	
	
	
	public void actionflinchr(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.uninterruptible = false;
		sprite.animate(sixFrame, this.flinchr, 0);
		sprite.flinchstarted=true;
	}
	
	public void actionslider(GameCharacter sprite){
		Debug.d("crouching/sliding left");
		sprite.setFlippedHorizontal(false);
		sprite.setCurrentTileIndex(36);//animate(oneFrame, slider, 0)
		sprite.isSliding = true;
		sprite.uninterruptible = false;
		  // slide right
	}
	
	
	public void actionslidel(GameCharacter sprite){
		Debug.d("crouching/sliding right");
			sprite.setFlippedHorizontal(true);
			sprite.setCurrentTileIndex(36);//.animate(oneFrame, slider, 0);
			sprite.isSliding = true;
			sprite.uninterruptible = false;
			 // slide left
		
	}
		
	
	public void actioncrouch(GameCharacter sprite){
		Debug.d("crouching/sliding still");
		sprite.setFlippedHorizontal(false);
		sprite.animate(oneFrame, crouch, 0);
		sprite.isSliding = false;
		sprite.uninterruptible = false;
		 // slide left
		
	}
		
	
	public void actionwalkr(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.animate(walkthreeFrame, walkr, -1);
		sprite.uninterruptible = false;
		  // walk right
		
	}
		
	
	public void actionrunr(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.animate(sixFrame,runr, -1);
		sprite.uninterruptible = false;
			// run right
		
	}
		
	
	public void actionwalkl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.animate(walkthreeFrame, walkr, -1);
		sprite.uninterruptible = false;
		 // walk left
		
	}
		
	
	public void actionrunl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.animate(sixFrame, runr, -1);
		sprite.uninterruptible = false;
		  //run left
		
	}
		
	
	public void actionstand1(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.animate(oneFrame, stand1, 0);
		sprite.uninterruptible = false;
		  // stand
		
	}
		
	
	public void actionkickr(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(fiveFrame,kickr, 0);
		sprite.specificAttack = sprite.kickRight;
		//FXkickpuff(right);
		sprite.uninterruptible = true;//curranim =6;//kick right
		
	}
		
	
	public void actionpunchr(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(fourFrame, punchr, 0);
		sprite.specificAttack = sprite.punchRight;
		//FXpunchpuff(right);
		sprite.uninterruptible = true;//curranim =7;//punch right
		
	}
		
	
	public void actionmidkickr(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(fiveFrame, midkickr, 0);
		sprite.specificAttack = sprite.midkickRight;
		//FXmidkickpuff(right);
		sprite.uninterruptible = true;//curranim =7;//punch right
		
	}
		
	
	public void actionkickl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(fiveFrame,kickr, 0);
		sprite.specificAttack = sprite.kickLeft;
		//FXkickpuff(left);
		sprite.uninterruptible = true;
		//curranim =8; //kick left
		
	}
		
	
	public void actionpunchl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(fourFrame,punchr, 0);
		sprite.specificAttack = sprite.punchLeft;
		//FXpunchpuff(left);
		sprite.uninterruptible = true;
		//curranim =9;//punch left
		
	}
		
	
	public void actionmidkickl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(fiveFrame, midkickr, 0);
		sprite.specificAttack = sprite.midkickLeft;
		//FXmidkickpuff(left);
		sprite.uninterruptible = true;
		//curranim =9;//punch left
		
	}
		
	
	public void actionswordoverhandR(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame,swordoverhandR, 0);
		sprite.specificAttack = sprite.swordoverhandRight;
		//FXkickpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionswordunderhandR(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame, swordunderhandR, 0);
		sprite.specificAttack = sprite.swordunderhandRight;
		//FXpunchpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionswordstabmidR(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame, swordstabmidR, 0);
		sprite.specificAttack = sprite.swordstabmidRight;
		//FXmidkickpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionswordhighstabR(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame, swordhighstabR, 0);
		sprite.specificAttack = sprite.swordstabmidRight;
		//FXmidkickpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionswordsidelowR(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame, swordsidelowR, 0);
		sprite.specificAttack = sprite.swordstabmidRight;
		//FXmidkickpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionswordsidemidR(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame, swordsidemidR, 0);
		sprite.specificAttack = sprite.swordstabmidRight;
		//FXmidkickpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionswordoverhandl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame,swordoverhandR, 0);
		sprite.specificAttack = sprite.swordoverhandLeft;
		//FXkickpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionswordunderhandl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame, swordunderhandR, 0);
		sprite.specificAttack = sprite.swordunderhandLeft;
		//FXpunchpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionswordstabmidl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame, swordstabmidR, 0);
		sprite.specificAttack = sprite.swordstabmidLeft;
		//FXmidkickpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionswordhighstabl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame, swordhighstabR, 0);
		sprite.specificAttack = sprite.swordstabmidLeft;
		//FXmidkickpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionswordsidelowl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame, swordsidelowR, 0);
		sprite.specificAttack = sprite.swordstabmidLeft;
		//FXmidkickpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionswordsidemidl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.currentlyattacking = true; 
		sprite.startattack = false;
		sprite.animate(threeFrame, swordsidemidR, 0);
		sprite.specificAttack = sprite.swordstabmidLeft;
		//FXmidkickpuff(right);
		sprite.uninterruptible = true;
		
	}
		
	
	public void actionjumprup(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.animate(oneFrame,jumprup, 0);
		sprite.setRotation(0);
		sprite.uninterruptible = false;
		//curranim =10;//jumping up right
		
	}
		
	
	public void actionjumprdown(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.animate(oneFrame,jumprdown, 0);
		sprite.setRotation(0);
		sprite.uninterruptible = false;
		//curranim =11; //falling down right
		
	}
		
	
	public void actionjumplup(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.animate(oneFrame, jumprup, 0);
		sprite.setRotation(0);
		sprite.uninterruptible = false;
		//curranim =12;//jump up left
		
	}
		
	
	public void actionjumpldown(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.animate(oneFrame, jumprdown, 0);
		sprite.setRotation(0);
		sprite.uninterruptible = false;
		//curranim =13;//falling down left
		
	}
		
	
	public void actionjumptop(GameCharacter sprite){
		sprite.animate(oneFrame,jumptop, 0);
		sprite.setRotation(0);
		sprite.uninterruptible = false;
		
		
	}
		
	
	public void actionjumpup(GameCharacter sprite){
		sprite.animate(oneFrame, jumpup, 0);
		sprite.setRotation(0);
		sprite.uninterruptible = false;
		//curranim =12;//jump up left
		
	}
		
	
	public void actionjumpdown(GameCharacter sprite){
		sprite.animate(oneFrame,jumpdown, 0);
		sprite.setRotation(0);
		sprite.uninterruptible = false;
		//curranim =13;//falling down left
		
	}
		
	
	public void actionjumpkickrup(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; sprite.startattack = false;    
		sprite.animate(oneFrame, jumpkickrup, -1);
		sprite.specificAttack = sprite.jumpkickUpRight;
		sprite.uninterruptible = true;
		//curranim =14;// jumping punch right
		
	}
		
	
	public void actionjumpkicklup(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.currentlyattacking = true; sprite.startattack = false;   
		sprite.animate(oneFrame,jumpkickrup, -1);
		sprite.specificAttack = sprite.jumpkickUpLeft;
		sprite.uninterruptible = true;
		//curranim = 15;// jumping punch left
	
		
	}
		
	
	public void actionswordjumpR(GameCharacter sprite){
		sprite.setFlippedHorizontal(false);
		sprite.currentlyattacking = true; sprite.startattack = false;    
		sprite.animate(oneFrame, swordjumpR, -1);
		sprite.specificAttack = sprite.swordjumpRight;
		sprite.uninterruptible = true;
		//curranim =14;// jumping punch right	
	
		
	}
		
	
	public void actionswordjumpl(GameCharacter sprite){
		sprite.setFlippedHorizontal(true);
		sprite.currentlyattacking = true; sprite.startattack = false;   
		sprite.animate(oneFrame,swordjumpR, -1);
		sprite.specificAttack = sprite.swordjumpLeft;
		sprite.uninterruptible = true;
		//curranim = 15;// jumping punch left
	
		
	}
		
	/*
	public void action(GameCharacter sprite){
		
		
	}
		*/
	
	public void specialspinright(GameCharacter sprite){
		sprite.specialattack[2].setVisible(false);
		Debug.d("swipe right, starting spin, swipeY is not 0");
		sprite.specialattack[3].setFlippedHorizontal(false);
		sprite.specificAttack = sprite.specialattackRight;
		sprite.uninterruptible = true;
		sprite.stopAnimation();
		sprite.specialInprogress = 3;
		sprite.setCurrentTileIndex(33);
		sprite.specialattack[3].setVisible(true);
		sprite.currentlyattacking = true; sprite.startattack = false;
		long[] durations = Speciallong(30, sprite.specialattack[3].partframes[0]);
		int[] frames = sprite.specialattack[3].part.get(0);
		sprite.specialattack[3].animate(durations, frames , 0);
	
		sprite.swipeX = 0;
		sprite.swipeY = 0;	
			
	}
	
	public void specialspinleft(GameCharacter sprite){
		Debug.d("swipe left, starting spin, swipeY is not 0");
		sprite.specialattack[2].setVisible(false);
		sprite.specialattack[3].setFlippedHorizontal(true);
		sprite.specificAttack = sprite.specialattackLeft;
		sprite.uninterruptible = true;
		sprite.stopAnimation();
		sprite.specialInprogress = 3;
		sprite.setCurrentTileIndex(33);
		sprite.specialattack[3].setVisible(true);
		sprite.currentlyattacking = true; sprite.startattack = false;
		long[] durations = Speciallong(30, sprite.specialattack[3].partframes[0]);
		int[] frames = sprite.specialattack[3].part.get(0);
		sprite.specialattack[3].animate(durations, frames , 0);
		
		sprite.swipeX = 0;
		sprite.swipeY = 0;
		
	}
	
	public void specialcombo1right(GameCharacter sprite, int part){
		Debug.d("swipeX right, starting special2, no swipeY");
		sprite.specialattack[3].setVisible(false);
		sprite.specialattack[2].setFlippedHorizontal(false);
		sprite.specificAttack = sprite.specialattackRight;
		sprite.uninterruptible = true;
		sprite.stopAnimation();
		sprite.specialInprogress = 2;
		sprite.setCurrentTileIndex(33);
		sprite.specialattack[2].setVisible(true);
		sprite.currentlyattacking = true; sprite.startattack = false;
		long[] durations = Speciallong(sprite.attackspeed-4, sprite.specialattack[2].partframes[part]);
		
		//Debug.d("frames for part "+part+" = "+sprite.specialattack[2].partframes[part]);
		int[] frames = sprite.specialattack[2].part.get(part);
		sprite.specialattack[2].animate(durations, frames , 0);
		sprite.specialattack[2].specialpartInprogress=2;
		sprite.swipeX = 0;
		sprite.swipeY = 0;
		
	}
	
	public void specialcombo1left(GameCharacter sprite, int part){
		Debug.d("swipeX left, starting special2, no swipeY");
		//if(AnimCheck(true, sprite, 2)==true){return true;}
		sprite.specialattack[3].setVisible(false);
		sprite.specialattack[2].setFlippedHorizontal(true);
		sprite.specificAttack = sprite.specialattackLeft;
		sprite.uninterruptible = true;
		sprite.stopAnimation();
		sprite.specialInprogress = 2;
		sprite.setCurrentTileIndex(33);
		sprite.specialattack[2].setVisible(true);
		sprite.currentlyattacking = true; sprite.startattack = false;
		long[] durations = Speciallong(sprite.attackspeed, sprite.specialattack[2].partframes[part]);
		//Debug.d("frames for part "+part+" = "+sprite.specialattack[2].partframes[part]);
		int[] frames = sprite.specialattack[2].part.get(part);
		sprite.specialattack[2].animate(durations, frames , 0);
		sprite.specialattack[2].specialpartInprogress=2;
		//sprite.specialattack[2].animate(specialAttack, specialattackR, 0);
		Debug.d("special attack in progress is "+sprite.specialattack[2].specialpartInprogress);
		
		//FXmidkickpuff(right);
		sprite.swipeX = 0;
		sprite.swipeY = 0;
		
	}
	
	public void specialcombo2left(GameCharacter sprite){
		
		
	}
	
	public void specialcombo2right(GameCharacter sprite){
		
		
	}
		
	public void initSpeed(int playerspeed)
	{
		
		
	    oneFrame[0] = playerspeed;
	    twoFrame[0] = playerspeed;
	    twoFrame[1] = playerspeed;
	    threeFrame[0] = playerspeed;
	    threeFrame[1] = playerspeed;
	    threeFrame[2] = playerspeed;
	    walkthreeFrame[0] = (int)(playerspeed);
	    walkthreeFrame[1] = (int)(playerspeed);
	    walkthreeFrame[2] = (int)(playerspeed);
	    fourFrame[0] = playerspeed;
	    fourFrame[1] = playerspeed;
	    fourFrame[2] = playerspeed;
	    fourFrame[3] = playerspeed;
	    fiveFrame[0] = playerspeed;
	    fiveFrame[1] = playerspeed;
	    fiveFrame[2] = playerspeed;
	    fiveFrame[3] = playerspeed;
	    fiveFrame[4] = playerspeed;
	    sixFrame[0] = playerspeed;
	    sixFrame[1] = playerspeed;
	    sixFrame[2] = playerspeed;
	    sixFrame[3] = playerspeed;
	    sixFrame[4] = playerspeed;
	    sixFrame[5] = playerspeed;
	    sevenFrame[0] = playerspeed;
	    sevenFrame[1] = playerspeed;
	    sevenFrame[2] = playerspeed;
	    sevenFrame[3] = playerspeed;
	    sevenFrame[4] = playerspeed;
	    sevenFrame[5] = playerspeed;
	    sevenFrame[6] = playerspeed;
	    eightFrame[0] = playerspeed;
	    eightFrame[1] = playerspeed;
	    eightFrame[2] = playerspeed;
	    eightFrame[3] = playerspeed;
	    eightFrame[4] = playerspeed;
	    eightFrame[5] = playerspeed;
	    eightFrame[6] = playerspeed;
	    eightFrame[7] = playerspeed;
	    
	}
	
	public void SpeedSwitch(GameCharacter player, boolean slowmo)
	{
		
		
		if((player.isPlayer==false)&&(slowmo == true))
			{
				initSpeed(player.attackspeed*3);
				
			}
			else
			{
				initSpeed(player.attackspeed);
				
			}
		
	}
	
	
	void init(){

	poundground = new int[2];
	poundground[0] = 9;
	poundground[1] = 17;
	
	poundgroundbegin = new int[1];
	poundgroundbegin[0] = 1;
	
	gutkicked = new int[3];
	gutkicked[0] = 18;
	gutkicked[1] = 19;
	gutkicked[2] = 20;
	
	
	shurikenl          = new int[4];
	shurikenl[0] = 0;
	shurikenl[1] = 1;
	shurikenl[2] = 2;
	shurikenl[3] = 3;
	
	
	
	shurikenr          = new int[4];
	shurikenr[0] = 3;
	shurikenr[1] = 2;
	shurikenr[2] = 1;
	shurikenr[3] = 0;
	

	walkr              = new int[3];
	walkr[0] = 21;
	walkr[1] = 22;
	walkr[2] = 23;
	
	
	
	kickr              = new int[5];
	kickr[0] = 39;
	kickr[1] = 39;
	kickr[2] = 38;
	kickr[3] = 38;
	kickr[4] = 38;
	
	midkickr           = new int[5];
	midkickr[0] = 42;
	midkickr[1] = 42;
	midkickr[2] = 41;
	midkickr[3] = 41;
	midkickr[4] = 41;
	
	
	tuckr              = new int[1];
	tuckr[0] = 43;
	

	
	runr               = new int[6];
	runr[0] = 2;
	runr[1] = 3;
	runr[2] = 4;
	runr[3] = 5;
	runr[4] = 6;
	runr[5] = 7;
			
	stand1             = new int[1];
	stand1[0] = 32;
	
	
	jumprup            = new int[1];
	jumprup[0] = 3;
	
	jumprtop           = new int[1];
	jumprtop[0] = 5;
	
	jumprdown          = new int[1];
	jumprdown[0] = 6;
	
	
	punchr             = new int[4];
	punchr[0] = 26;
	punchr[1] = 27;
	punchr[2] = 28;
	punchr[3] = 28;
	
	
	jumpkickrup        = new int[1];
	jumpkickrup[0] = 34;
	
	
	jumpkickrdown      = new int[1];
	jumpkickrdown[0] = 35;
	
	
	
	 swordoverhandR	      = new int[3];
	 swordoverhandR[0] = 25;
	 swordoverhandR[1] = 26;
	 swordoverhandR[2] = 27;
	 
	 
	
	 
	 
	 swordunderhandR	  = new int[3];
	 swordunderhandR[0] = 28;
	 swordunderhandR[1] = 37;
	 swordunderhandR[2] = 38;
	 
	 
	
	 swordhighstabR = new int[3];
	 swordhighstabR [0] = 46;  
	 swordhighstabR [1] = 47;  
	 swordhighstabR [2] = 39;
	 
	 swordsidelowR      = new int[3];
	 swordsidelowR [0] =  28;
	 swordsidelowR [1] =  29;
	 swordsidelowR [2] =  30;
	 
	 swordsidemidR	    = new int[3];
	 swordsidemidR	[0] =  28;
	 swordsidemidR	[1] =  29;
	 swordsidemidR	[2] =  31;
	 
	 specialattackR = new int[24];
	 
	
	 
	 swordstabmidR		      = new int[3];
	 swordstabmidR[0] = 41;
	 swordstabmidR[1] = 42;
	 swordstabmidR[2] = 44;
	 

	 
	 swordjumpR		      = new int[1];
	 swordjumpR[0] = 45;
	
	 
	
	
	
	slider             = new int[1];
	slider[0] = 36;
	
	
	layr               = new int[1];
	layr[0] = 15;
	
	jumpup             = new int[1];
	jumpup[0] = 0;
	
	jumpdown           = new int[1];
	jumpdown[0] = 8;
	
	jumptop            = new int[1];
	jumptop[0] = 16;
	
	crouch             = new int[1];
	crouch[0] = 24;
	
	
	flinchr            = new int[6];
	flinchr[0] = 10;
	flinchr[1] = 11;
	flinchr[2] = 12;
	flinchr[3] = 13;
	flinchr[4] = 14;
	flinchr[5] = 15;
	
	
	
	/*   have to make corpse objects and textures 
	ninjaCorpseflinchl = new int[3];
	ninjaCorpseflinchl[0] = 
	ninjaCorpseflinchl[1] = 
	ninjaCorpseflinchl[2] = 
	                 
	ninjaCorpseflinchr = new int[3];
	ninjaCorpseflinchr[0] = 
	ninjaCorpseflinchr[1] = 
	ninjaCorpseflinchr[2] = 
	                 
	stickmandeathl     = new int[1];
	stickmandeathl[0] = 
	
	stickmandeathr     = new int[1];
	stickmandeathr[0] = 
	
	ninjadeathl        = new int[1];
	ninjadeathl[0] = 
	
	ninjadeathr        = new int[1];
	ninjadeathr[0] = 

*/
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
	
}