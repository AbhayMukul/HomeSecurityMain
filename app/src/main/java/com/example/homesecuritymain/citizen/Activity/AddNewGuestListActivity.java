package com.example.homesecuritymain.citizen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class AddNewGuestListActivity extends AppCompatActivity {
    public EditText edName,edPhone,edCode,edWork;
    private CommonClass object = new CommonClass();

    public Button btn;

    //OTP
    PhoneAuthProvider.ForceResendingToken Token;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass = new SharedPrefrencesClass();
    String flat,verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_guest_list);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT,"");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send OTP to number of guest
                object.referenceGuestListCitizen(flat).child(edCode.getText().toString().trim()).child("code").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String code = (String) snapshot.getValue();

                        if(code == null){
                           requestOTP("+91" + edPhone.getText().toString().trim());
                        }else {
                            Toast.makeText(AddNewGuestListActivity.this, "The code exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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

                ModelGuestList modelGuestList = new ModelGuestList(edName.getText().toString().trim(),"demo",edPhone.getText().toString().trim(),edWork.getText().toString().trim(),edCode.getText().toString().trim());

                Intent intent = new Intent(getBaseContext(),GuestListOTPVerificationActivity.class);

                intent.putExtra("verificationOTP",verification);
                intent.putExtra("data",modelGuestList);

                startActivity(new Intent(intent));

                finish();
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
        edName = findViewById(R.id.Ed_AddNewGuestListActivityn_Name);
        edPhone = findViewById(R.id.Ed_AddNewGuestListActivity_PhoneNumber);
        edCode = findViewById(R.id.Ed_AddNewGuestListActivity_Code);
        edWork = findViewById(R.id.Ed_AddNewGuestListActivity_Work);

        btn = findViewById(R.id.Btn_AddNewGuestListActivity_Done);
    }
}