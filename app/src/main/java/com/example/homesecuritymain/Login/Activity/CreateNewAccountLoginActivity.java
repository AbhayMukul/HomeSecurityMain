package com.example.homesecuritymain.Login.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.CitizenMainActivity;
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
    private String key, password, passwordCheck, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account_login);

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);

//        key = sharedPreferences.getString(sharedPrefrencesClass.SP_FLATUID, "");
        phone = sharedPreferences.getString(sharedPrefrencesClass.SP_PHONE, "");

        object = new CommonClass();

//        Log.e("key", key);
        Log.e("phone", phone);

        Toast.makeText(this, "key :- " + key + "\nphone :- " + phone, Toast.LENGTH_SHORT).show();

        initialize();

        SharedPreferences.Editor editor = sharedPreferences.edit();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();

                if (password.equals(passwordCheck)) {
                    //update password
                    object.refrenceCitizenLoginPassword(phone).setValue(password);
                    //update date joined
//                    object.referenceCitizenMainDateJoined(key).setValue(new DateAndTimeClass().getCurrentDate());

                    editor.putBoolean(sharedPrefrencesClass.SP_ADMIN,true);

                    startActivity(new Intent(CreateNewAccountLoginActivity.this, WelcomeLoginActivity.class));
                    finish();
                } else {
                    tiPassword.setError("the passwords do not match");
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