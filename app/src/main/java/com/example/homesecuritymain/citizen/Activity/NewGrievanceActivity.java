package com.example.homesecuritymain.citizen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.R;
import com.example.homesecuritymain.citizen.Model.ModelGrievance;
import com.example.homesecuritymain.citizen.Model.ModelGrievanceAll;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class NewGrievanceActivity extends AppCompatActivity {
    String Date = null, Time = null, Grievance = null, Category = null;

    Spinner spinner;
    EditText edGrievance;
    TextView tvTime, tvDate;
    Button btn;

    DatabaseReference mUserDatabaseGrievance,mUserDatabaseCitizen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_grievance);

        initialize();

        mUserDatabaseGrievance = FirebaseDatabase.getInstance().getReference("Grievance");
        mUserDatabaseCitizen = FirebaseDatabase.getInstance().getReference("citizen");

        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this, R.array.categoryGrievance, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategory);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open DatePicker
                showDatepickerDialogue();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open TimePicker
                showTimepickerDialogue();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mUserDatabaseGrievance.push().getKey();
                Grievance = edGrievance.getText().toString().trim();

//                Toast.makeText(NewGrievanceActivity.this, Grievance, Toast.LENGTH_SHORT).show();

                ModelGrievance modelGrievance = new ModelGrievance(Category,Grievance,Time,Date,"demo","name","8793215306",false,false,key,"","",new DateAndTimeClass().getCurrentTime(),new DateAndTimeClass().getCurrentDate());
                ModelGrievanceAll modelGrievanceAll = new ModelGrievanceAll(Category,Grievance,Time,Date,"demo","name","8793215306",false,key,"not fixed yet","not fixed yet","","",new DateAndTimeClass().getCurrentTime(),new DateAndTimeClass().getCurrentDate());

                mUserDatabaseCitizen.child("demo").child("Grievance").child("Active").child(key).setValue(modelGrievance);
                mUserDatabaseCitizen.child("demo").child("Grievance").child("All").child(key).setValue(modelGrievanceAll);

                mUserDatabaseGrievance.child("Active").child(key).setValue(modelGrievance);
                mUserDatabaseGrievance.child("All").child(key).setValue(modelGrievanceAll);

                Toast.makeText(NewGrievanceActivity.this, "your grievance has been recorded", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initialize() {
        spinner = findViewById(R.id.Sp_NewGrievanceActivity_Category);

        edGrievance = findViewById(R.id.Ed_NewGrievanceActivity_Grievance);

        tvDate = findViewById(R.id.Tv_NewGrievanceActivity_Date);
        tvTime = findViewById(R.id.Tv_NewGrievanceActivity_Time);

        btn = findViewById(R.id.Btn_NewGrievanceActivity_Done);
    }

    private void showDatepickerDialogue() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date = "" + dayOfMonth + "/" + month + "/" + year;
                        tvDate.setText(Date);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimepickerDialogue() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(minute < 10){
                            Time = "" + hourOfDay + ":0" + minute;
                        }else{
                            Time = "" + hourOfDay + ":" + minute;
                        }
                        tvTime.setText(Time);
                    }
                }, 12, 0, false
        );
        timePickerDialog.show();
    }

//    private void showTimepickerDialogue(){
//        MaterialTimePicker picker =
//                new MaterialTimePicker.Builder()
//                        .setTimeFormat(TimeFormat.CLOCK_12H)
//                        .setHour(12)
//                        .setMinute(10).setTitleText("done")
//                        .build();
//
//        picker.show(fragmentManager, "tag");
//    }
}