package com.example.monsheeppractica.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.GetterAndSetter.Domicilios;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;
import com.example.monsheeppractica.sqlite.registros.InsertarTabla;

import java.util.ArrayList;

public class MainActivityAPagar extends AppCompatActivity {
    SharedPreferences preferences;
    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio;
    ArrayList<Clientes> Array = new ArrayList<>();
    ArrayList<Domicilios> domiciliosArray = new ArrayList<>();
    EditText etNombreCompleto, etDomicilio, etNumero, etColonia, etVecindario, etCelular, etCodigoPostal;
    EditText[] Informacion;
    Spinner spMunicipio;
    CheckBox cBConfirmoDatos;
    String Municipio = "San Martín Hidalgo",vecindario;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_apagar);
        try {
            getSupportActionBar().hide();
            getActionBar().hide();
        } catch (Exception e) {   }
        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");
        vecindario = preferences.getString("vecindario", "");

        etNombreCompleto = findViewById(R.id.etNombreContactoPedido);
        etDomicilio = findViewById(R.id.etDomicilioPedido);
        etCodigoPostal = findViewById(R.id.etCodigoPostalPedido);
        etNumero = findViewById(R.id.etNumPedido);
        etColonia = findViewById(R.id.etColoniaPedido);
        etCelular = findViewById(R.id.etContactoPedido);
        etVecindario = findViewById(R.id.etVecindarioPedido);
        spMunicipio = findViewById(R.id.spTipoMunicipioCliente2);
        cBConfirmoDatos = findViewById(R.id.cbConfirmoDatos);

        Informacion = new EditText[]{
                etNombreCompleto, etDomicilio, etNumero, etColonia, etVecindario, etCelular, etCodigoPostal};

        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        consultarTabla.ClientesConsulta(Array, "user", Integer.parseInt(idUser), idUser);
        Clientes user = Array.get(0);

        etNombreCompleto.setText(user.getNombre() + " " + user.getApellidoPaterno() + " " + user.getApellidoMaterno());
        etCelular.setText(user.getLada() + " " + user.getTeléfono());
        etColonia.setText(user.getColonia());
        etDomicilio.setText(user.getCalle());
        etNumero.setText(user.getNumero());
        etCodigoPostal.setText(user.getCodigo_Postal());
        etVecindario.setText(vecindario);
        municipio = new String[]{"" + user.getMunicipio(), "Otro Municipio"};
        spinnerMunicipio();
    }

    void Ciclo() {
        try {
            // Toast.makeText(this, ""+Informacion.length, Toast.LENGTH_SHORT).show();

            int cont = 0;
            for (int i = 0; i < Informacion.length; i++) {

                if (esVacio(Informacion[i], "LLena Todos los campos") == true) {
                    cont++;
                    if (Informacion.length == cont) {
                        if (cBConfirmoDatos.isChecked() == true) {
                            ConsultarTabla consultarTabla = new ConsultarTabla(this);
                            consultarTabla.DomicilioConsulta(domiciliosArray, "user", Integer.parseInt(idUser), idUser);
                            //Toast.makeText(this, "" + domiciliosArray.size(), Toast.LENGTH_SHORT).show();

                            if (domiciliosArray.size() >= 1) {
                                EditarTabla editarTabla = new EditarTabla();
                                editarTabla.EditarDomicilio(this, idUser, etNombreCompleto.getText().toString(),
                                        etDomicilio.getText().toString(), etVecindario.getText().toString(), etCelular.getText().toString(),
                                        etCodigoPostal.getText().toString(), etNumero.getText().toString(), Municipio, etColonia.getText().toString());

                            } else if (domiciliosArray.size() == 0) {
                                InsertarTabla insertarTabla = new InsertarTabla();
                                insertarTabla.RegistrarDomicilios(this, 0, idUser, etNombreCompleto.getText().toString(),
                                        etDomicilio.getText().toString(), etColonia.getText().toString(), etVecindario.getText().toString(),
                                        etCelular.getText().toString(), etCodigoPostal.getText().toString(), etNumero.getText().toString(),
                                        Municipio);
                            }
                            SharedPreferences.Editor editor= preferences.edit();
                            editor.putString("vecindario",etVecindario.getText().toString());
                            editor.commit();

                            Intent intent = new Intent(this, MainActivityTicket.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);

                        } else
                            cBConfirmoDatos.setError("Para continuar debes seleccionar la casilla\nTienes dudas lee terminos y condiciones");

                    }
                    // Toast.makeText(this, ""+cont, Toast.LENGTH_SHORT).show();
                }
                ///Toast.makeText(this, ""+Informacion[i], Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            System.out.println(e);
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void ContinuarPedido(View view) {

        Ciclo();


    }

    boolean esVacio(EditText editText, String msg) {
        Boolean bandel = true;

        if (editText.getText().toString().isEmpty()) {

            editText.setError(msg);
            bandel = false;
        }

        return bandel;
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
                if (Municipio.equals("Otro Municipio")) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);

    }
}