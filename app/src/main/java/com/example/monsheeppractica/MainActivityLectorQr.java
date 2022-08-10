package com.example.monsheeppractica;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivityLectorQr extends AppCompatActivity {
    private Button button;
    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String id_ticket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lector_qr);

        button = (Button) findViewById(R.id.button_start_scan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivityLectorQr.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Lector =MoonSheep");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result =IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents()== null){
                Toast.makeText(this, "Cancelada", Toast.LENGTH_SHORT).show();
            }else {
                // Toast.makeText(this, ""+result.getContents(), Toast.LENGTH_SHORT).show();
                id_ticket = result.getContents();
                Toast.makeText(this, ""+result.getContents(), Toast.LENGTH_SHORT).show();
                //Se puede añadir el edit text

            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
}