package com.example.homesecuritymain.Admin.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class AdapterAdminGrievanceMain extends RecyclerView.ViewHolder {
    public LinearLayout linearLayout;

    public TextView tvCategory,tvTime,tvDate,tvDescription,tvMoreDetails,tvCall;
    public Button btnDone;

    public EditText edName,edPhone;

    public AdapterAdminGrievanceMain(@NonNull View itemView) {
        super(itemView);

        tvCategory = itemView.findViewById(R.id.Tv_Lr_GrievanceHelper_Category);
        tvDate = itemView.findViewById(R.id.Tv_Lr_GrievanceHelper_Date);
        tvDescription = itemView.findViewById(R.id.Tv_Lr_GrievanceHelper_Description);
        tvMoreDetails = itemView.findViewById(R.id.Tv_Lr_GrievanceHelper_MoreDetails);
        tvTime = itemView.findViewById(R.id.Tv_Lr_GrievanceHelper_Time);
        tvCall = itemView.findViewById(R.id.Tv_Lr_GrievanceHelper_Call);

        btnDone = itemView.findViewById(R.id.Btn_Lr_GrievanceHelper_Update);

        linearLayout = itemView.findViewById(R.id.Ll_Lr_GrievanceHelper);

        edName = itemView.findViewById(R.id.Ed_Lr_GrievanceHelper_Name);
        edPhone = itemView.findViewById(R.id.Ed_Lr_GrievanceHelper_Phone);
    }
}
