package com.example.homesecuritymain.citizen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.TrailActivity;
import com.example.homesecuritymain.citizen.Model.ModelLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class CitizenMapAccountActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    //google API for location services
    FusedLocationProviderClient fusedLocationProviderClient;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    String flat,phone;

    CommonClass object = new CommonClass();

    private Spinner spinner;
    private TextView textView;
    private LinearLayout linearLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_map_account);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT,"");
        phone = sharedPreferences.getString(sharedPrefrencesClass.SP_PHONE,"");

        spinner.setEnabled(sharedPreferences.getBoolean(sharedPrefrencesClass.SP_ADMIN,true));
        textView.setEnabled(sharedPreferences.getBoolean(sharedPrefrencesClass.SP_ADMIN,true));
        linearLayout.setVisibility(View.GONE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayout.getVisibility() == View.VISIBLE){
                    linearLayout.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.ic_down_chevron);
                }else {
                    linearLayout.setVisibility(View.VISIBLE);
                    imageView.setImageResource(R.drawable.ic_up_chevron);
                }
            }
        });

        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Duration, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategory);

        object.referenceLocationCitizen(flat).child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelLocation modelLocation = (ModelLocation) snapshot.getValue(ModelLocation.class);

                spinner.setSelection(adapterCategory.getPosition(modelLocation.getDuration()));
                textView.setText("Last location update on : " + modelLocation.getTime());

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        object.referenceLocationCitizen(flat).child(phone).child("duration").setValue(spinner.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng latLng = new LatLng(object.StringToDouble(modelLocation.getLatitude()), object.StringToDouble(modelLocation.getLongitude()));
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("here");
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                        googleMap.addMarker(markerOptions).showInfoWindow();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialize() {
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_CitizenMapAccountActivity);

        textView = findViewById(R.id.Tv_CitizenMapAccountActivity_LocationUpdateTime);
        spinner = findViewById(R.id.Sp_CitizenMapAccountActivity_Duration);
        linearLayout = findViewById(R.id.Ll_CitizenMapAccountActivity);
        imageView = findViewById(R.id.Iv_CitizenMapAccountActivity);
    }

    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CitizenMapAccountActivity.this);
        if (ActivityCompat.checkSelfPermission(CitizenMapAccountActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //permission granted
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("here");
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                                googleMap.addMarker(markerOptions).showInfoWindow();
                            }
                        });
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(CitizenMapAccountActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
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