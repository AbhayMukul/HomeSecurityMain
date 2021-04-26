package com.example.homesecuritymain.citizen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.Login.Activity.LoginActivityMain;
import com.example.homesecuritymain.Login.Activity.LoginCitizenPreExistingAccountActivity;
import com.example.homesecuritymain.Login.Activity.LoginPreExistingAccountSplashScreenActivity;
import com.example.homesecuritymain.R;

public class SettingActivityCitizen extends AppCompatActivity {
    private TextView tvChangePhone,tvChangePassword,tvChangeFlat,tvExit;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_citizen);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);

        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvChangeFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivityCitizen.this, LoginCitizenPreExistingAccountActivity.class));
            }
        });

        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exit) {
                    exit();
                    finish();
                    startActivity(new Intent(SettingActivityCitizen.this, LoginActivityMain.class));
                }else {
                    Toast.makeText(SettingActivityCitizen.this, "please enter Exit again to confirm", Toast.LENGTH_SHORT).show();
                    exit = true;
                }
            }
        });

    }

    private void exit() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(sharedPrefrencesClass.SP_ACCOUNTTYPE,"");
        editor.putString(sharedPrefrencesClass.SP_ADMIN,"");
        editor.putString(sharedPrefrencesClass.SP_FLAT,"");
        editor.putString(sharedPrefrencesClass.SP_FLATUID,"");
        editor.putString(sharedPrefrencesClass.SP_LOGGEDIN,"");
        editor.putString(sharedPrefrencesClass.SP_NAME,"");
        editor.putString(sharedPrefrencesClass.SP_PHONE,"");

        editor.commit();
    }

    private void initialize() {
        tvExit = findViewById(R.id.Tv_SettingActivityCitizen_Exit);
        tvChangePhone = findViewById(R.id.Tv_SettingActivityCitizen_ChangePhone);
        tvChangeFlat = findViewById(R.id.Tv_SettingActivityCitizen_ChangeFlat);
        tvChangePassword = findViewById(R.id.Tv_SettingActivityCitizen_ChangePassword);
    }
}