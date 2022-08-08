package com.example.monsheeppractica.adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.monsheeppractica.Activitys.MainActivityCarrito;
import com.example.monsheeppractica.Activitys.MainActivityHistorial;
import com.example.monsheeppractica.Activitys.MainActivityPerfilUser;
import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.mytools.GenerarQR;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorNegocioPerfil extends RecyclerView.Adapter<AdaptadorNegocioPerfil.ViewHolder> {
    private ArrayList<Negocio> productos;
    private Context context;
    ArrayList<Carrito> lista;
    Activity mActivity;

    private static final int CODIGO_PERMISO_ESCRIBIR_ALMACENAMIENTO = 1;
    private static final int ALTURA_CODIGO = 500, ANCHURA_CODIGO = 500;
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

    public AdaptadorNegocioPerfil(ArrayList<Negocio> dataSet, Context context, String name, String idUser, String id_Foto, ArrayList lista, Activity mActivity) {
        productos = dataSet;
        this.context = context;
        this.name = name;
        this.idUser = idUser;
        this.id_Foto = id_Foto;
        this.lista = lista;
        this.mActivity = mActivity;

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
        SharedPreferences preferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");

        viewHolder.itemView.setOnClickListener(view -> {
//            Toast.makeText(context, ""+producto.getIdNegocio(), Toast.LENGTH_SHORT).show();
            if (name.equals("MDP")) {
//                Toast.makeText(context, "idUser: "+idUser +"IdNegocio: "+producto.getIdNegocio(), Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.alert_dialog_qrl, null, false);
                GenerarQR qr = new GenerarQR();
                ImageView imagenCodigo= viewInput.findViewById(R.id.imageView5);
                imagenCodigo.setImageBitmap(qr.createQRcodeImage(""+idUser,200,200));

                AlertDialog.Builder alerta = new AlertDialog.Builder(context);
                alerta.setView(viewInput);
                //Mensaje
                alerta.setTitle(Html.fromHtml("<font color='#FE4500'>¿Recibiste tu paquete de " + producto.getNombre() + "?"));
                alerta.setMessage(Html.fromHtml("<H6><font color='#0060D2'>" + "Porfavor,<br>Preciona: 'RECIBI MI PEDIDO'" + "</font></H6>"));
                alerta.setNegativeButton("Recibi mi pedido\nde " + producto.getNombre(), (dialogInterface, i) -> {

                    CerrarDato(idUser,""+producto.getIdNegocio());

                });
                alerta.setIcon(R.drawable.entregapaquete);
                alerta.show();
            } else {

                Intent intent = new Intent(context, MainActivityPerfilUser.class);
                intent.putExtra("idPerfil", "" + producto.getIdNegocio());
                context.startActivity(intent);
            }
        });
        DatabaseHandler db = new DatabaseHandler(context);
        Picasso.get().load(db.getImagen(""+producto.getIdNegocio()+".jpg")).memoryPolicy(MemoryPolicy.NO_CACHE).into(viewHolder.ivNegocioPerfil);

//        viewHolder.ivNegocioPerfil.setImageBitmap(db.getimageID(""+producto.getIdNegocio()));///cornerImage.getRoundedCornerBitmap(originalBitmap,100));
        viewHolder.tvnegocio.setText("" + producto.getNombre());
        viewHolder.tvnegociodatos.setText("Contacto:\n" + producto.getLada() + " " + producto.getTeléfono());


    }

    public void CerrarDato(String idUser,String idN){
        String[] arreglo = new String[lista.size()];


        for (int id = 0; id < arreglo.length; id++) {
            EditarTabla editarTabla = new EditarTabla();
            editarTabla.EditarCarritoStatusUser(context, "" + lista.get(id).getTicket()
                    , "Entregado", "" + idN,idUser);

        }

        Intent intent = new Intent(context, MainActivityHistorial.class);
        context.startActivity(intent);
        mActivity.finish();
        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productos.size();
    }

}
