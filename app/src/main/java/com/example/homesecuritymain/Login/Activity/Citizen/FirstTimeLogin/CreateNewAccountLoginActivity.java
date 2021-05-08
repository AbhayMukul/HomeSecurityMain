package com.example.homesecuritymain.Login.Activity.Citizen.FirstTimeLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class CreateNewAccountLoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    CommonClass object;
    private TextInputLayout tiPassword, tiPasswordCheck;
    private TextInputEditText tiEdPassword, tiEdpasswordCheck;
    private Button btn;
    private String password, passwordCheck, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account_login);

        phone = getIntent().getStringExtra("phone");

        object = new CommonClass();

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();

                if (password.equals(passwordCheck)) {
                    object.referenceCitizenLoginKeyUID(phone).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            btn.setEnabled(false);
                            String key = snapshot.getValue().toString().trim();

                            editor.putBoolean(sharedPrefrencesClass.SP_ADMIN,true);
                            editor.putString(sharedPrefrencesClass.SP_FLATUID,key);

                            editor.commit();

                            //update password
                            object.refrenceCitizenLoginPassword(phone).setValue(password);
                            //update date joined
                            object.referenceCitizenMainDateJoined(key).setValue(new DateAndTimeClass().getCurrentDate()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(CreateNewAccountLoginActivity.this, WelcomeLoginActivity.class).putExtra("keyFlat",key));
                                        finish();
                                    }else {
                                        btn.setEnabled(true);
                                        Toast.makeText(CreateNewAccountLoginActivity.this, "please retry", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    tiPassword.setError("the passwords do not match");
                    tiPasswordCheck.setError("the passwords do not match");
                }
            }
        });

    }

    private void getdata() {
        password = tiEdPassword.getText().toString().trim();
        passwordCheck = tiEdpasswordCheck.getText().toString().trim();
    }

    private void initialize() {
        tiEdPassword = findViewById(R.id.TiEt_CreateNewAccountLoginActivity_Password);
        tiEdpasswordCheck = findViewById(R.id.TiEt_CreateNewAccountLoginActivity_PasswordCheck);

        tiPassword = findViewById(R.id.Til_CreateNewAccountLoginActivity_Password);
        tiPasswordCheck = findViewById(R.id.Til_CreateNewAccountLoginActivity_PasswordCheck);

        btn = findViewById(R.id.Btn_CreateNewAccountLoginActivity_Next);
    }
}