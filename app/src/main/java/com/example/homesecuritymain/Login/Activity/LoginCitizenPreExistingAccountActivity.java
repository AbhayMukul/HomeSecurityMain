package com.example.homesecuritymain.Login.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DatabaseRefrencesFirebase;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.Login.Adapter.AdapterPreExistingAccoutFlat;
import com.example.homesecuritymain.Login.Model.ModelCitizen;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Activity.CitizenMainActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginCitizenPreExistingAccountActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    FirebaseRecyclerOptions<ModelCitizen> option;
    FirebaseRecyclerAdapter<ModelCitizen, AdapterPreExistingAccoutFlat> firebaseRecyclerAdapter;
    RecyclerView recyclerView;
    private String phone,flat;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_citizen_pre_existing_account);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        phone = sharedPreferences.getString(sharedPrefrencesClass.SP_PHONE, "");
        flat = sharedPreferences.getString(sharedPrefrencesClass.SP_FLAT,"");
        Log.e("phone", phone);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        linearLayout.setVisibility(View.GONE);

        Toast.makeText(this, "flatKeyUID" + sharedPreferences.getString(sharedPrefrencesClass.SP_FLATUID,""), Toast.LENGTH_SHORT).show();
        Log.e("flatKeyUID",sharedPreferences.getString(sharedPrefrencesClass.SP_FLATUID,"") + "  ::");


        option = new FirebaseRecyclerOptions.Builder<ModelCitizen>().setQuery(new DatabaseRefrencesFirebase().mUserDatabaseLoginMain.orderByChild("phone").startAt(phone).endAt(phone + "/uf8ff"), ModelCitizen.class).build();

        new DatabaseRefrencesFirebase().mUserDatabaseLoginMain.orderByChild("phone").startAt(phone).endAt(phone + "/uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long flats =  snapshot.getChildrenCount();
                if(flats.intValue() == 1 && !sharedPreferences.getString(sharedPrefrencesClass.SP_FLATUID,"").equals("")){
                    recyclerView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    setRecyclerview();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setRecyclerview() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelCitizen, AdapterPreExistingAccoutFlat>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterPreExistingAccoutFlat adapter, int i, @NonNull ModelCitizen model) {
                if(!flat.equals(model.getFlat())) {
                    adapter.tvFlat.setText(model.getFlat());

                    adapter.linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //set sharedPrefrences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(sharedPrefrencesClass.SP_FLATUID, model.getKeyUID());
                            editor.putBoolean(sharedPrefrencesClass.SP_LOGGEDIN, true);
                            editor.putString(sharedPrefrencesClass.SP_ACCOUNTTYPE, "citizen");
                            editor.putString(sharedPrefrencesClass.SP_NAME, model.getName());
                            editor.putString(sharedPrefrencesClass.SP_PHONE, phone);
                            editor.putBoolean(sharedPrefrencesClass.SP_ADMIN, model.getADMIN());
                            editor.putString(sharedPrefrencesClass.SP_FLAT, model.getFlat());
                            editor.commit();

                            startActivity(new Intent(LoginCitizenPreExistingAccountActivity.this, LoginPreExistingAccountSplashScreenActivity.class));
                        }
                    });
                }else {
                    adapter.linearLayout.getLayoutParams().height = 0;
                }
            }

            @NonNull
            @Override
            public AdapterPreExistingAccoutFlat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_resource_pre_existing_account_flats, parent, false);
                return new AdapterPreExistingAccoutFlat(v);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void initialize() {
        recyclerView = findViewById(R.id.Rv_LoginCitizenPreExistingAccountActivity);
        linearLayout = findViewById(R.id.Ll_LoginCitizenPreExistingAccountActivity);
    }
}