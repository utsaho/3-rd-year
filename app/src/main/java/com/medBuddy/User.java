package com.medBuddy;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class User extends AppCompatActivity {

//    User()
//    {
//        ft = fm.beginTransaction();
//    }

    DrawerLayout drawer;
    NavigationView navigate;
    Toolbar tool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();

        drawer = findViewById(R.id.drawer);
        navigate = findViewById(R.id.navigation);
        tool = findViewById(R.id.tool);

        setSupportActionBar(tool);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, tool, R.string.OpenDrawer, R.string.CloseDrawer);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
//        Toast.makeText(User.this, "Hare Krishna", Toast.LENGTH_LONG).show();
//        loadFragment(new DefaultFragment());
        loadFragment(new HomeFragment());
        navigate.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.home)
                {
                    loadFragment(new HomeFragment());
//                    Toast.makeText(User.this, "HK This from home", Toast.LENGTH_LONG).show();
                }
                else if(id == R.id.profile)
                {
                    loadFragment(new ProfileFragment());
//                    Toast.makeText(User.this, "HK This from profile", Toast.LENGTH_LONG).show();
                }
                else if(id == R.id.logout)
                {
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                        String provider = FirebaseAuth.getInstance().getCurrentUser().getProviderData().get(1).getProviderId();
                        if(provider.equals("google.com")){
//                            Toast.makeText(User.this, "Singed-out from Google", Toast.LENGTH_SHORT).show();
                            GoogleSignIn.getClient(getApplicationContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id)).requestEmail().build()).signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(User.this, "LogedOut", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(User.this, "Signed-out", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            if(provider.equals("google.com")){
//                            Toast.makeText(User.this, "Singed-out from Google", Toast.LENGTH_SHORT).show();
                                GoogleSignIn.getClient(getApplicationContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id)).requestEmail().build()).signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(User.this, "LogedOut", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
//                        FirebaseAuth.getInstance().signOut();
                    }
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                        Toast.makeText(User.this, "Still loged-in", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(User.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(User.this, LoginActivity.class));
                }
                else
                {
                    loadFragment(new DProfileFragment());
//                    Toast.makeText(User.this, "HK This from else", Toast.LENGTH_LONG).show();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }
    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    public void loadFragment(Fragment fr)
    {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, fr);
//        Toast.makeText(User.this, "Hare Krishna HK", Toast.LENGTH_LONG).show();
        ft.commit();
    }
}