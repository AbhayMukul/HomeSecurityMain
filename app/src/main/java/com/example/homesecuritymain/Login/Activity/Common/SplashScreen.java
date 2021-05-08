package com.example.homesecuritymain.Login.Activity.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.homesecuritymain.Admin.Model.GuardDetailsModel;
import com.example.homesecuritymain.CommonClasses.BroadcastReciever.MyLocationService;
import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.Login.Activity.Guard.InValidLoginGuardActivity;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.CitizenMainActivity;
import com.example.homesecuritymain.guard.Activity.GuardMainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class SplashScreen extends AppCompatActivity {
    Integer INTERVAL_UPDATE_MAP = 10;
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    LinearLayout linearLayout;

    String accountType, key;
    LocationRequest locationRequest;
    Animation splashTopDown;
    CommonClass object;
    //google API for location services
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        object = new CommonClass();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        accountType = sharedPreferences.getString(sharedPrefrencesClass.SP_ACCOUNTTYPE, "");

        linearLayout = findViewById(R.id.Ll_SplashScreen);
        splashTopDown = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.animation_top_down);

        linearLayout.setAnimation(splashTopDown);

        Log.e("INTERVAL", INTERVAL_UPDATE_MAP + "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (accountType.equals("citizen")) {
                    //open citizen account
                    startActivity(new Intent(SplashScreen.this, CitizenMainActivity.class));

                    //set location to FirebaseDatabase
                    setLocation();

                    finish();
                } else if (accountType.equals("")) {
                    startActivity(new Intent(SplashScreen.this, LoginActivityMain.class));
                    finish();
                } else if (accountType.equals("guard")) {
                    key = sharedPreferences.getString(sharedPrefrencesClass.SP_GUARDID, "");
                    object.mUserDatabaseGuardLogin.child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            GuardDetailsModel model = (GuardDetailsModel) snapshot.getValue(GuardDetailsModel.class);

                            if (model.getACTIVE() && new DateAndTimeClass().ShiftTimings(model.getShift())) {
                                startActivity(new Intent(SplashScreen.this, GuardMainActivity.class).putExtra("modelGuardAll", model));
                                finish();
                            } else {
                                Intent intent = new Intent(SplashScreen.this, InValidLoginGuardActivity.class).putExtra("active", model.getACTIVE());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        }, 2500);

    }

    private void setLocation() {
        setDexter();
    }

    public void setDexter() {
        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                upDateLocation();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }).check();
    }

    //location update ------------------------------------------------------------------------------

    private void upDateLocation() {
        setLocationRequest();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
    }

    private void setLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(INTERVAL_UPDATE_MAP * 1000);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //location current------------------------------------------------------------------------------

    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(SplashScreen.this);
        if (ActivityCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //permission granted
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceLocation();
            }
        } else
            Toast.makeText(this, "permission needed", Toast.LENGTH_SHORT).show();
    }

}