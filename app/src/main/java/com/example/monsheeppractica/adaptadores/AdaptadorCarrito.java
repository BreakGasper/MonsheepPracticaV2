package com.example.monsheeppractica.adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.Activitys.MainActivityCarrito;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.EliminarTabla;

import java.util.ArrayList;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.ViewHolder> {

    Context context;
    ArrayList<Carrito> productoArrayList = new ArrayList<>();
    Activity mActivity;
    String bandel;
    int a = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lLicons, lLfondo;
        TextView tvN;
        ImageView ivProducto, iVitemDelete, ivitemEdit;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tvN = (TextView) view.findViewById(R.id.tvEtiqueta);
            lLicons = (LinearLayout) view.findViewById(R.id.lLicons);
            lLfondo = (LinearLayout) view.findViewById(R.id.lLinearFondo);

            ivProducto = (ImageView) view.findViewById(R.id.iVProductos);
            iVitemDelete = (ImageView) view.findViewById(R.id.iVitemDelete);
            ivitemEdit = (ImageView) view.findViewById(R.id.iVitemEdit);

        }

    }

    public AdaptadorCarrito(Context context, ArrayList<Carrito> productoArrayList, Activity mActivity, String bandel) {
        this.context = context;
        this.productoArrayList = productoArrayList;
        this.mActivity = mActivity;
        this.bandel = bandel;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.carritolista, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Carrito itemcomentarios = productoArrayList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
                holder.ivitemEdit.startAnimation(animation);
                holder.iVitemDelete.startAnimation(animation);
                holder.iVitemDelete.setVisibility(View.VISIBLE);
                holder.ivitemEdit.setVisibility(View.VISIBLE);

                holder.lLfondo.setEnabled(false);
                holder.itemView.setEnabled(false);
            }
        });
        if (bandel.equals("ticket")){
            holder.itemView.setEnabled(false);
        }

        DatabaseHandler db = new DatabaseHandler(context);

        holder.ivProducto.setImageBitmap(db.getimage(itemcomentarios.getStatus()));
        String abrev = "pza";
        if (Integer.parseInt(itemcomentarios.getCantidad()) > 1) {
            abrev = "pzas";
        }
        int a = Integer.parseInt(itemcomentarios.getPrecio()) * Integer.parseInt(itemcomentarios.getCantidad());
        holder.tvN.setText("" + itemcomentarios.getProductos() + "\n$" + itemcomentarios.getPrecio() + " x " +
                itemcomentarios.getCantidad() + abrev + " = $" + a);

        holder.iVitemDelete.setOnClickListener(view -> {
            AlertDialog.Builder alerta = new AlertDialog.Builder(context);
            alerta.setTitle("Eliminar del carrito");
            alerta.setMessage("¿Deseas Eliminar este producto del carrito?");

            alerta.setNeutralButton("Eliminar", (dialogInterface, i) -> {
                //Toast.makeText(context, "Eliminar", Toast.LENGTH_SHORT).show();
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.left_out);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {


                        holder.lLfondo.setVisibility(View.GONE);
                        EliminarTabla eliminarTabla = new EliminarTabla();
                        eliminarTabla.EliminarProductoCarrito(context, "" + itemcomentarios.getIdProducto());

                        SharedPreferences preferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
                        String c = preferences.getString("art", "et_pass.getText().toString()");
                        int count = Integer.parseInt(c) - 1;
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("art", String.valueOf(count)).commit();

                        Intent intent = new Intent(context, MainActivityCarrito.class);
                        context.startActivity(intent);
                        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        mActivity.finish();

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                holder.lLfondo.startAnimation(animation);
            });

            alerta.setNegativeButton("Cancelar", null);
            alerta.setIcon(R.drawable.ic_baseline_delete_outline_24);
            alerta.show();

            //Eliminar
        });
        holder.ivitemEdit.setOnClickListener(view -> {

            MainActivityCarrito mainActivityCarrito = new MainActivityCarrito();
            mainActivityCarrito.AlertDialogEdit(context, itemcomentarios.getCantidadDisponible(),
                    itemcomentarios.getStatus(), itemcomentarios.getIdProducto(), itemcomentarios.getCantidad(), mActivity);
            //Editar
        });


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productoArrayList.size();
    }
}
