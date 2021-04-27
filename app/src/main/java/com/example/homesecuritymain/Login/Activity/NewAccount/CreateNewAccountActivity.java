package com.example.homesecuritymain.Login.Activity.NewAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DatabaseRefrencesFirebase;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.Login.Model.ModelCitizen;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Model.ModelLocation;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewAccountActivity extends AppCompatActivity {
    private EditText edPhone,edName,edFlat;
    private TextView tvCheck;
    private Button btn;

    CommonClass object;

    private Boolean confirm = false;
    private String phone,name,flat,key;

    DatabaseRefrencesFirebase refrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        initialize();

        object = new CommonClass();

        refrence = new DatabaseRefrencesFirebase();
        key = FirebaseDatabase.getInstance().getReference().push().getKey();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText();
                if(confirm) {
                    //register user
                    refrence.mUserDatabaseLoginDetails.child(phone).child("password").setValue("");
                    refrence.mUserDatabaseLoginDetails.child(phone).child("name").setValue(name);
                    refrence.mUserDatabaseLoginDetails.child(phone).child("first flat account KeyUID").setValue(key);

                    //register user with flat
                    refrence.mUserDatabaseLoginMain.child(key).setValue(new ModelCitizen(name,phone,key,true,flat,""));
                    ModelLocation modelLocation = new ModelLocation("0.00", "0.00", edName.getText().toString().trim(), new DateAndTimeClass().getCurrentTime(), "STOP");

                    object.referenceLocationCitizen(flat).child(edPhone.getText().toString().trim()).setValue(modelLocation);
                    object.referenceFamilyCitizen(flat).child(edPhone.getText().toString().trim()).setValue(new ModelCitizen(name,phone,key,true,flat,""));

                    Toast.makeText(CreateNewAccountActivity.this, "done", Toast.LENGTH_SHORT).show();
                    confirm = false;

                    edFlat.setText("");
                    edName.setText("");
                    edPhone.setText("");
                }else {
                    Toast.makeText(CreateNewAccountActivity.this, "press done again to confirm", Toast.LENGTH_SHORT).show();
                    confirm = true;
                }
            }
        });

    }

    private void getText(){
        phone = edPhone.getText().toString().trim();
        name = edName.getText().toString().trim();
        flat = edFlat.getText().toString().trim();
    }

    private void initialize() {
        edPhone = findViewById(R.id.Ed_CreateNewAccountActivity_PhoneNumber);
        edName = findViewById(R.id.Ed_CreateNewAccountActivity_Name);
        edFlat = findViewById(R.id.Ed_CreateNewAccountActivity_Flat);

        tvCheck = findViewById(R.id.Tv_CreateNewAccountActivity_GetDetails);

        btn = findViewById(R.id.Btn_CreateNewAccountActivity_Next);
    }
}