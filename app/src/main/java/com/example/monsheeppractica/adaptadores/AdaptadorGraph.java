package com.example.monsheeppractica.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdaptadorGraph extends RecyclerView.Adapter<AdaptadorGraph.ViewHolder> {

    Context context;
    Activity mActivity;
    ArrayList productoArrayList = new ArrayList<>();
    String idUser;
    String idNegocio, idFotoUser, NombreUser;
    List<Integer> colorArrayList = new ArrayList<>();


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvColor;
        ImageView ivColor;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvColor = (TextView) view.findViewById(R.id.tvColorGraph);
            ivColor = (ImageView) view.findViewById(R.id.ivColorGraph);
        }

    }

    public AdaptadorGraph(Context context, ArrayList productoArrayList,
                          List colorArrayList, Activity mActivity) {
        this.context = context;
        this.productoArrayList = productoArrayList;
        this.colorArrayList = colorArrayList;
        this.mActivity = mActivity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.graph_bar, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //  Carrito itemcomentarios = productoArrayList.get(position);
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);

        holder.tvColor.setText("" + productoArrayList.get(position));// + itemcomentarios.getProductos());
        holder.ivColor.setBackgroundColor(colorArrayList.get(position));//Color.parseColor("#FA584D"));

        SharedPreferences preferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");

//        ConsultarTabla consultarTabla =new ConsultarTabla(context);
//        consultarTabla.CarritoConsulta(ArrayList,"idNegocio",idNegocio,"",""+productoArrayList.get(position));


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productoArrayList.size();
    }
}
