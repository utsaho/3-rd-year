package com.medBuddy;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrListFragment extends Fragment {

    FirebaseFirestore firestore;
    RecyclerView drlist;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DrListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrListFragment newInstance(String param1, String param2) {

        DrListFragment fragment = new DrListFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dr_list, container, false);

        Fragment fragment = new DProfileFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        firestore = FirebaseFirestore.getInstance();
        ArrayList<Object> data = new ArrayList<>();
        Bundle bundle = this.getArguments();
        DrInfo drInfo= bundle.getParcelable("drInfo");



        Query query;
        query = firestore.collection("doctors")
                                        .whereEqualTo("validity", "true");



        if(!drInfo.drName.equals("")) {query = query.whereEqualTo("drName", drInfo.drName);}
        if(!drInfo.drLocation.equals("")) {query = query.whereEqualTo("drLocation", drInfo.drLocation);}
        if(!drInfo.drMedical.equals("")) {query = query.whereEqualTo("drMedical", drInfo.drMedical);}
        if(!drInfo.drSpecialist.equals("")) {query = query.whereEqualTo("drSpecialist", drInfo.drSpecialist);}







///*
//please work with the file for get data from firestore
                                query
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "HK", Toast.LENGTH_LONG).show();

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                data.add(document.getData());
                                            }
                                            Toast.makeText(getContext(), "size is: "+data.size(), Toast.LENGTH_SHORT).show();





                                            DrListAdapter adptr = new DrListAdapter(getContext(), data, fragmentTransaction, fragment);

                                            drlist = view.findViewById(R.id.drlist);
                                            drlist.setHasFixedSize(true);
                                            drlist.setLayoutManager(new LinearLayoutManager(getContext()));
                                            drlist.setAdapter(adptr);













//                                            if(current_data>=1)
//                                            {
//                                                view.findViewById(R.id.card1).setVisibility(View.VISIBLE);
//                                                Map<String, String> map = (Map<String, String>) data.get(current_page*0);
//                                                text1.setVisibility(View.VISIBLE);
//                                                text1.setText(map.get("drName"));
//                                            }
//                                            else{
//                                                view.findViewById(R.id.card1).setVisibility(View.INVISIBLE);
//                                            }
//                                            if(current_data>=2) {
//                                                view.findViewById(R.id.card2).setVisibility(View.VISIBLE);
//                                                Map<String, String> map = (Map<String, String>) data.get(current_page * 1);
//                                                text2.setText(map.get("drName"));
//                                            }else{
//                                                view.findViewById(R.id.card2).setVisibility(View.INVISIBLE);
//                                            }
//                                            if(current_data>=3) {
//                                                view.findViewById(R.id.card3).setVisibility(View.VISIBLE);
//                                                Map<String, String> map = (Map<String, String>) data.get(current_page*2);
//                                                text2.setText(map.get("drName"));
//                                            }else{
//                                                view.findViewById(R.id.card3).setVisibility(View.INVISIBLE);
//                                            }
//                                            if(current_data>=4) {
//                                                view.findViewById(R.id.card4).setVisibility(View.VISIBLE);
//                                                Map<String, String> map = (Map<String, String>) data.get(current_page*3);
//                                                text2.setText(map.get("drName"));
//                                            }else{
//                                                view.findViewById(R.id.card4).setVisibility(View.INVISIBLE);
//                                            }



                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                            Toast.makeText(getContext(), "HK failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

// */










        return view;
    }
}