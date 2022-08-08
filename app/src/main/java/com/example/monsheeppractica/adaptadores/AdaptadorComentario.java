package com.example.monsheeppractica.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.monsheeppractica.Activitys.MainActivityCarrito;
import com.example.monsheeppractica.Activitys.MainActivityDetalles;
import com.example.monsheeppractica.GetterAndSetter.Comentario;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.Interfaz.ItemClickListener;
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.EliminarTabla;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorComentario extends RecyclerView.Adapter<AdaptadorComentario.ViewHolder> {

       Context context;
     ArrayList<Comentario> productoArrayList = new ArrayList<>();
        String idUser;
    String idusers,idFotoUser,NombreUser;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvComentario,tvProducto,tvDelete;

        CircleImageView ivPerfil ;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvDelete = (TextView)view.findViewById(R.id.tvDelete);
            tvNombre = (TextView)view.findViewById(R.id.tvNombreComentario);
            tvComentario = (TextView)view.findViewById(R.id.tvComentario);
            tvProducto = (TextView)view.findViewById(R.id.tvProductoComentario);
            ivPerfil=(CircleImageView)view.findViewById(R.id.ivFotoComentario);
        }

    }

    public AdaptadorComentario(Context context, ArrayList<Comentario> productoArrayList,String idUser){
        this.context = context;
        this.productoArrayList = productoArrayList;
        this.idUser=idUser;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comentariolista, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Comentario itemcomentarios=productoArrayList.get(position);


        DatabaseHandler db  = new DatabaseHandler(context);
        holder.tvNombre.setText(""+itemcomentarios.getIdUser());
        holder.tvProducto.setText("Producto: "+itemcomentarios.getNombreProducto());
        holder.tvComentario.setText(""+itemcomentarios.getComentario());
//        Toast.makeText(context, ""+itemcomentarios.getIdUser(), Toast.LENGTH_SHORT).show();
        Picasso.get().load(db.getImagen(""+itemcomentarios.getIdProducto()+".jpg")).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.ivPerfil);

//        holder.ivPerfil.setImageBitmap(db.getimageID(itemcomentarios.getIdProducto()));

        SharedPreferences preferences =context.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idusers = preferences.getString("idusers","et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser","et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser","et_pass.getText().toString()");

     //   Toast.makeText(context, ""+itemcomentarios.getIdProducto()+idUser+idusers+NombreUser+idFotoUser, Toast.LENGTH_SHORT).show();

        if (idusers.equals(itemcomentarios.getIdProducto())){
            holder.tvDelete.setVisibility(View.VISIBLE);
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.left_out);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            EliminarTabla eliminarTabla = new EliminarTabla();
                            eliminarTabla.EliminarComentario(context,itemcomentarios.getIdComentario());
                            holder.tvDelete.setEnabled(false);
                            holder.itemView.setVisibility(View.GONE);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    holder.tvDelete.setEnabled(true);

                                }
                            },2000);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    holder.itemView.startAnimation(animation);

//                    Intent intent = new Intent(context, MainActivity.class);
//                    intent.putExtra("idusers",idusers);
//                    intent.putExtra("NombreUser",NombreUser);
//                    intent.putExtra("idFotoUser",idFotoUser);
//                    context.startActivity(intent);

                }
            });
        }else holder.tvDelete.setVisibility(View.GONE);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productoArrayList.size();
    }
}
