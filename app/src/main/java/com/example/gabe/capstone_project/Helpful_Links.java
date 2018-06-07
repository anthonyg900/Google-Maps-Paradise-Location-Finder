package com.example.gabe.capstone_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Helpful_Links extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpful__links);

        String data = getIntent().getExtras().getString("Result");
        Toast.makeText(this, "Intent data: " + data, Toast.LENGTH_SHORT).show();
    }


    public void goBack(View view){
        Intent i = new Intent(this,MainPage.class);
        startActivity(i);
    }
}
