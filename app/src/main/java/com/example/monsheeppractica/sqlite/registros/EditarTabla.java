package com.example.monsheeppractica.sqlite.registros;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.monsheeppractica.Activitys.MainActivityRegistroCategorias;
import com.example.monsheeppractica.Activitys.MainActivityRegistroProductos;
import com.example.monsheeppractica.Activitys.MainActivityRegistroUsuario;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.sqlite;

public class EditarTabla {
    DatabaseHandler db;
    SQLiteDatabase BaseDeDatos;
    int cantid = 0;


    public int EditarCategoria(Context context, String idCategoria, String categoria, String descripcion, String idFoto, String status, String idProducto, String x, String idFotoExtra) {
        try {

            db = new DatabaseHandler(context);
            sqlite admin = new sqlite
                    (context, "categoria", null, 1);

            BaseDeDatos = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("categoria", categoria);
            registro.put("Descripcion", descripcion);
            if (idFoto.isEmpty()) {
                cantid = BaseDeDatos.update("categoria", registro, "id_categoria= " + Integer.parseInt(idCategoria), null);
                BaseDeDatos.close();
                if (cantid == 1) {
                    Toast.makeText(context, "Edicion Exitosa", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Categoria no existe", Toast.LENGTH_SHORT).show();
                }

            } else {
                MainActivityRegistroCategorias result = new MainActivityRegistroCategorias();
                result.Devolver(context, idFoto, idCategoria, registro, x, BaseDeDatos);


            }


        } catch (Exception e) {
            Toast.makeText(context, "Validacion: " + e, Toast.LENGTH_SHORT).show();

        }
        return cantid;
    }

    public int EditarProducto(Context context, String nombre, String costo,
                              String menudeo, String mayoreo, String marca, String color,
                              String unidad_med, String cat_nombre_sp, int cat_id_sp,
                              String cant_min, String categoria_nombre_extra, int id_categoria_extra,
                              String num_id, String x, String id_producto_extra) {
        try {
            sqlite admin = new sqlite
                    (context, "producto", null, 1);
            // Toast.makeText(getApplicationContext(), ""+catego+descrip+num_id, Toast.LENGTH_SHORT).show();
            BaseDeDatos = admin.getWritableDatabase();

            ContentValues registro = new ContentValues();

            registro.put("producto", nombre);
            registro.put("costo", costo);
            registro.put("ventaMenudeo", menudeo);
            registro.put("ventaMayoreo", mayoreo);
            registro.put("marca", marca);

            registro.put("color", color);
            registro.put("unidadMedida", unidad_med);
            registro.put("categoria_nombre", cat_nombre_sp);

            if (cat_nombre_sp.equals(categoria_nombre_extra)) {
                cat_id_sp = id_categoria_extra;
            }

            registro.put("id_categoria", "" + cat_id_sp);
            registro.put("cantidadMinima", "" + cant_min);

            if (num_id.isEmpty()) {
                cantid = BaseDeDatos.update("producto", registro, "id_producto= " + Integer.parseInt(id_producto_extra), null);

                BaseDeDatos.close();


                return cantid;
            } else {
                MainActivityRegistroProductos result = new MainActivityRegistroProductos();
                result.Devolver(context, num_id, id_producto_extra, registro, x, BaseDeDatos);


            }

        } catch (Exception e) {
            Toast.makeText(context, "Validacion: " + e, Toast.LENGTH_SHORT).show();
        }


        return cantid;
    }

    public int EditarUsuario(Context context, String Nombre, String Calle,
                             String Numero, String Interior, String Codigo_Postal, String Colonia,
                             String Municipio, String Estado,
                             String Alias, String Lada, String Teléfono,
                             String num_id, String x, String tipoTelefono, String ApellidoPaterno,
                             String ApellidoMaterno, String TipoCompra, String idClienteExtra) {
        try {
            sqlite admin = new sqlite
                    (context, "clientes", null, 1);
            // Toast.makeText(getApplicationContext(), ""+catego+descrip+num_id, Toast.LENGTH_SHORT).show();
            BaseDeDatos = admin.getWritableDatabase();

            ContentValues registro = new ContentValues();
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
            registro.put("Teléfono", Teléfono);
            registro.put("status", "Activo");
            registro.put("TipoTelefono", tipoTelefono);
            registro.put("ApellidoPaterno", ApellidoPaterno);
            registro.put("ApellidoMaterno", ApellidoMaterno);
            registro.put("TipoCompra", TipoCompra);

            if (num_id.isEmpty()) {
                cantid = BaseDeDatos.update("clientes", registro,
                        "id_cliente= " + Integer.parseInt(idClienteExtra), null);

                BaseDeDatos.close();

                return cantid;
            } else {
                MainActivityRegistroUsuario result = new MainActivityRegistroUsuario();
                result.Devolver(context, num_id, idClienteExtra, registro, x, BaseDeDatos);


            }

        } catch (Exception e) {
            Toast.makeText(context, "Validacion: " + e, Toast.LENGTH_SHORT).show();
        }


        return cantid;
    }

    public int EditarUser(Context context, int codigo, String idNegocio) {
        sqlite admin = new sqlite
                (context, "clientes", null, 1);

        BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        if (!String.valueOf(codigo).equals("3751076001")) {
            registro.put("TipoCompra", "1");
        }

        registro.put("idNegocio", idNegocio);

        int cantid = BaseDeDatos.update("clientes", registro,
                "id_cliente=" + Integer.parseInt("" + codigo), null);
        if (cantid == 1) {
            Toast.makeText(context, "Modificacion exitosa", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Modificacion negada", Toast.LENGTH_SHORT).show();
        }
        BaseDeDatos.close();


        return cantid;
    }

    public int EditarCarrito(Context context, String codigo, String cantidad, String cantidadDisponible) {
        sqlite admin = new sqlite
                (context, "carrito", null, 1);

        BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("cantidad", cantidad);
        // registro.put("cantidadDisponible",cantidadDisponible);

        int cantid = BaseDeDatos.update("carrito", registro,
                "idProducto=" + codigo, null);
        if (cantid == 1) {
            Toast.makeText(context, "Modificacion exitosa", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Modificacion negada", Toast.LENGTH_SHORT).show();
        }
        BaseDeDatos.close();


        return cantid;
    }

    public int EditarProductos(Context context, int codigo, String Cantidadminima) {
        sqlite admin = new sqlite
                (context, "producto", null, 1);

        BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("cantidadMinima", Cantidadminima);

        int cantid = BaseDeDatos.update("producto", registro,
                "id_producto=" + codigo, null);
        if (cantid == 1) {
            Toast.makeText(context, "exitosa", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Modificacion negada", Toast.LENGTH_SHORT).show();
        }
        BaseDeDatos.close();


        return cantid;
    }

    public int EditarDomicilio(Context context, String idUser, String NombreCompleto,
                               String Domicilio, String Vecindario, String Celular, String CodigoPostal,
                               String numero, String municipio,
                               String Colonia) {
        try {
            sqlite admin = new sqlite
                    (context, "domicilios", null, 1);
            // Toast.makeText(getApplicationContext(), ""+catego+descrip+num_id, Toast.LENGTH_SHORT).show();
            BaseDeDatos = admin.getWritableDatabase();

            ContentValues registro = new ContentValues();

            registro.put("NombreCompleto", NombreCompleto);
            registro.put("Domicilio", Domicilio);
            registro.put("Colonia", Colonia);
            registro.put("Vecindario", Vecindario);
            registro.put("Celular", Celular);
            registro.put("CodigoPostal", CodigoPostal);
            registro.put("numero", numero);
            registro.put("municipio", municipio);


            cantid = BaseDeDatos.update("domicilios", registro,
                    "idUser= " + idUser, null);

            BaseDeDatos.close();

            return cantid;


        } catch (Exception e) {
            Toast.makeText(context, "Validacion: " + e, Toast.LENGTH_SHORT).show();
        }


        return cantid;
    }

}
