package com.medBuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnFocusChangeListener
{
    String[] bloodGroup = {"Blood Group", "A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-"};

    private EditText dateOfBirth, lastDonation;
    private int dobDay,dobMonth,dobYear;
    private int ldDay,ldMonth,ldYear;
    private DatePickerDialog dobDatePickerDialog,ldDatePickerDialog;
    private Spinner spinner;
    private CheckBox cbAgree;
    private Button regBtn;
    private TextInputEditText firstName, lastName, picURL, emailText, passText, phoneText, userText;
    private RadioButton radioButton, maleButton,femaleButton, othersButton;
    private RadioGroup radioGroup;
    private  int radioId, maleradioId, femaleradioId, othersradioId;

    private String firstname, lastname, dateofbirth, pic, bloodgroup, lastdonation,
            email, password, malegender, femalegender, othersgender, contact, userName, val, gender;

    private boolean chkbox;

    private Pattern emailRegex = null;
    private Pattern passwordRegex = null;
    private Pattern phoneRegex = null;

    private Matcher emailMatcher, passMatcher, phoneMatcher;

    FirebaseAuth mAuth;
//    FirebaseUser mUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firstName = (TextInputEditText) findViewById(R.id.fName); firstName.setOnFocusChangeListener(this);

        lastName = (TextInputEditText) findViewById(R.id.lName); lastName.setOnFocusChangeListener(this);

        dateOfBirth = (EditText) findViewById(R.id.dob); dateOfBirth.setFocusable(false); dateOfBirth.setOnClickListener(this);

        picURL = (TextInputEditText) findViewById(R.id.url); picURL.setOnFocusChangeListener(this);

        spinner = (Spinner) findViewById(R.id.bgSpinner); spinner.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(UserRegActivity.this, android.R.layout.simple_spinner_item, bloodGroup);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        lastDonation = (EditText) findViewById(R.id.lastDon); lastDonation.setFocusable(false); lastDonation.setOnClickListener(this);

        emailText = (TextInputEditText) findViewById(R.id.email); emailText.setOnFocusChangeListener(this);

        passText = (TextInputEditText) findViewById(R.id.pass); passText.setOnFocusChangeListener(this);

        maleButton = (RadioButton) findViewById(R.id.male); maleButton.setOnClickListener(this);

        femaleButton = (RadioButton) findViewById(R.id.female); femaleButton.setOnClickListener(this);

        othersButton = (RadioButton) findViewById(R.id.others); othersButton.setOnClickListener(this);

        phoneText = (TextInputEditText) findViewById(R.id.phone); phoneText.setOnFocusChangeListener(this);

        userText = (TextInputEditText) findViewById(R.id.username); userText.setOnFocusChangeListener(this);

        cbAgree = (CheckBox) findViewById(R.id.cb); cbAgree.setOnClickListener(this);
        cbAgree.setChecked(false);

        regBtn = (Button) findViewById(R.id.userRegBtn); regBtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
//        if(cbAgree.isChecked()) cbAgree.setChecked(false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view)
    {
        //Get Date of birth
        if(view.getId()==R.id.dob)
        {
            final Calendar dobCalendar = Calendar.getInstance();
            dobDay = dobCalendar.get(Calendar.DAY_OF_MONTH); dobMonth = dobCalendar.get(Calendar.MONTH); dobYear = dobCalendar.get(Calendar.YEAR);

            dobDatePickerDialog = new DatePickerDialog(UserRegActivity.this, new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker datePicker, int dobY, int dobM, int dobD)
                {
                    dateOfBirth.setText(dobD+"/"+dobM+"/"+dobY);
                }
            }, dobYear, dobMonth, dobDay);
            dobDatePickerDialog.show();
        }
        //Get Last donation
        else if(view.getId()==R.id.lastDon)
        {
            final Calendar ldCalendar = Calendar.getInstance();
            ldDay = ldCalendar.get(Calendar.DAY_OF_MONTH); ldMonth = ldCalendar.get(Calendar.MONTH); ldYear = ldCalendar.get(Calendar.YEAR);

            ldDatePickerDialog = new DatePickerDialog(UserRegActivity.this, new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker datePicker, int ldY, int ldM, int ldD)
                {
                    lastDonation.setText(ldD+"/"+ldM+"/"+ldY);
                }
            }, ldYear, ldMonth, ldDay);
            ldDatePickerDialog.show();
        }
        //Checkbox
        else if(view.getId()==R.id.cb)
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
        else if(view.getId()==R.id.male)
        {
            femaleButton.setChecked(false);

            othersButton.setChecked(false);
        }
        //Disable male and others radio button
        else if(view.getId()==R.id.female)
        {
            maleButton.setChecked(false);
            othersButton.setChecked(false);
        }
        //Disable male and female radio button
        else if(view.getId()==R.id.others)
        {
            maleButton.setChecked(false);
            femaleButton.setChecked(false);
//            maleButton.setEnabled(false);
//            femaleButton.setEnabled(false);
        }
        //User authentication function call
        else if(view.getId()==R.id.userRegBtn) userAuth();
    }

    //Terms and conditions dialog box
    public void tcDialogBox()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserRegActivity.this);

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

    //Get blood group
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        //Excluding initial position
        if(adapterView.getItemAtPosition(i).equals("Blood Group")){}
        else
        {
            bloodgroup = spinner.getItemAtPosition(i).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    //Focus listener method for all text field input
    @Override
    public void onFocusChange(View view, boolean b)
    {

    }

    //User Authentication
    public void userAuth()
    {
        cbAgree.setEnabled(true);
        firstname = (String) firstName.getText().toString();
        lastname = (String) lastName.getText().toString();
        dateofbirth = (String) dateOfBirth.getText().toString();
        pic = (String) picURL.getText().toString();
        bloodgroup = (String) spinner.getSelectedItem().toString();
        lastdonation = (String) lastDonation.getText().toString();

        email = (String) emailText.getText().toString();
        emailRegex = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

        malegender = (String) maleButton.getText().toString();
        femalegender = (String) femaleButton.getText().toString();
        othersgender = (String) othersButton.getText().toString();

        password = (String) passText.getText().toString();
        passwordRegex = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");

        contact = (String) phoneText.getText().toString();
        phoneRegex = Pattern.compile("^\\+?(88)?0?1[3456789][0-9]{8}\\b");

        userName = (String) userText.getText().toString();

        if((!firstname.isEmpty()) && (!lastname.isEmpty()) &&
                (!dateofbirth.isEmpty()) && (!pic.isEmpty()) &&
                (!bloodgroup.isEmpty()) && (!lastdonation.isEmpty()) &&
                (!email.isEmpty()) && (!password.isEmpty()) &&
                (!malegender.isEmpty()) && (!femalegender.isEmpty()) &&
                (!othersgender.isEmpty()) && (!contact.isEmpty()) &&
                (!userName.isEmpty()))
        {
            if(!email.matches(String.valueOf(emailRegex)))
            {
                emailText.setError("Invalid email address");
                regBtn.setEnabled(false); regBtn.setBackgroundColor(Color.GRAY);
            }
            else if((!password.matches(String.valueOf(passwordRegex))) && (password.length()<8))
            {
                passText.setError("Invalid password entered");
                regBtn.setEnabled(false); regBtn.setBackgroundColor(Color.GRAY);
            }
            else if(!contact.matches(String.valueOf(phoneRegex)))
            {
                phoneText.setError("Invalid phone number");
                regBtn.setEnabled(false); regBtn.setBackgroundColor(Color.GRAY);
            }
            else{
                cbAgree.setEnabled(true);
                regBtn.setEnabled(true); regBtn.setBackgroundColor(Color.BLUE);

                Map<String, String> user = new HashMap<>();
                user.put("userBloodGroup", bloodgroup);
                user.put("dataOfBirth", dateofbirth);
                user.put("userEmail", email);
                user.put("userLastDonationDate", lastdonation);
//                                user.put("userLocation", addre)
                user.put("userName", firstname+" "+lastname);
                user.put("userPhoneNumber", phoneText.getText().toString());
                user.put("userPhotoUrl", pic);
                user.put("validity", "true");
                updateUI(user, 1);
            }
        }
        //Check if any one of the input field is empty
        else if((firstname.isEmpty()) && (lastname.isEmpty()) &&
                (dateofbirth.isEmpty()) && (pic.isEmpty()) &&
                (bloodgroup.isEmpty()) && (lastdonation.isEmpty()) &&
                (email.isEmpty()) && (password.isEmpty()) &&
                (malegender.isEmpty()) && (femalegender.isEmpty()) &&
                (othersgender.isEmpty()) && (contact.isEmpty()) &&
                (userName.isEmpty()))
        {
            Toast.makeText(UserRegActivity.this, "Please complete all the fields.",
                    Toast.LENGTH_SHORT).show();
            regBtn.setEnabled(false); regBtn.setBackgroundColor(Color.GRAY);
        }
        else
        {
            Toast.makeText(this, "Something Went Wrong!!!\n Suggestion: Fill-up All field.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(Map<String, String>user, int hint) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                Toast.makeText(UserRegActivity.this, "On Way...", Toast.LENGTH_SHORT).show();
                if(task.isSuccessful())
                {
                    updateUserType();
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>()
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
                                            Toast.makeText(UserRegActivity.this, "Profile has been created!", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(UserRegActivity.this, "Error on store data", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), User.class));
                            }
                            else
                            {
                                Toast.makeText(UserRegActivity.this,
                                        ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(UserRegActivity.this,
                            ""+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    Map<String, Object>type = new HashMap<>();
    private void updateUserType(){
        FirebaseFirestore.getInstance().collection("userType").document("type").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                type = task.getResult().getData();
                type.put(email , "user");
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
