package com.example.monsheeppractica.sqlite.registros;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.sqlite;

public class EditarBaja {
    Context context;

    public EditarBaja(Context context) {
        this.context = context;
    }

    public void BajaProducto( int id_producto){
        sqlite admin = new sqlite
                (context,"monsheep", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("id_categoria", "baja");
        registro.put("status", "baja");

        int cantid = BaseDeDatos.update("producto", registro, "id_producto= " + id_producto, null);

        BaseDeDatos.close();

        if ( cantid == 1) {
            //aTrabajar();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

            TextView text = (TextView) viewInput.findViewById(R.id.text12);
            text.setText("se ha dado de baja");

            Toast toast = new Toast(context);
            //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(viewInput);
            toast.show();
        }else {
            Toast.makeText(context, "Categoria no existe", Toast.LENGTH_SHORT).show();
        }

    }
}
