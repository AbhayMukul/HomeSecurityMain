package com.example.homesecuritymain.Login.Activity.Guard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.Admin.Model.GuardDetailsModel;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.guard.Activity.GuardMainActivity;

public class NewGuardActivitySplashScreenActivity extends AppCompatActivity {
    GuardDetailsModel model;
    TextView tvName;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guard_splash_screen);

        model = (GuardDetailsModel) getIntent().getSerializableExtra("modelGuardAll");

        initialize();

        tvName.setText("Hi, " + model.getName());

        setDataSharedPrefrence();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(NewGuardActivitySplashScreenActivity.this, GuardMainActivity.class));
                finish();
            }
        },2500);
    }

    private void setDataSharedPrefrence() {
        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(sharedPrefrencesClass.SP_ACCOUNTTYPE,"guard");
        editor.putString(sharedPrefrencesClass.SP_GUARDID,model.getID());
        editor.putBoolean(sharedPrefrencesClass.SP_LOGGEDIN,true);
        editor.putString(sharedPrefrencesClass.SP_NAME,model.getName());
        editor.putString(sharedPrefrencesClass.SP_PHONE,model.getPhone());
        editor.putString(sharedPrefrencesClass.SP_SHIFT,model.getShift());

        editor.commit();
    }

    private void initialize() {
        tvName = findViewById(R.id.Tv_NewGuardActivitySplashScreenActivity_Name);
    }
}