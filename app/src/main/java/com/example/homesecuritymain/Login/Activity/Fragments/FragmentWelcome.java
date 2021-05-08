package com.example.homesecuritymain.Login.Activity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import com.example.homesecuritymain.R;

public class FragmentWelcome extends Fragment {
    public TextView tvWelcome,tvGuidDesctiption;
    public LinearLayout linearLayout;
    public static FragmentWelcome getInstance() {
        FragmentWelcome fragment = new FragmentWelcome();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout_layout_resource_first_time_login_welcome, container, false);

        tvGuidDesctiption = view.findViewById(R.id.Tb_lr_Tv_GuideDescription);
        tvWelcome = view.findViewById(R.id.Tb_Lr_Tv_Welcome);

        linearLayout = view.findViewById(R.id.Tb_Lr_Ll);

        Animation animationWelcome = AnimationUtils.loadAnimation(getContext(), R.anim.animation_top_down);
        Animation animationLinerLayout = AnimationUtils.loadAnimation(getContext(), R.anim.animation_top_down);
        Animation animationDescription = AnimationUtils.loadAnimation(getContext(), R.anim.animation_top_down);

        animationWelcome.setStartOffset(1500);
        animationDescription.setStartOffset(3000);

        linearLayout.setAnimation(animationLinerLayout);
        tvWelcome.setAnimation(animationWelcome);
        tvGuidDesctiption.setAnimation(animationDescription);

        return view;
    }
}
