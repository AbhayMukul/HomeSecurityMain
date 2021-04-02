package com.example.homesecuritymain.citizen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelGuestList;
import com.example.homesecuritymain.R;

public class AddNewGuestListActivity extends AppCompatActivity {
    public EditText edName,edPhone,edCode,edWork;

    public Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_guest_list);

        initialize();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send OTP to number of guest
                ModelGuestList modelGuestList = new ModelGuestList(edName.getText().toString().trim(),"demo",edPhone.getText().toString().trim(),edWork.getText().toString().trim(),edCode.getText().toString().trim());

                Intent intent = new Intent(getBaseContext(),GuestListOTPVerificationActivity.class);

                intent.putExtra("data",modelGuestList);

                startActivity(new Intent(intent));

                finish();
            }
        });
    }

    private void initialize() {
        edName = findViewById(R.id.Ed_AddNewGuestListActivityn_Name);
        edPhone = findViewById(R.id.Ed_AddNewGuestListActivity_PhoneNumber);
        edCode = findViewById(R.id.Ed_AddNewGuestListActivity_Code);
        edWork = findViewById(R.id.Ed_AddNewGuestListActivity_Work);

        btn = findViewById(R.id.Btn_AddNewGuestListActivity_Done);
    }
}