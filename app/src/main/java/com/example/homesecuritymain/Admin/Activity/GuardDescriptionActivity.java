package com.example.homesecuritymain.Admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.homesecuritymain.Admin.Model.GuardDetailsModel;
import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelAllGuest;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Adapters.AdapterGuestAllCitizen;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class GuardDescriptionActivity extends AppCompatActivity {
    GuardDetailsModel model;
    private LinearLayoutManager linearLayoutManager,linearLayoutManagerExit;
    private TextView tvName,tvId,tvPassword,tvDateJoined,tvShift;
    private EditText edPhone,edAddress;
    private TextView tvMoreDetails,tvGuestsEntered,tvGuestsExited;
    private Button btnExit,btnUpdate;
    private LinearLayout linearLayout,linearLayoutRecyclerGuestEntered,linearLayoutNoViewGuestEntered,linearLayoutGuestOverallExited,linearGuestNoViewsExited;
    private RecyclerView recyclerViewGuestEntered,recyclerViewGuestExited;

    private CommonClass object;

    FirebaseRecyclerOptions<ModelAllGuest> optionEntered,optionExited;
    FirebaseRecyclerAdapter<ModelAllGuest, AdapterGuestAllCitizen> firebaseRecyclerAdapterEntered,firebaseRecyclerAdapterExited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_description);

        initialize();

        object = new CommonClass();

        model = (GuardDetailsModel) getIntent().getSerializableExtra("guardDetails");

        linearLayout.setVisibility(View.GONE);
        linearLayoutRecyclerGuestEntered.setVisibility(View.GONE);
        linearLayoutNoViewGuestEntered.setVisibility(View.GONE);
        linearGuestNoViewsExited.setVisibility(View.GONE);
        linearLayoutGuestOverallExited.setVisibility(View.GONE);

        //show views
        linearLayoutManager = new LinearLayoutManager(GuardDescriptionActivity.this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        linearLayoutManagerExit = new LinearLayoutManager(GuardDescriptionActivity.this);
        linearLayoutManagerExit.setStackFromEnd(true);
        linearLayoutManagerExit.setReverseLayout(true);

        recyclerViewGuestEntered.setLayoutManager(linearLayoutManager);
        recyclerViewGuestEntered.setHasFixedSize(true);

        recyclerViewGuestExited.setLayoutManager(linearLayoutManagerExit);
        recyclerViewGuestExited.setHasFixedSize(true);

        setData();

        object.referenceGuestGuardAll().orderByChild("guardIn").startAt(model.getID()).endAt(model.getID() + "/uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long total = snapshot.getChildrenCount();
                if(total.intValue() == 0){
                    recyclerViewGuestEntered.setVisibility(View.GONE);
                    linearLayoutNoViewGuestEntered.setVisibility(View.VISIBLE);
                }else {
                    optionEntered = new FirebaseRecyclerOptions.Builder<ModelAllGuest>().setQuery(object.referenceGuestGuardAll().orderByChild("guardIn").startAt(model.getID()).endAt(model.getID() + "/uf8ff"), ModelAllGuest.class).build();
                    loadEntered();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        object.referenceGuestGuardAll().orderByChild("guardOut").startAt(model.getID()).endAt(model.getID() + "/uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long total = snapshot.getChildrenCount();
                if(total.intValue() == 0){
                    recyclerViewGuestExited.setVisibility(View.GONE);
                    linearGuestNoViewsExited.setVisibility(View.VISIBLE);
                }else {
                    optionExited = new FirebaseRecyclerOptions.Builder<ModelAllGuest>().setQuery(object.referenceGuestGuardAll().orderByChild("guardOut").startAt(model.getID()).endAt(model.getID() + "/uf8ff"), ModelAllGuest.class).build();
                    loadExited();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tvGuestsExited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayoutGuestOverallExited.getVisibility() == View.GONE){
                    linearLayoutGuestOverallExited.setVisibility(View.VISIBLE);
                }else {
                    linearLayoutGuestOverallExited.setVisibility(View.GONE);
                }
            }
        });

        tvMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayout.getVisibility() == View.GONE){
                    linearLayout.setVisibility(View.VISIBLE);
                }else {
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });

        tvGuestsEntered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayoutRecyclerGuestEntered.getVisibility() == View.GONE){
                    linearLayoutRecyclerGuestEntered.setVisibility(View.VISIBLE);
                }else {
                    linearLayoutRecyclerGuestEntered.setVisibility(View.GONE);
                }
            }
        });

    }

    private void loadExited() {
        firebaseRecyclerAdapterExited = new FirebaseRecyclerAdapter<ModelAllGuest, AdapterGuestAllCitizen>(optionEntered) {
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

        firebaseRecyclerAdapterExited.startListening();
        recyclerViewGuestExited.setAdapter(firebaseRecyclerAdapterExited);
    }

    private void loadEntered() {
        firebaseRecyclerAdapterEntered = new FirebaseRecyclerAdapter<ModelAllGuest, AdapterGuestAllCitizen>(optionEntered) {
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

        firebaseRecyclerAdapterEntered.startListening();
        recyclerViewGuestEntered.setAdapter(firebaseRecyclerAdapterEntered);
    }


    private void setData(){
        tvName.setText(model.getName());
        tvShift.setText(model.getShift());
        tvDateJoined.setText(model.getDateJoined());
        tvId.setText(model.getID());

        edAddress.setText(model.getAddress());
        edPhone.setText(model.getPhone());

        if(model.getPassword().equals("")){
            tvPassword.setText("Guard not registered on thier account yet");
        }else {
            tvPassword.setText(model.getPassword());
        }
    }

    private void initialize() {
        tvId = findViewById(R.id.Tv_GuardDescriptionActivity_ID);
        tvMoreDetails = findViewById(R.id.Tv_GuardDescriptionActivity_MoreDetails);
        tvPassword = findViewById(R.id.Tv_GuardDescriptionActivity_Passsword);
        tvShift = findViewById(R.id.Tv_GuardDescriptionActivity_Shift);
        tvName = findViewById(R.id.Tv_GuardDescriptionActivity_Name);
        tvDateJoined = findViewById(R.id.Tv_GuardDescriptionActivity_DateJoined);

        edAddress = findViewById(R.id.Ed_GuardDescriptionActivity_Address);
        edPhone = findViewById(R.id.Ed_GuardDescriptionActivity_Phone);

        btnExit = findViewById(R.id.Btn_GuardDescriptionActivity_JobLeft);
        btnUpdate = findViewById(R.id.Btn_GuardDescriptionActivity_Update);

        tvGuestsEntered = findViewById(R.id.Tv_GuardDescriptionActivity_GuardGuestEntered);
        tvGuestsExited = findViewById(R.id.Tv_GuardDescriptionActivity_GuardGuestExited);

        linearLayout = findViewById(R.id.Ll_GuardDescriptionActivity_MoreDetails);
        linearLayoutRecyclerGuestEntered = findViewById(R.id.Ll_GuardDescriptionActivity_RecyclerView_GuestEntered);
        linearLayoutNoViewGuestEntered = findViewById(R.id.Ll_GuardDescriptionActivity_NoVIew_GuestEntered);

        linearLayoutGuestOverallExited = findViewById(R.id.Ll_GuardDescriptionActivity_RecyclerView_GuestExited);
        linearGuestNoViewsExited = findViewById(R.id.Ll_GuardDescriptionActivity_NoVIew_GuestExited);

        recyclerViewGuestEntered = findViewById(R.id.Rv_GuardDescriptionActivity_GuestEntered);
        recyclerViewGuestExited = findViewById(R.id.Rv_GuardDescriptionActivity_GuestExited);
    }
}