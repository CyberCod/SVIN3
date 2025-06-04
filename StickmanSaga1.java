package com.smiths;


public class StickmanSaga1 extends StickmanActivity{
	
	
	
	private float shurikenHPratio;

	@Override
	public int ModeSelection(){
		Mode = preferences.getInt("Mode",0);	
		int Framerate;
		if(Mode == EasyMode){
			Framerate = 24;
			shurikenHPratio = (float) .5;
		}
		else if(Mode == NormalMode)
		{
			Framerate = 28;
			shurikenHPratio = (float)1;
		}
		else
		{
			Framerate = 32;
			shurikenHPratio = (float)2;
		}
		return Framerate;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}