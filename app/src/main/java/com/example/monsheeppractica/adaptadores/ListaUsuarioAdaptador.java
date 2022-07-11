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
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.DatabaseHandler;

import java.util.ArrayList;

public class ListaUsuarioAdaptador {

    public static class listaP extends BaseAdapter {

        private final Context context;
        private ArrayList<Clientes> ArrayList = new ArrayList<>();

        public listaP(Context context, ArrayList<Clientes> ArrayList) {
            this.context = context;
            this.ArrayList = ArrayList;
        }

        @Override
        public int getCount() {
            return ArrayList.size();

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
            //CircleTransform circleTransform = new CircleTransform(context);
            Clientes productos = ArrayList.get(i);
            DatabaseHandler db;
            ListViewHolders holder;
            if (view == null) {
                view = inflater.inflate(R.layout.clienteslista, viewGroup, false);

                holder = new ListViewHolders();
                holder.tvNombre = (TextView) view.findViewById(R.id.tvNombreCliente);
                holder.tvTelefono = (TextView) view.findViewById(R.id.tvTelefonoClientes);
                holder.ivClientes = (ImageView) view.findViewById(R.id.ivCliente);
                view.setTag(holder);
            } else {
                Log.d("ListView", "RECYCLED");
                holder = (ListViewHolders) view.getTag();
            }

            try {

                //  https://anonimus21pizza.000webhostapp.com/Anonimus_pizza/imagenesCategoria/Xuduff.jpg
                //  String patha="https://anonimus21pizza.000webhostapp.com/Anonimus_pizza/imagenesClientes/"+ productos.getId_Foto();

//                Glide.with(context)
//                        .load(patha).centerCrop().transform(circleTransform)
//                        .into(holder.ivClientes);
                db = new DatabaseHandler(context);
                holder.ivClientes.setImageBitmap(db.getimage(productos.getId_Foto()));
            } catch (Exception e) {
                Toast.makeText(context, "Error url: " + e, Toast.LENGTH_SHORT).show();
            }
            //asignar los datos correspondientes en la lista
            holder.tvNombre.setText("" + productos.getNombre() + "\n(" + productos.getAlias() + ")");
            holder.tvTelefono.setText(productos.getTipoTelefono() + " " + productos.getLada() + " " + productos.getTeléfono());

            return view;
        }


        static class ListViewHolders {
            TextView tvNombre, tvTelefono;
            ImageView ivClientes;

        }
    }
}
