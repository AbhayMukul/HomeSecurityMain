package com.example.homesecuritymain.Login.Activity.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.homesecuritymain.Login.Activity.Common.SplashScreen;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.CitizenMainActivity;

public class FragmentGrievance extends Fragment {
    public ImageView imageView;
    public TextView tvName, tvDescription;

    public static FragmentGrievance getInstance() {
        FragmentGrievance fragment = new FragmentGrievance();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout_layout_resource_first_time_login, container, false);

        imageView = view.findViewById(R.id.Tb_Lr_Iv_Login);
        tvDescription = view.findViewById(R.id.Tb_Lr_Tv_Description);
        tvName = view.findViewById(R.id.Tb_Lr_Tv_Name);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_top_down);

        tvDescription.setAnimation(animation);
        tvName.setAnimation(animation);

        imageView.setImageResource(R.drawable.grievance_handling_main);
        tvDescription.setText("Any problem in your flat?" + "\n" + "post it here and our team would reach out to you for your assistance");
        tvName.setText("Grievance");

        return view;
    }
}
