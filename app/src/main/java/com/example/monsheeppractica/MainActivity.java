package com.example.monsheeppractica;

import static com.example.monsheeppractica.WebService.wsDataDownload.NombreTablas;
import static com.example.monsheeppractica.mytools.Network.isNetDisponible;
import static com.example.monsheeppractica.mytools.Network.isOnlineNet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.monsheeppractica.Activitys.MainActivityAjustes;
import com.example.monsheeppractica.Activitys.MainActivityCarrito;
import com.example.monsheeppractica.Activitys.MainActivityHistorial;
import com.example.monsheeppractica.Activitys.MainActivityLogin;
import com.example.monsheeppractica.Activitys.MainActivityNotificacionesNegocio;
import com.example.monsheeppractica.Activitys.MainActivityRegistroProductos;
import com.example.monsheeppractica.Activitys.MainActivitySuperUser;

import com.example.monsheeppractica.GetterAndSetter.Carrito;
import com.example.monsheeppractica.WebService.wsDataDownload;
import com.example.monsheeppractica.adaptadores.ViewPagerAdapter;
import com.example.monsheeppractica.databinding.ActivityMainBinding;
import com.example.monsheeppractica.fragments.HomeFragment;
import com.example.monsheeppractica.fragments.MonsheepFragment;
import com.example.monsheeppractica.fragments.PerfilFragment;
import com.example.monsheeppractica.mytools.ConversoresAll;
import com.example.monsheeppractica.fragments.DashboardFragment;
import com.example.monsheeppractica.sqlite.registros.ConsultarTabla;
import com.example.monsheeppractica.sqlite.sqlite;
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
    CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navView = findViewById(R.id.bottomNavigationView);
        ivSuperUser = findViewById(R.id.ivSuperUser);

        navView.setBackground(null);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setLogo(R.drawable.mcolorm);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#282828'><small>\tMoonsheep</small></font>"));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.topbarbackground));


        preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");
        art = preferences.getString("art", "");

        ivSuperUser.setVisibility(View.GONE);


        ivSuperUser.setOnClickListener(view -> {

            ivSuperUser.setEnabled(false);

        });


        fab = binding.fab;
        conversoresAll = new ConversoresAll();
        //fab.setImageBitmap(conversoresAll.textAsBitmap("0", 16, Color.GRAY));
        Consulta();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fab.setEnabled(false);


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
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fab.setEnabled(true);

                        }
                    }, 2000);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });

         aTrabajar();
        // menuGestos();
       menu_bottom();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.overflow_basic, menu);;

        if (tipoUser.equals("admin")) {
           // ivSuperUser.setVisibility(View.VISIBLE);
            getMenuInflater().inflate(R.menu.overflow_admin, menu);
        } else if (tipoUser.equals("basico")) {
            //  binding.fab.setVisibility(View.INVISIBLE)
            getMenuInflater().inflate(R.menu.overflow_basic, menu);;
        } else if (tipoUser.equals("1")) {
            getMenuInflater().inflate(R.menu.overflow_negocio, menu);;

            // binding.fab.setVisibility(View.INVISIBLE);
        }



        return true;
    }
    //Metodo para asignar las funciones
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if (id == R.id.item_car_admin) {
            item.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), MainActivityHistorial.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    item.setEnabled(true);

                }
            }, 2000);
        }
        if (id == R.id.item_setting_admin) {
            item.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), MainActivityAjustes.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    item.setEnabled(true);

                }
            }, 2000);
        }
        if (id == R.id.item_super_admin) {
            item.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), MainActivitySuperUser.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    item.setEnabled(true);

                }
            }, 2000);

        }
        if (id == R.id.item_car_basic) {
            item.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), MainActivityHistorial.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    item.setEnabled(true);

                }
            }, 2000);
        }
        if (id == R.id.item_setting_basic) {
            item.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), MainActivityAjustes.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    item.setEnabled(true);

                }
            }, 2000);
        }

        if (id == R.id.item_car_negocio) {
            item.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), MainActivityHistorial.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    item.setEnabled(true);

                }
            }, 2000);
        }
        if (id == R.id.item_setting_negocio) {
            item.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), MainActivityAjustes.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    item.setEnabled(true);

                }
            }, 2000);
        }
        if (id == R.id.item_pedidos_negocio) {
            item.setEnabled(false);
            Intent intent = new Intent(getApplicationContext(), MainActivityNotificacionesNegocio.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    item.setEnabled(true);

                }
            }, 2000);
        }

        return super.onOptionsItemSelected(item);
    }


    public void ConsultarTicket(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String idUserq = preferences.getString("idusers", "et_pass.getText().toString()");
        ArrayList<Carrito> Array = new ArrayList<>();
        ConsultarTabla consultarTabla = new ConsultarTabla(context);
        consultarTabla.CarritoConsulta(Array, "counts", idUserq, "", "");

       // Toast.makeText(context, "Agregados: " + Array.size(), Toast.LENGTH_SHORT).show();
        c = "" + Array.size();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("art", c).commit();

    }

    public void Consulta(){
        SharedPreferences preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        String idUserq = preferences.getString("idusers", "et_pass.getText().toString()");
        ArrayList<Carrito> Array = new ArrayList<>();
        ConsultarTabla consultarTabla = new ConsultarTabla(this);
        consultarTabla.CarritoConsulta(Array, "counts", idUserq, "", "");

        // Toast.makeText(context, "Agregados: " + Array.size(), Toast.LENGTH_SHORT).show();
        c = "" + Array.size();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("art", c).commit();
    }



    private void aTrabajar() {
         countDownTimer = new CountDownTimer(10, 100) {
            @Override
            public void onTick(long l) {

                //  art = preferences.getString("art", "0");
                // Toast.makeText(getApplicationContext(), "aa" + art, Toast.LENGTH_SHORT).show();
                //  fab.setImageBitmap(new ConversoresAll().textAsBitmap("" + art, 12, Color.GRAY));

            }

            @Override
            public void onFinish() {
                art = preferences.getString("art", "0");
                if (tipo_menu.equals("producto")){
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.plus));
                }else if (art.equals("0")){
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.shop
                    ));

                }else {
                    fab.setImageBitmap(new ConversoresAll().textAsBitmap("" + art, 30, Color.WHITE));

                }
                //fab.setImageDrawable(getResources().getDrawable(R.drawable.ajuste));
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
                    fragmentTransactionm.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                    // fragmentTransactionm.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    fragmentTransactionm.replace(R.id.nav_host_fragment_activity_main, fragmentm);
                    fragmentTransactionm.commit();
                    tipo_menu = "categoria";
                    break;
                case R.id.navigation_notification:
                    HomeFragment fragmenth = new HomeFragment();
                    FragmentTransaction fragmentTransactionh = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionh.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);//setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    fragmentTransactionh.replace(R.id.nav_host_fragment_activity_main, fragmenth);
                    fragmentTransactionh.commit();
                    tipo_menu = "busqueda";
                    break;
                case R.id.navigation_dashboard:
                    DashboardFragment fragmentd = new DashboardFragment();
                    FragmentTransaction fragmentTransactiond = getSupportFragmentManager().beginTransaction();
                    fragmentTransactiond.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);//.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    fragmentTransactiond.replace(R.id.nav_host_fragment_activity_main, fragmentd);
                    fragmentTransactiond.commit();
                    tipo_menu = "proveedor";
                    break;
                case R.id.navigation_productos:
                    PerfilFragment fragmentp = new PerfilFragment();
                    FragmentTransaction fragmentTransactionp = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionp.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);//.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
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
        Intent intent = new Intent(this, MainActivityLogin.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);


    }
}