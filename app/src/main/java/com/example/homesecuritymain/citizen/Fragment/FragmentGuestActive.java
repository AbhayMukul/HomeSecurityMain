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

import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Adapters.AdapterGuestActiveCitizen;
import com.example.homesecuritymain.guard.Adapters.AdapterActiveGuard;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentGuestActive extends Fragment {
    public RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    //Firebase Database
    DatabaseReference mUserDatabaseGuest;

    FirebaseRecyclerOptions<ModelActiveGuest> option;
    FirebaseRecyclerAdapter<ModelActiveGuest, AdapterGuestActiveCitizen> firebaseRecyclerAdapter;

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
        View view = inflater.inflate(R.layout.tab_layout_layout_resource_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.Tb_Lr_Rv);

        //show views
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        option = new FirebaseRecyclerOptions.Builder<ModelActiveGuest>().setQuery(FirebaseDatabase.getInstance().getReference().child("citizen").child("demo").child("GUEST").child("Active"), ModelActiveGuest.class).build();
        load();

        return view;
    }

    private void load() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelActiveGuest, AdapterGuestActiveCitizen>(option) {
            @NonNull
            @Override
            protected void onBindViewHolder(@NonNull AdapterGuestActiveCitizen adapter, int i, @NonNull ModelActiveGuest model) {

            }

            @Override
            public AdapterGuestActiveCitizen onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_guest_active_citizen, parent, false);
                return new AdapterGuestActiveCitizen(v);
            }

        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
