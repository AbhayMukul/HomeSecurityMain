package com.example.homesecuritymain.citizen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Fragment.FragmentCurrentGrievance;
import com.example.homesecuritymain.citizen.Fragment.FragmentGrievanceAll;
import com.example.homesecuritymain.citizen.Fragment.FragmentGuestActive;
import com.example.homesecuritymain.citizen.Fragment.FragmentGuestAll;
import com.example.homesecuritymain.citizen.FragmentAdapter.ViewPageAdapterGuestActivity;
import com.google.android.material.tabs.TabLayout;

public class GrievanceAddressalActivity extends AppCompatActivity {

    //Tab Layout
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //ImageView
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance_addressal);

        initialize();

        setTabLayout();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GrievanceAddressalActivity.this,NewGrievanceActivity.class));
            }
        });
    }

    private void setTabLayout() {
        final ViewPageAdapterGuestActivity viewPageAdapter = new ViewPageAdapterGuestActivity(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPageAdapter.addFragment(FragmentCurrentGrievance.getInstance(), "Active Grievance");
                viewPageAdapter.addFragment(FragmentGrievanceAll.getInstance(), "Previous Grievance");

                viewPager.setAdapter(viewPageAdapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    private void initialize() {
        tabLayout = findViewById(R.id.Tl_GrievanceAddressalActivity);
        viewPager = findViewById(R.id.Vp_GrievanceAddressalActivity);

        imageView = findViewById(R.id.Iv_GrievanceAddressalActivity_NewGrievance);
    }
}