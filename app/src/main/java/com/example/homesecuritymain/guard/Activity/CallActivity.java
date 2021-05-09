package com.example.homesecuritymain.guard.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DatabaseRefrencesFirebase;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuestGuard;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelAllGuest;
import com.example.homesecuritymain.Login.Adapter.AdapterPreExistingAccoutFlat;
import com.example.homesecuritymain.Login.Model.ModelCitizen;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.FamilyActivity;
import com.example.homesecuritymain.citizen.Adapters.AdapterFamily;
import com.example.homesecuritymain.citizen.Model.ModelFamilyMember;
import com.example.homesecuritymain.guard.Adapters.AdapterCallCitizen;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class CallActivity extends AppCompatActivity implements Serializable {
    private static final int REQUEST_CALL = 1;
    ModelActiveGuest modelActiveGuest;
    DatabaseReference mUserDatabaseCitizen;
    DatabaseReference mUserDatabaseGuest;
    FirebaseRecyclerOptions<ModelFamilyMember> option;
    FirebaseRecyclerAdapter<ModelFamilyMember, AdapterCallCitizen> firebaseRecyclerAdapter;
    CommonClass object;
    String id;
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    private Button btnAllow, btnNotAllow;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(sharedPrefrencesClass.SP_GUARDID,"");

        //get modelActive
        Gson gson = new Gson();
        String json = sharedPreferences.getString(sharedPrefrencesClass.SP_JSON, "");
        modelActiveGuest = gson.fromJson(json, ModelActiveGuest.class);

        object = new CommonClass();
        mUserDatabaseCitizen = FirebaseDatabase.getInstance().getReference("citizen");
        mUserDatabaseGuest = FirebaseDatabase.getInstance().getReference("guest");

        btnAllow.setEnabled(false);
        btnNotAllow.setEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Toast.makeText(this, modelActiveGuest.getFlat(), Toast.LENGTH_SHORT).show();

        option = new FirebaseRecyclerOptions.Builder<ModelFamilyMember>().setQuery(FirebaseDatabase.getInstance().getReference().child("citizen").child(modelActiveGuest.getFlat()).child("family"), ModelFamilyMember.class).build();
        load();

        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAllow.setEnabled(false);
                uploadAllow();
                openMain();
            }
        });

        btnNotAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNotAllow.setEnabled(false);
                uploadNotAllow();
                openMain();
            }
        });
    }

    private void load() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelFamilyMember, AdapterCallCitizen>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterCallCitizen adapter, int i, @NonNull ModelFamilyMember model) {
                i++;
                adapter.tvCitizenName.setText("Family Member " + i);
                adapter.tvCitizenName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnAllow.setEnabled(true);
                        btnNotAllow.setEnabled(true);
                        if (model.getPhone().trim().length() > 0) {
                            if (ContextCompat.checkSelfPermission(CallActivity.this,
                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(CallActivity.this,
                                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                            } else {
                                String dial = "tel:" + model.getPhone();
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                            }
                        } else {
                            Toast.makeText(CallActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @NonNull
            @Override
            public AdapterCallCitizen onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_guard_guest_home_call, parent, false);
                return new AdapterCallCitizen(v);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void uploadNotAllow() {
        //set in Active and All
        DateAndTimeClass dateAndTimeClass = new DateAndTimeClass();

        //create model
        ModelAllGuest modelAllGuest = new ModelAllGuest(modelActiveGuest.getName(), modelActiveGuest.getFlat(), modelActiveGuest.getNumber(), modelActiveGuest.getWork(), modelActiveGuest.getKeyUID(), dateAndTimeClass.getCurrentDate(), dateAndTimeClass.getCurrentTime(), id, "", "", "", "", "", "", "", false, false);

        //set Citizen
        mUserDatabaseCitizen.child(modelActiveGuest.getFlat()).child("GUEST").child("All").setValue(modelAllGuest);

        //set Guest
        mUserDatabaseGuest.child("All").setValue(modelAllGuest);
    }

    private void uploadAllow() {
        //set in Active and All
        DateAndTimeClass dateAndTimeClass = new DateAndTimeClass();

        //create model
        ModelAllGuest modelAllGuest = new ModelAllGuest(modelActiveGuest.getName(), modelActiveGuest.getFlat(), modelActiveGuest.getNumber(), modelActiveGuest.getWork(), modelActiveGuest.getKeyUID(), dateAndTimeClass.getCurrentDate(), dateAndTimeClass.getCurrentTime(), "id", "CitizenID", "", "", "", "", "", "", false, true);
        ModelActiveGuestGuard modelActiveGuestGuard = new ModelActiveGuestGuard(modelActiveGuest.getName(), modelActiveGuest.getFlat(), modelActiveGuest.getNumber(), modelActiveGuest.getWork(), modelActiveGuest.getKeyUID(), modelActiveGuest.getTimeIn(), false, false);

        //set Citizen
        object.referenceGuestCitizenActive("demo").child(modelActiveGuest.getKeyUID()).setValue(modelActiveGuest);
        object.referenceGuestCitizenAll("demo").child(modelActiveGuest.getKeyUID()).setValue(modelAllGuest);

        //set Guest
        object.referenceGuestGuardActive().child(modelActiveGuest.getKeyUID()).setValue(modelActiveGuestGuard);
        object.referenceGuestGuardAll().child(modelActiveGuest.getKeyUID()).setValue(modelAllGuest);
    }


    private void openMain() {
        startActivity(new Intent(CallActivity.this, GuardMainActivity.class));
        finish();
    }

    private void initialize() {
        btnAllow = findViewById(R.id.Btn_CallActivity_CitizenAllow);
        btnNotAllow = findViewById(R.id.Btn_CallActivity_CitizenNotAllow);

        recyclerView = findViewById(R.id.Rv_CallActivity);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission ACCEPTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}