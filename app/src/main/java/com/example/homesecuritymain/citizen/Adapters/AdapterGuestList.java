package com.example.homesecuritymain.citizen.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class AdapterGuestList extends RecyclerView.ViewHolder{
    public TextView tvCode,tvName;

    public Button btnCall;

    public AdapterGuestList(@NonNull View itemView) {
        super(itemView);

        tvCode = itemView.findViewById(R.id.Tv_Lr_GuestListActivity_Code);
        tvName = itemView.findViewById(R.id.Tv_Lr_GuestListActivity_Name);

        btnCall = itemView.findViewById(R.id.Btn_Lr_GuestListActivity_Call);

    }
}
