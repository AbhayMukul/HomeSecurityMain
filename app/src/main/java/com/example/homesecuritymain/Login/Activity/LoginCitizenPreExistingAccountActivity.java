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
import com.google.firebase.database.FirebaseDatabase;

public class LoginCitizenPreExistingAccountActivity extends AppCompatActivity {
    LinearLayoutManager linearLayoutManager;
    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    FirebaseRecyclerOptions<ModelCitizen> option;
    FirebaseRecyclerAdapter<ModelCitizen, AdapterPreExistingAccoutFlat> firebaseRecyclerAdapter;
    RecyclerView recyclerView;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_citizen_pre_existing_account);

        initialize();

        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        phone = sharedPreferences.getString(sharedPrefrencesClass.SP_PHONE, "");
        Log.e("phone", phone);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        option = new FirebaseRecyclerOptions.Builder<ModelCitizen>().setQuery(new DatabaseRefrencesFirebase().mUserDatabaseLoginMain.orderByChild("phone").startAt(phone).endAt(phone + "/uf8ff"), ModelCitizen.class).build();

        setRecyclerview();
    }

    private void setRecyclerview() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelCitizen, AdapterPreExistingAccoutFlat>(option) {
            @Override
            protected void onBindViewHolder(@NonNull AdapterPreExistingAccoutFlat adapter, int i, @NonNull ModelCitizen model) {
                adapter.tvFlat.setText(model.getFlat());

                adapter.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(LoginCitizenPreExistingAccountActivity.this,model.getName(), Toast.LENGTH_SHORT).show();

                        //set sharedPrefrences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(sharedPrefrencesClass.SP_FLATUID, model.getKeyUID());
                        editor.putBoolean(sharedPrefrencesClass.SP_LOGGEDIN, true);
                        editor.putString(sharedPrefrencesClass.SP_ACCOUNTTYPE,"citizen");
                        editor.putString(sharedPrefrencesClass.SP_NAME, model.getName());
                        editor.putString(sharedPrefrencesClass.SP_PHONE, phone);
                        editor.putBoolean(sharedPrefrencesClass.SP_ADMIN, model.getADMIN());
                        editor.putString(sharedPrefrencesClass.SP_FLAT, model.getFlat());
                        editor.commit();

                        startActivity(new Intent(LoginCitizenPreExistingAccountActivity.this, CitizenMainActivity.class));
                    }
                });
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
    }
}