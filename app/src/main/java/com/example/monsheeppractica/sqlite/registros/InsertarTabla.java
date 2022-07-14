package com.example.monsheeppractica.sqlite.registros;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.sqlite;

public class InsertarTabla {
    DatabaseHandler db;

    public void RegistrarCategoria(Context context, int idCategoria, String categoria, String descripcion, String idFoto, String status, String idProducto, String x) {

        db = new DatabaseHandler(context);

        sqlite usuarios = new sqlite(context, "categoria", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        idCategoria = (int)(Math.random()*10000+1*3+15);

        registro.put("id_categoria",idCategoria);
        registro.put("categoria",categoria);
        registro.put("Descripcion", descripcion);
        registro.put("Id_Foto", idFoto.trim());
        registro.put("status",status );
        registro.put("idproducto", idProducto );

        BaseDeDatos.insert("categoria", null, registro);

        BaseDeDatos.close();


        if (!idFoto.isEmpty()){
            if (db.insertimage(x, idFoto, String.valueOf(idCategoria))) {
                Toast.makeText(context, "Registro Exitoso,\nImagen insertada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context, "Registro Exitoso,\nNo guardaste ninguna imagen", Toast.LENGTH_SHORT).show();
        }

    }

    public void RegistrarProducto(Context context, int id_producto, String nombre, String costo, String menudeo, String mayoreo, String marca, String x
    ,String color,String unidad_med, String cat_nombre_sp, String cat_id_sp, String num_id, String status,
                                  String cant_min, String idFotoUser, String idUser, String NombreUser,String idNegocio, String fecha, String hora) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "producto", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        id_producto = (int)(Math.random()*10000+1*3+15);

        registro.put("id_producto",id_producto);
        registro.put("producto",nombre);
        registro.put("costo", costo);
        registro.put("ventaMenudeo", menudeo);
        registro.put("ventaMayoreo",mayoreo);
        registro.put("marca",marca );
        registro.put("color", color);
        registro.put("unidadMedida", unidad_med);
        registro.put("categoria_nombre", cat_nombre_sp);
        registro.put("id_categoria", ""+cat_id_sp);
        registro.put("Id_Foto", num_id.trim());
        registro.put("status", status);
        registro.put("cantidadMinima",""+cant_min);
        registro.put("idFotoUser", idFotoUser);
        registro.put("NombreUser",NombreUser);
        registro.put("idUser", idUser);
        registro.put("idNegocio", idNegocio);
        registro.put("fecha", fecha);
        registro.put("hora",hora);

        int n = (int) BaseDeDatos.insert("producto", null, registro);

        if (n==1){
            Toast.makeText(context, "Exito de datos", Toast.LENGTH_SHORT).show();
        }
        BaseDeDatos.close();

        if (!num_id.isEmpty()){
            if (db.insertimage(x, num_id, String.valueOf(id_producto))) {
                Toast.makeText(context, "Registro Exitoso,\nImagen insertada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context, "Registro Exitoso,\nNo guardaste ninguna imagen", Toast.LENGTH_SHORT).show();
        }

    }

    public void RegistrarUsuario(Context context, int idCliente, String Nombre, String Calle, String Numero,
                                 String Interior, String Codigo_Postal, String Colonia
            ,String Municipio,String Estado, String Alias,
                                 String Lada, String Teléfono, String tipoTelefono, String ApellidoPaterno
            , String ApellidoMaterno, String TipoCompra, String num_id
            , String x, String Contra,String idNegocio) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "clientes", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        idCliente = (int)(Math.random()*10000+1*3+15);

        registro.put("id_cliente", idCliente);
        registro.put("Nombre",Nombre);
        registro.put("Calle", Calle);
        registro.put("Numero", Numero);
        registro.put("Interior",Interior);
        registro.put("Codigo_Postal",Codigo_Postal);
        registro.put("Colonia", Colonia);
        registro.put("Municipio", Municipio);
        registro.put("Estado",Estado );
        registro.put("Alias",Alias );
        registro.put("Lada", Lada);
        registro.put("Teléfono", Teléfono);
        registro.put("status", "Activo");
        registro.put("TipoTelefono",tipoTelefono);
        registro.put("ApellidoPaterno", ApellidoPaterno);
        registro.put("ApellidoMaterno", ApellidoMaterno);
        registro.put("TipoCompra", TipoCompra);
        registro.put("Id_Foto", num_id.trim());
        registro.put("Contra",""+Contra);
        registro.put("idNegocio",""+idNegocio);


        int n = (int) BaseDeDatos.insert("clientes", null, registro);

        if (n==1){
            Toast.makeText(context, "Exito de datos", Toast.LENGTH_SHORT).show();
        }//else Toast.makeText(context,"Falla al registro",Toast.LENGTH_SHORT).show();
        BaseDeDatos.close();


        if (!num_id.isEmpty()){
            if (db.insertimage(x, num_id, String.valueOf(idCliente))) {
                Toast.makeText(context, "Registro Exitoso,\nImagen insertada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public int RegistrarNegocio(Context context, int idNegocio,int idUser, String Nombre, String Calle, String Numero,
                                 String Interior, String Codigo_Postal, String Colonia
                                ,String Municipio,String Estado,
                                 String Lada, String Teléfono, String tipoTelefono,  String TipoCompra, String num_id
                                 , String x, String Contra,String Correo,String Descripcion,String idFotoNegocio) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "negocio", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();



        registro.put("idNegocio", idNegocio);
        registro.put("idUser", idUser);
        registro.put("Nombre",Nombre);
        registro.put("Calle", Calle);
        registro.put("Numero", Numero);
        registro.put("Interior",Interior);
        registro.put("Codigo_Postal",Codigo_Postal);
        registro.put("Colonia", Colonia);
        registro.put("Municipio", Municipio);
        registro.put("Estado",Estado );
        registro.put("Lada", Lada);
        registro.put("Teléfono", Teléfono);
        registro.put("status", "Activo");
        registro.put("TipoTelefono",tipoTelefono);
        registro.put("TipoCompra", TipoCompra);
        registro.put("Id_Foto", num_id.trim());
        registro.put("Contra",""+Contra);
        registro.put("Correo", Correo);
        registro.put("Descripcion",""+Descripcion);
      //  registro.put("idFotoNegocio",""+idFotoNegocio);


        int n = (int) BaseDeDatos.insert("negocio", null, registro);

        if (n==1){
            Toast.makeText(context, "Exito de datos", Toast.LENGTH_SHORT).show();
        }//else Toast.makeText(context,"Falla al registro",Toast.LENGTH_SHORT).show();
        //BaseDeDatos.close();


        if (!num_id.isEmpty()){
            if (db.insertimage(x, num_id, String.valueOf(idNegocio))) {
               // Toast.makeText(context, "Registro Exitoso,\nImagen insertada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        }

        return idNegocio;

    }

    public void RegistrarComentario(Context context, int id_comentario, String comentario, String nombreUser,
                                    String idProducto, String idUser, String nombreProducto, String urlFoto,
                                    String fecha, String hora) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "comentario", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        id_comentario = (int)(Math.random()*10000+1*3+15);//View.generateViewId();

        registro.put("id_comentario", id_comentario);
        registro.put("comentario",comentario);
        registro.put("nombreUser", idUser);
        registro.put("idProducto", idProducto);
        registro.put("idUser",nombreUser);
        registro.put("nombreProducto",nombreProducto);
        registro.put("urlFoto", urlFoto);
        registro.put("status","Activo");
        registro.put("fecha", fecha);
        registro.put("hora",hora);
        int n = (int) BaseDeDatos.insert("comentario", null, registro);

        if (n==1){
            Toast.makeText(context, "Comentario Exitoso", Toast.LENGTH_SHORT).show();
        }//else Toast.makeText(context,"Falla al registro",Toast.LENGTH_SHORT).show();
        BaseDeDatos.close();


    }

    public void RegistrarSeguidor(Context context, int idSeguir, String idUser, String idSeguidor,
                                    String nombreSeguidor, String status ) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "seguir", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        idSeguir = (int)(Math.random()*10000+1*3+15);//View.generateViewId();

        registro.put("idSeguir", idSeguir);
        registro.put("idUser",idUser);
        registro.put("idSeguidor", idSeguidor);
        registro.put("nombreSeguidor",nombreSeguidor);
        registro.put("status","Activo");
        int n = (int) BaseDeDatos.insert("seguir", null, registro);

        if (n==1){
            //Toast.makeText(context, "Comentario Exitoso", Toast.LENGTH_SHORT).show();
        }//else Toast.makeText(context,"Falla al registro",Toast.LENGTH_SHORT).show();
       // BaseDeDatos.close();


    }

    public void RegistrarCarritoTicket(Context context, int idticket, String idUser, String Productos,
                                    String cantidad, String precio, String idproveedor, String status,
                                    String idProducto,String fecha, String hora,String cantidadDisponible) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "carrito", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        //View.generateViewId();

        registro.put("idticket", idticket);
        registro.put("idUser", idUser);
        registro.put("Productos", Productos);
        registro.put("cantidad", cantidad);
        registro.put("precio", precio);
        registro.put("idproveedor", idproveedor);
        registro.put("idProducto", idProducto);
        registro.put("status",status);
        registro.put("fecha", "0");
        registro.put("hora","0");
        registro.put("cantidadDisponible",cantidadDisponible);
        registro.put("solicitud","0");
        registro.put("idDomicilio","0");
        int n = (int) BaseDeDatos.insert("carrito", null, registro);

        if (n==1  ){
            Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show();
        }//else Toast.makeText(context,"Falla al registro",Toast.LENGTH_SHORT).show();
        BaseDeDatos.close();

    }

    public void RegistrarDomicilios(Context context, int idDomicilio, String idUser, String NombreCompleto,
                                       String Domicilio, String Colonia, String Vecindario, String Celular,
                                       String CodigoPostal,String numero, String municipio) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "domicilios", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        idDomicilio = (int)(Math.random()*10000+1*3+15);//View.generateViewId();


        registro.put("idDomicilio", idDomicilio);
        registro.put("idUser", idUser);
        registro.put("NombreCompleto", NombreCompleto);
        registro.put("Domicilio", Domicilio);
        registro.put("Colonia", Colonia);
        registro.put("Vecindario", Vecindario);
        registro.put("Celular", Celular);
        registro.put("CodigoPostal",CodigoPostal);
        registro.put("numero", numero);
        registro.put("municipio",municipio);

        int n = (int) BaseDeDatos.insert("domicilios", null, registro);

        if (n==1  ){
            Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show();
        }//else Toast.makeText(context,"Falla al registro",Toast.LENGTH_SHORT).show();
      //  BaseDeDatos.close();

    }

}
