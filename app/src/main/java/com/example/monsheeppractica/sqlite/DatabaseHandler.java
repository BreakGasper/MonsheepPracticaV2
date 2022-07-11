package com.example.monsheeppractica.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, "imageDb.db", null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table images_categoria(id text primary key, img blob not null, id_Catego text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists images_categoria");
    }

    //insert a image
    //x = img location
    public  Boolean insertimage(String x, String i, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            FileInputStream file = new FileInputStream(x);
            byte[] imgbyte = new byte[file.available()];
            file.read(imgbyte);

            ContentValues contentValues = new ContentValues();
            contentValues.put("id", i);
            contentValues.put("img", imgbyte);
            contentValues.put("id_Catego", id);
            db.insert("images_categoria", null, contentValues);

            file.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    //retrive image
    public Bitmap getimage(String id){

        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("select * from images_categoria where id = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToNext()){
                byte [] imag = cursor.getBlob(1);
                bt = BitmapFactory.decodeByteArray(imag, 0, imag.length);

        }
        return  bt;
    }


}
