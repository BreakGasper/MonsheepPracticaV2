package com.example.monsheeppractica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.monsheeppractica.Activitys.MainActivityCarrito;
import com.example.monsheeppractica.Activitys.MainActivityRegistroProductos;
import com.example.monsheeppractica.Activitys.MainActivitySuperUser;

import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.adaptadores.ViewPagerAdapter;
import com.example.monsheeppractica.databinding.ActivityMainBinding;
import com.example.monsheeppractica.fragments.HomeFragment;
import com.example.monsheeppractica.fragments.MonsheepFragment;
import com.example.monsheeppractica.fragments.PerfilFragment;
import com.example.monsheeppractica.mytools.ConversoresAll;
import com.example.monsheeppractica.fragments.DashboardFragment;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    BottomNavigationView navView;
    FloatingActionButton fab;
    int id;
    String c;
    ImageView ivSuperUser;
    String tipo_menu = "categoria", NombreUser, idUser, idFotoUser, tipoUser, idNegocio;
    ConversoresAll conversoresAll;
    SharedPreferences preferences;
    String art;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navView = findViewById(R.id.bottomNavigationView);
        ivSuperUser = findViewById(R.id.ivSuperUser);

        navView.setBackground(null);
        try {
            getSupportActionBar().hide();
            getActionBar().hide();
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
        }

        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");
        art = preferences.getString("art", "");
        // Toast.makeText(this, ""+art, Toast.LENGTH_SHORT).show();
//        idUser =  getIntent().getStringExtra("idusers");
//        NombreUser =  getIntent().getStringExtra("NombreUser");
//        idFotoUser =  getIntent().getStringExtra("idFotoUser");
        //  Toast.makeText(this, ""+idUser+NombreUser+idFotoUser+" ID: "+idNegocio, Toast.LENGTH_SHORT).show();

        ivSuperUser.setVisibility(View.GONE);
        if (tipoUser.equals("admin")) {
            ivSuperUser.setVisibility(View.VISIBLE);
        } else if (tipoUser.equals("basico")) {
            //  binding.fab.setVisibility(View.INVISIBLE);
        } else if (tipoUser.equals("1")) {
            // binding.fab.setVisibility(View.INVISIBLE);
        }

        ivSuperUser.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivitySuperUser.class);
            startActivity(intent);
        });


        fab = binding.fab;
        conversoresAll = new ConversoresAll();
        fab.setImageBitmap(conversoresAll.textAsBitmap("0", 16, Color.GRAY));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    SharedPreferences productoPreferences = getSharedPreferences("producto", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = productoPreferences.edit();
                    editor.putString("edicion", "0");
                    editor.putString("IdProducto", "0");
                    editor.apply();

                    SharedPreferences.Editor editorpreferences = preferences.edit();
                    editorpreferences.putString("idPro", "0").apply();
                    switch (tipo_menu) {

                        case "categoria":
                            Intent intent = new Intent(getApplicationContext(), MainActivityCarrito.class);

                            startActivity(intent);
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                            break;
                        case "busqueda":
                            Intent f = new Intent(getApplicationContext(), MainActivityCarrito.class);
                            startActivity(f);
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                            break;

                        case "producto":
                            if (!idNegocio.equals("0") && !idNegocio.isEmpty()) {
                                Intent h = new Intent(getApplicationContext(), MainActivityRegistroProductos.class);
                                h.putExtra("idusers", "" + idUser);
                                h.putExtra("NombreUser", NombreUser);
                                h.putExtra("idFotoUser", idFotoUser);
                                startActivity(h);
                                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                            }
                            break;
                        case "proveedor":
                            Intent g = new Intent(getApplicationContext(), MainActivityCarrito.class);
                            startActivity(g);
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                            break;

                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        aTrabajar();
        // menuGestos();
       menu_bottom();
    }

    public void ConsultarTicket(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String idUserq = preferences.getString("idusers", "et_pass.getText().toString()");
        ArrayList<Carrito> Array = new ArrayList<>();
        ConsultarTabla consultarTabla = new ConsultarTabla(context);
        consultarTabla.CarritoConsulta(Array, "count", idUserq, "", "");

        Toast.makeText(context, "Agregados: " + Array.size(), Toast.LENGTH_SHORT).show();
        c = "" + Array.size();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("art", c).commit();

    }

    private void aTrabajar() {
        new CountDownTimer(10, 100) {
            @Override
            public void onTick(long l) {

                //  art = preferences.getString("art", "0");
                // Toast.makeText(getApplicationContext(), "aa" + art, Toast.LENGTH_SHORT).show();
                //  fab.setImageBitmap(new ConversoresAll().textAsBitmap("" + art, 12, Color.GRAY));

            }

            @Override
            public void onFinish() {
                art = preferences.getString("art", "0");
                fab.setImageBitmap(new ConversoresAll().textAsBitmap("" + art, 16, Color.WHITE));
                // Toast.makeText(getContext(), "aa", Toast.LENGTH_SHORT).show();
                aTrabajar();

            }
        }.start();
    }
    private void menuGestos() {
        viewPager = binding.viewpagerInner;
        // setting up the adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        // add the fragments
        viewPagerAdapter.add(new MonsheepFragment(), "Page 1");
        viewPagerAdapter.add(new HomeFragment(), "Page 2");
        viewPagerAdapter.add(new DashboardFragment(), "Page 3");
        viewPagerAdapter.add(new PerfilFragment(), "Page 4");


        if (viewPagerAdapter.getPageTitle(0).equals("Page 1")){
            navView.setSelectedItemId(R.id.navigation_home);
        }else if (viewPagerAdapter.getPageTitle(1).equals("Page 2")){
            navView.setSelectedItemId(R.id.navigation_notification);
        }else if (viewPagerAdapter.getPageTitle(2).equals("Page 3")){
            navView.setSelectedItemId(R.id.navigation_dashboard);
        }else if (viewPagerAdapter.getPageTitle(3).equals("Page 4")){
            navView.setSelectedItemId(R.id.navigation_productos);
        }


        // Set the adapter
        viewPager.setAdapter(viewPagerAdapter);


    }
    public void menu_bottom() {
//        navView.getOrCreateBadge(R.id.navigation_dashboard).setNumber(1000);
//        navView.getOrCreateBadge(R.id.navigation_home).setVisible(true);
        navView.setOnNavigationItemSelectedListener(menuItem -> {
            id = menuItem.getItemId();

            switch (id) {
                case R.id.navigation_home:
                    MonsheepFragment fragmentm = new MonsheepFragment();
                    FragmentTransaction fragmentTransactionm = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionm.setCustomAnimations(R.anim.zoom_back_in, R.anim.zoom_back_out);
                    fragmentTransactionm.replace(R.id.nav_host_fragment_activity_main, fragmentm);
                    fragmentTransactionm.commit();
                    tipo_menu = "categoria";
                    break;
                case R.id.navigation_notification:
                    HomeFragment fragmenth = new HomeFragment();
                    FragmentTransaction fragmentTransactionh = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionh.setCustomAnimations(R.anim.zoom_back_in, R.anim.zoom_back_out);

                    fragmentTransactionh.replace(R.id.nav_host_fragment_activity_main, fragmenth);
                    fragmentTransactionh.commit();
                    tipo_menu = "busqueda";
                    break;
                case R.id.navigation_dashboard:
                    DashboardFragment fragmentd = new DashboardFragment();
                    FragmentTransaction fragmentTransactiond = getSupportFragmentManager().beginTransaction();
                    fragmentTransactiond.setCustomAnimations(R.anim.zoom_back_in, R.anim.zoom_back_out);

                    fragmentTransactiond.replace(R.id.nav_host_fragment_activity_main, fragmentd);
                    fragmentTransactiond.commit();
                    tipo_menu = "proveedor";
                    break;
                case R.id.navigation_productos:
                    PerfilFragment fragmentp = new PerfilFragment();
                    FragmentTransaction fragmentTransactionp = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionp.setCustomAnimations(R.anim.zoom_back_in, R.anim.zoom_back_out);

                    fragmentTransactionp.replace(R.id.nav_host_fragment_activity_main, fragmentp);
                    fragmentTransactionp.commit();
                    tipo_menu = "producto";
                    break;
            }
            return true;
        });
        navView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);

    }
}