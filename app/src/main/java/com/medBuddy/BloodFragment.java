package com.medBuddy;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BloodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BloodFragment extends Fragment {
    AppCompatAutoCompleteTextView getBlood;
    TextInputEditText bldlocation, bldmedical;
    String path;
    Uri uri;
    private ShapeableImageView captureImage;
    MaterialButton btn;
    AutoCompleteTextView bldgrps;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BloodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BloodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BloodFragment newInstance(String param1, String param2) {
        BloodFragment fragment = new BloodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_blood, container, false);

        bldlocation = view.findViewById(R.id.bldLocation);
        bldmedical = view.findViewById(R.id.bldmedical);



        bldgrps = view.findViewById(R.id.bloodgrps);
        String[] bloodGrps = getResources().getStringArray(R.array.blood_group);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, bloodGrps);
        bldgrps.setAdapter(adapter);





        btn = view.findViewById(R.id.searchBlood);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bldgrp = bldgrps.getText().toString();
                String location = bldlocation.getText().toString();
                String medical = bldmedical.getText().toString();
//                Toast.makeText(getContext(), "hare Krishna", Toast.LENGTH_SHORT).show();
                UserInfo user = new UserInfo(bldgrp, location, medical);


                Fragment fragment = new BloodDonorListFragment();
                Bundle bndl = new Bundle();
                bndl.putParcelable("userInfo", user);
                fragment.setArguments(bndl);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.content, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();


//                Bundle bndl = new Bundle();
//                bndl.putParcelable("drInfo", dr);
//                fragment.setArguments(bndl);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }
}