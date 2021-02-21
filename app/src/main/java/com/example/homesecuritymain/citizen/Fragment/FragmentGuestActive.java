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

public class FragmentGuestActive extends Fragment {
    public RecyclerView recyclerView;

    public static FragmentGuestActive getInstance(){
        FragmentGuestActive fragmentGuestActive = new FragmentGuestActive();
        return fragmentGuestActive;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout_layout_resource_guest_active, container, false);
        recyclerView = view.findViewById(R.id.Tb_Lr_Rv_GuestActive);
        return view;
    }
}
