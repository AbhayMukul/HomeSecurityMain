package com.example.homesecuritymain.citizen.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class AdapterGrievanceAll extends RecyclerView.ViewHolder {
    public TextView tvTime, tvDate, tvMoreDetails;

    public AdapterGrievanceAll(@NonNull View itemView) {
        super(itemView);

        tvTime = itemView.findViewById(R.id.Tv_Lr_GrievanceAll_Time);
        tvDate = itemView.findViewById(R.id.Tv_Lr_GrievanceAll_Date);
        tvMoreDetails = itemView.findViewById(R.id.Tv_Lr_GrievanceAll_MoreDetails);
    }
}
