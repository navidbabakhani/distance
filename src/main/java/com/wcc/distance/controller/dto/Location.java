package com.wcc.distance.controller.dto;

public class Location {
    private String postalCode;
    private double latitude;
    private double longitude;

    public Location(String postalCode, double latitude, double longitude) {
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
}
