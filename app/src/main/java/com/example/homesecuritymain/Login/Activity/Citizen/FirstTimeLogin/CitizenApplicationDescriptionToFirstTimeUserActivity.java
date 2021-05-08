package com.example.homesecuritymain.Login.Activity.Citizen.FirstTimeLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.BroadcastReciever.MyLocationService;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.Login.Activity.Common.SplashScreen;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentFamily;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentGrievance;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentGuestList;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentGuests;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentIntroduction;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentLetStart;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentNoticeBoard;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentWelcome;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.FragmentAdapter.ViewPageAdapterGuestActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class CitizenApplicationDescriptionToFirstTimeUserActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    String phone,flat;

    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_application_description_to_first_time_user);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        phone = sharedPreferences.getString(sharedPrefrencesClass.SP_PHONE, "");
        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT, "");

        //set location to FirebaseDatabase
        setLocation();

        setViewPager();

        tabLayout.setVisibility(View.GONE);
    }

    private void setViewPager() {
        final ViewPageAdapterGuestActivity viewPageAdapter = new ViewPageAdapterGuestActivity(getSupportFragmentManager());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPageAdapter.addFragment(FragmentWelcome.getInstance(),"");
                viewPageAdapter.addFragment(FragmentIntroduction.getInstance(),"");
                viewPageAdapter.addFragment(FragmentGuests.getInstance(),"");
                viewPageAdapter.addFragment(FragmentGuestList.getInstance(),"");
                viewPageAdapter.addFragment(FragmentNoticeBoard.getInstance(),"");
                viewPageAdapter.addFragment(FragmentFamily.getInstance(),"");
                viewPageAdapter.addFragment(FragmentGrievance.getInstance(),"");
                viewPageAdapter.addFragment(FragmentLetStart.getInstance(),"");

                viewPager.setAdapter(viewPageAdapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    private void initialize() {
        viewPager = findViewById(R.id.Vp_CitizenApplicationDescriptionToFirstTimeUserActivity);
        tabLayout = findViewById(R.id.Tb_CitizenApplicationDescriptionToFirstTimeUserActivity);
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
        locationRequest.setInterval(10 * 1000);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //location current------------------------------------------------------------------------------

    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CitizenApplicationDescriptionToFirstTimeUserActivity.this);
        if (ActivityCompat.checkSelfPermission(CitizenApplicationDescriptionToFirstTimeUserActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
            ActivityCompat.requestPermissions(CitizenApplicationDescriptionToFirstTimeUserActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
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