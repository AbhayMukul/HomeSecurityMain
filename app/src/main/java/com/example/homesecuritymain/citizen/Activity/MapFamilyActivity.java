package com.example.homesecuritymain.citizen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Adapters.AdapterFamily;
import com.example.homesecuritymain.citizen.Model.ModelFamilyMember;
import com.example.homesecuritymain.citizen.Model.ModelLocation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.DeflaterOutputStream;

public class MapFamilyActivity extends AppCompatActivity {

    //Map
    SupportMapFragment supportMapFragment;
    Marker marker = null;

    //Firebase Database
    DatabaseReference databaseReference;

    String phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_family);

        initialize();

        phone = getIntent().getStringExtra("phone");

        databaseReference = FirebaseDatabase.getInstance().getReference("citizen");

        getLocationLatLng();
    }

    private void initialize() {
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }

    private void setMap(Double latitude, Double longitude, String name) {
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latLng = new LatLng(latitude, longitude);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10f));
                marker = googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(name));
            }
        });
    }

    private void getLocationLatLng() {
        databaseReference.child("demo").child("location").child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelLocation modelLocation = snapshot.getValue(ModelLocation.class);

                if(marker != null){
                    marker.remove();
                }

                setMap(new CommonClass().StringToDouble(modelLocation.getLatitude()), new CommonClass().StringToDouble(modelLocation.getLongitude()), modelLocation.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}