package com.example.monsheeppractica.adaptadores;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;


import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.R;

import com.example.monsheeppractica.sqlite.DatabaseHandler;

import java.util.ArrayList;

public class Lista_productoAdaptador {

    public static class listaP extends BaseAdapter {

        private final Context context;
        private ArrayList<Productos> productoArrayList = new ArrayList<>();

        public listaP(Context context, ArrayList<Productos> productoArrayList) {
            this.context = context;
            this.productoArrayList = productoArrayList;
        }

        @Override
        public int getCount() {
            return productoArrayList.size();

        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

            Productos productos = productoArrayList.get(i);
            ListViewHolders holder;
            if (view == null) {
                view = inflater.inflate(R.layout.productolista, viewGroup, false);

                holder = new ListViewHolders();
                holder.tvNombre = (TextView) view.findViewById(R.id.tvNombre);
                holder.tvCategoria = (TextView) view.findViewById(R.id.tvCategoriaProducto);
                holder.tvCosto = (TextView) view.findViewById(R.id.tvCosto);
                holder.ivProducto = (ImageView) view.findViewById(R.id.ivProducto);

                view.setTag(holder);
            } else {
                Log.d("ListView", "RECYCLED");
                holder = (ListViewHolders) view.getTag();
            }

            DatabaseHandler db = new DatabaseHandler(context);
            holder.ivProducto.setImageBitmap(db.getimage(productos.getId_Foto()));
            //asignar los datos correspondientes en la lista
            holder.tvNombre.setText("" + productos.getProducto());
            holder.tvCosto.setText("$" + productos.getCosto());
            holder.tvCategoria.setText("Categoria: " + productos.getCategoria_nombre());

            return view;
        }


        static class ListViewHolders {
            TextView tvNombre, tvCosto, tvCategoria;
            ImageView ivProducto;

        }
    }
}
