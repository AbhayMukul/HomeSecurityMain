package com.example.homesecuritymain.Admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.homesecuritymain.Admin.Model.NoticeModel;
import com.example.homesecuritymain.CommonClasses.ClassCommon.CommonClass;
import com.example.homesecuritymain.CommonClasses.ClassCommon.DateAndTimeClass;
import com.example.homesecuritymain.R;

public class NewNoticeActivity extends AppCompatActivity {
    private EditText edSubject,edBody;
    private ImageView imageView;

    private String body,subject,key;

    CommonClass object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_notice);

        initialize();

        object = new CommonClass();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();

                key = object.referenceAdminNotices().push().getKey();

                NoticeModel noticeModel = new NoticeModel(subject,body,"8793215306","Abhay",new DateAndTimeClass().getCurrentTime(),new DateAndTimeClass().getCurrentDate());

                object.referenceAdminNotices().child(key).setValue(noticeModel);

                Toast.makeText(NewNoticeActivity.this, "Notice has been posted", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }

    private void getData() {
        body = edBody.getText().toString().trim();
        subject = edSubject.getText().toString().trim();
    }

    private void initialize() {
        edBody = findViewById(R.id.Ed_NewNoticeActivity_Body);
        edSubject = findViewById(R.id.Ed_NewNoticeActivity_Subject);
        imageView = findViewById(R.id.Iv_NewNoticeActivity_Done);
    }
}