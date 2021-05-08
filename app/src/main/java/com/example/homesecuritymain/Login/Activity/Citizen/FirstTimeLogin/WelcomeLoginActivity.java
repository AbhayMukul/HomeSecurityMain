package com.example.homesecuritymain.Login.Activity.Citizen.FirstTimeLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.Login.Model.ModelCitizen;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.CitizenMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class WelcomeLoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    CommonClass object;

    String phone,key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_login);

        object = new CommonClass();

        key = getIntent().getStringExtra("keyFlat");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                object.mUserDatabaseLoginMain.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ModelCitizen model = (ModelCitizen) snapshot.getValue(ModelCitizen.class);

                        //set sharedPrefrences
                        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(sharedPrefrencesClass.SP_FLATUID, model.getKeyUID());
                        editor.putBoolean(sharedPrefrencesClass.SP_LOGGEDIN, true);
                        editor.putString(sharedPrefrencesClass.SP_ACCOUNTTYPE, "citizen");
                        editor.putString(sharedPrefrencesClass.SP_NAME, model.getName());
                        editor.putString(sharedPrefrencesClass.SP_PHONE, model.getPhone());
                        editor.putBoolean(sharedPrefrencesClass.SP_ADMIN, model.getADMIN());
                        editor.putString(sharedPrefrencesClass.SP_FLAT, model.getFlat());
                        editor.commit();

                        startActivity(new Intent(WelcomeLoginActivity.this,CitizenApplicationDescriptionToFirstTimeUserActivity.class));

                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }, 5000);

    }
}