package com.example.monsheeppractica.fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.Activitys.MainActivityRegistroProductos;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.Lista_productoAdaptador;
import com.example.monsheeppractica.databinding.FragmentProductosBinding;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.sqlite;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductosFragment newInstance(String param1, String param2) {
        ProductosFragment fragment = new ProductosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static String producto,costo,ventaMenudeo,ventaMayoreo,
            marca,color_p,unidadMedida,categoria_nombre,id_categoria,Id_Foto,status,busqueda_index,cantidadMinima;

    public static int id_producto;
    public static ListView lv_producto;
    public static String filtroCategoria,filtroUnidad;
    //public static ImageView iv_ver_foto;
    private FragmentProductosBinding binding;


    DatabaseHandler db;
    AlertDialog.Builder alerta;
    public static ArrayList<Productos> productoArrayList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentProductosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        lv_producto = binding.lvLista;

        //Referencia a la tabla imagenes categorias
        db = new DatabaseHandler(getActivity());

        registerForContextMenu(lv_producto);

        listaproducto();

        Bundle datos = getArguments();
        // Toast.makeText(getContext(), "bde: "+datos, Toast.LENGTH_SHORT).show();
        if (datos != null){

            filtroUnidad = datos.getString("unidadFiltro","0");
            filtroCategoria = datos.getString("categoriaFiltro","0");
            busqueda_index = datos.getString("message","0");

            // Toast.makeText(getContext(), "unidad: "+filtroUnidad+"\ncategoria"+filtroCategoria+"\n Busqueda:"+busqueda_index, Toast.LENGTH_SHORT).show();



            if (filtroUnidad.equals("0") && filtroCategoria.equals("0")){
                Buscar_categoria(busqueda_index);


            }else if (busqueda_index.equals("0") || busqueda_index.equals("0")){
                BuscarFiltro(filtroCategoria,filtroUnidad);

            }else {
                listaproducto();
            }


        }
        return root;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_contextual_producto, menu);



    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int numero=item.getItemId();
        Intent intent = new Intent(getContext(), MainActivityRegistroProductos.class);
        switch (numero){



            case R.id.item_Editar_pro:

                //  Toast.makeText(getContext(), "Editar: Producto"+categoria_nombre, Toast.LENGTH_SHORT).show();

                intent.putExtra("editar","editar" );
                intent.putExtra("id_producto",""+id_producto);
                intent.putExtra("categoria_nombre",categoria_nombre );
                intent.putExtra("costo",costo );
                intent.putExtra("Id_Foto",Id_Foto);
                intent.putExtra("producto",producto);
                intent.putExtra("ventaMenudeo",ventaMenudeo);
                intent.putExtra("ventaMayoreo",ventaMayoreo);
                intent.putExtra("marca",marca);
                intent.putExtra("color_p",color_p);
                intent.putExtra("unidadMedida",unidadMedida);
                intent.putExtra("id_categoria",id_categoria);
                intent.putExtra("status",status);
                intent.putExtra("cantidadMinima",""+cantidadMinima);
                startActivity(intent);

                return  true;
            case  R.id.item_Eliminar_pro:
                //  Toast.makeText(getContext(), "Eliminar: "+codigo, Toast.LENGTH_SHORT).show();
                Alert();
                return  true;
            case  R.id.item_detalle_pro:
                // Intent intent = new Intent(getContext(), MainActivity_Registrar_Producto.class);
                intent.putExtra("editar","detalle" );
                intent.putExtra("id_producto",""+id_producto);
                intent.putExtra("categoria_nombre",categoria_nombre );
                intent.putExtra("costo",costo );
                intent.putExtra("Id_Foto",Id_Foto);
                intent.putExtra("producto",producto);
                intent.putExtra("ventaMenudeo",ventaMenudeo);
                intent.putExtra("ventaMayoreo",ventaMayoreo);
                intent.putExtra("marca",marca);
                intent.putExtra("color_p",color_p);
                intent.putExtra("unidadMedida",unidadMedida);
                intent.putExtra("id_categoria",id_categoria);
                intent.putExtra("status",status);
                intent.putExtra("cantidadMinima",""+cantidadMinima);
                startActivity(intent);
                return  true;

        }

        return super.onContextItemSelected(item);


    }


    public void Modificar_Eliminacion(){
        sqlite admin = new sqlite
                (getActivity(),"producto", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("id_categoria", "baja");
        registro.put("status", "baja");

        int cantid = BaseDeDatos.update("producto", registro, "id_producto= " + id_producto, null);

        BaseDeDatos.close();

        if ( cantid == 1) {
            //aTrabajar();
            Toast.makeText(getContext(), "Eliminada correctamente", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "Categoria no existe", Toast.LENGTH_SHORT).show();
        }

    }


    private void Alert(){
        alerta = new AlertDialog.Builder(getActivity());
        //Mensaje
        alerta.setTitle( Html.fromHtml("<font color='#269BF4'>Mensaje informativo</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("¿Estas seguro que desea eliminar "+producto+"?");

        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Mensaje 1
            }
        });

        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Mensaje 2
                Modificar_Eliminacion();
                listaproducto();

            }
        });

        alerta.show();
    }


    public void clearData() {
        // limpiar la lista
        productoArrayList.clear();
    }

    // llenar lista por producto
    public void listaproducto(){
        clearData();
        sqlite bh = new sqlite
                (getContext(), "producto", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from producto where status='Activo' order by producto asc" , null);
//
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

        String[] arreglo = new String[productoArrayList.size()];
        //Toast.makeText(getContext(), ""+arreglo.length, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < arreglo.length; i++) {


            arreglo[i] = productoArrayList.get(i).getProducto()  +
                    productoArrayList.get(i).getMarca() + productoArrayList.get(i).getId_Foto();
            //Toast.makeText(getContext(), ""+arreglo, Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arreglo);
        //lv_producto.setAdapter(adaptador);
        lv_producto.setAdapter(new Lista_productoAdaptador.listaP(getContext(),productoArrayList));

        ListarObtenerDatos();
        db.close();

    }

    public void ListarObtenerDatos(){

        lv_producto.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                id_producto = productoArrayList.get(i).getId_producto();//Int
                Id_Foto = productoArrayList.get(i).getId_Foto();
                categoria_nombre = productoArrayList.get(i).getCategoria_nombre();
                costo = productoArrayList.get(i).getCosto();
                producto = productoArrayList.get(i).getProducto();
                ventaMenudeo = productoArrayList.get(i).getVentaMenudeo();
                ventaMayoreo = productoArrayList.get(i).getVentaMayoreo();
                marca = productoArrayList.get(i).getMarca();
                color_p = productoArrayList.get(i).getColor_p();
                unidadMedida = productoArrayList.get(i).getUnidadMedida();
                id_categoria = productoArrayList.get(i).getId_categoria();
                status = productoArrayList.get(i).getStatus();
                cantidadMinima = productoArrayList.get(i).getCantidadMinima();//Int

                return false;
            }
        });

    }
    public void BuscarFiltro(String categoria, String unidad) {
        clearData();
        sqlite bh = new sqlite
                (getContext(), "producto", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from producto where status='Activo' and categoria_nombre LIKE '"+categoria+ "%' and unidadMedida LIKE '"+unidad+"%'", null);

            if (c.moveToFirst()) {

                do {
                    productoArrayList.add(new Productos(c.getInt(0), c.getString(1), c.getString(2),
                            c.getString(3),c.getString(4),c.getString(5), c.getString(6),
                            c.getString(7),c.getString(8), c.getString(9),c.getString(10),
                            c.getString(11),c.getString(12),c.getString(13),
                            c.getString(14),c.getString(15),c.getString(16),c.getString(17),c.getString(18)));
                } while (c.moveToNext());

            }

            String[] arreglo = new String[productoArrayList.size()];

            for (int i = 0; i < arreglo.length; i++) {


                //  arreglo[i] = productoArrayList.get(i).getCategoria() + productoArrayList.get(i).getDescripcion() + productoArrayList.get(i).getStatus();

            }

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arreglo);
            //lv_lista.setAdapter(adaptador);
            lv_producto.setAdapter(new Lista_productoAdaptador.listaP(getContext(),productoArrayList));
            ListarObtenerDatos();

        }
    }
    public void Buscar_categoria(String palabra) {
        clearData();
        sqlite bh = new sqlite
                (getContext(), "producto", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from producto where status='Activo' and categoria_nombre LIKE '"+palabra+ "%' or producto LIKE '"+palabra+"%'", null);

            if (c.moveToFirst()) {

                do {
                    productoArrayList.add(new Productos(c.getInt(0), c.getString(1), c.getString(2),
                            c.getString(3),c.getString(4),c.getString(5), c.getString(6),
                            c.getString(7),c.getString(8), c.getString(9),c.getString(10),
                            c.getString(11),c.getString(12),c.getString(13),
                            c.getString(14),c.getString(15),c.getString(16),c.getString(17),c.getString(18)));
                } while (c.moveToNext());

            }

            String[] arreglo = new String[productoArrayList.size()];

            for (int i = 0; i < arreglo.length; i++) {


                //  arreglo[i] = productoArrayList.get(i).getCategoria() + productoArrayList.get(i).getDescripcion() + productoArrayList.get(i).getStatus();

            }

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arreglo);
            //lv_lista.setAdapter(adaptador);
            lv_producto.setAdapter(new Lista_productoAdaptador.listaP(getContext(),productoArrayList));
            ListarObtenerDatos();

        }
    }


}