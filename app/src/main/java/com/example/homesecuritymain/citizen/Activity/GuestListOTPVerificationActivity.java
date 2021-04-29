package com.example.homesecuritymain.citizen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelGuestList;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.guard.Activity.GuestListVerificationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class GuestListOTPVerificationActivity extends AppCompatActivity {
    private EditText edOTP;

    private Button btn;

    CommonClass object = new CommonClass();

    //get Data from previous Activity
    ModelGuestList modelGuestList;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    String flat,verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list_o_t_p_verification);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT,"");

        modelGuestList = (ModelGuestList) getIntent().getSerializableExtra("data");
        verification = (String) getIntent().getStringExtra("verificationOTP");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification, edOTP.getText().toString().trim());
                VerifyAuth(credential);
            }
        });
    }

    private void VerifyAuth(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //OTP verified
                    object.referenceGuestListCitizen(flat).child(modelGuestList.getCode()).setValue(modelGuestList);
                    finish();
                } else {
                    Toast.makeText(GuestListOTPVerificationActivity.this, "The OTP doesnt match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialize() {
        edOTP = findViewById(R.id.Ed_GuestListOTPVerificationActivity_OTP);

        btn = findViewById(R.id.Btn_GuestListOTPVerificationActivity_Verify);
    }
}