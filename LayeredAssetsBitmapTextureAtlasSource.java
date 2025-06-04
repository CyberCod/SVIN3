package com.smiths;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.source.BaseTextureAtlasSource;
import org.andengine.util.StreamUtils;
import org.andengine.util.debug.Debug;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Point;

public class LayeredAssetsBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource
{
   private final Context mContext;
   private final String mBaseAssetPath;
   private final ArrayList<AssetLayer> mAssetLayers = new ArrayList<AssetLayer>();

   // ===========================================================
   // Constructors
   // ===========================================================

   public LayeredAssetsBitmapTextureAtlasSource(final Context context, final String pBaseAssetPath)
   {
      this(context, pBaseAssetPath, 0, 0);
   }

   public LayeredAssetsBitmapTextureAtlasSource(final Context context, final String pBaseAssetPath, final int pTexturePositionX, final int pTexturePositionY)
   {
      this(context, pBaseAssetPath, pTexturePositionX, pTexturePositionY, getDimensions(context, BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + pBaseAssetPath));
   }

   public LayeredAssetsBitmapTextureAtlasSource(final Context context, final String pBaseAssetPath, final int pTexturePositionX, final int pTexturePositionY, final Point pSize)
   {
      super(pTexturePositionX, pTexturePositionY, pSize.x, pSize.y);
      this.mContext = context;
      this.mBaseAssetPath = pBaseAssetPath;
//      BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(pBaseAssetPath);
   }
   
   private LayeredAssetsBitmapTextureAtlasSource(final Context context, final String pBaseAssetPath, final ArrayList<AssetLayer> assetLayers, final int pTexturePositionX, final int pTexturePositionY, final int pWidth, final int pHeight)
   {
      super(pTexturePositionX, pTexturePositionY, pWidth, pHeight);
      this.mContext = context;
      this.mBaseAssetPath = pBaseAssetPath;
      for (int i = 0; i < assetLayers.size(); i++)
         mAssetLayers.add(assetLayers.get(i));
   }

   
   public void clearAssetLayer(){
	      mAssetLayers.clear();
	   }
   
   
   public boolean addLayer(final String layerPath, final float xOffset, final float yOffset)
   {
      if (mBaseAssetPath == null)
         return false;
      final Point size = getDimensions(mContext, BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + layerPath);
//      if (mTextureWidth < (size.x + xOffset) || mTextureHeight < (size.y + yOffset))
//         return false;
      mAssetLayers.add(new AssetLayer(layerPath, xOffset, yOffset));
      return true;
   }


   @Override
   public LayeredAssetsBitmapTextureAtlasSource deepCopy()
   {
      return new LayeredAssetsBitmapTextureAtlasSource(this.mContext, this.mBaseAssetPath, 
            this.mAssetLayers, this.mTextureX, this.mTextureY, this.mTextureWidth, this.mTextureHeight);
   }

   // ===========================================================
   // Methods for/from SuperClass/Interfaces
   // ===========================================================

   @Override
   public Bitmap onLoadBitmap(final Config pBitmapConfig)
   {
      final Bitmap image = BitmapFromAsset(mContext, BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + this.mBaseAssetPath);//mBaseAssetPath);
      if (image == null)
         return null;

      final Bitmap bitmap = Bitmap.createBitmap(mTextureWidth, mTextureHeight, Bitmap.Config.ARGB_8888);
      final Canvas canvas = new Canvas(bitmap);
      canvas.drawBitmap(image, 0f, 0f, null);
      for (int i = 0; i < mAssetLayers.size(); i++)
      {
         final AssetLayer al = mAssetLayers.get(i);
         final Bitmap layer = BitmapFromAsset(mContext, BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + al.mAssetPath);
         canvas.drawBitmap(layer, al.mXOffset, al.mYOffset, null);
      }
      return bitmap;
   }

   public Bitmap onLoadBitmap(Config pBitmapConfig, boolean pMutable) {
      return onLoadBitmap(pBitmapConfig);
   }

   // ===========================================================
   // Private Methods
   // ===========================================================

   private Bitmap BitmapFromAsset(final Context context, final String assetPath)
   {
      InputStream in = null;
      Bitmap bitmap = null;
      try
      {
         in = context.getAssets().open(assetPath);
         bitmap = BitmapFactory.decodeStream(in, null, null);
      }
      catch (final IOException e)
      {
         Debug.e("Failed loading Bitmap in BitmapFromAsset. AssetPath: " + assetPath, e);
      }
      finally
      {
         StreamUtils.close(in);
      }
      return bitmap;
   }

   private static Point getDimensions(final Context context, final String pAssetPath)
   {
      final BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
      decodeOptions.inJustDecodeBounds = true;

      InputStream in = null;
      Point size = null;
      try
      {
         in = context.getAssets().open(pAssetPath);
         BitmapFactory.decodeStream(in, null, decodeOptions);
         size = new Point(decodeOptions.outWidth, decodeOptions.outHeight);
      }
      catch (final IOException e)
      {
         Debug.e("Failed loading Bitmap in LayeredAssetsBitmapTextureAtlasSource. AssetPath: " + pAssetPath, e);

      }
      finally
      {
         StreamUtils.close(in);
      }
      return size;
   }

   private class AssetLayer
   {    
      public final String mAssetPath;
      public final float mXOffset;
      public final float mYOffset;
      public AssetLayer(final String assetPath, final float xOffset, final float yOffset)
      {
         mAssetPath = assetPath;
         mXOffset = xOffset;
         mYOffset = yOffset;
      }
   }

}

