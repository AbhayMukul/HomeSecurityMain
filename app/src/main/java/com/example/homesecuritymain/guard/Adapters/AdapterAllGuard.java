package com.example.homesecuritymain.guard.Adapters;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class AdapterAllGuard extends RecyclerView.ViewHolder {
    public TextView tvName, tvDateIn, tvDateOut;
    public LinearLayout linearLayout;

    public AdapterAllGuard(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.Tv_Lr_GuestAllGuard_GuestName);
        tvDateIn = itemView.findViewById(R.id.Tv_Lr_GuestAllGuard_Datein);
        tvDateOut = itemView.findViewById(R.id.Tv_Lr_GuestAllGuard_DateOut);

        linearLayout = itemView.findViewById(R.id.Ll_Lr_GuestAllGuard);
    }
}
