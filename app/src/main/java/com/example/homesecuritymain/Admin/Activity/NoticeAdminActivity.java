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
import android.widget.LinearLayout;

import com.example.homesecuritymain.Admin.Adapter.AdapterNotice;
import com.example.homesecuritymain.Admin.Model.NoticeModel;
import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DatabaseRefrencesFirebase;
import com.example.homesecuritymain.Login.Adapter.AdapterPreExistingAccoutFlat;
import com.example.homesecuritymain.Login.Model.ModelCitizen;
import com.example.homesecuritymain.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class NoticeAdminActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private ImageView imageView;

    CommonClass object;

    FirebaseRecyclerOptions<NoticeModel> option;
    FirebaseRecyclerAdapter<NoticeModel, AdapterNotice> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_admin);

        initialize();

        object = new CommonClass();

        linearLayout.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        object.referenceAdminNotices().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long along = snapshot.getChildrenCount();
                if(along.intValue() == 0){
                    recyclerView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }else {
                    option = new FirebaseRecyclerOptions.Builder<NoticeModel>().setQuery(object.referenceAdminNotices(), NoticeModel.class).build();
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
                startActivity(new Intent(NoticeAdminActivity.this,NewNoticeActivity.class));
            }
        });
    }

    private void load() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NoticeModel, AdapterNotice>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterNotice adapter, int i, @NonNull NoticeModel model) {
                adapter.tvDate.setText("Date : " + model.getDate());
                adapter.tvTime.setText("Time : " + model.getTime());
                adapter.tvSubject.setText(model.getSubject());

                adapter.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NoticeAdminActivity.this,NoticeReadActivity.class);
                        intent.putExtra("noticeData",model);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public AdapterNotice onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_notice, parent, false);
                return new AdapterNotice(v);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void initialize() {
        recyclerView = findViewById(R.id.Rv_NoticeAdminActivity);
        linearLayout = findViewById(R.id.Ll_NoticeAdminActivity);
        imageView = findViewById(R.id.Iv_NoticeAdminActivity_NewNotice);
    }
}