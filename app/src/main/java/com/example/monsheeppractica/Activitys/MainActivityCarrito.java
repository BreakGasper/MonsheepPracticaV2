package com.example.monsheeppractica.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.AdaptadorCarrito;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivityCarrito extends AppCompatActivity {
    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio;
    RecyclerView lv_lista;
    SharedPreferences preferences;
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Carrito> Array = new ArrayList<>();
    Button btnProcederAPagar;
    TextView tvSubtotal;
    int cadena = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_carrito);
        try {
            getSupportActionBar().hide();
            getActionBar().hide();
        } catch (Exception e) {

            //  Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
        }
        // getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        lv_lista = findViewById(R.id.rvListarCar);
        tvSubtotal = findViewById(R.id.tvSubTotal);
        btnProcederAPagar = findViewById(R.id.btnProcederAPagar);

        sumaDeDatos();
        ListarPedido();
        if (cadena == 0) {
            btnProcederAPagar.setEnabled(false);
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Esto se ejecuta cada vez que se realiza el gesto
                ListarPedido();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        btnProcederAPagar.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivityAPagar.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        });

    }

    public void sumaDeDatos() {
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        consultarTabla.CarritoConsulta(Array, "counts", idUser, "", "");

        String[] arreglo = new String[Array.size()];
        int sum = 0;

        for (int i = 0; i < arreglo.length; i++) {

            arreglo[i] = "" + Integer.parseInt(Array.get(i).getCantidad());
            cadena += Integer.parseInt(arreglo[i]);

            arreglo[i] = "" + Integer.parseInt(Array.get(i).getCantidad()) * Integer.parseInt(Array.get(i).getPrecio());
            sum += Integer.parseInt(arreglo[i]);
        }
        String word = "\tProducto";
        if (cadena > 1) {
            word = "\tProductos";
        }
        btnProcederAPagar.setText("Realizar pedido de " + cadena + word);
        tvSubtotal.setText("SubTotal: $" + sum);
    }

    private void ListarPedido() {
        try {
            Array.clear();
            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            lv_lista.setLayoutManager(new LinearLayoutManager(this));
            lv_lista.setAdapter(new AdaptadorCarrito(this,
                    consultarTabla.CarritoConsulta(Array, "counts", idUser, "", ""), MainActivityCarrito.this, "carrito"));
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            System.out.println(e);
        }
    }

    public void AlertDialogEdit(Context context, String cantidadOriginalAlert, String idTicket, String IdProductoAlert, String Cantidad, Activity mActivity) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewInput = inflater.inflate(R.layout.alert_dialog_editar_carrito, null, false);

        ImageView iVP = viewInput.findViewById(R.id.ivProductoCar);
        ImageView iVMas = viewInput.findViewById(R.id.ivMasAlert);
        ImageView iVMenos = viewInput.findViewById(R.id.ivMenosAlert);
        EditText etContador = viewInput.findViewById(R.id.etCountAlert);

        etContador.setText(Cantidad);

        DatabaseHandler db = new DatabaseHandler(context);
        Picasso.get().load(db.getImagen("" + IdProductoAlert + ".jpg")).memoryPolicy(MemoryPolicy.NO_CACHE).into(iVP);
//        iVP.setImageBitmap(db.getimageID(IdProductoAlert));

        iVMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int contador = Integer.parseInt(etContador.getText().toString()) + 1;
                if (contador <= Integer.parseInt(cantidadOriginalAlert)) {
                    etContador.setText("" + contador);
                } else {
                    Toast.makeText(context, "No hay mas disponibles", Toast.LENGTH_SHORT).show();
                }
            }
        });

        iVMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int contador = Integer.parseInt(etContador.getText().toString()) - 1;
                if (contador >= 1) {
                    etContador.setText("" + contador);
                } else {
                    Toast.makeText(context, "Preciona el icono de eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        //inflar layout
        alerta.setView(viewInput);
        //Mensaje
        alerta.setTitle(Html.fromHtml("<font color='#F23E0C'>Producto</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("Cantidad Disponible: " + cantidadOriginalAlert);
        alerta.setNeutralButton("Editar", (dialogInterface, i) -> {

            try {
                int contador = Integer.parseInt(etContador.getText().toString());
                if (contador <= Integer.parseInt(cantidadOriginalAlert)) {
                    int total = Integer.parseInt(cantidadOriginalAlert) - Integer.parseInt(etContador.getText().toString());
                    EditarTabla editarTabla = new EditarTabla();
                    editarTabla.EditarCarrito(context, IdProductoAlert, etContador.getText().toString(), idTicket);

                    Intent intent = new Intent(context, MainActivityCarrito.class);
                    context.startActivity(intent);
                    finish();
                    mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else {
                    Toast.makeText(context, "Disponibles: " + cantidadOriginalAlert, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
                System.out.println("" + e);
            }
        });
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                } catch (Exception e) {
                    System.out.println("" + e);
                }
            }
        });
        alerta.setIcon(R.drawable.editar);
        alerta.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);

    }
}