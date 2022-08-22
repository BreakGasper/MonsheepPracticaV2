package com.example.monsheeppractica.Activitys;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monsheeppractica.Activitys.MainActivityLogin;
import com.example.monsheeppractica.Activitys.MainActivityNotificacionesNegocio;
import com.example.monsheeppractica.Activitys.MainActivityTicket;
import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;
import com.example.monsheeppractica.sqlite.registros.InsertarTabla;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

public class MainActivityAjustes extends AppCompatActivity {

    SharedPreferences preferences;
    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio;
    public static ArrayList<Clientes> clientesArrayList = new ArrayList<>();
    public static ArrayList<Negocio> negocioArrayList = new ArrayList<>();
    String Nombre, Calle, Numero, Interior, Codigo_Postal, Colonia,
            Estado, Alias, Lada, Teléfono, TipoCompra = "basico", ApellidoPaterno, ApellidoMaterno, Contra, descrip, correo;
    TextView tvEdit;
    ImageView ivTienda, ivUser;
    EditText etNombre, etCalle, etNumero, etInterior,
            etCp, etColonia, etEstado,
            etAlias, etLada, etTelefono, etApellidoPaterno,
            etApellidoMaterno, etContra, etRepContra, etDescripcion, etCorreo, etCorreoUser;
    TextInputLayout tilAMaterno, tilAPaterno, tilAlias, tilDescripcion, tilCorreo;
    Spinner spTipoTelefono, spMunicipio;
    Button btnGuardar;
    ImageView iv_foto;
    String num_id = "", x, edit = "user";
    DatabaseHandler db;
    String tipoTelefono = "Fijo", Municipio = "San Martín Hidalgo";
    String[] tipoArrayTelefono = {"Fijo", "Celular"};
    String[] tipoArrayCompra = {"Menudeo", "Mayoreo"};
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
    private Uri mCropImageUri;
    String pass;
    EditText[] Informacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ajustes);
        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");
        pass = preferences.getString("pass", "et_pass.getText().toString()");

        db = new DatabaseHandler(this);
        tvEdit = findViewById(R.id.tvEdit);
        ivTienda = findViewById(R.id.iVTiendaSet);
        ivUser = findViewById(R.id.iVuserSet);
        etNombre = (EditText) findViewById(R.id.etNombreEdit);
        etCalle = (EditText) findViewById(R.id.etCalleClienteEdit);
        etNumero = (EditText) findViewById(R.id.etNumeroClienteEdit);
        etInterior = (EditText) findViewById(R.id.etInteriorEdit);
        etCp = (EditText) findViewById(R.id.etCpEdit);
        etColonia = (EditText) findViewById(R.id.etColoniaEdit);
        etEstado = (EditText) findViewById(R.id.etEstadoEdit);
        spMunicipio = (Spinner) findViewById(R.id.spTipoMunicipioEdit);
        etAlias = (EditText) findViewById(R.id.etAlias);
        etLada = (EditText) findViewById(R.id.etLadaEdit);
        etTelefono = (EditText) findViewById(R.id.etTelefonoEdit);
        etApellidoPaterno = (EditText) findViewById(R.id.etApellidoPaternoEdit);
        etApellidoMaterno = (EditText) findViewById(R.id.etApellidoMaternoEdit);
        etContra = (EditText) findViewById(R.id.etContra);
        etRepContra = (EditText) findViewById(R.id.etRepContra);
        etDescripcion = (EditText) findViewById(R.id.etDescripcionEdit);
        btnGuardar = (Button) findViewById(R.id.btnGuardarEdit);
        spTipoTelefono = (Spinner) findViewById(R.id.spTipoTelefonoEdit);
        etCorreo = findViewById(R.id.etCorreoEdit);
        etCorreoUser = findViewById(R.id.etCorreo);
        iv_foto = (ImageView) findViewById(R.id.ivFotoEdit);
        tilAlias = findViewById(R.id.tILA);
        tilCorreo = findViewById(R.id.tILCorreo);
        tilAMaterno = findViewById(R.id.tILAM);
        tilAPaterno = findViewById(R.id.tILAP);
        tilDescripcion = findViewById(R.id.tILD);


        ivTienda.setVisibility(View.GONE);
        etContra.setVisibility(View.GONE);
        etRepContra.setVisibility(View.GONE);

        if (!idNegocio.equals("0")) {
            ivTienda.setVisibility(View.VISIBLE);
            Picasso.get().load(db.getImagen(""+idNegocio+".jpg")).memoryPolicy(MemoryPolicy.NO_CACHE).into(ivTienda);

//            ivTienda.setImageBitmap(db.getimageID("" + idNegocio));
        }//else ivTienda.setImageDrawable(getResources().getDrawable(R.drawable.mcolor));
        Picasso.get().load(db.getImagen(""+idUser+".jpg")).memoryPolicy(MemoryPolicy.NO_CACHE).into(ivUser);

//        ivUser.setImageBitmap(db.getimageID("" + idUser));
        //Toast.makeText(this, ""+idNegocio, Toast.LENGTH_SHORT).show();
        ivTienda.setOnClickListener(view -> {
            if (!idNegocio.equals("0")){
                LlenarDatosNegocio();
            }else Toast.makeText(this, "Registra tu negocio", Toast.LENGTH_SHORT).show();

        });

        ivUser.setOnClickListener(view -> {

            LlenarDatosUSer();

        });
        tvEdit.setOnClickListener(view -> {
            iv_foto.setEnabled(false);
            tvEdit.setEnabled(false);
            onSelectImageClick(view);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    iv_foto.setEnabled(true);
                    tvEdit.setEnabled(true);

                }
            }, 2000);
        });
        iv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvEdit.setEnabled(false);
                iv_foto.setEnabled(false);
                onSelectImageClick(view);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iv_foto.setEnabled(true);
                        tvEdit.setEnabled(true);
                    }
                }, 2000);

            }
        });

        btnGuardar.setOnClickListener(view -> {
            Ciclo();

        });
        LlenarDatosUSer();
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
        descrip = etDescripcion.getText().toString();
        correo = etCorreo.getText().toString();

    }

    private void AlertaValidar() {
        ObtenerDatos_box();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewInput = inflater.inflate(R.layout.alertpassword, null, false);

        EditText etPass2 = viewInput.findViewById(R.id.etconfirmpassalert);

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setView(viewInput);
        //Mensaje
        alerta.setTitle(Html.fromHtml("<font color='#FE4500'>Editar mi cuenta"));
        alerta.setMessage(Html.fromHtml("<H6><font color='#0060D2'>" + "¿Deseas guardar los cambios?" + "</font></H6>"));
        alerta.setNeutralButton("Si", (dialogInterface, i) -> {

            if (!etPass2.getText().toString().isEmpty()) {

                if (pass.trim().equals(etPass2.getText().toString().trim())) {
                    EditarTabla editarTabla = new EditarTabla();
                    if (edit.equals("user")) {
                        editarTabla.EditarPerfil(this, Integer.parseInt(idUser), Nombre, Calle, Numero, Interior, Codigo_Postal, Colonia, Municipio, Estado, Alias, Lada, Teléfono, tipoTelefono, ApellidoPaterno, ApellidoMaterno, TipoCompra, num_id, x,etCorreoUser.getText().toString());

                    } else if (edit.equals("negocio")) {
                        editarTabla.EditarPerfilNegocio(this, Integer.parseInt(idNegocio), Nombre, Calle, Numero, Interior, Codigo_Postal, Colonia, Municipio, Estado, Alias, Lada, Teléfono, tipoTelefono, ApellidoPaterno, ApellidoMaterno, TipoCompra, num_id, x, descrip, correo);

                    }
                    Intent intent = new Intent(this, MainActivityLogin.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else Toast.makeText(this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
            }else etPass2.setError("No debe estar vacio");
        });

        alerta.setNegativeButton("No", (dialogInterface, i) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        alerta.setIcon(R.drawable.editar);
        alerta.show();
    }

    void LlenarDatosUSer() {
        Informacion = new EditText[]{
                etNombre, etCalle, etNumero, etInterior, etCp, etColonia, etEstado
                , etAlias, etLada, etTelefono, etApellidoPaterno, etApellidoMaterno,etCorreoUser};
        edit = "user";
        clientesArrayList.clear();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        consultarTabla.ClientesConsulta(clientesArrayList, "user", Integer.parseInt(idUser), "");
        Clientes c = clientesArrayList.get(0);
        etNombre.setText("" + c.getNombre());
        etCalle.setText("" + c.getCalle());
        etNumero.setText("" + c.getNumero());
        etInterior.setText("" + c.getInterior());
        etCp.setText("" + c.getCodigo_Postal());
        etColonia.setText("" + c.getColonia());
        etEstado.setText("" + c.getEstado());
        etApellidoMaterno.setText("" + c.getApellidoMaterno());
        etApellidoPaterno.setText("" + c.getApellidoPaterno());
        etAlias.setText("" + c.getAlias());
        etLada.setText("" + c.getLada());
        etTelefono.setText("" + c.getTeléfono());
        etCorreoUser.setText(""+c.getCorreo());
        etTelefono.setEnabled(false);
        etLada.setEnabled(false);
        tilDescripcion.setVisibility(View.GONE);
        tilCorreo.setVisibility(View.GONE);
        tilAMaterno.setVisibility(View.VISIBLE);
        tilAPaterno.setVisibility(View.VISIBLE);
        tilAlias.setVisibility(View.VISIBLE);
        Picasso.get().load(db.getImagen(""+idUser+".jpg")).memoryPolicy(MemoryPolicy.NO_CACHE).into(iv_foto);

//        iv_foto.setImageBitmap(db.getimageID("" + idUser));
        ivUser.setBackground(getResources().getDrawable(R.drawable.degradado));
        ivTienda.setFocusable(true);
        ivTienda.setBackground(getResources().getDrawable(R.drawable.marcolistview));

        tipoArrayTelefono = new String[]{"" + c.getTipoTelefono(), "Nuevo Tipo de Telefono"};
        tipoArrayCompra = new String[]{"" + c.getTipoCompra(), "Nueva Tipo Compra"};
        municipio = new String[]{"" + c.getMunicipio(), "Nuevo Municipio"};

        spinnerTipoDeTelefono();
        spinnerMunicipio();

    }


    boolean esVacio(EditText editText, String msg) {
        Boolean bandel = true;

        if (editText.getText().toString().isEmpty()) {

            editText.setError(msg);
            bandel = false;
        }

        return bandel;
    }

    void Ciclo() {
        try {
            // Toast.makeText(this, ""+Informacion.length, Toast.LENGTH_SHORT).show();

            int cont = 0;
            for (int i = 0; i < Informacion.length; i++) {

                if (esVacio(Informacion[i], "LLena este campo") == true) {
                    cont++;
                    if (Informacion.length == cont) {
                        // Toast.makeText(this, "Completo", Toast.LENGTH_SHORT).show();
                        AlertaValidar();
                    }
                    ///Toast.makeText(this, ""+Informacion[i], Toast.LENGTH_SHORT).show();

                }
            }
        } catch (Exception e) {
            System.out.println(e);
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }
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

    void LlenarDatosNegocio() {

        Informacion = new EditText[]{
                etNombre, etCalle, etNumero, etInterior, etCp, etColonia, etEstado
                , etLada, etTelefono, etDescripcion, etCorreo};

        edit = "negocio";
        negocioArrayList.clear();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        consultarTabla.NegocioConsulta(negocioArrayList, "Tienda", idNegocio, "", "");
        Negocio c = negocioArrayList.get(0);
        etNombre.setText("" + c.getNombre());
        etCalle.setText("" + c.getCalle());
        etNumero.setText("" + c.getNumero());
        etInterior.setText("" + c.getInterior());
        etCp.setText("" + c.getCodigo_Postal());
        etColonia.setText("" + c.getColonia());
        etEstado.setText("" + c.getEstado());
        etLada.setText("" + c.getLada());
        etTelefono.setText("" + c.getTeléfono());
        etDescripcion.setText("" + c.getDescripcion());
        etCorreo.setText("" + c.getCorreo());
        tilAMaterno.setVisibility(View.GONE);
        tilAPaterno.setVisibility(View.GONE);
        tilAlias.setVisibility(View.GONE);
        tilCorreo.setVisibility(View.VISIBLE);
        tilDescripcion.setVisibility(View.VISIBLE);
        Picasso.get().load(db.getImagen(""+idNegocio+".jpg")).memoryPolicy(MemoryPolicy.NO_CACHE).into(iv_foto);

//        iv_foto.setImageBitmap(db.getimageID("" + idNegocio));
        ivTienda.setBackground(getResources().getDrawable(R.drawable.degradado));
        ivUser.setFocusable(true);
        ivUser.setBackground(getResources().getDrawable(R.drawable.marcolistview));

        tipoArrayTelefono = new String[]{"" + c.getTipoTelefono(), "Nuevo Tipo de Telefono"};
        tipoArrayCompra = new String[]{"" + c.getTipoCompra(), "Nueva Tipo Compra"};
        municipio = new String[]{"" + c.getMunicipio(), "Nuevo Municipio"};

        spinnerTipoDeTelefono();
        spinnerMunicipio();

    }

    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);

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
                    num_id = num_id + (int) (Math.random() * 500);

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

}