package com.example.smarthome;

import android.os.Bundle;

import com.example.smarthome.ui.energycomponent.EnergyComponentFragment;
import com.example.smarthome.ui.gallery.GalleryFragment;
import com.example.smarthome.ui.home.HomeFragment;
import com.example.smarthome.ui.monthlyconsump.MonthlyGraphFragment;
import com.example.smarthome.ui.outletenergy.EnergyOutletFragment;
import com.example.smarthome.ui.send.SendFragment;
import com.example.smarthome.ui.share.ShareFragment;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private ImageView logo;
    private Toolbar toolbar;

    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        logo = findViewById(R.id.img_logo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        logo.setVisibility(View.INVISIBLE);
        switch (id) {
            case R.id.nav_home:
                toolbar.setTitle("Manual");
                HomeFragment manual = new HomeFragment();
                FragmentManager manager = getSupportFragmentManager();
                drawer.closeDrawers();
                manager.beginTransaction()
                        .replace(R.id.layout, manual)
                        .commit();
            break;
            case R.id.nav_gallery:
                toolbar.setTitle("Automatic");
                GalleryFragment automate = new GalleryFragment();
                FragmentManager manager1 = getSupportFragmentManager();
                drawer.closeDrawers();
                manager1.beginTransaction()
                        .replace(R.id.layout, automate)
                        .commit();
            break;
            case R.id.nav_share:
                toolbar.setTitle("Daily Power Consumption");
                ShareFragment powercon = new ShareFragment();
                FragmentManager manager2 = getSupportFragmentManager();
                drawer.closeDrawers();
                manager2.beginTransaction()
                        .replace(R.id.layout, powercon)
                        .commit();
            break;
            case R.id.nav_component:
                toolbar.setTitle("Light Consumption Graph");
                EnergyComponentFragment mpowercon = new EnergyComponentFragment();
                FragmentManager manager4 = getSupportFragmentManager();
                drawer.closeDrawers();
                manager4.beginTransaction()
                        .replace(R.id.layout, mpowercon)
                        .commit();
                break;
            case R.id.nav_outletcomponent:
                toolbar.setTitle("Component Consumption Graph");
                EnergyOutletFragment opowercon = new EnergyOutletFragment();
                FragmentManager manager6 = getSupportFragmentManager();
                drawer.closeDrawers();
                manager6.beginTransaction()
                        .replace(R.id.layout, opowercon)
                        .commit();
                break;
            case R.id.nav_send:
                toolbar.setTitle("Daily Consumption Graph");
                SendFragment congraph = new SendFragment();
                FragmentManager manager3 = getSupportFragmentManager();
                drawer.closeDrawers();
                manager3.beginTransaction()
                        .replace(R.id.layout, congraph)
                        .commit();
            break;
            case R.id.nav_monthly:
                toolbar.setTitle("Monthly Consumption Graph");
                MonthlyGraphFragment mcongraph = new MonthlyGraphFragment();
                FragmentManager manager5 = getSupportFragmentManager();
                drawer.closeDrawers();
                manager5.beginTransaction()
                        .replace(R.id.layout, mcongraph)
                        .commit();
            break;
        }
        return false;
    }
}
