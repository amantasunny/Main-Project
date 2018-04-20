package com.example.akhila.finalproject;



/**
 * Created by user on 27-10-2017.
 */


public class Savedata {

    Double lat,lon;
    String uid;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(Double lat) {
        this.uid = uid;
    }

    public Double getlon() {
        return lon;
    }

    public void setlon(Double lon) {
        this.lon = lon;
    }

    public Savedata(Double lat, Double lon) {

        this.lat = lat;

        this.lon = lon;
    }

}
