package com.medBuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {


    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //It's been quite fun
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /// ----------------------------------utsho------------------------------

        firestore = FirebaseFirestore.getInstance();
        Map<String, Object> users = new HashMap<>();
        users.put("FirstName", "NataPollob");
        users.put("LastName", "Sweety");
        users.put("Addresse", "Lamabazar");

        firestore.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(SecondActivity.this, "Launch Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SecondActivity.this, "Access Denied", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
