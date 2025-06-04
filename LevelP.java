	package com.smiths;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;

import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;



//level definition
public class LevelP
{
	
	
	
	public LevelP(VertexBufferObjectManager vBOM, Scene scene, ArrayList<Rectangle> horizontals, ArrayList<Rectangle> verticals )
	{
		verticals.add(new Rectangle(-1000,-1000,1000,1500,vBOM));
		verticals.add(new Rectangle(500,-1000,850,1500,vBOM));
		horizontals.add(new Rectangle(-1000, 300, 20000, 512, vBOM));
		horizontals.add(new Rectangle(150,150,150, 10, vBOM));
		
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
		
		
		
		
		
			
			
		