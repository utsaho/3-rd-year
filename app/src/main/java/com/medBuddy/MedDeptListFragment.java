package com.medBuddy;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedDeptListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedDeptListFragment extends Fragment implements View.OnClickListener
{
    private FloatingActionButton floatingActionButton;

    private TextView deptNameTextView;
    LinearLayout layout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MedDeptListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedDeptListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedDeptListFragment newInstance(String param1, String param2) {
        MedDeptListFragment fragment = new MedDeptListFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_med_dept_list, container, false);

        layout = view.findViewById(R.id.deptContainerId);

        floatingActionButton = view.findViewById(R.id.floatingButtonId);
        floatingActionButton.setOnClickListener(this);

        deptNameTextView = (TextView) view.findViewById(R.id.deptNameTextViewId);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.floatingButtonId)
        {
            AddDeptDialog addDeptDialog = new AddDeptDialog();
            addDeptDialog.linearLayout = layout;
            addDeptDialog.show(getActivity().getSupportFragmentManager(), null);
        }
    }
}