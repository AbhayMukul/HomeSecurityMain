package com.example.homesecuritymain.Login.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.homesecuritymain.R;
import com.example.homesecuritymain.guard.Activity.GuardMainActivity;

public class LoginActivityMain extends AppCompatActivity {
    private Button btnCitizen, btnGuard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        initialize();

        btnCitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityMain.this, LoginCitizenActivity.class));
            }
        });

        btnGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityMain.this, GuardMainActivity.class));
            }
        });
    }

    private void initialize() {
        btnCitizen = findViewById(R.id.Btn_LoginActivityMain_CitizenLogin);
        btnGuard = findViewById(R.id.btn_LoginActivityMain_GuardLogin);
    }
}