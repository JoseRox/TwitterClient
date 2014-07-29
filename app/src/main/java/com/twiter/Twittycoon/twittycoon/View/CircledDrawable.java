package com.twiter.Twittycoon.twittycoon.View;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * A Drawable that draws an oval with given {@link android.graphics.Bitmap}
 */
public class CircledDrawable extends Drawable {

    private final Bitmap mBitmap;
    private final Paint mPaint;
//    private final RectF mRectF;

    private final int mBitmapWidth;
    private final int mBitmapHeight;

    private Paint mBorderPaint;

    public CircledDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
//        mRectF = new RectF();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
//        mPaint.setColor(Color.BLUE);

        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.BLACK);
        mBorderPaint.setStrokeWidth(2f);

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
    //the bitmap drawn on the canvas

        final BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);

        //depands on the bitmap height and width
        int canvasSize = canvas.getWidth();
        if(canvas.getHeight() < canvasSize)
            canvasSize = canvas.getHeight();

        float borderWidth = getBorderWidth();
        float circleCenter = (canvasSize - (borderWidth * 2)) / 2;
        canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter + borderWidth, mBorderPaint);
        canvas.drawCircle(circleCenter + borderWidth, circleCenter + borderWidth, circleCenter, mPaint);
    }



    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
//        mRectF.set(bounds);
    }

    @Override
    public void setAlpha(int alpha) {
        if (mPaint.getAlpha() != alpha) {
            mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmapWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmapHeight;
    }

    public void setAntiAlias(boolean aa) {
        mPaint.setAntiAlias(aa);
        invalidateSelf();
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        mPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    @Override
    public void setDither(boolean dither) {
        mPaint.setDither(dither);
        invalidateSelf();
    }

    public float getBorderWidth(){return mBorderPaint.getStrokeWidth(); }

    public Bitmap getBitmap() {
        return mBitmap;
    }

}