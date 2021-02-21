package com.example.homesecuritymain.citizen.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Model.ModelRecyclerViewCitizenMainActivity;

import java.util.ArrayList;

public class AdapterRecyclerViewCitizenMainActivity extends RecyclerView.Adapter<AdapterRecyclerViewCitizenMainActivity.ViewHolder> {

    private ArrayList<ModelRecyclerViewCitizenMainActivity> arrayList;
    private Context context;

    public AdapterRecyclerViewCitizenMainActivity(ArrayList<ModelRecyclerViewCitizenMainActivity> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tvName;
        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.Lr_Iv_CitizenMainActivity_logo);
            tvName = itemView.findViewById(R.id.Lr_Tv_CitizenMainActivity_Name);
            linearLayout = itemView.findViewById(R.id.Lr_Ll_CitizenMainActivty);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_citizen_main_activity,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set Name
        holder.tvName.setText(arrayList.get(position).getName());

        //set Background
        holder.linearLayout.setBackgroundColor(Color.parseColor(arrayList.get(position).getBackground()));

        //set Icon
        holder.imageView.setImageResource(arrayList.get(position).getIcon());

        //set OnclickListener
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(arrayList.get(position).getIntent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
