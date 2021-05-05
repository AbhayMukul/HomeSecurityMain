package com.example.homesecuritymain.guard.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.Admin.Model.GuardDetailsModel;
import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.SharedPrefrencesClass;
import com.example.homesecuritymain.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GuardSettingActivity extends AppCompatActivity {
    GuardDetailsModel model;
    private String name,address;
    private TextView tvId,tvPassword,tvDateJoined,tvShift,tvPhone;
    private EditText edName,edAddress;
    private Button btnExit,btnUpdate;

    private CommonClass object;

    SharedPreferences sharedPreferences;
    SharedPrefrencesClass sharedPrefrencesClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_setting);

        initialize();

        model = (GuardDetailsModel) getIntent().getSerializableExtra("modelGuardAll");

       setText();

       object = new CommonClass();

       tvPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(tvPassword.getText().equals("********")){
                   tvPassword.setText(model.getPassword());
               }else {
                   tvPassword.setText("********");
               }
           }
       });

       btnUpdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getText();

               object.referenceGuardLoginAddress(model.getID()).setValue(address);
               object.referenceGuardLoginName(model.getID()).setValue(name);
           }
       });

       btnExit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               logout();
           }
       });
    }

    private void logout() {
        sharedPreferences = getSharedPreferences(sharedPrefrencesClass.LoginDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(sharedPrefrencesClass.SP_ACCOUNTTYPE,"");
        editor.putString(sharedPrefrencesClass.SP_GUARDID,"");
        editor.putBoolean(sharedPrefrencesClass.SP_LOGGEDIN,false);
        editor.putString(sharedPrefrencesClass.SP_NAME,"");
        editor.putString(sharedPrefrencesClass.SP_PHONE,"");
        editor.putString(sharedPrefrencesClass.SP_SHIFT,"");

        editor.commit();
    }

    private void getText() {
        name = edName.getText().toString().trim();
        address = edAddress.getText().toString().trim();
    }

    private void setText() {
        tvId.setText(model.getID());
        tvDateJoined.setText(model.getDateJoined());
        tvPassword.setText("********");
        tvPhone.setText(model.getPhone());
        tvShift.setText(model.getShift());

        edName.setText(model.getName());
        edAddress.setText(model.getAddress());
    }

    private void initialize() {
        tvId = findViewById(R.id.Tv_GuardSettingActivity_ID);
        tvPassword = findViewById(R.id.Tv_GuardSettingActivity_Passsword);
        tvShift = findViewById(R.id.Tv_GuardSettingActivity_Shift);
        tvPhone = findViewById(R.id.Ed_GuardSettingActivity_Phone);
        tvDateJoined = findViewById(R.id.Tv_GuardSettingActivity_DateJoined);

        edAddress = findViewById(R.id.Ed_GuardSettingActivity_Address);
        edName = findViewById(R.id.Ed_GuardSettingActivity_Name);

        btnExit = findViewById(R.id.Btn_GuardSettingActivity_Logout);
        btnUpdate = findViewById(R.id.Btn_GuardSettingActivity_Update);

    }
}