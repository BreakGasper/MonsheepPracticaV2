package com.example.monsheeppractica.adaptadores;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
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

import com.airbnb.lottie.LottieAnimationView;
import com.example.monsheeppractica.GetterAndSetter.Categorias;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.DatabaseHandler;

import java.util.ArrayList;

public   class ListaCategoriaAdapter extends BaseAdapter {

    private  final Context context;
    private ArrayList<Categorias> categoriasArrayList = new ArrayList<>();

    public ListaCategoriaAdapter(Context context, ArrayList<Categorias> categoriasArrayList){
        this.context = context;
        this.categoriasArrayList = categoriasArrayList;
    }

    @Override
    public int getCount() {
        return categoriasArrayList.size();

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
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        Categorias categoria=categoriasArrayList.get(i);
        ListaCategoriaAdapter.ListViewHolder holder;
        if (view == null){
            view =inflater.inflate(R.layout.categorialista, viewGroup, false);

            holder = new ListViewHolder();
            holder.tvNombre= (TextView) view.findViewById(R.id.tvCat);
            holder.tvDesc= (TextView) view.findViewById(R.id.tvDesc);
            holder.iv_fondo= (ImageView) view.findViewById(R.id.iv_fondo);
            holder.animationView   = view.findViewById(R.id.animation_viewCat);

            view.setTag(holder);
        }else
        {
            Log.d("ListView", "RECYCLED");
            holder = (ListViewHolder) view.getTag();
        }
        // Custom animation speed or duration.
    /*    ValueAnimator animator
                = ValueAnimator.ofFloat(0f, 1f);
         animator.setDuration(5000);
         animator.setStartDelay(1000);
         animator.reverse();
        animator.addUpdateListener(animation -> {
            holder.animationView.setProgress(
                    (Float) animation
                            .getAnimatedValue());
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
             //  animator.pause();

                //animator.pause();
                //holder.animationView.setVisibility(View.GONE);
               // holder.animationView.setVisibility(View.GONE);
//             Toast.makeText(context, "Hola mundo", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);

            }
        });
        animator.start();*/
        DatabaseHandler db  = new DatabaseHandler(context);
        holder.iv_fondo.setImageBitmap(db.getimage(categoria.getId_foto()));
        //asignar los datos correspondientes en la lista
        holder.tvNombre.setText(categoria.getCategoria() );
        holder.tvDesc.setText(categoria.getDescripcion());

        return view;
    }


    static class ListViewHolder{
        LottieAnimationView animationView;
        TextView tvNombre,tvDesc;
        ImageView iv_fondo;
    }
}