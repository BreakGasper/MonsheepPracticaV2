package com.example.monsheeppractica.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.AdaptadorHistorial;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;

import java.util.ArrayList;

public class MainActivityNotificacionesNegocio extends AppCompatActivity {
    SharedPreferences preferences;
    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio, fecha, hora, idDom;
    RecyclerView lVNotificacion;
    ArrayList<Carrito> carritoArray = new ArrayList<>();
    ArrayList<Carrito> carritoA = new ArrayList<>();
    ArrayList<Negocio> negocioArray = new ArrayList<>();
    boolean datos = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notificaciones_negocio);

        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");

        lVNotificacion = findViewById(R.id.rVNotificacion);
//        Toast.makeText(this, ""+idNegocio, Toast.LENGTH_SHORT).show();
//        ConsultarTabla consultarTabla = new ConsultarTabla(this);
//        consultarTabla.NegocioConsulta(negocioArray,"idUser", idUser, "","");
//        idNegocio=""+negocioArray.get(0).getIdNegocio();
//        Toast.makeText(this, ""+idNegocio, Toast.LENGTH_SHORT).show();

        pedido("solicitudProvedor");


    }

    void pedido(String clave) {
        // FechaYHoraActual();
        carritoArray.clear();
        try {

            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            lVNotificacion.setLayoutManager(layoutManager);
            carritoArray.clear();
            carritoA.clear();

            consultarTabla.CarritoConsulta(carritoArray, clave, idNegocio, "", fecha);
            if (carritoArray.size() == 0) {
                datos = true;
                MSJ_TOAST();
            }
            String[] arreglo = new String[carritoArray.size()];
            // Toast.makeText(this, ""+idNegocio, Toast.LENGTH_SHORT).show();
            for (int i = 0; i < arreglo.length; i++) {

                if (i == 0) {
                    lVNotificacion.setAdapter(new AdaptadorHistorial(this,
                            consultarTabla.CarritoConsulta(carritoA, "notificacion", idNegocio,
                                    "" + carritoArray.get(i).getIdticket(), "" + carritoArray.get(i).getTicket()),
                            MainActivityNotificacionesNegocio.this, "ket"));

                } else if (!carritoArray.get(i).getTicket().equals(carritoArray.get(i - 1).getTicket())) {

                    lVNotificacion.setAdapter(new AdaptadorHistorial(this,
                            consultarTabla.CarritoConsulta(carritoA, "notificacion", idNegocio,
                                    "" + carritoArray.get(i).getIdticket(), "" + carritoArray.get(i).getTicket()),
                            MainActivityNotificacionesNegocio.this, "ket"));
                }
            }


        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            System.out.println(e);
        }
    }

    private void MSJ_TOAST() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewInput = inflater.inflate(R.layout.toast_layout, null, false);
        TextView text = (TextView) viewInput.findViewById(R.id.text12);
        text.setText("¡Aun no tienes pedidos!,\nInvita a tus clientes a usar la app");
        Toast toast = new Toast(this);
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(viewInput);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.overflowfiltro_negocio, menu);

        return true;
    }

    //Metodo para asignar las funciones
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.itemfiltro) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewInput = inflater.inflate(R.layout.filtros, null, false);
            Spinner sp = viewInput.findViewById(R.id.spFiltroUnidad);

            ArrayList<Carrito> carritoArray = new ArrayList<>();
            carritoArray.clear();
            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            consultarTabla.CarritoConsulta(carritoArray, "solicitud", idUser, "", fecha);
            String[] arreglo = new String[carritoArray.size()];
            ArrayList<String> cars = new ArrayList<String>();

            for (int i = 0; i < arreglo.length; i++) {
                //arreglo[i]= carritoArray.get(i).getFecha();
                if (i == 0) {
                    cars.add(carritoArray.get(i).getFecha());
                } else if (!carritoArray.get(i).getFecha().equals(carritoArray.get(i - 1).getFecha())) {
                    cars.add(carritoArray.get(i).getFecha());
                }
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, R.layout.adaptadorspinner,
                            cars);
            sp.setAdapter(spinnerArrayAdapter);
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    fecha = (String) adapterView.getItemAtPosition(i);
                    //Toast.makeText(MainActivityHistorial.this, ""+fecha, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            AlertDialog.Builder alerta = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            //inflar layout
            alerta.setView(viewInput);
            //Mensaje
            alerta.setTitle(Html.fromHtml("<font color='#F23E0C'>Buscar por Filtro"));
            alerta.setIcon(R.drawable.filtrar);
            alerta.setNeutralButton(Html.fromHtml("<font color='#1CB1C5'>Ver Todos"),
                    (dialogInterface, i) -> {
                        pedido("solicitudProvedor");
                    });
            alerta.setNegativeButton(Html.fromHtml("<font color='#F23E0C'>Buscar"),
                    (dialogInterface, i) -> {
                        pedido("filtroNegocio");
                    });
            alerta.show().getWindow().getDecorView().setBackgroundDrawable(getResources().getDrawable(R.drawable.alertdegradado));//.setBackgroundColor(Color.parseColor("#F1EEEE"));


        }
        if (id == R.id.itemgrafico) {
//            if (datos) {
            Intent intent = new Intent(this, MainActivityEstadisticas.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
//            } else MSJ_TOAST();
        }


        return super.onOptionsItemSelected(item);
    }
}