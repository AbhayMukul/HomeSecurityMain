package com.example.homesecuritymain.Login.Activity.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import com.example.homesecuritymain.Login.Activity.Citizen.FirstTimeLogin.CitizenApplicationDescriptionToFirstTimeUserActivity;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.CitizenMainActivity;

public class FragmentLetStart extends Fragment{
    Button btn;
    ProgressBar progressBar;

    public static FragmentLetStart getInstance() {
        FragmentLetStart fragment = new FragmentLetStart();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout_layout_resource_first_time_login_start, container, false);
        btn = view.findViewById(R.id.Tb_Lr_Btn);
        progressBar = view.findViewById(R.id.Tb_Lr_Pb);

        progressBar.setVisibility(View.GONE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setEnabled(false);

                progressBar.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getContext(), CitizenMainActivity.class));
                    }
                },1500);
            }
        });
        return view;
    }
}
