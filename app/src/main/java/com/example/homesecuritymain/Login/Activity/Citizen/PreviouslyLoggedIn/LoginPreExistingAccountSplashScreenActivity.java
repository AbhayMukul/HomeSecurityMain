package com.example.homesecuritymain.Login.Activity.Citizen.PreviouslyLoggedIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.BroadcastReciever.MyLocationService;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.CitizenMainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class LoginPreExistingAccountSplashScreenActivity extends AppCompatActivity {
    private TextView tvName;
    private Integer INTERVAL_UPDATE_MAP = 10;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    LocationRequest locationRequest;
    //google API for location services
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pre_existing_account_splash_screen);

        sharedPrefrencesClass = new SharedPrefrencesClass();

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        tvName.setText("Hi, " + sharedPreferences.getString(sharedPrefrencesClass.SP_NAME,""));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setLocation();
                startActivity(new Intent(LoginPreExistingAccountSplashScreenActivity.this, CitizenMainActivity.class));
            }
        },2500);

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
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LoginPreExistingAccountSplashScreenActivity.this);
        if (ActivityCompat.checkSelfPermission(LoginPreExistingAccountSplashScreenActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
            ActivityCompat.requestPermissions(LoginPreExistingAccountSplashScreenActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
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


    private void initialize() {
        tvName = findViewById(R.id.Tv_LoginPreExistingAccountSplashScreenActivity_Name);
    }
}