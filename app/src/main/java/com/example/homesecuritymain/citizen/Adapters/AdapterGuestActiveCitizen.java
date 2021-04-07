package com.example.homesecuritymain.citizen.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class AdapterGuestActiveCitizen extends RecyclerView.ViewHolder {
    public TextView tvName,tvWork,tvCall;
    public ImageView imageView;
    public Button btnExit;

    public AdapterGuestActiveCitizen(@NonNull View itemView) {
        super(itemView);
        
        tvCall = itemView.findViewById(R.id.Tv_Lr_GuestActiveCitizen_CallGuest);
        tvName = itemView.findViewById(R.id.Tv_Lr_GuestActiveCitizen_GuestName);
        imageView = itemView.findViewById(R.id.Iv_Lr_GuestActiveCitizen_Stop);
        tvWork = itemView.findViewById(R.id.Tv_Lr_GuestActiveCitizen_GuestWork);

        btnExit = itemView.findViewById(R.id.Btn_Lr_GuestActiveCitizen_AllowExit);
    }
}
