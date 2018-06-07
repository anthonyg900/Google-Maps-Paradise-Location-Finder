package com.example.gabe.capstone_project;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

//CONNECTED TO ACTIVITY_MAIN_PAGE.XML

public class MainPage extends AppCompatActivity {

    private static final int MY_PERMISSIONS_ACCESS_COURSE_LOCATION = 0;
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 0;

    //Toggle buttons & Start button Identities
    ToggleButton toggleShelters;
    ToggleButton toggleParking;
    ToggleButton toggleWifi;
    Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        DialogueBox customDialog = new DialogueBox(this);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customDialog.show();

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_ACCESS_COURSE_LOCATION);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            }
        }
    }


    /*
    Start button will pass which toggle buttons are selected via
    Intent put extra, This is for the mapping function on the next page
    Start button will take user to the next page to display map with selected
    data
     */
    public void onStartButtonClick(View V) {

        /*
        Button Id's for toggle buttons
         */
        ToggleButton toggleShelters = (ToggleButton) findViewById(R.id.toggleShelters);
        ToggleButton toggleParking = (ToggleButton) findViewById(R.id.toggleParking);
        ToggleButton toggleWifi = (ToggleButton) findViewById(R.id.toggleWifi);

        //Check if the Toggle buttons have been selected or not
        boolean Shelters = toggleShelters.isChecked();
        boolean Parking = toggleParking.isChecked();
        boolean WiFi = toggleWifi.isChecked();

        //Create a string ("RESULT") based on Toggle button selection
        String Result = "";
        if (Shelters) {
            Result += "1";
        }
        if (Parking) {
            Result += "2";
        }
        if (WiFi) {
            Result += "3";
        }
        //If String is empty, leave it empty.


        //Pass string ("RESULT") to intent for Maps activity
        Intent goToMapsIntent = new Intent(this, MappingActivityClosest.class);
        goToMapsIntent.putExtra("Result", Result);
        //Go to Maps Intent
        startActivity(goToMapsIntent);

    }


}
