package com.example.monsheeppractica.adaptadores;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.monsheeppractica.Activitys.MainActivityPerfilUser;
import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.Activitys.MainActivityDetalles;
import com.example.monsheeppractica.MainActivity;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.InsertarTabla;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorPost extends RecyclerView.Adapter<AdaptadorPost.ViewHolder> {
    private ArrayList<Productos> productos;
    private Context context;
    Activity mActivity;
    // private ItemClickListener listener;
    //  public abstract void onItemClick(Productos item);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDescPost, tvPrecio, tvEscrip, tvProvedor;
        ImageView ivCora, ivPost;
        LinearLayout linearLayout;
        CircleImageView ivPerfil;
        Context context;
        LottieAnimationView animationView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvDescPost = (TextView) view.findViewById(R.id.tvDescPost);
            tvEscrip = (TextView) view.findViewById(R.id.tvDEsci);
            tvPrecio = (TextView) view.findViewById(R.id.tvPrice);
            ivCora = (ImageView) view.findViewById(R.id.ivCorazon);
            ivPerfil = (CircleImageView) view.findViewById(R.id.ivNegocioPerfil);
            ivPost = (ImageView) view.findViewById(R.id.ivpost);
            tvProvedor = (TextView) view.findViewById(R.id.tvProvedor);
            linearLayout = (LinearLayout) view.findViewById(R.id.linear);
            animationView = view.findViewById(R.id.animation_view);
        }


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    String name, idUser, id_Foto;

    public AdaptadorPost(ArrayList<Productos> dataSet, Context context, String name, String idUser, String id_Foto, Activity mActivity) {
        productos = dataSet;
        this.context = context;
        this.name = name;
        this.idUser = idUser;
        this.id_Foto = id_Foto;
        this.mActivity=mActivity;

    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.postlista, viewGroup, false);

        return new AdaptadorPost.ViewHolder(view);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Productos producto = productos.get(position);
        viewHolder.itemView.setEnabled(true);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewHolder.itemView.setEnabled(false);

                Intent intent = new Intent(context, MainActivityDetalles.class);
                intent.putExtra("id_producto", "" +  producto.getId_producto() );
                intent.putExtra("idFotoNego", producto.getIdUser());
                intent.putExtra("idPerfil", "" + producto.getIdNegocio());
                intent.putExtra("namePerfil", "" + producto.getId_Foto());
                intent.putExtra("proveedor", producto.getIdFotoUser());
                context.startActivity(intent);
                mActivity.overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.itemView.setEnabled(true);

                    }
                },2000);

            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                return false;
            }
        });


// Custom animation speed or duration.
        ValueAnimator animator
                = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(2000);
        animator.setStartDelay(2000);
       //
        animator.addUpdateListener(animation -> {
            viewHolder.animationView.setProgress(
                    (Float) animation
                            .getAnimatedValue());
        });

        animator.start();

        SharedPreferences preferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        ArrayList<Carrito> Array = new ArrayList<>();
        ConsultarTabla consultarTabla = new ConsultarTabla(context);
        consultarTabla.CarritoConsulta(Array, "Validate", idUser, "" + producto.getId_producto(), "");
        String[] arreglo = new String[ Array.size()];
        //Toast.makeText(context, ""+Array.size()+idUser+producto.getId_producto(), Toast.LENGTH_SHORT).show();

        for (int i = 0; i < arreglo.length; i++) {
            //Toast.makeText(context, ""+Array.get(i).getSolicitud(), Toast.LENGTH_SHORT).show();
            if (Array.get(i).getSolicitud().equals("0")){

                viewHolder.animationView.setAnimation(R.raw.caradd);

            }

        }

        viewHolder.animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            viewHolder.animationView.setAnimation(R.raw.caradd);



            int idticket = (int) (Math.random() * 10000 + 1 * 3 + 15);


            consultarTabla.CarritoConsulta(Array, "Validate", idUser, "" + producto.getId_producto(), "");

            if (Array.size() == 0) {
                InsertarTabla insertarTabla = new InsertarTabla();
                insertarTabla.RegistrarCarritoTicket(context, idticket, "" + idUser, producto.getProducto(), "1", producto.getCosto(), "" + producto.getIdNegocio(),
                        producto.getId_Foto(), "" + producto.getId_producto(), "", "",  producto.getCantidadMinima());
                MainActivity mainActivity = new MainActivity();
                mainActivity.ConsultarTicket(context );
                mActivity.overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);

            }
//                Toast.makeText(context, "Ya lo agregaste al carrito", Toast.LENGTH_SHORT).show();

        }
        });

        DatabaseHandler db = new DatabaseHandler(context);

        Picasso.get().load(db.getImagen(""+producto.getIdNegocio()+".jpg")).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(viewHolder.ivPerfil);
        Picasso.get().load(db.getImagen(""+producto.getId_producto()+".jpg")).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(viewHolder.ivPost);

//        viewHolder.ivPost.setImageBitmap(db.getimageID(""+producto.getId_producto()));///cornerImage.getRoundedCornerBitmap(originalBitmap,100));
//        viewHolder.ivPerfil.setImageBitmap(db.getimageID(producto.getIdNegocio()));//cornerImage.getRoundedCornerBitmap(originalBitmap,100) cornerImage.getRoundedCornerBitmap(originalBitmap, context)]

        viewHolder.ivPerfil.setOnClickListener(view -> {
            Intent intent = new Intent(context, MainActivityPerfilUser.class);
            intent.putExtra("idPerfil",""+ producto.getIdNegocio());
            context.startActivity(intent);
        });
        GradientDrawable drawable = (GradientDrawable) viewHolder.linearLayout.getBackground();
        drawable.mutate(); // only change this instance of the xml, not all components using this xml
        drawable.setStroke(10, Color.RED);
        //// set stroke width and stroke color
        String color = "#ff6565";

        switch (producto.getColor_p()) {

            case "Rojo":
                color = "#ff6565";
                // holder.linearLayout.setBackgroundColor(Color.parseColor("#ff6565"));
                break;
            case "Naranja":
                color = "#fa5f49";
                break;
            case "Rosa":
                color = "#f3a8c2";
                break;
            case "Amarillo":
                color = "#ffda89";
                break;
            case "Verde":
                color = "#8cfca4";
                break;
            case "Azul":
                // holder.linearLayout.setBackgroundColor(Color.parseColor("#75c2f9"));
                color = "#75c2f9";
                break;
            case "Violeta":
                //holder.linearLayout.setBackgroundColor(Color.parseColor("#caacf9"));
                color = "#caacf9";
                break;
            case "Negro":
                //holder.linearLayout.setBackgroundColor(Color.parseColor("#000000"));
                color = "#000000";
                break;
            case "Blanco":
                //  holder.linearLayout.setBackgroundColor(Color.parseColor("#ffe4e1"));
                color = "#ffe4e1";
                break;


        }
        drawable.setStroke(15, Color.parseColor(color));
        viewHolder.tvDescPost.setText("" + producto.getProducto());
        viewHolder.tvEscrip.setText("Descripcion: " + producto.getVentaMenudeo());

        viewHolder.tvProvedor.setText("(" + producto.getCategoria_nombre() + ")" + " $" + producto.getCosto(), TextView.BufferType.SPANNABLE);

        Spannable s = (Spannable) viewHolder.tvProvedor.getText();
        int start = producto.getCategoria_nombre().length() + 2;
        int end = start + producto.getCosto().length() + 2;
        s.setSpan(new ForegroundColorSpan(Color.rgb(2, 133, 5)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        viewHolder.tvDescPost.setText("" + producto.getProducto());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productos.size();
    }

}
