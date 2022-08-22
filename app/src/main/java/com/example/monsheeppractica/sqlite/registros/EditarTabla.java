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

import com.example.monsheeppractica.Activitys.MainActivityRegistroCategorias;
import com.example.monsheeppractica.Activitys.MainActivityRegistroProductos;
import com.example.monsheeppractica.Activitys.MainActivityRegistroUsuario;
import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.WebService.wsEdit;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.sqlite;

import java.io.FileInputStream;
import java.util.ArrayList;

public class EditarTabla {
    DatabaseHandler db;
    SQLiteDatabase BaseDeDatos;
    int cantid = 0;


    public int EditarCategoria(Context context, String idCategoria,
                               String categoria, String descripcion, String idFoto,
                               String status, String idProducto, String x, String idFotoExtra) {
        try {

            db = new DatabaseHandler(context);
            sqlite admin = new sqlite
                    (context, "monsheep", null, 1);

            BaseDeDatos = admin.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("categoria", categoria);
            registro.put("Descripcion", descripcion);

            if (isNetDisponible(context) == true && isOnlineNet() == true) {

                if (idFoto.isEmpty()) {
                    cantid = BaseDeDatos.update("categoria", registro, "id_categoria= " + Integer.parseInt(idCategoria), null);
                    BaseDeDatos.close();
                    if (cantid == 1) {
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                        TextView text = (TextView) viewInput.findViewById(R.id.text12);
                        text.setText("Edicion Exitosa");

                        Toast toast = new Toast(context);
                        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(viewInput);
                        toast.show();
                    }
                    wsEdit edit = new wsEdit(context);
                    edit.wsEditarTabla("update_Categoria.php?id_categoria=" + idCategoria + "&categoria=" + categoria + "&Descripcion=" + descripcion);

                } else {
                    MainActivityRegistroCategorias result = new MainActivityRegistroCategorias();
                    result.Devolver(context, idFoto, idCategoria, registro, x, BaseDeDatos);


                }
            } else {
                Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
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
                    (context, "monsheep", null, 1);
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


            if (isNetDisponible(context) == true && isOnlineNet() == true) {

                if (num_id.isEmpty()) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewInput = inflater.inflate(R.layout.toast_layout, null, false);
                    TextView text = (TextView) viewInput.findViewById(R.id.text12);
                    text.setText("Edicion Exitosa");
                    Toast toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(viewInput);
                    toast.show();
                    cantid = BaseDeDatos.update("producto", registro, "id_producto= " + Integer.parseInt(id_producto_extra), null);

                    BaseDeDatos.close();
                    wsEdit edit = new wsEdit(context);
                    edit.wsEditarTabla("update_Producto.php?" +
                            "id_producto=" + Integer.parseInt(id_producto_extra) + "&producto=" + nombre + "&costo=" + costo
                            + "&ventaMenudeo=" + menudeo + "&ventaMayoreo=" + mayoreo + "&marca=" + marca
                            + "&color=" + color + "&unidadMedida=" + unidad_med + "&categoria_nombre=" + cat_nombre_sp
                            + "&id_categoria=" + cat_id_sp + "&cantidadMinima=" + cant_min);


                    return cantid;
                } else {
                    MainActivityRegistroProductos result = new MainActivityRegistroProductos();
                    result.Devolver(context, num_id, id_producto_extra, registro, x, BaseDeDatos);
                }

            } else {
                Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(context, "Validacion: " + e, Toast.LENGTH_SHORT).show();
        }


        return cantid;
    }

    public int EditarUsuario(Context context, String Nombre, String Calle,
                             String Numero, String Interior, String Codigo_Postal, String Colonia,
                             String Municipio, String Estado,
                             String Alias, String Lada, String Telefono,
                             String num_id, String x, String tipoTelefono, String ApellidoPaterno,
                             String ApellidoMaterno, String TipoCompra, String idClienteExtra) {
        try {
            sqlite admin = new sqlite
                    (context, "monsheep", null, 1);
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
            registro.put("Telefono", Telefono);
            registro.put("status", "Activo");
            registro.put("TipoTelefono", tipoTelefono);
            registro.put("ApellidoPaterno", ApellidoPaterno);
            registro.put("ApellidoMaterno", ApellidoMaterno);
            registro.put("TipoCompra", TipoCompra);

            if (isNetDisponible(context) == true && isOnlineNet() == true) {

                if (num_id.isEmpty()) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                    TextView text = (TextView) viewInput.findViewById(R.id.text12);
                    text.setText("Edicion Exitosa");

                    Toast toast = new Toast(context);
                    //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(viewInput);
                    toast.show();
                    cantid = BaseDeDatos.update("clientes", registro,
                            "id_cliente= " + Integer.parseInt(idClienteExtra), null);

                    BaseDeDatos.close();
                    wsEdit edit = new wsEdit(context);
                    edit.wsEditarTabla("update_Usuario.php?" +
                            "id_cliente=" + idClienteExtra + "&Nombre=" + Nombre + "&Calle=" + Calle + "&Numero=" + Numero
                            + "&Interior=" + Interior + "&Codigo_Postal=" + Codigo_Postal + "&Colonia=" + Colonia
                            + "&Municipio=" + Municipio + "&Estado=" + Estado + "&Alias=" + Alias
                            + "&Lada=" + Lada + "&Telefono=" + Telefono + "&status='Activo'"
                            + "&TipoTelefono=" + tipoTelefono + "&ApellidoPaterno=" + ApellidoPaterno + "&ApellidoMaterno=" + ApellidoMaterno
                            + "&TipoCompra=" + TipoCompra);

                    return cantid;
                } else {
                    MainActivityRegistroUsuario result = new MainActivityRegistroUsuario();
                    result.Devolver(context, num_id, idClienteExtra, registro, x, BaseDeDatos);


                }

            } else {
                Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(context, "Validacion: " + e, Toast.LENGTH_SHORT).show();
        }


        return cantid;
    }

    public void EditarPerfilNegocio(Context context, int idNegocio, String Nombre, String Calle, String Numero,
                                    String Interior, String Codigo_Postal, String Colonia
            , String Municipio, String Estado, String Alias,
                                    String Lada, String Telefono, String tipoTelefono, String ApellidoPaterno
            , String ApellidoMaterno, String TipoCompra, String num_id
            , String x, String descripcion, String correo) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

        ContentValues registro = new ContentValues();

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
        registro.put("Correo", correo);
        registro.put("Descripcion", "" + descripcion);


        if (isNetDisponible(context) == true && isOnlineNet() == true) {

            int cantid = BaseDeDatos.update("negocio", registro,
                    "idNegocio=" + idNegocio, null);
            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("update_Negocio.php?" +
                    "idNegocio=" + idNegocio + "&Nombre=" + Nombre + "&Calle=" + Calle + "&Numero=" + Numero
                    + "&Interior=" + Interior + "&Codigo_Postal=" + Codigo_Postal + "&Colonia=" + Colonia
                    + "&Municipio=" + Municipio + "&Estado=" + Estado
                    + "&Lada=" + Lada + "&Telefono=" + Telefono
                    + "&TipoTelefono=" + tipoTelefono + "&TipoCompra=" + TipoCompra + "&Id_Foto=" + num_id + "&Correo=" + correo
                    + "&Descripcion=" + descripcion);
            if (cantid == 1) {

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
                    if (db.deleteImage(x, String.valueOf(idNegocio))) {
                        if (db.insertimage(x, num_id, String.valueOf(idNegocio))) {

                        }
                    }
                }
            }

        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

    }

    public void EditarPerfil(Context context, int idCliente, String Nombre, String Calle, String Numero,
                             String Interior, String Codigo_Postal, String Colonia
            , String Municipio, String Estado, String Alias,
                             String Lada, String Telefono, String tipoTelefono, String ApellidoPaterno
            , String ApellidoMaterno, String TipoCompra, String num_id
            , String x,String correo) {

        db = new DatabaseHandler(context);
        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();

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
        registro.put("Telefono", Telefono);
        registro.put("status", "Activo");
        registro.put("TipoTelefono", tipoTelefono);
        registro.put("ApellidoPaterno", ApellidoPaterno);
        registro.put("ApellidoMaterno", ApellidoMaterno);
        registro.put("Id_Foto", num_id.trim());
        registro.put("correo", correo.trim());

        if (isNetDisponible(context) == true && isOnlineNet() == true) {

            int cantid = BaseDeDatos.update("clientes", registro,
                    "id_cliente=" + idCliente, null);

            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("update_Usuario.php?" +
                    "id_cliente=" + idCliente + "&Nombre=" + Nombre + "&Calle=" + Calle + "&Numero=" + Numero
                    + "&Interior=" + Interior + "&Codigo_Postal=" + Codigo_Postal + "&Colonia=" + Colonia
                    + "&Municipio=" + Municipio + "&Estado=" + Estado + "&Alias=" + Alias
                    + "&Lada=" + Lada + "&Telefono=" + Telefono
                    + "&TipoTelefono=" + tipoTelefono + "&ApellidoPaterno=" + ApellidoPaterno + "&ApellidoMaterno=" + ApellidoMaterno
                    + "&Id_Foto=" + num_id+"&correo="+correo.trim());

            if (cantid == 1) {

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
                    if (db.deleteImage(x, String.valueOf(idCliente))) {
                        if (db.insertimage(x, num_id, String.valueOf(idCliente))) {

                        }
                    }
                }
            }
        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }
    }

    public int EditarUser(Context context, int codigo, String idNegocio, String TipoCompra) {
        sqlite admin = new sqlite
                (context, "monsheep", null, 1);

        BaseDeDatos = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();

        ConsultarTabla ct = new ConsultarTabla(context);
        ArrayList<Clientes> ArrayClientes = new ArrayList<>();
        ct.ClientesConsulta(ArrayClientes, "user", codigo, null);
        TipoCompra = "1";
        // Toast.makeText(context, ""+ArrayClientes.get(0).Telefono(), Toast.LENGTH_SHORT).show();
        if (ArrayClientes.get(0).getTeléfono().trim().equals("3751076001")) {
            TipoCompra = "admin";
        }
        registro.put("TipoCompra", TipoCompra);
        registro.put("idNegocio", idNegocio);


        if (isNetDisponible(context) == true && isOnlineNet() == true) {

            int cantid = BaseDeDatos.update("clientes", registro,
                    "id_cliente=" + Integer.parseInt("" + codigo), null);

            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("update_UserTipoCompra.php?id_cliente=" + codigo
                    + "&idNegocio=" + idNegocio + "&TipoCompra=" + TipoCompra);

            if (cantid == 1) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Edicion Exitosa");
                Toast toast = new Toast(context);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
            } else {
                Toast.makeText(context, "Modificacion negada", Toast.LENGTH_SHORT).show();
            }
            BaseDeDatos.close();
        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

        return cantid;
    }

    public int EditarCarrito(Context context, String codigo, String cantidad, String ticket) {
        sqlite admin = new sqlite
                (context, "monsheep", null, 1);

        BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("cantidad", cantidad);
        // registro.put("cantidadDisponible",cantidadDisponible);


        if (isNetDisponible(context) == true && isOnlineNet() == true) {

            int cantid = BaseDeDatos.update("carrito", registro,
                    "idProducto=" + codigo + " and idticket=" + ticket, null);
            String pro = "" + codigo;
            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("update_CarritoCantidad.php?idticket=" + ticket + "&cantidad=" + cantidad + "&idProducto=" + pro);


            if (cantid == 1) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Edicion Exitosa");

                Toast toast = new Toast(context);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
            } else {
                //   Toast.makeText(context, "Modificacion negada", Toast.LENGTH_SHORT).show();
            }
            BaseDeDatos.close();
        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

        return cantid;
    }

    public int EditarProductoCantidad(Context context, int codigo, String Cantidadminima) {
        sqlite admin = new sqlite
                (context, "monsheep", null, 1);

        BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("cantidadMinima", Cantidadminima);


        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            int cantid = BaseDeDatos.update("producto", registro,
                    "id_producto=" + codigo, null);
            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("update_ProductoCantidad.php?id_producto=" + codigo + "&cantidadMinima=" + Cantidadminima);

            BaseDeDatos.close();

        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }
        return cantid;
    }

    public int EditarCarritoFinal(Context context, String idProducto, String fecha,
                                  String hora, String solicitud, String idDomicilio, String almacen, String tk) {
        sqlite admin = new sqlite
                (context, "monsheep", null, 1);

        BaseDeDatos = admin.getWritableDatabase();
        // int idticket = (int) (Math.random() * 10000 + 1 * 3 + 15);
        ContentValues registro = new ContentValues();

        registro.put("fecha", fecha);
        registro.put("hora", hora);
        registro.put("solicitud", solicitud);
        registro.put("idDomicilio", idDomicilio);
        registro.put("cantidadDisponible", almacen);
        registro.put("Ticket", tk);

        if (isNetDisponible(context) == true && isOnlineNet() == true) {

            int cantid = BaseDeDatos.update("carrito", registro,
                    "idticket=" + Integer.parseInt(idProducto), null);

            BaseDeDatos.close();
            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("update_CarritoFinal.php?idticket=" + idProducto + "&fecha=" + fecha + "&hora="
                    + hora + "&solicitud=" + solicitud + "&idDomicilio=" + idDomicilio + "&cantidadDisponible=" + almacen + "&Ticket=" + tk);

        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }
        return cantid;
    }

    /************STRINGS*************/
    public int EditarCarritoStatus(Context context, String id, String status, String id2) {
        sqlite admin = new sqlite
                (context, "monsheep", null, 1);

        BaseDeDatos = admin.getWritableDatabase();
        // int idticket = (int) (Math.random() * 10000 + 1 * 3 + 15);
        ContentValues registro = new ContentValues();

        registro.put("solicitud", status);

        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            int cantid = BaseDeDatos.update("carrito", registro,
                    "Ticket='" + id + "' and idproveedor='" + id2 + "'", null);
            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("update_CarritoStatus.php?Ticket=" + id + "&idproveedor=" + id2 + "&solicitud="
                    + status);

            if (cantid >= 1) {
                Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
            }

            BaseDeDatos.close();

        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

        return cantid;
    }

    public int EditarCarritoStatusUser(Context context, String id, String status, String id2, String idUser) {
        sqlite admin = new sqlite
                (context, "monsheep", null, 1);

        BaseDeDatos = admin.getWritableDatabase();
        // int idticket = (int) (Math.random() * 10000 + 1 * 3 + 15);
        ContentValues registro = new ContentValues();

        registro.put("solicitud", status);

        if (isNetDisponible(context) == true && isOnlineNet() == true) {
            int cantid = BaseDeDatos.update("carrito", registro,
                    "Ticket='" + id + "' and idproveedor='" + id2 + "' and idUser='" + idUser + "'", null);
            wsEdit edit = new wsEdit(context);
            edit.wsEditarTabla("update_CarritoStatusUser.php?Ticket=" + id + "&idproveedor=" + id2 + "&idUser=" + idUser
                    + "&solicitud=" + status);
            if (cantid >= 1) {
//            Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
            }

            BaseDeDatos.close();
        } else {
            Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
        }

        return cantid;
    }


    public int EditarDomicilio(Context context, String idUser, String NombreCompleto,
                               String Domicilio, String Vecindario, String Celular, String CodigoPostal,
                               String numero, String municipio,
                               String Colonia) {
        try {
            sqlite admin = new sqlite
                    (context, "monsheep", null, 1);
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



            if (isNetDisponible(context) == true && isOnlineNet() == true) {
                cantid = BaseDeDatos.update("domicilios", registro,
                        "idUser= " + idUser, null);

                BaseDeDatos.close();
                wsEdit edit = new wsEdit(context);
                edit.wsEditarTabla("update_Domicilios.php?idUser=" + idUser /*idUser*/ + "&NombreCompleto=" + NombreCompleto
                        + "&Domicilio=" + Domicilio + "&Colonia=" + Colonia + "&Vecindario=" + Vecindario
                        + "&Celular=" + Celular + "&CodigoPostal=" + CodigoPostal + "&numero=" + numero + "&municipio=" + municipio);

            } else {
                Toast.makeText(context, "Sin Red", Toast.LENGTH_SHORT).show();
            }
            return cantid;


        } catch (Exception e) {
            Toast.makeText(context, "Validacion: " + e, Toast.LENGTH_SHORT).show();
        }


        return cantid;
    }


}
