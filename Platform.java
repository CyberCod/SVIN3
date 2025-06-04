	package com.smiths;

import java.util.ArrayList;
import java.util.Map;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.vbo.IRectangleVertexBufferObject;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;


public class Platform extends Rectangle
	{
	public Platform(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager vbom) {
		super(pX, pY, pWidth, pHeight, vbom);
		// TODO Auto-generated constructor stub
	}
	int xpos;
	int ypos;
	int length;
	Color color;
	int platformNumber;
	int type;

	
	
	
	
}