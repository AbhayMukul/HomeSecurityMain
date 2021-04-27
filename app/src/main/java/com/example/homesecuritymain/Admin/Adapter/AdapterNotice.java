package com.example.homesecuritymain.Admin.Adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class AdapterNotice extends RecyclerView.ViewHolder {
    public TextView tvSubject,tvTime,tvDate;
    public LinearLayout linearLayout;

    public AdapterNotice(@NonNull View itemView) {
        super(itemView);

        tvDate = itemView.findViewById(R.id.Tv_Lr_NoticeDate);
        tvTime = itemView.findViewById(R.id.Tv_Lr_NoticeTime);
        tvSubject = itemView.findViewById(R.id.Tv_Lr_Notice);

        linearLayout = itemView.findViewById(R.id.Ll_Lr_Notice);
    }
}
