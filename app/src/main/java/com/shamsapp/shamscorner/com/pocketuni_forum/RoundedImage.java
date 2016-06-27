package com.shamsapp.shamscorner.com.pocketuni_forum;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

/**
 * Created by shamim on 07-Jun-16.
 */
public class RoundedImage {

    private Resources mResources;
    public RoundedImage(Context context, ImageView mImageView, int id){
        mResources = context.getResources();
        Bitmap mBitmap = BitmapFactory.decodeResource(mResources,id);
        mImageView.setImageBitmap(mBitmap);
        RoundedBitmapDrawable drawable = createRoundedBitmapDrawableWithBorder(mBitmap);
        mImageView.setImageDrawable(drawable);
    }

    public RoundedBitmapDrawable createRoundedBitmapDrawableWithBorder(Bitmap bitmap) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int borderWidthHalf = 10; // In pixels
        int bitmapRadius = Math.min(bitmapWidth, bitmapHeight) / 2;
        int bitmapSquareWidth = Math.min(bitmapWidth, bitmapHeight);
        int newBitmapSquareWidth = bitmapSquareWidth + borderWidthHalf;
        Bitmap roundedBitmap = Bitmap.createBitmap(newBitmapSquareWidth, newBitmapSquareWidth, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundedBitmap);
        canvas.drawColor(Color.RED);
        int x = borderWidthHalf + bitmapSquareWidth - bitmapWidth;
        int y = borderWidthHalf + bitmapSquareWidth - bitmapHeight;
        canvas.drawBitmap(bitmap, x, y, null);
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidthHalf * 2);
        borderPaint.setColor(Color.rgb(255, 115, 163));
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getWidth() / 2, newBitmapSquareWidth / 2, borderPaint);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mResources, roundedBitmap);
        roundedBitmapDrawable.setCornerRadius(bitmapRadius);
        roundedBitmapDrawable.setAntiAlias(true);
        return roundedBitmapDrawable;
    }
}
