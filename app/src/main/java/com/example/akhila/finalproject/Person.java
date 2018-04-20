package com.example.akhila.finalproject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Person {
    String location;
    String address;
    String contact;
    String rate;
    String imageUri;

    public Person(String location, String address,String contact, String rate,String imageUri) {
        this.location = location;
        this.address = address;
        this.contact = contact;
        this.rate = rate;
        this.imageUri = imageUri;
    }

    public Person() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getContact() {
        return contact;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
