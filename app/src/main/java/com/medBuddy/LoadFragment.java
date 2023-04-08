package com.medBuddy;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LoadFragment{
    public void loadFragment(Fragment fr)
    {
        Fragment fragment = new SearchDoctorFragment();
        FragmentManager fragmentManager = fragment.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
//        FragmentManager fm = fr.getActivity().getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.content, fr);
//        ft.commit();
    }
}
