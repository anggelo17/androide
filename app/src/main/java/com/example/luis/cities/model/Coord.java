package com.example.luis.cities.model;


import java.io.Serializable;

public class Coord implements Serializable {

    private String lon;
    private String lat;


    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}

