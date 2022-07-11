package com.example.monsheeppractica.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Comentario;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.AdaptadorComentario;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.InsertarTabla;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityDetalles extends AppCompatActivity {
    String id_producto_extra="", Id_Foto_extra,categoria_nombre_extra,costo_extra,producto_extra
            ,ventaMenudeo_extra,ventaMayoreo_extra,marca_extra,color_p_extra,unidadMedida_extra,id_categoria_extra
            ,status_extra,cantidadMinima_extra,editar_extra,NombreUser_extra="",proveedor;
    TextView tvDesc,tvDescDeta,tvLinkProveedor,tvComentarioDetalle;
  //  ListView lvProducto;
    ArrayList<Comentario> listaProducto;
    EditText etComentario;
    String idFotoUser="",idFotoNego,idUser="",idPerfil="",namePerfil="", fecha, hora,idNegocio;
    Button btnCom ;
    RecyclerView rvLista;
    Date d = new Date();
    CircleImageView ivFotoProvedor;
    LinearLayout linearDetalle;
    static SimpleDateFormat DateFormat_Fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    static SimpleDateFormat DateFormat_Hour = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detalles);
        try {
            getSupportActionBar().hide();
            getActionBar().hide();
        }catch (Exception e){
            //Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
        }
        //Obtener datos desde el activity
        editar_extra =  getIntent().getStringExtra("editar");
        id_producto_extra =  getIntent().getStringExtra("id_producto");
        Id_Foto_extra =  getIntent().getStringExtra("Id_Foto");
        categoria_nombre_extra =  getIntent().getStringExtra("categoria_nombre");
        costo_extra =  getIntent().getStringExtra("costo");//******
        producto_extra =  getIntent().getStringExtra("producto");//***
        ventaMenudeo_extra =  getIntent().getStringExtra("ventaMenudeo");//****
        ventaMayoreo_extra =  getIntent().getStringExtra("ventaMayoreo");//****
        marca_extra =  getIntent().getStringExtra("marca");//***
        color_p_extra =  getIntent().getStringExtra("color_p");///****
        unidadMedida_extra =  getIntent().getStringExtra("unidadMedida");
        id_categoria_extra =  getIntent().getStringExtra("id_categoria");
        status_extra =  getIntent().getStringExtra("status");
        cantidadMinima_extra =  getIntent().getStringExtra("cantidadMinima");
        proveedor =  getIntent().getStringExtra("proveedor");
       // idUser =  getIntent().getStringExtra("idusers");
        //NombreUser_extra =  getIntent().getStringExtra("NombreUser");
         idFotoNego =  getIntent().getStringExtra("idFotoNego");
        idPerfil =  getIntent().getStringExtra("idPerfil");
        namePerfil =  getIntent().getStringExtra("namePerfil");


        SharedPreferences preferences =getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers","et_pass.getText().toString()");
        NombreUser_extra = preferences.getString("NombreUser","et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser","et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio","et_pass.getText().toString()");


        //Casteo de variables
        tvLinkProveedor=findViewById(R.id.tvLinkProveedor);
        tvDesc = findViewById(R.id.tvDe);
        tvDescDeta = findViewById(R.id.tvDescDeta);
        ImageView ivFoto =findViewById(R.id.ivFotoDetails);
        etComentario = findViewById(R.id.etComentario);
        ivFotoProvedor = findViewById(R.id.ivPerfilFoto);
        rvLista = findViewById(R.id.rVListaCom);
        btnCom = findViewById(R.id.btnCom);
        linearDetalle = findViewById(R.id.linearDetalles);
        tvComentarioDetalle =findViewById(R.id.tvComentarioDetalle);



        tvDesc.setText("\nProducto: "+producto_extra+ "\n$"+costo_extra+" x "+unidadMedida_extra);
        tvDescDeta.setText("Descripcion: "+ventaMenudeo_extra+"\nMarca: "+marca_extra
                +"\nColor: "+color_p_extra+"\nCategoria: "+categoria_nombre_extra
                +"\nPiezas disponibles: "+cantidadMinima_extra);
        tvLinkProveedor.setText("|-> "+proveedor+" <-|");
        //Inicializar base de datos Imagenes
        DatabaseHandler db  = new DatabaseHandler(this);
        ivFoto.setImageBitmap(db.getimage(Id_Foto_extra));

        //Toast.makeText(this, ""+idFotoNego, Toast.LENGTH_SHORT).show();
        ivFotoProvedor.setImageBitmap(db.getimage(idFotoNego));

        listaProducto=new ArrayList<>();
        tvLinkProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityPerfilUser.class);
//                intent.putExtra("idusers",""+idUser);
//                intent.putExtra("NombreUser",""+NombreUser_extra);
                intent.putExtra("idFotoUser",idFotoUser);
                intent.putExtra("idPerfil",""+idPerfil);
                intent.putExtra("namePerfil",""+namePerfil);
                startActivity(intent);
            }
        });
        ivFotoProvedor.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivityPerfilUser.class);
            intent.putExtra("idFotoUser",idFotoUser);
            intent.putExtra("idPerfil",""+idPerfil);
            intent.putExtra("namePerfil",""+namePerfil);
            startActivity(intent);
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
                btnCom.setText("Ver Detalles");
                linearDetalle.setVisibility(View.GONE);
                return false;
            }
        });

        btnCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDescDeta.setVisibility(View.VISIBLE);
                tvLinkProveedor.setVisibility(View.VISIBLE);
                linearDetalle.setVisibility(View.VISIBLE);
                ivFoto.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );

                if (!etComentario.getText().toString().isEmpty()){
                    InsertarComentario();
                }else etComentario.setError("Debes ingresar un comentario");

            }
        });

        rvLista.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                etComentario.setVisibility(View.GONE);
                btnCom.setVisibility(View.GONE);
                tvLinkProveedor.setVisibility(View.GONE);
                linearDetalle.setVisibility(View.GONE);
                ivFoto.setVisibility(View.GONE);
                tvComentarioDetalle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tvComentarioDetalle.setText("Ver Detalles");
                tvComentarioDetalle.setBackground(getResources().getDrawable(R.drawable.degradado));
                tvComentarioDetalle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnCom.setVisibility(View.VISIBLE);
                        etComentario.setVisibility(View.VISIBLE);
                        tvLinkProveedor.setVisibility(View.VISIBLE);
                        ivFoto.setVisibility(View.VISIBLE);
                        linearDetalle.setVisibility(View.VISIBLE);
                        tvComentarioDetalle.setText("Comentarios\t\t");
                        tvComentarioDetalle.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                        tvComentarioDetalle.setBackgroundResource(R.drawable.degradadofondo);
                        tvDescDeta.setText("Descripcion: "+ventaMenudeo_extra+"\nMarca: "+marca_extra
                                +"\nColor: "+color_p_extra+"\nCategoria: "+categoria_nombre_extra
                                +"\nPiezas disponibles: "+cantidadMinima_extra+"\nProveedor: "+NombreUser_extra);
                    }
                });
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });



         listaproducto();

    }
    private  void  FechaYHoraActual(){
        fecha = DateFormat_Fecha.format(d);
        hora = DateFormat_Hour.format(d);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void InsertarComentario(){
        FechaYHoraActual();
        InsertarTabla inTab = new InsertarTabla();
        inTab.RegistrarComentario(this, 0,etComentario.getText().toString(),idUser,
                id_producto_extra,NombreUser_extra,producto_extra,idFotoUser, fecha, hora);

        etComentario.setText("");
        listaproducto();
    }

    public void listaproducto(){
        listaProducto.clear();

        try {
            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            rvLista.setLayoutManager(new LinearLayoutManager(this));
            rvLista.setAdapter(new AdaptadorComentario(this,
                    consultarTabla.ComentarioConsulta(listaProducto,
                            "pro",id_producto_extra,null,null),NombreUser_extra));

        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }

    }
}