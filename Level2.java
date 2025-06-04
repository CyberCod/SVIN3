	package com.smiths;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;

import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;



//level definition
public class Level2 
{
	
	
	
	public Level2(VertexBufferObjectManager vBOM, Scene scene, ArrayList<Rectangle> horizontals, ArrayList<Rectangle> verticals )
	{
		verticals.add(new Rectangle(-1000,-1000,850,1500,vBOM));
		verticals.add(new Rectangle(2000,-1000,850,1500,vBOM));
		horizontals.add(new Rectangle(-1000, 300, 20000, 512, vBOM));
		for(int l=0; l<5; l++)
		{
			int X = 400 * l;
			horizontals.add(new Rectangle((X+200),-200,100,12,vBOM));
			
			horizontals.add(new Rectangle(X, 155, 256,12, vBOM));
			horizontals.add(new Rectangle(X+196, 180, 64,12, vBOM));
			if(l<4)
			{
			horizontals.add(new Rectangle((X*2),-50,600,12,vBOM));
			}
		}
		
		for(int i=0; i<horizontals.size(); i++)
		{
			scene.attachChild(horizontals.get(i));
			horizontals.get(i).setColor(Color.BLACK);
		}
		for(int i=0; i<verticals.size(); i++)
		{
			scene.attachChild(verticals.get(i));
			verticals.get(i).setColor(Color.BLACK);
		}
		
		
		
		
		
		
		
	}
	
	
}
		
		
		
		
		
			
			
		