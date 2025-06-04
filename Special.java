package com.smiths;


import java.util.ArrayList;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;




//root game object class
public class Special extends AnimatedSprite implements IAnimationListener{
	

	public int specialpartInprogress = 0;
	public boolean running;
	public int totalparts;
	public ArrayList<int[]> part = new ArrayList<int[]>();
	public int[] partframes;
	
	public Special( int x, int y,TiledTextureRegion texture, VertexBufferObjectManager OM, int parts) 
	{
	super(x, y, texture, OM);
	
	this.totalparts = parts;
	partframes = new int[parts];
	this.setVisible(false);
	}

	
	
	public void AddPart(int partnumber, int startframe, int endframe)
	{
		int numberofframes =endframe - startframe + 1;
		partframes[partnumber] = numberofframes;
		int[] temp = new int[numberofframes];
		int count = 0;
		for(int x = startframe; x <= endframe; x++)
		{
			temp[count] = x;
			count++;
		}
		this.part.add(temp);
	}
	
	
	@Override
	public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
			int pInitialLoopCount) {
		// TODO Auto-generated method stub
		this.running = true;
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
		this.running = false;
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		// TODO Auto-generated method stub
		this.running = false;
	}
	
	
	
	
}