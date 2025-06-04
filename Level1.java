package com.smiths;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;

import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;



//level definition
public class Level1 
{
	
	
	public Level1(VertexBufferObjectManager vBOM, Scene scene, ArrayList<Rectangle> collidables )
	{
	collidables.add(new Rectangle(800, 140, 256,8, vBOM));
	collidables.add(new Rectangle(800, 140, 256,8, vBOM));
	collidables.add(new Rectangle((0), (200), 256,8, vBOM));
	collidables.add(new Rectangle((0), (100), 256,8, vBOM));
	collidables.add(new Rectangle((600), (250), 256,8, vBOM));
	collidables.add(new Rectangle((700), (350), 256,8, vBOM));
	collidables.add(new Rectangle((800), (50), 256,8, vBOM));
	collidables.add(new Rectangle((900), (250), 256,8, vBOM));
	collidables.add(new Rectangle((1000), (175), 256,8, vBOM));
	collidables.add(new Rectangle((1100), (100), 256,8, vBOM));
	collidables.add(new Rectangle((1200), (50), 256,8, vBOM));
	collidables.add(new Rectangle((1300), (200), 256,8, vBOM));
	collidables.add(new Rectangle((1400), (150), 256,8, vBOM));
	collidables.add(new Rectangle((1500), (100), 256,8, vBOM));
	collidables.add(new Rectangle((1600), (450), 256,8, vBOM));
	collidables.add(new Rectangle((1700), (250), 256,8, vBOM));
	collidables.add(new Rectangle(-800, 140, 256,8, vBOM));
	collidables.add(new Rectangle((-200), (200), 256,8, vBOM));
	collidables.add(new Rectangle((-200), (100), 256,8, vBOM));
	collidables.add(new Rectangle((-600), (250), 256,8, vBOM));
	collidables.add(new Rectangle((-700), (350), 256,8, vBOM));
	collidables.add(new Rectangle((-800), (50), 256,8, vBOM));
	collidables.add(new Rectangle((-900), (250), 256,8, vBOM));
	collidables.add(new Rectangle((-1000), (175), 256,8, vBOM));
	collidables.add(new Rectangle((-1100), (100), 256,8, vBOM));
	collidables.add(new Rectangle((-1200), (50), 256,8, vBOM));
	collidables.add(new Rectangle((-1300), (200), 256,8, vBOM));
	collidables.add(new Rectangle((-1400), (150), 256,8, vBOM));
	collidables.add(new Rectangle((-1500), (100), 256,8, vBOM));
	collidables.add(new Rectangle((-1600), (450), 256,8, vBOM));
	collidables.add(new Rectangle((-1700), (250), 256,8, vBOM));
	collidables.add(new Rectangle((-1800), (650), 256,8, vBOM));
	collidables.add(new Rectangle((0), (0), 256,8, vBOM));
	collidables.add(new Rectangle((-450), (240), 1024, 8, vBOM));
	collidables.add(new Rectangle((-400), (420), 256,8, vBOM));
	collidables.add(new Rectangle((-350), (590), 512, 8, vBOM));
	collidables.add(new Rectangle((-300), (750), 256,8, vBOM));
	collidables.add(new Rectangle((-250), (900), 512, 8, vBOM));
	collidables.add(new Rectangle((-200), (1040), 256,8, vBOM));
	collidables.add(new Rectangle((-150), (1170), 512, 8, vBOM));
	collidables.add(new Rectangle((-100), (1290), 256,8, vBOM));
	collidables.add(new Rectangle((-500), (1400), 1024, 8, vBOM));
	collidables.add(new Rectangle(-100000, 1500, 262144, 512, vBOM));
	
	for(int i=0; i<collidables.size(); i++)
		{
			scene.attachChild(collidables.get(i));
			collidables.get(i).setColor(Color.BLACK);
		}
	}
	
	
	
	
}
		
		
		
		
		
		