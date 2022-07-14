package com.example.monsheeppractica.sqlite.registros;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.GetterAndSetter.Categorias;
import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.GetterAndSetter.Comentario;
import com.example.monsheeppractica.GetterAndSetter.Domicilios;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.GetterAndSetter.Seguidores;
import com.example.monsheeppractica.sqlite.sqlite;

import java.util.ArrayList;

public class ConsultarTabla {
    Context context;

    public ConsultarTabla(Context context) {
        this.context = context;
    }

    public ArrayList PostConsulta(ArrayList productoArrayList, String clave, String word,
                                  String categoria, String unidad) {
        sqlite bh = new sqlite
                (context, "producto", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = null;
            if (clave.equals("all")) {
                c = db.rawQuery("select * from producto where status='Activo' and cantidadMinima >=1  order by fecha desc LIMIT 100 ", null);
            } else if (clave.equals("like")) {
                c = db.rawQuery("select * from producto where status='Activo' and cantidadMinima >=1 and categoria_nombre LIKE '" + word + "%' or producto LIKE '" + word + "%'", null);

            } else if (clave.equals("filtro")) {
                c = db.rawQuery("select * from producto where status='Activo' and cantidadMinima >=1 and categoria_nombre LIKE '" + categoria + "%' and unidadMedida LIKE '" + unidad + "%'", null);

            } else if (clave.equals("catego")) {
                c = db.rawQuery("select * from producto where status='Activo' and cantidadMinima >=1 and categoria_nombre LIKE '" + categoria + "%'", null);

            } else if (clave.equals("costoMenor")) {
                c = db.rawQuery("select * from producto where status='Activo' and cantidadMinima >=1  order by costo asc", null);

            } else if (clave.equals("costoMayor")) {
                c = db.rawQuery("select * from producto where status='Activo' and cantidadMinima >=1 order by costo desc", null);

            } else if (clave.equals("costo")) {
                c = db.rawQuery("select * from producto where status='Activo'and cantidadMinima >=1  and costo < '50' order by costo asc", null);

            } else if (clave.equals("precio")) {
                c = db.rawQuery("select * from producto where status='Activo' and cantidadMinima >=1 and costo > '49' order by costo asc", null);

            }
            if (c.moveToFirst()) {
                do {
                    productoArrayList.add(new Productos(c.getInt(0), c.getString(1), c.getString(2),
                            c.getString(3),c.getString(4),c.getString(5), c.getString(6),
                            c.getString(7),c.getString(8), c.getString(9),c.getString(10),
                            c.getString(11),c.getString(12),c.getString(13),
                            c.getString(14),c.getString(15),c.getString(16),c.getString(17),c.getString(18)));

                } while (c.moveToNext());

            }
        }

        return productoArrayList;
    }

    public ArrayList ComentarioConsulta(ArrayList ArrayList, String clave, String word, String categoria, String unidad) {

        sqlite bh = new sqlite
                (context, "comentario", null, 1);
        if (bh != null) {
            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = null;

            if (clave.equals("pro")) {
                c = db.rawQuery("select * from comentario where status='Activo' and idProducto='" + word + "' order by comentario asc", null);
            }


            if (c.moveToFirst()) {

                do {

                    ArrayList.add(new Comentario(c.getInt(0), c.getString(1), c.getString(2),
                            c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8)));

                } while (c.moveToNext());

            }
        }

        return ArrayList;
    }

    /*---------------------------CATEGORIAS-----------------------*/

    public ArrayList CategoriaConsulta(ArrayList ArrayList, String clave,
                                       String word, String categoria, String unidad) {
        sqlite bh = new sqlite
                (context, "categoria", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = null;
            if (clave.equals("all")) {
                c = db.rawQuery("select * from categoria where status='Activo' order by categoria asc", null);

            } else if (clave.equals("search")) {
                c = db.rawQuery("select * from categoria where  status='Activo' and categoria LIKE '" + word + "%'  or Descripcion LIKE '" + word + "%'", null);

            }
            if (c.moveToFirst()) {

                do {
                    ArrayList.add(new Categorias(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5)));


                } while (c.moveToNext());

            }
        }

        return ArrayList;
    }

    /*-----------------PRODUCTOS----------------------*/


    public ArrayList ProductoConsulta(ArrayList ArrayList, String clave,
                                      String idUser, String word) {
        sqlite bh = new sqlite
                (context, "producto", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();


            Cursor c = null;
            if (clave.equals("all")) {
                c = db.rawQuery("select * from producto where  status='Activo' order by producto asc", null);

            } else if (clave.equals("allLike")) {
                c = db.rawQuery("select * from producto where  status='Activo' and  producto LIKE'" + word + "%' order by producto asc", null);

            } else if (clave.equals("user")) {
                c = db.rawQuery("select * from producto where idUser=='" + idUser + "' and status='Activo' order by producto asc", null);

            } else if (clave.equals("like")) {
                c = db.rawQuery("select * from producto where idUser= '" + idUser + "' and status='Activo' and producto Like '" + word + "%'order by producto asc", null);

            } else if (clave.equals("negocio")) {
                c = db.rawQuery("select * from producto where idNegocio=='" + idUser + "' and status='Activo' order by producto asc", null);

            }

            if (c.moveToFirst()) {

                do {

                    ArrayList.add(new Productos(c.getInt(0), c.getString(1), c.getString(2),
                            c.getString(3),c.getString(4),c.getString(5), c.getString(6),
                            c.getString(7),c.getString(8), c.getString(9),c.getString(10),
                            c.getString(11),c.getString(12),c.getString(13),
                            c.getString(14),c.getString(15),c.getString(16),c.getString(17),c.getString(18)));

                } while (c.moveToNext());

            }
        }


        return ArrayList;
    }

    /*-----------------ClientesUsuarios----------------------*/


    public ArrayList ClientesConsulta(ArrayList ArrayList, String clave, int idCliente, String idUser) {
        sqlite bh = new sqlite
                (context, "clientes", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = null;
            if (clave.equals("all")) {
                c = db.rawQuery("select * from clientes where status='Activo' order by Nombre asc", null);
            } else if (clave.equals("allLike")) {
                c = db.rawQuery("select * from clientes where status='Activo' and  Nombre LIKE'" + idUser + "%' order by Nombre asc", null);
            } else if (clave.equals("user")) {
                c = db.rawQuery("select * from clientes where id_cliente=" + idCliente, null);
            } else if (clave.equals("seguidor")) {
                c = db.rawQuery("select * from clientes where id_cliente=" + idCliente, null);
            }

//
            if (c.moveToFirst()) {

                do {

                    ArrayList.add(new Clientes(c.getInt(0), c.getString(1), c.getString(2),
                            c.getString(3), c.getString(4), c.getString(5), c.getString(6),
                            c.getString(7), c.getString(8), c.getString(9), c.getString(10),
                            c.getString(11), c.getString(12), c.getString(13),
                            c.getString(14), c.getString(15), c.getString(16),
                            c.getString(17), c.getString(18), c.getString(19)));

                } while (c.moveToNext());

            }
        }

        return ArrayList;
    }

    /********************************** Seguidores***********************************/
    public ArrayList SeguirConsulta(ArrayList ArrayList, String clave, String idUser, String idSeguidor, String word) {

        sqlite bh = new sqlite
                (context, "seguir", null, 1);
        if (bh != null) {
            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = null;

            if (clave.equals("pro")) {
                c = db.rawQuery("select * from seguir where status='Activo' and idUser='" + idUser + "' and idSeguidor='" + idSeguidor + "' order by nombreSeguidor asc", null);
            }
            if (clave.equals("userlist")) {
                c = db.rawQuery("select * from seguir where status='Activo' and idUser='" + idUser + "' order by nombreSeguidor asc", null);
            }


            if (c.moveToFirst()) {

                do {

                    ArrayList.add(new Seguidores(c.getInt(0), c.getString(1), c.getString(2),
                            c.getString(3), c.getString(4)));

                } while (c.moveToNext());

            }
        }

        return ArrayList;
    }

    /******************************Negocio*********************/
    public ArrayList NegocioConsulta(ArrayList ArrayList, String clave, String idUser, String idSeguidor, String word) {

        sqlite bh = new sqlite
                (context, "negocio", null, 1);
        if (bh != null) {
            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = null;

            if (clave.equals("all")) {
                c = db.rawQuery("select * from negocio where status='Activo' ", null);
            }
            if (clave.equals("allLike")) {
                c = db.rawQuery("select * from negocio where status='Activo' and  Nombre LIKE '" + word + "%' ", null);
            }
            if (clave.equals("like")) {
                c = db.rawQuery("select * from negocio where status=='Activo' and idNegocio==" + Integer.parseInt(idUser) + "", null);
            }
            if (clave.equals("pedido")) {
                c = db.rawQuery("select * from negocio where idNegocio==" + Integer.parseInt(idUser) + "", null);
            }
//

            if (c.moveToFirst()) {

                do {

                    ArrayList.add(new Negocio(c.getInt(0), c.getInt(1), c.getString(2),
                            c.getString(3), c.getString(4), c.getString(5), c.getString(6),
                            c.getString(7), c.getString(8), c.getString(9), c.getString(10),
                            c.getString(11), c.getString(12), c.getString(13),
                            c.getString(14), c.getString(15), c.getString(16),
                            c.getString(17), c.getString(18)));

                } while (c.moveToNext());

            }
        }

        return ArrayList;
    }

    public ArrayList CarritoConsulta(ArrayList ArrayList, String clave, String idUser, String idProducto, String unidad) {

        sqlite bh = new sqlite
                (context, "carrito", null, 1);
        if (bh != null) {
            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c =  db.rawQuery("select * from carrito", null);;

            if (clave.equals("count")) {
                c = db.rawQuery("select * from carrito", null);
            }
            if (clave.equals("counts")) {
                c = db.rawQuery("select * from carrito  where   idUser=='"+idUser+"' ", null);
                //  where status='Activo'  and  idUser=='"+idUser+"'Count DistinctDistinct idProducto,Productos,cantidad,precio,status
            }if (clave.equals("Validate")) {
                c = db.rawQuery("select * from carrito  where  idUser=='"+idUser+"' and idProducto=='"+idProducto+"'", null);
                //  where status='Activo'  and  idUser=='"+idUser+"'Count DistinctDistinct idProducto,Productos,cantidad,precio
            }


            if (c.moveToFirst()) {

                do {

                    ArrayList.add(new Carrito(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10)));

                } while (c.moveToNext());

            }
        }

        return ArrayList;
    }

    /******************************Domicilio*********************/
    public ArrayList DomicilioConsulta(ArrayList ArrayList, String clave, int idCliente, String idUser) {
        sqlite bh = new sqlite
                (context, "domicilios", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = null;
            if (clave.equals("all")) {
                c = db.rawQuery("select * from domicilios order by NombreCompleto asc", null);
            } else  if (clave.equals("user")) {
                c = db.rawQuery("select * from domicilios where idUser='" + idCliente+"'", null);
            }

//
            if (c.moveToFirst()) {

                do {

                    ArrayList.add(new Domicilios(c.getInt(0), c.getString(1), c.getString(2),
                            c.getString(3), c.getString(4), c.getString(5), c.getString(6),
                            c.getString(7), c.getString(8), c.getString(9)));

                } while (c.moveToNext());

            }
        }

        return ArrayList;
    }

}
