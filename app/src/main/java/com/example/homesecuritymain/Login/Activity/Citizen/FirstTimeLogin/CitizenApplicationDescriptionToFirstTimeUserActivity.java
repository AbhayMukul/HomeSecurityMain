package com.example.homesecuritymain.Login.Activity.Citizen.FirstTimeLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.homesecuritymain.Login.Activity.Fragments.FragmentFamily;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentGrievance;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentGuestList;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentGuests;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentIntroduction;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentLetStart;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentNoticeBoard;
import com.example.homesecuritymain.Login.Activity.Fragments.FragmentWelcome;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.FragmentAdapter.ViewPageAdapterGuestActivity;
import com.google.android.material.tabs.TabLayout;

public class CitizenApplicationDescriptionToFirstTimeUserActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_application_description_to_first_time_user);

        initialize();

        setViewPager();

        tabLayout.setVisibility(View.GONE);
    }

    private void setViewPager() {
        final ViewPageAdapterGuestActivity viewPageAdapter = new ViewPageAdapterGuestActivity(getSupportFragmentManager());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPageAdapter.addFragment(FragmentWelcome.getInstance(),"");
                viewPageAdapter.addFragment(FragmentIntroduction.getInstance(),"");
                viewPageAdapter.addFragment(FragmentGuests.getInstance(),"");
                viewPageAdapter.addFragment(FragmentGuestList.getInstance(),"");
                viewPageAdapter.addFragment(FragmentNoticeBoard.getInstance(),"");
                viewPageAdapter.addFragment(FragmentFamily.getInstance(),"");
                viewPageAdapter.addFragment(FragmentGrievance.getInstance(),"");
                viewPageAdapter.addFragment(FragmentLetStart.getInstance(),"");

                viewPager.setAdapter(viewPageAdapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    private void initialize() {
        viewPager = findViewById(R.id.Vp_CitizenApplicationDescriptionToFirstTimeUserActivity);
        tabLayout = findViewById(R.id.Tb_CitizenApplicationDescriptionToFirstTimeUserActivity);
    }
}