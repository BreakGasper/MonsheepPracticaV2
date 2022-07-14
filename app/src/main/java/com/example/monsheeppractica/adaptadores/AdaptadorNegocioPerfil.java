package com.example.monsheeppractica.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.DatabaseHandler;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorNegocioPerfil extends RecyclerView.Adapter<AdaptadorNegocioPerfil.ViewHolder> {
    private ArrayList<Negocio> productos;
    private Context context;

    // private ItemClickListener listener;
    //  public abstract void onItemClick(Productos item);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvnegocio, tvnegociodatos;

        CircleImageView ivNegocioPerfil;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvnegocio = (TextView) view.findViewById(R.id.tvnegocio);
            tvnegociodatos = (TextView) view.findViewById(R.id.tvnegociodatos);
            ivNegocioPerfil = (CircleImageView) view.findViewById(R.id.ivNegocioPerfil);

        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    String name, idUser, id_Foto;

    public AdaptadorNegocioPerfil(ArrayList<Negocio> dataSet, Context context, String name, String idUser, String id_Foto) {
        productos = dataSet;
        this.context = context;
        this.name = name;
        this.idUser = idUser;
        this.id_Foto = id_Foto;

    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.negocio_perfil_lista, viewGroup, false);

        return new AdaptadorNegocioPerfil.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Negocio producto = productos.get(position);


        DatabaseHandler db = new DatabaseHandler(context);

        viewHolder.ivNegocioPerfil.setImageBitmap(db.getimage(producto.getId_Foto()));///cornerImage.getRoundedCornerBitmap(originalBitmap,100));
        viewHolder.tvnegocio.setText(""+producto.getNombre());
        viewHolder.tvnegociodatos.setText("Contacto:\n"+producto.getLada()+" "+producto.getTeléfono());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productos.size();
    }

}
