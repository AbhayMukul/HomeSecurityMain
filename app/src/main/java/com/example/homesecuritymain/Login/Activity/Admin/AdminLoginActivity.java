package com.example.homesecuritymain.Login.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.example.homesecuritymain.Login.Activity.Citizen.LoginCitizenPreExistingAccountActivity;
import com.example.homesecuritymain.Login.Activity.NewAccount.CreateNewAccountActivity;
import com.example.homesecuritymain.R;
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

public class AdminLoginActivity extends AppCompatActivity {
    //OTP
    String verification;
    PhoneAuthProvider.ForceResendingToken Token;
    //shared Prefrence
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    private TextInputLayout tilPhone, tilOTP, tilPassword;
    private TextInputEditText tiEdPhone, tiEdOTP, tiEdPassword;
    private Button btn;
    private String phone, password, OTP;
    private int i = 0;
    private CommonClass object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);

        object = new CommonClass();

        btn.setText("Send OTP");

        tiEdOTP.setVisibility(View.GONE);
        tilOTP.setVisibility(View.GONE);
        tiEdPassword.setVisibility(View.GONE);
        tilPassword.setVisibility(View.GONE);

        i = 0;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText();

                if (i == 0) {
                    //verification
                    btn.setEnabled(false);
                    getPassword(new AdminLoginActivity.FirebaseCallBack() {
                        @Override
                        public void onCallBack(String string) {
                            password = string;
                            if (password != null) {
                                requestOTP("+91" + phone);
                            }
                            if (password == null) {
                                btn.setEnabled(true);
                                tilPhone.setError("check the phone");
                                Toast.makeText(AdminLoginActivity.this, "The account with that phone does not exixts", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

                if (i == 1) {
                    Log.e("password in OTP check", "" + password);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification, OTP);
                    VerifyAuth(credential, password);
                    btn.setEnabled(false);
                }

                if (i == 2) {
                    getPassword(new AdminLoginActivity.FirebaseCallBack() {
                        @Override
                        public void onCallBack(String string) {
                            if (tiEdPassword.getText().toString().trim().equals(string)) {

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(sharedPrefrencesClass.SP_PHONE, phone);
                                editor.putString(sharedPrefrencesClass.SP_PASSWORD,string);
                                editor.commit();

                                startActivity(new Intent(AdminLoginActivity.this, LoginCitizenPreExistingAccountActivity.class));
                            } else {
                                tilPassword.setError("check password");
                                Toast.makeText(AdminLoginActivity.this, "please enter the right password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void getPassword(AdminLoginActivity.FirebaseCallBack firebaseCallBack) {
        object.referenceAdminAccountPassword(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firebaseCallBack.onCallBack(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

                Toast.makeText(AdminLoginActivity.this, "OTP sent to number : " + phoneNum, Toast.LENGTH_SHORT).show();

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

    private void VerifyAuth(PhoneAuthCredential credential, String password) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //OTP verified
                    getPassword(new AdminLoginActivity.FirebaseCallBack() {
                        @Override
                        public void onCallBack(String string) {
                            if (string.equals("")) {
                                //new User
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(sharedPrefrencesClass.SP_PHONE, tiEdPhone.getText().toString().trim());
                                editor.commit();

                                //set account
                                startActivity(new Intent(AdminLoginActivity.this, CreateNewAccountLoginActivity.class));
                                finish();
                            }   else {
                                tilPassword.setVisibility(View.VISIBLE);
                                tiEdPassword.setVisibility(View.VISIBLE);
                                i = 2;
                                btn.setText("LOGIN");
                                btn.setEnabled(true);
                            }
                        }
                    });
                    Log.e("password after OTP verified", "" + password);

                } else {
                    btn.setEnabled(true);
                    tilOTP.setError("wrong OTP");
                }
            }
        });
    }

    private void getText() {
        phone = tiEdPhone.getText().toString().trim();
        password = tiEdPassword.getText().toString().trim();
        OTP = tiEdOTP.getText().toString().trim();
    }

    private interface FirebaseCallBack {
        void onCallBack(String string);
    }

    private void initialize() {
        tilPassword = findViewById(R.id.Til_AdminLoginActivity_Password);
        tilPhone = findViewById(R.id.Til_AdminLoginActivity_Phone);
        tilOTP = findViewById(R.id.Til_AdminLoginActivity_OTP);

        tiEdPassword = findViewById(R.id.TiEt_AdminLoginActivity_Password);
        tiEdOTP = findViewById(R.id.TiEt_AdminLoginActivity_OTP);
        tiEdPhone = findViewById(R.id.TiEt_AdminLoginActivity_Phone);

        btn = findViewById(R.id.Btn_AdminLoginActivity_Next);
    }
}