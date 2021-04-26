package com.example.homesecuritymain.citizen.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelAllGuest;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Adapters.AdapterGuestActiveCitizen;
import com.example.homesecuritymain.citizen.Adapters.AdapterGuestAllCitizen;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentGuestAll extends Fragment {
    public RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    String flat;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    //Firebase Database
    DatabaseReference mUserDatabaseGuest;

    FirebaseRecyclerOptions<ModelAllGuest> option;
    FirebaseRecyclerAdapter<ModelAllGuest, AdapterGuestAllCitizen> firebaseRecyclerAdapter;

    public static FragmentGuestAll getInstance() {
        FragmentGuestAll fragmentGuestAll = new FragmentGuestAll();
        return fragmentGuestAll;
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

        sharedPreferences = getContext().getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT,"");

        //show views
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        option = new FirebaseRecyclerOptions.Builder<ModelAllGuest>().setQuery(FirebaseDatabase.getInstance().getReference().child("citizen").child(flat).child("GUEST").child("All"), ModelAllGuest.class).build();
        load();

        return view;
    }

    private void load() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelAllGuest, AdapterGuestAllCitizen>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterGuestAllCitizen adapter, int i, @NonNull ModelAllGuest model) {
                adapter.tvName.setText(model.getName());
                adapter.tvDateIn.setText("In :-" + model.getDateIn());
                adapter.tvDateOut.setText("Out:- "+ model.getDateOutCitizen());
            }

            @NonNull
            @Override
            public AdapterGuestAllCitizen onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_guest_all_citizen, parent, false);
                return new AdapterGuestAllCitizen(v);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}
