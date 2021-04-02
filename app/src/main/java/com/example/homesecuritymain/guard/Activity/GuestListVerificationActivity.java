
package com.example.homesecuritymain.guard.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
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

    ModelGuestList modelGuestList;

    //firebase Database
    DatabaseReference mUserDatabaseCitizen;
    DatabaseReference mUserDatabaseGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list_verification);

        initialize();

        mUserDatabaseCitizen = FirebaseDatabase.getInstance().getReference("citizen");
        mUserDatabaseGuest = FirebaseDatabase.getInstance().getReference("guest");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                getDataFirebaseDatabase();
//                startActivity(new Intent(GuestListVerificationActivity.this,GuardMainActivity.class));
            }
        });
    }

    private void getDataFirebaseDatabase() {
        mUserDatabaseCitizen.child(flat).child("GuestList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(code).exists()){
                    uploadData();
                }else{
                    Toast.makeText(GuestListVerificationActivity.this, "please verify the code and/flat given", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void uploadData() {
        mUserDatabaseCitizen.child(flat).child("GuestList").child(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelGuestList = (ModelGuestList) snapshot.getValue(ModelGuestList.class);

                DateAndTimeClass dateAndTimeClass = new DateAndTimeClass();
                String key = mUserDatabaseCitizen.push().getKey();

                //Models
                ModelActiveGuest modelActiveGuest = new ModelActiveGuest(modelGuestList.getName(),modelGuestList.getFlat(),modelGuestList.getNumber(),modelGuestList.getWork(),key,dateAndTimeClass.getCurrentTime(),false);
                ModelAllGuest modelAllGuest = new ModelAllGuest(modelGuestList.getName(),modelGuestList.getFlat(),modelGuestList.getNumber(),modelGuestList.getWork(),key,dateAndTimeClass.getCurrentDate(),dateAndTimeClass.getCurrentTime(),"100","","","","","","","",false,true);

                //set Citizen
                mUserDatabaseCitizen.child(modelActiveGuest.getFlat()).child("GUEST").child("Active").child(modelActiveGuest.getKeyUID()).setValue(modelActiveGuest);
                mUserDatabaseCitizen.child(modelActiveGuest.getFlat()).child("GUEST").child("All").child(modelActiveGuest.getKeyUID()).setValue(modelAllGuest);

                //set Guest
                mUserDatabaseGuest.child("Active").child(modelActiveGuest.getKeyUID()).setValue(modelActiveGuest);
                mUserDatabaseGuest.child("All").child(modelActiveGuest.getKeyUID()).setValue(modelAllGuest);

                //remove from GuestList
                mUserDatabaseCitizen.child(flat).child("GuestList").child(code).removeValue();

                startActivity(new Intent(GuestListVerificationActivity.this,GuardMainActivity.class));
                finish();
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