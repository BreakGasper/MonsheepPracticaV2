package com.example.monsheeppractica.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.ImageFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.monsheeppractica.Activitys.MainActivityRegistroCategorias;
import com.example.monsheeppractica.GetterAndSetter.Categorias;
import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.ListaCategoriaAdapter;
import com.example.monsheeppractica.adaptadores.ListaClientesAdaptador;
import com.example.monsheeppractica.adaptadores.ListaUsuarioAdaptador;
import com.example.monsheeppractica.adaptadores.Lista_productoAdaptador;
import com.example.monsheeppractica.adaptadores.TestNormalAdapter;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.InsertarTabla;
import com.jude.rollviewpager.RollPagerView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivitySuperUser extends AppCompatActivity {
    SearchView txt_buscar;
    ImageView ivRegister, ivRegisterPromo, iv_foto;
    RadioButton rbProducto, rbCategoria, rbNegocio, rbUsuario;
    ListView lista;
    EditText etPromo;
    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio, like = "producto", fecha, hora, id;
    public static ArrayList<Categorias> categoriasArrayList = new ArrayList<>();
    public static ArrayList<Productos> productosArrayList = new ArrayList<>();
    public static ArrayList<Negocio> negociosArrayList = new ArrayList<>();
    public static ArrayList<Clientes> usuariosArrayList = new ArrayList<>();
    static SimpleDateFormat DateFormat_Fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    static SimpleDateFormat DateFormat_Hour = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private Uri mCropImageUri;
    String num_id = "", x;
    Date d = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_super_user);

        SharedPreferences preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");

        lista = findViewById(R.id.lvSuperUser);
        rbCategoria = findViewById(R.id.radio1);
        rbProducto = findViewById(R.id.radio0);
        rbNegocio = findViewById(R.id.radio3);
        rbUsuario = findViewById(R.id.radio2);
        txt_buscar = findViewById(R.id.txtBuscar);
        ivRegister = findViewById(R.id.ivRegister);
        ivRegisterPromo = findViewById(R.id.ivRegisterPromo);
        iv_foto = findViewById(R.id.ivRegisterPromo2);
        etPromo = findViewById(R.id.etPromo);
        productosArrayList.clear();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        lista.setAdapter(new Lista_productoAdaptador.listaP(this,
                consultarTabla.ProductoConsulta(productosArrayList, "all", "", "")));

        ivRegisterPromo.setOnClickListener(view -> {

            RegistrarDato();
        });

        ivRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivityRegistroCategorias.class);
            startActivity(intent);
            switch (like) {
                case "producto":

                    break;
                case "categoria":

                    break;
                case "negocio":

                    break;
                case "usuario":

                    break;
            }
        });
        txt_buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                try {
                    BuscarAll(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                try {
                    BuscarAll(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
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

        lista.setOnItemClickListener((adapterView, view, i, l) -> {
            if (like.equals("producto")) {
                id = String.valueOf(productosArrayList.get(i).getId_producto());
            } else if (like.equals("negocio")) {
                id = String.valueOf(negociosArrayList.get(i).getIdNegocio());
            } else if (like.equals("categoria")) {
                id = String.valueOf(categoriasArrayList.get(i).getId_catgoria());
            }
            if (like.equals("usuario")) {
                id = String.valueOf(usuariosArrayList.get(i).getId_cliente());
            }

            Toast.makeText(MainActivitySuperUser.this, like + ": " + id, Toast.LENGTH_SHORT).show();

        });
        ivRegister.setVisibility(View.GONE);

    }

    public void RegistrarDato() {
        Toast.makeText(this, "Selecciona un articulo de la lista: " + id, Toast.LENGTH_SHORT).show();
        if (!etPromo.getText().toString().isEmpty() && !id.equals(null)) {
            FechaYHoraActual();

            InsertarTabla insertarTabla = new InsertarTabla();
            insertarTabla.RegistrarPromocion(this, this, 0, fecha, hora,
                    "Activo", etPromo.getText().toString(), id);
            etPromo.setText("");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else etPromo.setError("Debes llenar el campo");


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
                    if (!etPromo.getText().toString().isEmpty()) {
                        DatabaseHandler db = new DatabaseHandler(this);
                        iv_foto.setImageURI(result.getUri());
                        x =  ""+ result.getUri();//getPath(result.getUri());
                        num_id = "" + (int) (Math.random() * 10000 + 1 * 3 + 15);
                        num_id = num_id + (int) (Math.random() * 505);
                        id = num_id;
//                        id = "" + View.generateViewId();
                        db.insertimage(x, num_id, id);
                        FechaYHoraActual();
                        InsertarTabla insertarTabla = new InsertarTabla();
                        insertarTabla.RegistrarPromocion(this, this, 0, fecha, hora,
                                "Activo", etPromo.getText().toString(), id);
                        etPromo.setText("");
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else{
                        Toast.makeText(this, "Debes llenar el campo \ny vuelve a cargar la imagen", Toast.LENGTH_SHORT).show();
                        etPromo.setError("Debes llenar el campo \ny vuelve a cargar la imagen");
                    }


                    //Toast.makeText(this, "Cropping successful, Sample:" + result.getSampleSize(), Toast.LENGTH_LONG).show();
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }

    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private String getPath(Uri uri) {
        if (uri == null) return null;

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int colum_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(colum_index);
        }

        return uri.getPath();
    }

    private void FechaYHoraActual() {
        fecha = DateFormat_Fecha.format(d);
        hora = DateFormat_Hour.format(d);
    }

    public void BuscarAll(String s) {
        clearData();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);

        switch (like) {
            case "producto":
                lista.setAdapter(new Lista_productoAdaptador.listaP(getApplicationContext(),
                        consultarTabla.ProductoConsulta(productosArrayList, "allLike", s, s)));


                break;
            case "categoria":

                lista.setAdapter(new ListaCategoriaAdapter(getApplicationContext(),
                        consultarTabla.CategoriaConsulta(categoriasArrayList, "search",
                                s, null, null)));

                break;
            case "negocio":
                lista.setAdapter(new ListaClientesAdaptador.listaP(getApplicationContext(),
                        consultarTabla.NegocioConsulta(negociosArrayList, "allLike",
                                s, s, s)));

                break;
            case "usuario":
                lista.setAdapter(new ListaUsuarioAdaptador.listaP(getApplicationContext(),
                        consultarTabla.ClientesConsulta(usuariosArrayList, "allLike",
                                0, s)));
                break;
        }

    }

    public void Selector(View view) {
        clearData();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        ivRegister.setVisibility(View.GONE);

        if (rbProducto.isChecked() == true) {
            lista.setAdapter(new Lista_productoAdaptador.listaP(this,
                    consultarTabla.ProductoConsulta(productosArrayList, "all", "", "")));
            like = "producto";
        } else if (rbCategoria.isChecked() == true) {
            lista.setAdapter(new ListaCategoriaAdapter(this,
                    consultarTabla.CategoriaConsulta(categoriasArrayList, "all",
                            "", null, null)));
            ivRegister.setVisibility(View.VISIBLE);
            like = "categoria";
        } else if (rbNegocio.isChecked() == true) {

            lista.setAdapter(new ListaClientesAdaptador.listaP(this,
                    consultarTabla.NegocioConsulta(negociosArrayList, "all",
                            "", "", "")));
            like = "negocio";
        } else if (rbUsuario.isChecked() == true) {
            lista.setAdapter(new ListaUsuarioAdaptador.listaP(this,
                    consultarTabla.ClientesConsulta(usuariosArrayList, "all",
                            0, "")));
            like = "usuario";
        }
    }

    public void clearData() {
        // limpiar la lista
        categoriasArrayList.clear();
        productosArrayList.clear();
        negociosArrayList.clear();
        usuariosArrayList.clear();
    }


}