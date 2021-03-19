package com.example.homesecuritymain.citizen.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;
import com.google.android.gms.maps.SupportMapFragment;

public class AdapterFamily extends RecyclerView.ViewHolder{
    public TextView tvName,tvPhone,tvLocation;
    public ImageView imvSetting;

    public SupportMapFragment supportMapFragment;
    public AdapterFamily(@NonNull View itemView) {
        super(itemView);

        tvLocation = itemView.findViewById(R.id.Tv_Lr_FamilyActivity_Location);
        tvName = itemView.findViewById(R.id.Tv_Lr_FamilyActivity_Name);

        imvSetting = itemView.findViewById(R.id.Iv_Lr_FamilyActivity_Setting);
//        supportMapFragment = itemView.findViewById(R.id.mapFragment);

//        tvPhone = itemView.findViewById(R.id.Tv_Lr_FamilyActivity_Phone);
//
//        btnAdmin = itemView.findViewById(R.id.Btn_Lr_FamilyActivity_MakeAdmin);
    }
}
