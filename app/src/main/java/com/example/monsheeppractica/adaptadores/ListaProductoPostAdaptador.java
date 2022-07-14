package com.example.monsheeppractica.adaptadores;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.monsheeppractica.GetterAndSetter.Productos;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.mytools.CornerImage;
import com.example.monsheeppractica.sqlite.DatabaseHandler;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaProductoPostAdaptador  {

   public static class listaP extends BaseAdapter {

        private  final Context context;
        private ArrayList<Productos> productoArrayList = new ArrayList<>();

        public listaP(Context context, ArrayList<Productos> productoArrayList){
            this.context = context;
            this.productoArrayList = productoArrayList;
        }

        @Override
        public int getCount() {
            return productoArrayList.size();

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

            Productos productos=productoArrayList.get(i);
            ListViewHolders holder;
            if (view == null){
                view =inflater.inflate(R.layout.postlista, viewGroup, false);

                holder = new ListViewHolders();
                holder.tvDescPost = (TextView)view.findViewById(R.id.tvDescPost);
                holder.tvEscrip = (TextView)view.findViewById(R.id.tvDEsci);
                holder.tvPrecio = (TextView) view.findViewById(R.id.tvPrice);
                holder.ivCora=(ImageView)view.findViewById(R.id.ivCorazon);
                holder.ivPerfil=( CircleImageView)view.findViewById(R.id.ivNegocioPerfil);
                holder.ivPost=(ImageView)view.findViewById(R.id.ivpost);
                holder.tvProvedor=(TextView)view.findViewById(R.id.tvProvedor);
                holder.linearLayout=(LinearLayout) view.findViewById(R.id.linear);



                view.setTag(holder);
            }else
            {
                Log.d("ListView", "RECYCLED");
                holder = (ListViewHolders) view.getTag();
            }

            DatabaseHandler db  = new DatabaseHandler(context);



            Bitmap originalBitmap =db.getimage(productos.getId_Foto());
            Bitmap perfilFoto = db.getimage(productos.getIdUser());

            /*
//            Glide.with(context)
//                    .load("https://picsum.photos/700/400?random").centerCrop()
//                    .into(holder.ivPost);
//            Glide.with(context)
//                    .load("https://picsum.photos/700/400?random").centerCrop()
//                    .into(holder.ivPerfil);

//            Glide.with(context) //https://picsum.photos/700/400?random
//                    .asBitmap()
//                    .load(perfilFoto)
//                    .into(new CustomTarget<Bitmap>(1980, 1080){
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            holder.ivPerfil.setImageBitmap(resource);
//                        }
//
//                        @Override
//                        public void onLoadCleared(@Nullable Drawable placeholder) {
//                        }
//                    });
//


        /*    OutputStream bytearrayoutputstream;*/
//            originalBitmap.compress(Bitmap.CompressFormat.JPEG,40 /*Calidad*/,originalBitmap);
            //Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 100 /*Ancho*/, 100 /*Alto*/, false /* filter*/);

            CornerImage cornerImage = new CornerImage();
//           holder.ivPost.setImageBitmap(db.getimage(productos.getId_Foto()));///cornerImage.getRoundedCornerBitmap(originalBitmap,100));
//            holder.ivPerfil.setImageBitmap(db.getimage(productos.getIdUser()));//cornerImage.getRoundedCornerBitmap(originalBitmap,100) cornerImage.getRoundedCornerBitmap(originalBitmap, context)]
            //Toast.makeText(context, ""+productos.getIdFotoUser()+ productos.getNombreUser()+"\nid foto: "+ productos.getId_Foto() +"\nIdUser: "+productos.getIdUser(), Toast.LENGTH_LONG).show();
            GradientDrawable drawable = (GradientDrawable)holder.linearLayout.getBackground();
            drawable.mutate(); // only change this instance of the xml, not all components using this xml
            drawable.setStroke(10, Color.RED);
           //// set stroke width and stroke color
            String color="#ff6565";


            switch (productos.getColor_p()){

                case "Rojo":
                    color="#ff6565";
                   // holder.linearLayout.setBackgroundColor(Color.parseColor("#ff6565"));
                    break;
                case "Naranja":
                    color="#fa5f49";
                  //  holder.linearLayout.setBackgroundColor(Color.parseColor("#fa5f49"));

                    break;
                case "Rosa":
                    //holder.linearLayout.setBackgroundColor(Color.parseColor("#f3a8c2"));
                    color="#f3a8c2";
                    break;
                case "Amarillo":
                   // holder.linearLayout.setBackgroundColor(Color.parseColor("#ffda89"));
                    color="#ffda89";
                    break;
                case "Verde":
                 //   holder.linearLayout.setBackgroundColor(Color.parseColor("#8cfca4"));
                    color="#8cfca4";
                    break;
                case "Azul":
                   // holder.linearLayout.setBackgroundColor(Color.parseColor("#75c2f9"));
                    color="#75c2f9";
                    break;
                case "Violeta":
                    //holder.linearLayout.setBackgroundColor(Color.parseColor("#caacf9"));
                    color="#caacf9";
                    break;
                case "Negro":
                    //holder.linearLayout.setBackgroundColor(Color.parseColor("#000000"));
                    color="#000000";
                    break;
                case "Blanco":
                  //  holder.linearLayout.setBackgroundColor(Color.parseColor("#ffe4e1"));
                    color="#ffe4e1";
                    break;


            }
           // drawable.setColor(Color.parseColor(color));
           // drawable.setStroke(15, Color.parseColor(color));
            holder.tvDescPost.setText(""+productos.getProducto());
            holder.tvEscrip.setText("Descripcion: "+productos.getVentaMenudeo());

            holder.tvProvedor.setText("("+productos.getCategoria_nombre()+")"+" $" + productos.getCosto(), TextView.BufferType.SPANNABLE);

            Spannable s = (Spannable)holder.tvProvedor.getText();
            int start = productos.getCategoria_nombre().length()+2;
            int end = start + productos.getCosto().length()+2;
            s.setSpan(new ForegroundColorSpan(Color.rgb(2,133,5)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            return view;
        }


        static class ListViewHolders{
            TextView tvDescPost, tvPrecio,tvEscrip,tvProvedor;
            ImageView ivCora,ivPost;
            LinearLayout linearLayout;
            CircleImageView ivPerfil ;

        }
    }
}
