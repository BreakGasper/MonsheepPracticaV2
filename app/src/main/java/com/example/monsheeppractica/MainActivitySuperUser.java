package com.example.monsheeppractica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.monsheeppractica.Activitys.MainActivityRegistroCategorias;
import com.example.monsheeppractica.GetterAndSetter.Categorias;
import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.adaptadores.ListaCategoriaAdapter;
import com.example.monsheeppractica.adaptadores.ListaClientesAdaptador;
import com.example.monsheeppractica.adaptadores.ListaUsuarioAdaptador;
import com.example.monsheeppractica.adaptadores.Lista_productoAdaptador;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;

import java.util.ArrayList;

public class MainActivitySuperUser extends AppCompatActivity {
    SearchView txt_buscar;
    ImageView ivRegister;
    RadioButton rbProducto, rbCategoria, rbNegocio, rbUsuario;
    ListView lista;
    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio, like = "producto";
    public static ArrayList<Categorias> categoriasArrayList = new ArrayList<>();
    public static ArrayList<Productos> productosArrayList = new ArrayList<>();
    public static ArrayList<Negocio> negociosArrayList = new ArrayList<>();
    public static ArrayList<Clientes> usuariosArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_super_user);

        SharedPreferences preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");

        lista = findViewById(R.id.lvSuperUser);
        rbCategoria = findViewById(R.id.radio1);
        rbProducto = findViewById(R.id.radio0);
        rbNegocio = findViewById(R.id.radio3);
        rbUsuario = findViewById(R.id.radio2);
        txt_buscar = findViewById(R.id.txtBuscar);
        ivRegister = findViewById(R.id.ivRegister);

        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        lista.setAdapter(new Lista_productoAdaptador.listaP(this,
                consultarTabla.ProductoConsulta(productosArrayList, "all", "", "")));


        ivRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (like) {
                    case "producto":

                        break;
                    case "categoria":
                        Intent intent = new Intent(getApplicationContext(), MainActivityRegistroCategorias.class);
                        startActivity(intent);
                        break;
                    case "negocio":

                        break;
                    case "usuario":

                        break;
                }
            }
        });
        txt_buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                try {
                    BuscarAll(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                try {
                    BuscarAll(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });


    }

    public void BuscarAll(String s) {
        clearData();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);

        switch (like) {
            case "producto":
                lista.setAdapter(new Lista_productoAdaptador.listaP(getApplicationContext(),
                        consultarTabla.ProductoConsulta(productosArrayList, "allLike", s, s)));
                break;
            case "categoria":

                    lista.setAdapter(new ListaCategoriaAdapter(getApplicationContext(),
                            consultarTabla.CategoriaConsulta(categoriasArrayList, "search",
                                    s,null,null)));

                break;
            case "negocio":
                lista.setAdapter(new ListaClientesAdaptador.listaP(getApplicationContext(),
                        consultarTabla.NegocioConsulta(negociosArrayList, "allLike",
                                s, s, s)));
                break;
            case "usuario":
                lista.setAdapter(new ListaUsuarioAdaptador.listaP(getApplicationContext(),
                        consultarTabla.ClientesConsulta(usuariosArrayList, "allLike",
                                0, s)));
                break;
        }
    }

    public void Selector(View view) {
        clearData();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);

        if (rbProducto.isChecked() == true) {
            lista.setAdapter(new Lista_productoAdaptador.listaP(this,
                    consultarTabla.ProductoConsulta(productosArrayList, "all", "", "")));
            like = "producto";
        } else if (rbCategoria.isChecked() == true) {
            lista.setAdapter(new ListaCategoriaAdapter(this,
                    consultarTabla.CategoriaConsulta(categoriasArrayList, "all",
                            "", null, null)));
            like = "categoria";
        } else if (rbNegocio.isChecked() == true) {

            lista.setAdapter(new ListaClientesAdaptador.listaP(this,
                    consultarTabla.NegocioConsulta(negociosArrayList, "all",
                            "", "", "")));
            like = "negocio";
        } else if (rbUsuario.isChecked() == true) {
            lista.setAdapter(new ListaUsuarioAdaptador.listaP(this,
                    consultarTabla.ClientesConsulta(usuariosArrayList, "all",
                            0, "")));
            like = "usuario";
        }
    }

    public void clearData() {
        // limpiar la lista
        categoriasArrayList.clear();
        productosArrayList.clear();
        negociosArrayList.clear();
        usuariosArrayList.clear();
    }


}