package com.medBuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MedRegActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener
{
    private CheckBox cbMedReg; private Button medRegButton;

    private TextInputEditText medNameInput, districtInput, divisionInput, webInput, phoneInput,
            mailInput, uNameInput, passInput, picInput;
    private RadioButton pubRadioButton, priRadioButton;

    private String medText, disText, divText, webText, contactText, mailText, userText, passText,
            pubText, priText, picText;

    private Pattern medicalWebsiteRegex = null;
    private Pattern medicalContactRegex = null;
    private Pattern medicalEmailRegex = null;
    private Pattern medicalPasswordRegex = null;

    private boolean checkbox;

    private Matcher mWebMatcher, mPhoneMatcher, mEmailMatcher, mPasswordMatcher;

    FirebaseAuth medAuth; FirebaseUser medUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_reg);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        medNameInput = (TextInputEditText) findViewById(R.id.medId);
        medNameInput.setOnFocusChangeListener(this);

        districtInput = (TextInputEditText) findViewById(R.id.districtId);
        districtInput.setOnFocusChangeListener(this);

        divisionInput = (TextInputEditText) findViewById(R.id.divisionId);
        divisionInput.setOnFocusChangeListener(this);

        webInput = (TextInputEditText) findViewById(R.id.webId);
        webInput.setOnFocusChangeListener(this);

        phoneInput = (TextInputEditText) findViewById(R.id.contactId);
        phoneInput.setOnFocusChangeListener(this);

        mailInput = (TextInputEditText) findViewById(R.id.mailId);
        mailInput.setOnFocusChangeListener(this);

        uNameInput = (TextInputEditText) findViewById(R.id.usernameId);
        uNameInput.setOnFocusChangeListener(this);

        passInput = (TextInputEditText) findViewById(R.id.passwordId);
        passInput.setOnFocusChangeListener(this);

        pubRadioButton = (RadioButton) findViewById(R.id.pubId);
        pubRadioButton.setOnClickListener(this);

        priRadioButton = (RadioButton) findViewById(R.id.priId);
        priRadioButton.setOnClickListener(this);

        picInput = (TextInputEditText) findViewById(R.id.photoId);
        picInput.setOnFocusChangeListener(this);

        cbMedReg = (CheckBox) findViewById(R.id.cbId);
        cbMedReg.setOnClickListener(this);

        medRegButton = (Button) findViewById(R.id.medRegId);
        medRegButton.setOnClickListener(this);

        medAuth = FirebaseAuth.getInstance();
        medUser = medAuth.getCurrentUser();
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.cbId)
        {
            if(!cbMedReg.isChecked()) {
                cbMedReg.setChecked(false);
                medRegButton.setEnabled(false);
                medRegButton.setBackgroundColor(Color.GRAY);
            }
            else tcDialogBoxTwo();
        }
        //Disable private radio button
        else if(view.getId()==R.id.pubId) priRadioButton.setEnabled(false);
        //Disable public radio button
        else if(view.getId()==R.id.priId) pubRadioButton.setEnabled(false);
        //On clicking register button
        else if(view.getId()==R.id.medRegId) authorityAuth();
    }

    public void tcDialogBoxTwo()
    {
        AlertDialog.Builder builderTwo = new AlertDialog.Builder(MedRegActivity.this);

        builderTwo.setTitle("Terms & Conditions");
        builderTwo.setMessage("1. Your information might be used for various purposes\n2. All of this app data are copyrighted material");

        builderTwo.setPositiveButton("Agree", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                cbMedReg.setChecked(true); medRegButton.setEnabled(true);
                medRegButton.setBackgroundColor(Color.BLUE);
            }
        });
        builderTwo.setNegativeButton("Disagree", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                cbMedReg.setChecked(false); medRegButton.setEnabled(false);
                medRegButton.setBackgroundColor(Color.GRAY);
            }
        });
        builderTwo.show();
    }

    @Override
    public void onFocusChange(View view, boolean b)
    {

    }

    //Medical Authority authentication
    public void authorityAuth()
    {
        medText = (String) medNameInput.getText().toString();
        disText = (String) districtInput.getText().toString();
        divText = (String) divisionInput.getText().toString();

        webText = (String) webInput.getText().toString();
//        medicalWebsiteRegex = Pattern.compile("^(www)(.){1}([a-z])+(.){1}([a-z])+$");

        contactText = (String) phoneInput.getText().toString();
        medicalContactRegex = Pattern.compile("^\\+?(88)?0?1[3456789][0-9]{8}\\b");

        mailText = (String) mailInput.getText().toString();
        medicalEmailRegex = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

        userText = (String) uNameInput.getText().toString();

        passText = (String) passInput.getText().toString();
        medicalPasswordRegex = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");

        pubText = (String) pubRadioButton.getText().toString();
        priText = (String) priRadioButton.getText().toString();
        picText = (String) picInput.getText().toString();
        //Checks if all the input fields are filled up
        if((medText.isEmpty()) || (disText.isEmpty()) || (divText.isEmpty()) || (webText.isEmpty()) || (contactText.isEmpty()) || (mailText.isEmpty()) || (userText.isEmpty()) || (passText.isEmpty()) || (pubText.isEmpty()) || (priText.isEmpty()) || (picText.isEmpty())){
            Toast.makeText(MedRegActivity.this, "Please complete all the fields.",
                    Toast.LENGTH_SHORT).show();
            cbMedReg.setEnabled(false);
            medRegButton.setEnabled(false); medRegButton.setBackgroundColor(Color.GRAY);
        }
        //Authentication starts here
        else {
//            if(!contactText.matches(String.valueOf(medicalContactRegex)))
//            {
//                phoneInput.setError("Invalid contact number");
//                medRegButton.setEnabled(false); medRegButton.setBackgroundColor(Color.GRAY);
//            }
//            else if(!mailText.matches(String.valueOf(medicalEmailRegex)))
//            {
//                mailInput.setError("Invalid email address");
//                medRegButton.setEnabled(false); medRegButton.setBackgroundColor(Color.GRAY);
//            }
//            else if((!passText.matches(String.valueOf(medicalPasswordRegex))) && (passText.length()<8))
//            {
//                passInput.setError("Invalid password entered");
//                medRegButton.setEnabled(false); medRegButton.setBackgroundColor(Color.GRAY);
//            }else{
                Toast.makeText(this, "All are ok!", Toast.LENGTH_SHORT).show();
                Map <String, String> map = new HashMap<>();
                map.put("hospitalName", medText);
                map.put("hospitalContactNumber", contactText);
                map.put("hospitalEmail", mailText);
                map.put("hospitalImage", picText);
                map.put("hospitalLocation", disText);
                map.put("hospitalWebsite", webText);
                map.put("validity", "true");
                updateUI(map, 2);
//            }


//            cbMedReg.setEnabled(true);
//            medRegButton.setEnabled(true); medRegButton.setBackgroundColor(Color.BLUE);
//
//            medAuth.createUserWithEmailAndPassword(mailText,passText).addOnCompleteListener(new OnCompleteListener<AuthResult>()
//            {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task)
//                {
//                    if(task.isSuccessful())
//                    {
//                        medAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>()
//                        {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task)
//                            {
//                                if(task.isSuccessful())
//                                {
//                                    Toast.makeText(MedRegActivity.this,
//                                            "Registration Successful! PLease verify your email to continue.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                                else
//                                {
//                                    Toast.makeText(MedRegActivity.this,
//                                            ""+task.getException(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                    else
//                    {
//                        Toast.makeText(MedRegActivity.this,
//                                ""+task.getException(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }
    }

    private void updateUI(Map<String, String> user, int hint) {
        medAuth.createUserWithEmailAndPassword(mailText,passText).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                Toast.makeText(getApplicationContext(), "On Way...", Toast.LENGTH_SHORT).show();
                if(task.isSuccessful())
                {
                    medAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                user.put("userId", userId);
                                FirebaseFirestore.getInstance().collection((hint==1)?"user":"hospital").document(userId).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            updateType(user.get("hospitalEmail").toString());
                                            Toast.makeText(getApplicationContext(), "Profile has been created!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Error on store data", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), User.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    Map<String, Object>type = new HashMap<>();
    private void updateType(String email){
        FirebaseFirestore.getInstance().collection("userType").document("type").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                type = task.getResult().getData();
                type.put(email , "authority");
                FirebaseFirestore.getInstance().collection("userType").document("type").set(type).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                System.out.println("utsho: " + type);
                System.out.println("HELLO" + task.getResult().getData());
                Toast.makeText(getApplicationContext(), "Completed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "FFFFFF", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
