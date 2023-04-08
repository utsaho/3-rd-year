package com.medBuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MedAuthHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout medUserDrawer; private NavigationView medUserNavView; private Toolbar medUserToolbar;
    private String HospitalID = "lFFKT3XBlu5umjWhw8E7";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_auth_home);

        medUserDrawer = (DrawerLayout) findViewById(R.id.medAuthUserDrawer);
        medUserNavView = (NavigationView) findViewById(R.id.medAuthUserNav);
        medUserToolbar = (Toolbar) findViewById(R.id.tool);

        //Set toolbar as actionbar
        //setSupportActionBar(medUserToolbar);

        @SuppressLint("ResourceType") ActionBarDrawerToggle medUserToggle = new ActionBarDrawerToggle(this,
                 medUserDrawer, medUserToolbar, R.id.navigation, R.id.navigation);

        medUserDrawer.addDrawerListener(medUserToggle);
        medUserToggle.syncState();
        medUserNavView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content, new MedAuthHomeFragment()).commit();
    }

    @Override
    public void onBackPressed()
    {
        if(medUserDrawer.isDrawerOpen(GravityCompat.START)) medUserDrawer.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    //For selecting navigation menu items
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId()==R.id.profile)
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new MedAuthProfileFragment()).commit();
        else if(item.getItemId()==R.id.home)
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new MedAuthHomeFragment()).commit();
        else if(item.getItemId()==R.id.logout)
        {
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
        }
        medUserDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}