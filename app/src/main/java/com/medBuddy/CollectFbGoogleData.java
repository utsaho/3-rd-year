package com.medBuddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectFbGoogleData extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener
{
    String[] bloodGroup = {"Blood Group", "A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-"};
    private EditText dateOfBirth, lastDonation;
    private int ldDay2,ldMonth2,ldYear2;
    private int dobDay,dobMonth,dobYear;
    private DatePickerDialog ldDatePickerDialog2, dobDatePickerDialog;
    private Spinner spinner;
    private CheckBox cbAgree;
    private Button regBtn;
    private TextInputEditText phoneText, userText;
    private RadioButton radioButton, maleButton,femaleButton, othersButton;
    private RadioGroup radioGroup;
    private  int radioId, maleradioId, femaleradioId, othersradioId;

    private String bloodgroup, lastdonation, malegender, femalegender, othersgender, contact, userName, val, gender, dob;
    private Pattern phoneRegex = null;
    private boolean chkbox;
    private Matcher phoneMatcher;
    String email;

    private GoogleSignInClient client;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_fb_google_data);


        spinner = (Spinner) findViewById(R.id.bgSpinner2); spinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bloodGroup);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        lastDonation = (EditText) findViewById(R.id.lastDon2); lastDonation.setFocusable(false); lastDonation.setOnClickListener(this);
        dateOfBirth = (EditText) findViewById(R.id.dob2); dateOfBirth.setFocusable(false); dateOfBirth.setOnClickListener(this);

        maleButton = (RadioButton) findViewById(R.id.male2); maleButton.setOnClickListener(this);

        femaleButton = (RadioButton) findViewById(R.id.female2); femaleButton.setOnClickListener(this);

        othersButton = (RadioButton) findViewById(R.id.others2); othersButton.setOnClickListener(this);

        phoneText = (TextInputEditText) findViewById(R.id.phone2); phoneText.setOnFocusChangeListener(this);

        userText = (TextInputEditText) findViewById(R.id.username2); userText.setOnFocusChangeListener(this);

        cbAgree = (CheckBox) findViewById(R.id.cb2); cbAgree.setOnClickListener(this);
        cbAgree.setChecked(false);

        regBtn = (Button) findViewById(R.id.userRegBtn2); regBtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        email = getIntent().getStringExtra("email");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.dob2){
            final Calendar dobCalendar = Calendar.getInstance();
            dobDay = dobCalendar.get(Calendar.DAY_OF_MONTH); dobMonth = dobCalendar.get(Calendar.MONTH); dobYear = dobCalendar.get(Calendar.YEAR);

            dobDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker datePicker, int dobY, int dobM, int dobD)
                {
                    dateOfBirth.setText(dobD+"/"+dobM+"/"+dobY);
                }
            }, dobYear, dobMonth, dobDay);
            dobDatePickerDialog.show();
        }
        else if(view.getId()==R.id.lastDon2) {
            Calendar ldCalendar = Calendar.getInstance();
            ldDay2 = ldCalendar.get(Calendar.DAY_OF_MONTH); ldMonth2 = ldCalendar.get(Calendar.MONTH); ldYear2 = ldCalendar.get(Calendar.YEAR);

            ldDatePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker datePicker, int ldY2, int ldM2, int ldD2)
                {
                    lastDonation.setText(ldD2+"/"+ldM2+"/"+ldY2);
                }
            }, ldYear2, ldMonth2, ldDay2);
            ldDatePickerDialog2.show();
        }
        //Checkbox
        else if(view.getId()==R.id.cb2)
        {
            if(!cbAgree.isChecked()) {
                cbAgree.setChecked(false);
//                Toast.makeText(this, "now false", Toast.LENGTH_SHORT).show();
                regBtn.setEnabled(false);
                regBtn.setBackgroundColor(Color.GRAY);
            }
            else tcDialogBox();
        }
        //Disable female and others radio button
        else if(view.getId()==R.id.male2)
        {
            femaleButton.setChecked(false);

            othersButton.setChecked(false);
        }
        //Disable male and others radio button
        else if(view.getId()==R.id.female2)
        {
            maleButton.setChecked(false);
            othersButton.setChecked(false);
        }
        //Disable male and female radio button
        else if(view.getId()==R.id.others2)
        {
            maleButton.setChecked(false);
            femaleButton.setChecked(false);
//            maleButton.setEnabled(false);
//            femaleButton.setEnabled(false);
        }
        //User authentication function call
        else if(view.getId()==R.id.userRegBtn2) userAuth();
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getItemAtPosition(i).equals("Blood Group")){}
        else
        {
            bloodgroup = spinner.getItemAtPosition(i).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void tcDialogBox()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Terms & Conditions");
        builder.setMessage("1. Your information might be used for various purposes\n2. All of this app data are copyrighted material");

        builder.setPositiveButton("Agree", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                cbAgree.setChecked(true); regBtn.setBackgroundColor(Color.BLUE);
                regBtn.setEnabled(true);
            }
        });
        builder.setNegativeButton("Disagree", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                cbAgree.setChecked(false); regBtn.setBackgroundColor(Color.GRAY);
                regBtn.setEnabled(false);
            }
        });
        builder.show();
    }
    public void userAuth(){
        cbAgree.setEnabled(true);
        bloodgroup = (String) spinner.getSelectedItem().toString();
        lastdonation = (String) lastDonation.getText().toString();
        dob = (String) dateOfBirth.getText().toString();


        malegender = (String) maleButton.getText().toString();
        femalegender = (String) femaleButton.getText().toString();
        othersgender = (String) othersButton.getText().toString();

        contact = (String) phoneText.getText().toString();
        phoneRegex = Pattern.compile("^\\+?(88)?0?1[3456789][0-9]{8}\\b");

        userName = (String) userText.getText().toString();

        if((!bloodgroup.isEmpty()) && (!lastdonation.isEmpty()) && (!malegender.isEmpty()) && (!femalegender.isEmpty()) && (!othersgender.isEmpty()) && (!contact.isEmpty()) && (!userName.isEmpty()))
        {
            if(!contact.matches(String.valueOf(phoneRegex)))
            {
                phoneText.setError("Invalid phone number");
                regBtn.setEnabled(false); regBtn.setBackgroundColor(Color.GRAY);
            }
            else{
                cbAgree.setEnabled(true);
                regBtn.setEnabled(true); regBtn.setBackgroundColor(Color.BLUE);
//                Toast.makeText(this, "Calling updateType", Toast.LENGTH_SHORT).show();
                updateType();

            }
        }
        //Check if any one of the input field is empty
        else if((bloodgroup.isEmpty()) && (lastdonation.isEmpty()) && (malegender.isEmpty()) && (femalegender.isEmpty()) && (othersgender.isEmpty()) && (contact.isEmpty()) && (userName.isEmpty()))
        {
            Toast.makeText(this, "Please complete all the fields.",
                    Toast.LENGTH_SHORT).show();
            regBtn.setEnabled(false); regBtn.setBackgroundColor(Color.GRAY);
        }
        else
        {
            Toast.makeText(this, "Something Went Wrong!!!\n Suggestion: Fill-up All field.", Toast.LENGTH_SHORT).show();
        }
    }
    Map<String, Object> type = new HashMap<>();
    private void updateType() {
        String pic = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String email = getIntent().getStringExtra("email");
        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, String> user = new HashMap<>();

        FirebaseFirestore.getInstance().collection("userType").document("type").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                type = task.getResult().getData();
                type.put(email , "user");
                FirebaseFirestore.getInstance().collection("userType").document("type").set(type).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CollectFbGoogleData.this, "Inserted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CollectFbGoogleData.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
                System.out.println("utsho: " + type);
                System.out.println("HELLO" + task.getResult().getData());
                Toast.makeText(CollectFbGoogleData.this, "Completed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CollectFbGoogleData.this, "FFFFFF", Toast.LENGTH_SHORT).show();
            }
        });

        user.put("userId", uId);
        user.put("userBloodGroup", bloodgroup);
        user.put("userEmail", email);
        user.put("userLastDonationDate", lastdonation);
        user.put("userName", name);
        user.put("userPhoneNumber", phoneText.getText().toString());
        user.put("userPhotoUrl", pic);
        user.put("validity", "true");
        user.put("userDateOfBirth", dob);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("user").document(userId).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), "Profile has been created!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), User.class));
                }else{
//                    Toast.makeText(getApplicationContext(), "Error on store data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}