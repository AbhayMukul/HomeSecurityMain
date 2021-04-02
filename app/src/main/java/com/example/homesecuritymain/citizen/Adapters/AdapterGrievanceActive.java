package com.example.homesecuritymain.citizen.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class AdapterGrievanceActive extends RecyclerView.ViewHolder {
    public LinearLayout linearLayout;

    public TextView tvCategory,tvTime,tvDate,tvDescription,tvName,tvMoreDetails;
    public Button btnCall;

    public AdapterGrievanceActive(@NonNull View itemView) {
        super(itemView);

        tvCategory = itemView.findViewById(R.id.Tv_Lr_GrievanceActive_Category);
        tvDate = itemView.findViewById(R.id.Tv_Lr_GrievanceActive_Date);
        tvDescription = itemView.findViewById(R.id.Tv_Lr_GrievanceActive_Description);
        tvName = itemView.findViewById(R.id.Tv_Lr_GrievanceActive_AssignedHelpName);
        tvTime = itemView.findViewById(R.id.Tv_Lr_GrievanceActive_Time);
        tvMoreDetails = itemView.findViewById(R.id.Tv_Lr_GrievanceAll_MoreDetails);

        btnCall = itemView.findViewById(R.id.Btn_Lr_GrievanceActive_AssignedHelpCall);

        linearLayout = itemView.findViewById(R.id.Ll_Lr_GrievanceActive);
    }
}
