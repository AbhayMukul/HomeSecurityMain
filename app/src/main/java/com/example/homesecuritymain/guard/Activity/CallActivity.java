package com.example.homesecuritymain.guard.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelAllGuest;
import com.example.homesecuritymain.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class CallActivity extends AppCompatActivity implements Serializable {
    private TextView tvCitizenNumber;
    private Button btnCall, btnAllow, btnNotAllow;

    private String d,time;

    ModelActiveGuest modelActiveGuest;

    private static final int REQUEST_CALL = 1;

    DatabaseReference mUserDatabaseCitizen;
    DatabaseReference mUserDatabaseGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);


        initialize();

        mUserDatabaseCitizen = FirebaseDatabase.getInstance().getReference("citizen");
        mUserDatabaseGuest = FirebaseDatabase.getInstance().getReference("guest");

        btnAllow.setEnabled(false);
        btnNotAllow.setEnabled(false);

        //get modelActive
        modelActiveGuest = (ModelActiveGuest) getIntent().getSerializableExtra("data");
        Toast.makeText(this,"" + modelActiveGuest.getName(), Toast.LENGTH_SHORT).show();

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                linearLayout.setEnabled(true);
                btnAllow.setEnabled(true);
                btnNotAllow.setEnabled(true);
            }
        });

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

    private void uploadNotAllow() {
        //set in Active and All
        DateAndTimeClass dateAndTimeClass = new DateAndTimeClass();

        //create model
        ModelAllGuest modelAllGuest = new ModelAllGuest(modelActiveGuest.getName(),modelActiveGuest.getFlat(),modelActiveGuest.getNumber(),modelActiveGuest.getWork(),modelActiveGuest.getKeyUID(),dateAndTimeClass.getCurrentDate(),dateAndTimeClass.getCurrentTime(),"100","CitizenID","","","","","","",false,false);

        //set Citizen
        mUserDatabaseCitizen.child(modelActiveGuest.getFlat()).child("GUEST").child("All").setValue(modelAllGuest);

        //set Guest
        mUserDatabaseGuest.child("All").setValue(modelAllGuest);
    }

    private void uploadAllow() {
        //set in Active and All
        DateAndTimeClass dateAndTimeClass = new DateAndTimeClass();

        //create model
        ModelAllGuest modelAllGuest = new ModelAllGuest(modelActiveGuest.getName(),modelActiveGuest.getFlat(),modelActiveGuest.getNumber(),modelActiveGuest.getWork(),modelActiveGuest.getKeyUID(),dateAndTimeClass.getCurrentDate(),dateAndTimeClass.getCurrentTime(),"100","CitizenID","","","","","","",false,true);

        //set Citizen
        mUserDatabaseCitizen.child(modelActiveGuest.getFlat()).child("GUEST").child("Active").child(modelActiveGuest.getKeyUID()).setValue(modelActiveGuest);
        mUserDatabaseCitizen.child(modelActiveGuest.getFlat()).child("GUEST").child("All").child(modelActiveGuest.getKeyUID()).setValue(modelAllGuest);

        //set Guest
        mUserDatabaseGuest.child("Active").child(modelActiveGuest.getKeyUID()).setValue(modelActiveGuest);
        mUserDatabaseGuest.child("All").child(modelActiveGuest.getKeyUID()).setValue(modelAllGuest);
    }


    private void openMain() {
        startActivity(new Intent(CallActivity.this, GuardMainActivity.class));
        finish();
    }

    private void initialize() {
        btnCall = findViewById(R.id.Btn_PhoneCitizen);
        btnAllow = findViewById(R.id.Btn_CitizenAllow);
        btnNotAllow = findViewById(R.id.Btn_CitizenNotAllow);

//        linearLayout = findViewById(R.id.Ll_CallActivity);
    }

}