package com.example.homesecuritymain.guard.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.Login.Activity.Guard.GuardLoginActivity;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.AddNewGuestListActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AddNewGuestActivity extends AppCompatActivity implements Serializable {
    String name, flat, work, phone;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    String verification;
    PhoneAuthProvider.ForceResendingToken Token;
    ModelActiveGuest modelActiveGuest;
    //firebase Database
    DatabaseReference mUserDatabaseCitizen;

    private EditText edName, edFlat, edWork, edPhone;
    private Button btnDone;
    private TextView tvGuestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_guest);

        initialize();

        mUserDatabaseCitizen = FirebaseDatabase.getInstance().getReference("citizen");
        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);

        //onClickListeners
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDone.setEnabled(false);
                getAndPutData();
                requestOTP("+91" + phone);
            }
        });

        tvGuestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNewGuestActivity.this, GuestListVerificationActivity.class));
            }
        });
    }

    private void requestOTP(String phoneNum) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 10L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verification = s;

                Token = forceResendingToken;

                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(modelActiveGuest);
                editor.putString(sharedPrefrencesClass.SP_JSON,json);
                editor.commit();

                Intent intent = new Intent(AddNewGuestActivity.this,OTPActivity.class);

                intent.putExtra("verification",verification);
                intent.putExtra(sharedPrefrencesClass.OTP_TYPE,"addGuest");

                Toast.makeText(AddNewGuestActivity.this, "OTP sent to " + phoneNum, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }
        });
    }

    private void getAndPutData() {
        name = edName.getText().toString().trim();
        phone = edPhone.getText().toString().trim();
        work = edWork.getText().toString().trim();
        flat = edFlat.getText().toString().trim();

        modelActiveGuest = new ModelActiveGuest(name,flat,phone,work,mUserDatabaseCitizen.push().getKey(),new DateAndTimeClass().getCurrentTime(),false);
    }

    private void initialize() {
        edFlat = findViewById(R.id.Ed_AddNewGuestActivity_FlatNumber);
        edName = findViewById(R.id.Ed_AddNewGuestActivity_Name);
        edPhone = findViewById(R.id.Ed_AddNewGuestActivity_PhoneNumber);
        edWork = findViewById(R.id.Ed_AddNewGuestActivity_Work);

        btnDone = findViewById(R.id.Btn_AddNewGuestActivity_Verify);

        tvGuestList = findViewById(R.id.Tv_AddNewGuestActivity_GuestList);
    }
}