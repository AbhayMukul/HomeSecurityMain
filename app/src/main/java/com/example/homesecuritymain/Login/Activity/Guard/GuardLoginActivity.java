package com.example.homesecuritymain.Login.Activity.Guard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.Login.Activity.Citizen.CreateNewAccountLoginActivity;
import com.example.homesecuritymain.Login.Activity.Citizen.LoginCitizenActivity;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.guard.Activity.GuardMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class GuardLoginActivity extends AppCompatActivity {
    String verification;
    PhoneAuthProvider.ForceResendingToken Token;
    private TextInputLayout tilPhone, tilOTP, tilPassword, tilID;
    private TextInputEditText tiEdPhone, tiEdOTP, tiEdPassword, tiEdID;
    private Button btn;
    private String phone, password, OTP, id;
    private int i = 0;
    private CommonClass object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_login);

        initialize();

        object = new CommonClass();

        tiEdPassword.setVisibility(View.GONE);
        tiEdOTP.setVisibility(View.GONE);

        tilOTP.setVisibility(View.GONE);
        tilPassword.setVisibility(View.GONE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    //OTP sent
                    //check if ID and Phone Match
                    phone = tiEdPhone.getText().toString().trim();
                    id = tiEdID.getText().toString().trim();

                    object.mUserDatabaseGuardLogin.child(id).child("phone").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String phoneNum = snapshot.getValue().toString().trim();
                            if(phoneNum == null){
                                Toast.makeText(GuardLoginActivity.this, "The account with id " + id + "doesnt exists", Toast.LENGTH_SHORT).show();
                            }else {
                                if (phoneNum.equals(phone)) {
                                    btn.setEnabled(false);
                                    requestOTP("+91" + phone);
                                } else {
                                    Toast.makeText(GuardLoginActivity.this, "the phone number given doesnt belong to the ID", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (i == 1) {
                    //verify OTP
                    OTP = tiEdOTP.getText().toString().trim();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification, OTP);
                    VerifyAuth(credential);
                    btn.setEnabled(false);
                }else if(i == 2){
                    //get Password and check
                    password = tiEdPassword.getText().toString().trim();
                    object.mUserDatabaseGuardLogin.child(id).child("password").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String passwordDatabase = snapshot.getValue().toString().trim();
                            if(passwordDatabase.equals(password)){
                                startActivity(new Intent(GuardLoginActivity.this, GuardMainActivity.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    private void VerifyAuth(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //OTP verified
                    object.mUserDatabaseGuardLogin.child(id).child("password").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String passwordDatabase = snapshot.getValue().toString().trim();
                            if(passwordDatabase.equals("")){
                                //new account
                                Intent intent = new Intent(GuardLoginActivity.this,NewGuardActivity.class);
                                intent.putExtra("idGuard",id);
                                startActivity(intent);
                            }else {
                                //old account
                                tiEdPassword.setVisibility(View.VISIBLE);
                                tilPassword.setVisibility(View.VISIBLE);
                                i = 2;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    btn.setEnabled(true);
                    tilOTP.setError("wrong OTP");
                }
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

                Log.e("password in OTP sent", "" + password);

                //visibility set for OTP edittext
                tilOTP.setVisibility(View.VISIBLE);
                tiEdOTP.setVisibility(View.VISIBLE);

                i = 1;

                Toast.makeText(GuardLoginActivity.this, "OTP sent to number : " + phoneNum, Toast.LENGTH_SHORT).show();

                btn.setText("VERIFY OTP");
                btn.setEnabled(true);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }
        });
    }

    private void initialize() {
        tilPassword = findViewById(R.id.Til_GuardLoginActivity_Password);
        tilPhone = findViewById(R.id.Til_GuardLoginActivity_Phone);
        tilOTP = findViewById(R.id.Til_GuardLoginActivity_OTP);
        tilID = findViewById(R.id.Til_GuardLoginActivity_ID);

        tiEdPassword = findViewById(R.id.TiEt_GuardLoginActivity_Password);
        tiEdOTP = findViewById(R.id.TiEt_GuardLoginActivity_OTP);
        tiEdPhone = findViewById(R.id.TiEt_GuardLoginActivity_Phone);
        tiEdID = findViewById(R.id.TiEt_GuardLoginActivity_ID);

        btn = findViewById(R.id.Btn_GuardLoginActivity_Next);
    }

}