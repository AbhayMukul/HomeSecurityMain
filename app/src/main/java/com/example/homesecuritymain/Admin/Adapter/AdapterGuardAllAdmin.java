package com.example.homesecuritymain.Admin.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

import org.w3c.dom.Text;

public class AdapterGuardAllAdmin extends RecyclerView.ViewHolder {
    public TextView tvID,tvName,tvPhone,tvActive;

    public AdapterGuardAllAdmin(@NonNull View itemView) {
        super(itemView);

        tvActive = itemView.findViewById(R.id.Tv_Lr_AllGuardAdminActivity_Active);
        tvID = itemView.findViewById(R.id.Tv_Lr_AllGuardAdminActivity_ID);
        tvName = itemView.findViewById(R.id.Tv_Lr_AllGuardAdminActivity_Name);
        tvPhone = itemView.findViewById(R.id.Tv_Lr_AllGuardAdminActivity_Phone);
    }
}
