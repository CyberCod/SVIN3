	package com.smiths;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;

import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;



//level definition
public class LevelSlope
{
	
	
	
	public LevelSlope(VertexBufferObjectManager VBO, Scene scene, ArrayList<Rectangle> horizontals, ArrayList<Rectangle> verticals )
	{
		verticals.add(new Rectangle(-1000,-1000,1000,1500,VBO));
		verticals.add(new Rectangle(500,-1000,850,1500,VBO));
		horizontals.add(new Rectangle(-1000, 300, 20000, 512, VBO));
		
		
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
		
		

		
		Rectangle spun = new Rectangle(250,200,200,200, VBO);
		horizontals.add(spun);
		spun.setRotationCenter(100, 100);
		spun.setRotation(45);
		scene.attachChild(spun);
		spun.setColor(Color.BLACK);

		
		
		
		
	}
	
	
}
		
		
		
		
		
			
			
		