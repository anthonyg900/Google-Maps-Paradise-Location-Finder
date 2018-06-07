package com.example.gabe.capstone_project;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public LatLng moveMeMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(14);

        //place Location Marker.
        String latitude = getIntent().getExtras().getString("latitude");
        String longitude = getIntent().getExtras().getString("longitude");
        Double alatitude = Double.parseDouble(latitude);
        Double alongitude = Double.parseDouble(longitude);
        LatLng youAreHereMarker = new LatLng(alatitude, alongitude);
        mMap.addMarker(new MarkerOptions().position(youAreHereMarker).title("You are Here."));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(youAreHereMarker));

        moveMeMarker = new LatLng(alatitude + .003, alongitude + .003);
        mMap.addMarker(new MarkerOptions().position(moveMeMarker).
                title("Move Me").draggable(true).icon(getMarkerIcon("#0000FF")
        ));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);

                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                moveMeMarker = arg0.getPosition();


            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });


    }

    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

//Add a Water Location
    public void getDraggedMarkerLocation(View view) {
        String dLongitude = String.valueOf(moveMeMarker.longitude);
        String dLatitude = String.valueOf(moveMeMarker.latitude);
        Toast.makeText(this, "Location is: " + dLatitude + "  " + dLongitude, Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Water");
        Map<String, Object> WaterData = new HashMap<>();
        WaterData.put("lat", moveMeMarker.latitude);
        WaterData.put("long", moveMeMarker.longitude);
        WaterData.put("snippet", "User generated Water Point");
        myRef.push().setValue(WaterData);
    }

//Add a Parking Location
    public void placeParkLocation(View view) {
        String dLongitude = String.valueOf(moveMeMarker.longitude);
        String dLatitude = String.valueOf(moveMeMarker.latitude);
        Toast.makeText(this, "Location is: " + dLatitude + "  " + dLongitude, Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Parking");
        Map<String, Object> parkingData = new HashMap<>();
        parkingData.put("lat", moveMeMarker.latitude);
        parkingData.put("long", moveMeMarker.longitude);
        parkingData.put("snippet", "User generated Parking");
        myRef.push().setValue(parkingData);
    }


}
