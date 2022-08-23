package com.example.monsheeppractica.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.monsheeppractica.R;

public class sqlite extends SQLiteOpenHelper {
    public String Categoria = "CREATE TABLE categoria (id_categoria  int   , categoria text, Descripcion text" +
            ", Id_Foto text, status text, idproducto text,id int  primary key)";

    public String producto = "CREATE TABLE producto (id_producto int , producto text, costo text," +
            "ventaMenudeo text,ventaMayoreo text,marca text,color text,unidadMedida text" +
            ", categoria_nombre text, id_categoria text, Id_Foto text, status text, cantidadMinima text, idFotoUser text" +
            ", NombreUser text, idUser text, idNegocio text, fecha text, hora text,id int  primary key)";

//    public String proveedor = "CREATE TABLE provedor (id_proveedor int primary key, Nombre text, Calle text," +
//            "Numero text,Interior text,Codigo_Postal text,Colonia text,Municipio text" +
//            ", Estado text, Contacto text, Lada text, Telefono text, status text, TipoTelefono text)";

    public String Cliente = "CREATE TABLE clientes (id_cliente int  , Nombre text, ApellidoPaterno text," +
            "ApellidoMaterno text,TipoCompra text, Calle text, Numero text, Interior text, Codigo_Postal text," +
            "Colonia text,Municipio text" +
            ", Estado text, Lada text, Telefono text, " +
            "status text, TipoTelefono text, Alias text, " +
            "Id_Foto text,Contra text,idNegocio text,correo text,id int  primary key)";//, idFotoNegocio text

    public String Comentario = "CREATE TABLE comentario (id_comentario int , comentario text, nombreUser text," +
            "idProducto text,idUser text, nombreProducto text, urlFoto text, status text, fecha text, hora text,id int  primary key)";

    public String Seguir = "CREATE TABLE seguir (idSeguir int  , idUser text, idSeguidor text," +
            "nombreSeguidor text, status text,id int  primary key)";

    public String Negocio = "CREATE TABLE negocio (idNegocio int  ,idUser int, Nombre text ," +
            " TipoCompra text, Calle text, Numero text, Interior text, Codigo_Postal text," +
            "Colonia text,Municipio text" +
            ", Estado text, Lada text, Telefono text, status text, TipoTelefono text ," +
            " Id_Foto text,Contra text,Correo text,Descripcion text,servicioDomicilio text, id int primary key)";

    public String Carrito = "CREATE TABLE carrito (idticket int  , idUser text, Productos text," +
            "cantidad text,precio text,idproveedor text, status text,idProducto text, fecha text, hora text,cantidadDisponible text,solicitud text,idDomicilio text,Ticket text,id int  primary key)";

    public String Domicilios = "Create table domicilios(idDomicilio int  , idUser text,NombreCompleto text,Domicilio text, Colonia text,Vecindario text,Celular text," +
            "CodigoPostal text, numero text, municipio text,id int primary key)";

    public String Roll = "Create table roll(idPromo int, fecha text,Hora text,oferta text, status text,dato text,id int primary key)";

    public String images ="Create table images_categoria(id_foto int, img blob not null, id_Catego text, id int primary key)";

    public sqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Categoria);
        db.execSQL(producto);
        db.execSQL(images);
        db.execSQL(Cliente);
        db.execSQL(Comentario);
        db.execSQL(Seguir);
        db.execSQL(Negocio);
        db.execSQL(Carrito);
        db.execSQL(Domicilios);
        db.execSQL(Roll);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /*
    CREATE TABLE categoria (id_categoria  int   , categoria text, Descripcion text, Id_Foto text, status text, idproducto text,id int primary key AUTO_INCREMENT);
CREATE TABLE producto (id_producto int , producto text, costo text,
            ventaMenudeo text,ventaMayoreo text,marca text,color text,unidadMedida text
            , categoria_nombre text, id_categoria text, Id_Foto text, status text, cantidadMinima text, idFotoUser text
            , NombreUser text, idUser text, idNegocio text, fecha text, hora text,id int  primary key AUTO_INCREMENT);
CREATE TABLE clientes (id_cliente int  , Nombre text, ApellidoPaterno text,
            ApellidoMaterno text,TipoCompra text, Calle text, Numero text, Interior text, Codigo_Postal text,
            Colonia text,Municipio text
            , Estado text, Lada text, Telefono text,
            status text, TipoTelefono text, Alias text,
            Id_Foto text,Contra text,idNegocio text,correo text,id int  primary key AUTO_INCREMENT);

 CREATE TABLE comentario (id_comentario int , comentario text, nombreUser text,
            idProducto text,idUser text, nombreProducto text, urlFoto text, status text, fecha text, hora text,id int  primary key AUTO_INCREMENT);

 CREATE TABLE seguir (idSeguir int  , idUser text, idSeguidor text,
            nombreSeguidor text, status text,id int  primary key AUTO_INCREMENT);

CREATE TABLE negocio (idNegocio int  ,idUser int, Nombre text ,
             TipoCompra text, Calle text, Numero text, Interior text, Codigo_Postal text,
            Colonia text,Municipio text
            , Estado text, Lada text, Telefono text, status text, TipoTelefono text ,
             Id_Foto text,Contra text,Correo text,Descripcion text,id int primary key AUTO_INCREMENT);

CREATE TABLE carrito (idticket int  , idUser text, Productos text,
            cantidad text,precio text,idproveedor text, status text,idProducto text, fecha text, hora text,cantidadDisponible text,solicitud text,idDomicilio text,Ticket text,id int  primary key AUTO_INCREMENT);

 Create table domicilios(idDomicilio int  , idUser text,NombreCompleto text,Domicilio text, Colonia text,Vecindario text,Celular text,CodigoPostal text, numero text, municipio text,id int primary key AUTO_INCREMENT);

Create table roll(idPromo int, fecha text,Hora text,oferta text, status text,dato text,id int primary key AUTO_INCREMENT);

Create table images_categoria(id_foto int, img blob not null, id_Catego text, id int primary key AUTO_INCREMENT);




---------------------------------------------------------------------------------------------------------


  Create table usuarios(id_usuario int primary key Auto_increment,nombre text,celular text,contra text, terminos_condiciones text,sexo text,tipo_user text);

    Create table ticket(id_ticket int primary key Auto_increment,pedido text,precio text,fecha text, hora text,usuario text, localidad text,domicilio text,referencia text
    ,id_user int,tipo_pedido text,entregado text);

    Create table promocion(id_promo int primary key Auto_increment,pizzas text,bebida text,fecha_caduca text, fecha_creacion text,precio text,estado text,calzone text
    ,nombre_promo text,desc_promo text,photo_promo text);

    Create table cupones(codigo_cupon text,descuento text,fecha_caduca text, fecha_creacion text,estado text, id int primary key Auto_increment );

     */
}
