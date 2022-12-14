package com.example.monsheeppractica.Activitys;

import static com.example.monsheeppractica.mytools.Network.isNetDisponible;
import static com.example.monsheeppractica.mytools.Network.isOnlineNet;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.mytools.NumberTextWatcher;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;
import com.example.monsheeppractica.sqlite.registros.InsertarTabla;
import com.example.monsheeppractica.sqlite.sqlite;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivityRegistroNegocio extends AppCompatActivity {
    EditText etNombre, etCalle, etNumero, etInterior,
            etCp, etColonia, etEstado,
            etLada, etTelefono, etContra, etRepContra, etDescripcion, etCorreoNegocio;
    CheckBox cbServicio;
    Button btnGuardar;
    String[] tipoArrayTelefono = {"Fijo", "Celular"};
    String[] tipoArrayCompra = {"Menudeo", "Mayoreo"};
    Spinner spTipoTelefono, spMunicipio;
    DatabaseHandler db;
    ImageView iv_foto;
    String idClienteExtra, editar_extra, tipoTelefonoExtra, NombreExtra, CalleExtra, NumeroExtra, InteriorExtra, CodigoPostalExtra, ColoniaExtra, MunicipioExtra, EstadoExtra, LadaExtra, Tel??fonoExtra, TipoCompraExtra, Id_Foto_extra;

    String Nombre, Calle, Numero, Interior, Codigo_Postal, Colonia, Municipio = "San Mart??n Hidalgo",
            Estado, Alias, Lada, Tel??fono, TipoCompra = "basico", ApellidoPaterno, ApellidoMaterno, Contra,servicioDomicilio="no";

    String tipoTelefono = "Fijo";
    int idCliente;
    private Uri mCropImageUri;
    String num_id = "", x;
    String[] municipio = {"San Mart??n Hidalgo", "Cocula", "Ameca", "Juchitl??n", "Acatic", "Acatl??n de Ju??rez", "Ahualulco de Mercado", "Amacueca", "Amatit??n", "San Juanito de Escobedo", "Arandas"
            , "El Arenal", "Atemajac de Brizuela", "Atengo", "Atenguillo", "Atotonilco el Alto", "Atoyac", "Autl??n de Navarro"
            , "Ayotl??n", "Ayutla", "La Barca", "Bola??os", "Cabo Corrientes", "Casimiro Castillo", "Cihuatl??n"
            , "Zapotl??n el Grande", "Colotl??n", "Concepci??n de Buenos Aires", "Cuautitl??n de Garc??a Barrag??n", "Cuautla", "Cuqu??o"
            , "Chapala", "Chimaltit??n", "Chiquilistl??n", "Degollado", "Ejutla", "Encarnaci??n de D??az", "Etzatl??n"
            , "El Grullo", "Guachinango", "Guadalajara", "Hostotipaquillo", "Huej??car", "Huejuquilla el Alto", "La Huerta"
            , "Ixtlahuac??n de los Membrillos", "Ixtlahuac??n del R??o", "Jalostotitl??n", "Jamay", "Jes??s Mar??a", "Jilotl??n de los Dolores"
            , "Jocotepec", "Juanacatl??n", "Lagos de Moreno", "El Lim??n", "Magdalena"
            , "Santa Mar??a del Oro", "La Manzanilla de la Paz", "Mascota", "Mazamitla", "Mexticac??n", "Mezquitic", "Mixtl??n", "Ocotl??n", "Ojuelos de Jalisco", "Pihuamo"
            , "Poncitl??n", "Puerto Vallarta", "Villa Purificaci??n", "Quitupan", "El Salto", "San Crist??bal de la Barranca", "San Diego de Alejandr??a"
            , "San Juan de los Lagos", "San Juli??n", "San Marcos", "San Mart??n de Bola??os", "San Miguel el Alto"
            , "G??mez Far??as", "San Sebasti??n del Oeste", "Santa Mar??a de los ??ngeles"
            , "Sayula", "Tala", "Talpa de Allende", "Tamazula de Gordiano", "Tapalpa", "Techaluta de Montenegro", "Tecolotl??n", "Tenamaxtl??n", "Teocaltiche"
            , "Teocuitatl??n de Corona", "Tepatitl??n de Morelos", "Tequila", "Teuchitl??n", "Tizap??n el Alto"
            , "Tlajomulco de Z????iga", "San Pedro Tlaquepaque", "Tolim??n", "Tomatl??n", "Tonal??", "Tonaya", "Tonila", "Totatiche", "Tototl??n", "Tuxcacuesco", "Tuxcueca"
            , "Tuxpan", "Uni??n de San Antonio", "Uni??n de Tula", "Valle de Guadalupe", "Valle de Ju??rez", "San Gabriel", "Villa Corona", "Villa Guerrero", "Villa Hidalgo"
            , "Ca??adas de Obreg??n", "Yahualica de Gonz??lez Gallo", "Zacoalco de Torres", "Zapopan", "Zapotiltic", "Zapotitl??n de Vadillo", "Zapotl??n del Rey", "Zapotlanejo", "San Ignacio Cerro Gordo"};


    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio, idFotoNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro_negocio);
        try {
            getSupportActionBar().hide();
            getActionBar().hide();
        } catch (Exception e) {

            //  Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
        }
        SharedPreferences preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");
        idFotoNegocio = preferences.getString("idFotoNegocio", "et_pass.getText().toString()");

        try {

            etNombre = (EditText) findViewById(R.id.etNombreNegocio);
            etCalle = (EditText) findViewById(R.id.etCalleNegocio);
            etNumero = (EditText) findViewById(R.id.etNumeroNegocio);
            etInterior = (EditText) findViewById(R.id.etInteriorNegocio);
            etCp = (EditText) findViewById(R.id.etCpNegocio);
            etColonia = (EditText) findViewById(R.id.etColoniaNegocio);
            etEstado = (EditText) findViewById(R.id.etEstadoNegocio);
            etDescripcion = (EditText) findViewById(R.id.etDescripcionNegocio);
            etCorreoNegocio = (EditText) findViewById(R.id.etCorreoEdit);
            etLada = (EditText) findViewById(R.id.etLadaNegocio);
            etTelefono = (EditText) findViewById(R.id.etTelefonoNegocio);
            etContra = (EditText) findViewById(R.id.etContraNegocio);
            etRepContra = (EditText) findViewById(R.id.etRepContraNegocio);
            btnGuardar = (Button) findViewById(R.id.btnGuardarNegocio);
            spMunicipio = (Spinner) findViewById(R.id.spMunicipioNegocio);
            spTipoTelefono = (Spinner) findViewById(R.id.spTipoTelefonoNegocio);
            iv_foto = (ImageView) findViewById(R.id.ivFotoNegocio);
            cbServicio =findViewById(R.id.cbServices);
            //Referencia a la tabla imagenes categorias
            db = new DatabaseHandler(this);


            etCp.addTextChangedListener(new NumberTextWatcher(etCp));
            etLada.addTextChangedListener(new NumberTextWatcher(etLada));
            etTelefono.addTextChangedListener(new NumberTextWatcher(etTelefono));

            if (editar_extra == null) {
                //Registrar
                spinnerMunicipio();
                spinnerTipoDeTelefono();
                // spinnerTipoCompra();
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
                    etLada.setEnabled(false);
                    etTelefono.setEnabled(false);
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

                etNombre.setText("" + NombreExtra);
                etCalle.setText("" + CalleExtra);
                etNumero.setText("" + NumeroExtra);
                etInterior.setText("" + InteriorExtra);
                etCp.setText("" + CodigoPostalExtra);
                etColonia.setText("" + ColoniaExtra);
                etEstado.setText("" + EstadoExtra);
                etLada.setText("" + LadaExtra);
                etTelefono.setText("" + Tel??fonoExtra);
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
                    x = "" + result.getUri();//getPath(result.getUri());
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
            Cursor c = db.rawQuery("select * from clientes where Nombre LIKE '" + Nombre + "'", null);

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

            EditarTabla eTabla = new EditarTabla();
            int a = eTabla.EditarUsuario(this, Nombre, Calle, Numero, Interior, Codigo_Postal, Colonia, Municipio,
                    Estado, Alias, Lada, Tel??fono, num_id, x, tipoTelefono, ApellidoPaterno, ApellidoMaterno,
                    TipoCompra, idClienteExtra);
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
        Lada = etLada.getText().toString();
        Tel??fono = etTelefono.getText().toString();
        Contra = etContra.getText().toString();


    }

    private void Registrar() {
        ObtenerDatos_box();

        // Toast.makeText(this, "Contra: "+Contra, Toast.LENGTH_SHORT).show();
        int id = (int) (Math.random() * 10000 + 1 * 3 + 15);
        if (x != null && !x.isEmpty()) {

            if (cbServicio.isChecked() == true){
                servicioDomicilio ="si";
            }

            InsertarTabla insert = new InsertarTabla();
            int idEditNego = insert.RegistrarNegocio(this, id, Integer.parseInt(idUser), Nombre, Calle, Numero, Interior, Codigo_Postal,
                    Colonia, Municipio, Estado, Lada, Tel??fono, tipoTelefono,
                    TipoCompra, num_id, x, Contra, etCorreoNegocio.getText().toString().trim(), etDescripcion.getText().toString(), idFotoNegocio,servicioDomicilio);

            EditarTabla editarTabla = new EditarTabla();
            editarTabla.EditarUser(this, Integer.parseInt(idUser), String.valueOf(idEditNego), TipoCompra);

            if (isNetDisponible(this) == true && isOnlineNet() == true) {
                Intent intent = new Intent(this, MainActivityLogin.class);
                startActivity(intent);
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
        alerta.setMessage("Existe una imagen asociada, ??desea reemplazarla?");

        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    registro.put("Id_Foto", idFoto.trim());
                    //Registro de imagen en la base de datos
                    if (!idFoto.isEmpty()) {
                        if (db.insertimage(x, idFoto, String.valueOf(id))) {
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
                            "clientes", registro, "id_cliente= " + Integer.parseInt(id), null);
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
                            "clientes", registro, "id_cliente= " + Integer.parseInt(id), null);
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
                    municipio = new String[]{"Acatic", "Acatl??n de Ju??rez", "Ahualulco de Mercado", "Amacueca", "Amatit??n", "Ameca", "San Juanito de Escobedo", "Arandas"
                            , "El Arenal", "Atemajac de Brizuela", "Atengo", "Atenguillo", "Atotonilco el Alto", "Atoyac", "Autl??n de Navarro"
                            , "Ayotl??n", "Ayutla", "La Barca", "Bola??os", "Cabo Corrientes", "Casimiro Castillo", "Cihuatl??n"
                            , "Zapotl??n el Grande", "Cocula", "Colotl??n", "Concepci??n de Buenos Aires", "Cuautitl??n de Garc??a Barrag??n", "Cuautla", "Cuqu??o"
                            , "Chapala", "Chimaltit??n", "Chiquilistl??n", "Degollado", "Ejutla", "Encarnaci??n de D??az", "Etzatl??n"
                            , "El Grullo", "Guachinango", "Guadalajara", "Hostotipaquillo", "Huej??car", "Huejuquilla el Alto", "La Huerta"
                            , "Ixtlahuac??n de los Membrillos", "Ixtlahuac??n del R??o", "Jalostotitl??n", "Jamay", "Jes??s Mar??a", "Jilotl??n de los Dolores"
                            , "Jocotepec", "Juanacatl??n", "Juchitl??n", "Lagos de Moreno", "El Lim??n", "Magdalena"
                            , "Santa Mar??a del Oro", "La Manzanilla de la Paz", "Mascota", "Mazamitla", "Mexticac??n", "Mezquitic", "Mixtl??n", "Ocotl??n", "Ojuelos de Jalisco", "Pihuamo"
                            , "Poncitl??n", "Puerto Vallarta", "Villa Purificaci??n", "Quitupan", "El Salto", "San Crist??bal de la Barranca", "San Diego de Alejandr??a"
                            , "San Juan de los Lagos", "San Juli??n", "San Marcos", "San Mart??n de Bola??os", "San Mart??n Hidalgo", "San Miguel el Alto"
                            , "G??mez Far??as", "San Sebasti??n del Oeste", "Santa Mar??a de los ??ngeles"
                            , "Sayula", "Tala", "Talpa de Allende", "Tamazula de Gordiano", "Tapalpa", "Techaluta de Montenegro", "Tecolotl??n", "Tenamaxtl??n", "Teocaltiche"
                            , "Teocuitatl??n de Corona", "Tepatitl??n de Morelos", "Tequila", "Teuchitl??n", "Tizap??n el Alto"
                            , "Tlajomulco de Z????iga", "San Pedro Tlaquepaque", "Tolim??n", "Tomatl??n", "Tonal??", "Tonaya", "Tonila", "Totatiche", "Tototl??n", "Tuxcacuesco", "Tuxcueca"
                            , "Tuxpan", "Uni??n de San Antonio", "Uni??n de Tula", "Valle de Guadalupe", "Valle de Ju??rez", "San Gabriel", "Villa Corona", "Villa Guerrero", "Villa Hidalgo"
                            , "Ca??adas de Obreg??n", "Yahualica de Gonz??lez Gallo", "Zacoalco de Torres", "Zapopan", "Zapotiltic", "Zapotitl??n de Vadillo", "Zapotl??n del Rey", "Zapotlanejo", "San Ignacio Cerro Gordo"};

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
                                        if (!Tel??fono.isEmpty() && Tel??fono.length() == 10) {//Son mu
                                            if (etContra.getText().toString().trim().equals(etRepContra.getText().toString().toString().trim())) {

                                                if (!etDescripcion.getText().toString().isEmpty()) {
                                                    if (!etCorreoNegocio.getText().toString().isEmpty()) {
                                                        if (Nombre.equals(NombreExtra)) {
                                                            Editar();
                                                        } else lista_query_sin_duplicados();
                                                    } else
                                                        etCorreoNegocio.setError("Agrega un Correo");
                                                } else
                                                    etDescripcion.setError("Agrega una descripcion");

                                            } else
                                                etRepContra.setError("Las contrase??as no coinciden");
                                        } else
                                            etTelefono.setError("El campo Tel??fono es obligatoria y debe ser de 10 caracteres");
                                    } else
                                        etLada.setError("El campo Lada es obligatoria y debe ser al menos de 2 caracteres");
                                } else etEstado.setError("El estado es obligatorio");
                                // }else  etMunicipio.setError("El municipio es obligatorio");
                            } else etColonia.setError("La colonia es obligatoria");
                        } else etCp.setError("El C.P. es obligatorio");
                    } else etNumero.setError("El n??mero es obligatorio");
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
        alerta.setMessage("El Nombre del negocio ya fue previamente registrado y no puede duplicarse");

        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Mensaje2();
            }
        });
        alerta.show();
    }

}