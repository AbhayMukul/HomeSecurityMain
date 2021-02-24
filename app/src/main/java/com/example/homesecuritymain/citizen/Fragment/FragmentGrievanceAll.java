package com.example.homesecuritymain.citizen.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;

public class FragmentGrievanceAll extends Fragment {
    public RecyclerView recyclerView;

    public static FragmentGrievanceAll getInstance(){
        FragmentGrievanceAll fragmentGrievanceAll = new FragmentGrievanceAll();
        return fragmentGrievanceAll;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout_layout_resource_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.Tb_Lr_Rv);
        return view;
    }
}
