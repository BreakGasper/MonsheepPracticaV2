package com.example.monsheeppractica.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.GetterAndSetter.Domicilios;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.AdaptadorCarrito;
import com.example.monsheeppractica.adaptadores.AdaptadorNegocioPerfil;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;

import java.util.ArrayList;

public class MainActivityTicket extends AppCompatActivity {
    SharedPreferences preferences;
    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio;
    ArrayList<Domicilios> domiciliosArray = new ArrayList<>();
    ArrayList<Negocio> negocioArray = new ArrayList<>();
    ArrayList<Carrito> carritoArray = new ArrayList<>();
    TextView tvDatosUsuario;
    RecyclerView lv_lista,rvListadoProvedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ticket);
//        try {
//            getSupportActionBar().hide();
//            getActionBar().hide();
//        } catch (Exception e) {
//
//            //  Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
//        }
        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");
        tvDatosUsuario = findViewById(R.id.tvDatosTicket);
        lv_lista = findViewById(R.id.rvListaTicket);
        rvListadoProvedor = findViewById(R.id.rvProvedorestickets);

        try {
            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            consultarTabla.DomicilioConsulta(domiciliosArray, "user", Integer.parseInt(idUser), idUser);
            Domicilios user = domiciliosArray.get(0);
            tvDatosUsuario.setText(user.getNombreCompleto()+"\nMi contacto: "+user.getCelular()
                    +"\n"+user.getDomicilio()+" "+user.getNumero()
                    +"\n"+user.getVecindario()+" "+user.getColonia()+"\n"+user.getMunicipio());
        }catch (Exception e){
            System.out.println(e);
            //Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
        pedido();
        proveedores();
    }

    void pedido(){
        try {
            carritoArray.clear();
            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            lv_lista.setLayoutManager(layoutManager);
            lv_lista.setAdapter(new AdaptadorCarrito(this,
                    consultarTabla.CarritoConsulta(carritoArray, "counts", idUser, "", "")
                    ,MainActivityTicket.this, "ticket"));

        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            System.out.println(e);
        }
    }
    void proveedores(){
        negocioArray.clear();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListadoProvedor.setLayoutManager(layoutManager);
        String[] arreglo = new String[ carritoArray.size()];

        for (int i = 0; i < arreglo.length; i++) {

            //arreglo[i] = ""+Integer.parseInt(carritoArray.get(i).getCantidad());

            if (i==0){
                rvListadoProvedor.setAdapter(new AdaptadorNegocioPerfil(
                        consultarTabla.NegocioConsulta(negocioArray, "pedido", carritoArray.get(i).getIdproveedor(),"","")
                        ,this,"", "ticket",""));
            }else {
                if (!carritoArray.get(i).getIdproveedor().equals(carritoArray.get(i-1).getIdproveedor())){
                    //Toast.makeText(this, ""+carritoArray.get(i).getIdproveedor(), Toast.LENGTH_SHORT).show();
                    rvListadoProvedor.setAdapter(new AdaptadorNegocioPerfil(
                            consultarTabla.NegocioConsulta(negocioArray, "pedido", carritoArray.get(i).getIdproveedor(),"","")
                            ,this,"", "ticket",""));
                }
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.overflowcontinuar, menu);

        return true;
    }
    //Metodo para asignar las funciones
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if (id == R.id.itemcontinuar) {

            Toast.makeText(getApplicationContext(), "Text", Toast.LENGTH_SHORT).show();

        }


        return super.onOptionsItemSelected(item);
    }
}