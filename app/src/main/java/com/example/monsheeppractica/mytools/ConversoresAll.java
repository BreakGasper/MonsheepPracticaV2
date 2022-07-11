package com.example.monsheeppractica.mytools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ConversoresAll {

    //Converitr Bitmap a String
    public String convertirImgString(Bitmap bitmap){
        ByteArrayOutputStream array= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imageByte = array.toByteArray();
        String imageString =  Base64.encodeToString(imageByte, Base64.DEFAULT);

        return  imageString;
    }

    //method to convert your text to image
    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}
