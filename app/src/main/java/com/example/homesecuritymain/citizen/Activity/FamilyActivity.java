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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FamilyActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseRecyclerOptions<ModelFamilyMember> option;
    FirebaseRecyclerAdapter<ModelFamilyMember, AdapterFamily> firebaseRecyclerAdapter;
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    String flat;
    Boolean ADMIN;
    CommonClass object;
    private TextView tvNewFamily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        initialize();

        object = new CommonClass();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT, "");
        ADMIN = sharedPreferences.getBoolean(sharedPrefrencesClass.SP_ADMIN, false);

        if (!ADMIN) {
//            ADMIN = false;
            tvNewFamily.setVisibility(View.GONE);
        } else {
//            ADMIN = true;
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

                adapter.linearLayout.setVisibility(View.GONE);

                if (!ADMIN) {
                    adapter.imvSetting.setVisibility(View.GONE);
                }

                adapter.imvSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (adapter.linearLayout.getVisibility() == View.GONE) {
                            adapter.linearLayout.setVisibility(View.VISIBLE);
                        } else {
                            adapter.linearLayout.setVisibility(View.GONE);
                        }
                    }
                });

                ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Duration, android.R.layout.simple_spinner_item);
                adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter.spinner.setAdapter(adapterCategory);

                object.referenceLocationCitizen(model.getFlat()).child(model.getPhone()).child("duration").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String string = snapshot.getValue().toString();

                        int position = adapterCategory.getPosition(string);

                        adapter.spinner.setSelection(position);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                adapter.btnLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //set intent
                        Intent intent = new Intent(FamilyActivity.this, MapFamilyActivity.class);

                        //set Extra
                        intent.putExtra("phone", model.getPhone());

                        //start Activity
                        startActivity(intent);
                    }
                });

                adapter.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        object.referenceLocationCitizen(flat).child(model.getPhone()).child("duration").setValue(adapter.spinner.getItemAtPosition(position).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

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