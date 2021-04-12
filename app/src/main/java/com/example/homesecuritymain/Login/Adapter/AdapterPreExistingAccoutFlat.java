package com.example.homesecuritymain.Login.Adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class AdapterPreExistingAccoutFlat extends RecyclerView.ViewHolder {
    public TextView tvFlat;
    public LinearLayout linearLayout;

    public AdapterPreExistingAccoutFlat(@NonNull View itemView) {
        super(itemView);

        tvFlat = itemView.findViewById(R.id.Tv_Lr_PreExistingAccountFlats);
        linearLayout = itemView.findViewById(R.id.Ll_Lr_PreExistingAccountFlats);

    }
}
