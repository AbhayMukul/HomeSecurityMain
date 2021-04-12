package com.example.homesecuritymain.CommonClasses.BroadcastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.example.homesecuritymain.citizen.Model.ModelLocation;
import com.google.android.gms.location.LocationResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class MyLocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATE = "edmt.dev.googlelocationbackground.UPDATE_LOCATION";
    //Firebase DataBase
    DatabaseReference mUserDatabase;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATE.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    Location location = result.getLastLocation();
                    ModelLocation modelLocation = new ModelLocation(location.getLatitude() + "", location.getLongitude() + "", "", "", "");

                    //set Data
                    mUserDatabase = FirebaseDatabase.getInstance().getReference("Location");

                    mUserDatabase.child("Activity2").child(mUserDatabase.push().getKey()).child("Locations").setValue(modelLocation);
                }
            }
        }
    }
}