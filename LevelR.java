	package com.smiths;

import java.util.ArrayList;
import java.util.Map;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;



//level definition
public class LevelR
{
	static int platforms = 20;
	static int VerticalIncrement = 64;
	static int HorizontalIncrement = 100;
	int platformcount = 0;
	int height = 10;
	static Platform[] platformgroup;
	VertexBufferObjectManager vbom;
	ArrayList<Rectangle> horiz;
	Color col;
	Scene SCENE;
	String LevelString = "";
	
	public  LevelR(VertexBufferObjectManager vBOM, Scene scene, ArrayList<Rectangle> horizontals, ArrayList<Rectangle> verticals, Color color )
	{
		SCENE = scene;
		col = color;
		vbom = vBOM;
		horiz = horizontals;
		
		platformgroup = new Platform[platforms];
		verticals.add(new Rectangle(-1000,-1000,1000,1500,vBOM)); //left wall
		verticals.add(new Rectangle(2000,-1000,850,1500,vBOM)); //right wall
		horizontals.add(new Rectangle(-2000, 300, 6000, 512, vBOM));  //main floor
		//horizontals.add(new Rectangle(150,150,150, 10, vBOM));
		
		
		int VerticalRange = 10;
		int HorizontalRange = 20;
		int xpos;
		int ypos;
		int length;
		
		int Xgridstart = 0;
		int Ygridstart = -200;
		int segments;
		for(int r=0; r < platforms; r++)
		{
			
			xpos = ((int) (Math.random()*HorizontalRange)*HorizontalIncrement)+Xgridstart;
			ypos = ((int) (Math.random()*VerticalRange)*VerticalIncrement)+Ygridstart;
			segments = (int)(Math.random()*3);
			length = HorizontalIncrement+(segments*HorizontalIncrement);
			LevelString = LevelString+length+"|"+Integer.toString(xpos)+"|"+Integer.toString(ypos)+"|";
			
			
			//horizontals.add(new Rectangle(xpos, ypos, length, height, vBOM));
		}
		
		Debug.d("LevelString = "+LevelString);
		StringToLevel(LevelString, horizontals, scene, color);
		
		for(int i=0; i<horizontals.size(); i++)
		{
			scene.attachChild(horizontals.get(i));
			horizontals.get(i).setColor(color);
		}
		
		for(int i=0; i<verticals.size(); i++)
		{
			scene.attachChild(verticals.get(i));
			verticals.get(i).setColor(color);
		}
	
		
		
		
		
		
		
	}
	
	
	
public void StringToLevel(String args, ArrayList<Rectangle> horizontals, Scene scene, Color color) {
        
        //String str = "e|1000|-200|c|1100|120|c|1300|-72|a|100|56|c|1100|376|c|500|120|b|1100|-72|c|400|56|e|1600|-72|e|1500|-72|c|800|56|a|1600|120|c|400|-200|e|1500|-72|b|700|56|b|1200|-72|e|0|-200|b|300|120|b|0|-136|b|1500|-200|";
        int threecount = 1;
        int xpos=0;
        int ypos=0;
        int length=0;
        for(int index = 0;index<platforms*3;index++){
        
        	
        	String piece =  str_piece(args, '|',  index+1);
        	Debug.d("index ="+index+", piece = "+piece+", threecount = "+threecount+", parsed = "+Integer.parseInt(piece)+", platformcount = "+platformcount);

        	if(threecount == 1){
        			
        		length = (int)Integer.parseInt(piece);
        		
        	}
        	else if(threecount==2){
        		xpos = Integer.parseInt(piece);
        	}
        	else if(threecount == 3){
        		ypos = Integer.parseInt(piece);
        	}
        	threecount++;
        	if(threecount==4)
        	{
        		
        		MakePlatform(platformcount,length, xpos,ypos, horizontals, scene, color);
        		threecount=1;
        	}
        	
        }
    }  
  
public void MakePlatform(int index, int l, int x, int y, ArrayList<Rectangle> horizontals, Scene scene, Color color){
	
	platformgroup[platformcount]=new Platform((float)x, (float)y, (float)l, height, vbom);
	horizontals.add(platformgroup[platformcount]);
	
	platformgroup[platformcount].setColor(color);
	platformgroup[platformcount].setVisible(true);
	
	//scene.attachChild(platformgroup[platformcount]);
	
	platformcount++;
}

    
    private static String str_piece(String str, char separator, int index) {
        String str_result = "";
        int count = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == separator) {
                count++;
                if(count == index) {
                    break;
                }
            }
            else {
                if(count == index-1) {
                    str_result += str.charAt(i);
                }
            }
        }
        return str_result;
    }
	
}
		
		
		
		
		
			
			
		