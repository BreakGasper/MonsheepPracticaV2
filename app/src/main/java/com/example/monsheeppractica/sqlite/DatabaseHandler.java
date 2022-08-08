package com.example.monsheeppractica.sqlite;

import static com.example.monsheeppractica.WebService.wsDataDownload.NombreTablas;
import static com.example.monsheeppractica.WebService.wsDataDownload.ip;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.monsheeppractica.WebService.wsDataDownload;
import com.example.monsheeppractica.WebService.wsInsert;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    wsInsert tab;
    Context context;
    Bitmap bmp;
    String Stimagen;

    public DatabaseHandler(Context context) {
        super(context, "monsheep", null, 1);
        tab = new wsInsert(context);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table images_categoria(id_foto int, img blob not null, id_Catego text, id int primary key)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists images_categoria");
    }

    //insert a image
    //x = img location
    public Boolean insertimage(String x, String i, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Uri myUri = Uri.parse(x);
        InputStream imageStream = null;
        try {
            imageStream = context.getContentResolver().openInputStream(
                    myUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(), myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Transformamos la URI de la imagen a inputStream y este a un Bitmap
        bmp = BitmapFactory.decodeStream(imageStream);

        Stimagen = convertirImgString(bmp);
/*
//            FileInputStream file = new FileInputStream(x);
//            byte[] imgbyte = new byte[file.available()];
//            file.read(imgbyte);

//            ContentValues contentValues = new ContentValues();
//            contentValues.put("id_foto", i);
//            contentValues.put("id", i);
//            contentValues.put("img", imgbyte);
//            contentValues.put("id_Catego", id);
//            db.insert("images_categoria", null, contentValues);
*/
        ArrayList listata = new ArrayList();
        listata.add("" + i);
        listata.add(Stimagen);
        listata.add(id);
        ArrayList lista = new ArrayList();
        lista.add("id_foto");
        lista.add("img");
        lista.add("id_Catego");

        tab.enlase_base_de_datos(lista, listata, "insert_img.php");

//            file.close();

//            for (int z = 0; z < NombreTablas().size(); z++) {
//
//
//                sqlite admin = new sqlite(context, "monsheep", null, 1);
//                SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
//                BaseDeDatos.delete(NombreTablas().get(z).toString(), "", null);
//
//                wsDataDownload download = new wsDataDownload(context);
//                download.cargarWebService(NombreTablas().get(z).toString());
//                //System.out.println(NombreTablas().get(i).toString());"clientes"
//            }
        return true;

    }

    private String convertirImgString(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, array);
        byte[] imageByte = array.toByteArray();
        String imageString = Base64.encodeToString(imageByte, Base64.DEFAULT);

        return imageString;
    }

    public Boolean deleteImage(String x, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("images_categoria", "id_Catego='" + id + "'", null);

        return true;

    }


    //retrive image
    public Bitmap getimagex(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("select * from images_categoria where id = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToNext()) {
            byte[] imag = cursor.getBlob(1);
            bt = BitmapFactory.decodeByteArray(imag, 0, imag.length);

        }
        return bt;
    }

    public Bitmap getimageID(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("select * from images_categoria where id_Catego = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToNext()) {
            byte[] imag = cursor.getBlob(1);
            bt = BitmapFactory.decodeByteArray(imag, 0, imag.length);

        }
        return bt;
    }

    public String getImagen(String ruta){
        String path= "https://monsheep.000webhostapp.com/Moonsheep/imagenes/"+ ruta;
        //Toast.makeText(context, ""+path+ ruta, Toast.LENGTH_SHORT).show();

        return path;
    }

}
