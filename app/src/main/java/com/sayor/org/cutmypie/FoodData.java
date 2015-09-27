package com.sayor.org.cutmypie;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("FoodData")
public class FoodData extends ParseObject{

    String fooddesc;
    String feedcap;
    String timeexp;
    double lat;
    double lon;

    public FoodData(){
        super();
    }

    public String getFooddesc() {
        return getString(fooddesc);
    }

    public void setFooddesc(String fooddesc) {
        put("fooddesc", fooddesc);
    }

    public String getFeedcap() {
        return getString(feedcap);
    }

    public void setFeedcap(String feedcap) {
        put("feedcap", feedcap);
    }

    public String getTimeexp() {
        return getString(timeexp);
    }

    public void setTimeexp(String timeexp) {
        put("timeexp", timeexp);
    }

    public double getLat() {
        return getDouble(String.valueOf(lat));
    }

    public void setLat(double lat) {
        put("lat", lat);
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        put("lon", lon);
    }

    public String toString() {
        return "fooddesc = " + fooddesc + ", feedcap = " + feedcap + ", timeexp = " + timeexp + ", lat = " + lat + ", lon = " + lon;
    }
}
