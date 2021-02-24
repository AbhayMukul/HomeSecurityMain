package com.example.homesecuritymain.guard.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Fragment.FragmentCurrentGrievance;
import com.example.homesecuritymain.citizen.Fragment.FragmentGrievanceAll;
import com.example.homesecuritymain.citizen.FragmentAdapter.ViewPageAdapterGuestActivity;
import com.example.homesecuritymain.guard.FragmentAdapter.FragmentActiveGuest;
import com.example.homesecuritymain.guard.FragmentAdapter.FragmentAllGuest;
import com.google.android.material.tabs.TabLayout;

public class GuardMainActivity extends AppCompatActivity {
    //XML elements
    ImageView ivOpenLinearLayout, ivSetting, ivNewGuest;
    LinearLayout linearLayout;

    //Tab Layout
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_main);

        initialize();

        setTabLayout();

        linearLayout.setVisibility(View.GONE);

        ivOpenLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayout.getVisibility() == View.GONE) {
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });

        ivNewGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuardMainActivity.this,AddNewGuestActivity.class));
            }
        });
    }

    private void initialize() {
        tabLayout = findViewById(R.id.Tb_GuardMainActivity);
        viewPager = findViewById(R.id.Vp_GuardMainActivity);

        //ImageView
        ivOpenLinearLayout = findViewById(R.id.Iv_GuardMainActivity_OpenLinerLayout);
        ivSetting = findViewById(R.id.Iv_GuardMainActivity_Setting);
        ivNewGuest = findViewById(R.id.Iv_GuardMainActivity_NewGuest);

        //LinearLayout
        linearLayout = findViewById(R.id.Ll_GuardMainActivity_SettingsPannel);
    }

    private void setTabLayout() {
        final ViewPageAdapterGuestActivity viewPageAdapter = new ViewPageAdapterGuestActivity(getSupportFragmentManager());

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPageAdapter.addFragment(FragmentActiveGuest.getInstance(), "Current Guests");
                viewPageAdapter.addFragment(FragmentAllGuest.getInstance(), "All Guests");

                viewPager.setAdapter(viewPageAdapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}