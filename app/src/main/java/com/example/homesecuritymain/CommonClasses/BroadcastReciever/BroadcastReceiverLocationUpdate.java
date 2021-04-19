package com.example.homesecuritymain.CommonClasses.BroadcastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.Login.SplashScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BroadcastReceiverLocationUpdate extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    String flat, phone;

    CommonClass object;

    SplashScreen splashScreen;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);

        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT, "");
        phone = sharedPreferences.getString(sharedPrefrencesClass.SP_PHONE, "");

        object = new CommonClass();
        splashScreen = new SplashScreen();

        object.referenceLocationCitizen(flat).child(phone).child("duration").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String string = snapshot.getValue().toString();

                sharedPreferences = context.getSharedPreferences(sharedPrefrencesClass.LocationDetails, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(sharedPrefrencesClass.SP_UPDATETIME, string);
                editor.commit();

                Log.e("duration", string);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
