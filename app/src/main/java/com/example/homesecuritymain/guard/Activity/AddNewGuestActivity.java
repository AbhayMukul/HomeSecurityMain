package com.example.homesecuritymain.guard.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class AddNewGuestActivity extends AppCompatActivity implements Serializable {
    String name, flat, work, phone;

    ModelActiveGuest modelActiveGuest;
    //firebase Database
    DatabaseReference mUserDatabaseCitizen;

    private EditText edName, edFlat, edWork, edPhone;
    private Button btnDone;
    private TextView tvGuestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_guest);

        initialize();

        mUserDatabaseCitizen = FirebaseDatabase.getInstance().getReference("citizen");

        //onClickListeners
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndPutData();

                Intent intent = new Intent(getBaseContext(), CallActivity.class);

                //create model
                intent.putExtra("data",modelActiveGuest);

                startActivity(new Intent(intent));
            }
        });

        tvGuestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNewGuestActivity.this, GuestListVerificationActivity.class));
            }
        });
    }

    private void getAndPutData() {
        name = edName.getText().toString().trim();
        phone = edPhone.getText().toString().trim();
        work = edWork.getText().toString().trim();
        flat = edFlat.getText().toString().trim();

        modelActiveGuest = new ModelActiveGuest(name,flat,phone,work,mUserDatabaseCitizen.push().getKey(),new DateAndTimeClass().getCurrentTime(),false);

    }

    private void initialize() {
        edFlat = findViewById(R.id.Ed_AddNewGuestActivity_FlatNumber);
        edName = findViewById(R.id.Ed_AddNewGuestActivity_Name);
        edPhone = findViewById(R.id.Ed_AddNewGuestActivity_PhoneNumber);
        edWork = findViewById(R.id.Ed_AddNewGuestActivity_Work);

        btnDone = findViewById(R.id.Btn_AddNewGuestActivity_Verify);

        tvGuestList = findViewById(R.id.Tv_AddNewGuestActivity_GuestList);

//        data = new ArrayList<>();
    }
}