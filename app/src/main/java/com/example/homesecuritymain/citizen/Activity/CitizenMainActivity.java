package com.example.homesecuritymain.citizen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Adapters.AdapterRecyclerViewCitizenMainActivity;
import com.example.homesecuritymain.citizen.Model.ModelRecyclerViewCitizenMainActivity;

import java.util.ArrayList;

public class CitizenMainActivity extends AppCompatActivity {

    //recyclerView
    private RecyclerView recyclerView;
    private ArrayList<ModelRecyclerViewCitizenMainActivity> arrayList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //XML elements
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_main);

        initialize();

        //set RecyclerView
        setData();
        setAdapter();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CitizenMainActivity.this,SettingActivityCitizen.class));
            }
        });
    }

    private void setData() {
        arrayList = new ArrayList<>();

        arrayList.add(new ModelRecyclerViewCitizenMainActivity(R.drawable.ic_group, "GUESTS", "#C8F69B",new Intent(CitizenMainActivity.this,GuestsActivity.class)));
        arrayList.add(new ModelRecyclerViewCitizenMainActivity(R.drawable.ic_guest_list, "GUESTLIST", "#FFEEA5",new Intent(CitizenMainActivity.this,GuestListActivity.class)));
        arrayList.add(new ModelRecyclerViewCitizenMainActivity(R.drawable.ic_file, "NOTICE BOARD", "#FFCBA5",new Intent(CitizenMainActivity.this,NoticeActivity.class)));
        arrayList.add(new ModelRecyclerViewCitizenMainActivity(R.drawable.ic_family, "FAMILY", "#FFB1AF",new Intent(CitizenMainActivity.this,FamilyActivity.class)));
        arrayList.add(new ModelRecyclerViewCitizenMainActivity(R.drawable.ic_review, "GRIEVANCE REDRESSAL", "#D6D4FF",new Intent(CitizenMainActivity.this,GrievanceAddressalActivity.class)));
    }

    private void setAdapter() {
        AdapterRecyclerViewCitizenMainActivity adapter = new AdapterRecyclerViewCitizenMainActivity(arrayList,CitizenMainActivity.this);
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initialize() {
        recyclerView = findViewById(R.id.Rv_CitizenMainActivity);
        imageView = findViewById(R.id.Iv_CitizenMainActivity_Setting);
    }
}