package com.example.monsheeppractica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.monsheeppractica.Activitys.MainActivityLogin;
import com.example.monsheeppractica.Activitys.MainActivityRegistroCategorias;
import com.example.monsheeppractica.Activitys.MainActivityRegistroProductos;
import com.example.monsheeppractica.Activitys.MainActivityRegistroUsuario;
import com.example.monsheeppractica.databinding.ActivityMainBinding;
import com.example.monsheeppractica.fragments.HomeFragment;
import com.example.monsheeppractica.fragments.MonsheepFragment;
import com.example.monsheeppractica.fragments.NotificationsFragment;
import com.example.monsheeppractica.fragments.PerfilFragment;
import com.example.monsheeppractica.mytools.ConversoresAll;
import com.example.monsheeppractica.fragments.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import kotlin.jvm.internal.Ref;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    BottomNavigationView navView;
    FloatingActionButton fab;
    int id;
    ImageView ivSuperUser;
    String tipo_menu = "categoria", NombreUser, idUser, idFotoUser, tipoUser, idNegocio;

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

        SharedPreferences preferences = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        idUser = preferences.getString("idusers", "et_pass.getText().toString()");
        NombreUser = preferences.getString("NombreUser", "et_pass.getText().toString()");
        idFotoUser = preferences.getString("idFotoUser", "et_pass.getText().toString()");
        tipoUser = preferences.getString("tipouser", "et_pass.getText().toString()");
        idNegocio = preferences.getString("idNegocio", "et_pass.getText().toString()");


//        idUser =  getIntent().getStringExtra("idusers");
//        NombreUser =  getIntent().getStringExtra("NombreUser");
//        idFotoUser =  getIntent().getStringExtra("idFotoUser");
        //  Toast.makeText(this, ""+idUser+NombreUser+idFotoUser+" ID: "+idNegocio, Toast.LENGTH_SHORT).show();
        menu_bottom();
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
        ConversoresAll conversoresAll = new ConversoresAll();
        fab.setImageBitmap(conversoresAll.textAsBitmap("0", 14, Color.GRAY));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    switch (tipo_menu) {

                        case "categoria":
                            break;
                        case "busqueda":
                            break;

                        case "producto":
                            if (!idNegocio.equals("0") && !idNegocio.isEmpty()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivityRegistroProductos.class);
                                intent.putExtra("idusers", "" + idUser);
                                intent.putExtra("NombreUser", NombreUser);
                                intent.putExtra("idFotoUser", idFotoUser);
                                startActivity(intent);
                            }
                            break;
                        case "proveedor":
                            break;

                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void menu_bottom() {
//        navView.getOrCreateBadge(R.id.navigation_dashboard).setNumber(1000);
//        navView.getOrCreateBadge(R.id.navigation_home).setVisible(true);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                id = menuItem.getItemId();

                switch (id) {
                    case R.id.navigation_home:

                        MonsheepFragment fragmentm = new MonsheepFragment();
                        FragmentTransaction fragmentTransactionm = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionm.replace(R.id.nav_host_fragment_activity_main, fragmentm);
                        fragmentTransactionm.commit();
                        tipo_menu = "categoria";
                        break;
                    case R.id.navigation_notification:
                        HomeFragment fragmenth = new HomeFragment();
                        FragmentTransaction fragmentTransactionh = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionh.replace(R.id.nav_host_fragment_activity_main, fragmenth);
                        fragmentTransactionh.commit();
                        tipo_menu = "busqueda";
                        break;
                    case R.id.navigation_dashboard:
                        DashboardFragment fragmentd = new DashboardFragment();
                        FragmentTransaction fragmentTransactiond = getSupportFragmentManager().beginTransaction();
                        fragmentTransactiond.replace(R.id.nav_host_fragment_activity_main, fragmentd);
                        fragmentTransactiond.commit();
                        tipo_menu = "cliente";
                        break;
                    case R.id.navigation_productos:
                        PerfilFragment fragmentp = new PerfilFragment();
                        FragmentTransaction fragmentTransactionp = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionp.replace(R.id.nav_host_fragment_activity_main, fragmentp);
                        fragmentTransactionp.commit();
                        tipo_menu = "producto";
                        break;
                }
                return true;
            }
        });
        navView.setSelectedItemId(R.id.navigation_home);
    }


}