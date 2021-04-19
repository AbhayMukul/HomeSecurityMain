package com.example.homesecuritymain.CommonClasses.BroadcastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyLocationService extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATE = "edmt.dev.googlelocationbackground.UPDATE_LOCATION";

    CommonClass object;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    String phone, flat, name;

    Integer timeUpdate;

    DateAndTimeClass dateAndTimeClass;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        dateAndTimeClass = new DateAndTimeClass();

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATE.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);
                if (result != null) {
                    Location location = result.getLastLocation();

                    object = new CommonClass();

                    phone = sharedPreferences.getString(sharedPrefrencesClass.SP_PHONE, "");
                    flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT, "");
                    name = sharedPreferences.getString(sharedPrefrencesClass.SP_NAME, "");

                    object.referenceLocationCitizen(flat).child(phone).child("duration").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //duration
                            String string = snapshot.getValue().toString();

                            //to get last update time
                            if (!string.equals("STOP")) {
                                object.referenceLocationCitizen(flat).child(phone).child("time").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String time = snapshot.getValue().toString();

                                        Log.e("time", time);
                                        Log.e("duration", string);

                                        SimpleDateFormat df = new SimpleDateFormat("kk:mm:ss");

                                        Date d = dateAndTimeClass.StringToDate(time);
                                        Calendar cal = Calendar.getInstance();
                                        cal.setTime(d);

                                        timeUpdate = object.getLocationUpdateDuration(string);

                                        cal.add(Calendar.SECOND, timeUpdate);

                                        String newTime = df.format(cal.getTime());

                                        Log.e("update at time", newTime);

                                        //add duration to last time
                                        Log.e("Date Check", dateAndTimeClass.DateCompare(newTime) + "");
                                        if (dateAndTimeClass.DateCompare(newTime)) {
                                            Log.e("updated at that time", "");
                                            object.referenceLocationCitizen(flat).child(phone).child("latitude").setValue(location.getLatitude() + "");
                                            object.referenceLocationCitizen(flat).child(phone).child("longitude").setValue(location.getLongitude() + "");
                                            object.referenceLocationCitizen(flat).child(phone).child("time").setValue(dateAndTimeClass.getCurrentTime() + "");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                            if (string.equals("LIVE")) {
                                Log.e("updated Live", "");
                                Log.e("duration", string);
                                object.referenceLocationCitizen(flat).child(phone).child("latitude").setValue(location.getLatitude() + "");
                                object.referenceLocationCitizen(flat).child(phone).child("longitude").setValue(location.getLongitude() + "");
                                object.referenceLocationCitizen(flat).child(phone).child("time").setValue(dateAndTimeClass.getCurrentTime() + "");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        }
    }
}