package com.example.monsheeppractica.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.GetterAndSetter.Seguidores;
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.ListaClientesAdaptador;
import com.example.monsheeppractica.adaptadores.Lista_productoAdaptador;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;
import com.example.monsheeppractica.sqlite.registros.EliminarTabla;
import com.example.monsheeppractica.sqlite.registros.InsertarTabla;
import com.example.monsheeppractica.sqlite.sqlite;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivityPerfilUser extends AppCompatActivity {
    ImageView ivFotoPerfil, ivCall, ivSms;
    TextView tvNombrePerfil, tvDescrip, tvDestails, tvSeguir;
    ListView lvPerfil;
    ArrayList<Productos> listaProducto;
    ArrayList<Clientes> listaNegocio;
    ArrayList<Seguidores> listaseguir;

    Boolean identificador = false, negocioID = false;
    String NombreUser, idUser, idFotoUser;
    LottieAnimationView animationView;
    String idPerfil, namePerfil, idNegocio;
    DatabaseHandler db;
    int idSeguir;
    ArrayList<Negocio> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_perfil_user);
        try {
            getSupportActionBar().hide();
            getActionBar().hide();
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
        }
        lvPerfil = findViewById(R.id.lvPerfil);
        ivFotoPerfil = findViewById(R.id.ivPerfilP);
        tvNombrePerfil = findViewById(R.id.tvDescPostPerfil);
        tvDescrip = findViewById(R.id.tvProvedorPerfil);
        tvDestails = findViewById(R.id.tvDetails);

        ivCall = findViewById(R.id.ivCall);
         ivSms = findViewById(R.id.ivSms);

        SharedPreferences preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");


//        idUser =  getIntent().getStringExtra("idusers");
//        NombreUser =  getIntent().getStringExtra("NombreUser");
//        idFotoUser =  getIntent().getStringExtra("idFotoUser");

        idPerfil = getIntent().getStringExtra("idPerfil");
        namePerfil = getIntent().getStringExtra("namePerfil");
        db = new DatabaseHandler(this);
        tvSeguir = findViewById(R.id.tvSeguir);
        //  Toast.makeText(this, ""+idPerfil, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, ""+idPerfil+namePerfil+idFotoUser, Toast.LENGTH_SHORT).show();
        checkCameraPermission();
        tvSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidarSeguidor();

            }
        });
        animationView = findViewById(R.id.animation_Lottie);
        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidarSeguidor();
            }
        });

        listaProducto = new ArrayList<>();
        listaNegocio = new ArrayList<>();
        listaseguir = new ArrayList<>();


        listaClientes();
        try {
            listaproducto();
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            System.out.println(e);
        }

        if (idUser.equals(idPerfil)) {
            tvSeguir.setVisibility(View.GONE);
            animationView.setVisibility(View.GONE);
        }

        try {
            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            consultarTabla.SeguirConsulta(listaseguir, "pro", idUser, idPerfil, namePerfil);
            //Toast.makeText(this, ""+listaseguir.get(0).getIdSeguir(), Toast.LENGTH_SHORT).show();

            idSeguir = listaseguir.get(0).getIdSeguir();

            if (listaseguir.get(0).getIdUser().isEmpty() || listaseguir.get(0).getIdUser() == null) {
                animationView.setAnimation(R.raw.adduser);
                animationView.loop(true);
                animationView.playAnimation();

                tvSeguir.setText("\t\tSeguir\t\t");
                tvSeguir.setBackgroundResource(R.drawable.degradadofondocircle);
                tvSeguir.setTextColor(Color.WHITE);
            } else {

                animationView.setAnimation(R.raw.subs);
                animationView.loop(true);
                animationView.playAnimation();

                tvSeguir.setText("\t\tSiguiendo\t\t");
                tvSeguir.setBackgroundResource(R.drawable.radio_flat_regular);
                tvSeguir.setTextColor(Color.BLACK);
            }
        } catch (Exception e) {
            //Toast.makeText(this, "1"+e, Toast.LENGTH_SHORT).show();
        }

    }

    public void listaClientes() {

        lista = new ArrayList<>();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        lista = consultarTabla.NegocioConsulta(lista, "like", idPerfil, "", "");
        //Toast.makeText(this, ""+lista.size(), Toast.LENGTH_SHORT).show();
        //  consultarTabla.ClientesConsulta(listaNegocio,"user",Integer.parseInt(idPerfil),"");

        tvDestails.setText("Contacto: " + lista.get(0).getLada() + " " + lista.get(0).getTeléfono());
        //Toast.makeText(this, "tel:"+lista.get(0).getLada().trim()+lista.get(0).getTeléfono().trim(), Toast.LENGTH_SHORT).show();
        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivCall.setEnabled(false);

                Intent intents = new Intent(Intent.ACTION_CALL);
                intents.setData(Uri.parse("tel:" + lista.get(0).getLada().trim() + lista.get(0).getTeléfono().trim()));
                startActivity(intents);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ivCall.setEnabled(true);

                    }
                }, 2000);

            }
        });
        namePerfil = lista.get(0).getNombre();
        tvNombrePerfil.setText("" + lista.get(0).getNombre());
        tvDescrip.setText("(" + lista.get(0).getStatus()
                + ")\nDomicilio: " + lista.get(0).getCalle() + " #" + lista.get(0).getNumero()
                + "\n" + lista.get(0).getColonia() + ", " + lista.get(0).getMunicipio()
                + ", " + lista.get(0).getEstado() + ".\n" + lista.get(0).getDescripcion());
        Picasso.get().load(db.getImagen(""+lista.get(0).getIdNegocio()+".jpg")).memoryPolicy(MemoryPolicy.NO_CACHE).into(ivFotoPerfil);

       // ivFotoPerfil.setImageBitmap(db.getimageID(""+lista.get(0).getIdNegocio()));


    }

    private void checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 225);
        } else {
            Log.i("Mensaje", "No se tiene permiso para la camara!");
        }
    }

    public void listaproducto() {
        //Toast.makeText(this, ""+idUser+NombreUser, Toast.LENGTH_SHORT).show();
        listaProducto.clear();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        lvPerfil.setAdapter(new Lista_productoAdaptador.listaP(this,
                consultarTabla.ProductoConsulta(listaProducto, "negocio", idPerfil, "")));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void openWhatsApp(View view) {
        try {
            String text = "!Te envio desde la app Monsheep¡\nHola me gustaria Ordenar:";// Replace with your message.

            String toNumber = "" + lista.get(0).getLada() + lista.get(0).getTeléfono(); // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.

            ivSms.setEnabled(false);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivSms.setEnabled(true);

                }
            }, 2000);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ValidarSeguidor() {

        try {
            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            consultarTabla.SeguirConsulta(listaseguir, "pro", idUser, idPerfil, namePerfil);
            if (listaseguir.get(0).getIdUser().isEmpty() || listaseguir.get(0).getIdUser() == null) {


            } else {
                animationView.setAnimation(R.raw.adduser);
                animationView.loop(true);
                animationView.playAnimation();
                SeguirDelete();
                tvSeguir.setText("\t\tSeguir\t\t");
                tvSeguir.setBackgroundResource(R.drawable.degradadofondocircle);
                tvSeguir.setTextColor(Color.WHITE);
            }
        } catch (Exception e) {


            animationView.setAnimation(R.raw.subs);
            animationView.loop(true);
            animationView.playAnimation();
            SeguirInsert();
            tvSeguir.setText("\t\tSiguiendo\t\t");
            tvSeguir.setBackgroundResource(R.drawable.radio_flat_regular);
            tvSeguir.setTextColor(Color.BLACK);
            // Toast.makeText(this, "2"+e, Toast.LENGTH_SHORT).show();
        }
    }

    public void SeguirInsert() {

        listaseguir.clear();

        InsertarTabla insertarTabla = new InsertarTabla();
        insertarTabla.RegistrarSeguidor(this, 0, idUser, idPerfil, namePerfil, "Activo");
        //  finish();


    }

    public void SeguirDelete() {

        listaseguir.clear();
        EliminarTabla eliminarSeguidor = new EliminarTabla();
        eliminarSeguidor.EliminarSeguidor(this, idSeguir);
        try {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}