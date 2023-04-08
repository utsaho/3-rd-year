package com.medBuddy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedAuthHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedAuthHomeFragment extends Fragment implements View.OnClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Declared
    private CardView deptCardView, bbCardView;

    public MedAuthHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedAuthHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedAuthHomeFragment newInstance(String param1, String param2) {
        MedAuthHomeFragment fragment = new MedAuthHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_med_auth_home, container, false);

        deptCardView = (CardView) view.findViewById(R.id.deptCardId);
        bbCardView = (CardView) view.findViewById(R.id.bbCardId);

        deptCardView.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.deptCardId)
        {
            Fragment deptListFragment = new MedDeptListFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction deptListTran = fragmentManager.beginTransaction();
            deptListTran.replace(R.id.content,deptListFragment);
            deptListTran.addToBackStack(null);
            deptListTran.commit();
        }
        else
        {

        }
    }
}