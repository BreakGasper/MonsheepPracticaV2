package com.example.monsheeppractica.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class wsInsert {

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Context context;
    ProgressDialog progreso;
    String ip="https://monsheep.000webhostapp.com/";
    public wsInsert(Context context) {
        this.context = context;
    }
    public void enlase_base_de_datos(ArrayList list, ArrayList listdata, String ruta){
        request = Volley.newRequestQueue(context);
        System.out.println("Lista and Ruta "+list.size()+"<---->"+listdata+"<------->"+ruta+"<----");
//        Toast.makeText(context, "Lista and Ruta"+list.size()+" "+listdata+" "+ruta, Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, ""+list.size()+" "+listdata+" "+ruta, Toast.LENGTH_SHORT).show();
        try {
            ejecutarServicio(ip+"/Moonsheep/"+ruta,list,listdata);
        }catch (Exception e){
            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
            System.out.println(e);
        }
    }
    private void ejecutarServicio(String URL, ArrayList list, ArrayList listdata){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Subido  WebService", Toast.LENGTH_SHORT).show();

            }
        }, error -> {
            Toast.makeText(context, "Operaccion Erronea"+error, Toast.LENGTH_SHORT).show();
            //android:usesCleartextTraffic="true"
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                String[] arreglo = new String[list.size()];
                for (int i = 0; i < arreglo.length; i++) {
                    parametros.put(list.get(i).toString(),""+listdata.get(i).toString());
                }
                return parametros;
            }
        };
        request.add(stringRequest);
    }

}
