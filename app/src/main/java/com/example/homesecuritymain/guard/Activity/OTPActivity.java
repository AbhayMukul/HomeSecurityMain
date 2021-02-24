package com.example.homesecuritymain.guard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.homesecuritymain.R;

public class OTPActivity extends AppCompatActivity {
    private EditText edOTP;
    private Button button;

    private String OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        initialize();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OTPActivity.this,CallActivity.class));
            }
        });
    }

    private void initialize() {
        edOTP = findViewById(R.id.Ed_OTPActivity_OTP);

        button = findViewById(R.id.Btn_OTPActivity_Verify);
    }
}