package com.example.monsheeppractica.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;

public class TestNormalAdapter extends StaticPagerAdapter {
    private  final Context context;
    private ArrayList<Productos> productos;

    public TestNormalAdapter(Context context, ArrayList<Productos> promo) {
        this.context = context;
        this.productos = promo;
    }

    @Override
    public View getView(ViewGroup container, final int position) {

        Productos producto=productos.get(position);
        ImageView view = new ImageView(container.getContext());
        try {


            DatabaseHandler db  = new DatabaseHandler(context);
            Bitmap originalBitmap =db.getimage(producto.getId_Foto());
            view.setImageBitmap(originalBitmap);

        }catch (Exception e){
            Toast.makeText(context, "Error url: "+e, Toast.LENGTH_SHORT).show();
        }
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        return view;
    }


    @Override
    public int getCount() {
        return productos.size();
    }
}
