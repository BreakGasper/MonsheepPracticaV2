package com.example.monsheeppractica.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.GetterAndSetter.Comentario;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.AdaptadorComentario;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.EliminarTabla;
import com.example.monsheeppractica.sqlite.registros.InsertarTabla;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityDetalles extends AppCompatActivity {
    String id_producto_extra = "", Id_Foto_extra, categoria_nombre_extra, costo_extra, producto_extra, ventaMenudeo_extra, ventaMayoreo_extra, marca_extra, color_p_extra, unidadMedida_extra, id_categoria_extra, status_extra, cantidadMinima_extra,
             NombreUser_extra = "", proveedor,alias="";
    TextView tvDesc, tvDescDeta, tvLinkProveedor;
    Button tvComentarioDetalle;
    //  ListView lvProducto;
    ArrayList<Comentario> listaProducto;
    EditText etComentario;
    String idFotoUser = "", tipoUser,NombreUser, idFotoNego, idUser = "", idPerfil = "", namePerfil = "", fecha, hora, idNegocio;
    Button btnCom;
    RecyclerView rvLista;
    Date d = new Date();
    CircleImageView ivFotoProvedor;
    ArrayList<Productos> productosArrayList = new ArrayList<>();
    static SimpleDateFormat DateFormat_Fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    static SimpleDateFormat DateFormat_Hour = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    ImageView ivFoto;
    LinearLayout lineaDetalles, linea;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detalles);
        try {
            getSupportActionBar().hide();
            getActionBar().hide();
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
        }
        //Obtener datos desde el activity

        id_producto_extra = getIntent().getStringExtra("id_producto");

        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser_extra = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");
        alias = preferences.getString("Alias", "et_pass.getText().toString()");

        idFotoNego = getIntent().getStringExtra("idFotoNego");
        idPerfil = getIntent().getStringExtra("idPerfil");
        namePerfil = getIntent().getStringExtra("namePerfil");


        SharedPreferences preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser_extra = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");
        proveedor = getIntent().getStringExtra("proveedor");

        //Casteo de variables
        tvLinkProveedor = findViewById(R.id.tvLinkProveedor);
        tvDesc = findViewById(R.id.tvDe);
        tvDescDeta = findViewById(R.id.tvDescDeta);
        ivFoto = findViewById(R.id.ivFotoDetails);
        etComentario = findViewById(R.id.etComentario);
        ivFotoProvedor = findViewById(R.id.ivPerfilFoto);
        rvLista = findViewById(R.id.rVListaCom);
        btnCom = findViewById(R.id.btnCom);

        tvComentarioDetalle = findViewById(R.id.tvComentarioDetalle);
        lineaDetalles = findViewById(R.id.lineaDetalles);
        linea = findViewById(R.id.linea);

        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        consultarTabla.PostConsulta(productosArrayList,"detalles",id_producto_extra,"","");


        tvDesc.setText("\nProducto: " + productosArrayList.get(0).getProducto() + "\n$" + productosArrayList.get(0).getCosto() + " x " + productosArrayList.get(0).getUnidadMedida());
        tvDescDeta.setText("Descripcion: " + productosArrayList.get(0).getVentaMenudeo() + "\nMarca: " + productosArrayList.get(0).getMarca()
                + "\nColor: " + productosArrayList.get(0).getColor_p() + "\nCategoria: " + productosArrayList.get(0).getCategoria_nombre()
                + "\nPiezas disponibles: " + productosArrayList.get(0).getCantidadMinima());

        tvLinkProveedor.setText("|-> " + productosArrayList.get(0).getIdFotoUser() + " <-|");
        //Inicializar base de datos Imagenes
        DatabaseHandler db = new DatabaseHandler(this);
        Picasso.get().load(db.getImagen(""+productosArrayList.get(0).getId_producto()+".jpg")).memoryPolicy(MemoryPolicy.NO_CACHE).into(ivFoto);

       // ivFoto.setImageBitmap(db.getimageID(""+productosArrayList.get(0).getId_producto()));
        Picasso.get().load(db.getImagen(""+productosArrayList.get(0).getIdNegocio()+".jpg")).memoryPolicy(MemoryPolicy.NO_CACHE).into(ivFotoProvedor);

        //Toast.makeText(this, ""+idFotoNego, Toast.LENGTH_SHORT).show();
        //ivFotoProvedor.setImageBitmap(db.getimageID(productosArrayList.get(0).getIdNegocio()));

        listaProducto = new ArrayList<>();
        tvLinkProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLinkProveedor.setEnabled(false);

                Intent intent = new Intent(getApplicationContext(), MainActivityPerfilUser.class);
                intent.putExtra("idFotoUser", productosArrayList.get(0).getId_Foto());
                intent.putExtra("idPerfil", "" + productosArrayList.get(0).getIdNegocio());
                intent.putExtra("namePerfil", "" + productosArrayList.get(0).getId_Foto());
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvLinkProveedor.setEnabled(true);

                    }
                },2000);
            }
        });
        ivFotoProvedor.setOnClickListener(view -> {
            ivFotoProvedor.setEnabled(false);

            Intent intent = new Intent(getApplicationContext(), MainActivityPerfilUser.class);
            intent.putExtra("idFotoUser", idFotoUser);
            intent.putExtra("idPerfil", "" + idPerfil);
            intent.putExtra("namePerfil", "" + namePerfil);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivFotoProvedor.setEnabled(true);

                }
            },2000);

        });

        etComentario.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                etComentario.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etComentario, InputMethodManager.SHOW_IMPLICIT);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                //  tvDescDeta.setVisibility(View.GONE);
                //tvLinkProveedor.setVisibility(View.GONE);
                tvComentarioDetalle.setBackground(getResources().getDrawable(R.drawable.degradadofondo));
                tvComentarioDetalle.setText("Ver Detalles");
                // android:animateLayoutChanges="true"
                // lineaDetalles.setVisibility(View.GONE);
                linea.setVisibility(View.GONE);
                return false;
            }
        });

        tvComentarioDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(getApplicationContext(), "" + tvComentarioDetalle.getText().toString(), Toast.LENGTH_SHORT).show();
                if (tvComentarioDetalle.getText().toString().trim().equals("Ver Comentarios")) {
                    // Toast.makeText(this, "Cierto", Toast.LENGTH_SHORT).show();
//                    Animacion();

                    lineaDetalles.setVisibility(View.GONE);

                    tvComentarioDetalle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tvComentarioDetalle.setText("\tVer Detalles\t\t");
                    tvComentarioDetalle.setBackground(getResources().getDrawable(R.drawable.degradadofondo));
                }else if (tvComentarioDetalle.getText().toString().trim().equals("Ver Detalles")) {

                    tvComentarioDetalle.setText("\tVer Comentarios\t\t");
                    tvComentarioDetalle.setBackgroundResource(R.drawable.lineaabajo);
                    lineaDetalles.setVisibility(View.VISIBLE);
                    linea.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });


        btnCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
                linea.setVisibility(View.VISIBLE);
                tvComentarioDetalle.setText("\tVer Comentarios\t\t");
                if (!etComentario.getText().toString().isEmpty()) {
                    InsertarComentario();
                } else etComentario.setError("Debes ingresar un comentario");

            }
        });


        listaproducto();

    }

    private void FechaYHoraActual() {
        fecha = DateFormat_Fecha.format(d);
        hora = DateFormat_Hour.format(d);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void InsertarComentario() {
        FechaYHoraActual();
        InsertarTabla inTab = new InsertarTabla();
        inTab.RegistrarComentario(this, 0, etComentario.getText().toString(), idUser,
                id_producto_extra, alias, producto_extra, idFotoUser, fecha, hora);

        etComentario.setText("");
        listaproducto();
    }

    public void listaproducto() {
        listaProducto.clear();

        try {
            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            rvLista.setLayoutManager(new LinearLayoutManager(this));
            rvLista.setAdapter(new AdaptadorComentario(this,
                    consultarTabla.ComentarioConsulta(listaProducto,
                            "pro", id_producto_extra, null, null), NombreUser_extra));

        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }


}