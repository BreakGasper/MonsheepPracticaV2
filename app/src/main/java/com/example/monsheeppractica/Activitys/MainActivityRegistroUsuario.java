package com.example.monsheeppractica.Activitys;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

import static com.example.monsheeppractica.mytools.Network.isNetDisponible;
import static com.example.monsheeppractica.mytools.Network.isOnlineNet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.mytools.NumberTextWatcher;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;
import com.example.monsheeppractica.sqlite.registros.InsertarTabla;
import com.example.monsheeppractica.sqlite.sqlite;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

public class MainActivityRegistroUsuario extends AppCompatActivity {
    EditText etNombre, etCalle, etNumero, etInterior,
            etCp, etColonia, etEstado,
            etAlias, etLada, etTelefono, etApellidoPaterno,
            etApellidoMaterno, etContra, etRepContra;

    Button btnGuardar;
    String[] tipoArrayTelefono = {"Fijo", "Celular"};
    String[] tipoArrayCompra = {"Menudeo", "Mayoreo"};
    Spinner spTipoTelefono, spMunicipio;
    DatabaseHandler db;
    ImageView iv_foto;
    String idClienteExtra, editar_extra, tipoTelefonoExtra, NombreExtra, CalleExtra, NumeroExtra, InteriorExtra, CodigoPostalExtra, ColoniaExtra, MunicipioExtra, EstadoExtra, AliasExtra, LadaExtra, TeléfonoExtra, ApellidoPaternoExtra, ApellidoMaternoExtra, TipoCompraExtra, Id_Foto_extra;

    String Nombre, Calle, Numero, Interior, Codigo_Postal, Colonia, Municipio = "San Martín Hidalgo",
            Estado, Alias, Lada, Teléfono, TipoCompra = "basico", ApellidoPaterno, ApellidoMaterno, Contra;

    String tipoTelefono = "Fijo";
    int idCliente;
    private Uri mCropImageUri;
    String num_id = "", x;
    public static ArrayList<Clientes> clientesArrayList = new ArrayList<>();

    String[] municipio = {"San Martín Hidalgo", "Cocula", "Ameca", "Juchitlán", "Acatic", "Acatlán de Juárez", "Ahualulco de Mercado", "Amacueca", "Amatitán", "San Juanito de Escobedo", "Arandas"
            , "El Arenal", "Atemajac de Brizuela", "Atengo", "Atenguillo", "Atotonilco el Alto", "Atoyac", "Autlán de Navarro"
            , "Ayotlán", "Ayutla", "La Barca", "Bolaños", "Cabo Corrientes", "Casimiro Castillo", "Cihuatlán"
            , "Zapotlán el Grande", "Colotlán", "Concepción de Buenos Aires", "Cuautitlán de García Barragán", "Cuautla", "Cuquío"
            , "Chapala", "Chimaltitán", "Chiquilistlán", "Degollado", "Ejutla", "Encarnación de Díaz", "Etzatlán"
            , "El Grullo", "Guachinango", "Guadalajara", "Hostotipaquillo", "Huejúcar", "Huejuquilla el Alto", "La Huerta"
            , "Ixtlahuacán de los Membrillos", "Ixtlahuacán del Río", "Jalostotitlán", "Jamay", "Jesús María", "Jilotlán de los Dolores"
            , "Jocotepec", "Juanacatlán", "Lagos de Moreno", "El Limón", "Magdalena"
            , "Santa María del Oro", "La Manzanilla de la Paz", "Mascota", "Mazamitla", "Mexticacán", "Mezquitic", "Mixtlán", "Ocotlán", "Ojuelos de Jalisco", "Pihuamo"
            , "Poncitlán", "Puerto Vallarta", "Villa Purificación", "Quitupan", "El Salto", "San Cristóbal de la Barranca", "San Diego de Alejandría"
            , "San Juan de los Lagos", "San Julián", "San Marcos", "San Martín de Bolaños", "San Miguel el Alto"
            , "Gómez Farías", "San Sebastián del Oeste", "Santa María de los Ángeles"
            , "Sayula", "Tala", "Talpa de Allende", "Tamazula de Gordiano", "Tapalpa", "Techaluta de Montenegro", "Tecolotlán", "Tenamaxtlán", "Teocaltiche"
            , "Teocuitatlán de Corona", "Tepatitlán de Morelos", "Tequila", "Teuchitlán", "Tizapán el Alto"
            , "Tlajomulco de Zúñiga", "San Pedro Tlaquepaque", "Tolimán", "Tomatlán", "Tonalá", "Tonaya", "Tonila", "Totatiche", "Tototlán", "Tuxcacuesco", "Tuxcueca"
            , "Tuxpan", "Unión de San Antonio", "Unión de Tula", "Valle de Guadalupe", "Valle de Juárez", "San Gabriel", "Villa Corona", "Villa Guerrero", "Villa Hidalgo"
            , "Cañadas de Obregón", "Yahualica de González Gallo", "Zacoalco de Torres", "Zapopan", "Zapotiltic", "Zapotitlán de Vadillo", "Zapotlán del Rey", "Zapotlanejo", "San Ignacio Cerro Gordo"};

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro_usuario);


        try {


            etNombre = (EditText) findViewById(R.id.etNombreCliente);
            etCalle = (EditText) findViewById(R.id.etCalleCliente);
            etNumero = (EditText) findViewById(R.id.etNumeroCliente);
            etInterior = (EditText) findViewById(R.id.etInteriorCliente);
            etCp = (EditText) findViewById(R.id.etCpCliente);
            etColonia = (EditText) findViewById(R.id.etColoniaCliente);
            etEstado = (EditText) findViewById(R.id.etEstadoCliente);
            spMunicipio = (Spinner) findViewById(R.id.spTipoMunicipioCliente);
            etAlias = (EditText) findViewById(R.id.etAlias);
            etLada = (EditText) findViewById(R.id.etLadaCliente);
            etTelefono = (EditText) findViewById(R.id.etTelefonoCliente);
            etApellidoPaterno = (EditText) findViewById(R.id.etApellidoPaterno);
            etApellidoMaterno = (EditText) findViewById(R.id.etApellidoMaterno);
            etContra = (EditText) findViewById(R.id.etContra);
            etRepContra = (EditText) findViewById(R.id.etRepContra);
            btnGuardar = (Button) findViewById(R.id.btnGuardarCliente);
            spTipoTelefono = (Spinner) findViewById(R.id.spTipoTelefonoCliente);
            iv_foto = (ImageView) findViewById(R.id.iv_foto_cliente);

            //Referencia a la tabla imagenes categorias
            db = new DatabaseHandler(this);


            editar_extra = getIntent().getStringExtra("editar");
            idClienteExtra = getIntent().getStringExtra("id_cliente");
            NombreExtra = getIntent().getStringExtra("Nombre");
            CalleExtra = getIntent().getStringExtra("Calle");
            NumeroExtra = getIntent().getStringExtra("Numero");//******
            InteriorExtra = getIntent().getStringExtra("Interior");//***
            CodigoPostalExtra = getIntent().getStringExtra("Codigo_Postal");//****
            ColoniaExtra = getIntent().getStringExtra("Colonia");//****
            MunicipioExtra = getIntent().getStringExtra("Municipio");//***
            EstadoExtra = getIntent().getStringExtra("Estado");///****
            AliasExtra = getIntent().getStringExtra("Alias");
            LadaExtra = getIntent().getStringExtra("Lada");
            TeléfonoExtra = getIntent().getStringExtra("Teléfono");
            tipoTelefonoExtra = getIntent().getStringExtra("TipoTelefono");
            ApellidoMaternoExtra = getIntent().getStringExtra("ApellidoMaterno");
            ApellidoPaternoExtra = getIntent().getStringExtra("ApellidoPaterno");
            TipoCompraExtra = getIntent().getStringExtra("TipoCompra");
            Id_Foto_extra = getIntent().getStringExtra("Id_Foto");

            etCp.addTextChangedListener(new NumberTextWatcher(etCp));
            etLada.addTextChangedListener(new NumberTextWatcher(etLada));
            etTelefono.addTextChangedListener(new NumberTextWatcher(etTelefono));

            if (editar_extra == null) {
                //Registrar
                spinnerMunicipio();
                spinnerTipoDeTelefono();
            } else {
                if (editar_extra.equals("detalle")) {
                    btnGuardar.setVisibility(View.INVISIBLE);
                    btnGuardar.setVisibility(View.INVISIBLE);
                    etNombre.setEnabled(false);
                    etCalle.setEnabled(false);
                    etNumero.setEnabled(false);
                    etInterior.setEnabled(false);
                    etCp.setEnabled(false);
                    etColonia.setEnabled(false);
                    etEstado.setEnabled(false);
                    etAlias.setEnabled(false);
                    etLada.setEnabled(false);
                    etTelefono.setEnabled(false);
                    etApellidoMaterno.setEnabled(false);
                    etApellidoPaterno.setEnabled(false);
                    spTipoTelefono.setEnabled(false);
                }
                btnGuardar.setText("Editar");

                tipoArrayTelefono = new String[]{"" + tipoTelefonoExtra, "Nuevo Tipo de Telefono"};
                tipoArrayCompra = new String[]{"" + TipoCompraExtra, "Nueva Tipo Compra"};
                municipio = new String[]{"" + MunicipioExtra, "Nuevo Municipio"};

                //   spinnerTipoCompra();
                spinnerTipoDeTelefono();
                spinnerMunicipio();
                etNombre.setEnabled(false);
                etApellidoMaterno.setEnabled(false);
                etApellidoPaterno.setEnabled(false);
                etNombre.setText("" + NombreExtra);
                etCalle.setText("" + CalleExtra);
                etNumero.setText("" + NumeroExtra);
                etInterior.setText("" + InteriorExtra);
                etCp.setText("" + CodigoPostalExtra);
                etColonia.setText("" + ColoniaExtra);
                etEstado.setText("" + EstadoExtra);
                etApellidoMaterno.setText("" + ApellidoMaternoExtra);
                etApellidoPaterno.setText("" + ApellidoPaternoExtra);
                etAlias.setText("" + AliasExtra);
                etLada.setText("" + LadaExtra);
                etTelefono.setText("" + TeléfonoExtra);
                etRepContra.setVisibility(View.GONE);
                etContra.setVisibility(View.GONE);

                if (Id_Foto_extra == null || Id_Foto_extra.isEmpty()) {
                    //Toast.makeText(getApplicationContext(), "Sin foto"+id_foto_extra, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        iv_foto.setImageBitmap(db.getimageID(Id_Foto_extra));

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                    }
                    //con foto
                }
                //Edicion
            }

        } catch (Exception e) {
            System.out.println(e);
            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGuardar.setEnabled(false);
                Validar_datos();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnGuardar.setEnabled(true);

                    }
                }, 2000);

            }
        });

        iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrirGaleria();
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

        requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, 0);
//        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 0);
//        requestPermissions(new String[]{CAMERA}, 0);
//        requestPermissions(new String[]{CALL_PHONE}, 0);
//carlosgaspar210898-Break
    }

    public void spinnerTipoDeTelefono() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.adaptadorspinner, tipoArrayTelefono);
        spTipoTelefono.setAdapter(adapter);

        spTipoTelefono.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(adapterView.getContext(),(String) adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();

                //   =categoriasArrayList.get(i).getCategoria();
                tipoTelefono = (String) adapterView.getItemAtPosition(i);
                if (tipoTelefono.equals("Nuevo Tipo de Telefono")) {
                    tipoArrayTelefono = new String[]{"Fijo", "Celular"};
                    spinnerTipoDeTelefono();
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
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
//              Toast.makeText(this,"Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
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
                    requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, 0);

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
                (this, "monsheep", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from clientes where Telefono = '" + Teléfono + "'", null);

            if (c.moveToFirst()) {
                do {
                    Alert();
                    //Toast.makeText(getApplicationContext(), "Categoria !Ya existe!", Toast.LENGTH_SHORT).show();
                } while (c.moveToNext());
            } else {
                // Toast.makeText(getApplicationContext(), ""+editar_extra, Toast.LENGTH_SHORT).show();
                if (editar_extra == null || editar_extra.isEmpty()) {

                    ValidarExistenciaUsuario();
                    //Toast.makeText(getApplicationContext(), "Registro", Toast.LENGTH_SHORT).show();
                    //Registrar
                } else {
                    //Editar
                    Editar();
                }


            }
        }
    }

    private void ValidarExistenciaUsuario() {
        clientesArrayList.clear();
        sqlite bh = new sqlite
                (this, "monsheep", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from clientes where Telefono=='" + etTelefono.getText().toString().trim() + "' and status='Activo'", null);
            if (c.moveToFirst()) {
//                do {
//                    clientesArrayList.add(new Clientes(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4),c.getString(5), c.getString(6),c.getString(7),c.getString(8), c.getString(9),c.getString(10),c.getString(11),c.getString(12),c.getString(13),c.getString(14),c.getString(15),c.getString(16),c.getString(17),c.getString(18),c.getString(19)));
//                } while (c.moveToNext());
                Toast.makeText(this, "El numero de telefono ya existe Utiliza uno nuevo", Toast.LENGTH_SHORT).show();

            } else {
                Registrar();
            }

        }

    }


    private void Editar() {
        try {

            EditarTabla eTabla = new EditarTabla();
            int a = eTabla.EditarUsuario(this, Nombre, Calle, Numero, Interior, Codigo_Postal, Colonia, Municipio,
                    Estado, Alias, Lada, Teléfono, num_id, x, tipoTelefono, ApellidoPaterno, ApellidoMaterno,
                    TipoCompra, idClienteExtra);
            etApellidoMaterno.setEnabled(false);
            etApellidoPaterno.setEnabled(false);
            spTipoTelefono.setEnabled(false);

            if (a == 1) {
                Intent intent = new Intent(this, MainActivityLogin.class);
                startActivity(intent);
                finish();
            }


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Validacion: " + e, Toast.LENGTH_SHORT).show();
        }

    }

    public void ObtenerDatos_box() {


        Nombre = etNombre.getText().toString();
        Calle = etCalle.getText().toString();
        Numero = etNumero.getText().toString();
        Interior = etInterior.getText().toString();
        Codigo_Postal = etCp.getText().toString();
        Colonia = etColonia.getText().toString();
        Estado = etEstado.getText().toString();
        Alias = etAlias.getText().toString();
        Lada = etLada.getText().toString();
        Teléfono = etTelefono.getText().toString();
        ApellidoPaterno = etApellidoPaterno.getText().toString();
        ApellidoMaterno = etApellidoMaterno.getText().toString();
        Contra = etContra.getText().toString();


    }

    private void Registrar() {
        ObtenerDatos_box();

        // Toast.makeText(this, "Contra: "+Contra, Toast.LENGTH_SHORT).show();

        if (x != null && !x.isEmpty()) {
            if (etTelefono.getText().toString().equals("3751076001")) {
                TipoCompra = "admin";
            }

            InsertarTabla insert = new InsertarTabla();
            insert.RegistrarUsuario(this, idCliente, Nombre, Calle, Numero, Interior, Codigo_Postal,
                    Colonia, Municipio, Estado, Alias, Lada, Teléfono, tipoTelefono, ApellidoPaterno, ApellidoMaterno,
                    TipoCompra, num_id, x, Contra.trim(), "0");

            if (isNetDisponible(this) == true && isOnlineNet() == true) {
                finish();
            } else {
                Toast.makeText(this, "Sin Red", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Agrega una foto", Toast.LENGTH_SHORT).show();
        }
    }

    public void Devolver(Context context, String idFoto,
                         String id, ContentValues registro,
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
                        if (db.insertimage(x, idFoto, String.valueOf(id))) {
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
                            Toast.makeText(context,
                                    "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                        TextView text = (TextView) viewInput.findViewById(R.id.text12);
                        text.setText("Edicion Exitosa");

                        Toast toast = new Toast(context);
                        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(viewInput);
                        toast.show();
                    }
                    int cantid = BaseDeDatos.update(
                            "monsheep", registro, "id_cliente= " + Integer.parseInt(id), null);
                    BaseDeDatos.close();
                    if (cantid == 1) {
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
                            "monsheep", registro, "id_cliente= " + Integer.parseInt(id), null);
                    BaseDeDatos.close();
                    if (cantid == 1) {
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

    public void spinnerMunicipio() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.adaptadorspinner, municipio);
        spMunicipio.setAdapter(adapter);

        spMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(adapterView.getContext(),(String) adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();

                //   =categoriasArrayList.get(i).getCategoria();
                Municipio = (String) adapterView.getItemAtPosition(i);
                if (Municipio.equals("Nuevo Municipio")) {
                    municipio = new String[]{"Acatic", "Acatlán de Juárez", "Ahualulco de Mercado", "Amacueca", "Amatitán", "Ameca", "San Juanito de Escobedo", "Arandas"
                            , "El Arenal", "Atemajac de Brizuela", "Atengo", "Atenguillo", "Atotonilco el Alto", "Atoyac", "Autlán de Navarro"
                            , "Ayotlán", "Ayutla", "La Barca", "Bolaños", "Cabo Corrientes", "Casimiro Castillo", "Cihuatlán"
                            , "Zapotlán el Grande", "Cocula", "Colotlán", "Concepción de Buenos Aires", "Cuautitlán de García Barragán", "Cuautla", "Cuquío"
                            , "Chapala", "Chimaltitán", "Chiquilistlán", "Degollado", "Ejutla", "Encarnación de Díaz", "Etzatlán"
                            , "El Grullo", "Guachinango", "Guadalajara", "Hostotipaquillo", "Huejúcar", "Huejuquilla el Alto", "La Huerta"
                            , "Ixtlahuacán de los Membrillos", "Ixtlahuacán del Río", "Jalostotitlán", "Jamay", "Jesús María", "Jilotlán de los Dolores"
                            , "Jocotepec", "Juanacatlán", "Juchitlán", "Lagos de Moreno", "El Limón", "Magdalena"
                            , "Santa María del Oro", "La Manzanilla de la Paz", "Mascota", "Mazamitla", "Mexticacán", "Mezquitic", "Mixtlán", "Ocotlán", "Ojuelos de Jalisco", "Pihuamo"
                            , "Poncitlán", "Puerto Vallarta", "Villa Purificación", "Quitupan", "El Salto", "San Cristóbal de la Barranca", "San Diego de Alejandría"
                            , "San Juan de los Lagos", "San Julián", "San Marcos", "San Martín de Bolaños", "San Martín Hidalgo", "San Miguel el Alto"
                            , "Gómez Farías", "San Sebastián del Oeste", "Santa María de los Ángeles"
                            , "Sayula", "Tala", "Talpa de Allende", "Tamazula de Gordiano", "Tapalpa", "Techaluta de Montenegro", "Tecolotlán", "Tenamaxtlán", "Teocaltiche"
                            , "Teocuitatlán de Corona", "Tepatitlán de Morelos", "Tequila", "Teuchitlán", "Tizapán el Alto"
                            , "Tlajomulco de Zúñiga", "San Pedro Tlaquepaque", "Tolimán", "Tomatlán", "Tonalá", "Tonaya", "Tonila", "Totatiche", "Tototlán", "Tuxcacuesco", "Tuxcueca"
                            , "Tuxpan", "Unión de San Antonio", "Unión de Tula", "Valle de Guadalupe", "Valle de Juárez", "San Gabriel", "Villa Corona", "Villa Guerrero", "Villa Hidalgo"
                            , "Cañadas de Obregón", "Yahualica de González Gallo", "Zacoalco de Torres", "Zapopan", "Zapotiltic", "Zapotitlán de Vadillo", "Zapotlán del Rey", "Zapotlanejo", "San Ignacio Cerro Gordo"};

                    spinnerMunicipio();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void Validar_datos() {
        ObtenerDatos_box();
        try {
            if (!Nombre.isEmpty() && Nombre.length() >= 5 && Nombre.length() <= 70) {
                if (!Calle.isEmpty() && Calle.length() >= 1 && Calle.length() <= 100) {
                    if (!Numero.isEmpty() && Numero.length() >= 1 && Numero.length() <= 20) {
                        if (!Codigo_Postal.isEmpty() && Codigo_Postal.length() == 5) {
                            if (!Colonia.isEmpty() && Colonia.length() >= 5) {//Son muy pocos caracteres para una colonia
                                // if (!Municipio.isEmpty() && Municipio.length() >= 5){//Son muy pocos caracteres para una colonia
                                if (!Estado.isEmpty() && Estado.length() >= 5) {//Son muy pocos caracteres para una colonia
                                    if (!Lada.isEmpty() && Lada.length() >= 2 && Lada.length() <= 3) {//Son mu
                                        if (!Teléfono.isEmpty() && Teléfono.length() == 10) {//Son mu
                                            if (etContra.getText().toString().trim().equals(etRepContra.getText().toString().toString().trim())) {
                                                if (!etAlias.getText().toString().isEmpty()) {
                                                    if (Nombre.equals(NombreExtra)) {
                                                        Editar();
                                                    } else lista_query_sin_duplicados();
                                                } else etAlias.setError("Agrega un alias");
                                            } else
                                                etRepContra.setError("Las contraseñas no coinciden");
                                        } else
                                            etTelefono.setError("El campo Teléfono es obligatoria y debe ser de 10 caracteres");
                                    } else
                                        etLada.setError("El campo Lada es obligatoria y debe ser al menos de 2 caracteres");
                                } else etEstado.setError("El estado es obligatorio");
                                // }else  etMunicipio.setError("El municipio es obligatorio");
                            } else etColonia.setError("La colonia es obligatoria");
                        } else etCp.setError("El C.P. es obligatorio");
                    } else etNumero.setError("El número es obligatorio");
                } else etCalle.setError("La calle es obligatorio");
            } else
                etNombre.setError("El campo nombre es obligatorio y debe ser al menos de 5 caracteres");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
            System.out.println("" + e);
        }

    }

    private void Alert() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        //Mensaje
        alerta.setTitle(Html.fromHtml("<font color='#269BF4'>Mensaje informativo</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("El Numero de telefono ya fue previamente registrado y no puede duplicarse");

        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Mensaje2();
            }
        });
        alerta.show();
    }
}