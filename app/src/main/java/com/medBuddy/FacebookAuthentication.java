package com.medBuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Map;

public class FacebookAuthentication extends AppCompatActivity {
    CallbackManager callbackManager;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(FacebookAuthentication.this, "HHHHHH", Toast.LENGTH_SHORT).show();
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FacebookAuthentication.this, "Request Canceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(FacebookAuthentication.this, "Something went wrong on facebook Login", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(FacebookAuthentication.this, "MMMM", Toast.LENGTH_SHORT).show();
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println("pollob" +task.getResult().getAdditionalUserInfo().getProfile());
                            Map<String, Object> fbUser =  task.getResult().getAdditionalUserInfo().getProfile();
                            String email = fbUser.get("email").toString();
//                            Toast.makeText(FacebookAuthentication.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                            updateUI(user, email);
                        } else {
                            Toast.makeText(FacebookAuthentication.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user, String email) {
        Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
//                                                            Toast.makeText(LoginActivity.this, "hhhh", Toast.LENGTH_SHORT).show();
                                if(obj!=null){
                                    String type= map.get(email).toString();
                                    if(type.equals("user")){
//                                                            Toast.makeText(LoginActivity.this, mail, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Moving", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), User.class));
                                    }
                                    else if(type.equals("authority")){
                                        startActivity(new Intent(getApplicationContext(), MedAuthHomeActivity.class));
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Not Moving", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), CollectFbGoogleData.class).putExtra("email", email));
                                    }
                                }
                                else {
//                                                                System.out.println("MyString" + obj);
//                                                                Toast.makeText(LoginActivity.this, obj.toString(), Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "object is null", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Failed To retrive", Toast.LENGTH_SHORT).show();
                }
            }
        });



//        startActivity(new Intent(getApplicationContext(), CollectFbGoogleData.class).putExtra("email", email));
    }
}