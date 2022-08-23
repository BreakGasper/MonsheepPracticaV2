package com.example.monsheeppractica.sqlite.registros;

import static com.example.monsheeppractica.WebService.wsDataDownload.NombreTablas;
import static com.example.monsheeppractica.mytools.Network.isNetDisponible;
import static com.example.monsheeppractica.mytools.Network.isOnlineNet;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.WebService.wsDataDownload;
import com.example.monsheeppractica.WebService.wsInsert;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.sqlite;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;

public class InsertarTabla {
    DatabaseHandler db;
    wsInsert tab;

    public void RegistrarCategoria(Context context, int idCategoria, String categoria, String descripcion, String idFoto, String status, String idProducto, String x) {

        db = new DatabaseHandler(context);

        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        idCategoria = (int) (Math.random() * 10000 + 1 * 3 + 15);

        registro.put("id_categoria", idCategoria);
        registro.put("categoria", categoria);
        registro.put("Descripcion", descripcion);
        registro.put("Id_Foto", idFoto.trim());
        registro.put("status", status);
        registro.put("idproducto", idProducto);

        ArrayList list = new ArrayList();
        list.add("id_categoria");
        list.add("categoria");
        list.add("Descripcion");
        list.add("Id_Foto");
        list.add("status");
        list.add("idproducto");
        ArrayList listdata = new ArrayList();
        listdata.add(idCategoria);
        listdata.add(categoria);
        listdata.add(descripcion);
        listdata.add(idFoto);
        listdata.add(status);
        listdata.add(idProducto);

        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            BaseDeDatos.insert("categoria", null, registro);
            BaseDeDatos.close();
            tab = new wsInsert(context);
            tab.enlase_base_de_datos(list, listdata, "insert_categorias.php");
            if (!idFoto.isEmpty()) {
                if (db.insertimage(x, idFoto, String.valueOf(idCategoria))) {
//                Toast.makeText(context, "Registro Exitoso,\nImagen insertada", Toast.LENGTH_SHORT).show();
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                    TextView text = (TextView) viewInput.findViewById(R.id.text12);
                    text.setText("Registro Exitoso");

                    Toast toast = new Toast(context);
                    //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(viewInput);
                    toast.show();
                } else {
                    //Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Registro Exitoso");

                Toast toast = new Toast(context);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
//            Toast.makeText(context, "Registro Exitoso,\nNo guardaste ninguna imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }


    }

    public void RegistrarPromocion(Context context, Activity mactivity, int id,
                                   String fecha, String hora, String status,
                                   String oferta, String dato) {

        db = new DatabaseHandler(context);

        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        id = (int) (Math.random() * 10000 + 1 * 3 + 15);
        id = id + (int) (Math.random() * 1000);

        registro.put("idPromo", id);
        registro.put("fecha", fecha);
        registro.put("Hora", hora);
        registro.put("oferta", oferta);
        registro.put("status", status);
        registro.put("dato", dato);

        ArrayList list = new ArrayList();
        list.add("idPromo");
        list.add("fecha");
        list.add("Hora");
        list.add("oferta");
        list.add("status");
        list.add("dato");
        ArrayList listdata = new ArrayList();
        listdata.add(id);
        listdata.add(fecha);
        listdata.add(hora);
        listdata.add(oferta);
        listdata.add(status);
        listdata.add(dato);

        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            BaseDeDatos.insert("roll", null, registro);
            BaseDeDatos.close();

            tab = new wsInsert(context);
            tab.enlase_base_de_datos(list, listdata, "insert_roll.php");

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewInput = inflater.inflate(R.layout.toast_layout, null, false);
            TextView text = (TextView) viewInput.findViewById(R.id.text12);
            text.setText("Registro Exitoso");
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(viewInput);
            toast.show();
        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

    }


    public void RegistrarProducto(Context context, int id_producto, String nombre, String costo,
                                  String menudeo, String mayoreo, String marca, String x
            , String color, String unidad_med, String cat_nombre_sp, String cat_id_sp,
                                  String num_id, String status,
                                  String cant_min, String idFotoUser, String idUser,
                                  String NombreUser, String idNegocio, String fecha, String hora) {
        SharedPreferences preferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
//        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
//        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        id_producto = (int) (Math.random() * 10000 + 1 * 3 + 15);

        registro.put("id_producto", id_producto);
        registro.put("producto", nombre);
        registro.put("costo", costo);
        registro.put("ventaMenudeo", menudeo);
        registro.put("ventaMayoreo", mayoreo);
        registro.put("marca", marca);
        registro.put("color", color);
        registro.put("unidadMedida", unidad_med);
        registro.put("categoria_nombre", cat_nombre_sp);
        registro.put("id_categoria", "" + cat_id_sp);
        registro.put("Id_Foto", num_id.trim());
        registro.put("status", status);
        registro.put("cantidadMinima", "" + cant_min);
        registro.put("idFotoUser", "0" + idFotoUser);
        registro.put("NombreUser", NombreUser);
        registro.put("idUser", idUser);
        registro.put("idNegocio", idNegocio);
        registro.put("fecha", fecha);
        registro.put("hora", hora);


        ArrayList list = new ArrayList();
        list.add("id_producto");
        list.add("producto");
        list.add("costo");
        list.add("ventaMenudeo");
        list.add("ventaMayoreo");
        list.add("marca");
        list.add("color");
        list.add("unidadMedida");
        list.add("categoria_nombre");
        list.add("id_categoria");
        list.add("Id_Foto");
        list.add("status");
        list.add("cantidadMinima");
        list.add("idFotoUser");
        list.add("NombreUser");
        list.add("idUser");
        list.add("idNegocio");
        list.add("fecha");
        list.add("hora");

        ArrayList listdata = new ArrayList();
        listdata.add("" + id_producto);
        listdata.add(nombre);
        listdata.add(costo);
        listdata.add(menudeo);
        listdata.add(mayoreo);
        listdata.add(marca);
        listdata.add(color);
        listdata.add(unidad_med);
        listdata.add(cat_nombre_sp);
        listdata.add(cat_id_sp);
        listdata.add("" + num_id.trim());
        listdata.add(status);
        listdata.add(cant_min);
        listdata.add("0" + idFotoUser);
        listdata.add(NombreUser);
        listdata.add("" + idUser);
        listdata.add(idNegocio);
        listdata.add(fecha);
        listdata.add(hora);

        if (isNetDisponible(context) == true && isOnlineNet() == true) {
           // Toast.makeText(context, "Conectado", Toast.LENGTH_SHORT).show();
            int n = (int) BaseDeDatos.insert("producto", null, registro);
            tab = new wsInsert(context);
            tab.enlase_base_de_datos(list, listdata, "insert_producto.php");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewInput = inflater.inflate(R.layout.toast_layout, null, false);
            TextView text = (TextView) viewInput.findViewById(R.id.text12);
            text.setText("Registro Exitoso");
            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(viewInput);
            toast.show();
            if (!num_id.isEmpty()) {

                if (db.insertimage(x, num_id, String.valueOf(id_producto))) {

                    //  Toast.makeText(context, "Registro Exitoso,\nImagen insertada", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                viewInput = inflater.inflate(R.layout.toast_layout, null, false);
                text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Registro Exitoso");

            }
        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

    }

    public void RegistrarUsuario(Context context, int idCliente, String Nombre, String Calle, String Numero,
                                 String Interior, String Codigo_Postal, String Colonia
            , String Municipio, String Estado, String Alias,
                                 String Lada, String Telefono, String tipoTelefono, String ApellidoPaterno
            , String ApellidoMaterno, String TipoCompra, String num_id
            , String x, String Contra, String idNegocio, String correo) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        idCliente = (int) (Math.random() * 10000 + 1 * 3 + 15);

        registro.put("id_cliente", idCliente);
        registro.put("Nombre", Nombre);
        registro.put("Calle", Calle);
        registro.put("Numero", Numero);
        registro.put("Interior", Interior);
        registro.put("Codigo_Postal", Codigo_Postal);
        registro.put("Colonia", Colonia);
        registro.put("Municipio", Municipio);
        registro.put("Estado", Estado);
        registro.put("Alias", Alias);
        registro.put("Lada", Lada);
        registro.put("Telefono", Telefono);
        registro.put("status", "Activo");
        registro.put("TipoTelefono", tipoTelefono);
        registro.put("ApellidoPaterno", ApellidoPaterno);
        registro.put("ApellidoMaterno", ApellidoMaterno);
        registro.put("TipoCompra", TipoCompra);
        registro.put("Id_Foto", num_id.trim());
        registro.put("Contra", "" + Contra);
        registro.put("idNegocio", "" + idNegocio);
        registro.put("correo", "" + correo);

        ArrayList list = new ArrayList();
        list.add("id_cliente");
        list.add("Nombre");
        list.add("Calle");
        list.add("Numero");
        list.add("Interior");
        list.add("Codigo_Postal");
        list.add("Colonia");
        list.add("Municipio");
        list.add("Estado");
        list.add("Alias");
        list.add("Lada");
        list.add("status");
        list.add("Telefono");
        list.add("TipoTelefono");
        list.add("ApellidoPaterno");
        list.add("ApellidoMaterno");
        list.add("TipoCompra");
        list.add("Id_Foto");
        list.add("Contra");
        list.add("idNegocio");
        list.add("correo");

        ArrayList listdata = new ArrayList();
        listdata.add(idCliente);
        listdata.add(Nombre);
        listdata.add(Calle);
        listdata.add(Numero);
        listdata.add(Interior);
        listdata.add(Codigo_Postal);
        listdata.add(Colonia);
        listdata.add(Municipio);
        listdata.add(Estado);
        listdata.add(Alias);
        listdata.add(Lada);
        listdata.add("Activo");
        listdata.add(Telefono);
        listdata.add(tipoTelefono);
        listdata.add(ApellidoPaterno);
        listdata.add(ApellidoMaterno);
        listdata.add(TipoCompra);
        listdata.add(num_id);
        listdata.add(Contra);
        listdata.add(idNegocio);
        listdata.add(correo);

        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            BaseDeDatos.insert("clientes", null, registro);
            tab = new wsInsert(context);
            tab.enlase_base_de_datos(list, listdata, "insert_user.php");

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

            TextView text = (TextView) viewInput.findViewById(R.id.text12);
            text.setText("Registro Exitoso");

            Toast toast = new Toast(context);
            //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(viewInput);
            toast.show();

            BaseDeDatos.close();


            if (!num_id.isEmpty()) {
                if (db.insertimage(x, num_id, String.valueOf(idCliente))) {
//                Toast.makeText(context, "Registro Exitoso,\nImagen insertada", Toast.LENGTH_SHORT).show();
                } else {
//                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

    }

    public int RegistrarNegocio(Context context, int idNegocio, int idUser, String Nombre, String Calle, String Numero,
                                String Interior, String Codigo_Postal, String Colonia, String Municipio, String Estado,
                                String Lada, String Telefono, String tipoTelefono, String TipoCompra, String num_id,
                                String x, String Contra, String Correo, String Descripcion, String idFotoNegocio,String servicioDomicilio) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("idNegocio", idNegocio);
        registro.put("idUser", idUser);
        registro.put("Nombre", Nombre);
        registro.put("Calle", Calle);
        registro.put("Numero", Numero);
        registro.put("Interior", Interior);
        registro.put("Codigo_Postal", Codigo_Postal);
        registro.put("Colonia", Colonia);
        registro.put("Municipio", Municipio);
        registro.put("Estado", Estado);
        registro.put("Lada", Lada);
        registro.put("Telefono", Telefono);
        registro.put("status", "Activo");
        registro.put("TipoTelefono", tipoTelefono);
        registro.put("TipoCompra", TipoCompra);
        registro.put("Id_Foto", num_id.trim());
        registro.put("Contra", "" + Contra);
        registro.put("Correo", Correo);
        registro.put("Descripcion", "" + Descripcion);
        registro.put("servicioDomicilio", servicioDomicilio);

        ArrayList list = new ArrayList();
        list.add("idNegocio");
        list.add("idUser");
        list.add("Nombre");
        list.add("TipoCompra");
        list.add("Calle");
        list.add("Numero");
        list.add("Interior");
        list.add("Codigo_Postal");
        list.add("Colonia");
        list.add("Municipio");
        list.add("Estado");
        list.add("Lada");
        list.add("Telefono");
        list.add("status");
        list.add("TipoTelefono");
        list.add("Id_Foto");
        list.add("Contra");
        list.add("Correo");
        list.add("Descripcion");
        list.add("servicioDomicilio");

        ArrayList listdata = new ArrayList();
        listdata.add("" + idNegocio);
        listdata.add("" + idUser);
        listdata.add(Nombre);
        listdata.add(TipoCompra);
        listdata.add(Calle);
        listdata.add(Numero);
        listdata.add(Interior);
        listdata.add(Codigo_Postal);
        listdata.add(Colonia);
        listdata.add(Municipio);
        listdata.add(Estado);
        listdata.add(Lada);
        listdata.add(Telefono);
        listdata.add("Activo");
        listdata.add(tipoTelefono);
        listdata.add(num_id);
        listdata.add(Contra);
        listdata.add(Correo);
        listdata.add(Descripcion);
        listdata.add(servicioDomicilio);


        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            int n = (int) BaseDeDatos.insert("negocio", null, registro);
            tab = new wsInsert(context);
            tab.enlase_base_de_datos(list, listdata, "insert_negocio.php");

            if (n == 1) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Registro Exitoso");

                Toast toast = new Toast(context);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
            }//else Toast.makeText(context,"Falla al registro",Toast.LENGTH_SHORT).show();
            //BaseDeDatos.close();
            if (!num_id.isEmpty()) {
                if (db.insertimage(x, num_id, String.valueOf(idNegocio))) {
                    // Toast.makeText(context, "Registro Exitoso,\nImagen insertada", Toast.LENGTH_SHORT).show();
                } else {
//                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

        return idNegocio;

    }

    public void RegistrarComentario(Context context, int id_comentario, String comentario, String nombreUser,
                                    String idProducto, String idUser, String nombreProducto, String urlFoto,
                                    String fecha, String hora) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        id_comentario = (int) (Math.random() * 10000 + 1 * 3 + 15);//View.generateViewId();

        registro.put("id_comentario", id_comentario);
        registro.put("comentario", comentario);
        registro.put("nombreUser", idUser);
        registro.put("idProducto", idProducto);
        registro.put("idUser", nombreUser);
        registro.put("nombreProducto", nombreProducto);
        registro.put("urlFoto", urlFoto);
        registro.put("status", "Activo");
        registro.put("fecha", fecha);
        registro.put("hora", hora);


        ArrayList list = new ArrayList();
        list.add("id_comentario");
        list.add("comentario");
        list.add("nombreUser");
        list.add("idProducto");
        list.add("idUser");
        list.add("nombreProducto");
        list.add("urlFoto");
        list.add("status");
        list.add("fecha");
        list.add("hora");
        ArrayList listdata = new ArrayList();
        listdata.add(id_comentario);
        listdata.add(comentario);
        listdata.add(idUser);
        listdata.add(idProducto);
        listdata.add(nombreUser);
        listdata.add("0");
        listdata.add(urlFoto);
        listdata.add("Activo");
        listdata.add(fecha);
        listdata.add(hora);


        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            int n = (int) BaseDeDatos.insert("comentario", null, registro);
            tab = new wsInsert(context);
            tab.enlase_base_de_datos(list, listdata, "insert_comentario.php");
            if (n == 1) {
//            Toast.makeText(context, "Comentario Exitoso", Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Registro Exitoso");

                Toast toast = new Toast(context);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
            }//else Toast.makeText(context,"Falla al registro",Toast.LENGTH_SHORT).show();

            BaseDeDatos.close();
        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

    }

    public void RegistrarSeguidor(Context context, int idSeguir, String idUser, String idSeguidor,
                                  String nombreSeguidor, String status) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        idSeguir = (int) (Math.random() * 10000 + 1 * 3 + 15);//View.generateViewId();

        registro.put("idSeguir", idSeguir);
        registro.put("idUser", idUser);
        registro.put("idSeguidor", idSeguidor);
        registro.put("nombreSeguidor", nombreSeguidor);
        registro.put("status", "Activo");

        ArrayList list = new ArrayList();
        list.add("idSeguir");
        list.add("idUser");
        list.add("idSeguidor");
        list.add("nombreSeguidor");
        list.add("status");

        ArrayList listdata = new ArrayList();
        listdata.add(idSeguir);
        listdata.add(idUser);
        listdata.add(idSeguidor);
        listdata.add(nombreSeguidor);
        listdata.add("Activo");


        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            int n = (int) BaseDeDatos.insert("seguir", null, registro);
            tab = new wsInsert(context);
            tab.enlase_base_de_datos(list, listdata, "insert_seguir.php");
            if (n == 1) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Registro Exitoso");

                Toast toast = new Toast(context);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
                //Toast.makeText(context, "Comentario Exitoso", Toast.LENGTH_SHORT).show();
            }//else Toast.makeText(context,"Falla al registro",Toast.LENGTH_SHORT).show();
            // BaseDeDatos.close();

        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }


    }

    public void RegistrarCarritoTicket(Context context, int idticket, String idUser, String Productos,
                                       String cantidad, String precio, String idproveedor, String status,
                                       String idProducto, String fecha, String hora, String cantidadDisponible) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
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
        registro.put("status", status);
        registro.put("fecha", "0");
        registro.put("hora", "0");
        registro.put("cantidadDisponible", cantidadDisponible);
        registro.put("solicitud", "0");
        registro.put("idDomicilio", "0");
        registro.put("Ticket", "0");

        ArrayList list = new ArrayList();
        list.add("idticket");
        list.add("idUser");
        list.add("Productos");
        list.add("cantidad");
        list.add("precio");
        list.add("idproveedor");
        list.add("idProducto");
        list.add("status");
        list.add("fecha");
        list.add("hora");
        list.add("cantidadDisponible");
        list.add("solicitud");
        list.add("idDomicilio");
        list.add("Ticket");

        ArrayList listdata = new ArrayList();
        listdata.add(idticket);
        listdata.add(idUser);
        listdata.add(Productos);
        listdata.add(cantidad);
        listdata.add(precio);
        listdata.add(idproveedor);
        listdata.add(idProducto);
        listdata.add("Activo");
        listdata.add("0");
        listdata.add("0");
        listdata.add(cantidadDisponible);
        listdata.add("0");
        listdata.add("0");
        listdata.add("0");




        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            int n = (int) BaseDeDatos.insert("carrito", null, registro);
            BaseDeDatos.close();
            tab = new wsInsert(context);
            tab.enlase_base_de_datos(list, listdata, "insert_carrito.php");
            if (n == 1) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View viewInput = inflater.inflate(R.layout.toast_layout, null, false);
//
//            TextView text = (TextView) viewInput.findViewById(R.id.text12);
//            text.setText("Registro Exitoso");
//
//            Toast toast = new Toast(context);
//            //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//            toast.setDuration(Toast.LENGTH_LONG);
//            toast.setView(viewInput);
//            toast.show();
//            Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show();
            }//else Toast.makeText(context,"Falla al registro",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }
    }

    public void RegistrarDomicilios(Context context, int idDomicilio, String idUser, String NombreCompleto,
                                    String Domicilio, String Colonia, String Vecindario, String Celular,
                                    String CodigoPostal, String numero, String municipio) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

        idDomicilio = (int) (Math.random() * 10000 + 1 * 3 + 15);//View.generateViewId();


        registro.put("idDomicilio", idDomicilio);
        registro.put("idUser", idUser);
        registro.put("NombreCompleto", NombreCompleto);
        registro.put("Domicilio", Domicilio);
        registro.put("Colonia", Colonia);
        registro.put("Vecindario", Vecindario);
        registro.put("Celular", Celular);
        registro.put("CodigoPostal", CodigoPostal);
        registro.put("numero", numero);
        registro.put("municipio", municipio);


        ArrayList list = new ArrayList();
        list.add("idDomicilio");
        list.add("idUser");
        list.add("NombreCompleto");
        list.add("Domicilio");
        list.add("Colonia");
        list.add("Vecindario");
        list.add("Celular");
        list.add("CodigoPostal");
        list.add("numero");
        list.add("municipio");

        ArrayList listdata = new ArrayList();
        listdata.add(idDomicilio);
        listdata.add(idUser);
        listdata.add(NombreCompleto);
        listdata.add(Domicilio);
        listdata.add(Colonia);
        listdata.add(Vecindario);
        listdata.add(Celular);
        listdata.add(CodigoPostal);
        listdata.add(numero);
        listdata.add(municipio);

        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            int n = (int) BaseDeDatos.insert("domicilios", null, registro);
            tab = new wsInsert(context);
            tab.enlase_base_de_datos(list, listdata, "insert_domicilios.php");
            if (n == 1) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View viewInput = inflater.inflate(R.layout.toast_layout, null, false);
//
//            TextView text = (TextView) viewInput.findViewById(R.id.text12);
//            text.setText("Registro Exitoso");
//
//            Toast toast = new Toast(context);
//            //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//            toast.setDuration(Toast.LENGTH_LONG);
//            toast.setView(viewInput);
//            toast.show();
//            Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show();
            }//else Toast.makeText(context,"Falla al registro",Toast.LENGTH_SHORT).show();
            BaseDeDatos.close();

        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

    }

}
