package com.example.monsheeppractica.WebService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.monsheeppractica.Activitys.MainActivityLogin;
import com.example.monsheeppractica.GetterAndSetter.wsGetterSetter.wsProducto;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.sqlite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class wsDataDownload implements Response.Listener<JSONObject>, Response.ErrorListener {
    Context context;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    //ProgressDialog progreso;
    List<String>listConsejos = Arrays.asList("Preciona el icono del carrito para agregarlos a tu lista.", "Antes de hacer un pedido revisa el provedor"
            , "No es necesario hacer un pedido, puedes comparar precios asi ahorrar gastos"
            ,"No compartas tus datos personales externos a la app","Puedes contactar a los negocios por medio whatsapp o llamada");
//   static public String ip = "https://monsheep.000webhostapp.com/";//https://moonsheep-com.preview-domain.com/
    static public String ip = "https://moonsheep.ga/";///82.180.138.54
    String tables = "0";
    Random aleatorio = new Random();
    public wsDataDownload(Context context) {

        this.context = context;
    }

    public void cargarWebService(String table) {
        // System.out.println("Tabla WS: "+table);
        tables = table;
        System.out.println(tables);

        request = Volley.newRequestQueue(context);
        try {
//            progreso = new ProgressDialog(context);
//           //
//            // progreso.setContentView(R.layout.progres_dialog);
//         //   TextView tvProgreso = progreso.findViewById(R.id.tv_progres);
//
////            progreso.setMax(100);
//            progreso.setTitle("¡Obteniendo Catalogo Actual!");
//            progreso.setMessage("consejo: "+listConsejos.get(aleatorio.nextInt(listConsejos.size())));
//           // tvProgreso.setText("Consejo: "+listConsejos.get(aleatorio.nextInt(listConsejos.size())));
//
////            progreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progreso.show();
            String url = ip + "Moonsheep/extraer_DatosProductos.php?tabla=" + table;
            url = url.replace(" ", "%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            request.add(jsonObjectRequest);
        } catch (Exception e) {
            System.out.println("Edit: " + e);
           // Toast.makeText(context, "Error RN CARGAR WEBSERCIE 2 " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
//        progreso.hide();
        System.out.println("Error: " + error);
        // Toast.makeText(context, "No se pudo consultar" + error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    ArrayList Columnas(@NonNull String tabla) {

        ArrayList list = new ArrayList();
        list.clear();
        switch (tabla) {
            case "carrito":
                list.add("idticket");
                list.add("idUser");
                list.add("Productos");
                list.add("cantidad");
                list.add("precio");
                list.add("idproveedor");
                list.add("idProducto");
                list.add("status");
                list.add("fecha");
                list.add("hora");
                list.add("cantidadDisponible");
                list.add("solicitud");
                list.add("idDomicilio");
                list.add("Ticket");
                list.add("id");
                break;
            case "categoria":
                list.add("id_categoria");
                list.add("categoria");
                list.add("Descripcion");
                list.add("Id_Foto");
                list.add("status");
                list.add("idproducto");
                list.add("id");
                break;
            case "clientes":
                list.add("id_cliente");
                list.add("Nombre");
                list.add("Calle");
                list.add("Numero");
                list.add("Interior");
                list.add("Codigo_Postal");
                list.add("Colonia");
                list.add("Municipio");
                list.add("Estado");
                list.add("Alias");
                list.add("Lada");
                list.add("status");
                list.add("Telefono");
                list.add("TipoTelefono");
                list.add("ApellidoPaterno");
                list.add("ApellidoMaterno");
                list.add("TipoCompra");
                list.add("Id_Foto");
                list.add("Contra");
                list.add("idNegocio");
                list.add("correo");
                list.add("id");
                break;
            case "comentario":
                list.add("id_comentario");
                list.add("comentario");
                list.add("nombreUser");
                list.add("idProducto");
                list.add("idUser");
                list.add("nombreProducto");
                list.add("urlFoto");
                list.add("status");
                list.add("fecha");
                list.add("hora");
                list.add("id");
                break;
            case "domicilios":
                list.add("idDomicilio");
                list.add("idUser");
                list.add("NombreCompleto");
                list.add("Domicilio");
                list.add("Colonia");
                list.add("Vecindario");
                list.add("Celular");
                list.add("CodigoPostal");
                list.add("numero");
                list.add("municipio");
                list.add("id");
                break;
            case "negocio":
                list.add("idNegocio");
                list.add("idUser");
                list.add("Nombre");
                list.add("TipoCompra");
                list.add("Calle");
                list.add("Numero");
                list.add("Interior");
                list.add("Codigo_Postal");
                list.add("Colonia");
                list.add("Municipio");
                list.add("Estado");
                list.add("Lada");
                list.add("Telefono");
                list.add("status");
                list.add("TipoTelefono");
                list.add("Id_Foto");
                list.add("Contra");
                list.add("Correo");
                list.add("Descripcion");
                list.add("servicioDomicilio");
                list.add("id");
                break;
            case "producto":
                list = new ArrayList();
                list.add("id_producto");
                list.add("producto");
                list.add("costo");
                list.add("ventaMenudeo");
                list.add("ventaMayoreo");
                list.add("marca");
                list.add("color");
                list.add("unidadMedida");
                list.add("categoria_nombre");
                list.add("id_categoria");
                list.add("Id_Foto");
                list.add("status");
                list.add("cantidadMinima");
                list.add("idFotoUser");
                list.add("NombreUser");
                list.add("idUser");
                list.add("idNegocio");
                list.add("fecha");
                list.add("hora");
                list.add("id");
                break;
            case "roll":
                list.add("idPromo");
                list.add("fecha");
                list.add("Hora");
                list.add("oferta");
                list.add("status");
                list.add("dato");
                list.add("id");
                break;
            case "seguir":
                list.add("idSeguir");
                list.add("idUser");
                list.add("idSeguidor");
                list.add("nombreSeguidor");
                list.add("status");
                list.add("id");
                break;
            case "images_categoria":
                list.add("id_foto");
                list.add("img");
                list.add("id_Catego");
                list.add("id");
                break;

        }
        return list;
    }

    @NonNull
    static public ArrayList NombreTablas() {
        ArrayList list = new ArrayList();
        list.add("carrito");
        list.add("categoria");
        list.add("clientes");
        list.add("comentario");
        list.add("domicilios");
        list.add("negocio");
        list.add("producto");
        list.add("roll");
        list.add("seguir");
        list.add("images_categoria");
        return list;
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray json = response.optJSONArray("tables");
            for (int j = 0; j < json.length(); j++) {
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(j);
                DatosGuardando(j, Columnas(tables), tables, jsonObject);
            }
//            progreso.hide();
        } catch (JSONException e) {
            e.printStackTrace();
//            progreso.hide();
        }
    }

    void DatosGuardando(int pos, ArrayList list, String tabla, JSONObject jsonObject) {

        sqlite usuarios = new sqlite(context, "monsheep", null, 1);
        SQLiteDatabase BaseDeDatos = usuarios.getWritableDatabase();
        ContentValues registro = new ContentValues();
        String[] arreglo = new String[list.size()];

        for (int i = 0; i < arreglo.length; i++) {

            registro.put(list.get(i).toString(), jsonObject.optString(list.get(i).toString()));
            // Toast.makeText(context, ""+list+" \n"+jsonObject.optString(list.get(i).toString())+"\n"+tabla, Toast.LENGTH_SHORT).show();
            System.out.println("" + list.get(i).toString() + " = " + jsonObject.optString(list.get(i).toString()) + "\n" + tabla);
        }
        int n = (int) BaseDeDatos.insert(tabla, null, registro);
        if (n == 1) {
            System.out.println(tabla);

        }
//        Intent intent = new Intent(context, MainActivityLogin.class);
//        context.startActivity(intent);
//        activity.finish();
    }
}
