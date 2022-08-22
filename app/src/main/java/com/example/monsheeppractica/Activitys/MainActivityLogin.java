package com.example.monsheeppractica.Activitys;

import static com.example.monsheeppractica.WebService.wsDataDownload.NombreTablas;
import static com.example.monsheeppractica.mytools.Network.isNetDisponible;
import static com.example.monsheeppractica.mytools.Network.isOnlineNet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.MainActivitySplash;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.WebService.wsDataDownload;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.sqlite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivityLogin extends AppCompatActivity {

    FloatingActionButton fab;
    EditText et_cel, et_pass;
    String cel, pass, alias;
    SharedPreferences preferences;
    NetworkInfo networkInfo;
    String usern, tipoUser;
    Button btnLogin, btnRecuperar;
    DatabaseHandler db;
    AlertDialog.Builder alerta;
    TextView tvRegistrarme, tvOlvideContra, tvTitle;
    String contraOlvide;
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


        VerificarAcceso();

        tvOlvideContra.setOnClickListener(view -> {
            alertOlvidePass();

        });

        tvRegistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityRegistroUsuario.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                tvRegistrarme.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvRegistrarme.setEnabled(true);

                    }
                }, 2000);

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
        alias = preferences.getString("Alias", "et_pass.getText().toString()");

        et_cel.setText("" + cel);
//        et_pass.setText("" + pass);
        btnLogin = (Button) findViewById(R.id.btnLog);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    checkCameraPermission();
                    Ingresar();
                } catch (Exception e) {
                    et_cel.setError("Ingresa solo Numeros porfavor");
                    Toast.makeText(MainActivityLogin.this, "Ingresa solo numeros" + e, Toast.LENGTH_SHORT).show();
                    System.out.println(e);
                }

            }
        });

    }

    public void VerificarAcceso() {
        if (isNetDisponible(this) == true && isOnlineNet() == true) {
            Toast.makeText(this, "Conectado", Toast.LENGTH_SHORT).show();
            int count = 0;

            for (int i = 0; i < NombreTablas().size(); i++) {
                count++;

                sqlite admin = new sqlite(this, "monsheep", null, 1);
                SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
                BaseDeDatos.delete(NombreTablas().get(i).toString(), "", null);
                wsDataDownload download = new wsDataDownload(this);
                download.cargarWebService(NombreTablas().get(i).toString());

            }

        } else {
            Toast.makeText(this, "Sin Red", Toast.LENGTH_SHORT).show();
        }

    }

    void alertOlvidePass() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewInput = inflater.inflate(R.layout.olvidepassword, null, false);
        EditText etLada = viewInput.findViewById(R.id.et_lada);
        EditText etCel = viewInput.findViewById(R.id.et_cel_olvide);
        EditText etCode = viewInput.findViewById(R.id.et_code_olvide);
        TextView tvPass = viewInput.findViewById(R.id.tv_pass_olvide);
        btnRecuperar = viewInput.findViewById(R.id.btn_recuperar);
        Button btnConfirm = viewInput.findViewById(R.id.btn_confirmar_olvide);


        btnRecuperar.setOnClickListener(view -> {
            if (!etCel.getText().toString().isEmpty() && !etLada.getText().toString().isEmpty()) {
                btnRecuperar.setEnabled(false);
                etCode.setVisibility(View.VISIBLE);
                btnConfirm.setVisibility(View.VISIBLE);
                openWhatsApp(etLada.getText().toString().trim(), etCel.getText().toString().trim());
            } else {
                Toast.makeText(this, "Debes Llenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        btnConfirm.setOnClickListener(view -> {
            String codeVerify = preferences.getString("codeVerify", "et_pass.getText().toString()");
            //Toast.makeText(this, ""+codeVerify, Toast.LENGTH_SHORT).show();
            if (etCode.getText().toString().trim().equals(codeVerify)) {
                tvPass.setVisibility(View.VISIBLE);
                tvPass.setText("Tu contraseña es: " + contraOlvide);
            } else Toast.makeText(this, "Codigo Incorrecto", Toast.LENGTH_SHORT).show();


        });


        AlertDialog.Builder alerta = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        //inflar layout
        alerta.setView(viewInput);
        //Mensaje
        alerta.setTitle(Html.fromHtml("<font color='#F23E0C'>Recuperar contraseña"));
        alerta.setIcon(R.drawable.ic_baseline_lock_24);
//        alerta.setNeutralButton(Html.fromHtml("<font color='#1CB1C5'>Ver Todos"),
//                (dialogInterface, i) -> {
//
//                });
        alerta.setNegativeButton(Html.fromHtml("<font color='#F23E0C'>Salir"),
                (dialogInterface, i) -> {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("codeVerify", "Null");
                    editor.commit();
                });
        alerta.show().getWindow().getDecorView().setBackgroundDrawable(getResources().getDrawable(R.drawable.alertdegradado));//.setBackgroundColor(Color.parseColor("#F1EEEE"));

    }

    public void openWhatsApp(String lada, String cel) {
        try {
            sqlite bh = new sqlite
                    (this, "monsheep", null, 1);
            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from clientes where Telefono=='" + cel.trim()
                    + "' and status='Activo'", null);
            if (c.moveToFirst()) {

                do {
                    clientesArrayList.add(new Clientes(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10), c.getString(11), c.getString(12), c.getString(13), c.getString(14), c.getString(15), c.getString(16), c.getString(17), c.getString(18), c.getString(19), c.getString(20)));
                } while (c.moveToNext());
                contraOlvide = "" + clientesArrayList.get(0).getContra();
                int code = (int) (Math.random() * 256);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("codeVerify", "" + code + clientesArrayList.get(0).getNumero().trim());
                editor.commit();

                String text = "No lo compartas a nadie mas, Tu codigo de verificacion es: " + code;// Replace with your message.

                String toNumber = "" + lada + cel;
                String MonshepCel = "+523751076001";
                tvOlvideContra.setEnabled(false);
                Correo3(clientesArrayList.get(0).getContra().trim(),clientesArrayList.get(0).getCorreo().trim());

//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + MonshepCel + "&text=" +toNumber+" "+ text+"-$"+clientesArrayList.get(0).getId_cliente()));
//                startActivity(intent);
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvOlvideContra.setEnabled(true);

                    }
                }, 2000);
            } else {
                btnRecuperar.setEnabled(true);
                Toast.makeText(this, "El numero de telefono no esta registrado en la app", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        btnLogin.setEnabled(false);

        clientesArrayList.clear();
        sqlite bh = new sqlite
                (this, "monsheep", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from clientes where Telefono=='" + et_cel.getText().toString().trim()
                    + "' and Contra=='" + et_pass.getText().toString().trim() + "' and status='Activo'", null);
            if (c.moveToFirst()) {
                do {
                    clientesArrayList.add(new Clientes(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8), c.getString(9), c.getString(10), c.getString(11), c.getString(12), c.getString(13), c.getString(14), c.getString(15), c.getString(16), c.getString(17), c.getString(18), c.getString(19), c.getString(20)));
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
                editor.putString("pass", pass.toString().trim());
                editor.putString("user", cel);
                editor.putString("tipouser", "" + clientesArrayList.get(0).getTipoCompra());
                editor.putString("idNegocio", "" + clientesArrayList.get(0).getIdNegocio());
                editor.putString("art", "0");
                editor.putString("Alias", "" + clientesArrayList.get(0).getAlias());
                editor.commit();


            } else {
                Toast.makeText(this, "Tu numero de telefono o contraseña no coinciden", Toast.LENGTH_SHORT).show();
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnLogin.setEnabled(true);

                }
            }, 2000);
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

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void Correo3(String contra, String correo) {
        Toast.makeText(this, ""+contra+correo, Toast.LENGTH_SHORT).show();
        String sAsunto = "Recuperar Contraseña";
        String sMensaje = "Tu contraseña de MoonSheep es: " + contra + "\nSi no fuiste tu quien realizo esta consulta, Ignora este mensaje";
        String sEmail = "backgaspar@gmail.com";
        String sPassword = "xnwmglhcjbjjqdaz";


        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sEmail, sPassword);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sEmail));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(correo.trim()));
            message.setSubject(sAsunto.trim());
            message.setText(sMensaje.trim());

            new SendMail().execute(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    private class SendMail extends AsyncTask<Message, String, String> {
        private ProgressDialog progressDialog;


        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivityLogin.this, "Please Wait", "Sending Mail", true, false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();
            if (s.equals("Success")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityLogin.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'> Success</font>"));
                builder.setMessage("Mail send successfully");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
//                        correo.setText("");
//                        asunto.setText("");
//                        mensaje.setText("");
                    }
                });

                builder.show();

            } else {
                Toast.makeText(MainActivityLogin.this, "Someting also wrong", Toast.LENGTH_SHORT).show();
            }
        }


    }


}