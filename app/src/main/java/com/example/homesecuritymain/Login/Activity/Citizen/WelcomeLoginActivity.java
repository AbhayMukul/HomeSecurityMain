package com.example.homesecuritymain.Login.Activity.Citizen;

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
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.CitizenMainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class WelcomeLoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    CommonClass object;

    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_login);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);

                phone = sharedPreferences.getString(sharedPrefrencesClass.SP_PHONE, "");

                object = new CommonClass();

                //set Login Details parameters
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(sharedPrefrencesClass.SP_LOGGEDIN, true);
                editor.putString(sharedPrefrencesClass.SP_ACCOUNTTYPE, "citizen");
                editor.commit();

                object.referenceCitizenLoginKeyUID(phone).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String key = snapshot.getValue().toString();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(sharedPrefrencesClass.SP_FLATUID, key);
                        editor.putString(sharedPrefrencesClass.SP_ACCOUNTTYPE, "citizen");
                        editor.commit();

                        object.referenceCitizenMainDateJoined(key).setValue(new DateAndTimeClass().getCurrentDate());

                        object.referenceCitizenMainAdmin(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Boolean bn = (Boolean) snapshot.getValue();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(sharedPrefrencesClass.SP_ADMIN, "" + bn);
                                editor.commit();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        object.referenceCitizenMain(key).child("flat").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String flat = (String) snapshot.getValue();

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(sharedPrefrencesClass.SP_FLAT, flat);
                                editor.commit();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        object.referenceCitizenMain(key).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(sharedPrefrencesClass.SP_NAME, snapshot.getValue().toString());
                                editor.commit();

                                Log.e("getValue()", snapshot.getKey());
                                Log.e("name", snapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                startActivity(new Intent(WelcomeLoginActivity.this, CitizenMainActivity.class));
                finish();
            }
        }, 5000);

    }
}