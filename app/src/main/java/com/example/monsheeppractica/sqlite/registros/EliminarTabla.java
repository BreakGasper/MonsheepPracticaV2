package com.example.monsheeppractica.sqlite.registros;

import static com.example.monsheeppractica.mytools.Network.isNetDisponible;
import static com.example.monsheeppractica.mytools.Network.isOnlineNet;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.R;
import com.example.monsheeppractica.WebService.wsEdit;
import com.example.monsheeppractica.sqlite.sqlite;

public class EliminarTabla {


    public int EliminarCategoria(Context context, int codigo) {
        sqlite admin = new sqlite
                (context, "monsheep", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("status", "baja");

        int cantid = 0;
        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            cantid = BaseDeDatos.update("categoria", registro, "id_categoria= " + codigo, null);
            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("delete_Categria.php?id_categoria=" + codigo);
            if (cantid == 1) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Eliminado correctamente");

                Toast toast = new Toast(context);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
            }
            BaseDeDatos.close();

        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }
        return cantid;
    }

    public int EliminarSeguidor(Context context, int codigo) {
        sqlite admin = new sqlite
                (context, "monsheep", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("status", "baja");

        int cantid = 0;
        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            cantid = BaseDeDatos.delete("seguir", "idSeguir= " + codigo, null);
            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("delete_Seguidor.php?idSeguir=" + codigo);
            if (cantid == 1) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Eliminado correctamente");


                Toast toast = new Toast(context);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
                // Toast.makeText(context, "Exito", Toast.LENGTH_SHORT).show();
            } else {
                // Toast.makeText(context, "Falla al eliminar"+codigo, Toast.LENGTH_SHORT).show();
            }
            BaseDeDatos.close();


        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }
        return cantid;
    }

    public int EliminarComentario(Context context, int codigo) {
        sqlite admin = new sqlite
                (context, "monsheep", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        int cantid = 0;
        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            cantid = BaseDeDatos.delete("comentario", "id_comentario= " + codigo, null);
            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("delete_Comentario.php?id_comentario=" + codigo);
            if (cantid == 1) {
//            Toast.makeText(context, "Exito", Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);
                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Eliminado correctamente");
                Toast toast = new Toast(context);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
            } else {
//            Toast.makeText(context, "Falla al eliminar"+codigo, Toast.LENGTH_SHORT).show();
            }
            BaseDeDatos.close();
        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

        return cantid;
    }

    public int EliminarProductoCarrito(Context context, String codigo) {
        sqlite admin = new sqlite
                (context, "monsheep", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        int cantid = 0;

        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            cantid = BaseDeDatos.delete("carrito", "idProducto= " + codigo, null);
            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("delete_Carrito.php?idProducto=" + codigo);
            if (cantid == 1) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);
                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Eliminado correctamente");
                Toast toast = new Toast(context);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
            } else {
                // Toast.makeText(context, "Falla al eliminar"+codigo, Toast.LENGTH_SHORT).show();
            }
            BaseDeDatos.close();
        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

        return cantid;
    }

}
