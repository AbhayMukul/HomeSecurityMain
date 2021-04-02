package com.example.homesecuritymain.citizen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelGuestList;
import com.example.homesecuritymain.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GuestListOTPVerificationActivity extends AppCompatActivity {
    private EditText edOTP;

    private Button btn;

    //firebase Database
    DatabaseReference mUserDatabaseCitizen;

    //get Data from previous Activity
    ModelGuestList modelGuestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list_o_t_p_verification);

        initialize();

        modelGuestList = (ModelGuestList) getIntent().getSerializableExtra("data");
        mUserDatabaseCitizen = FirebaseDatabase.getInstance().getReference("citizen");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDatabaseCitizen.child("demo").child("GuestList").child(modelGuestList.getCode()).setValue(modelGuestList);
                finish();
            }
        });
    }

    private void initialize() {
        edOTP = findViewById(R.id.Ed_GuestListOTPVerificationActivity_OTP);

        btn = findViewById(R.id.Btn_GuestListOTPVerificationActivity_Verify);
    }
}