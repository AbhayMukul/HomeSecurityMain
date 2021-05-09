package com.example.homesecuritymain.guard.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class AdapterActiveGuard extends RecyclerView.ViewHolder {

    //GUARD ACTIVE (guest_guard)
    public TextView tvName, tvTimeIn,tvCallGuest;
    public LinearLayout linearLayout;
    public Button btnExit, btnCall;

    public AdapterActiveGuard(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.Tv_Lr_GuestActiveGuard_Name);
        tvTimeIn = itemView.findViewById(R.id.Tv_Lr_GuestActiveGuard_TimeIn);
        tvCallGuest = itemView.findViewById(R.id.Tv_Lr_GuestActiveGuard_CallGuest);

        linearLayout = itemView.findViewById(R.id.Ll_Lr_GuestActiveGuard);

        btnCall = itemView.findViewById(R.id.Tv_Lr_GuestActiveGuard_CallCitizen);
        btnExit = itemView.findViewById(R.id.Btn_Lr_GuestActiveGuard_AllowExit);

    }
}
