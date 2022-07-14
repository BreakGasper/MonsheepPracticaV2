package com.example.monsheeppractica.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.sqlite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivityLogin extends AppCompatActivity {

    FloatingActionButton fab;
    EditText et_cel, et_pass;
    String cel, pass;
    SharedPreferences preferences;
    NetworkInfo networkInfo;
    String usern, tipoUser;
    Button btnLogin;
    DatabaseHandler db;
    AlertDialog.Builder alerta;
    TextView tvRegistrarme, tvOlvideContra, tvTitle;

    String idusers, idFotoUser, NombreUser, idNegocio, idFotoNegocio;
    public static ArrayList<Clientes> clientesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        try {
            getSupportActionBar().hide();
            getActionBar().hide();
        } catch (Exception e) {

            //  Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
        }
        et_cel = (EditText) findViewById(R.id.etCelLog);
        et_pass = (EditText) findViewById(R.id.etPassLog);
        tvRegistrarme = findViewById(R.id.tvReg);
        tvOlvideContra = findViewById(R.id.tvPass);
        tvTitle = findViewById(R.id.tvTitle);

        tvRegistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityRegistroUsuario.class);
                startActivity(intent);
            }
        });


        String inicio = "¿No tienes una cuenta?", fin = "Registrarme";
        Spannable s = (Spannable) tvRegistrarme.getText();
        int start = inicio.length();
        int end = start + fin.length() + 1;
        s.setSpan(new ForegroundColorSpan(Color.rgb(232, 57, 2)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        try {
            String moon = "Moon", sheep = "Sheep";
            Spannable span = (Spannable) tvTitle.getText();
            int st = moon.length();
            int ed = st + sheep.length();
            span.setSpan(new
                            ForegroundColorSpan(Color.rgb(232, 57, 2)), st,
                    ed, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//      android:bufferType="spannable"

        } catch (Exception e) {
            System.out.println(e);
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }


        checkCameraPermission();
        //Verificacion a internet
        ConnectivityManager con = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = con.getActiveNetworkInfo();

        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        cel = preferences.getString("user", et_cel.getText().toString());
        pass = preferences.getString("pass", et_pass.getText().toString());
        usern = preferences.getString("tipouser", "et_pass.getText().toString()");
        idusers = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");
        idFotoNegocio = preferences.getString("idFotoNegocio", "et_pass.getText().toString()");

        et_cel.setText("" + cel);
        et_pass.setText("" + pass);
        btnLogin = (Button) findViewById(R.id.btnLog);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Ingresar();
                } catch (Exception e) {
                    et_cel.setError("Ingresa solo Numeros porfavor");
                    Toast.makeText(MainActivityLogin.this, "Ingresa solo numeros" + e, Toast.LENGTH_SHORT).show();
                    System.out.println(e);
                }

            }
        });

    }

    private void checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "No se tiene permiso para la camara!");
        }
    }

    public void listaproducto() {
        clientesArrayList.clear();
        sqlite bh = new sqlite
                (this, "clientes", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from clientes where Teléfono=='" + et_cel.getText().toString().trim()
                    + "' and Contra=='" + et_pass.getText().toString().trim() + "' and status='Activo'", null);
            if (c.moveToFirst()) {
                do {
                    clientesArrayList.add(new Clientes(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10), c.getString(11), c.getString(12), c.getString(13), c.getString(14), c.getString(15), c.getString(16), c.getString(17), c.getString(18), c.getString(19)));
                } while (c.moveToNext());

                Intent intent = new Intent(MainActivityLogin.this, MainActivity.class);
                intent.putExtra("idusers", "" + clientesArrayList.get(0).getId_cliente());
                intent.putExtra("NombreUser", clientesArrayList.get(0).getNombre() + "" + clientesArrayList.get(0).getApellidoMaterno().trim());
                intent.putExtra("idFotoUser", clientesArrayList.get(0).getId_Foto());
                // Toast.makeText(this, ""+clientesArrayList.get(0).getId_Foto()+clientesArrayList.get(0).getNombre()+clientesArrayList.get(0).getId_cliente(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("idusers", "" + clientesArrayList.get(0).getId_cliente());
                editor.putString("NombreUser", clientesArrayList.get(0).getNombre() + "" + clientesArrayList.get(0).getApellidoMaterno().trim());
                editor.putString("idFotoUser", clientesArrayList.get(0).getId_Foto());
                editor.putString("pass", pass);
                editor.putString("user", cel);
                editor.putString("tipouser", "" + clientesArrayList.get(0).getTipoCompra());
                editor.putString("idNegocio", "" + clientesArrayList.get(0).getIdNegocio());
                editor.commit();
            } else {
                Toast.makeText(this, "Tu numero de telefono o contraseña no coinciden" + clientesArrayList.size(), Toast.LENGTH_SHORT).show();
            }
            db.disableWriteAheadLogging();
        }
    }

    public void Ingresar() {

        cel = et_cel.getText().toString().trim();
        pass = et_pass.getText().toString().trim();
        listaproducto();
        if (networkInfo != null && networkInfo.isConnected()) {
            //webservice

        } else {
            Toast.makeText(this, "No se pudo conectar, Verifique " +
                    "\n el acceso a internet", Toast.LENGTH_SHORT).show();
        }

    }


}