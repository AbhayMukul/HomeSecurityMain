package com.example.homesecuritymain.Login.Activity.Guard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.homesecuritymain.R;

public class InValidLoginGuardActivity extends AppCompatActivity {
    Boolean ACTIVE;
    LinearLayout linearLayoutOutOfTime,linearLayoutInActiveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_valid_login_guard);

        ACTIVE = getIntent().getBooleanExtra("active",false);

        initialize();

        if(ACTIVE){
            linearLayoutInActiveAccount.setVisibility(View.GONE);
        }else {
            linearLayoutOutOfTime.setVisibility(View.GONE);
        }

    }

    private void initialize() {
        linearLayoutInActiveAccount = findViewById(R.id.Ll_InValidLoginGuardActivity_AccountInActive);
        linearLayoutOutOfTime = findViewById(R.id.Ll_InValidLoginGuardActivity_AccountOutOfTime);
    }
}