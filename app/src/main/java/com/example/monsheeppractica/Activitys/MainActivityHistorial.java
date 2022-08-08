package com.example.monsheeppractica.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.AdaptadorHistorial;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivityHistorial extends AppCompatActivity {
    SharedPreferences preferences;
    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio, fecha, hora, idDom;
    RecyclerView lVHistorial;
    ArrayList<Carrito> carritoArray = new ArrayList<>();
    ArrayList<Carrito> carritoA = new ArrayList<>();
    static SimpleDateFormat DateFormat_Fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    Date d = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_historial);

        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");

        lVHistorial = findViewById(R.id.rVHistorial);
        pedido("solicitud");

    }

    private void FechaYHoraActual() {
        fecha = DateFormat_Fecha.format(d);

    }

    void pedido(String clave) {
        // FechaYHoraActual();
        carritoArray.clear();
        try {

            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            lVHistorial.setLayoutManager(layoutManager);
            carritoArray.clear();
            carritoA.clear();

            consultarTabla.CarritoConsulta(carritoArray, clave, idUser, "", fecha);
            if (carritoArray.size()==0){
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);
                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Aun no tienes paquetes,\nRealiza un pedido desde la app");
                Toast toast = new Toast(this);
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
            }
            String[] arreglo = new String[carritoArray.size()];

            for (int i = 0; i < arreglo.length; i++) {
                if (i == 0) {
                    lVHistorial.setAdapter(new AdaptadorHistorial(this,
                            consultarTabla.CarritoConsulta(carritoA, "tick", idUser,
                                    "", "" + carritoArray.get(i).getIdticket()),
                            MainActivityHistorial.this, "ticket"));
//                    Toast.makeText(this, ""+carritoArray.get(i).getTicket(), Toast.LENGTH_SHORT).show();
                } else if (!carritoArray.get(i).getTicket().equals(carritoArray.get(i - 1).getTicket())) {
//                    Toast.makeText(this, ""+carritoArray.get(i).getTicket(), Toast.LENGTH_SHORT).show();

                    lVHistorial.setAdapter(new AdaptadorHistorial(this,
                            consultarTabla.CarritoConsulta(carritoA, "tick", idUser,
                                    "", "" + carritoArray.get(i).getIdticket()),
                            MainActivityHistorial.this, "ticket"));
                }
            }


        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            System.out.println(e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.overflowfiltro, menu);

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
                        pedido("solicitud");
                    });
            alerta.setNegativeButton(Html.fromHtml("<font color='#FE4500'>Buscar"),
                    (dialogInterface, i) -> {
                        pedido("filtro");
                    });
            alerta.show().getWindow().getDecorView().setBackgroundColor(Color.parseColor("#F1EEEE"));


//            consultarTabla.CarritoConsulta(carritoArray,"filtro",idUser,"",fecha);

        }


        return super.onOptionsItemSelected(item);
    }

}