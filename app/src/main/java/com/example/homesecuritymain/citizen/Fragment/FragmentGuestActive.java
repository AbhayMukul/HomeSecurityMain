package com.example.homesecuritymain.citizen.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DatabaseRefrencesFirebase;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.CommonClasses.ModelCommon.ModelActiveGuest;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.GuestsActivity;
import com.example.homesecuritymain.citizen.Adapters.AdapterGuestActiveCitizen;
import com.example.homesecuritymain.guard.Activity.CallActivity;
import com.example.homesecuritymain.guard.Adapters.AdapterActiveGuard;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentGuestActive extends Fragment {
    private static final int REQUEST_CALL = 1;
    public RecyclerView recyclerView;
    public ImageView imageView;
    public TextView textView;
    public LinearLayout linearLayout;
    LinearLayoutManager linearLayoutManager;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    String flat, citizenID;

    //Firebase Database
    FirebaseRecyclerOptions<ModelActiveGuest> option;
    FirebaseRecyclerAdapter<ModelActiveGuest, AdapterGuestActiveCitizen> firebaseRecyclerAdapter;

    CommonClass object;

    public static FragmentGuestActive getInstance() {
        FragmentGuestActive fragmentGuestActive = new FragmentGuestActive();
        return fragmentGuestActive;
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
        imageView = view.findViewById(R.id.Tb_Lr_Iv);
        textView = view.findViewById(R.id.Tb_Lr_Tv);
        linearLayout = view.findViewById(R.id.Tb_Lr_Ll);

        linearLayout.setVisibility(View.GONE);

        sharedPreferences = getContext().getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT, "");
        citizenID = sharedPreferences.getString(sharedPrefrencesClass.SP_PHONE, "");

        object = new CommonClass();
        object.referenceGuestCitizenActive(flat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long aLong = snapshot.getChildrenCount();
                Integer guests = aLong.intValue();

                if (guests == 0) {
                    recyclerView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    imageView.setImageResource(R.drawable.ic_clipboard);
                    textView.setText("You dont have any active guests");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //show views
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        option = new FirebaseRecyclerOptions.Builder<ModelActiveGuest>().setQuery(FirebaseDatabase.getInstance().getReference().child("citizen").child(flat).child("GUEST").child("Active"), ModelActiveGuest.class).build();
        load();

        return view;
    }

    private void load() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelActiveGuest, AdapterGuestActiveCitizen>(option) {
            @NonNull
            @Override
            protected void onBindViewHolder(@NonNull AdapterGuestActiveCitizen adapter, int i, @NonNull ModelActiveGuest model) {
                adapter.tvName.setText(model.getName());
                adapter.tvWork.setText(model.getWork());

                adapter.btnExit.setEnabled(!model.getSTOP());

                adapter.tvCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //call Guest
                        if (model.getNumber().trim().length() > 0) {
                            if (ContextCompat.checkSelfPermission(getContext(),
                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                            } else {
                                String dial = "tel:" + model.getNumber();
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                            }
                        } else {
                            Toast.makeText(getContext(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                adapter.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //set Boolean STOP to true
                        object.referenceGuestCitizenActive(flat).child(model.getKeyUID()).child("stop").setValue(!model.getSTOP());
                        object.referenceGuestGuardActive().child(model.getKeyUID()).child("stop").setValue(!model.getSTOP());
                    }
                });

                adapter.btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!model.getSTOP()) {
                            //set Value for time and date for exit
                            //Citizen
                            object.referenceGuestCitizenAll(flat).child(model.getKeyUID()).child("citizenOut").setValue(citizenID);
                            object.referenceGuestCitizenAll(flat).child(model.getKeyUID()).child("dateOutCitizen").setValue(new DateAndTimeClass().getCurrentDate());
                            object.referenceGuestCitizenAll(flat).child(model.getKeyUID()).child("timeOutCitizen").setValue(new DateAndTimeClass().getCurrentTime());

                            //Guard
                            object.referenceGuestGuardAll().child(model.getKeyUID()).child("citizenOut").setValue(citizenID);
                            object.referenceGuestGuardAll().child(model.getKeyUID()).child("dateOutCitizen").setValue(new DateAndTimeClass().getCurrentDate());
                            object.referenceGuestGuardAll().child(model.getKeyUID()).child("timeOutCitizen").setValue(new DateAndTimeClass().getCurrentTime());

                            //remove value from Active
                            object.referenceGuestCitizenActive(flat).child(model.getKeyUID()).removeValue();

                            //allow guard exit from society
//                            object.referenceGuestGuardActive().child(model.getKeyUID()).child("allowedExit").setValue(true);
                        } else {
                            Toast.makeText(getContext(), "the guest cannot exit due to being stopped", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public AdapterGuestActiveCitizen onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_guest_active_citizen, parent, false);
                return new AdapterGuestActiveCitizen(v);
            }

        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission ACCEPTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
