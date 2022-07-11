package com.example.monsheeppractica.Activitys;

import static com.example.monsheeppractica.Activitys.MainActivityDetalles.DateFormat_Fecha;
import static com.example.monsheeppractica.Activitys.MainActivityDetalles.DateFormat_Hour;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Categorias;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.AdaptadorComentario;
import com.example.monsheeppractica.adaptadores.ListaCategoriaAdapter;
import com.example.monsheeppractica.mytools.NumberTextWatcher;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;
import com.example.monsheeppractica.sqlite.registros.InsertarTabla;
import com.example.monsheeppractica.sqlite.sqlite;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivityRegistroProductos extends AppCompatActivity {
    EditText et_nombre, et_costo, et_precio_menudeo, et_precio_mayoreo, et_marca, et_cant_min;
    Spinner sp_categoria, sp_unidad_medida, spColores;
    ImageView iv_foto;
    Button btn_pro;

    String nombre;
    String costo;
    String menudeo;
    String mayoreo;
    String marca;
    String color;
    String cant_min;
    String unidad_med = "Pieza", fecha, hora;
    String cat_nombre_sp = "Sin Categoria";
    int cat_id_sp = 007;
    String[] colores = {"Rojo", "Naranja", "Rosa", "Amarillo", "Verde", "Azul", "Violeta", "Negro", "Blanco"};
    static SimpleDateFormat DateFormat_Fecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    static SimpleDateFormat DateFormat_Hour = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    DatabaseHandler db;
    String num_id = "", x;
    int id_producto;
    Date d = new Date();
    private String current = "";
    //url de recorte de imagen
    private Uri mCropImageUri;
    int cont = 0;
    public static ArrayList<Categorias> categoriasArrayList = new ArrayList<>();
    LinearLayout ll_producto;
    ContentValues registro;
    SQLiteDatabase BaseDeDatos;
    String[] Unidad_de_medida = {"Pieza", "Litro", "Paquete", "Metros"};
    String[] catego_sp_edit;
    String id_producto_extra, Id_Foto_extra, categoria_nombre_extra, costo_extra, producto_extra, ventaMenudeo_extra, ventaMayoreo_extra, marca_extra, color_p_extra, unidadMedida_extra, id_categoria_extra, status_extra, cantidadMinima_extra, editar_extra;
    String negocioname="";
    String idUser = "", idFotoUser, NombreUser, idNegocio;
    String listar = "0";
    ArrayList<Negocio> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro_productos);

        et_nombre = (EditText) findViewById(R.id.et_nombre_pro);
        et_costo = (EditText) findViewById(R.id.et_costo_pro);
        et_precio_menudeo = (EditText) findViewById(R.id.et_precio_menudeo_pro);
        et_precio_mayoreo = (EditText) findViewById(R.id.et_precio_mayoreo_pro);
        et_marca = (EditText) findViewById(R.id.et_marca_pro);
        spColores = (Spinner) findViewById(R.id.sp_colores);
        et_cant_min = (EditText) findViewById(R.id.et_cant_min_pro);

        sp_categoria = (Spinner) findViewById(R.id.sp_categoria);
        sp_unidad_medida = (Spinner) findViewById(R.id.sp_unidad_medida_pro);

        iv_foto = (ImageView) findViewById(R.id.iv_foto_prod);

        btn_pro = (Button) findViewById(R.id.btn_gd_pro);

        ll_producto = (LinearLayout) findViewById(R.id.ll_prod);
        //Referencia a la tabla imagenes categorias
        db = new DatabaseHandler(this);


        //Obtener datos para la edicion
        editar_extra = getIntent().getStringExtra("editar");
        id_producto_extra = getIntent().getStringExtra("id_producto");
        Id_Foto_extra = getIntent().getStringExtra("Id_Foto");
        categoria_nombre_extra = getIntent().getStringExtra("categoria_nombre");
        costo_extra = getIntent().getStringExtra("costo");//******
        producto_extra = getIntent().getStringExtra("producto");//***
        ventaMenudeo_extra = getIntent().getStringExtra("ventaMenudeo");//****
        ventaMayoreo_extra = getIntent().getStringExtra("ventaMayoreo");//****
        marca_extra = getIntent().getStringExtra("marca");//***
        color_p_extra = getIntent().getStringExtra("color_p");///****
        unidadMedida_extra = getIntent().getStringExtra("unidadMedida");
        id_categoria_extra = getIntent().getStringExtra("id_categoria");
        status_extra = getIntent().getStringExtra("status");
        cantidadMinima_extra = getIntent().getStringExtra("cantidadMinima");//****

        SharedPreferences preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");

//        Datos del usuario
//        idUser =  getIntent().getStringExtra("idusers");
//        NombreUser =  getIntent().getStringExtra("NombreUser");
//        idFotoUser =  getIntent().getStringExtra("idFotoUser");
//        Toast.makeText(this, ""+idUser+NombreUser+idFotoUser, Toast.LENGTH_SHORT).show();

        try {
            lista = new ArrayList<>();
            ConsultarTabla consultarTabla = new ConsultarTabla(this);
            lista = consultarTabla.NegocioConsulta(lista, "like", idNegocio, "", "");
           // Toast.makeText(this, "" + lista.size(), Toast.LENGTH_SHORT).show();


            lista.size();
            listar = lista.get(0).getId_Foto();
            negocioname = lista.get(0).getNombre();

        } catch (Exception e) {
            Toast.makeText(this, "quepdo" + e, Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
     //   Toast.makeText(this, "q" + idNegocio +negocioId+ listar, Toast.LENGTH_SHORT).show();



        et_costo.addTextChangedListener(new NumberTextWatcher(et_costo));
        et_precio_menudeo.addTextChangedListener(new NumberTextWatcher(et_precio_menudeo));
        et_precio_mayoreo.addTextChangedListener(new NumberTextWatcher(et_precio_mayoreo));
        et_cant_min.addTextChangedListener(new NumberTextWatcher(et_cant_min));
        if (editar_extra == null) {
            //Registrar
            spinner_categoria();
        } else {
            if (editar_extra.equals("detalle")) {

                btn_pro.setVisibility(View.INVISIBLE);

                ll_producto.setEnabled(false);
                et_nombre.setEnabled(false);
                et_costo.setEnabled(false);
                et_precio_menudeo.setEnabled(false);
                et_precio_mayoreo.setEnabled(false);
                et_marca.setEnabled(false);
                iv_foto.setEnabled(false);
                et_cant_min.setEnabled(false);
                sp_unidad_medida.setEnabled(false);
                sp_categoria.setEnabled(false);
                spColores.setEnabled(false);
            }
            btn_pro.setText("Editar");

            Unidad_de_medida = new String[]{"" + unidadMedida_extra, "Nueva unidad de medida"};
            catego_sp_edit = new String[]{"" + categoria_nombre_extra, "Nueva Categoria"};
            colores = new String[]{"" + color_p_extra, "Nuevo Color"};

            spinner_edit();

            et_nombre.setText("" + producto_extra);
            et_costo.setText("" + costo_extra);
            et_precio_menudeo.setText("" + ventaMenudeo_extra);
            et_precio_mayoreo.setText("" + ventaMayoreo_extra);
            et_marca.setText("" + marca_extra);
            // et_color.setText(""+color_p_extra);
            et_cant_min.setText("" + cantidadMinima_extra);

            if (Id_Foto_extra == null || Id_Foto_extra.isEmpty()) {
                //Toast.makeText(getApplicationContext(), "Sin foto"+id_foto_extra, Toast.LENGTH_SHORT).show();

            } else {
                try {
                    iv_foto.setImageBitmap(db.getimage(Id_Foto_extra));


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                }

                //con foto
            }
            //Edicion
        }
        spinnerColores();

        spinner_medida();

        btn_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validar_datos();
            }
        });

        iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abrirGaleria();

                // Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                onSelectImageClick(view);

            }
        });

    }

    public void spinner_categoria() {
        categoriasArrayList.clear();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        sp_categoria.setAdapter(new ListaCategoriaAdapter(this,
                consultarTabla.CategoriaConsulta(categoriasArrayList, "all", null, null, null)));

        sp_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                cat_nombre_sp = categoriasArrayList.get(i).getCategoria();
                cat_id_sp = categoriasArrayList.get(i).getId_catgoria();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void spinner_medida() {

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, R.layout.adaptadorspinner, Unidad_de_medida);
        sp_unidad_medida.setAdapter(adapter);
        //sp_unidad_medida.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
        // android.R.layout.simple_spinner_dropdown_item,Unidad_de_medida));
        sp_unidad_medida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(adapterView.getContext(),(String) adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                unidad_med = (String) adapterView.getItemAtPosition(i);
                if (unidad_med.equals("Nueva unidad de medida")) {
                    Unidad_de_medida = new String[]{"Pieza", "Litro", "Paquete", "Metros"};
                    spinner_medida();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void spinner_edit() {


        sp_categoria.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, catego_sp_edit));


        sp_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(adapterView.getContext(),(String) adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();

                //   =categoriasArrayList.get(i).getCategoria();
                cat_nombre_sp = (String) adapterView.getItemAtPosition(i);

                ///Toast.makeText(getApplicationContext(), ""+cont, Toast.LENGTH_SHORT).show();
                if (cat_nombre_sp.equals("Nueva Categoria")) {
                    spinner_categoria();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                    x = getPath(result.getUri());
                    num_id = "" + (int) (Math.random() * 10000 + 1 * 3 + 15);


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

    public void lista_query_sin_duplicados() {

        sqlite bh = new sqlite
                (this, "producto", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from producto where producto LIKE '" + nombre + "'", null);

            if (c.moveToFirst()) {
                do {
                    Alert();
                    //Toast.makeText(getApplicationContext(), "Categoria !Ya existe!", Toast.LENGTH_SHORT).show();
                } while (c.moveToNext());
            } else {
                // Toast.makeText(getApplicationContext(), ""+editar_extra, Toast.LENGTH_SHORT).show();
                if (editar_extra == null || editar_extra.isEmpty()) {
                    Registrar();
                    //Toast.makeText(getApplicationContext(), "Registro", Toast.LENGTH_SHORT).show();
                    //Registrar
                } else {
                    //Editar
                    Editar();
                }


            }
        }
    }


    private void Editar() {
        try {

            EditarTabla etabla = new EditarTabla();
            if (etabla.EditarProducto(this, nombre, costo, menudeo, mayoreo, marca, color, unidad_med,
                    cat_nombre_sp, cat_id_sp, cant_min, categoria_nombre_extra,
                    Integer.parseInt(id_categoria_extra), num_id, x, id_producto_extra) == 1) {

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("idusers", "" + idUser);
                intent.putExtra("NombreUser", NombreUser);
                intent.putExtra("idFotoUser", idFotoUser);
                startActivity(intent);
                finish();

            }


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Validacion: " + e, Toast.LENGTH_SHORT).show();
        }

    }

    public void Devolver(Context context, String idFoto,
                         String id_producto, ContentValues registro,
                         String x, SQLiteDatabase BaseDeDatos) {
        db = new DatabaseHandler(context);
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        //Mensaje
        alerta.setTitle(Html.fromHtml("<font color='#269BF4'>Mensaje informativo</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("Existe una imagen asociada, ¿desea reemplazarla?");

        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    registro.put("Id_Foto", idFoto.trim());
                    //Registro de imagen en la base de datos
                    if (!idFoto.isEmpty()) {
                        if (db.insertimage(x, idFoto, String.valueOf(id_producto))) {
                            Toast.makeText(context,
                                    "Edicion Exitosa,\nImagen insertada", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(context,
                                    "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context,
                                "Registro Exitoso,\nNo guardaste ninguna imagen", Toast.LENGTH_SHORT).show();
                    }
                    int cantid = BaseDeDatos.update(
                            "producto", registro, "id_producto= " + Integer.parseInt(id_producto), null);
                    BaseDeDatos.close();
                    if (cantid == 1) {
                        Nose(context);
                        Toast.makeText(context, "Edicion Exitosa", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "Producto no existe", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                    Toast.makeText(context, "ExSi" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    int cantid = BaseDeDatos.update(
                            "producto", registro, "id_producto= " + Integer.parseInt(id_producto), null);
                    BaseDeDatos.close();
                    if (cantid == 1) {
                        Nose(context);
                        Toast.makeText(context, "Edicion Exitosa", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, "Producto no existe", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                    Toast.makeText(context, "ExNo" + e, Toast.LENGTH_SHORT).show();
                }

            }
        });
        alerta.show();

    }

    public void Nose(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        finish();
    }

    public void ObtenerDatos_box() {

        nombre = et_nombre.getText().toString();
        costo = et_costo.getText().toString();
        menudeo = et_precio_menudeo.getText().toString();
        mayoreo = et_precio_mayoreo.getText().toString();
        marca = et_marca.getText().toString();

        cant_min = et_cant_min.getText().toString();


    }

    private void Registrar() {
        FechaYHoraActual();
        ObtenerDatos_box();

        InsertarTabla inTab = new InsertarTabla();
       // ConsultarTabla consultarTabla = new ConsultarTabla(this);

//NombreUser
        if (x != null && !x.isEmpty()) {
            inTab.RegistrarProducto(this, id_producto, nombre, costo, menudeo, mayoreo,
                    marca, x, color, unidad_med, cat_nombre_sp, String.valueOf(cat_id_sp),
                    num_id, "Activo", cant_min, listar, idUser, negocioname, idNegocio,fecha,hora);//idNegocio

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("final", "produ");
            intent.putExtra("idusers", "" + idUser);
            intent.putExtra("NombreUser", NombreUser);
            intent.putExtra("idFotoUser", idFotoUser);
            startActivity(intent);

            finish();
        } else {
            Toast.makeText(this, "Agrega una foto", Toast.LENGTH_SHORT).show();
        }


//        finish();
    }
    private  void  FechaYHoraActual(){
        fecha = DateFormat_Fecha.format(d);
        hora = DateFormat_Hour.format(d);
    }
    public void spinnerColores() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.adaptadorspinner, colores);

        spColores.setAdapter(adapter);
        //spUnidadMedida.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item,Unidad_de_medida));


        spColores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                color = (String) adapterView.getItemAtPosition(i);

                if (color.equals("Nuevo Color")) {
                    colores = new String[]{"Rojo", "Naranja", "Rosa", "Amarillo", "Verde", "Azul", "Violeta", "Negro", "Blanco"};

                    spinnerColores();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    public void Validar_datos() {
        ObtenerDatos_box();

      //  Toast.makeText(this, "" + costo + mayoreo, Toast.LENGTH_SHORT).show();
        try {
            float costo_parse = Float.parseFloat(costo);
            //float menudeo_parse = Float.parseFloat(menudeo);
            float mayoreo_parse = Float.parseFloat(mayoreo);


            if (!nombre.isEmpty() && nombre.length() >= 4 && nombre.length() <= 70) {
                if (costo_parse >= 1) {
                    if (!menudeo.isEmpty() && menudeo.length() >= 5 && menudeo.length() <= 100) {
                        if (mayoreo_parse >= 1) {
                            if (!marca.isEmpty() && marca.length() >= 3 && marca.length() <= 100) {
                                //  if (!color.isEmpty() && color.length() >= 5 && color.length() <=40) {
                                if (!cant_min.isEmpty()) {
                                    if (!cat_nombre_sp.isEmpty()) {
                                        if (nombre.equals(producto_extra)) {

                                            Editar();
                                            //Toast.makeText(getApplicationContext(), "Registro", Toast.LENGTH_SHORT).show();

                                        } else {

                                            //Toast.makeText(getApplicationContext(), "Editar", Toast.LENGTH_SHORT).show();
                                            lista_query_sin_duplicados();
                                        }
                                    } else
                                        Toast.makeText(getApplicationContext(), "No tienes categorias disponibles, \n Favor de asignar una", Toast.LENGTH_SHORT).show();

                                } else et_cant_min.setError("Es obligatorio este campo");

                                // }else et_color.setError("El campo color es obligatorio y debe ser al menos de 5 caracteres");

                            } else
                                et_marca.setError("El campo marca es obligatorio y debe ser al menos de 3 caracteres ");

                        } else et_precio_mayoreo.setError("El costo es obligatorio");
                    } else
                        et_precio_menudeo.setError("La Descripcion es obligatorio y debe ser al menos de 5 caracteres a 100 caracteres");

                } else et_costo.setError("El costo es obligatorio");

            } else
                et_nombre.setError("El campo categoría es obligatorio y debe ser al menos de 5 caracteresa");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
            System.out.println("" + e);
        }

    }

    /*
    private void Alert_foto(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        //Mensaje
        alerta.setTitle( Html.fromHtml("<font color='#269BF4'>Mensaje informativo</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("Existe una imagen asociada, ¿desea reemplazarla?");

        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                registro.put("Id_Foto", num_id.trim());
                //Registro de imagen en la base de datos
                if (!num_id.isEmpty()){
                    if (db.insertimage(x, num_id, String.valueOf(id_producto))) {
                        Toast.makeText(getApplicationContext(), "Edicion Exitosa,\nImagen insertada", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Registro Exitoso,\nNo guardaste ninguna imagen", Toast.LENGTH_SHORT).show();
                }

                Validacion_foto_editar_alerta();
            }
        });

        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    iv_foto.setImageBitmap(db.getimage(Id_Foto_extra));
                    Validacion_foto_editar_alerta();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        alerta.show();
    }
    public void Validacion_foto_editar_alerta(){

        int cantid = BaseDeDatos.update("producto", registro, "id_producto= " + Integer.parseInt(id_producto_extra), null);

        BaseDeDatos.close();

        if ( cantid == 1) {
            Toast.makeText(this, "Edicion Exitosa", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("final", "produ");
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Categoria no existe", Toast.LENGTH_SHORT).show();
        }
    }*/
    private void Alert() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        //Mensaje
        alerta.setTitle(Html.fromHtml("<font color='#269BF4'>Mensaje informativo</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("El producto ya fue previamente registrado y no puede duplicarse");

        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Mensaje2();
            }
        });
        alerta.show();
    }

}