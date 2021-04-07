package com.example.homesecuritymain.Login.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.CitizenMainActivity;
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

public class LoginCitizenActivity extends AppCompatActivity {
    //OTP
    String verification;
    PhoneAuthProvider.ForceResendingToken Token;

    private EditText edPhone, edPassword, edOTP;
    private TextView tvOTP;
    private TextInputLayout tilPhone, tilOTP, tilPassword;
    private TextInputEditText tiEdPhone, tiEdOTP, tiEdPassword;
    private Button btn;
    private String phone = "8793215306", password, OTP;
    private int i = 0;
    private CommonClass object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_citizen);

        initialize();

        object = new CommonClass();

        btn.setText("Send OTP");

        tvOTP.setVisibility(View.GONE);
        tiEdOTP.setVisibility(View.GONE);
        tilOTP.setVisibility(View.GONE);
        tiEdPassword.setVisibility(View.GONE);
        tilPassword.setVisibility(View.GONE);

        i = 0;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText();

                if(phone.equals("15306")){
                    startActivity(new Intent(LoginCitizenActivity.this,CreateNewAccountActivity.class));
                    i = 100;
                }

                if (i == 0) {
                    //verification
                    getPassword(new FirebaseCallBack() {
                        @Override
                        public void onCallBack(String string) {
                            password = string;
                            Log.e("password in onCallBack","" + password);
                        }
                    });

                    Log.e("password outside","" + password);

                    if(password != null){
                        btn.setEnabled(false);
                        requestOTP("+91" + phone);
                    }else {
                        tilPhone.setError("check the phone");
                        Toast.makeText(LoginCitizenActivity.this, "The account with that phone does not exixts", Toast.LENGTH_SHORT).show();
                    }

                }

                if (i == 1) {
                    Log.e("password in OTP check","" + password);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verification, OTP);
                    VerifyAuth(credential,password);
                    btn.setEnabled(false);
                }

                if (i == 2) {
                    getPassword(new FirebaseCallBack() {
                        @Override
                        public void onCallBack(String string) {
                            if (tiEdPassword.getText().toString().trim().equals(string)) {
                                startActivity(new Intent(LoginCitizenActivity.this, CitizenMainActivity.class));
                            } else {
                                tilPassword.setError("check password");
                                Toast.makeText(LoginCitizenActivity.this, "please enter the right password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void getPassword(FirebaseCallBack firebaseCallBack) {
        object.refrenceCitizenLoginPassword(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firebaseCallBack.onCallBack(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private interface FirebaseCallBack{
        void onCallBack(String string);
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

                Log.e("password in OTP sent","" + password);

                //visibility set for OTP edittext
                tilOTP.setVisibility(View.VISIBLE);
                tiEdOTP.setVisibility(View.VISIBLE);

                i = 1;

                Toast.makeText(LoginCitizenActivity.this, "OTP sent to number : " + phoneNum, Toast.LENGTH_SHORT).show();

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

    private void VerifyAuth(PhoneAuthCredential credential,String password) {
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //OTP verified
                    getPassword(new FirebaseCallBack() {
                        @Override
                        public void onCallBack(String string) {
                            if(string.equals("")){
                                //new User
                                //set account
                                startActivity(new Intent(LoginCitizenActivity.this, CreateNewAccountLoginActivity.class));
                            }else {
                                tilPassword.setVisibility(View.VISIBLE);
                                tiEdPassword.setVisibility(View.VISIBLE);
                                i = 2;
                                btn.setText("LOGIN");
                                btn.setEnabled(true);
                            }
                        }
                    });
                    Log.e("password after OTP verified","" + password);

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

    private void initialize() {
        tilPassword = findViewById(R.id.Til_LoginCitizenActivity_Password);
        tilPhone = findViewById(R.id.Til_LoginCitizenActivity_Phone);
        tilOTP = findViewById(R.id.Til_LoginCitizenActivity_OTP);

        tiEdPassword = findViewById(R.id.TiEt_LoginCitizenActivity_Password);
        tiEdOTP = findViewById(R.id.TiEt_LoginCitizenActivity_OTP);
        tiEdPhone = findViewById(R.id.TiEt_LoginCitizenActivity_Phone);

        tvOTP = findViewById(R.id.Tv_LoginCitizenActivity_SendOTP);

        btn = findViewById(R.id.Btn_LoginCitizenActivity_Next);
    }
}