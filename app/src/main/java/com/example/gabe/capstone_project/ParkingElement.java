package com.example.gabe.capstone_project;

/**
 * Created by Gabe on 2/6/2018.
 */

public class ParkingElement {

    private double latitude;
    private double longitude;
    private String snippet;

    public ParkingElement(){

    }

    public ParkingElement(double latitude, double longitude,String snippet) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.snippet = snippet;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
