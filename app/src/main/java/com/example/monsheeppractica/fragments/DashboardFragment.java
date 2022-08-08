package com.example.monsheeppractica.fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.monsheeppractica.GetterAndSetter.Clientes;
import com.example.monsheeppractica.Activitys.MainActivityRegistroUsuario;
import com.example.monsheeppractica.GetterAndSetter.Negocio;
import com.example.monsheeppractica.GetterAndSetter.Seguidores;
import com.example.monsheeppractica.Activitys.MainActivityPerfilUser;
import com.example.monsheeppractica.R;
import com.example.monsheeppractica.adaptadores.ListaClientesAdaptador;
import com.example.monsheeppractica.databinding.FragmentDashboardBinding;
import com.example.monsheeppractica.sqlite.DatabaseHandler;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.sqlite;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    View vista;
    DatabaseHandler db;
    AlertDialog.Builder alerta;
    ArrayList<Negocio> clientesArrayList = new ArrayList<>();
    ListView lv_clientes;
    String busqueda_index;
    String NombreUser, idUser, idFotoUser, tipoUser, idNegocio;
    ArrayList<Seguidores> listaseguir;

    String Nombre, ApellidoPaterno, ApellidoMaterno, TipoCompra, Calle, Numero, Interior, Codigo_Postal, Colonia,
            Municipio, Estado, Lada, Teléfono, status, tipoTelefono, Alias, Id_Foto;
    int id_cliente;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        vista = inflater.inflate(R.layout.fragment_dashboard, container, false);

//        lv_clientes = binding.lvClientesSeguir;
        lv_clientes = vista.findViewById(R.id.lvClientesSeguir);
        db = new DatabaseHandler(getActivity());
        listaseguir = new ArrayList<>();
        //registerForContextMenu(lv_clientes);
        SharedPreferences preferences = getActivity().getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");

        // prueba();
        listaproducto();

        Bundle datos = getArguments();
        // Toast.makeText(getContext(), "bde: "+datos, Toast.LENGTH_SHORT).show();
        if (datos != null) {

            busqueda_index = datos.getString("message", "0");
            // Toast.makeText(getContext(), "mundo: "+busqueda_index, Toast.LENGTH_SHORT).show();
            //   clearData();
            if (busqueda_index == null || busqueda_index.isEmpty()) {
                listaproducto();
            } else Buscar_categoria(busqueda_index);

        }

        return vista;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_contextual_proveedor, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int numero = item.getItemId();
        Intent intent = new Intent(getContext(), MainActivityRegistroUsuario.class);
        switch (numero) {

            case R.id.item_Editar_pro:

                //  Toast.makeText(getContext(), "Editar: Producto"+categoria_nombre, Toast.LENGTH_SHORT).show();

                intent.putExtra("editar", "editar");
                intent.putExtra("id_cliente", "" + id_cliente);
                intent.putExtra("TipoCompra", TipoCompra);
                intent.putExtra("ApellidoMaterno", ApellidoMaterno);
                intent.putExtra("ApellidoPaterno", ApellidoPaterno);
                intent.putExtra("Nombre", "" + Nombre);
                intent.putExtra("Calle", Calle);
                intent.putExtra("Numero", Numero);
                intent.putExtra("Interior", Interior);
                intent.putExtra("Codigo_Postal", Codigo_Postal);
                intent.putExtra("Colonia", Colonia);
                intent.putExtra("Municipio", Municipio);
                intent.putExtra("Estado", Estado);
                intent.putExtra("Alias", Alias);
                intent.putExtra("Lada", Lada);
                intent.putExtra("Teléfono", Teléfono);
                intent.putExtra("TipoTelefono", tipoTelefono);
                intent.putExtra("Id_Foto", "" + Id_Foto);
                startActivity(intent);

                return true;
            case R.id.item_Eliminar_pro:
                //  Toast.makeText(getContext(), "Eliminar: "+codigo, Toast.LENGTH_SHORT).show();
                Alert();
                return true;
            case R.id.item_detalle_pro:
                // Intent intent = new Intent(getContext(), MainActivity_Registrar_Producto.class);
                intent.putExtra("editar", "detalle");
                intent.putExtra("id_cliente", "" + id_cliente);
                intent.putExtra("TipoCompra", TipoCompra);
                intent.putExtra("ApellidoMaterno", ApellidoMaterno);
                intent.putExtra("ApellidoPaterno", ApellidoPaterno);
                intent.putExtra("Nombre", "" + Nombre);
                intent.putExtra("Calle", Calle);
                intent.putExtra("Numero", Numero);
                intent.putExtra("Interior", Interior);
                intent.putExtra("Codigo_Postal", Codigo_Postal);
                intent.putExtra("Colonia", Colonia);
                intent.putExtra("Municipio", Municipio);
                intent.putExtra("Estado", Estado);
                intent.putExtra("Alias", Alias);
                intent.putExtra("Lada", Lada);
                intent.putExtra("Teléfono", Teléfono);
                intent.putExtra("TipoTelefono", tipoTelefono);
                intent.putExtra("Id_Foto", "" + Id_Foto);
                startActivity(intent);
                return true;

            case R.id.item_Llamar:
                Modificar_Tipo();
//                Intent intents = new Intent(Intent.ACTION_CALL);
//                intents.setData(Uri.parse("tel:"+Lada+Teléfono));
//                startActivity(intents);

                return true;


        }

        return super.onContextItemSelected(item);


    }


    public void Modificar_Eliminacion() {
        sqlite admin = new sqlite
                (getActivity(), "monsheep", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("status", "baja");

        int cantid = BaseDeDatos.update("clientes", registro, "id_cliente= " + id_cliente, null);

        BaseDeDatos.close();

        if (cantid == 1) {
            //aTrabajar();
            Toast.makeText(getContext(), "Eliminada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Categoria no existe", Toast.LENGTH_SHORT).show();
        }

    }

    public void Modificar_Tipo() {
        sqlite admin = new sqlite
                (getActivity(), "monsheep", null, 1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();


        registro.put("TipoCompra", "admin");

        int cantid = BaseDeDatos.update("clientes", registro, "id_cliente= " + id_cliente, null);

        BaseDeDatos.close();

        if (cantid == 1) {
            //aTrabajar();
            Toast.makeText(getContext(), "actualizada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Categoria no existe", Toast.LENGTH_SHORT).show();
        }

    }

    private void Alert() {
        alerta = new AlertDialog.Builder(getActivity());
        //Mensaje
        alerta.setTitle(Html.fromHtml("<font color='#269BF4'>Mensaje informativo</font>"));
        //alerta.setTitle("Mensaje informativo");
        alerta.setMessage("¿Estas seguro que desea eliminar " + Nombre + "?");

        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Mensaje 1
            }
        });

        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Mensaje 2
                Modificar_Eliminacion();
                listaproducto();

            }
        });

        alerta.show();
    }


    public void clearData() {
        // limpiar la lista
        clientesArrayList.clear();
    }

    public void prueba() {
        ConsultarTabla consultarTabla = new ConsultarTabla(getActivity());

        lv_clientes.setAdapter(new ListaClientesAdaptador.listaP(getContext(),
                consultarTabla.ClientesConsulta(clientesArrayList, "all",
                        0, "")));


    }

    // llenar lista por producto
    public void listaproducto() {
        clearData();
        ConsultarTabla consultarTabla = new ConsultarTabla(getActivity());

        try {
            String[] arreglo = new String[consultarTabla.SeguirConsulta(listaseguir, "userlist", idUser, "idPerfil", "namePerfil").size()];
            if (arreglo.length==0){
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewInput = inflater.inflate(R.layout.toast_layout, null, false);

                TextView text = (TextView) viewInput.findViewById(R.id.text12);
                text.setText("Aun no sigues a  ningun negocio");

                Toast toast = new Toast(getActivity());
                //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(viewInput);
                toast.show();
            }
            for (int i = 0; i < arreglo.length; i++) {


                String hola = listaseguir.get(i).getIdSeguidor();
                // Toast.makeText(getActivity(), ""+hola, Toast.LENGTH_SHORT).show();
                lv_clientes.setAdapter(new ListaClientesAdaptador.listaP(getContext(),
                        consultarTabla.NegocioConsulta(clientesArrayList, "like",
                                hola, "", "")));
            }

            lv_clientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    id_cliente = clientesArrayList.get(i).getIdNegocio();//Int
                    TipoCompra = clientesArrayList.get(i).getTipoCompra();
//                ApellidoMaterno = clientesArrayList.get(i).getApellidoMaterno();
//                ApellidoPaterno = clientesArrayList.get(i).getApellidoPaterno();
                    Nombre = clientesArrayList.get(i).getNombre();
                    Calle = clientesArrayList.get(i).getCalle();
                    Numero = clientesArrayList.get(i).getNumero();
                    Interior = clientesArrayList.get(i).getInterior();
                    Codigo_Postal = clientesArrayList.get(i).getCodigo_Postal();
                    Colonia = clientesArrayList.get(i).getColonia();
                    Municipio = clientesArrayList.get(i).getMunicipio();
                    Estado = clientesArrayList.get(i).getEstado();
//                Alias = clientesArrayList.get(i).getAlias();
                    Lada = clientesArrayList.get(i).getLada();
                    Teléfono = clientesArrayList.get(i).getTeléfono();
                    status = clientesArrayList.get(i).getStatus();//Int
                    tipoTelefono = clientesArrayList.get(i).getTipoTelefono();
                    Id_Foto = clientesArrayList.get(i).getId_Foto();
                    lv_clientes.setEnabled(false);

                    Intent intent = new Intent(getContext(), MainActivityPerfilUser.class);
                    intent.putExtra("idPerfil", "" + id_cliente);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lv_clientes.setEnabled(true);

                        }
                    }, 2000);
                }
            });
            lv_clientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

//                id_cliente = clientesArrayList.get(i).getId_cliente();//Int
                    TipoCompra = clientesArrayList.get(i).getTipoCompra();
//                ApellidoMaterno = clientesArrayList.get(i).getApellidoMaterno();
//                ApellidoPaterno = clientesArrayList.get(i).getApellidoPaterno();
                    Nombre = clientesArrayList.get(i).getNombre();
                    Calle = clientesArrayList.get(i).getCalle();
                    Numero = clientesArrayList.get(i).getNumero();
                    Interior = clientesArrayList.get(i).getInterior();
                    Codigo_Postal = clientesArrayList.get(i).getCodigo_Postal();
                    Colonia = clientesArrayList.get(i).getColonia();
                    Municipio = clientesArrayList.get(i).getMunicipio();
                    Estado = clientesArrayList.get(i).getEstado();
//                Alias = clientesArrayList.get(i).getAlias();
                    Lada = clientesArrayList.get(i).getLada();
                    Teléfono = clientesArrayList.get(i).getTeléfono();
                    status = clientesArrayList.get(i).getStatus();//Int
                    tipoTelefono = clientesArrayList.get(i).getTipoTelefono();
                    Id_Foto = clientesArrayList.get(i).getId_Foto();


                    return false;
                }
            });

        } catch (Exception e) {
            Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
        }

    }

    public void Buscar_categoria(String palabra) {
       /* clearData();
        sqlite bh = new sqlite
                (getContext(), "clientes", null, 1);
        if (bh != null) {

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor c = db.rawQuery("select * from clientes where status='Activo' and Nombre LIKE '"+palabra+ "%' or Calle LIKE '"+palabra+"%'", null);

            if (c.moveToFirst()) {

                do {
//                    clientesArrayList.add(new Negocio(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(4),c.getString(5), c.getString(6),c.getString(7),c.getString(8), c.getString(9),c.getString(10),c.getString(11),c.getString(12),c.getString(13),c.getString(14),c.getString(15),c.getString(16),c.getString(17),c.getString(18),c.getString(19)));

                } while (c.moveToNext());

            }

            String[] arreglo = new String[clientesArrayList.size()];

            for (int i = 0; i < arreglo.length; i++) {


                //  arreglo[i] = productoArrayList.get(i).getCategoria() + productoArrayList.get(i).getDescripcion() + productoArrayList.get(i).getStatus();

            }

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arreglo);
            //lv_lista.setAdapter(adaptador);
//            lv_clientes.setAdapter(new ListaClientesAdaptador.listaP(getContext(),clientesArrayList));
            lv_clientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


//                    id_cliente = clientesArrayList.get(i).getId_cliente();//Int
                    TipoCompra = clientesArrayList.get(i).getTipoCompra();
//                    ApellidoMaterno = clientesArrayList.get(i).getApellidoMaterno();
//                    ApellidoPaterno = clientesArrayList.get(i).getApellidoPaterno();
                    Nombre = clientesArrayList.get(i).getNombre();
                    Calle = clientesArrayList.get(i).getCalle();
                    Numero = clientesArrayList.get(i).getNumero();
                    Interior = clientesArrayList.get(i).getInterior();
                    Codigo_Postal = clientesArrayList.get(i).getCodigo_Postal();
                    Colonia = clientesArrayList.get(i).getColonia();
                    Municipio = clientesArrayList.get(i).getMunicipio();
                    Estado = clientesArrayList.get(i).getEstado();
//                    Alias = clientesArrayList.get(i).getAlias();
                    Lada = clientesArrayList.get(i).getLada();
                    Teléfono = clientesArrayList.get(i).getTeléfono();
                    status = clientesArrayList.get(i).getStatus();//Int
                    tipoTelefono = clientesArrayList.get(i).getTipoTelefono();
                    Id_Foto = clientesArrayList.get(i).getId_Foto();

                    return false;
                }
            });

        }*/
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}