package com.example.emergencycontacts;

import java.util.ArrayList;

public class ContactsModel {
    private String contact;
    private String details;
    private float dist;
    private int hits;

    private String id;
    private String lat;
    private String lon;
    private String name;
    private float rating;
    private String service;
    ArrayList<String> spinnerList = new ArrayList<>();
    private String town;

    public int getHits() {
        return this.hits;
    }

    public float getRating() {
        return this.rating;
    }

    public String getService() {
        return this.service;
    }

    public float getDist() {
        return this.dist;
    }

    public String getLat() {
        return this.lat;
    }

    public String getLon() {
        return this.lon;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getTown() {
        return this.town;
    }

    public String getDetails() {
        return this.details;
    }

    public String getContact() {
        return this.contact;
    }

    public ArrayList<String> getSpinnerList() {
        return this.spinnerList;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public void setTown(String town2) {
        this.town = town2;
    }

    public void setDetails(String details2) {
        this.details = details2;
    }

    public void setContact(String contact2) {
        this.contact = contact2;
    }

    public void setLat(String lat2) {
        this.lat = lat2;
    }

    public void setLon(String lon2) {
        this.lon = lon2;
    }

    public void setHits(int hits2) {
        this.hits = hits2;
    }

    public void setRating(float rating2) {
        this.rating = rating2;
    }

    public void setDist(float dist2) {
        this.dist = dist2;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setService(String service2) {
        this.service = service2;
    }

    public void setSpinnerList(ArrayList<String> spinnerList2) {
        this.spinnerList = spinnerList2;
    }
}
