package com.example.homesecuritymain.guard.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class AdapterCallCitizen extends RecyclerView.ViewHolder {
    public TextView tvCitizenName;

    public AdapterCallCitizen(@NonNull View itemView) {
        super(itemView);

        tvCitizenName = itemView.findViewById(R.id.Lr_Tv_CallActivity_CitizenNumber);
    }
}
