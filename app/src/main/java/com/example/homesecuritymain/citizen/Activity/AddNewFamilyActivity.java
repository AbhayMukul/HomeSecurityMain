package com.example.homesecuritymain.citizen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Model.ModelFamilyMember;
import com.example.homesecuritymain.citizen.Model.ModelLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewFamilyActivity extends AppCompatActivity {
    private EditText edName,edPassword,edPhone;
    private Button btn;

    private Boolean aBoolean = false;

    private ToggleButton toggleButton;

    //firebase Database
    DatabaseReference mUserDataBaseFamily;
    DatabaseReference mUserDataBaseMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_family);

        initialize();

        mUserDataBaseFamily = FirebaseDatabase.getInstance().getReference("citizen").child("demo");
        mUserDataBaseMain = FirebaseDatabase.getInstance().getReference("Main");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        aBoolean = isChecked;
                    }
                });

                LatLng latLng = new LatLng(180.00,180.00);

                ModelLocation modelLocation = new ModelLocation("0.00","0.00",edName.getText().toString().trim(),"","0");
                ModelFamilyMember modelFamilyMemberMain = new ModelFamilyMember(edName.getText().toString().trim(),edPhone.getText().toString().trim(),"",aBoolean,"demo",new DateAndTimeClass().getCurrentDate());
                ModelFamilyMember modelFamilyMember = new ModelFamilyMember(edName.getText().toString().trim(),edPhone.getText().toString().trim(),"",aBoolean,"demo");

                //set Value In FLat Family
                mUserDataBaseFamily.child("family").child(edPhone.getText().toString().trim()).setValue(modelFamilyMember);
                mUserDataBaseFamily.child("location").child(edPhone.getText().toString().trim()).setValue(modelLocation);

                //set value in main
                mUserDataBaseMain.child(edPhone.getText().toString().trim()).setValue(modelFamilyMemberMain);

                finish();
            }
        });
    }

    private void initialize() {
        edName = findViewById(R.id.Ed_AddNewFamilyActivity_Name);
        edPhone = findViewById(R.id.Ed_AddNewFamilyActivity_Phone);

        btn = findViewById(R.id.Btn_AddNewFamilyActivity_Verify);

        toggleButton = findViewById(R.id.Tb_AddNewFamilyActiviy_Admin);
    }
}