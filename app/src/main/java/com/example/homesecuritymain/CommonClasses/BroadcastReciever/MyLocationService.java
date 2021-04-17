package com.example.homesecuritymain.CommonClasses.BroadcastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;

import androidx.annotation.NonNull;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.citizen.Model.ModelLocation;
import com.google.android.gms.location.LocationResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class MyLocationService extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATE = "edmt.dev.googlelocationbackground.UPDATE_LOCATION";
    //Firebase DataBase
    DatabaseReference mUserDatabase;

    CommonClass object;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    String phone,flat,name;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATE.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    Location location = result.getLastLocation();

                    object = new CommonClass();
//
                    phone = sharedPreferences.getString(sharedPrefrencesClass.SP_PHONE, "");
                    flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT,"");
                    name = sharedPreferences.getString(sharedPrefrencesClass.SP_NAME,"");

                    ModelLocation modelLocation = new ModelLocation(location.getLatitude() + "", location.getLongitude() + "", name, new DateAndTimeClass().getCurrentTime(), "");

//                    object.referenceLocationCitizen(flat).child(phone).setValue(modelLocation);
                }
            }
        }
    }
}