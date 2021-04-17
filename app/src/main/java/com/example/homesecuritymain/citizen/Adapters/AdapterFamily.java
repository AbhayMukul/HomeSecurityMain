package com.example.homesecuritymain.citizen.Adapters;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;
import com.google.android.gms.maps.SupportMapFragment;

public class AdapterFamily extends RecyclerView.ViewHolder {
    public TextView tvName;
    public ImageView imvSetting;

    public Button btnLocation;

    public LinearLayout linearLayout;
    public Spinner spinner;

    public AdapterFamily(@NonNull View itemView) {
        super(itemView);

        btnLocation = itemView.findViewById(R.id.Btn_Lr_FamilyActivity_Location);
        tvName = itemView.findViewById(R.id.Tv_Lr_FamilyActivity_Name);

        imvSetting = itemView.findViewById(R.id.Iv_Lr_FamilyActivity_Setting);

        linearLayout = itemView.findViewById(R.id.Ll_Lr_FamilyActivity);

        spinner = itemView.findViewById(R.id.Sp_Lr_FamilyActivity_Duration);

    }
}
