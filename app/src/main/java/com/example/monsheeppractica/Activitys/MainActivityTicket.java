package com.example.monsheeppractica.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.GetterAndSetter.Domicilios;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.AdaptadorCarrito;
import com.example.monsheeppractica.adaptadores.AdaptadorNegocioPerfil;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainActivityTicket extends AppCompatActivity {
    SharedPreferences preferences;
    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio, fecha, hora, idDom;
    ArrayList<Domicilios> domiciliosArray = new ArrayList<>();
    ArrayList<Negocio> negocioArray = new ArrayList<>();
    ArrayList<Carrito> carritoArray = new ArrayList<>();
    TextView tvDatosUsuario, tvSubtotalTicket;
    RecyclerView lv_lista, rvListadoProvedor;
    static SimpleDateFormat DateFormat_Fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    static SimpleDateFormat DateFormat_Hour = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    Date d = new Date();

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
        tvSubtotalTicket = findViewById(R.id.tvSubtotalTicket);
        try {
            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            consultarTabla.DomicilioConsulta(domiciliosArray, "user", Integer.parseInt(idUser), idUser);
            Domicilios user = domiciliosArray.get(0);
            idDom = "" + user.getIdDomicilio();
            tvDatosUsuario.setText(user.getNombreCompleto() + "\nMi contacto: " + user.getCelular()
                    + "\n" + user.getDomicilio() + " #" + user.getNumero()
                    + "\n" + user.getVecindario() + " " + user.getColonia() + "\n" + user.getMunicipio());
        } catch (Exception e) {
            System.out.println(e);
            //Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
        pedido();
        proveedores();
    }

    void pedido() {
        try {
            carritoArray.clear();
            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            lv_lista.setLayoutManager(layoutManager);
            lv_lista.setAdapter(new AdaptadorCarrito(this,
                    consultarTabla.CarritoConsulta(carritoArray, "counts", idUser, "", "")
                    , MainActivityTicket.this, "ticket"));

        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            System.out.println(e);
        }
    }

    void proveedores() {
        negocioArray.clear();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvListadoProvedor.setLayoutManager(layoutManager);
        String[] arreglo = new String[carritoArray.size()];
        int sum = 0;
        List<String> lista = new ArrayList<>();

        for (int i = 0; i < arreglo.length; i++) {
                lista.add(carritoArray.get(i).getIdproveedor());
            arreglo[i] = "" + Integer.parseInt(carritoArray.get(i).getCantidad()) * Integer.parseInt(carritoArray.get(i).getPrecio());
            sum += Integer.parseInt(arreglo[i]);


        }
        tvSubtotalTicket.setText("SubTotal: $" + sum);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            lista = lista.stream().distinct().collect(Collectors.toList());
            lista.forEach(obj -> {
                rvListadoProvedor.setAdapter(new AdaptadorNegocioPerfil(
                        consultarTabla.NegocioConsulta(negocioArray, "Tienda", obj, "", "")
                        , this, "", "ticket", "", null, this));
            });
        }
    }


    private void FechaYHoraActual() {
        fecha = DateFormat_Fecha.format(d);
        hora = DateFormat_Hour.format(d);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.overflowcontinuar, menu);

        return true;
    }

    //Metodo para asignar las funciones
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.itemcontinuar) {
            FechaYHoraActual();
            EditarAlmacen();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("art", "0").commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);

        }


        return super.onOptionsItemSelected(item);
    }

    void EditarAlmacen() {
        negocioArray.clear();
        EditarTabla editarTabla = new EditarTabla();

        String[] arreglo = new String[carritoArray.size()];
        int almacen = 0;
        int idticket = (int) (Math.random() * 10000 + 1 * 3 + 15);

        for (int i = 0; i < arreglo.length; i++) {
            almacen = Integer.parseInt(carritoArray.get(i).getCantidadDisponible()) - Integer.parseInt(carritoArray.get(i).getCantidad());
            editarTabla.EditarProductoCantidad(this, Integer.parseInt(carritoArray.get(i).getIdProducto()), "" + almacen);
            editarTabla.EditarCarritoFinal(this, "" + carritoArray.get(i).getIdticket(),
                    fecha, hora, "Tienda", idDom, "" + almacen, "" + idticket);
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewInput = inflater.inflate(R.layout.toast_layout, null, false);
        TextView text = (TextView) viewInput.findViewById(R.id.text12);
        text.setText("Compra Exitosa");
        Toast toast = new Toast(this);
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(viewInput);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);

    }
}