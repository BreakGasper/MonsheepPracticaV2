package com.example.monsheeppractica.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqlite extends SQLiteOpenHelper {
    public String Categoria = "CREATE TABLE categoria (id_categoria  int primary key , categoria text, Descripcion text" +
            ", Id_Foto text, status text, idproducto text)";

    public String producto = "CREATE TABLE producto (id_producto int primary key not null, producto text, costo text," +
            "ventaMenudeo text,ventaMayoreo text,marca text,color text,unidadMedida text" +
            ", categoria_nombre text, id_categoria text, Id_Foto text, status text, cantidadMinima text, idFotoUser text" +
            ", NombreUser text, idUser text, idNegocio text, fecha text, hora text)";

    public String proveedor = "CREATE TABLE provedor (id_proveedor int primary key, Nombre text, Calle text," +
            "Numero text,Interior text,Codigo_Postal text,Colonia text,Municipio text" +
            ", Estado text, Contacto text, Lada text, Teléfono text, status text, TipoTelefono text)";

    public String Cliente = "CREATE TABLE clientes (id_cliente int primary key, Nombre text, ApellidoPaterno text," +
            "ApellidoMaterno text,TipoCompra text, Calle text, Numero text, Interior text, Codigo_Postal text," +
            "Colonia text,Municipio text" +
            ", Estado text, Lada text, Teléfono text, " +
            "status text, TipoTelefono text, Alias text, " +
            "Id_Foto text,Contra text,idNegocio text)";//, idFotoNegocio text

    public String Comentario = "CREATE TABLE comentario (id_comentario int primary key, comentario text, nombreUser text," +
            "idProducto text,idUser text, nombreProducto text, urlFoto text, status text, fecha text, hora text)";

    public String Seguir = "CREATE TABLE seguir (idSeguir int primary key, idUser text, idSeguidor text," +
            "nombreSeguidor text, status text)";

    public String  Negocio = "CREATE TABLE negocio (idNegocio int primary key,idUser int, Nombre text ," +
            " TipoCompra text, Calle text, Numero text, Interior text, Codigo_Postal text," +
            "Colonia text,Municipio text" +
            ", Estado text, Lada text, Teléfono text, status text, TipoTelefono text ," +
            " Id_Foto text,Contra text,Correo text,Descripcion text)";


     public String  Carrito = "CREATE TABLE carrito (idticket int primary key, idUser text, Productos text," +
             "cantidad text,precio text,idproveedor text, status text,idProducto text, fecha text, hora text,cantidadDisponible text,solicitud text,idDomicilio text)";

    public String  Domicilios = "Create table domicilios(idDomicilio int primary key, idUser text,NombreCompleto text,Domicilio text, Colonia text,Vecindario text,Celular text," +
            "CodigoPostal text, numero text, municipio text);";


    public sqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Categoria);
        db.execSQL(producto);
        db.execSQL(proveedor);
        db.execSQL(Cliente);
        db.execSQL(Comentario);
        db.execSQL(Seguir);
        db.execSQL(Negocio);
        db.execSQL(Carrito);
        db.execSQL(Domicilios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
