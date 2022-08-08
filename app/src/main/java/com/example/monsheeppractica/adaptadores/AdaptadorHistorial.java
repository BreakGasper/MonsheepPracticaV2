package com.example.monsheeppractica.adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monsheeppractica.Activitys.MainActivityHistorial;
import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.Activitys.MainActivityNotificacionesNegocio;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.registros.EditarTabla;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdaptadorHistorial extends RecyclerView.Adapter<AdaptadorHistorial.ViewHolder> {

    Context context;
    ArrayList<Carrito> productoArrayList = new ArrayList<>();
    Activity mActivity;
    String bandel;
    ArrayList<Carrito> carritoArray = new ArrayList<>();
    int tienda = 0, camino = 0, entrega = 0;
    Boolean aBoolean = false;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv1, tv2, tv3, tv4;
        LinearLayout lLTick;
        ImageView iVStatus;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tv1 = (TextView) view.findViewById(R.id.tv1);
            tv2 = (TextView) view.findViewById(R.id.tv2);
            tv3 = (TextView) view.findViewById(R.id.tv3);
            tv4 = (TextView) view.findViewById(R.id.tv4);


            lLTick = view.findViewById(R.id.lLTick);
            iVStatus = view.findViewById(R.id.ivStatus);

        }

    }

    public AdaptadorHistorial(Context context, ArrayList<Carrito> productoArrayList, Activity mActivity, String bandel) {
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
                .inflate(R.layout.ticket_historial, viewGroup, false);

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
//                if (!bandel.equals("ticket")) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.alert_dialog_historial, null, false);

                RecyclerView rvLista = viewInput.findViewById(R.id.rVList);
                RecyclerView rvNegocio = viewInput.findViewById(R.id.rvNegocios);//*****************
                Button btnpack = viewInput.findViewById(R.id.btnpack);
                Button btnOk = viewInput.findViewById(R.id.btnOk);
                TextView textView6 = viewInput.findViewById(R.id.textView6);
                TextView tvDataUser = viewInput.findViewById(R.id.tvTextdataHistorial);
                TextView t7 = viewInput.findViewById(R.id.textView7);
                TextView tVInfo = viewInput.findViewById(R.id.tVInfo);
                TextView tvLisCompleta = viewInput.findViewById(R.id.tvLisCompleta);

                tvLisCompleta.setOnClickListener(view1 -> {

                    if (aBoolean == false) {
                        aBoolean = true;
                        tvDataUser.setVisibility(View.GONE);
                        t7.setVisibility(View.GONE);
                        tVInfo.setVisibility(View.GONE);
                        btnpack.setVisibility(View.GONE);

                        tvLisCompleta.setText("Ver Detalles del pedido");

                    } else {
                        aBoolean = false;
                        tvDataUser.setVisibility(View.VISIBLE);
                        t7.setVisibility(View.VISIBLE);
                        tVInfo.setVisibility(View.VISIBLE);
                        btnpack.setVisibility(View.VISIBLE);
                        tvLisCompleta.setText("Ver Lista en pantalla completa");
                    }

                });

                String clave = "historial";
                SharedPreferences preferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
                String idUser = preferences.getString("idusers", "et_pass.getText().toString()");
                String idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");
                tvDataUser.setVisibility(View.GONE);
                t7.setVisibility(View.GONE);
                tVInfo.setVisibility(View.GONE);
                tvLisCompleta.setVisibility(View.GONE);
                textView6.setVisibility(View.VISIBLE);
                rvNegocio.setVisibility(View.VISIBLE);
                if (bandel.equals("ket")) {
                    btnpack.setVisibility(View.VISIBLE);
                    tvDataUser.setVisibility(View.VISIBLE);
                    t7.setVisibility(View.VISIBLE);
                    tVInfo.setVisibility(View.VISIBLE);
                    tvLisCompleta.setVisibility(View.VISIBLE);
                    textView6.setVisibility(View.GONE);
                    rvNegocio.setVisibility(View.GONE);

                    clave = "historialProv";
                    idUser = idNegocio;

                    ArrayList<Clientes> clientesArray = new ArrayList<>();
                    ConsultarTabla consultarTabla = new ConsultarTabla(context);
                    consultarTabla.ClientesConsulta(clientesArray, "user", Integer.parseInt(itemcomentarios.getIdUser()), "");
                    Clientes clientes = clientesArray.get(0);
                    tvDataUser.setText("Nombre: " + clientes.getNombre() + " " + clientes.getApellidoPaterno() + " " + clientes.getApellidoMaterno()
                            + "\nDomicilio: " + clientes.getCalle() + " #" + clientes.getNumero()
                            + "\nContacto: " + clientes.getLada() + " " + clientes.getTeléfono());
                }
                String info = "";
                if (itemcomentarios.getSolicitud().equals("Tienda")) {
                    btnpack.setEnabled(true);
                    btnpack.setTextColor(Color.parseColor("#0B0B0B"));
                    btnpack.setText("Ya tengo todo de la lista");
                    btnpack.setBackgroundColor(Color.parseColor("#FFF700"));
                    info = "¡Informacion!\n¿Ya tienes el pedido completo?\nPreciona:\n'Ya tengo todo de la lista' \nY Envialo ";
                    tVInfo.setTextColor(Color.parseColor("#FF0C00"));


                } else if (itemcomentarios.getSolicitud().equals("Entregado")) {
                    btnpack.setEnabled(false);
                    btnpack.setText("Entrega Exitosa");
                    btnpack.setBackgroundColor(Color.parseColor("#08D615"));
                    info = "¡Informacion!\nSe entrego exitosamente tu paquete";
                    tVInfo.setTextColor(Color.parseColor("#0ECB05"));

                } else if (itemcomentarios.getSolicitud().equals("Camino")) {
                    btnpack.setEnabled(false);
                    btnpack.setTextColor(Color.parseColor("#F8F8F8"));
                    btnpack.setText("Pedido Completo y enviado");
                    btnpack.setBackgroundColor(Color.parseColor("#138CFE"));
                    info = "¡Informacion!\n!El pedido debe estar en camino¡";
                    tVInfo.setTextColor(Color.parseColor("#138CFE"));

                }
                tVInfo.setText(info);
                btnpack.setOnClickListener(view1 -> {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(context);
                    //Mensaje
                    alerta.setTitle(Html.fromHtml("<font color='#FE4500'>¿Paquete Completo?"));
                    alerta.setMessage(Html.fromHtml("<H6><font color='#0060D2'>" + "¿Todos los productos de la lista estan listos en este paquete?" + "</font></H6>"));
                    alerta.setNeutralButton("Paquete Listo", (dialogInterface, i) -> {
                        EditarTabla editarTabla = new EditarTabla();
                        editarTabla.EditarCarritoStatus(context, "" + itemcomentarios.getTicket()
                                , "Camino", "" + itemcomentarios.getIdproveedor());
                        btnpack.setEnabled(false);
                        btnpack.setText("Pedido Listo");
                        btnpack.setBackgroundColor(Color.parseColor("#138CFE"));
                        Intent intent = new Intent(context, MainActivityNotificacionesNegocio.class);
                        context.startActivity(intent);
                        mActivity.finish();
                        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    });
                    alerta.setNegativeButton("Cancelar", (dialogInterface, i) -> {

                    });
                    alerta.setIcon(R.drawable.entregarapida);
                    alerta.show();


                });

                ArrayList<Negocio> negocioArray = new ArrayList<>();
                carritoArray.clear();
                ConsultarTabla consultarTabla = new ConsultarTabla(context);

                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                rvLista.setLayoutManager(layoutManager);
                rvLista.setAdapter(new AdaptadorCarrito(context,
                        consultarTabla.CarritoConsulta(carritoArray, clave, idUser,
                                "", "" + itemcomentarios.getTicket())
                        , mActivity, "tk"));

                String[] arreglo = new String[carritoArray.size()];
                int sum = 0;
                List<String> lista = new ArrayList<>();
                for (int i = 0; i < arreglo.length; i++) {
                    lista.add(carritoArray.get(i).getIdproveedor());
                    arreglo[i] = "" + Integer.parseInt(carritoArray.get(i).getCantidad()) * Integer.parseInt(carritoArray.get(i).getPrecio());
                    sum += Integer.parseInt(arreglo[i]);

                }
                LinearLayoutManager layoutManagerHorizontal
                        = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                rvNegocio.setLayoutManager(layoutManagerHorizontal);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    lista = lista.stream().distinct().collect(Collectors.toList());
                    lista.forEach(obj -> {
                        rvNegocio.setAdapter(new AdaptadorNegocioPerfil(
                                consultarTabla.NegocioConsulta(negocioArray, "Tienda", obj, "", "")
                                , context, "MDP", "ticket", "", carritoArray, mActivity));
                    });
                }
                AlertDialog.Builder alerta = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                //inflar layout
                alerta.setView(viewInput);
                //Mensaje
                alerta.setTitle(Html.fromHtml("<font color='#138CFE'>Ticket"));

                if (bandel.equals("ket")) {
                    alerta.setMessage(Html.fromHtml("<H6><font color='#45CE00'>" + "SubTotal: $" + sum + "</font></H6>"));

                } else
                    alerta.setMessage(Html.fromHtml("<H6><font color='#45CE00'>" + "SubTotal: $" + sum + "</font></H6>"));

                alerta.setIcon(R.drawable.factura);
                btnOk.setOnClickListener(view1 -> {
                    btnOk.setEnabled(false);
                    if (bandel.equals("ket")) {
                        Intent intent = new Intent(context, MainActivityNotificacionesNegocio.class);
                        context.startActivity(intent);
                        mActivity.finish();
                        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    } else {
                        Intent intent = new Intent(context, MainActivityHistorial.class);
                        context.startActivity(intent);
                        mActivity.finish();
                        mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }

                });
                alerta.show().getWindow().getDecorView().setBackground(mActivity.getResources().getDrawable(R.drawable.alertdegradado));//setBackgroundColor(Color.parseColor("#F1EEEE"));


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.itemView.setEnabled(true);
                        btnOk.setEnabled(true);
                    }
                }, 2000);
            }
        });
        SharedPreferences preferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        String idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");


        carritoArray.clear();
        String status = "", clave = "historial";
        if (bandel.equals("ket")) {

            clave = "historialProv";
            idUser = idNegocio;
        }
        ConsultarTabla consultarTabla = new ConsultarTabla(context);
        consultarTabla.CarritoConsulta(carritoArray, clave, idUser,
                "", "" + itemcomentarios.getTicket());
        String[] arreglo = new String[carritoArray.size()];
        for (int i = 0; i < arreglo.length; i++) {
            //Toast.makeText(context, ""+carritoArray.get(i).getTicket()+carritoArray.get(i).getSolicitud(), Toast.LENGTH_SHORT).show();
            if (carritoArray.get(i).getSolicitud().equals("Entregado")) {
                entrega++;
            }
            if (carritoArray.get(i).getSolicitud().equals("Tienda")) {
                tienda++;

            }
            if (carritoArray.get(i).getSolicitud().equals("Camino")) {
                camino++;

            }

        }
        // Toast.makeText(context, "id: "+itemcomentarios.getTicket()+" Tienda: "+ tienda+" Camino: "+camino+" Entrega: "+entrega, Toast.LENGTH_SHORT).show();
        if (tienda >= 1) {
            holder.iVStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.entregatime));
            status = "Preparando paquete";

        } else if (camino >= 1) {
            holder.iVStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.entregarapida));
            status = "Paquete Enviado";

        } else {
            holder.iVStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.entregapaquete));
            status = "Paquete Entregado";

        }
        tienda = camino = entrega = 0;

        int list = productoArrayList.size() - 1;

        holder.tv1.setText("" + itemcomentarios.getTicket());
        holder.tv2.setText("Compra: " + (list - position + 1));
        holder.tv3.setText("Status: " + status);
        holder.tv4.setText("Fecha: " + itemcomentarios.getFecha() + " Hora: " + itemcomentarios.getHora());

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productoArrayList.size();
    }
}
