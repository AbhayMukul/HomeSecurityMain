package com.example.homesecuritymain.citizen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Adapters.AdapterRecyclerViewCitizenMainActivity;
import com.example.homesecuritymain.citizen.Model.ModelRecyclerViewCitizenMainActivity;

import java.util.ArrayList;

public class CitizenMainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    //recyclerView
    private RecyclerView recyclerView;
    private ArrayList<ModelRecyclerViewCitizenMainActivity> arrayList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView tvName;

    //XML elements
    private ImageView imageView;

    //strings
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_main);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(sharedPrefrencesClass.SP_NAME, "");
        tvName.setText(name);

        Toast.makeText(this, "flat :- " + sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT, ""), Toast.LENGTH_SHORT).show();

        Log.e("name", name);

        //set RecyclerView
        setData();
        setAdapter();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CitizenMainActivity.this, SettingActivityCitizen.class));
            }
        });
    }

    private void setData() {
        arrayList = new ArrayList<>();

        arrayList.add(new ModelRecyclerViewCitizenMainActivity(R.drawable.ic_group, "GUESTS", "#C8F69B", new Intent(CitizenMainActivity.this, GuestsActivity.class)));
        arrayList.add(new ModelRecyclerViewCitizenMainActivity(R.drawable.ic_guest_list, "GUESTLIST", "#FFEEA5", new Intent(CitizenMainActivity.this, GuestListActivity.class)));
        arrayList.add(new ModelRecyclerViewCitizenMainActivity(R.drawable.ic_file, "NOTICE BOARD", "#FFCBA5", new Intent(CitizenMainActivity.this, NoticeActivity.class)));
        arrayList.add(new ModelRecyclerViewCitizenMainActivity(R.drawable.ic_family, "FAMILY", "#FFB1AF", new Intent(CitizenMainActivity.this, FamilyActivity.class)));
        arrayList.add(new ModelRecyclerViewCitizenMainActivity(R.drawable.ic_review, "GRIEVANCE REDRESSAL", "#D6D4FF", new Intent(CitizenMainActivity.this, GrievanceAddressalActivity.class)));
    }

    private void setAdapter() {
        AdapterRecyclerViewCitizenMainActivity adapter = new AdapterRecyclerViewCitizenMainActivity(arrayList, CitizenMainActivity.this);
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initialize() {
        recyclerView = findViewById(R.id.Rv_CitizenMainActivity);
        imageView = findViewById(R.id.Iv_CitizenMainActivity_Setting);
        tvName = findViewById(R.id.Tv_CitizenMainActivity_UserName);
    }
}