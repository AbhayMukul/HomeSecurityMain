package com.example.homesecuritymain.citizen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Adapters.AdapterFamily;
import com.example.homesecuritymain.citizen.Model.ModelFamilyMember;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FamilyActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseRecyclerOptions<ModelFamilyMember> option;
    FirebaseRecyclerAdapter<ModelFamilyMember, AdapterFamily> firebaseRecyclerAdapter;
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    String flat, admin;
    Boolean ADMIN;
    private TextView tvNewFamily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT, "");
        admin = sharedPreferences.getString(sharedPrefrencesClass.SP_ADMIN, "");

        if (admin.equals("false")) {
            ADMIN = false;
            tvNewFamily.setVisibility(View.GONE);
        } else {
            ADMIN = true;
            tvNewFamily.setVisibility(View.VISIBLE);
        }

        //show views
        linearLayoutManager = new LinearLayoutManager(FamilyActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        option = new FirebaseRecyclerOptions.Builder<ModelFamilyMember>().setQuery(FirebaseDatabase.getInstance().getReference().child("citizen").child(flat).child("family"), ModelFamilyMember.class).build();
        load();

        tvNewFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FamilyActivity.this, AddNewFamilyActivity.class));
            }
        });
    }

    private void load() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelFamilyMember, AdapterFamily>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterFamily adapter, int i, @NonNull ModelFamilyMember model) {
                adapter.tvName.setText(model.getName());

                adapter.tvLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //set intent
                        Intent intent = new Intent(FamilyActivity.this,MapFamilyActivity.class);

                        //set Extra
                        intent.putExtra("phone",model.getPhone());

                        //start Activity
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public AdapterFamily onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_family_memeber_citizen, parent, false);
                return new AdapterFamily(v);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void initialize() {
        tvNewFamily = findViewById(R.id.Tv_FamilyActivity_AddNewFamily);
        recyclerView = findViewById(R.id.Rv_FamilyActivity);
    }
}