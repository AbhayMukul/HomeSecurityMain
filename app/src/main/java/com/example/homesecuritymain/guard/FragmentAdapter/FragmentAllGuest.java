package com.example.homesecuritymain.guard.FragmentAdapter;

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

import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelAllGuest;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Fragment.FragmentCurrentGrievance;
import com.example.homesecuritymain.guard.Adapters.AdapterActiveGuard;
import com.example.homesecuritymain.guard.Adapters.AdapterAllGuard;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentAllGuest extends Fragment {
    public RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    //Firebase Database
    FirebaseRecyclerOptions<ModelAllGuest> option;
    FirebaseRecyclerAdapter<ModelAllGuest, AdapterAllGuard> firebaseRecyclerAdapter;

    public static FragmentAllGuest getInstance(){
        FragmentAllGuest fragmentAllGuest = new FragmentAllGuest();
        return fragmentAllGuest;
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

        option = new FirebaseRecyclerOptions.Builder<ModelAllGuest>().setQuery(FirebaseDatabase.getInstance().getReference().child("guest").child("All"), ModelAllGuest.class).build();
        load();

        return view;
    }

    private void load() {
        firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<ModelAllGuest, AdapterAllGuard>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterAllGuard adapter, int i, @NonNull ModelAllGuest model) {
                adapter.tvName.setText(model.getName());
                adapter.tvDateIn.setText("Date In :- " + "\n" + model.getDateIn());
                adapter.tvDateOut.setText("Date Out :- " + "\n" +model.getDateOutGuard());
            }

            @NonNull
            @Override
            public AdapterAllGuard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_guest_all_guard, parent, false);
                return new AdapterAllGuard(v);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
