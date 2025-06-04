package com.smiths;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.util.GLState;

public class ParallaxLayer extends Entity {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private final ArrayList<ParallaxEntity> mParallaxEntities = new ArrayList<ParallaxEntity>();
    private int mParallaxEntityCount;

    
    protected float mParallaxValueX;
    protected float mParallaxScrollValueX;
    protected float mParallaxChangePerSecondX;
   
    protected float mParallaxValueY;
    protected float mParallaxScrollValueY;
    protected float mParallaxChangePerSecondY;
   

    

    protected float mParallaxScrollFactorX = 0.2f;
   

    protected float mParallaxScrollFactorY = 0.2f;
   
    private Camera mCamera;
   
    

    private float mCameraPreviousPosX;
    private float mCameraOffsetPosX;
   

    private float mCameraPreviousPosY;
    private float mCameraOffsetPosY;
    
    private boolean mIsScrollableX = false;
	private boolean mIsScrollableY = false;
	
	private int mLevelScaleX = 0;
	private int mLevelScaleY = 0;
	
   
    // ===========================================================
    // Constructors
    // ===========================================================
    public ParallaxLayer() {
    }

    public ParallaxLayer(final Camera camera, final boolean mIsScrollableX,final boolean mIsScrollableY){
            this.mCamera = camera;
            this.mIsScrollableX = mIsScrollableX;
            this.mIsScrollableY = mIsScrollableY;
            mCameraPreviousPosX = camera.getCenterX();
            mCameraPreviousPosY = camera.getCenterY();
    }
   
    public ParallaxLayer(final Camera camera, final boolean mIsScrollableX,final boolean mIsScrollableY, final int mLevelScaleX,final int mLevelScaleY){
            this.mCamera = camera;
            this.mIsScrollableX = mIsScrollableX;
            this.mIsScrollableX = mIsScrollableY;
            this.mLevelScaleX = mLevelScaleX;
            this.mLevelScaleY = mLevelScaleY;
            mCameraPreviousPosX = camera.getCenterX();
            mCameraPreviousPosY = camera.getCenterY();
    }
   
    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public void setParallaxValueX(final float pParallaxValueX) {
        this.mParallaxValueX = pParallaxValueX;
    }
   
    public void setParallaxValueY(final float pParallaxValueY) {
        this.mParallaxValueY = pParallaxValueY;
}

    public void setParallaxChangePerSecondX(final float pParallaxChangePerSecondX) {
        this.mParallaxChangePerSecondX = pParallaxChangePerSecondX;
    }

    public void setParallaxChangePerSecondY(final float pParallaxChangePerSecondY) {
        this.mParallaxChangePerSecondY = pParallaxChangePerSecondY;
}

    
    
    public void setParallaxScrollFactorX(final float pParallaxScrollFactorX){
        this.mParallaxScrollFactorX = pParallaxScrollFactorX;
    }
   
    public void setParallaxScrollFactorY(final float pParallaxScrollFactorY){
        this.mParallaxScrollFactorY = pParallaxScrollFactorY;
}

    
    
    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public void onManagedDraw(GLState pGLState, Camera pCamera) {
            super.preDraw(pGLState, pCamera);

           
            final float parallaxValueX = this.mParallaxValueX;
            final float parallaxScrollValueX = this.mParallaxScrollValueX;
            final float parallaxValueY = this.mParallaxValueY;
            final float parallaxScrollValueY = this.mParallaxScrollValueY;
            float x;
            float y;
            final ArrayList<ParallaxEntity> parallaxEntities = this.mParallaxEntities;
            /*
            for(int i = 0; i < this.mParallaxEntityCount; i++) {
                    if(parallaxEntities.get(i).mIsScrollable){
                            parallaxEntities.get(i).onDraw(pGLState, pCamera, parallaxScrollValue, mLevelScale, mParallaxOnAxisX);
                    } else {
                            parallaxEntities.get(i).onDraw(pGLState, pCamera, parallaxValue, mLevelScale, mParallaxOnAxisX);
                    }

            }
            */
            
            for(int i = 0; i < this.mParallaxEntityCount; i++) {
            	 if(parallaxEntities.get(i).mIsScrollableX == true)
            	 {
            		 x = parallaxScrollValueX;
            	 }
            	 else
            	 {
            		 x = parallaxValueX;
            	 }

            	 if (parallaxEntities.get(i).mIsScrollableY == true)
            	 {
            		 y = parallaxScrollValueY;
            	 }
            	 else
            	 {
            		 y = parallaxValueY;
            	 }

            	 parallaxEntities.get(i).onDraw(pGLState, pCamera, x, y, mLevelScaleX, mLevelScaleY);
            	 }
    }
   
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
           
            final float cameraCenterPosX = this.mCamera.getCenterX();
            final float cameraCenterPosY = this.mCamera.getCenterY();
            
            if(mIsScrollableX && mCameraPreviousPosX != cameraCenterPosX){
                            mCameraOffsetPosX = mCameraPreviousPosX - cameraCenterPosX;
                            mCameraPreviousPosX = cameraCenterPosX;
                           
                            this.mParallaxScrollValueX += mCameraOffsetPosX * this.mParallaxScrollFactorX;
                            mCameraOffsetPosX = 0;
            }
           
            this.mParallaxValueX += this.mParallaxChangePerSecondX * pSecondsElapsed;
            
            

            if(mIsScrollableY && mCameraPreviousPosY != cameraCenterPosY){
                            mCameraOffsetPosY = mCameraPreviousPosY - cameraCenterPosY;
                            mCameraPreviousPosY = cameraCenterPosY;
                           
                            this.mParallaxScrollValueY += mCameraOffsetPosY * this.mParallaxScrollFactorY;
                            mCameraOffsetPosY = 0;
            }
           
            this.mParallaxValueY += this.mParallaxChangePerSecondY * pSecondsElapsed;
           
            
            
            
            super.onManagedUpdate(pSecondsElapsed);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public void attachParallaxEntity(final ParallaxEntity parallaxEntity) {
            this.mParallaxEntities.add(parallaxEntity);
            this.mParallaxEntityCount++;
    }

    public boolean detachParallaxEntity(final ParallaxEntity pParallaxEntity) {
            this.mParallaxEntityCount--;
            final boolean success = this.mParallaxEntities.remove(pParallaxEntity);
            if(!success) {
                    this.mParallaxEntityCount++;
            }
            return success;
    }
   
    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    public static class ParallaxEntity {
            // ===========================================================
            // Constants
            // ===========================================================

            // ===========================================================
            // Fields
            // ===========================================================

            final float mParallaxFactorX;
            final float mParallaxFactorY;
            
            final IAreaShape mAreaShape;
            final boolean mIsScrollableX;
            final boolean mIsScrollableY;
            final float shapeScaledX;
            final float shapeScaledY;

            // ===========================================================
            // Constructors
            // ===========================================================
/*
            public ParallaxEntity(final float pParallaxFactorX,final float pParallaxFactorY, final IAreaShape pAreaShape) {
                    this.mParallaxFactorX = pParallaxFactorX;
                    this.mAreaShape = pAreaShape;
                    this.mIsScrollableX = false;
                    this.mIsScrollableY = false;
                    this.mParallaxFactorY = pParallaxFactorY;
                    
                    
                    shapeScaledX = this.mAreaShape.getWidthScaled() ;
                    shapeScaledY =  this.mAreaShape.getHeightScaled();
                    
            }
  */         
            public ParallaxEntity( final float pParallaxFactorX,final float pParallaxFactorY,final IAreaShape pAreaShape, final boolean mIsScrollableX, final boolean mIsScrollableY) {
            	this.mParallaxFactorX = pParallaxFactorX;
                this.mAreaShape = pAreaShape;
                this.mIsScrollableX = mIsScrollableX;
                this.mParallaxFactorY = pParallaxFactorY;
                
                this.mIsScrollableY = mIsScrollableY;
                this.shapeScaledX = this.mAreaShape.getWidthScaled() ;
                this.shapeScaledY = this.mAreaShape.getHeightScaled();
                                    
            }
           
            public ParallaxEntity(final float pParallaxFactorX,final float pParallaxFactorY, final IAreaShape pAreaShape, final boolean mIsScrollableX,final boolean mIsScrollableY, final int mReduceFrequency) {
            	this.mParallaxFactorX = pParallaxFactorX;
            	this.mParallaxFactorY = pParallaxFactorY;
                this.mAreaShape = pAreaShape;
                this.mIsScrollableX = mIsScrollableX;
                this.mIsScrollableY = mIsScrollableY;
                this.shapeScaledX = this.mAreaShape.getWidthScaled() * mReduceFrequency ;
                this.shapeScaledY = this.mAreaShape.getHeightScaled() * mReduceFrequency;
            }

            // ===========================================================
            // Getter & Setter
            // ===========================================================

            // ===========================================================
            // Methods for/from SuperClass/Interfaces
            // ===========================================================

            // ===========================================================
            // Methods
            // ===========================================================

            public void onDraw(final GLState pGLState, final Camera pCamera, final float pParallaxValueX, final float pParallaxValueY, final float mLevelScaleX, final float mLevelScaleY) {
                    pGLState.pushModelViewGLMatrix();
                    {
                            float Xrange;
                            float Yrange;
                            
                            if(mLevelScaleX != 0){
                                    Xrange = mLevelScaleX;
                                    
                            } else {
                                    Xrange = pCamera.getWidth();
                                    
                            }


                            if(mLevelScaleY != 0){
                                    
                                    Yrange = mLevelScaleY;
                            } else {
                                    
                                    Yrange = pCamera.getHeight();
                            }

                            
                            
                            
                            float baseOffsetX = (pParallaxValueX * this.mParallaxFactorX) % shapeScaledX;
                            float baseOffsetY = (pParallaxValueY * this.mParallaxFactorY) % shapeScaledY;

                            
                            while(baseOffsetX > 0) {
                                    baseOffsetX -= shapeScaledX;
                            }
                            
                            while(baseOffsetY > 0) {
                                baseOffsetY -= shapeScaledY;
                            }
                            
                            pGLState.translateModelViewGLMatrixf(baseOffsetX, baseOffsetY, 0);

                            float currentMaxDimensionX = baseOffsetX;
                            float currentMaxDimensionY = baseOffsetY;
                           
                            do {
                                    this.mAreaShape.onDraw(pGLState, pCamera);
                                    pGLState.translateModelViewGLMatrixf(shapeScaledX - 1, 0, 0);
                                    
                                   
                                    currentMaxDimensionX += shapeScaledX;
                            } while(currentMaxDimensionX < Xrange);
                    
                  
                    
                    
                    
		                    do {
		                        this.mAreaShape.onDraw(pGLState, pCamera);
		                        pGLState.translateModelViewGLMatrixf(0, shapeScaledY - 1, 0);
		                        currentMaxDimensionY += shapeScaledY;
		                    } while(currentMaxDimensionY < Yrange);
            		}
                    pGLState.popModelViewGLMatrix();
            }

            // ===========================================================
            // Inner and Anonymous Classes
            // ===========================================================
    }


}