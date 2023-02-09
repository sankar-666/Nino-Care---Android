package com.example.ninocare;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ninocare.databinding.ActivityMainHomeBinding;

public class MainHome extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full sc


        binding = ActivityMainHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        setSupportActionBar(binding.appBarMainHome.toolbar);
        binding.appBarMainHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.managebabie)
                {
                    startActivity(new Intent(getApplicationContext(),Managebabies.class));
                }
                else if(item.getItemId()==R.id.viewvideos)
                {
                    startActivity(new Intent(getApplicationContext(),ViewVideos.class));
                }
                else if(item.getItemId()==R.id.noti)
                {
                    startActivity(new Intent(getApplicationContext(),ViewNotifications.class));
                } else if(item.getItemId()==R.id.doc)
                {
                    startActivity(new Intent(getApplicationContext(),ViewDoctors.class));
                } else if(item.getItemId()==R.id.asha)
                {
                    startActivity(new Intent(getApplicationContext(),ViewAshaWorker.class));
                } else if(item.getItemId()==R.id.appointment)
                {
                    startActivity(new Intent(getApplicationContext(),ViewMyAppointments.class));
                } else if(item.getItemId()==R.id.prec)
                {
                    startActivity(new Intent(getApplicationContext(),ViewPrecuations.class));
                }else if(item.getItemId()==R.id.vaccinereq)
                {
                    startActivity(new Intent(getApplicationContext(),ViewVaccinations.class));
                }else if(item.getItemId()==R.id.feedback)
                {
                    startActivity(new Intent(getApplicationContext(),Feedback.class));
                }else if(item.getItemId()==R.id.logout)
                {
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }



                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}