package com.example.homesecuritymain.Admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.homesecuritymain.Admin.Adapter.AdapterAdminGrievanceMain;
import com.example.homesecuritymain.Admin.Adapter.AdapterGuardAllAdmin;
import com.example.homesecuritymain.Admin.Model.GuardDetailsModel;
import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Model.ModelGrievance;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AllGuardAdminActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView imageView;

    private LinearLayoutManager linearLayoutManager;
    private CommonClass object;

    FirebaseRecyclerOptions<GuardDetailsModel> option;
    FirebaseRecyclerAdapter<GuardDetailsModel, AdapterGuardAllAdmin> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_guard_admin);

        initialize();

        object = new CommonClass();

        //show views
        linearLayoutManager = new LinearLayoutManager(AllGuardAdminActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        option = new FirebaseRecyclerOptions.Builder<GuardDetailsModel>().setQuery(object.mUserDatabaseGuardLogin, GuardDetailsModel.class).build();
        load();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AllGuardAdminActivity.this,NewGuardCreationActivity.class));
            }
        });

    }

    private void load() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<GuardDetailsModel, AdapterGuardAllAdmin>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterGuardAllAdmin adapter, int i, @NonNull GuardDetailsModel model) {
                adapter.tvPhone.setText(model.getPhone());
                adapter.tvName.setText(model.getName());
                adapter.tvID.setText(model.getID());

                adapter.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AllGuardAdminActivity.this,GuardDescriptionActivity.class);
                        intent.putExtra("guardDetails",model);
                        startActivity(intent);
                    }
                });

                if(model.getACTIVE()){
                    adapter.tvActive.setBackgroundColor(getResources().getColor(R.color.Active));
                }else {
                    adapter.tvActive.setBackgroundColor(getResources().getColor(R.color.Inactive));
                }

                adapter.tvActive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        object.mUserDatabaseGuardLogin.child(model.getID()).child("active").setValue(!model.getACTIVE());
                    }
                });
            }

            @NonNull
            @Override
            public AdapterGuardAllAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_guard_all_admin, parent, false);
                return new AdapterGuardAllAdmin(v);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void initialize() {
        recyclerView = findViewById(R.id.Rv_AllGuardAdminActivity);
        imageView = findViewById(R.id.Iv_AllGuardAdminActivity_NewGuard);
    }
}