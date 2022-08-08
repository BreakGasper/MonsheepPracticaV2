package com.example.monsheeppractica.sqlite.registros;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.monsheeppractica.WebService.wsInsert;
import com.example.monsheeppractica.sqlite.sqlite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class InsertarImagenes {
    Context context ;
    wsInsert tab;
    public InsertarImagenes(Context context) {
        this.context = context;
    }
    public  Boolean insertimage(String x, String i, String id){

        try {
            sqlite usuarios = new sqlite(context, "monsheep", null, 1);
            SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

            ContentValues registro = new ContentValues();

            FileInputStream file = new FileInputStream(x);
            byte[] imgbyte = new byte[file.available()];
            file.read(imgbyte);

            registro.put("id", i);
            registro.put("img", imgbyte);
            registro.put("id_Catego", id);
            BaseDeDatos.insert("images_categoria", null, registro);

            ArrayList listata = new ArrayList();
            listata.add(imgbyte);
            listata.add(id);
            ArrayList lista = new ArrayList();
            lista.add("img");
            lista.add("id_Catego");
            tab = new wsInsert(context);
            tab.enlase_base_de_datos(lista,listata,"insert_img.php");

            file.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
