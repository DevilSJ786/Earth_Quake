package com.equake.earth_quake.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquake {

    private final String magnitude;
    private final String location;
    private final String location1;
    private final String date;
    private final String time;

    public Earthquake(String magnitude, String location, String location1, String date, String time) {
        this.magnitude = magnitude;
        Log.d("devil", "earthquake call");

        this.location1 = location1;
        this.location = location;
        this.date = date;
        this.time = time;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getLocation1() {
        return location1;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}

