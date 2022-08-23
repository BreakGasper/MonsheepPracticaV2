package com.example.monsheeppractica.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.monsheeppractica.Activitys.MainActivityDetalles;
import com.example.monsheeppractica.Activitys.MainActivityPerfilUser;
import com.example.monsheeppractica.GetterAndSetter.Categorias;
import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.GetterAndSetter.Roll;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TestNormalAdapter extends StaticPagerAdapter {
    private final Context context;
    private ArrayList<Roll> productos;
    private ArrayList<Productos> productosArrayList = new ArrayList<>();

    private ArrayList<Negocio> negocioArrayList = new ArrayList<>();

    private ArrayList<Categorias> categoriasArrayList = new ArrayList<>();


    public TestNormalAdapter(Context context, ArrayList<Roll> promo) {
        this.context = context;
        this.productos = promo;
    }

    @Override
    public View getView(ViewGroup container, final int position) {

        Roll producto = productos.get(position);
        ImageView view = new ImageView(container.getContext());

        try {
            DatabaseHandler db = new DatabaseHandler(context);
            Picasso.get().load(db.getImagen(""+producto.getDato()+".jpg")).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(view);

            Bitmap originalBitmap = db.getimageID(producto.getDato());
           // view.setImageBitmap(originalBitmap);

        } catch (Exception e) {
            Toast.makeText(context, "Error url: " + e, Toast.LENGTH_SHORT).show();
        }

        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOnClickListener(v -> {
            ConsultarTabla consultarTabla = new ConsultarTabla(context);
            productosArrayList.clear();
            categoriasArrayList.clear();
            negocioArrayList.clear();
            consultarTabla.ProductoConsulta(productosArrayList, "idProducto", producto.getDato(), "");
            consultarTabla.CategoriaConsulta(categoriasArrayList, "idCatego", "", "", producto.getDato());
            consultarTabla.NegocioConsulta(negocioArrayList, "Tienda", producto.getDato(), "", "");

//                Toast.makeText(context, "roll: "+producto.getDato()
//                        +"\nArray \nProductos: "+productosArrayList.size()
//                        +"Categoria: "+categoriasArrayList.size()
//                        +"Negocio: "+negocioArrayList.size(), Toast.LENGTH_SHORT).show();

            if (productosArrayList.size() >= 1) {
                if (!productosArrayList.get(0).getCantidadMinima().equals("0")) {
                    Intent intent = new Intent(context, MainActivityDetalles.class);
                    intent.putExtra("id_producto", "" + producto.getDato());
                    context.startActivity(intent);
                } else Toast.makeText(context, "Producto agotado", Toast.LENGTH_SHORT).show();
//                 Toast.makeText(context, "Producto: "+producto.getDato(), Toast.LENGTH_SHORT).show();
            } else if (categoriasArrayList.size() >= 1) {
//                    Toast.makeText(context, "Categoria: " + producto.getDato(), Toast.LENGTH_SHORT).show();
            } else if (negocioArrayList.size() >= 1) {
                Intent intent = new Intent(context, MainActivityPerfilUser.class);
                intent.putExtra("idPerfil", "" + producto.getDato());
                context.startActivity(intent);
//                    Toast.makeText(context, "Negocio: "+producto.getDato(), Toast.LENGTH_SHORT).show();
            }else {
                Snackbar.make(
                       view,
                        ""+producto.getOferta(),
                        BaseTransientBottomBar.LENGTH_LONG
                ).setBackgroundTint(Color.parseColor("#E7610F")).show();
            }

            view.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setEnabled(true);

                }
            }, 2000);

        });
        return view;
    }


    @Override
    public int getCount() {
        return productos.size();
    }
}
