package com.example.monsheeppractica;

import static com.example.monsheeppractica.WebService.wsDataDownload.NombreTablas;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.monsheeppractica.Activitys.MainActivityLogin;
import com.example.monsheeppractica.WebService.wsDataDownload;
import com.example.monsheeppractica.sqlite.sqlite;

public class MainActivitySplash extends AppCompatActivity {
    ImageView logo;
    private ObjectAnimator animatorx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);
        try {
            getSupportActionBar().hide();
            getActionBar().hide();
        } catch (Exception e) {
            //  Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
        }

        logo = (ImageView) findViewById(R.id.logo);

        int count= 0;
        for (int i = 0; i < NombreTablas().size(); i++) {
            count++;

            sqlite admin = new sqlite(this, "monsheep", null, 1);
            SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
//            if (!NombreTablas().get(i).toString().equals("images_categoria")) {
                BaseDeDatos.delete(NombreTablas().get(i).toString(), "", null);
//            }
            wsDataDownload download = new wsDataDownload(this, MainActivitySplash.this);
            download.cargarWebService(NombreTablas().get(i).toString());
            //System.out.println(NombreTablas().get(i).toString());"clientes"
             if (count == NombreTablas().size()){
                        Toast.makeText(this, ""+count, Toast.LENGTH_SHORT).show();
                        animacion2();
                    }
        }

        logo.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivityLogin.class);
            startActivity(intent);
        });

    }

    private void animacion2() {
        animatorx = ObjectAnimator.ofFloat(logo, "rotation", 0f, 360f);
        animatorx.setDuration(2000);
        animatorx.setRepeatCount(ObjectAnimator.INFINITE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorx);
        animatorSet.start();
        splash();

    }

    private void splash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivityLogin.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}