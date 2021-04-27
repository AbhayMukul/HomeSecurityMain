package com.example.homesecuritymain.Admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.homesecuritymain.Admin.Model.NoticeModel;
import com.example.homesecuritymain.R;

public class NoticeReadActivity extends AppCompatActivity {
    private TextView tvSubject,tvTime,tvDate,tvBody,tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_read);

        initialize();

        NoticeModel model = (NoticeModel) getIntent().getSerializableExtra("noticeData");

        tvBody.setText(model.getBody());
        tvDate.setText(model.getDate());
        tvName.setText(model.getNameAdminUpload());
        tvSubject.setText(model.getSubject());
        tvTime.setText(model.getTime());

    }

    private void initialize() {
        tvSubject = findViewById(R.id.Tv_NoticeReadActivity_Subject);
        tvTime = findViewById(R.id.Tv_NoticeReadActivity_NoticeTime);
        tvName = findViewById(R.id.Tv_NoticeReadActivity_NoticePublisherName);
        tvDate = findViewById(R.id.Tv_NoticeReadActivity_NoticeDate);
        tvBody = findViewById(R.id.Tv_NoticeReadActivity_NoticeBody);
    }
}