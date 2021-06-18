package com.example.homesecuritymain.Login.Activity.Fragments;

import android.content.Context;
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
import androidx.transition.TransitionInflater;

import com.example.homesecuritymain.R;

public class FragmentFamily extends Fragment {
    public ImageView imageView;
    public TextView tvName, tvDescription;

    public static FragmentFamily getInstance() {
        FragmentFamily fragment = new FragmentFamily();
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

        imageView.setImageResource(R.drawable.family_main);
        tvDescription.setText("All your family members are found here with their locations seen by everyone in the family for better safety");
        tvName.setText("Family");

        return view;
    }
}
