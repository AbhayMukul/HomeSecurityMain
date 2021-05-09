package com.example.homesecuritymain.guard.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.Login.Activity.Guard.GuardLoginActivity;
import com.example.homesecuritymain.Login.Activity.Guard.NewGuardActivity;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.CitizenMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class OTPActivity extends AppCompatActivity {
    private EditText edOTP;
    private Button button;

    private String OTP,verification,type;
    private SharedPrefrencesClass sharedPrefrencesClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        initialize();

        type = getIntent().getStringExtra(sharedPrefrencesClass.OTP_TYPE);
        verification = getIntent().getStringExtra("verification");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                OTP = edOTP.getText().toString().trim();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification, OTP);
                VerifyAuth(credential);
            }
        });
    }

    private void VerifyAuth(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(OTPActivity.this, "OTP verified", Toast.LENGTH_SHORT).show();

                    if(type.equals("addGuest")) {
                        //used when entering guest
                        startActivity(new Intent(OTPActivity.this, CallActivity.class));
                    }else if(type.equals("changePassword")){
                        //change Password
                    }else {
                        //change phone

                    }
                    finish();
                }
                else {
                    button.setEnabled(true);
                    edOTP.setError("wrong OTP");
                }
            }
        });
    }

    private void initialize() {
        edOTP = findViewById(R.id.Ed_OTPActivity_OTP);
        button = findViewById(R.id.Btn_OTPActivity_Verify);
    }
}