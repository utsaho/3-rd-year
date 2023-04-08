package com.medBuddy;

import static com.google.android.gms.auth.api.signin.GoogleSignInOptions.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextInputEditText loginEmailTextField, loginPasswordTextField;
    private TextView joinNowText;
    private Button loginButton;
    private String loginEmailString, loginPasswordString;

    private ImageView facebookbtn, googlebtn;
    private GoogleSignInClient client;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loginEmailTextField = (TextInputEditText) findViewById(R.id.loginEmailID);
        loginPasswordTextField = (TextInputEditText) findViewById(R.id.loginPasswordID);
        loginButton = (Button)findViewById(R.id.loginButtonID);
        joinNowText = (TextView) findViewById(R.id.joinNowID);

        facebookbtn = (ImageView) findViewById(R.id.facebookButton);
        googlebtn = (ImageView) findViewById(R.id.googleButton);
        setClickListener();         // Calling onclick method

//        ----------------- Google signin process -----------------


        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this,options);

        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i,1234);

            }
        });

        facebookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent facebookAuthentication = new Intent(LoginActivity.this, FacebookAuthentication.class);
                facebookAuthentication.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(facebookAuthentication);

            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    // ---------------- onCreate end here ---------------


//    ------------- Button control method --------------

    private void setClickListener(){
        loginButton.setOnClickListener(this);
        joinNowText.setOnClickListener(this);
        facebookbtn.setOnClickListener(this);
        googlebtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        final Loading loadingObj = new Loading(LoginActivity.this);
        loginEmailString = (String) loginEmailTextField.getText().toString();
        loginPasswordString = (String) loginPasswordTextField.getText().toString();
        //By clicking join now, activity for "Sign Up As" opens
        if(view.getId()==R.id.joinNowID)
        {
            Intent signUpAs = new Intent(LoginActivity.this,JoinNowActivity.class);
            startActivity(signUpAs);
        }
        else if(view.getId()==R.id.loginButtonID)
        {
            loginWithEmailPassword();
        }
    }

    private void loginWithEmailPassword() {
        String email  = String.valueOf(loginEmailTextField.getText());
        String password = String.valueOf(loginPasswordTextField.getText());
        if(email.isEmpty() || password.isEmpty())
            Toast.makeText(this, "Wrong username/email or password", Toast.LENGTH_SHORT).show();
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                        sendToHome(email);
//                        startActivity(new Intent(LoginActivity.this, User.class));
                    }else{
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String email = account.getEmail();
//                                    Toast.makeText(LoginActivity.this, "HHH" + mail, Toast.LENGTH_SHORT).show();
//                                    if(mail.isEmpty()){
//                                        mail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//                                    }
                                    sendToHome(email);
                                    Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendToHome(String email) {
        FirebaseFirestore.getInstance().collection("userType").document("type").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null){
//                                                    Toast.makeText(LoginActivity.this, "Not Null", Toast.LENGTH_SHORT).show();
                        if(task.getResult().getData()!=null){
//                                                        Toast.makeText(LoginActivity.this, "Gettign Data", Toast.LENGTH_SHORT).show();
                            Map<String, Object> map = task.getResult().getData();
                            if(!map.isEmpty()){
//                                                            Toast.makeText(LoginActivity.this, map.toString(), Toast.LENGTH_SHORT).show();
//                                                            Toast.makeText(LoginActivity.this, mail, Toast.LENGTH_SHORT).show();
                                Object obj =  map.get(email);
                                Toast.makeText(LoginActivity.this, email, Toast.LENGTH_SHORT).show();
//                                                            Toast.makeText(LoginActivity.this, "hhhh", Toast.LENGTH_SHORT).show();
                                if(obj!=null){
                                    String type= map.get(email).toString();
                                    if(type.equals("user")){
//                                                            Toast.makeText(LoginActivity.this, mail, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(LoginActivity.this, "Moving", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), User.class));
                                    }
                                    else if(type.equals("authority")){
                                        startActivity(new Intent(getApplicationContext(), MedAuthHomeActivity.class));
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Not Moving", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), CollectFbGoogleData.class).putExtra("email", email));
                                    }
                                }
                                else {
//                                                                System.out.println("MyString" + obj);
//                                                                Toast.makeText(LoginActivity.this, obj.toString(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(LoginActivity.this, "object is null", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), CollectFbGoogleData.class).putExtra("email", email));
                                }
                            }else{
                                startActivity(new Intent(getApplicationContext(), CollectFbGoogleData.class).putExtra("email", email));
                            }
                        }else{
                            startActivity(new Intent(getApplicationContext(), CollectFbGoogleData.class).putExtra("email", email));
                        }
                    }
                    else{
                        startActivity(new Intent(getApplicationContext(), CollectFbGoogleData.class).putExtra("email", email));
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Failed To retrive", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!= null){
//            Toast.makeText(this, FirebaseAuth.getInstance().getCurrentUser().getProviderData().get(1).getProviderId(), Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this,MainActivity2.class);
        }
    }
}
