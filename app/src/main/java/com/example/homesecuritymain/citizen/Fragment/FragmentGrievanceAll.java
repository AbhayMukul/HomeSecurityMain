package com.example.homesecuritymain.citizen.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Adapters.AdapterGrievanceAll;
import com.example.homesecuritymain.citizen.Model.ModelGrievanceAll;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentGrievanceAll extends Fragment {
    public RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    FirebaseRecyclerOptions<ModelGrievanceAll> option;
    FirebaseRecyclerAdapter<ModelGrievanceAll, AdapterGrievanceAll> firebaseRecyclerAdapter;

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

        //show views
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        option = new FirebaseRecyclerOptions.Builder<ModelGrievanceAll>().setQuery(FirebaseDatabase.getInstance().getReference().child("citizen").child("demo").child("Grievance").child("All"), ModelGrievanceAll.class).build();
        load();

        return view;
    }

    private void load() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelGrievanceAll, AdapterGrievanceAll>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterGrievanceAll adapter, int i, @NonNull ModelGrievanceAll model) {
                adapter.tvTime.setText(model.getTimeFixed());
                adapter.tvDate.setText(model.getDateFixed());
            }

            @NonNull
            @Override
            public AdapterGrievanceAll onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_grievance_all, parent, false);
                return new AdapterGrievanceAll(v);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
