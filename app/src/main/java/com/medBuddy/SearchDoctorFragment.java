package com.medBuddy;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchDoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDoctorFragment extends Fragment {

    TextInputEditText name, specialist, medicalname, location;
    MaterialButton searchdr;


    FirebaseFirestore firestore;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchDoctorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchDoctorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchDoctorFragment newInstance(String param1, String param2) {
        SearchDoctorFragment fragment = new SearchDoctorFragment();
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
        firestore = FirebaseFirestore.getInstance();

        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_search_doctor, container, false);
         name = view.findViewById(R.id.drname);
                specialist = view.findViewById(R.id.drspecialist);
                medicalname = view.findViewById(R.id.drmedical);
                location = view.findViewById(R.id.drlocation);
                searchdr = view.findViewById(R.id.drsearch);













                searchdr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



//                        /*
                        //get the value from input box
                        String drname = name.getText().toString();
                        String drspecialist = specialist.getText().toString();
                        String drmedical = medicalname.getText().toString();
                        String drlocation = location.getText().toString();
                        DrInfo dr = new DrInfo(drname, drspecialist, drmedical, drlocation);
//                        ArrayList<Object> data = new ArrayList<>();

//                        Log.d(TAG, "document.getId() + \" => \" + document.getData()");

                        //myRef.child("hk").setValue(dr);
//
//                         */


 /*
                    store data with uid

                        firestore.collection("users")
                                .document("Radha")
                                .set(dr.toMap())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getContext(), "success hk", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "failed hk", Toast.LENGTH_LONG).show();
                                    }
                                });
  */

//                        Query query, nameQuery, specialistQuery, medicalQuery, locationQuery;
//                        query = firestore.collection("users")
//                                        .whereEqualTo("validity", "true");
//                        if(!drname.equals("")) {
//                            Toast.makeText(getContext(), "hk here", Toast.LENGTH_LONG).show();
//                            query = query.whereEqualTo("drName", drname);
//                        }
//                        if(!drname.equals("")) query = query.whereEqualTo("drName", drname);
//                        if(!drmedical.equals("")) query = query.whereEqualTo("drMedical", drmedical);
//                        if(!drspecialist.equals("")) query = query.whereEqualTo("drSpecialist", drspecialist);
//
//                        if(!drlocation.equals("")) query = query.whereEqualTo("drLocation", drlocation);






///*
//please work with the file for get data from firestore
//                                query
//                                .get()
//                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(getContext(), "HK", Toast.LENGTH_LONG).show();
//
//                                            for (QueryDocumentSnapshot document : task.getResult()) {
////                                                Map<String, Object> datas = document.getData();
//                                                data.add(document.getData());
//                                                System.out.println("hk now size is: "+data.size());
//
////                                                Iterator<Map.Entry<String, String>> itr = datas.entrySet().iterator();
////                                                datas.forEach((k, v)->{
////
////                                                    System.out.println(k+" "+v);
////                                                });
//                                            }
//                                            System.out.println("the size of data is: "+data.size());
////                                            for(String dr : DrInfo.listName)
////                                            {
////                                                System.out.println(dr);
////                                            }
////                                            Toast.makeText(getContext(), "HK FROM FOR ext", Toast.LENGTH_LONG).show();
//                                        } else {
//                                            Log.d(TAG, "Error getting documents: ", task.getException());
//                                            Toast.makeText(getContext(), "HK failed", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });

// */











/*

//firebase realtime

                        ValueEventListener postListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                Toast.makeText(getContext(), "child is: "+dataSnapshot.getKey(), Toast.LENGTH_LONG).show();
                                Iterable<DataSnapshot> ob = dataSnapshot.getChildren();
                                Toast.makeText(getContext(), "HK get", Toast.LENGTH_SHORT).show();
                                Object object = dataSnapshot.getValue(Object.class);
//                                List<object> ob = dataSnapshot.getValue()
//                                DrInfo post = dataSnapshot.getValue(DrInfo.class);
//                                Map<String, DrInfo> ob = (Map<String, DrInfo>) dataSnapshot.getValue();
                                if(ob == null)
                                {
                                    Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    System.out.println("Hare Krishna");
                                    ob.forEach(data -> {
                                        System.out.println(data.getValue());
                                    });
                                    DrInfo dr = (DrInfo) dataSnapshot.child("hk").getValue(DrInfo.class);
                                    System.out.println("Krishna: "+ob);
//                                    String d = dataSnapshot.child("hk").getValue().toString();
                                    Toast.makeText(getContext(), "k: "+ dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();


                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Getting Post failed, log a message
                                Toast.makeText(getContext(), "HK failed", Toast.LENGTH_SHORT).show();
                            }
                        };

                        myRef.addValueEventListener(postListener);

 */
//                        Toast.makeText(getContext(), "size of arr is: "+DrInfo.listName., Toast.LENGTH_SHORT).show();
//                        System.out.println("HK my data is: ");
//                        for(int i = 0; i<data.size(); i++)
//                        {
//                            System.out.println(data.get(i));
//                        }
//                        for(String dr2 : DrInfo.listName)
//                        {
//                            System.out.println(dr2);
//                        }
                        Fragment fragment = new DrListFragment();
                        Bundle bndl = new Bundle();
                        bndl.putParcelable("drInfo", dr);
                        fragment.setArguments(bndl);
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