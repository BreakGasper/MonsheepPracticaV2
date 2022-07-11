package com.example.monsheeppractica.sqlite.registros;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.monsheeppractica.sqlite.sqlite;

public class EditarBaja {
    Context context;

    public EditarBaja(Context context) {
        this.context = context;
    }

    public void BajaProducto( int id_producto){
        sqlite admin = new sqlite
                (context,"producto", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("id_categoria", "baja");
        registro.put("status", "baja");

        int cantid = BaseDeDatos.update("producto", registro, "id_producto= " + id_producto, null);

        BaseDeDatos.close();

        if ( cantid == 1) {
            //aTrabajar();
            Toast.makeText(context, "Eliminada correctamente", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Categoria no existe", Toast.LENGTH_SHORT).show();
        }

    }
}
