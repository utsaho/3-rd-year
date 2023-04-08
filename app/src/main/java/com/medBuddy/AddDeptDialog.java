package com.medBuddy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AddDeptDialog extends AppCompatDialogFragment
{
    private TextInputEditText deptName, deptHead, deptId, deptContact, deptEmail;

    private String dnText, dhText, diText, dcText, deText;

    private CardView cardView;

    static LinearLayout linearLayout;
    FirebaseFirestore firestore;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.add_dept_dialog_layout, null);

        deptName = (TextInputEditText) view.findViewById(R.id.deptNameId);
        deptHead = (TextInputEditText) view.findViewById(R.id.deptHeadId);
        deptId = (TextInputEditText) view.findViewById(R.id.medDeptId);
        deptContact = (TextInputEditText) view.findViewById(R.id.deptContactId);
        deptEmail = (TextInputEditText) view.findViewById(R.id.deptEmailId);

        builder.setView(view).setTitle("Add New Department")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(getActivity(), "No new department added", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dnText = deptName.getText().toString();
                        dhText = deptHead.getText().toString();
                        diText = deptId.getText().toString();
                        dcText = deptContact.getText().toString();
                        deText = deptEmail.getText().toString();

                        if(dnText.isEmpty()||dhText.isEmpty()||diText.isEmpty()||dcText.isEmpty()||
                                deText.isEmpty())
                            Toast.makeText(getActivity(), "Please complete all the fields",
                                    Toast.LENGTH_SHORT).show();
                        else
                        {
                            //Passing the input fields from the dialog
                            DeptInfo deptInfoObj = new DeptInfo(dnText, dhText, diText, dcText, deText);
                            addDeptNameToCard(deptInfoObj);
                        }
                    }
                });
        return builder.create();
    }

    @SuppressLint("MissingInflatedId")
    private void addDeptNameToCard(DeptInfo deptInfoObj)
    {
        View view = getLayoutInflater().inflate(R.layout.deptname_cardview_layout, null);

        CardView cardView = view.findViewById(R.id.depNameCardViewId);

        TextView textView = cardView.findViewById(R.id.deptNameTextViewId);

        textView.setText(deptInfoObj.deptName);

        linearLayout.addView(view);


        //firestore
//        firestore = FirebaseFirestore.getInstance();
//        Query query = firestore.collection("department")


        //cardView.setOnClickListener();
    }
}