package com.example.homesecuritymain.guard.FragmentAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelAllGuest;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Fragment.FragmentCurrentGrievance;
import com.example.homesecuritymain.guard.Adapters.AdapterActiveGuard;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentActiveGuest extends Fragment {
    public RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    //Firebase Database
    DatabaseReference mUserDatabaseGuest;

    FirebaseRecyclerOptions<ModelActiveGuest> option;
    FirebaseRecyclerAdapter<ModelActiveGuest, AdapterActiveGuard> firebaseRecyclerAdapter;

    public static FragmentActiveGuest getInstance(){
        FragmentActiveGuest fragmentActiveGuest = new FragmentActiveGuest();
        return fragmentActiveGuest;
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

        mUserDatabaseGuest = FirebaseDatabase.getInstance().getReference("guest").child("Active");

        option = new FirebaseRecyclerOptions.Builder<ModelActiveGuest>().setQuery(FirebaseDatabase.getInstance().getReference().child("guest").child("Active"), ModelActiveGuest.class).build();
        load();

        return view;
    }

    private void load() {
        firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<ModelActiveGuest, AdapterActiveGuard>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterActiveGuard adapter, int i, @NonNull ModelActiveGuest model) {
                adapter.tvName.setText(model.getName());
                adapter.tvTimeIn.setText(model.getTimeIn());

                adapter.btnExit.setEnabled(!model.getSTOP());

                adapter.btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!model.getSTOP()){
                            new CommonClass().referenceGuestGuardActive().child(model.getKeyUID()).removeValue();
                        }
                    }
                });
            }

            @NonNull
            @Override
            public AdapterActiveGuard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_guest_active_guard, parent, false);
                return new AdapterActiveGuard(v);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
