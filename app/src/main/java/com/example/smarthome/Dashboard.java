package com.example.smarthome;

import android.os.Bundle;

import com.example.smarthome.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navigationView.setNavigationItemSelectedListener(this);
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

        switch (id) {
            case R.id.nav_home:
                HomeFragment manual = new HomeFragment();
                FragmentManager manager = getSupportFragmentManager();
                drawer.closeDrawers();
                manager.beginTransaction()
                        .replace(R.id.layout, manual)
                        .commit();
            break;
            case R.id.nav_gallery:
                GalleryFragment automate = new GalleryFragment();
                FragmentManager manager = getSupportFragmentManager();
                drawer.closeDrawers();
                manager.beginTransaction()
                        .replace(R.id.layout, automate)
                        .commit();
            break;
            case R.id.nav_share:
                ShareFragment powercon = new ShareFragment();
                FragmentManager manager = getSupportFragmentManager();
                drawer.closeDrawers();
                manager.beginTransaction()
                        .replace(R.id.layout, powercon)
                        .commit();
            break;
            case R.id.nav_send:
                SendFragment congraph = new SendFragment();
                FragmentManager manager = getSupportFragmentManager();
                drawer.closeDrawers();
                manager.beginTransaction()
                        .replace(R.id.layout, congraph)
                        .commit();
            break;
        }
        return false;
    }
}
