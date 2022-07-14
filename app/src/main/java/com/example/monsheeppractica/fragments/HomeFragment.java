package com.example.monsheeppractica.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.monsheeppractica.Activitys.MainActivityRegistroProductos;
import com.example.monsheeppractica.GetterAndSetter.Categorias;
import com.example.monsheeppractica.Activitys.MainActivityRegistroCategorias;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.AdaptadorPost;
import com.example.monsheeppractica.adaptadores.ListaCategoriaAdapter;
import com.example.monsheeppractica.adaptadores.TestNormalAdapter;
import com.example.monsheeppractica.databinding.FragmentHomeBinding;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.EliminarTabla;
import com.example.monsheeppractica.sqlite.sqlite;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    AlertDialog.Builder alerta;
    //Categoria
    public static ListView lv_catego;
    public static ImageView iv_ver_foto;
    public static int codigo = 0;
    public static String codigo_imagen = "", nombre_categoria = "", descripcion_categoria = "", busqueda_index;
    DatabaseHandler db;
    Spinner sp_categoria, spfiltro;
    RadioButton rB1, rB2, rB4;
    String costo;
    String[] Unidad_de_medida = {"Menor a Mayor", "Mayor a Menor", "Menos de $50", "Mayor a $50"};
    SearchView txt_buscar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static ArrayList<Categorias> categoriasArrayList = new ArrayList<>();
    public static ArrayList<Productos> productoArrayList = new ArrayList<>();

    String NombreUser, idUser, idFotoUser, tipoUser;
    String clave = "";
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        txt_buscar = binding.txtBuscar;
        swipeRefreshLayout = binding.swipeRefreshLayout;

        txt_buscar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                binding.dailyWeeklyButtonView.setVisibility(View.VISIBLE);
                BuscarProductoCat(nombre_categoria, "all");

                return false;
            }
        });


        txt_buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Toast.makeText(getActivity(), "1: "+s, Toast.LENGTH_SHORT).show();
                BuscarProductoCat(s, "like");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (s.isEmpty()) {
                    binding.dailyWeeklyButtonView.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(root.getWindowToken(), 0);
                } else {
                    binding.dailyWeeklyButtonView.setVisibility(View.GONE);
                    spfiltro.setVisibility(View.GONE);
                    sp_categoria.setVisibility(View.GONE);
                }
                BuscarProductoCat(s, "like");

                return false;
            }
        });

        lv_catego = binding.lvListaCategoria;
        iv_ver_foto = binding.ivImgFot;
        rB1 = binding.radio0;
        rB2 = binding.radio1;
        spfiltro = binding.spCategorias2;
        rB4 = binding.radio3;
        iv_ver_foto.setVisibility(View.INVISIBLE);
        sp_categoria = binding.spCategorias;
//        idUser = getActivity().getIntent().getStringExtra("idusers");
//        NombreUser = getActivity().getIntent().getStringExtra("NombreUser");
//        idFotoUser = getActivity().getIntent().getStringExtra("idFotoUser");

        SharedPreferences preferences = getActivity().getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");

        //Referencia a la tabla imagenes categorias
        db = new DatabaseHandler(getActivity());

        registerForContextMenu(lv_catego);

        //listaporcategoria();
        spinner_categoria();

        Bundle datos = getArguments();
        // Toast.makeText(getContext(), "bde: "+datos, Toast.LENGTH_SHORT).show();
        if (datos != null) {

            busqueda_index = datos.getString("message", "0");
            // Toast.makeText(getContext(), "mundo: "+busqueda_index, Toast.LENGTH_SHORT).show();
            //   clearData();
            if (busqueda_index == null || busqueda_index.isEmpty()) {
                spinner_categoria();
            } else Buscar_categoria(busqueda_index);

        }
        BuscarProductoCat(nombre_categoria, "all");
        rB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadiosClic();
            }
        });
        rB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadiosClic();
            }
        });

        rB4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadiosClic();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Esto se ejecuta cada vez que se realiza el gesto
                BuscarProductoCat(nombre_categoria, "all");
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        return root;
    }

    public void RadiosClic() {

        if (rB1.isChecked() == true) {
            //All
            sp_categoria.setVisibility(View.GONE);
            spfiltro.setVisibility(View.GONE);
            BuscarProductoCat(nombre_categoria, "all");
        }
        if (rB2.isChecked() == true) {
            //Categoriaas
            sp_categoria.setVisibility(View.VISIBLE);
            spfiltro.setVisibility(View.GONE);

//            Toast.makeText(getActivity(), ""+nombre_categoria, Toast.LENGTH_SHORT).show();

            BuscarProductoCat(nombre_categoria, "catego");
            //listaporcategoria();
        }
        if (rB4.isChecked() == true) {
            //precio costo
            sp_categoria.setVisibility(View.GONE);
            spfiltro.setVisibility(View.VISIBLE);

            try {
                spinner_medida();


            } catch (Exception e) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }

            // BuscarProductoCat(nombre_categoria,"catego");
        }


    }

    public void spinner_medida() {

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(), R.layout.adaptadorspinner, Unidad_de_medida);
        spfiltro.setAdapter(adapter);
        //sp_unidad_medida.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
        // android.R.layout.simple_spinner_dropdown_item,Unidad_de_medida));
        spfiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(adapterView.getContext(),(String) adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                costo = (String) adapterView.getItemAtPosition(i);

                if (costo.equals("Menor a Mayor")) {
                    clave = "costoMenor";
                }
                if (costo.equals("Mayor a Menor")) {
                    clave = "costoMayor";
                }
                if (costo.equals("Menos de $50")) {
                    clave = "costo";
                }
                if (costo.equals("Mayor a $50")) {
                    clave = "precio";
                }
                BuscarProductoCat(nombre_categoria, clave);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_contextual_1, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int numero = item.getItemId();

        switch (numero) {


            case R.id.item_Editar:

                // Toast.makeText(getContext(), "Editar: "+categoriasArrayList.get(0).getId_catgoria(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainActivityRegistroCategorias.class);
                intent.putExtra("editar", "editar");
                intent.putExtra("code", "" + codigo);
                intent.putExtra("categoria", nombre_categoria);
                intent.putExtra("Descripcion", descripcion_categoria);
                intent.putExtra("id_foto", codigo_imagen);
                startActivity(intent);
                //   getActivity().onBackPressed();
                return true;
            case R.id.item_Eliminar:
                //  Toast.makeText(getContext(), "Eliminar: "+codigo, Toast.LENGTH_SHORT).show();
                Alert();
                return true;
            case R.id.item_imagen:
                //   Toast.makeText(getContext(), "Imagen: "+codigo_imagen, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), "Codigo Imagen"+codigo_imagen, Toast.LENGTH_SHORT).show();
                if (!codigo_imagen.isEmpty()) {

                    iv_ver_foto.setVisibility(View.VISIBLE);
                    //Enviar imagen al imageView
                    iv_ver_foto.setImageBitmap(db.getimage("" + codigo_imagen));
                    //Set ImageView alpha to zero
                    iv_ver_foto.setAlpha(0f);

                    //Animate the alpha value to 1f and set duration as 1.5 secs.
                    iv_ver_foto.animate().alpha(1f).setDuration(1500);


                    iv_ver_foto.setVisibility(View.VISIBLE);
                    duracionDeImagen();
                } else {
                    Toast.makeText(getContext(), "no hay imagen para esta categoría", Toast.LENGTH_SHORT).show();
                }
                return true;


        }

        return super.onContextItemSelected(item);


    }


    public void Modificar_Eliminacion() {
        EliminarTabla tEliminar = new EliminarTabla();
        if (tEliminar.EliminarCategoria(getActivity(), codigo) == 1) {
            Toast.makeText(getContext(), "Categoria eliminada correctamente", Toast.LENGTH_SHORT).show();
            listaporcategoria();
        } else {
            Toast.makeText(getContext(), "Categoria no existe", Toast.LENGTH_SHORT).show();
        }

    }

    public void validacion_de_elace_a_productos() {

        sqlite bh = new sqlite
                (getContext(), "producto", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from producto where id_categoria = '" + codigo + "'", null);

            if (c.moveToFirst()) {
                do {
                    Alert_producto_enlasado();
                    //Toast.makeText(getApplicationContext(), "Categoria !Ya existe!", Toast.LENGTH_SHORT).show();
                } while (c.moveToNext());
            } else {
                Modificar_Eliminacion();

            }
        }
    }

    private void Alert_producto_enlasado() {
        alerta = new AlertDialog.Builder(getActivity());
        //Mensaje
        alerta.setTitle(Html.fromHtml("<font color='#269BF4'>Mensaje informativo</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("La categoría no puede eliminarse ya que hay productos asociados a ella");

        alerta.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Mensaje 1
            }
        });
        alerta.show();
    }

    private void Alert() {
        alerta = new AlertDialog.Builder(getActivity());
        //Mensaje
        alerta.setTitle(Html.fromHtml("<font color='#269BF4'>Mensaje informativo</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("¿Estas seguro que desea eliminar la categoria " + nombre_categoria + "?");

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
                validacion_de_elace_a_productos();

            }
        });

        alerta.show();
    }

    private void duracionDeImagen() {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                iv_ver_foto.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    public void clearData() {
        // limpiar la lista
        categoriasArrayList.clear();
        productoArrayList.clear();
    }

    // llenar lista por categoria
    public void listaporcategoria() {
        clearData();
        ConsultarTabla consultarTabla = new ConsultarTabla(getActivity());

        lv_catego.setAdapter(new ListaCategoriaAdapter(getContext(), consultarTabla.CategoriaConsulta(categoriasArrayList, "all", null, null, null)));
        GetDataArray();

    }

    public void BuscarPorSearchView(String filtro) {
        BuscarProductoCat(filtro, "filtro");

    }

    public void GetDataArray() {
        lv_catego.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                codigo = categoriasArrayList.get(i).getId_catgoria();
                codigo_imagen = categoriasArrayList.get(i).getId_foto();
                nombre_categoria = categoriasArrayList.get(i).getCategoria();
                descripcion_categoria = categoriasArrayList.get(i).getDescripcion();
                busqueda_index = categoriasArrayList.get(i).getCategoria();

                return false;
            }
        });
        //((MainActivity_index)getActivity()).metodo(String);

    }

    public void Buscar_categoria(String palabra) {
        clearData();

        ConsultarTabla consultarTabla = new ConsultarTabla(getActivity());

        lv_catego.setAdapter(new ListaCategoriaAdapter(getActivity(),
                consultarTabla.CategoriaConsulta(categoriasArrayList, "search",
                        palabra, null, null)));
        lv_catego.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        lv_catego.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                codigo = categoriasArrayList.get(i).getId_catgoria();
                codigo_imagen = categoriasArrayList.get(i).getId_foto();
                nombre_categoria = categoriasArrayList.get(i).getCategoria();
                descripcion_categoria = categoriasArrayList.get(i).getDescripcion();


                return false;
            }
        });

    }

    public void BuscarProductoCat(String word, String clave) {
        productoArrayList.clear();
        ConsultarTabla consultarTabla = new ConsultarTabla(getActivity());

        binding.rVcatego.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rVcatego.setAdapter(new AdaptadorPost(consultarTabla.PostConsulta
                (productoArrayList, clave, word, word, "null"),
                getActivity(), idUser, NombreUser, idFotoUser,getActivity()));


    }

    public void BuscarP(Context context, String word) {
        productoArrayList.clear();
        ConsultarTabla consultarTabla = new ConsultarTabla(context);

        binding.rVcatego.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.rVcatego.setAdapter(new AdaptadorPost(consultarTabla.PostConsulta
                (productoArrayList, clave, "", word, "null"),
                context, idUser, NombreUser, idFotoUser,getActivity()));
    }

    public void spinner_categoria() {
        categoriasArrayList.clear();
        //productoArrayList.clear();
        ConsultarTabla consultarTabla = new ConsultarTabla(getActivity());
        sp_categoria.setAdapter(new ListaCategoriaAdapter(getActivity(),
                consultarTabla.CategoriaConsulta(categoriasArrayList, "all", null, null, null)));

        sp_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                nombre_categoria = categoriasArrayList.get(i).getCategoria();
                //   Toast.makeText(getContext(), ""+nombre_categoria+ idUser+nombre_categoria, Toast.LENGTH_SHORT).show();

                try {
                    BuscarProductoCat(nombre_categoria, "catego");

                } catch (Exception e) {
                    Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}