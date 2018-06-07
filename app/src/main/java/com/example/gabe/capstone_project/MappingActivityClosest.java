package com.example.gabe.capstone_project;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class MappingActivityClosest extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int MY_PERMISSIONS_ACCESS_COURSE_LOCATION = 0;
    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 0;
    double userLatitude = 0;
    double userLongitude = 0;

    public Marker userPositionMarker = null;



    //Firebase Instance Grabber
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping_closest);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        mLocationListener = new LocationListener() {


            @Override
            public void onLocationChanged(android.location.Location location) {

                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                userLatitude = latitude;
                userLongitude = longitude;
                LatLng YourLocation = new LatLng(latitude, longitude);

                if(userPositionMarker != null){
                    userPositionMarker.remove();
                }
                Date setMarkerplaceTimestamp = Calendar.getInstance().getTime();
                userPositionMarker = mMap.addMarker(new MarkerOptions().position(YourLocation).title("YOU ARE HERE!"));

                Date currentTime = Calendar.getInstance().getTime();
                if( setMarkerplaceTimestamp.compareTo(currentTime) > 1){

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(YourLocation));
                }
                //Toast.makeText(MappingActivityClosest.this, "code", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
    }


    /*
    Map Initial set-up
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setMinZoomPreference(11);

        String data = getIntent().getExtras().getString("Result");
        if (data.contains("1")) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Shelter");
            ref.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Get map of locations in datasnapshot
                            //ParkingElement post = dataSnapshot.getValue(ParkingElement.class);
                            //AddAllParkingElements(post);
                            Toast.makeText(MappingActivityClosest.this, "Placing Shelter points"
                                    , Toast.LENGTH_SHORT).show();
                            ShelterMap((Map<String, Object>) dataSnapshot.getValue());

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
        }
        if (data.contains("2")) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Water");
            ref.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Get map of locations in datasnapshot
                            //ParkingElement post = dataSnapshot.getValue(ParkingElement.class);
                            //AddAllParkingElements(post);
                            Toast.makeText(MappingActivityClosest.this, "Placing Water Points"
                                    , Toast.LENGTH_SHORT).show();
                            WaterFountainMap((Map<String, Object>) dataSnapshot.getValue());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });}

        if (data.contains("3")) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Parking");
            ref.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Get map of locations in datasnapshot
                            //ParkingElement post = dataSnapshot.getValue(ParkingElement.class);
                            //AddAllParkingElements(post);
                            Toast.makeText(MappingActivityClosest.this, "Placing parking markers"
                                    , Toast.LENGTH_SHORT).show();
                            ParkingMap((Map<String, Object>) dataSnapshot.getValue());


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });}

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, "Permissions Good.", Toast.LENGTH_SHORT).show();
            mLocationManager.requestLocationUpdates("gps", 100, 0, mLocationListener);

            LatLng defaultLocation = new LatLng(34.10711, -117.2963962);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
        }

    }

    public void LocationUpdatesClick(View v) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permissions Good.", Toast.LENGTH_SHORT).show();
            mLocationManager.requestLocationUpdates("gps", 100, 0, mLocationListener);

            LatLng defaultLocation = new LatLng(37.422006, -122.084085);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
        }

        Intent i = new Intent(this, AddLocation.class);
        i.putExtra("longitude","" + userLongitude);
        i.putExtra("latitude", "" + userLatitude);
        startActivity(i);

    }


    public void goBack(View v){
        Intent goBack = new Intent(this,MainPage.class);
        startActivity(goBack);
    }

    //String to change color of the point
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }


    //              FIREBASE WIFI MAPLIST
    private void ParkingMap(Map<String, Object> users) {

        for (Map.Entry<String, Object> entry : users.entrySet()) {
            //Get map
            Map singleUser = (Map) entry.getValue();

            System.out.println("---"+singleUser.get("long")+" ; "+singleUser.get("lat"));

            if((singleUser.get("long")!= null) && (singleUser.get("lat") != null)){
                double stringLongValue = 0;
                double stringLatValue = 0;

                if (singleUser.get("long") instanceof Long) {
                    stringLongValue = ((Long) singleUser.get("long")).doubleValue();
                } else {
                    stringLongValue = (double) singleUser.get("long");
                }
                if (singleUser.get("lat") instanceof Long) {
                    stringLatValue = ((Long) singleUser.get("lat")).doubleValue();
                } else {
                    stringLatValue = (double) singleUser.get("lat");
                }
                LatLng parkingMarker = new LatLng(stringLatValue,stringLongValue);


        //Write to file
//                try {
//                    Toast.makeText(this, "1st", Toast.LENGTH_SHORT).show();
//                    Writer wk = new FileWriter("C:/Users/Gabe/Desktop/testFile.txt");
//                    //BufferedWriter FileWriterBW = new FileWriter("C:/Users/Gabe/Desktop/testFile.txt");
//                    Toast.makeText(this, "2nd step", Toast.LENGTH_SHORT).show();
//                    wk.append('c');
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


                mMap.addMarker(new MarkerOptions().position(parkingMarker).title("Parking")
                        .icon(bitmapDescriptorFromVector(this, R.drawable.car_marker)));
            }}}
    //         FIREBASE WIFI MAPLIST


    private void ShelterMap(Map<String, Object> shelterElement) {

        for (Map.Entry<String, Object> entry : shelterElement.entrySet()) {
            //Get map
            Map singleShelterElement = (Map) entry.getValue();

            System.out.println("---"+singleShelterElement.get("long")+" ; "+singleShelterElement.get("lat"));

            double stringLatValue = 0;
            double stringLongValue = 0;
            String titleInfo = String.valueOf(singleShelterElement.get("snippet"));
            if((singleShelterElement.get("long")!= null) && (singleShelterElement.get("lat") != null)){

                if (singleShelterElement.get("long") instanceof Long) {
                    stringLongValue = ((Long) singleShelterElement.get("long")).doubleValue();
                } else {
                    stringLongValue = (double) singleShelterElement.get("long");
                }
                if (singleShelterElement.get("lat") instanceof Long) {
                    stringLatValue = ((Long) singleShelterElement.get("lat")).doubleValue();
                } else {
                    stringLatValue = (double) singleShelterElement.get("lat");
                }

                LatLng ShelterMarker = new LatLng(stringLatValue,stringLongValue);
                mMap.addMarker(new MarkerOptions().position(ShelterMarker).title(titleInfo)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.shelter)));

            }}}

    private void WaterFountainMap(Map<String, Object> users) {

        for (Map.Entry<String, Object> entry : users.entrySet()) {
            //Get map
            Map singleUser = (Map) entry.getValue();

            System.out.println("---"+singleUser.get("long")+" ; "+singleUser.get("lat"));

            if((singleUser.get("long")!= null) && (singleUser.get("lat") != null)){
                double stringLongValue = 0;
                double stringLatValue = 0;

                if (singleUser.get("long") instanceof Long) {
                    stringLongValue = ((Long) singleUser.get("long")).doubleValue();
                } else {
                    stringLongValue = (double) singleUser.get("long");
                }
                if (singleUser.get("lat") instanceof Long) {
                    stringLatValue = ((Long) singleUser.get("lat")).doubleValue();
                } else{
                    stringLatValue = (double) singleUser.get("lat");
                }

                LatLng WaterMarker= new LatLng(stringLatValue,stringLongValue);
                mMap.addMarker(new MarkerOptions().position(WaterMarker).title("WaterFountain")
                        .icon(bitmapDescriptorFromVector(this, R.drawable.water)));

            }}}

    //Convert image to bitmap (used in marker drawing)
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void resetCameraOnUser(View v){
        LatLng UserLocation = new LatLng(userLatitude,userLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UserLocation));
    }



}
