package com.medBuddy;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BloodDonorListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BloodDonorListFragment extends Fragment {
    RecyclerView bldDonorlist;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseFirestore firestore;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BloodDonorListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BloodDonorListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BloodDonorListFragment newInstance(String param1, String param2) {
        BloodDonorListFragment fragment = new BloodDonorListFragment();
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
        View view = inflater.inflate(R.layout.fragment_blood_donor_list, container, false);

        firestore = FirebaseFirestore.getInstance();


        Bundle bundle = this.getArguments();
        UserInfo userinfo= bundle.getParcelable("userInfo");



        Query query;
        query = firestore.collection("user")
                .whereEqualTo("validity", "true");

//        if(!userinfo.UserBloodGroup.equals("")) {query = query.whereEqualTo("userBloodGroup", userinfo.UserName);}
//        if(!userinfo.UserLocation.equals("")) {query = query.whereEqualTo("userLocation", userinfo.UserLocation);}
//        if(!userinfo.medical.equals("")) {query = query.whereEqualTo("medical", userinfo.medical);}
        ArrayList<Object> data = new ArrayList<>();




        //query for search
        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(getContext(), "hk from for", Toast.LENGTH_SHORT).show();
                                data.add(document.getData());
                            }

                            AdapterBlood adptr = new AdapterBlood(getContext(), data);
//                            Toast.makeText(getContext(), "total item: "+data.size(), Toast.LENGTH_SHORT).show();

                            bldDonorlist = view.findViewById(R.id.bloodlist);
                            bldDonorlist.setHasFixedSize(true);
                            bldDonorlist.setLayoutManager(new LinearLayoutManager(getContext()));
                            bldDonorlist.setAdapter(adptr);

                        } else {
                            Toast.makeText(getContext(), "Error getting documents: "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });





//        AdapterBlood adptr = new AdapterBlood(getContext(), name, bldGrp);
//
//        bldDonorlist = view.findViewById(R.id.bloodlist);
//        bldDonorlist.setHasFixedSize(true);
//        bldDonorlist.setLayoutManager(new LinearLayoutManager(getContext()));
//        bldDonorlist.setAdapter(adptr);


        return view;
    }
}