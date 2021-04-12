package com.example.homesecuritymain.citizen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DatabaseRefrencesFirebase;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.Login.Model.ModelCitizen;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Model.ModelFamilyMember;
import com.example.homesecuritymain.citizen.Model.ModelLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNewFamilyActivity extends AppCompatActivity {
    //firebase Database
    DatabaseReference mUserDataBaseFamily;
    DatabaseReference mUserDataBaseMain;
    CommonClass object;
    private EditText edName, edPhone;
    private Button btn;
    private Boolean aBoolean = false;
    private ToggleButton toggleButton;
    DatabaseRefrencesFirebase refrence;
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    String flat,phone,name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_family);

        initialize();

        object = new CommonClass();
        refrence = new DatabaseRefrencesFirebase();

        mUserDataBaseFamily = FirebaseDatabase.getInstance().getReference("citizen").child("demo");
        mUserDataBaseMain = FirebaseDatabase.getInstance().getReference("Main");

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT,"");

        Toast.makeText(this, "flat :- " + flat, Toast.LENGTH_SHORT).show();

        Log.e("flat",flat);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextEditText();

                toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        aBoolean = isChecked;
                    }
                });

                String key = FirebaseDatabase.getInstance().getReference().push().getKey();

                ModelLocation modelLocation = new ModelLocation("0.00", "0.00", name, "", "0");
                ModelCitizen modelCitizen = new ModelCitizen(name,phone, key, aBoolean, flat, new DateAndTimeClass().getCurrentDate());

                object.refrenceCitizenLoginPassword(phone).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String string  = (String) snapshot.getValue();

                        if (string == null) {
                            //first time account
                            refrence.mUserDatabaseLoginDetails.child(edPhone.getText().toString().trim()).child("password").setValue("");
                            refrence.mUserDatabaseLoginDetails.child(edPhone.getText().toString().trim()).child("name").setValue(edName.getText().toString().trim());
                            refrence.mUserDatabaseLoginDetails.child(edPhone.getText().toString().trim()).child("first flat account KeyUID").setValue(key);

                            //main
                            refrence.mUserDatabaseLoginMain.child(key).setValue(modelCitizen);

                        }else {
                            //account already registered
                            //create a new flat for the given account with the given phone number
                            refrence.mUserDatabaseLoginMain.child(key).setValue(modelCitizen);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                object.referenceLocationCitizen(flat).child(edPhone.getText().toString().trim()).setValue(modelLocation);
                object.referenceFamilyCitizen(flat).child(edPhone.getText().toString().trim()).setValue(modelCitizen);

                Toast.makeText(AddNewFamilyActivity.this, "A new family memeber has been added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void getTextEditText() {
        name = edName.getText().toString().trim();
        phone = edPhone.getText().toString().trim();
    }

    private void initialize() {
        edName = findViewById(R.id.Ed_AddNewFamilyActivity_Name);
        edPhone = findViewById(R.id.Ed_AddNewFamilyActivity_Phone);

        btn = findViewById(R.id.Btn_AddNewFamilyActivity_Verify);

        toggleButton = findViewById(R.id.Tb_AddNewFamilyActiviy_Admin);
    }
}