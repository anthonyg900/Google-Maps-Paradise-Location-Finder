package com.example.gabe.capstone_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class addALocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alocation);


        String latitude= getIntent().getExtras().getString("latitude");
        Toast.makeText(this, latitude, Toast.LENGTH_SHORT).show();
        String longitude = getIntent().getExtras().getString("longitude");




    }
}
