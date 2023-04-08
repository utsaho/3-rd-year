package com.medBuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

//cmnt by pollob

public class JoinNowActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button userBtn, medButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_now);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        userBtn = (Button)findViewById(R.id.userButton);
        userBtn.setOnClickListener(this);
        medButton = (Button)findViewById(R.id.authButton);
        medButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.userButton)
        {
            Intent userReg = new Intent(JoinNowActivity.this,UserRegActivity.class);
            startActivity(userReg);
        }
        else if(view.getId()==R.id.authButton)
        {
            Intent medReg = new Intent(JoinNowActivity.this,MedRegActivity.class);
            startActivity(medReg);
        }
    }
}