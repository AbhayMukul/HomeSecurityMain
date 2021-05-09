package com.example.homesecuritymain.citizen.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelGuestList;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Adapters.AdapterGuestActiveCitizen;
import com.example.homesecuritymain.citizen.Adapters.AdapterGuestList;
import com.example.homesecuritymain.citizen.Fragment.FragmentGuestAll;
import com.example.homesecuritymain.citizen.FragmentAdapter.ViewPageAdapterGuestActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuestListActivity extends AppCompatActivity {
    //recyclerView
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    LinearLayout linearLayout;

    //Button
    ImageView imageView;

    String flat;

    //Firebase Database
    DatabaseReference mUserDatabaseGuest;
    FirebaseRecyclerOptions<ModelGuestList> option;
    FirebaseRecyclerAdapter<ModelGuestList, AdapterGuestList> firebaseRecyclerAdapter;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT,"");

        mUserDatabaseGuest = FirebaseDatabase.getInstance().getReference("guest").child("Active");

        //show views
        linearLayoutManager = new LinearLayoutManager(GuestListActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        FirebaseDatabase.getInstance().getReference().child("citizen").child(flat).child("GuestList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long count = snapshot.getChildrenCount();
                if(count.intValue() == 0){
                    recyclerView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }else {
                    linearLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    option = new FirebaseRecyclerOptions.Builder<ModelGuestList>().setQuery(FirebaseDatabase.getInstance().getReference().child("citizen").child(flat).child("GuestList"), ModelGuestList.class).build();
                    load();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuestListActivity.this, AddNewGuestListActivity.class));
            }
        });
    }

    private void load() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelGuestList, AdapterGuestList>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterGuestList adapter, int i, @NonNull ModelGuestList model) {
                adapter.tvName.setText(model.getName());
                adapter.tvCode.setText("Code : " + model.getCode());
                adapter.btnCall.setText(model.getNumber());
            }

            @NonNull
            @Override
            public AdapterGuestList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_guestlist, parent, false);
                return new AdapterGuestList(v);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    private void initialize() {
        imageView = findViewById(R.id.Iv_GuestListActivity_NewGuest);

        recyclerView = findViewById(R.id.Rv_GuestListACtivity);

        linearLayout = findViewById(R.id.Ll_GuestListActivity);
    }
}