package com.example.monsheeppractica.sqlite.registros;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.monsheeppractica.sqlite.sqlite;

public class EliminarTabla {


    public int  EliminarCategoria(Context context, int codigo){
        sqlite admin = new sqlite
                (context,"categoria", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("status", "baja");

        int cantid = BaseDeDatos.update("categoria", registro, "id_categoria= " + codigo, null);

        BaseDeDatos.close();


        return  cantid;
    }

    public int  EliminarSeguidor(Context context, int codigo){
        sqlite admin = new sqlite
                (context,"seguir", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("status", "baja");

        int cantid = BaseDeDatos.delete("seguir",  "idSeguir= " + codigo, null);
        if (cantid==1){
           // Toast.makeText(context, "Exito", Toast.LENGTH_SHORT).show();
        }else {
           // Toast.makeText(context, "Falla al eliminar"+codigo, Toast.LENGTH_SHORT).show();
        }
        BaseDeDatos.close();


        return  cantid;
    }

    public int  EliminarComentario(Context context, int codigo){
        sqlite admin = new sqlite
                (context,"comentario", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        int cantid = BaseDeDatos.delete("comentario",  "id_comentario= " + codigo, null);
        if (cantid==1){
            Toast.makeText(context, "Exito", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Falla al eliminar"+codigo, Toast.LENGTH_SHORT).show();
        }
        BaseDeDatos.close();


        return  cantid;
    }
}
