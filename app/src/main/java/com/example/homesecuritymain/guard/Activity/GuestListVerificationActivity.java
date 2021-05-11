
package com.example.homesecuritymain.guard.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuestGuard;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelAllGuest;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelGuestList;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.GuestListActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuestListVerificationActivity extends AppCompatActivity {
    private EditText edFlat,edCode;
    private Button button;

    private String flat,code;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    String id;

    //firebase Database
    DatabaseReference mUserDatabaseCitizen;
    DatabaseReference mUserDatabaseGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list_verification);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(sharedPrefrencesClass.SP_GUARDID,"");

        mUserDatabaseCitizen = FirebaseDatabase.getInstance().getReference("citizen");
        mUserDatabaseGuest = FirebaseDatabase.getInstance().getReference("guest");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                getDataFirebaseDatabase();
            }
        });
    }

    private void getDataFirebaseDatabase() {
        mUserDatabaseCitizen.child(flat).child("GuestList").child(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelGuestList model = (ModelGuestList) snapshot.getValue(ModelGuestList.class);
                if(model == null){
                    Toast.makeText(GuestListVerificationActivity.this, "The code for the given flat doesnt exist", Toast.LENGTH_SHORT).show();
                }else {
                    DateAndTimeClass dateAndTimeClass = new DateAndTimeClass();
                    String key = mUserDatabaseCitizen.push().getKey();

                    String name = model.getName();
                    String flat = model.getFlat();
                    String number = model.getNumber();
                    String work = model.getWork();

                    //Models
                    ModelActiveGuestGuard modelActiveGuestGuard = new ModelActiveGuestGuard(name,flat,number,work,key,dateAndTimeClass.getCurrentTime(),false,false);
                    ModelAllGuest modelAllGuest = new ModelAllGuest(name,flat,number,work,key,dateAndTimeClass.getCurrentDate(),dateAndTimeClass.getCurrentTime(),id,"","","","","","","",false,true);

                    //set Citizen
                    mUserDatabaseCitizen.child(flat).child("GUEST").child("Active").child(key).setValue(modelActiveGuestGuard);
                    mUserDatabaseCitizen.child(flat).child("GUEST").child("All").child(key).setValue(modelAllGuest);

                    //set Guest
                    mUserDatabaseGuest.child("Active").child(key).setValue(modelActiveGuestGuard);
                    mUserDatabaseGuest.child("All").child(key).setValue(modelAllGuest);

                    //remove from GuestList
                    mUserDatabaseCitizen.child(flat).child("GuestList").child(code).removeValue();

                    startActivity(new Intent(GuestListVerificationActivity.this,GuardMainActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getData() {
        flat = edFlat.getText().toString().trim();
        code = edCode.getText().toString().trim();
    }

    private void initialize() {
        edCode = findViewById(R.id.Ed_GuestListVerificationActivity_Code);
        edFlat = findViewById(R.id.Ed_GuestListVerificationActivity_Flat);

        button = findViewById(R.id.Btn_GuestListVerificationActivity_Verify);
    }
}