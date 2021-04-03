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

import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelAllGuest;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Adapters.AdapterGrievanceActive;
import com.example.homesecuritymain.citizen.Adapters.AdapterGrievanceAll;
import com.example.homesecuritymain.citizen.Adapters.AdapterGuestAllCitizen;
import com.example.homesecuritymain.citizen.Model.ModelGrievance;
import com.example.homesecuritymain.citizen.Model.ModelGrievanceAll;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentCurrentGrievance extends Fragment {
    public RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    FirebaseRecyclerOptions<ModelGrievance> option;
    FirebaseRecyclerAdapter<ModelGrievance, AdapterGrievanceActive> firebaseRecyclerAdapter;

    DatabaseReference mUserDatabaseGrievance,mUserDatabaseCitizen;

    public static FragmentCurrentGrievance getInstance(){
        FragmentCurrentGrievance fragmentCurrentGrievance = new FragmentCurrentGrievance();
        return fragmentCurrentGrievance;
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

        mUserDatabaseGrievance = FirebaseDatabase.getInstance().getReference("Grievance");
        mUserDatabaseCitizen = FirebaseDatabase.getInstance().getReference("citizen");

        //show views
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        option = new FirebaseRecyclerOptions.Builder<ModelGrievance>().setQuery(FirebaseDatabase.getInstance().getReference().child("citizen").child("demo").child("Grievance").child("Active"), ModelGrievance.class).build();
        load();

        return view;
    }

    private void load() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelGrievance, AdapterGrievanceActive>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterGrievanceActive adapter, int i, @NonNull ModelGrievance model) {
                adapter.linearLayout.setVisibility(View.GONE);
                adapter.tvCategory.setText(model.getCategory());
                adapter.tvDate.setText(model.getPreferredDate());
                adapter.tvTime.setText(model.getPreferredTime());
                adapter.tvDescription.setText(model.getGrievance());

                if(model.getAssignedHelpName() == "") {
                    adapter.tvName.setText("yet to be assigned");
                    adapter.btnCall.setVisibility(View.GONE);
                    adapter.btnDone.setVisibility(View.GONE);
                }else {
                    adapter.tvName.setText(model.getAssignedHelpName());
                    adapter.btnDone.setVisibility(View.VISIBLE);
                    adapter.btnCall.setText(model.getAssignedHelpNumber());
                }

                adapter.tvMoreDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(adapter.linearLayout.getVisibility() == View.GONE){
                            adapter.tvMoreDetails.setText("Hide Details");
                            adapter.linearLayout.setVisibility(View.VISIBLE);
                        }else {
                            adapter.tvMoreDetails.setText("More Details");
                            adapter.linearLayout.setVisibility(View.GONE);
                        }
                    }
                });

                adapter.tvRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mUserDatabaseCitizen.child("demo").child("Grievance").child("Active").child(model.getFirebaseUID()).removeValue();
                        mUserDatabaseGrievance.child("Active").child(model.getFirebaseUID()).removeValue();
                    }
                });

                adapter.btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //remove from active
                        mUserDatabaseCitizen.child("demo").child("Grievance").child("Active").child(model.getFirebaseUID()).removeValue();
                        mUserDatabaseGrievance.child("Active").child(model.getFirebaseUID()).removeValue();

                        //set false in all
                        mUserDatabaseCitizen.child("demo").child("Grievance").child("All").child(model.getFirebaseUID()).child("done").setValue(true);
                        mUserDatabaseGrievance.child("All").child(model.getFirebaseUID()).child("done").setValue(true);

                        //set Date fixed
                        mUserDatabaseCitizen.child("demo").child("Grievance").child("All").child(model.getFirebaseUID()).child("dateFixed").setValue(new DateAndTimeClass().getCurrentDate());
                        mUserDatabaseGrievance.child("All").child(model.getFirebaseUID()).child("dateFixed").setValue(new DateAndTimeClass().getCurrentDate());

                        //set Date fixed
                        mUserDatabaseCitizen.child("demo").child("Grievance").child("All").child(model.getFirebaseUID()).child("timeFixed").setValue(new DateAndTimeClass().getCurrentTime());
                        mUserDatabaseGrievance.child("All").child(model.getFirebaseUID()).child("timeFixed").setValue(new DateAndTimeClass().getCurrentTime());
                    }
                });
            }

            @NonNull
            @Override
            public AdapterGrievanceActive onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_grievance_active, parent, false);
                return new AdapterGrievanceActive(v);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}
