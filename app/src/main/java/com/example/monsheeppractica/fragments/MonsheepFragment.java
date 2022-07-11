package com.example.monsheeppractica.fragments;

import static com.example.monsheeppractica.adaptadores.ListaProductoPostAdaptador.*;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.monsheeppractica.Activitys.MainActivityRegistroProductos;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.Activitys.MainActivityDetalles;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.AdaptadorPost;
import com.example.monsheeppractica.adaptadores.Lista_productoAdaptador;
import com.example.monsheeppractica.adaptadores.TestNormalAdapter;
import com.example.monsheeppractica.databinding.FragmentMonsheepBinding;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.EditarBaja;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;
import com.example.monsheeppractica.sqlite.sqlite;
import com.jude.rollviewpager.RollPagerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonsheepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsheepFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MonsheepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonsheepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonsheepFragment newInstance(String param1, String param2) {
        MonsheepFragment fragment = new MonsheepFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static String producto, costo, ventaMenudeo, ventaMayoreo,
            marca, color_p, unidadMedida, categoria_nombre, id_categoria,
            Id_Foto, status, busqueda_index, cantidadMinima, NombreUser, idUser, idFotoUser;

    public static int id_producto;
    RecyclerView Rv;
    //    public static ListView lv_producto;
    public static String filtroCategoria, filtroUnidad;
    //public static ImageView iv_ver_foto;
    private FragmentMonsheepBinding binding;

    DatabaseHandler db;
    AlertDialog.Builder alerta;
    public static ArrayList<Productos> productoArrayList = new ArrayList<>();
    private RollPagerView mRollViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        binding = FragmentMonsheepBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        idUser = getActivity().getIntent().getStringExtra("idusers");
        NombreUser = getActivity().getIntent().getStringExtra("NombreUser");
        idFotoUser = getActivity().getIntent().getStringExtra("idFotoUser");
//Evitar el teclado
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(root.getWindowToken(), 0);
        // Toast.makeText(getActivity(), ""+idUser+NombreUser+idFotoUser, Toast.LENGTH_SHORT).show();

        mRollViewPager = binding.rollViewPager;

        //Referencia a la tabla imagenes categorias
        db = new DatabaseHandler(getActivity());

        registerForContextMenu(binding.rV);

        listaproducto();

        Bundle datos = getArguments();
        // Toast.makeText(getContext(), "bde: "+datos, Toast.LENGTH_SHORT).show();
        if (datos != null) {

            filtroUnidad = datos.getString("unidadFiltro", "0");
            filtroCategoria = datos.getString("categoriaFiltro", "0");
            busqueda_index = datos.getString("message", "0");

            // Toast.makeText(getContext(), "unidad: "+filtroUnidad+"\ncategoria"+filtroCategoria+"\n Busqueda:"+busqueda_index, Toast.LENGTH_SHORT).show();

            if (filtroUnidad.equals("0") && filtroCategoria.equals("0")) {
                Buscar_categoria(busqueda_index);

            } else if (busqueda_index.equals("0") || busqueda_index.equals("0")) {
                BuscarFiltro(filtroCategoria, filtroUnidad);

            } else {
                listaproducto();
            }


        }
        return root;
    }


    public void Modificar_Eliminacion() {
        EditarBaja editarBaja = new EditarBaja(getActivity());
        editarBaja.BajaProducto(id_producto);

    }


    private void Alert() {
        alerta = new AlertDialog.Builder(getActivity());
        //Mensaje
        alerta.setTitle(Html.fromHtml("<font color='#269BF4'>Mensaje informativo</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("¿Estas seguro que desea eliminar " + producto + "?");

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
    public void listaproducto() {
        ExtraerDatos("all", null, null, null);
        mRollViewPager.setAdapter(new TestNormalAdapter(getActivity(), productoArrayList));

    }

    /*  public void ListarObtenerDatos(){

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
                  NombreUser = productoArrayList.get(i).getNombreUser();//Int

                  return false;
              }
          });

      }*/
    public void BuscarFiltro(String categoria, String unidad) {
        ExtraerDatos("filtro", null, categoria, unidad);

    }

    public void Buscar_categoria(String palabra) {
        ExtraerDatos("like", palabra, null, null);

    }

    public void ExtraerDatos(String clave, String palabra, String categoria, String unidad) {
        clearData();
        ConsultarTabla consultarTabla = new ConsultarTabla(getActivity());

        //  AdaptadorPost customAdapter = new AdaptadorPost(productoArrayList, getActivity(),);
        binding.rV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rV.setAdapter(new AdaptadorPost(consultarTabla.PostConsulta(productoArrayList, clave, palabra, categoria, unidad),
                getActivity(), idUser, NombreUser, idFotoUser));

    }
}
