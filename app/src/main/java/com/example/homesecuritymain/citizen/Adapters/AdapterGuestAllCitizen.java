package com.example.homesecuritymain.citizen.Adapters;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class AdapterGuestAllCitizen extends RecyclerView.ViewHolder{
    public TextView tvName, tvDateIn, tvDateOut;
    public LinearLayout linearLayout;

    public AdapterGuestAllCitizen(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.Tv_Lr_GuestAllCitizen_GuestName);
        tvDateIn = itemView.findViewById(R.id.Tv_Lr_GuestAllCitizen_Datein);
        tvDateOut = itemView.findViewById(R.id.Tv_Lr_GuestAllCitizen_DateOut);

        linearLayout = itemView.findViewById(R.id.Ll_Lr_GuestAllCitizen);
    }
}
