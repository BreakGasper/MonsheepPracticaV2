package com.example.monsheeppractica.WebService;

import static com.example.monsheeppractica.WebService.wsDataDownload.ip;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
 import org.json.JSONObject;

public class wsEdit implements Response.Listener<JSONObject>, Response.ErrorListener {
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Context context;
    ProgressDialog progreso;
   // String ip = "https://monsheep.000webhostapp.com/";

    public wsEdit(Context context) {
        this.context = context;
    }
//Modificar el id de la baja***Modificar Validaciion registro de usuario name??
    public void wsEditarTabla(String ruta) {
        request = Volley.newRequestQueue(context);
        try {
            progreso = new ProgressDialog(context);
            progreso.setMessage("Consultando...");
            progreso.show();
            String url = ip + "Moonsheep/"+ruta;
            url=url.replace(" ","%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            progreso.hide();
            request.add(jsonObjectRequest);
        } catch (Exception e) {
            System.out.println("Edit: "+e);
            //Toast.makeText(context, "Error RN CARGAR WEBSERCIE 2 " + e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(context, "No tienes un historial aun,\n puedes ordenar alguna especialidad\no revisa tu conexion a internet", Toast.LENGTH_LONG).show();
        System.out.println("Edit: "+error);
        Log.d("ERROR: ", error.toString());
        progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
//        wsProducto p = null;
//        JSONArray json = response.optJSONArray("producto");
//        System.out.println("JSON: "+json.length());
//        Log.e("JSON",""+json.length());
//        try {

//            for (int i = 0; i < json.length(); i++) {
//                p = new wsProducto();
//                JSONObject jsonObject = null;
//                jsonObject = json.getJSONObject(i);
//                p.setId_producto(jsonObject.optInt("id_producto"));
//                p.setId_categoria(jsonObject.optString("id_categoria"));
//                p.setStatus(jsonObject.optString("status"));
//                Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
//            }
//

           progreso.hide();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Toast.makeText(context, "No se ha podido establecer conexión con el servidor" +
//                    " " + response, Toast.LENGTH_LONG).show();
//            progreso.hide();
//        }
    }
}
