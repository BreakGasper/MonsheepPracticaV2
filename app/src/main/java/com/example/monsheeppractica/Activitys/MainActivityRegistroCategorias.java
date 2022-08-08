package com.example.monsheeppractica.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;
import com.example.monsheeppractica.sqlite.registros.InsertarTabla;
import com.example.monsheeppractica.sqlite.sqlite;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivityRegistroCategorias extends AppCompatActivity {

    EditText et_catego, et_descrip;
    ImageView iv_foto;
    Button btn_catgo;
    DatabaseHandler db;
    int id_catego;
    String cant;
    private Uri mCropImageUri;
    String catego="", descrip="", desc_extra="", cat_extra="", id_foto_extra="", code_catego_extra="",editar_extra="";
    String num_id="",x;
    ContentValues registro;
    SQLiteDatabase BaseDeDatos;
    Bitmap bmp;
    String Stimagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro_categorias);

        et_catego =(EditText) findViewById(R.id.et_categoria);
        et_descrip =(EditText) findViewById(R.id.et_descripcion);
        iv_foto = (ImageView)findViewById(R.id.iv_foto);

        btn_catgo = (Button)findViewById(R.id.btn_gd_catego);

        //Referencia a la tabla imagenes categorias
        db = new DatabaseHandler(this);

        //Oftener datos para la edicion
        editar_extra =  getIntent().getStringExtra("editar");
        code_catego_extra =  getIntent().getStringExtra("code");
        desc_extra =  getIntent().getStringExtra("Descripcion");
        cat_extra =  getIntent().getStringExtra("categoria");
        id_foto_extra =  getIntent().getStringExtra("id_foto");

        if (editar_extra == null){
            //Registrar
        }else{
            btn_catgo.setText("Editar");
            et_catego.setText(""+cat_extra);
            et_descrip.setText(""+desc_extra);
            if (id_foto_extra==null || id_foto_extra.isEmpty()){
                //Toast.makeText(getApplicationContext(), "Sin foto"+id_foto_extra, Toast.LENGTH_SHORT).show();

            }else {
                try {
                    iv_foto.setImageBitmap(db.getimageID(id_foto_extra));

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
                }

                //con foto
            }
            //Edicion
        }


        btn_catgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_catgo.setEnabled(false);

                Validar_datos();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_catgo.setEnabled(true);

                    }
                }, 2000);

            }
        });

        iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iv_foto.setEnabled(false);

                onSelectImageClick(view);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iv_foto.setEnabled(true);

                    }
                }, 2000);

            }
        });
    }

    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // handle result of pick image chooser
            if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                Uri imageUri = CropImage.getPickImageResultUri(this, data);

                // For API >= 23 we need to check specifically that we have permissions to read external storage.
                if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                    // request permissions and handle the result in onRequestPermissionsResult()
                    mCropImageUri = imageUri;
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

                } else {
                    // no permissions required or already grunted, can start crop image activity
                    startCropImageActivity(imageUri);
                }
            }

            // handle result of CropImageActivity
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {


                     iv_foto.setImageURI(result.getUri());
                    x =  ""+ result.getUri();//getPath(result.getUri());
                    num_id=""+(int)(Math.random()*10000+1*3+15);

//                    FileInputStream file = new FileInputStream(x);
//                    byte[] imgbyte = new byte[file.available()];
//                    file.read(imgbyte);
//
//                    InputStream imageStream = null;
//                    try {
//                        imageStream = getContentResolver().openInputStream(
//                                result.getUri());
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        bmp = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),result.getUri());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    // Transformamos la URI de la imagen a inputStream y este a un Bitmap
//                    bmp = BitmapFactory.decodeStream(imageStream);
//
//
//                    // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
//                    //ImageView mImg = (ImageView) findViewById(R.id.imagen);
//                    iv_foto.setImageBitmap(bmp);

                    //Toast.makeText(this, "Cropping successful, Sample:" + result.getSampleSize(), Toast.LENGTH_LONG).show();
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }


    }



    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
    private String getPath(Uri uri) {
        if (uri ==null) return null;

        String[] projection ={MediaStore.Images.Media.DATA};
        Cursor cursor =this.managedQuery(uri,projection, null, null,null);
        if (cursor !=null){
            int colum_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(colum_index);
        }

        return uri.getPath();
    }
    public void lista_query_sin_duplicados(){

        sqlite bh = new sqlite
                (this, "monsheep", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from categoria where categoria LIKE '"+catego+"'" , null);

            if (c.moveToFirst()) {
                do {
                    Alert();
                    //Toast.makeText(getApplicationContext(), "Categoria !Ya existe!", Toast.LENGTH_SHORT).show();
                } while (c.moveToNext());
            }else {

                if (editar_extra== null || editar_extra.isEmpty()){
                    Registrar();
                    //Registrar
                }else {
                    //Editar
                    Editar();

                }
            }
        }
    }

    public void Devolver(Context context,String idFoto,
                         String idCategoria,ContentValues registro,
                         String x,SQLiteDatabase BaseDeDatos){
        db = new DatabaseHandler(context);
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        //Mensaje
        alerta.setTitle( Html.fromHtml("<font color='#269BF4'>Mensaje informativo</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("Existe una imagen asociada, ¿desea reemplazarla?");

        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                EditarTabla ed= new EditarTabla();
                try {
                    registro.put("Id_Foto", idFoto.trim());
                    //Registro de imagen en la base de datos
                    if (!idFoto.isEmpty()){
                        if (db.insertimage(x, idFoto, String.valueOf(idCategoria))) {
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                            TextView text = (TextView) viewInput.findViewById(R.id.text12);
                            text.setText("Edicion Exitosa");

                            Toast toast = new Toast(context);
                            //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(viewInput);
                            toast.show();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                        TextView text = (TextView) viewInput.findViewById(R.id.text12);
                        text.setText("Edicion Exitosa");

                        Toast toast = new Toast(context);
                        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(viewInput);
                        toast.show(); }

                    int cantid = BaseDeDatos.update("categoria", registro, "id_categoria= " + Integer.parseInt(idCategoria), null);
                    BaseDeDatos.close();
                    if ( cantid == 1) {
                        Nose(context);
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                        TextView text = (TextView) viewInput.findViewById(R.id.text12);
                        text.setText("Edicion Exitosa");

                        Toast toast = new Toast(context);
                        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(viewInput);
                        toast.show();

                    }else {
                        Toast.makeText(context, "Categoria no existe", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                    Toast.makeText(context, "ExSi"+e, Toast.LENGTH_SHORT).show();
                }


            }
        });

        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    int cantid = BaseDeDatos.update("categoria", registro, "id_categoria= " + Integer.parseInt(idCategoria), null);
                    BaseDeDatos.close();
                    if ( cantid == 1) {
                      Nose(context);
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                        TextView text = (TextView) viewInput.findViewById(R.id.text12);
                        text.setText("Edicion Exitosa");

                        Toast toast = new Toast(context);
                        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(viewInput);
                        toast.show();
                    }else {
                        Toast.makeText(context, "Categoria no existe", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                    Toast.makeText(context, "ExNo"+e, Toast.LENGTH_SHORT).show();
                }

            }
        });
        alerta.show();

    }

    public void Nose(Context context){
        Intent intent= new Intent(context, MainActivity.class);
        context.startActivity(intent);
        finish();
    }




    private void Editar() {

//        preferences = getSharedPreferences("validar", Context.MODE_PRIVATE);
//        cant = preferences.getString("cant","0");
        EditarTabla eTabla= new EditarTabla();

        int a = eTabla.EditarCategoria(this,code_catego_extra,catego,descrip,
                num_id,"","",x,id_foto_extra);
       // Toast.makeText(this, ""+a, Toast.LENGTH_SHORT).show();

            if ( a== 1){
                Intent intent= new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }

           // iv_foto.setImageBitmap(db.getimage(id_foto_extra));

    }

    private void Registrar() {
//        Stimagen=convertirImgString(bmp);
        InsertarTabla tDatos= new InsertarTabla();
        tDatos.RegistrarCategoria(this,id_catego,catego,descrip,num_id,"Activo","",x);

        et_catego.setText("");
        et_descrip.setText("");

        finish();
    }


    public void Validar_datos(){
        descrip = et_descrip.getText().toString();
        catego = et_catego.getText().toString();

        if (!catego.isEmpty() && catego.length() >= 4 && catego.length() <=70 ){
            if (!descrip.isEmpty() && descrip.length() >= 10 && descrip.length() <=150) {
                if (catego.equals(cat_extra)){
                    Editar();

                }else lista_query_sin_duplicados();

            }else {
                et_descrip.setError("El campo descripción es obligatorio y debe ser al menos de 10 caracteres");
            }

        }else {
            et_catego.setError("El campo categoría es obligatorio y debe ser al menos de 5 caracteresa");
        }
    }




    private void Alert(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        //Mensaje
        alerta.setTitle( Html.fromHtml("<font color='#269BF4'>Mensaje informativo</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("La categoria ya fue previamente registrada y no puede duplicarse");

        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Mensaje2();
            }
        });
        alerta.show();
    }


}