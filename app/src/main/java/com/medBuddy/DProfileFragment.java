package com.medBuddy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "phnNmbr";
    FirebaseFirestore firestore;
    TextView headName;
    MaterialTextView name, des, dept, visD, vstime, vsfee, phnNmbr, email, medical;
    ShapeableImageView pImage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static String phn;

    public DProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DProfileFragment newInstance(String param1, String param2) {

        DProfileFragment fragment = new DProfileFragment();
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


        View view = inflater.inflate(R.layout.fragment_d_profile, container, false);

        headName = view.findViewById(R.id.headName);
        name = view.findViewById(R.id.drPName);
        des = view.findViewById(R.id.drPDes);
        dept = view.findViewById(R.id.drPDept);
        visD = view.findViewById(R.id.drPVDay);
        vstime = view.findViewById(R.id.drPVTIME);
        vsfee = view.findViewById(R.id.drPVFee);
        phnNmbr = view.findViewById(R.id.drPHl);
        email = view.findViewById(R.id.drPeml);
        medical = view.findViewById(R.id.drPMdcl);
        pImage = view.findViewById(R.id.drPImage);



//        fetch data using the nmbr


        firestore = FirebaseFirestore.getInstance();
//        Toast.makeText(getContext(), "Hare Krishna from toast"+phn, Toast.LENGTH_SHORT).show();

        DocumentReference docRef = firestore.collection("doctors").document(phn);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> data = document.getData();

                        System.out.println(data);
                        Toast.makeText(getContext(), "Hare Krishna from toast", Toast.LENGTH_SHORT).show();

                        //set profile
                        headName.setText(data.get("drName").toString());
                        name.setText(data.get("drName").toString());
                        des.setText(data.get("drDesignation").toString());
                        dept.setText(data.get("drDepartment").toString());
                        visD.setText(data.get("drVisitingDay").toString());
                        vstime.setText(data.get("drVisitingTime").toString());
                        vsfee.setText(data.get("drVisitingFee").toString());
                        phnNmbr.setText(data.get("drHelpLine").toString());
                        email.setText(data.get("drEmail").toString());
                        medical.setText(data.get("drMedical").toString());
                        Glide.with(getActivity()).load(data.get("drPhotoUrl").toString()).into(pImage);

                    } else {
                        Toast.makeText(getContext(), "Hare Krishna no data found", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getContext(), "get failed with "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}