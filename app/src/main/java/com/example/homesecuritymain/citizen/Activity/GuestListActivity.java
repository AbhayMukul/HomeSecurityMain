package com.example.homesecuritymain.citizen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.homesecuritymain.R;

public class GuestListActivity extends AppCompatActivity {
    //recyclerView
    RecyclerView recyclerView;

    //Button
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_list);

        initialize();
    }

    private void initialize() {
        recyclerView = findViewById(R.id.Rv_GuestListACtivity);
        imageView = findViewById(R.id.Iv_GuestListActivity_NewGuest);
    }
}