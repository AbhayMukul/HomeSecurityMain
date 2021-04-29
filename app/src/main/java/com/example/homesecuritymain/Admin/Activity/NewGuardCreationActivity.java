package com.example.homesecuritymain.Admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.homesecuritymain.Admin.Model.GuardDetailsModel;
import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.R;

public class NewGuardCreationActivity extends AppCompatActivity {
    private EditText edName,edPhone,edAddress,edID;
    private Button btn;

    private String name,phone,address,id,key,shift;
    private CommonClass object;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guard_creation);

        initialize();

        object = new CommonClass();

        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this, R.array.shift, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategory);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextFromEditText();

                key = object.mUserDatabaseGuardLogin.push().getKey();

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        shift = spinner.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                GuardDetailsModel model = new GuardDetailsModel(name,phone,"",key,id,true,new DateAndTimeClass().getCurrentDate(),"",address,shift);

                object.mUserDatabaseGuardLogin.child(key).setValue(model);

                Toast.makeText(NewGuardCreationActivity.this, "Account with ID " + id + " has been created", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getTextFromEditText() {
        name = edName.getText().toString().trim();
        phone = edPhone.getText().toString().trim();
        id = edID.getText().toString().trim();
        address = edAddress.getText().toString().trim();
    }

    private void initialize() {
        edAddress = findViewById(R.id.Ed_NewGuardCreationActivity_Address);
        edID = findViewById(R.id.Ed_NewGuardCreationActivity_ID);
        edName = findViewById(R.id.Ed_NewGuardCreationActivity_Name);
        edPhone = findViewById(R.id.Ed_NewGuardCreationActivity_Phone);

        btn = findViewById(R.id.Btn_NewGuardCreationActivity_Done);

        spinner = findViewById(R.id.Sp_NewGuardCreationActivity_Shift);
    }
}