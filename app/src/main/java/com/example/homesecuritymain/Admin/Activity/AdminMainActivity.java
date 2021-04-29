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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.homesecuritymain.Admin.Adapter.AdapterAdminGrievanceMain;
import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.Login.Activity.Common.SplashScreen;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Adapters.AdapterGrievanceActive;
import com.example.homesecuritymain.citizen.Model.ModelGrievance;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminMainActivity extends AppCompatActivity {
    private ImageView imOpen,imSetting,imNewGuard,imNewAdmin,ivNotices;
    private CommonClass object;

    public RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    private LinearLayout linearLayout;

    FirebaseRecyclerOptions<ModelGrievance> option;
    FirebaseRecyclerAdapter<ModelGrievance, AdapterAdminGrievanceMain> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        initialize();

        object = new CommonClass();

        linearLayout.setVisibility(View.GONE);

        imNewGuard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,AllGuardAdminActivity.class));
            }
        });

        ivNotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,NoticeAdminActivity.class));
            }
        });

        imOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayout.getVisibility() == View.GONE){
                    linearLayout.setVisibility(View.VISIBLE);
                }else{
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });

        //show views
        linearLayoutManager = new LinearLayoutManager(AdminMainActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        option = new FirebaseRecyclerOptions.Builder<ModelGrievance>().setQuery(object.referenceGrievanceActive(), ModelGrievance.class).build();
        load();
    }

    private void load() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelGrievance, AdapterAdminGrievanceMain>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterAdminGrievanceMain adapter, int i, @NonNull ModelGrievance model) {
                adapter.linearLayout.setVisibility(View.GONE);
                adapter.tvTime.setText(model.getPreferredTime());
                adapter.tvDate.setText(model.getPreferredDate());
                adapter.tvCategory.setText(model.getCategory());
                adapter.tvDescription.setText(model.getGrievance());

                adapter.edName.setText(model.getAssignedHelpName());
                adapter.edPhone.setText(model.getAssignedHelpNumber());

                adapter.tvMoreDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (adapter.linearLayout.getVisibility() == View.GONE) {
                            adapter.linearLayout.setVisibility(View.VISIBLE);
                        }else {
                            adapter.linearLayout.setVisibility(View.GONE);
                        }
                    }
                });

                adapter.btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //take name and phone and update
                        String name = adapter.edName.getText().toString().trim();
                        String phone = adapter.edPhone.getText().toString().trim();

                        //active grievance
                        object.referenceGrievanceActiveAssignedHelpName(model.getFirebaseUID()).setValue(name);
                        object.referenceGrievanceActiveAssignedHelpPhone(model.getFirebaseUID()).setValue(phone);

                        //flat
                        object.referenceGrievanceCitizenActiveAssignedHelpName(model.getFlat(),model.getFirebaseUID()).setValue(name);
                        object.referenceGrievanceCitizenActiveAssignedHelpNumber(model.getFlat(),model.getFirebaseUID()).setValue(phone);

                        //all grievance
//                        object.referenceGrievanceAllAssignedHelpName(model.getFirebaseUID()).setValue(name);
//                        object.referenceGrievanceAllAssignedHelpPhone(model.getFirebaseUID()).setValue(phone);


                        Toast.makeText(AdminMainActivity.this, "details updated", Toast.LENGTH_SHORT).show();
                    }
                });

                adapter.tvCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //call user who registered the grievance
                    }
                });
            }

            @NonNull
            @Override
            public AdapterAdminGrievanceMain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_admin_grievance_main, parent, false);
                return new AdapterAdminGrievanceMain(v);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void initialize() {
        recyclerView = findViewById(R.id.Rv_AdminMainActivity);

        imNewGuard = findViewById(R.id.Iv_AdminMainActivity_Guard);
        imOpen = findViewById(R.id.Iv_AdminMainActivity_OpenLinerLayout);
        ivNotices = findViewById(R.id.Iv_AdminMainActivity_Notice);
//        imSetting = findViewById(R.id.Iv_AdminMainActivity_Setting);

        linearLayout = findViewById(R.id.Ll_AdminMainActivity_SettingsPannel);
    }
}