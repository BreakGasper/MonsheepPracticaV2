package com.example.monsheeppractica.adaptadores;

import static com.example.monsheeppractica.WebService.wsDataDownload.ip;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.monsheeppractica.Activitys.MainActivityDetalles;
import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.Activitys.MainActivityCarrito;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.EliminarTabla;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.ViewHolder> {

    Context context;
    ArrayList<Carrito> productoArrayList = new ArrayList<>();
    ArrayList<Negocio> negocioArrayList = new ArrayList<>();
    Activity mActivity;
    String bandel;
    int a = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout lLicons, lLfondo;
        TextView tvN;
        ImageView ivProducto, iVitemDelete, ivitemEdit,iVitemStatus;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tvN = (TextView) view.findViewById(R.id.tvEtiqueta);
            lLicons = (LinearLayout) view.findViewById(R.id.lLicons);
            lLfondo = (LinearLayout) view.findViewById(R.id.lLinearFondo);

            ivProducto = (ImageView) view.findViewById(R.id.iVProductos);
            iVitemDelete = (ImageView) view.findViewById(R.id.iVitemDelete);
            ivitemEdit = (ImageView) view.findViewById(R.id.iVitemEdit);
            iVitemStatus= (ImageView) view.findViewById(R.id.iVitemStatus);
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
                holder.itemView.setEnabled(false);
                if (bandel.equals("ticket") || bandel.equals("ket") || bandel.equals("tk")) {
                    Intent intent = new Intent(context , MainActivityDetalles.class);
                    intent.putExtra("id_producto",""+itemcomentarios.getIdProducto());
                    context.startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.itemView.setEnabled(true);

                        }
                    },2000);
                } else {
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.ivgirar);
                    holder.ivitemEdit.startAnimation(animation);
                    holder.iVitemDelete.startAnimation(animation);
                    holder.iVitemDelete.setVisibility(View.VISIBLE);
                    holder.ivitemEdit.setVisibility(View.VISIBLE);
                    holder.lLfondo.setEnabled(false);
                    holder.itemView.setEnabled(false);
                }
            }
        });

        if (bandel.equals("tk")){
            holder.iVitemStatus.setVisibility(View.VISIBLE);
            if (itemcomentarios.getSolicitud().equals("Tienda")) {
                holder.iVitemStatus.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.entregatime));
            } else if (itemcomentarios.getSolicitud().equals("Entregado")) {
                holder.iVitemStatus.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.entregapaquete));
            } else if (itemcomentarios.getSolicitud().equals("Camino")) {
                holder.iVitemStatus.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.entregarapida));
            }
        }
        DatabaseHandler db = new DatabaseHandler(context);

        Picasso.get().load(db.getImagen(""+itemcomentarios.getIdProducto()+".jpg")).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.ivProducto);
//        holder.ivProducto.setImageBitmap(db.getimageID(itemcomentarios.getIdProducto()));

        String abrev = "pza";
        if (Integer.parseInt(itemcomentarios.getCantidad()) > 1) {
            abrev = "pzas";
        }

        ConsultarTabla consultarTabla = new ConsultarTabla(context);
        consultarTabla.NegocioConsulta(negocioArrayList,"like",itemcomentarios.getIdproveedor(),"","");
        int a = Integer.parseInt(itemcomentarios.getPrecio()) * Integer.parseInt(itemcomentarios.getCantidad());
        holder.tvN.setText("" + itemcomentarios.getProductos() + "\n$" + itemcomentarios.getPrecio() + " x " +
                itemcomentarios.getCantidad() + abrev + " = $" + a);
        if (bandel.equals("tk")){
            holder.tvN.setText("" + itemcomentarios.getProductos() + "\n$" + itemcomentarios.getPrecio() + " x " +
                    itemcomentarios.getCantidad() + abrev + " = $" + a+"\nNegocio:\n\t"+negocioArrayList.get(position).getNombre());
        }



        holder.iVitemDelete.setOnClickListener(view -> {
            AlertDialog.Builder alerta = new AlertDialog.Builder(context);
            alerta.setTitle("Eliminar del carrito");
            alerta.setMessage("¿Deseas Eliminar este producto del carrito?");
            alerta.setNegativeButton("Cancelar", null);
            alerta.setNeutralButton("Eliminar", (dialogInterface, i) -> {

                Animation animation = AnimationUtils.loadAnimation(context, R.anim.left_out);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        holder.iVitemDelete.setEnabled(false);
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
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.iVitemDelete.setEnabled(true);

                            }
                        },2000);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                holder.lLfondo.startAnimation(animation);
            });
            alerta.setIcon(R.drawable.ic_baseline_delete_outline_24);
            alerta.show();

            //Eliminar
        });
        holder.ivitemEdit.setOnClickListener(view -> {
            holder.ivitemEdit.setEnabled(false);
            MainActivityCarrito mainActivityCarrito = new MainActivityCarrito();
            mainActivityCarrito.AlertDialogEdit(context, itemcomentarios.getCantidadDisponible(),
                   ""+ itemcomentarios.getIdticket(), itemcomentarios.getIdProducto(), itemcomentarios.getCantidad(), mActivity);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.ivitemEdit.setEnabled(true);

                }
            },2000);
        });


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productoArrayList.size();
    }
}
