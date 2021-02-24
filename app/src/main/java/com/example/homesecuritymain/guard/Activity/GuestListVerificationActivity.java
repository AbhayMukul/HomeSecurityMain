
package com.example.homesecuritymain.guard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.homesecuritymain.R;

public class GuestListVerificationActivity extends AppCompatActivity {
    private EditText edFlat,edCode;
    private Button button;

    private String flat,code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list_verification);

        initialize();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                startActivity(new Intent(GuestListVerificationActivity.this,GuardMainActivity.class));
            }
        });
    }

    private void getData() {
        flat = edFlat.getText().toString().trim();
        code = edCode.getText().toString().trim();
    }

    private void initialize() {
        edCode = findViewById(R.id.Ed_GuestListVerificationActivity_Code);
        edFlat = findViewById(R.id.Ed_GuestListVerificationActivity_Flat);

        button = findViewById(R.id.Btn_GuestListVerificationActivity_Verify);
    }
}